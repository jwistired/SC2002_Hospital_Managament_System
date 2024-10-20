import java.io.*;

public class SerializationUtils {
    // Serialize an object to a file
    public static void serialize(Object object, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize an object from a file
    public static Object deserialize(String filename) {
        Object object = null;
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            object = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
