package org.serialize.serializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.serialize.pojo.BaseTypes;

public class FileSerializeTest {

    @BeforeAll
    public static void setup() {
        System.setProperty("java.io.tmpdir", "src/test/resources/");
        String tempDir = System.getProperty("java.io.tmpdir");
        if (!new java.io.File(tempDir).exists()) {
            new java.io.File(tempDir).mkdir();
        }
    }

    @AfterAll
    public static void cleanup() {
        String tempDir = System.getProperty("java.io.tmpdir");
        java.io.File file = new java.io.File(tempDir);
        if (file.exists()) {
            java.io.File[] files = file.listFiles();
            for (java.io.File f : files) {
                f.delete();
            }
        }

        System.clearProperty("java.io.tmpdir");
    }

    @Test
    public void testSerializeBaseTypes() {
        BaseTypes baseTypes = new BaseTypes();
        baseTypes.setByteValue((byte) 1);
        baseTypes.setShortValue((short) 2);
        baseTypes.setIntValue(3);
        baseTypes.setLongValue(4L);
        baseTypes.setFloatValue(5.0f);
        baseTypes.setDoubleValue(6.0);
        baseTypes.setCharValue('7');
        baseTypes.setBooleanValue(true);

        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            String fileName = tempDir + "baseTypes.ser";
            FileSerialize.serialize(baseTypes, fileName);
            BaseTypes baseTypesDeserialized = FileSerialize.deserialize(fileName);
            assertEquals(baseTypes, baseTypesDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}