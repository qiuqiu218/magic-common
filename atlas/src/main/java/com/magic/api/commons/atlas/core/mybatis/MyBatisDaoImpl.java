package com.magic.api.commons.atlas.core.mybatis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.magic.api.commons.atlas.core.BaseDao;
import com.magic.api.commons.atlas.utils.reflection.Reflections;
import com.magic.api.commons.model.Page;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.apache.ibatis.reflection.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

/**
 * @param <T>
 * @param <PK>
 * @Doc 泛型Dao，提供多个数据源，根据id取余获取属于源
 * 需要在配置文件中配置bean idShardRange、sqlSessionShardTemplates、globalSqlSessionTemplate
 * 使用多数据源时，需要使用参数中带id的方法
 */
@SuppressWarnings("unchecked")
public class MyBatisDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

    /**
     * Entity的类型
     */
    protected Class<T> entityClass;

    /**
     * Entity的主键类型
     */
    protected Class<PK> pkClass;

    public String sqlMapNamespace = null;

    public static final String POSTFIX_INSERT = "insert";

    public static final String POSTFIX_INSERT_BATCH = "insertBatch";

    public static final String POSTFIX_UPDATE = "update";

    public static final String POSTFIX_DELETE = "delete";

    public static final String POSTFIX_GET = "get";

    public static final String POSTFIX_SELECT = "find";

    public static final String POSTFIX_SELECT_COUNT = "findCount";

    public static final String POSTFIX_SELECTPAGE = "findByPage";

    public static final String POSTFIX_SELECTPAGE_COUNT = "findByPageCount";

    public Map<Integer, Integer> dbModShardRange;

    protected Map<Integer, SqlSession> shardSqlSessionTemplates;

    protected SqlSession globalSqlSessionTemplate;
    /* 默认根据1024取余 */
    private static final int RANGE_SIZE = 1024;
    /* 分片查询线程池*/
    private static ExecutorService SELECT_EXEC;
    /*cpu核心数*/
    private static int CPU_CORE_NUMBER;
    /* 使用多线程处理分片id数据库操作的阀值,默认为cpu数量的1/2 */
    private static int PROC_EXEC_THRESHOLD;


    /**
     * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
     * SimpleHibernateDao<User, Long>
     */
    public MyBatisDaoImpl() {
        this.entityClass = Reflections.getSuperClassGenricType(getClass());
        this.pkClass = Reflections.getSuperClassGenricType(getClass(), 1);
        this.sqlMapNamespace = entityClass.getName();
        CPU_CORE_NUMBER = Runtime.getRuntime().availableProcessors();
        PROC_EXEC_THRESHOLD = CPU_CORE_NUMBER / 2;
        initExecutor();
    }

    /**
     * @param shardRange
     * @Des 设置唯一id hash取余的范围
     */
    @Resource(name = "idShardRange")
    public void setDbModShardRange(Map<String, String> shardRange) {
        if (shardRange == null || shardRange.size() <= 0) {
            dbModShardRange = (Map<Integer, Integer>) Maps.newTreeMap().put(0, RANGE_SIZE);
        } else {
            dbModShardRange = Maps.newTreeMap();
            for (Map.Entry<String, String> entry : shardRange.entrySet()) {
                dbModShardRange.put(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()));
            }
        }
    }

    /**
     * @param shardSqlSessionTemplates
     * @Des 设置多个sqlSessionTemplate，key为id hash取余的开始值
     */
    @Resource(name = "sqlSessionShardTemplates")
    public void setShardSqlSessionTemplate(Map<String, SqlSession> shardSqlSessionTemplates) {
        if (shardSqlSessionTemplates == null || shardSqlSessionTemplates.size() <= 0) {
            System.out.println("There is no sqlSessionTemplate");
            System.exit(0);
        } else {
            this.shardSqlSessionTemplates = Maps.newHashMap();
            for (Map.Entry<String, SqlSession> entry : shardSqlSessionTemplates.entrySet())
                this.shardSqlSessionTemplates.put(Integer.parseInt(entry.getKey()), entry.getValue());
        }
    }

    /**
     * @param sqlSession
     * @Des 全局的sqlSessionTemplate，用于不跨mysql实例存储的业务使用
     */
    @Resource(name = "globalSqlSessionTemplate")
    public void setGlobalSqlSessionTemplate(SqlSession sqlSession) {
        this.globalSqlSessionTemplate = sqlSession;
    }

    /**
     * @return
     */
    public SqlSession getSqlSession() {
        return globalSqlSessionTemplate;
    }

    /**
     * @param id
     * @return
     * @Des 获取跨实例存储数据的sqlSessionTemplate
     */
    public SqlSession getSqlSession(final PK id) {
        int mod = getMod(id);
        for (Map.Entry<Integer, Integer> entry : dbModShardRange.entrySet()) {
            if (mod >= entry.getKey() && mod <= entry.getValue()) {
                return shardSqlSessionTemplates.get(entry.getKey());
            }
        }
        return null;
    }

    public int getMod(final PK id) {
        return Math.abs(id.hashCode()) % RANGE_SIZE;
    }


    public String getSqlMapNamespace() {
        return sqlMapNamespace;
    }

    public PK insert(T entity) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int num = getSqlSession().insert(
                sqlMapNamespace + "." + POSTFIX_INSERT, entity);
        return pkClass.getConstructor(String.class).newInstance(
                String.valueOf(num));
    }

    @Override
    public PK insert(PK id, T entity) throws Exception {
        int num = getSqlSession(id).insert(
                sqlMapNamespace + "." + POSTFIX_INSERT, entity);
        return pkClass.getConstructor(String.class).newInstance(
                String.valueOf(num));
    }

    public List<PK> insert(List<T> entitys) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<PK> pkList = new ArrayList<PK>();
        for (T e : entitys)
            pkList.add(null == e ? null : insert(e));
        return pkList;
    }

    @Override
    public PK insertBatch(List<T> entitys) throws Exception {
        int num = getSqlSession().insert(
                sqlMapNamespace + "." + POSTFIX_INSERT_BATCH, entitys);
        return pkClass.getConstructor(String.class).newInstance(
                String.valueOf(num));
    }

    @Override
    public List<PK> insertBatch(final List<T> entitys, final List<PK> ids) throws Exception {
        Map<Integer, List<T>> mapEntities = new HashedMap();
        if (ids != null && ids.size() > 0) {
            for (int i = 0;i < ids.size();i++) {
                int mod = getMod(ids.get(i));
                for (Map.Entry<Integer, Integer> entry : dbModShardRange.entrySet()) {
                    if (mod >= entry.getKey() && mod <= entry.getValue()) {
                        if (mapEntities.get(entry.getKey()) == null) {
                            List<T> list = new ArrayList<T>();
                            list.add(entitys.get(i));
                            mapEntities.put(entry.getKey(), list);
                        } else {
                            mapEntities.get(entry.getKey()).add(entitys.get(i));
                        }
                    }
                }
            }
            for (Map.Entry<Integer, List<T>> entry : mapEntities.entrySet()) {
                shardSqlSessionTemplates.get(entry.getKey()).insert(sqlMapNamespace + "." + POSTFIX_INSERT_BATCH, entry.getValue());
            }
            return ids;
        }
        return null;
    }

    @Override
    public List<PK> insert(Map<PK, T> entitys) throws Exception {
        List<PK> pkList = new ArrayList<PK>();

        for (Map.Entry<PK, T> e : entitys.entrySet())
            pkList.add(null == e ? null : insert(e.getKey(), e.getValue()));
        return pkList;
    }

    @Override
    public PK insert(final String ql, final String[] paramNames, final Object[] values) throws Exception {
        long num = 0;
        if (values != null) {
            if (values.length == 1)
                num = getSqlSession().insert(sqlMapNamespace + "." + ql, values[0]);
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    HashMap<String, Object> map = new HashMap<>();
                    for (int i = 0; i < paramNames.length; i++) {
                        map.put(paramNames[i], values[i]);
                    }
                    num = getSqlSession().insert(sqlMapNamespace + "." + ql, map);
                }
            }

        } else {
            num += getSqlSession().insert(sqlMapNamespace + "." + ql);
        }
        return pkClass.getConstructor(String.class).newInstance(
                String.valueOf(num));
    }

    @Override
    public PK insert(PK id, String ql, final String[] paramNames, final Object[] values) throws Exception {

        int num = 0;
        if (values != null) {
            if (values.length <= 1)
                num = getSqlSession(id).insert(sqlMapNamespace + "." + ql,
                        values[0]);
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    num = getSqlSession(id).insert(sqlMapNamespace + "." + ql,
                            param);
                }
            }
        } else {
            num += getSqlSession(id).insert(sqlMapNamespace + "." + ql);
        }
        return pkClass.getConstructor(String.class).newInstance(
                String.valueOf(num));
    }

    public int delete(T entity) throws Exception {
        return getSqlSession().delete(sqlMapNamespace + "." + POSTFIX_DELETE,
                entity);
    }

    @Override
    public int delete(PK pk) throws Exception {
        return getSqlSession().delete(sqlMapNamespace + "." + POSTFIX_DELETE,
                pk);
    }

    @Override
    public int delete(PK pk, boolean isShard) throws Exception {
        if (isShard)
            return getSqlSession(pk).delete(sqlMapNamespace + "." + POSTFIX_DELETE,
                    pk);
        else
            return getSqlSession().delete(sqlMapNamespace + "." + POSTFIX_DELETE,
                    pk);
    }

    @Override
    public int delete(PK id, T entity) throws Exception {
        return getSqlSession(id).delete(sqlMapNamespace + "." + POSTFIX_DELETE,
                entity);
    }

    public int delete(List<T> entitys) throws Exception {
        int rowsEffected = 0;
        for (T e : entitys)
            rowsEffected += null == e ? 0 : delete(e);
        return rowsEffected;
    }

    @Override
    public int delete(final Map<PK, T> entitys) throws Exception {
        int rowsEffected = 0;
        for (Map.Entry<PK, T> e : entitys.entrySet())
            rowsEffected += null == e ? 0 : delete(e.getKey(), e.getValue());
        return rowsEffected;
    }

    @Override
    public int delete(final String ql, final String[] paramNames, final Object[] values) throws Exception {
        int num = 0;
        if (values != null) {
            if (values.length == 1)
                num = getSqlSession().delete(sqlMapNamespace + "." + ql,
                        values[0]);
            else if (paramNames != null && paramNames.length == values.length) {
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 0; i < paramNames.length; i++) {
                    map.put(paramNames[i], values[i]);
                }
                num = getSqlSession().delete(sqlMapNamespace + "." + ql,
                        map);
            }

        } else {
            num += getSqlSession().delete(sqlMapNamespace + "." + ql);
        }
        return num;
    }

    @Override
    public int delete(PK id, String ql, final String[] paramNames, final Object[] values) throws Exception {

        int num = 0;
        if (values != null) {
            if (values.length <= 1)
                num = getSqlSession(id).delete(sqlMapNamespace + "." + ql,
                        values[0]);
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    num = getSqlSession(id).delete(sqlMapNamespace + "." + ql,
                            param);
                }
            }
        } else {
            num += getSqlSession(id).delete(sqlMapNamespace + "." + ql);
        }
        return num;
    }

    public int update(T entity) throws Exception {
        return getSqlSession().update(sqlMapNamespace + "." + POSTFIX_UPDATE, entity);
    }

    @Override
    public int update(PK id, T entity) throws Exception {
        return getSqlSession(id).update(sqlMapNamespace + "." + POSTFIX_UPDATE, entity);
    }

    public int update(List<T> entity) throws Exception {
        int rowsEffected = 0;
        for (T e : entity)
            rowsEffected += null == e ? 0 : update(e);
        return rowsEffected;
    }

    @Override
    public int update(Map<PK, T> entitys) throws Exception {
        int rowsEffected = 0;
        for (Map.Entry<PK, T> e : entitys.entrySet())
            rowsEffected += null == e ? 0 : update(e.getKey(), e.getValue());
        return rowsEffected;
    }

    @Override
    public int update(final String ql, final String[] paramNames, final Object[] values) throws Exception {
        int num = 0;
        if (values != null) {
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                num = getSqlSession().update(sqlMapNamespace + "." + ql,
                        map);
            }
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    num = getSqlSession().update(sqlMapNamespace + "." + ql,
                            param);
                }
            }
        } else {
            num += getSqlSession().update(sqlMapNamespace + "." + ql);
        }
        return num;
    }

    @Override
    public int update(String ql, PK id, final String[] paramNames, final Object[] values) throws Exception {
        int num = 0;
        if (values != null) {
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                num = getSqlSession(id).update(sqlMapNamespace + "." + ql,
                        map);
            }
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    num = getSqlSession(id).update(sqlMapNamespace + "." + ql,
                            param);
                }
            }
        } else {
            num += getSqlSession(id).update(sqlMapNamespace + "." + ql);
        }
        return num;
    }

    public T get(final PK id) throws Exception {
        return (T) getSqlSession().selectOne(
                sqlMapNamespace + "." + POSTFIX_GET, id);
    }

    @Override
    public T get(PK id, boolean isShard) throws Exception {
        if (isShard)
            return (T) getSqlSession(id).selectOne(
                    sqlMapNamespace + "." + POSTFIX_GET, id);
        else
            return (T) getSqlSession().selectOne(
                    sqlMapNamespace + "." + POSTFIX_GET, id);
    }

    @Override
    public Object get(final String ql, final String[] paramNames, final Object[] values) throws Exception {
        if (values != null) {
            Object result = null;
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                result = getSqlSession().selectOne(sqlMapNamespace + "." + ql,
                        map);
            }
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession().selectOne(sqlMapNamespace + "." + ql,
                            param);
                }
            }
            return result;
        } else {
            return getSqlSession().selectOne(sqlMapNamespace + "." + ql);
        }
    }

    @Override
    public Object get(String ql, PK id, final String[] paramNames, final Object[] values) throws Exception {
        if (values != null) {
            Object result = null;
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                result = getSqlSession(id).selectOne(sqlMapNamespace + "." + ql,
                        map);
            }
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession(id).selectOne(sqlMapNamespace + "." + ql,
                            param);
                }
            }
            return result;
        } else {
            return getSqlSession(id).selectOne(sqlMapNamespace + "." + ql);
        }
    }

    public List<T> find(final T entity) throws Exception {
        return getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECT, entity);
    }

    @Override
    public List<T> find(List<PK> ids) throws Exception {
        return getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECT, ids);
    }

    @Override
    public List<T> find(List<PK> ids, boolean isShard) throws Exception {
        if (isShard) {
            if (ids.size() < PROC_EXEC_THRESHOLD)
                return coreFind(0, ids.size(), ids);
            else
                return assembleFindResult(ids);
        } else {
            return getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECT, ids);
        }
    }

    public final long findCount(final T entity) throws Exception {
        return getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECT, entity).size();
    }

    @Override
    public List findObj(String hql, final String[] paramNames, final Object[] values) throws Exception {

        if (values != null) {
            List result = null;
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                result = getSqlSession().selectList(sqlMapNamespace + "." + hql, map);
            }
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession().selectList(sqlMapNamespace + "." + hql, param);
                }
            }
            return result;
        } else {
            return getSqlSession().selectList(sqlMapNamespace + "." + hql);
        }
    }

    @Override
    public <X> List<X> find(String hql, String[] paramNames, Object[] values) throws Exception {
        if (values != null) {
            List<X> result = null;
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                result = getSqlSession().selectList(sqlMapNamespace + "." + hql, map);
            }
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession().selectList(sqlMapNamespace + "." + hql, param);
                }
            }
            return result;
        } else {
            return getSqlSession().selectList(sqlMapNamespace + "." + hql);
        }
    }

    @Override
    public <X> List<X> findCustom(String hql, String[] paramNames, Object[] values) throws Exception {
        if (values != null) {
            List<X> result = null;
            if (values.length <= 1)
                result = getSqlSession().selectList(sqlMapNamespace + "." + hql, values[0]);
            else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession().selectList(sqlMapNamespace + "." + hql, param);
                }
            }
            return result;
        } else {
            return getSqlSession().selectList(sqlMapNamespace + "." + hql);
        }
    }

    @Override
    public <X> List<X> findCustom(String hql, PK id, String[] paramNames, Object[] values) throws Exception {
        if (values != null) {
            List<X> result = null;
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                result = getSqlSession(id).selectList(sqlMapNamespace + "." + hql, map);
            } else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession(id).selectList(sqlMapNamespace + "." + hql, param);
                }
            }
            return result;
        } else {
            return getSqlSession().selectList(sqlMapNamespace + "." + hql);
        }
    }

    @Override
    public long findCount(final String ql, final String[] paramNames, final Object[] values) throws Exception {
        Long result = null;
        if (values != null) {
            if (paramNames == null) {
                return getSqlSession().selectOne(sqlMapNamespace + "." + ql, values[0]);
            }
            if (values.length <= 1) {
                HashMap<Object, Object> map = new HashMap<>();
                map.put(paramNames[0], values[0]);
                result = getSqlSession().selectOne(sqlMapNamespace + "." + ql, map);
            } else {
                if (paramNames != null && paramNames.length == values.length) {
                    final Map<String, Object> param = new TreeMap<>();
                    for (int i = 0; i < values.length; i++) {
                        param.put(paramNames[i], values[i]);
                    }
                    result = getSqlSession().selectOne(sqlMapNamespace + "." + ql, param);
                }
            }
        } else {
            result = getSqlSession().selectOne(sqlMapNamespace + "." + ql);
        }
        return result;
    }

    public Page<T> find(final Page<T> page, final T entity) throws Exception {
        RowBounds rowBounds = new RowBounds((page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        page.setResult((List<T>) getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECTPAGE, entity, rowBounds));
        page.setTotalCount(findCount(POSTFIX_SELECTPAGE_COUNT, null, new Object[]{entity}));
        return page;
    }

    @Override
    public Page<T> find(final Page<T> page, final String ql, final String[] paramNames, final Object[] values) throws Exception {
        RowBounds rowBounds = new RowBounds((page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        HashMap<String, Object> map = new HashMap<>();
        if (paramNames != null){
            for (int i = 0; i < paramNames.length; i++) {
                map.put(paramNames[i], values[i]);
            }
        }
        page.setResult((List<T>) getSqlSession().selectList(sqlMapNamespace + "." + ql, map.size() > 0 ? map : values, rowBounds));
        page.setTotalCount(findCount(ql + "Count", paramNames, values));
        return page;
    }


    /** more databases process ***/
    /**
     * init thread pool
     */
    private void initExecutor() {
        SELECT_EXEC = Executors.newFixedThreadPool(CPU_CORE_NUMBER);
    }

    /**
     * close thread pool
     */
    private void closeExecutor() {
        SELECT_EXEC.shutdown();
    }

    /**
     * 内部类,实现Callable，执行任务
     */
    class ExtraResult implements Callable<List<T>> {
        private List<PK> numbers;
        private int start;

        private int end;

        public ExtraResult(final List<PK> numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        public List<T> call() throws Exception {
            return coreFind(start, end, numbers);
        }
    }

    /**
     * @param start
     * @param end
     * @param numbers
     * @return
     * @Doc 核心查询方法, 多线程或者单线程查询都可使用
     */
    public List<T> coreFind(int start, int end, List<PK> numbers) {
        Map<Integer, List<PK>> mapKeys = new HashMap(dbModShardRange.size());
        for (int i = start; i < end; i++) {
            PK id = numbers.get(i);
            int mod = Math.abs(id.hashCode()) % RANGE_SIZE;
            for (Map.Entry<Integer, Integer> entry : dbModShardRange.entrySet()) {
                if (mod >= entry.getKey() && mod <= entry.getValue()) {
                    if (mapKeys.get(entry.getKey()) != null) {
                        mapKeys.get(entry.getKey()).add(id);
                    } else {
                        mapKeys.put(entry.getKey(), Lists.newArrayList(id));
                    }
                }
            }
        }
        List<T> allRes = Lists.newArrayList();
        for (Map.Entry<Integer, List<PK>> entry : mapKeys.entrySet()) {
            allRes.addAll(shardSqlSessionTemplates.get(entry.getKey()).selectList(sqlMapNamespace + "." + POSTFIX_SELECT, entry.getValue()));
        }
        return allRes;
    }

    /**
     * @param ids
     * @return
     * @Doc 线程池处理逻辑, 并将查询结果汇总
     */
    public List<T> assembleFindResult(final List<PK> ids) {
        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor
        int increment = ids.size() / (CPU_CORE_NUMBER) + 1;
        List<FutureTask<List<T>>> tasks = new ArrayList<>();
        List<T> results = Lists.newArrayList();
        for (int i = 0; i < CPU_CORE_NUMBER; i++) {
            int start = increment * i;
            int end = increment * i + increment;
            if (end > ids.size()) {
                end = ids.size();
                ExtraResult cal = new ExtraResult(ids, start, end);
                FutureTask<List<T>> task = new FutureTask<>(cal);
                tasks.add(task);
                if (!SELECT_EXEC.isShutdown()) {
                    SELECT_EXEC.submit(task);
                }
                break;
            } else {
                ExtraResult cal = new ExtraResult(ids, start, end);
                FutureTask<List<T>> task = new FutureTask<>(cal);
                tasks.add(task);
                if (!SELECT_EXEC.isShutdown()) {
                    SELECT_EXEC.submit(task);
                }
            }
        }
        for (FutureTask<List<T>> futureTask : tasks) {
            try {
                results.addAll(futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


    public static String[] getAllParamaterName(Method method)
            throws NotFoundException {
        Class<?> clazz = method.getDeclaringClass();
        ClassPool pool = ClassPool.getDefault();
        CtClass clz = pool.get(clazz.getName());
        CtClass[] params = new CtClass[method.getParameterTypes().length];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            params[i] = pool.getCtClass(method.getParameterTypes()[i].getName());
        }
        CtMethod cm = clz.getDeclaredMethod(method.getName(), params);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        String[] paramNames = new String[cm.getParameterTypes().length];
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
    }
}
