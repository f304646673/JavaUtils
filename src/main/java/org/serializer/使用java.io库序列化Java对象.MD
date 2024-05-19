在我们使用诸如Redis这类缓存系统时，我们往往会存在如下需求：将Java对象保存到Redis缓存中，然后在其他机器上还原回来。
# Json方案
我们可以引入Json库等方式，将Java对象序列化为Json字符串来实现这个目的，但是这样的方案还是过于复杂。因为对于二进制类型数据，我们需要通过Base64之类的字符转换方式将其变成Json可以存储的字符串类型。反序列化时，又要Base64反解。这过程非常繁琐而且严重影响整体的效率。
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/6fbffc9a557e4b8ca5b8e3f35e393fb2.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/f789c59a73074c52adcc1849b4d3dcbf.png)
# 二进制方案
实际我们可以使用java.io库中相关类，直接将Java对象转换为二进制；还可以直接通过加载二进制数据重新构建该对象。并且这个操作**支持数组、List、Set、Map等非基础类型**。
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/4c68260b9cd942b684529d96d6fd5984.png)
直接上代码
## 核心代码

```java
package org.serialize.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MemorySerialize {
    public static <T> byte[] serialize(T obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }

    public static <T> T deserialize(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try( ObjectInputStream ois = new ObjectInputStream(bis)) {
            @SuppressWarnings("unchecked")
            T obj = (T) ois.readObject();  
            return obj;
        }
    }
}
```
## 测试代码
### 数据类
下面的数据类包含了8种Java基础类型。
为了书写方便，我们使用了Data注解来帮我们生成诸如set/get类操作。
数据类需要继承于java.io.Serializable接口，否则生成操作会报错。
```java
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
```
### Pom.xml
因为引入了lombok，并且需要写单元测试，所以在pom.xml中新增如下依赖。

```xml
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>RELEASE</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>RELEASE</version>
            <scope>test</scope>
        </dependency>
```
### 测试代码
#### 基础类型

```java
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
```
#### 数组

```java
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
```
#### List
```java
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
```
#### Set

```java
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
```

#### Map

```java
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
```
# 文件方案
将Java对象直接保存到文件中，以及直接从文件中加载内容并转换为Java对象，可以使用java.io库中FileInputStream、FileOutputStream来实现。
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/009f7803e06c4b70aa9ddbdce275ee0e.png)
## 核心代码
```java
package org.serialize.serializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSerialize {
    public static <T> void serialize(T obj, String fileName) throws Exception {
        try(FileOutputStream fos = new FileOutputStream(fileName)) {
            try(ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);
                oos.flush();
            }
        }
    }

    public static <T> T deserialize(String fileName) throws Exception {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                @SuppressWarnings("unchecked")
                T obj = (T) ois.readObject();
                return obj;
            }
        }
    }

}
```
# 代码
完整代码
[https://github.com/f304646673/JavaUtils/tree/master/src/main/java/org/serialize](https://github.com/f304646673/JavaUtils/tree/master/src/main/java/org/serialize)
