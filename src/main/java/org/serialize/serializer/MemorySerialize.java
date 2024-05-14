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