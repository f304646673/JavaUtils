package org.serialize.pojo;

import lombok.Data;

@Data
public class BaseTypes implements java.io.Serializable{
    private byte byteValue;
    private short shortValue;
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private char charValue;
    private boolean booleanValue;
}