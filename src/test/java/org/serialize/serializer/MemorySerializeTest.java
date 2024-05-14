package org.serialize.serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.serialize.pojo.BaseTypes;

import static org.junit.jupiter.api.Assertions.*;

public class MemorySerializeTest {

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
            byte[] data = MemorySerialize.serialize(baseTypes);
            BaseTypes baseTypesDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypes, baseTypesDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeNull() {
        try {
            byte[] data = MemorySerialize.serialize(null);
            Object object = MemorySerialize.deserialize(data);
            assertNull(object);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeEmpty() {
        BaseTypes baseTypes = new BaseTypes();
        try {
            byte[] data = MemorySerialize.serialize(baseTypes);
            BaseTypes baseTypesDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypes, baseTypesDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesArray() {
        BaseTypes[] baseTypesArray = new BaseTypes[3];
        for (int i = 0; i < baseTypesArray.length; i++) {
            BaseTypes baseTypes = new BaseTypes();
            baseTypes.setByteValue((byte) (i + 1));
            baseTypes.setShortValue((short) (i + 2));
            baseTypes.setIntValue(i + 3);
            baseTypes.setLongValue(i + 4L);
            baseTypes.setFloatValue(i + 5.0f);
            baseTypes.setDoubleValue(i + 6.0);
            baseTypes.setCharValue((char) (i + 7));
            baseTypes.setBooleanValue(i % 2 == 0);
            baseTypesArray[i] = baseTypes;
        }

        try {
            byte[] data = MemorySerialize.serialize(baseTypesArray);
            BaseTypes[] baseTypesArrayDeserialized = MemorySerialize.deserialize(data);
            assertArrayEquals(baseTypesArray, baseTypesArrayDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesArrayNull() {
        BaseTypes[] baseTypesArray = null;
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArray);
            BaseTypes[] baseTypesArrayDeserialized = MemorySerialize.deserialize(data);
            assertArrayEquals(baseTypesArray, baseTypesArrayDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    @Test
    public void testSerializeBaseTypesArrayEmpty() {
        BaseTypes[] baseTypesArray = new BaseTypes[0];
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArray);
            BaseTypes[] baseTypesArrayDeserialized = MemorySerialize.deserialize(data);
            assertArrayEquals(baseTypesArray, baseTypesArrayDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesArrayNullElement() {
        BaseTypes[] baseTypesArray = new BaseTypes[3];
        baseTypesArray[0] = new BaseTypes();
        baseTypesArray[1] = null;
        baseTypesArray[2] = new BaseTypes();
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArray);
            BaseTypes[] baseTypesArrayDeserialized = MemorySerialize.deserialize(data);
            assertArrayEquals(baseTypesArray, baseTypesArrayDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesArrayEmptyElement() {
        BaseTypes[] baseTypesArray = new BaseTypes[3];
        baseTypesArray[0] = new BaseTypes();
        baseTypesArray[1] = new BaseTypes();
        baseTypesArray[2] = new BaseTypes();
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArray);
            BaseTypes[] baseTypesArrayDeserialized = MemorySerialize.deserialize(data);
            assertArrayEquals(baseTypesArray, baseTypesArrayDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithArrayList() {
        List<BaseTypes> baseTypesArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BaseTypes baseTypes = new BaseTypes();
            baseTypes.setByteValue((byte) (i + 1));
            baseTypes.setShortValue((short) (i + 2));
            baseTypes.setIntValue(i + 3);
            baseTypes.setLongValue(i + 4L);
            baseTypes.setFloatValue(i + 5.0f);
            baseTypes.setDoubleValue(i + 6.0);
            baseTypes.setCharValue((char) (i + 7));
            baseTypes.setBooleanValue(i % 2 == 0);
            baseTypesArrayList.add(baseTypes);
        }

        try {
            byte[] data = MemorySerialize.serialize(baseTypesArrayList);
            List<BaseTypes> baseTypesArrayListDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesArrayList, baseTypesArrayListDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithArrayListNull() {
        List<BaseTypes> baseTypesArrayList = null;
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArrayList);
            List<BaseTypes> baseTypesArrayListDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesArrayList, baseTypesArrayListDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithArrayListEmpty() {
        List<BaseTypes> baseTypesArrayList = new ArrayList<>();
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArrayList);
            List<BaseTypes> baseTypesArrayListDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesArrayList, baseTypesArrayListDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithArrayListNullElement() {
        List<BaseTypes> baseTypesArrayList = new ArrayList<>();
        baseTypesArrayList.add(new BaseTypes());
        baseTypesArrayList.add(null);
        baseTypesArrayList.add(new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArrayList);
            List<BaseTypes> baseTypesArrayListDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesArrayList, baseTypesArrayListDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithArrayListEmptyElement() {

        List<BaseTypes> baseTypesArrayList = new ArrayList<>();
        baseTypesArrayList.add(new BaseTypes());
        baseTypesArrayList.add(new BaseTypes());
        baseTypesArrayList.add(new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesArrayList);
            List<BaseTypes> baseTypesArrayListDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesArrayList, baseTypesArrayListDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithSet() {
        Set<BaseTypes> baseTypesSet = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            BaseTypes baseTypes = new BaseTypes();
            baseTypes.setByteValue((byte) (i + 1));
            baseTypes.setShortValue((short) (i + 2));
            baseTypes.setIntValue(i + 3);
            baseTypes.setLongValue(i + 4L);
            baseTypes.setFloatValue(i + 5.0f);
            baseTypes.setDoubleValue(i + 6.0);
            baseTypes.setCharValue((char) (i + 7));
            baseTypes.setBooleanValue(i % 2 == 0);
            baseTypesSet.add(baseTypes);
        }
        
        try {
            byte[] data = MemorySerialize.serialize(baseTypesSet);
            Set<BaseTypes> baseTypesSetDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesSet, baseTypesSetDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithSetNull() {
        Set<BaseTypes> baseTypesSet = null;
        try {
            byte[] data = MemorySerialize.serialize(baseTypesSet);
            Set<BaseTypes> baseTypesSetDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesSet, baseTypesSetDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithSetEmpty() {
        Set<BaseTypes> baseTypesSet = new HashSet<>();
        try {
            byte[] data = MemorySerialize.serialize(baseTypesSet);
            Set<BaseTypes> baseTypesSetDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesSet, baseTypesSetDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithSetNullElement() {
        Set<BaseTypes> baseTypesSet = new HashSet<>();
        baseTypesSet.add(new BaseTypes());
        baseTypesSet.add(null);
        baseTypesSet.add(new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesSet);
            Set<BaseTypes> baseTypesSetDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesSet, baseTypesSetDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithSetEmptyElement() {
        Set<BaseTypes> baseTypesSet = new HashSet<>();
        baseTypesSet.add(new BaseTypes());
        baseTypesSet.add(new BaseTypes());
        baseTypesSet.add(new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesSet);
            Set<BaseTypes> baseTypesSetDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesSet, baseTypesSetDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMap() {
        Map<String, BaseTypes> baseTypesMap = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            BaseTypes baseTypes = new BaseTypes();
            baseTypes.setByteValue((byte) (i + 1));
            baseTypes.setShortValue((short) (i + 2));
            baseTypes.setIntValue(i + 3);
            baseTypes.setLongValue(i + 4L);
            baseTypes.setFloatValue(i + 5.0f);
            baseTypes.setDoubleValue(i + 6.0);
            baseTypes.setCharValue((char) (i + 7));
            baseTypes.setBooleanValue(i % 2 == 0);
            baseTypesMap.put(String.valueOf(i), baseTypes);
        }

        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMapNull() {
        Map<String, BaseTypes> baseTypesMap = null;
        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMapEmpty() {
        Map<String, BaseTypes> baseTypesMap = new HashMap<>();
        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMapNullElement() {
        Map<String, BaseTypes> baseTypesMap = new HashMap<>();
        baseTypesMap.put("0", new BaseTypes());
        baseTypesMap.put("1", null);
        baseTypesMap.put("2", new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMapEmptyElement() {
        Map<String, BaseTypes> baseTypesMap = new HashMap<>();
        baseTypesMap.put("0", new BaseTypes());
        baseTypesMap.put("1", new BaseTypes());
        baseTypesMap.put("2", new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMapNullKey() {
        Map<String, BaseTypes> baseTypesMap = new HashMap<>();
        baseTypesMap.put(null, new BaseTypes());
        baseTypesMap.put("1", new BaseTypes());
        baseTypesMap.put("2", new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSerializeBaseTypesWithMapEmptyKey() {
        Map<String, BaseTypes> baseTypesMap = new HashMap<>();
        baseTypesMap.put("", new BaseTypes());
        baseTypesMap.put("1", new BaseTypes());
        baseTypesMap.put("2", new BaseTypes());
        try {
            byte[] data = MemorySerialize.serialize(baseTypesMap);
            Map<String, BaseTypes> baseTypesMapDeserialized = MemorySerialize.deserialize(data);
            assertEquals(baseTypesMap, baseTypesMapDeserialized);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
