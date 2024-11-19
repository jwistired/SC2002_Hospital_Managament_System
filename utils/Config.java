package utils;

import java.io.File;

/**
 * Configuration class for storing application-wide constants.
 * This class defines constants used throughout the application,
 * such as the base directory for database files. It also ensures that
 * the necessary directories for storing database files exist when the
 * application is initialized.
 */
public class Config {

    /**
     * The base directory for database files. This is the absolute path
     * to the "src/database/" directory in the project structure.
     * The path is constructed dynamically to ensure platform compatibility.
     */
    public static final String DATABASE_DIR = new File("src/database/").getAbsolutePath() + File.separator;

    // Static block to ensure the database directory exists
    static {
        File dbDir = new File(DATABASE_DIR);
        if (!dbDir.exists()) {
            dbDir.mkdirs();  // Creates the directory if it doesn't exist
        }
    }
}
