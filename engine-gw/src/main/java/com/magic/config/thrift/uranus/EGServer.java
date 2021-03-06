/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.magic.config.thrift.uranus;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-05-08")
public class EGServer {

  public interface Iface {

    public com.magic.config.thrift.base.EGResp CallEGService(com.magic.config.thrift.base.EGReq req, com.magic.config.thrift.base.Trace trace) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void CallEGService(com.magic.config.thrift.base.EGReq req, com.magic.config.thrift.base.Trace trace, org.apache.thrift.async.AsyncMethodCallback<com.magic.config.thrift.base.EGResp> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public com.magic.config.thrift.base.EGResp CallEGService(com.magic.config.thrift.base.EGReq req, com.magic.config.thrift.base.Trace trace) throws org.apache.thrift.TException
    {
      send_CallEGService(req, trace);
      return recv_CallEGService();
    }

    public void send_CallEGService(com.magic.config.thrift.base.EGReq req, com.magic.config.thrift.base.Trace trace) throws org.apache.thrift.TException
    {
      CallEGService_args args = new CallEGService_args();
      args.setReq(req);
      args.setTrace(trace);
      sendBase("CallEGService", args);
    }

    public com.magic.config.thrift.base.EGResp recv_CallEGService() throws org.apache.thrift.TException
    {
      CallEGService_result result = new CallEGService_result();
      receiveBase(result, "CallEGService");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "CallEGService failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void CallEGService(com.magic.config.thrift.base.EGReq req, com.magic.config.thrift.base.Trace trace, org.apache.thrift.async.AsyncMethodCallback<com.magic.config.thrift.base.EGResp> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      CallEGService_call method_call = new CallEGService_call(req, trace, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class CallEGService_call extends org.apache.thrift.async.TAsyncMethodCall<com.magic.config.thrift.base.EGResp> {
      private com.magic.config.thrift.base.EGReq req;
      private com.magic.config.thrift.base.Trace trace;
      public CallEGService_call(com.magic.config.thrift.base.EGReq req, com.magic.config.thrift.base.Trace trace, org.apache.thrift.async.AsyncMethodCallback<com.magic.config.thrift.base.EGResp> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.req = req;
        this.trace = trace;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("CallEGService", org.apache.thrift.protocol.TMessageType.CALL, 0));
        CallEGService_args args = new CallEGService_args();
        args.setReq(req);
        args.setTrace(trace);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public com.magic.config.thrift.base.EGResp getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_CallEGService();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, java.util.Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> java.util.Map<String,  org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(java.util.Map<String, org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("CallEGService", new CallEGService());
      return processMap;
    }

    public static class CallEGService<I extends Iface> extends org.apache.thrift.ProcessFunction<I, CallEGService_args> {
      public CallEGService() {
        super("CallEGService");
      }

      public CallEGService_args getEmptyArgsInstance() {
        return new CallEGService_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public CallEGService_result getResult(I iface, CallEGService_args args) throws org.apache.thrift.TException {
        CallEGService_result result = new CallEGService_result();
        result.success = iface.CallEGService(args.req, args.trace);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, java.util.Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> java.util.Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(java.util.Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("CallEGService", new CallEGService());
      return processMap;
    }

    public static class CallEGService<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, CallEGService_args, com.magic.config.thrift.base.EGResp> {
      public CallEGService() {
        super("CallEGService");
      }

      public CallEGService_args getEmptyArgsInstance() {
        return new CallEGService_args();
      }

      public org.apache.thrift.async.AsyncMethodCallback<com.magic.config.thrift.base.EGResp> getResultHandler(final org.apache.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new org.apache.thrift.async.AsyncMethodCallback<com.magic.config.thrift.base.EGResp>() { 
          public void onComplete(com.magic.config.thrift.base.EGResp o) {
            CallEGService_result result = new CallEGService_result();
            result.success = o;
            try {
              fcall.sendResponse(fb, result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
            } catch (org.apache.thrift.transport.TTransportException e) {
              _LOGGER.error("TTransportException writing to internal frame buffer", e);
              fb.close();
            } catch (Exception e) {
              _LOGGER.error("Exception writing to internal frame buffer", e);
              onError(e);
            }
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TSerializable msg;
            CallEGService_result result = new CallEGService_result();
            if (e instanceof org.apache.thrift.transport.TTransportException) {
              _LOGGER.error("TTransportException inside handler", e);
              fb.close();
              return;
            } else if (e instanceof org.apache.thrift.TApplicationException) {
              _LOGGER.error("TApplicationException inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TApplicationException)e;
            } else {
              _LOGGER.error("Exception inside handler", e);
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
            } catch (Exception ex) {
              _LOGGER.error("Exception writing to internal frame buffer", ex);
              fb.close();
            }
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, CallEGService_args args, org.apache.thrift.async.AsyncMethodCallback<com.magic.config.thrift.base.EGResp> resultHandler) throws org.apache.thrift.TException {
        iface.CallEGService(args.req, args.trace,resultHandler);
      }
    }

  }

  public static class CallEGService_args implements org.apache.thrift.TBase<CallEGService_args, CallEGService_args._Fields>, java.io.Serializable, Cloneable, Comparable<CallEGService_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CallEGService_args");

    private static final org.apache.thrift.protocol.TField REQ_FIELD_DESC = new org.apache.thrift.protocol.TField("req", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField TRACE_FIELD_DESC = new org.apache.thrift.protocol.TField("trace", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CallEGService_argsStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CallEGService_argsTupleSchemeFactory();

    public com.magic.config.thrift.base.EGReq req; // required
    public com.magic.config.thrift.base.Trace trace; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      REQ((short)1, "req"),
      TRACE((short)2, "trace");

      private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // REQ
            return REQ;
          case 2: // TRACE
            return TRACE;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.REQ, new org.apache.thrift.meta_data.FieldMetaData("req", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.magic.config.thrift.base.EGReq.class)));
      tmpMap.put(_Fields.TRACE, new org.apache.thrift.meta_data.FieldMetaData("trace", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.magic.config.thrift.base.Trace.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CallEGService_args.class, metaDataMap);
    }

    public CallEGService_args() {
    }

    public CallEGService_args(
      com.magic.config.thrift.base.EGReq req,
      com.magic.config.thrift.base.Trace trace)
    {
      this();
      this.req = req;
      this.trace = trace;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public CallEGService_args(CallEGService_args other) {
      if (other.isSetReq()) {
        this.req = new com.magic.config.thrift.base.EGReq(other.req);
      }
      if (other.isSetTrace()) {
        this.trace = new com.magic.config.thrift.base.Trace(other.trace);
      }
    }

    public CallEGService_args deepCopy() {
      return new CallEGService_args(this);
    }

    @Override
    public void clear() {
      this.req = null;
      this.trace = null;
    }

    public com.magic.config.thrift.base.EGReq getReq() {
      return this.req;
    }

    public CallEGService_args setReq(com.magic.config.thrift.base.EGReq req) {
      this.req = req;
      return this;
    }

    public void unsetReq() {
      this.req = null;
    }

    /** Returns true if field req is set (has been assigned a value) and false otherwise */
    public boolean isSetReq() {
      return this.req != null;
    }

    public void setReqIsSet(boolean value) {
      if (!value) {
        this.req = null;
      }
    }

    public com.magic.config.thrift.base.Trace getTrace() {
      return this.trace;
    }

    public CallEGService_args setTrace(com.magic.config.thrift.base.Trace trace) {
      this.trace = trace;
      return this;
    }

    public void unsetTrace() {
      this.trace = null;
    }

    /** Returns true if field trace is set (has been assigned a value) and false otherwise */
    public boolean isSetTrace() {
      return this.trace != null;
    }

    public void setTraceIsSet(boolean value) {
      if (!value) {
        this.trace = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case REQ:
        if (value == null) {
          unsetReq();
        } else {
          setReq((com.magic.config.thrift.base.EGReq)value);
        }
        break;

      case TRACE:
        if (value == null) {
          unsetTrace();
        } else {
          setTrace((com.magic.config.thrift.base.Trace)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case REQ:
        return getReq();

      case TRACE:
        return getTrace();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case REQ:
        return isSetReq();
      case TRACE:
        return isSetTrace();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof CallEGService_args)
        return this.equals((CallEGService_args)that);
      return false;
    }

    public boolean equals(CallEGService_args that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_req = true && this.isSetReq();
      boolean that_present_req = true && that.isSetReq();
      if (this_present_req || that_present_req) {
        if (!(this_present_req && that_present_req))
          return false;
        if (!this.req.equals(that.req))
          return false;
      }

      boolean this_present_trace = true && this.isSetTrace();
      boolean that_present_trace = true && that.isSetTrace();
      if (this_present_trace || that_present_trace) {
        if (!(this_present_trace && that_present_trace))
          return false;
        if (!this.trace.equals(that.trace))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetReq()) ? 131071 : 524287);
      if (isSetReq())
        hashCode = hashCode * 8191 + req.hashCode();

      hashCode = hashCode * 8191 + ((isSetTrace()) ? 131071 : 524287);
      if (isSetTrace())
        hashCode = hashCode * 8191 + trace.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(CallEGService_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetReq()).compareTo(other.isSetReq());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetReq()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.req, other.req);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetTrace()).compareTo(other.isSetTrace());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetTrace()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.trace, other.trace);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("CallEGService_args(");
      boolean first = true;

      sb.append("req:");
      if (this.req == null) {
        sb.append("null");
      } else {
        sb.append(this.req);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("trace:");
      if (this.trace == null) {
        sb.append("null");
      } else {
        sb.append(this.trace);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (req != null) {
        req.validate();
      }
      if (trace != null) {
        trace.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class CallEGService_argsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public CallEGService_argsStandardScheme getScheme() {
        return new CallEGService_argsStandardScheme();
      }
    }

    private static class CallEGService_argsStandardScheme extends org.apache.thrift.scheme.StandardScheme<CallEGService_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, CallEGService_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // REQ
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.req = new com.magic.config.thrift.base.EGReq();
                struct.req.read(iprot);
                struct.setReqIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // TRACE
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.trace = new com.magic.config.thrift.base.Trace();
                struct.trace.read(iprot);
                struct.setTraceIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, CallEGService_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.req != null) {
          oprot.writeFieldBegin(REQ_FIELD_DESC);
          struct.req.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.trace != null) {
          oprot.writeFieldBegin(TRACE_FIELD_DESC);
          struct.trace.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class CallEGService_argsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public CallEGService_argsTupleScheme getScheme() {
        return new CallEGService_argsTupleScheme();
      }
    }

    private static class CallEGService_argsTupleScheme extends org.apache.thrift.scheme.TupleScheme<CallEGService_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, CallEGService_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetReq()) {
          optionals.set(0);
        }
        if (struct.isSetTrace()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetReq()) {
          struct.req.write(oprot);
        }
        if (struct.isSetTrace()) {
          struct.trace.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, CallEGService_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.req = new com.magic.config.thrift.base.EGReq();
          struct.req.read(iprot);
          struct.setReqIsSet(true);
        }
        if (incoming.get(1)) {
          struct.trace = new com.magic.config.thrift.base.Trace();
          struct.trace.read(iprot);
          struct.setTraceIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

  public static class CallEGService_result implements org.apache.thrift.TBase<CallEGService_result, CallEGService_result._Fields>, java.io.Serializable, Cloneable, Comparable<CallEGService_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CallEGService_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);

    private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CallEGService_resultStandardSchemeFactory();
    private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CallEGService_resultTupleSchemeFactory();

    public com.magic.config.thrift.base.EGResp success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.magic.config.thrift.base.EGResp.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CallEGService_result.class, metaDataMap);
    }

    public CallEGService_result() {
    }

    public CallEGService_result(
      com.magic.config.thrift.base.EGResp success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public CallEGService_result(CallEGService_result other) {
      if (other.isSetSuccess()) {
        this.success = new com.magic.config.thrift.base.EGResp(other.success);
      }
    }

    public CallEGService_result deepCopy() {
      return new CallEGService_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public com.magic.config.thrift.base.EGResp getSuccess() {
      return this.success;
    }

    public CallEGService_result setSuccess(com.magic.config.thrift.base.EGResp success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((com.magic.config.thrift.base.EGResp)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof CallEGService_result)
        return this.equals((CallEGService_result)that);
      return false;
    }

    public boolean equals(CallEGService_result that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetSuccess()) ? 131071 : 524287);
      if (isSetSuccess())
        hashCode = hashCode * 8191 + success.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(CallEGService_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      scheme(oprot).write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("CallEGService_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class CallEGService_resultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public CallEGService_resultStandardScheme getScheme() {
        return new CallEGService_resultStandardScheme();
      }
    }

    private static class CallEGService_resultStandardScheme extends org.apache.thrift.scheme.StandardScheme<CallEGService_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, CallEGService_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new com.magic.config.thrift.base.EGResp();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, CallEGService_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class CallEGService_resultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
      public CallEGService_resultTupleScheme getScheme() {
        return new CallEGService_resultTupleScheme();
      }
    }

    private static class CallEGService_resultTupleScheme extends org.apache.thrift.scheme.TupleScheme<CallEGService_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, CallEGService_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, CallEGService_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new com.magic.config.thrift.base.EGResp();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

    private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
      return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

}
