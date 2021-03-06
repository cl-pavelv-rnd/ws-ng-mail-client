/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.ws.ng.mail_client_avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class ExternalEmailMessageEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 2083753522927626172L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ExternalEmailMessageEvent\",\"namespace\":\"com.ws.ng.mail_client_avro\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"subject\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"sentDate\",\"type\":\"long\",\"logicalType\":\"timestamp-millis\"},{\"name\":\"receivedDate\",\"type\":\"long\",\"logicalType\":\"timestamp-millis\"},{\"name\":\"from\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"EmailAddress\",\"fields\":[{\"name\":\"username\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"address\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"displayName\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null}]}],\"default\":null},{\"name\":\"to\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"EmailAddress\"}],\"default\":null},{\"name\":\"cc\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"EmailAddress\"}],\"default\":null},{\"name\":\"bcc\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"EmailAddress\"}],\"default\":null}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<ExternalEmailMessageEvent> ENCODER =
      new BinaryMessageEncoder<ExternalEmailMessageEvent>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<ExternalEmailMessageEvent> DECODER =
      new BinaryMessageDecoder<ExternalEmailMessageEvent>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<ExternalEmailMessageEvent> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<ExternalEmailMessageEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<ExternalEmailMessageEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<ExternalEmailMessageEvent>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this ExternalEmailMessageEvent to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a ExternalEmailMessageEvent from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a ExternalEmailMessageEvent instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static ExternalEmailMessageEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String id;
   private java.lang.String subject;
   private long sentDate;
   private long receivedDate;
   private com.ws.ng.mail_client_avro.EmailAddress from;
   private java.util.List<com.ws.ng.mail_client_avro.EmailAddress> to;
   private java.util.List<com.ws.ng.mail_client_avro.EmailAddress> cc;
   private java.util.List<com.ws.ng.mail_client_avro.EmailAddress> bcc;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public ExternalEmailMessageEvent() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param subject The new value for subject
   * @param sentDate The new value for sentDate
   * @param receivedDate The new value for receivedDate
   * @param from The new value for from
   * @param to The new value for to
   * @param cc The new value for cc
   * @param bcc The new value for bcc
   */
  public ExternalEmailMessageEvent(java.lang.String id, java.lang.String subject, java.lang.Long sentDate, java.lang.Long receivedDate, com.ws.ng.mail_client_avro.EmailAddress from, java.util.List<com.ws.ng.mail_client_avro.EmailAddress> to, java.util.List<com.ws.ng.mail_client_avro.EmailAddress> cc, java.util.List<com.ws.ng.mail_client_avro.EmailAddress> bcc) {
    this.id = id;
    this.subject = subject;
    this.sentDate = sentDate;
    this.receivedDate = receivedDate;
    this.from = from;
    this.to = to;
    this.cc = cc;
    this.bcc = bcc;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return subject;
    case 2: return sentDate;
    case 3: return receivedDate;
    case 4: return from;
    case 5: return to;
    case 6: return cc;
    case 7: return bcc;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = value$ != null ? value$.toString() : null; break;
    case 1: subject = value$ != null ? value$.toString() : null; break;
    case 2: sentDate = (java.lang.Long)value$; break;
    case 3: receivedDate = (java.lang.Long)value$; break;
    case 4: from = (com.ws.ng.mail_client_avro.EmailAddress)value$; break;
    case 5: to = (java.util.List<com.ws.ng.mail_client_avro.EmailAddress>)value$; break;
    case 6: cc = (java.util.List<com.ws.ng.mail_client_avro.EmailAddress>)value$; break;
    case 7: bcc = (java.util.List<com.ws.ng.mail_client_avro.EmailAddress>)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'subject' field.
   * @return The value of the 'subject' field.
   */
  public java.lang.String getSubject() {
    return subject;
  }


  /**
   * Sets the value of the 'subject' field.
   * @param value the value to set.
   */
  public void setSubject(java.lang.String value) {
    this.subject = value;
  }

  /**
   * Gets the value of the 'sentDate' field.
   * @return The value of the 'sentDate' field.
   */
  public long getSentDate() {
    return sentDate;
  }


  /**
   * Sets the value of the 'sentDate' field.
   * @param value the value to set.
   */
  public void setSentDate(long value) {
    this.sentDate = value;
  }

  /**
   * Gets the value of the 'receivedDate' field.
   * @return The value of the 'receivedDate' field.
   */
  public long getReceivedDate() {
    return receivedDate;
  }


  /**
   * Sets the value of the 'receivedDate' field.
   * @param value the value to set.
   */
  public void setReceivedDate(long value) {
    this.receivedDate = value;
  }

  /**
   * Gets the value of the 'from' field.
   * @return The value of the 'from' field.
   */
  public com.ws.ng.mail_client_avro.EmailAddress getFrom() {
    return from;
  }


  /**
   * Sets the value of the 'from' field.
   * @param value the value to set.
   */
  public void setFrom(com.ws.ng.mail_client_avro.EmailAddress value) {
    this.from = value;
  }

  /**
   * Gets the value of the 'to' field.
   * @return The value of the 'to' field.
   */
  public java.util.List<com.ws.ng.mail_client_avro.EmailAddress> getTo() {
    return to;
  }


  /**
   * Sets the value of the 'to' field.
   * @param value the value to set.
   */
  public void setTo(java.util.List<com.ws.ng.mail_client_avro.EmailAddress> value) {
    this.to = value;
  }

  /**
   * Gets the value of the 'cc' field.
   * @return The value of the 'cc' field.
   */
  public java.util.List<com.ws.ng.mail_client_avro.EmailAddress> getCc() {
    return cc;
  }


  /**
   * Sets the value of the 'cc' field.
   * @param value the value to set.
   */
  public void setCc(java.util.List<com.ws.ng.mail_client_avro.EmailAddress> value) {
    this.cc = value;
  }

  /**
   * Gets the value of the 'bcc' field.
   * @return The value of the 'bcc' field.
   */
  public java.util.List<com.ws.ng.mail_client_avro.EmailAddress> getBcc() {
    return bcc;
  }


  /**
   * Sets the value of the 'bcc' field.
   * @param value the value to set.
   */
  public void setBcc(java.util.List<com.ws.ng.mail_client_avro.EmailAddress> value) {
    this.bcc = value;
  }

  /**
   * Creates a new ExternalEmailMessageEvent RecordBuilder.
   * @return A new ExternalEmailMessageEvent RecordBuilder
   */
  public static com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder newBuilder() {
    return new com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder();
  }

  /**
   * Creates a new ExternalEmailMessageEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new ExternalEmailMessageEvent RecordBuilder
   */
  public static com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder newBuilder(com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder other) {
    if (other == null) {
      return new com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder();
    } else {
      return new com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder(other);
    }
  }

  /**
   * Creates a new ExternalEmailMessageEvent RecordBuilder by copying an existing ExternalEmailMessageEvent instance.
   * @param other The existing instance to copy.
   * @return A new ExternalEmailMessageEvent RecordBuilder
   */
  public static com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder newBuilder(com.ws.ng.mail_client_avro.ExternalEmailMessageEvent other) {
    if (other == null) {
      return new com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder();
    } else {
      return new com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder(other);
    }
  }

  /**
   * RecordBuilder for ExternalEmailMessageEvent instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ExternalEmailMessageEvent>
    implements org.apache.avro.data.RecordBuilder<ExternalEmailMessageEvent> {

    private java.lang.String id;
    private java.lang.String subject;
    private long sentDate;
    private long receivedDate;
    private com.ws.ng.mail_client_avro.EmailAddress from;
    private com.ws.ng.mail_client_avro.EmailAddress.Builder fromBuilder;
    private java.util.List<com.ws.ng.mail_client_avro.EmailAddress> to;
    private java.util.List<com.ws.ng.mail_client_avro.EmailAddress> cc;
    private java.util.List<com.ws.ng.mail_client_avro.EmailAddress> bcc;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.subject)) {
        this.subject = data().deepCopy(fields()[1].schema(), other.subject);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.sentDate)) {
        this.sentDate = data().deepCopy(fields()[2].schema(), other.sentDate);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.receivedDate)) {
        this.receivedDate = data().deepCopy(fields()[3].schema(), other.receivedDate);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.from)) {
        this.from = data().deepCopy(fields()[4].schema(), other.from);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (other.hasFromBuilder()) {
        this.fromBuilder = com.ws.ng.mail_client_avro.EmailAddress.newBuilder(other.getFromBuilder());
      }
      if (isValidValue(fields()[5], other.to)) {
        this.to = data().deepCopy(fields()[5].schema(), other.to);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.cc)) {
        this.cc = data().deepCopy(fields()[6].schema(), other.cc);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.bcc)) {
        this.bcc = data().deepCopy(fields()[7].schema(), other.bcc);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
    }

    /**
     * Creates a Builder by copying an existing ExternalEmailMessageEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ws.ng.mail_client_avro.ExternalEmailMessageEvent other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.subject)) {
        this.subject = data().deepCopy(fields()[1].schema(), other.subject);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.sentDate)) {
        this.sentDate = data().deepCopy(fields()[2].schema(), other.sentDate);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.receivedDate)) {
        this.receivedDate = data().deepCopy(fields()[3].schema(), other.receivedDate);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.from)) {
        this.from = data().deepCopy(fields()[4].schema(), other.from);
        fieldSetFlags()[4] = true;
      }
      this.fromBuilder = null;
      if (isValidValue(fields()[5], other.to)) {
        this.to = data().deepCopy(fields()[5].schema(), other.to);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.cc)) {
        this.cc = data().deepCopy(fields()[6].schema(), other.cc);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.bcc)) {
        this.bcc = data().deepCopy(fields()[7].schema(), other.bcc);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'subject' field.
      * @return The value.
      */
    public java.lang.String getSubject() {
      return subject;
    }


    /**
      * Sets the value of the 'subject' field.
      * @param value The value of 'subject'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setSubject(java.lang.String value) {
      validate(fields()[1], value);
      this.subject = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'subject' field has been set.
      * @return True if the 'subject' field has been set, false otherwise.
      */
    public boolean hasSubject() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'subject' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearSubject() {
      subject = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'sentDate' field.
      * @return The value.
      */
    public long getSentDate() {
      return sentDate;
    }


    /**
      * Sets the value of the 'sentDate' field.
      * @param value The value of 'sentDate'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setSentDate(long value) {
      validate(fields()[2], value);
      this.sentDate = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'sentDate' field has been set.
      * @return True if the 'sentDate' field has been set, false otherwise.
      */
    public boolean hasSentDate() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'sentDate' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearSentDate() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'receivedDate' field.
      * @return The value.
      */
    public long getReceivedDate() {
      return receivedDate;
    }


    /**
      * Sets the value of the 'receivedDate' field.
      * @param value The value of 'receivedDate'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setReceivedDate(long value) {
      validate(fields()[3], value);
      this.receivedDate = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'receivedDate' field has been set.
      * @return True if the 'receivedDate' field has been set, false otherwise.
      */
    public boolean hasReceivedDate() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'receivedDate' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearReceivedDate() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'from' field.
      * @return The value.
      */
    public com.ws.ng.mail_client_avro.EmailAddress getFrom() {
      return from;
    }


    /**
      * Sets the value of the 'from' field.
      * @param value The value of 'from'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setFrom(com.ws.ng.mail_client_avro.EmailAddress value) {
      validate(fields()[4], value);
      this.fromBuilder = null;
      this.from = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'from' field has been set.
      * @return True if the 'from' field has been set, false otherwise.
      */
    public boolean hasFrom() {
      return fieldSetFlags()[4];
    }

    /**
     * Gets the Builder instance for the 'from' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.ws.ng.mail_client_avro.EmailAddress.Builder getFromBuilder() {
      if (fromBuilder == null) {
        if (hasFrom()) {
          setFromBuilder(com.ws.ng.mail_client_avro.EmailAddress.newBuilder(from));
        } else {
          setFromBuilder(com.ws.ng.mail_client_avro.EmailAddress.newBuilder());
        }
      }
      return fromBuilder;
    }

    /**
     * Sets the Builder instance for the 'from' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setFromBuilder(com.ws.ng.mail_client_avro.EmailAddress.Builder value) {
      clearFrom();
      fromBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'from' field has an active Builder instance
     * @return True if the 'from' field has an active Builder instance
     */
    public boolean hasFromBuilder() {
      return fromBuilder != null;
    }

    /**
      * Clears the value of the 'from' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearFrom() {
      from = null;
      fromBuilder = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'to' field.
      * @return The value.
      */
    public java.util.List<com.ws.ng.mail_client_avro.EmailAddress> getTo() {
      return to;
    }


    /**
      * Sets the value of the 'to' field.
      * @param value The value of 'to'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setTo(java.util.List<com.ws.ng.mail_client_avro.EmailAddress> value) {
      validate(fields()[5], value);
      this.to = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'to' field has been set.
      * @return True if the 'to' field has been set, false otherwise.
      */
    public boolean hasTo() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'to' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearTo() {
      to = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'cc' field.
      * @return The value.
      */
    public java.util.List<com.ws.ng.mail_client_avro.EmailAddress> getCc() {
      return cc;
    }


    /**
      * Sets the value of the 'cc' field.
      * @param value The value of 'cc'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setCc(java.util.List<com.ws.ng.mail_client_avro.EmailAddress> value) {
      validate(fields()[6], value);
      this.cc = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'cc' field has been set.
      * @return True if the 'cc' field has been set, false otherwise.
      */
    public boolean hasCc() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'cc' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearCc() {
      cc = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'bcc' field.
      * @return The value.
      */
    public java.util.List<com.ws.ng.mail_client_avro.EmailAddress> getBcc() {
      return bcc;
    }


    /**
      * Sets the value of the 'bcc' field.
      * @param value The value of 'bcc'.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder setBcc(java.util.List<com.ws.ng.mail_client_avro.EmailAddress> value) {
      validate(fields()[7], value);
      this.bcc = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'bcc' field has been set.
      * @return True if the 'bcc' field has been set, false otherwise.
      */
    public boolean hasBcc() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'bcc' field.
      * @return This builder.
      */
    public com.ws.ng.mail_client_avro.ExternalEmailMessageEvent.Builder clearBcc() {
      bcc = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ExternalEmailMessageEvent build() {
      try {
        ExternalEmailMessageEvent record = new ExternalEmailMessageEvent();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.subject = fieldSetFlags()[1] ? this.subject : (java.lang.String) defaultValue(fields()[1]);
        record.sentDate = fieldSetFlags()[2] ? this.sentDate : (java.lang.Long) defaultValue(fields()[2]);
        record.receivedDate = fieldSetFlags()[3] ? this.receivedDate : (java.lang.Long) defaultValue(fields()[3]);
        if (fromBuilder != null) {
          try {
            record.from = this.fromBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("from"));
            throw e;
          }
        } else {
          record.from = fieldSetFlags()[4] ? this.from : (com.ws.ng.mail_client_avro.EmailAddress) defaultValue(fields()[4]);
        }
        record.to = fieldSetFlags()[5] ? this.to : (java.util.List<com.ws.ng.mail_client_avro.EmailAddress>) defaultValue(fields()[5]);
        record.cc = fieldSetFlags()[6] ? this.cc : (java.util.List<com.ws.ng.mail_client_avro.EmailAddress>) defaultValue(fields()[6]);
        record.bcc = fieldSetFlags()[7] ? this.bcc : (java.util.List<com.ws.ng.mail_client_avro.EmailAddress>) defaultValue(fields()[7]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<ExternalEmailMessageEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<ExternalEmailMessageEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<ExternalEmailMessageEvent>
    READER$ = (org.apache.avro.io.DatumReader<ExternalEmailMessageEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.id);

    if (this.subject == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      out.writeString(this.subject);
    }

    out.writeLong(this.sentDate);

    out.writeLong(this.receivedDate);

    if (this.from == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      this.from.customEncode(out);
    }

    if (this.to == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      long size0 = this.to.size();
      out.writeArrayStart();
      out.setItemCount(size0);
      long actualSize0 = 0;
      for (com.ws.ng.mail_client_avro.EmailAddress e0: this.to) {
        actualSize0++;
        out.startItem();
        e0.customEncode(out);
      }
      out.writeArrayEnd();
      if (actualSize0 != size0)
        throw new java.util.ConcurrentModificationException("Array-size written was " + size0 + ", but element count was " + actualSize0 + ".");
    }

    if (this.cc == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      long size1 = this.cc.size();
      out.writeArrayStart();
      out.setItemCount(size1);
      long actualSize1 = 0;
      for (com.ws.ng.mail_client_avro.EmailAddress e1: this.cc) {
        actualSize1++;
        out.startItem();
        e1.customEncode(out);
      }
      out.writeArrayEnd();
      if (actualSize1 != size1)
        throw new java.util.ConcurrentModificationException("Array-size written was " + size1 + ", but element count was " + actualSize1 + ".");
    }

    if (this.bcc == null) {
      out.writeIndex(0);
      out.writeNull();
    } else {
      out.writeIndex(1);
      long size2 = this.bcc.size();
      out.writeArrayStart();
      out.setItemCount(size2);
      long actualSize2 = 0;
      for (com.ws.ng.mail_client_avro.EmailAddress e2: this.bcc) {
        actualSize2++;
        out.startItem();
        e2.customEncode(out);
      }
      out.writeArrayEnd();
      if (actualSize2 != size2)
        throw new java.util.ConcurrentModificationException("Array-size written was " + size2 + ", but element count was " + actualSize2 + ".");
    }

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.id = in.readString();

      if (in.readIndex() != 1) {
        in.readNull();
        this.subject = null;
      } else {
        this.subject = in.readString();
      }

      this.sentDate = in.readLong();

      this.receivedDate = in.readLong();

      if (in.readIndex() != 1) {
        in.readNull();
        this.from = null;
      } else {
        if (this.from == null) {
          this.from = new com.ws.ng.mail_client_avro.EmailAddress();
        }
        this.from.customDecode(in);
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.to = null;
      } else {
        long size0 = in.readArrayStart();
        java.util.List<com.ws.ng.mail_client_avro.EmailAddress> a0 = this.to;
        if (a0 == null) {
          a0 = new SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>((int)size0, SCHEMA$.getField("to").schema().getTypes().get(1));
          this.to = a0;
        } else a0.clear();
        SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>)a0 : null);
        for ( ; 0 < size0; size0 = in.arrayNext()) {
          for ( ; size0 != 0; size0--) {
            com.ws.ng.mail_client_avro.EmailAddress e0 = (ga0 != null ? ga0.peek() : null);
            if (e0 == null) {
              e0 = new com.ws.ng.mail_client_avro.EmailAddress();
            }
            e0.customDecode(in);
            a0.add(e0);
          }
        }
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.cc = null;
      } else {
        long size1 = in.readArrayStart();
        java.util.List<com.ws.ng.mail_client_avro.EmailAddress> a1 = this.cc;
        if (a1 == null) {
          a1 = new SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>((int)size1, SCHEMA$.getField("cc").schema().getTypes().get(1));
          this.cc = a1;
        } else a1.clear();
        SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress> ga1 = (a1 instanceof SpecificData.Array ? (SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>)a1 : null);
        for ( ; 0 < size1; size1 = in.arrayNext()) {
          for ( ; size1 != 0; size1--) {
            com.ws.ng.mail_client_avro.EmailAddress e1 = (ga1 != null ? ga1.peek() : null);
            if (e1 == null) {
              e1 = new com.ws.ng.mail_client_avro.EmailAddress();
            }
            e1.customDecode(in);
            a1.add(e1);
          }
        }
      }

      if (in.readIndex() != 1) {
        in.readNull();
        this.bcc = null;
      } else {
        long size2 = in.readArrayStart();
        java.util.List<com.ws.ng.mail_client_avro.EmailAddress> a2 = this.bcc;
        if (a2 == null) {
          a2 = new SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>((int)size2, SCHEMA$.getField("bcc").schema().getTypes().get(1));
          this.bcc = a2;
        } else a2.clear();
        SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress> ga2 = (a2 instanceof SpecificData.Array ? (SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>)a2 : null);
        for ( ; 0 < size2; size2 = in.arrayNext()) {
          for ( ; size2 != 0; size2--) {
            com.ws.ng.mail_client_avro.EmailAddress e2 = (ga2 != null ? ga2.peek() : null);
            if (e2 == null) {
              e2 = new com.ws.ng.mail_client_avro.EmailAddress();
            }
            e2.customDecode(in);
            a2.add(e2);
          }
        }
      }

    } else {
      for (int i = 0; i < 8; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.id = in.readString();
          break;

        case 1:
          if (in.readIndex() != 1) {
            in.readNull();
            this.subject = null;
          } else {
            this.subject = in.readString();
          }
          break;

        case 2:
          this.sentDate = in.readLong();
          break;

        case 3:
          this.receivedDate = in.readLong();
          break;

        case 4:
          if (in.readIndex() != 1) {
            in.readNull();
            this.from = null;
          } else {
            if (this.from == null) {
              this.from = new com.ws.ng.mail_client_avro.EmailAddress();
            }
            this.from.customDecode(in);
          }
          break;

        case 5:
          if (in.readIndex() != 1) {
            in.readNull();
            this.to = null;
          } else {
            long size0 = in.readArrayStart();
            java.util.List<com.ws.ng.mail_client_avro.EmailAddress> a0 = this.to;
            if (a0 == null) {
              a0 = new SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>((int)size0, SCHEMA$.getField("to").schema().getTypes().get(1));
              this.to = a0;
            } else a0.clear();
            SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>)a0 : null);
            for ( ; 0 < size0; size0 = in.arrayNext()) {
              for ( ; size0 != 0; size0--) {
                com.ws.ng.mail_client_avro.EmailAddress e0 = (ga0 != null ? ga0.peek() : null);
                if (e0 == null) {
                  e0 = new com.ws.ng.mail_client_avro.EmailAddress();
                }
                e0.customDecode(in);
                a0.add(e0);
              }
            }
          }
          break;

        case 6:
          if (in.readIndex() != 1) {
            in.readNull();
            this.cc = null;
          } else {
            long size1 = in.readArrayStart();
            java.util.List<com.ws.ng.mail_client_avro.EmailAddress> a1 = this.cc;
            if (a1 == null) {
              a1 = new SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>((int)size1, SCHEMA$.getField("cc").schema().getTypes().get(1));
              this.cc = a1;
            } else a1.clear();
            SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress> ga1 = (a1 instanceof SpecificData.Array ? (SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>)a1 : null);
            for ( ; 0 < size1; size1 = in.arrayNext()) {
              for ( ; size1 != 0; size1--) {
                com.ws.ng.mail_client_avro.EmailAddress e1 = (ga1 != null ? ga1.peek() : null);
                if (e1 == null) {
                  e1 = new com.ws.ng.mail_client_avro.EmailAddress();
                }
                e1.customDecode(in);
                a1.add(e1);
              }
            }
          }
          break;

        case 7:
          if (in.readIndex() != 1) {
            in.readNull();
            this.bcc = null;
          } else {
            long size2 = in.readArrayStart();
            java.util.List<com.ws.ng.mail_client_avro.EmailAddress> a2 = this.bcc;
            if (a2 == null) {
              a2 = new SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>((int)size2, SCHEMA$.getField("bcc").schema().getTypes().get(1));
              this.bcc = a2;
            } else a2.clear();
            SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress> ga2 = (a2 instanceof SpecificData.Array ? (SpecificData.Array<com.ws.ng.mail_client_avro.EmailAddress>)a2 : null);
            for ( ; 0 < size2; size2 = in.arrayNext()) {
              for ( ; size2 != 0; size2--) {
                com.ws.ng.mail_client_avro.EmailAddress e2 = (ga2 != null ? ga2.peek() : null);
                if (e2 == null) {
                  e2 = new com.ws.ng.mail_client_avro.EmailAddress();
                }
                e2.customDecode(in);
                a2.add(e2);
              }
            }
          }
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










