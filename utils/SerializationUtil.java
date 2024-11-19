// utils/SerializationUtil.java

package utils;

import java.io.*;

/**
 * Utility class for object serialization and deserialization.
 */
public class SerializationUtil {

    /**
     * Serializes an object to a file in the database directory.
     *
     * @param obj      The object to serialize.
     * @param fileName The name of the file to serialize to (without path).
     * @throws IOException If an I/O error occurs.
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        String filePath = Config.DATABASE_DIR + fileName;
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(obj);
        }
    }

    /**
     * Deserializes an object from a file in the database directory.
     *
     * @param fileName The name of the file to deserialize from (without path).
     * @return The deserialized object.
     * @throws IOException            If an I/O error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        String filePath = Config.DATABASE_DIR + fileName;
        Object obj;
        try (FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            obj = in.readObject();
        }
        return obj;
    }
}