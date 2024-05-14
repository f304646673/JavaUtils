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