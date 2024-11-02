package utils;

import java.io.File;

/**
 * Configuration class for storing application-wide constants.
 */
public class Config {
    /**
     * The base directory for database files.
     */
    public static final String DATABASE_DIR = new File("src/database/").getAbsolutePath() + File.separator;

    static {
        // Static block to ensure the database directory exists
        File dbDir = new File(DATABASE_DIR);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }
    }
}
