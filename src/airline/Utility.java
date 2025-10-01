package airline;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.swing.text.*;

/**
 * Helper functions: Database Connection, Session, Hashing Function, Config loader, Length Filter
 * @author kondk
 */

public class Utility {
    
    /**
     * DATABASE CONNECTION
     * Provides MySQL connection using JDBC
     */
    public static class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/airline_reservations";
        private static final String USER = "root";
        private static final String PASSWORD = Config.getConfigProperty("DB_PASS");

        /**
         * Utility.DatabaseConnection.getConnection()
         * @return Connection
         * @throws SQLException 
         */
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    } 

    
    /**
     * SESSION
     * Creates the logged in user's session
     */
    public static class Session {

        private static String username;
        private static String paxID;
        
        /**
         * Utility.Session.setUser(uname,id) : Sets username and ID in the session
         * @param uname username of the user
         * @param id Passenger ID of the user
         */
        public static void setUser(String uname, String id) {
            username = uname;
            paxID = id;
        }
        
        /**
         * Utility.Session.getUsername() : Returns the username of the user in the current session
         * @return username
         */
        public static String getUsername() {
            return username;
        }
        
        /**
         * Utility.Session.getPaxID() : Returns the Passenger ID of the user in the current session
         * @return paxID
         */
        public static String getPaxID() {
            return paxID;
        }
    }
    
    
    /**
     * CONFIG
     * To access contents from config.properties file
     */
    public static class Config {
        private static final Properties props = new Properties();
        static {
            try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    System.err.println("config.properties not found in classpath!");
                } else {
                    props.load(input);
                }
            } catch (IOException e) {
                System.err.println("Could not load properties file: " + e.getMessage());
            }
        }
        
        /**
         * Utility.Config.getConfigProperty(key) : Returns value of the input key from config.properties file
         * @param key
         * @return value of the key
         */
        public static String getConfigProperty(String key) {
            return props.getProperty(key.trim());
        }
    }
    
    
    /**
     * HASHING
     * Performs FNV-1a hashing
     * ⚠ ⚠ NOT TO BE USED FOR ACTUAL PASSWORD STORING. FNV-1a IS NOT CRYPTOGRAPHICALLY SECURE
     * ⚠ ⚠ THIS IS JUST USED AS A DEMONSTRATION FOR THIS PROJECT
     */
    public static class Hash { 
        private static final long FNV_64_BASIS = Long.parseUnsignedLong(Config.getConfigProperty("FNV_64_BASIS").trim()); // Basis number, from confiig.properties
        private static final long FNV_64_PRIME = Long.parseUnsignedLong(Config.getConfigProperty("FNV_64_PRIME").trim()); // Prime number, from config.properties
                
        /**
         * Utility.Hash.hash64(text) : Converts text into FNV-1a hash and returns integer(long) representation
         * @param text input text, e.g. password
         * @return hashed output in long format
         */
        public static long hash64(String text) {
            // Step 1:  Start the hash with the initial FNV basis value
            long hash = FNV_64_BASIS;

            // Step 2: Convert the input string to a sequence of bytes
            final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

            // Step 3: Loop through each byte of the data.
            for (final byte b : bytes) {
                // Step 3.1: XOR the hash with the current byte's value.
                hash = hash ^ b;

                // Step 3.2: Multiply the hash by the FNV prime. Java's long overflow is expected and part of the algorithm.
                hash = hash * FNV_64_PRIME;
            }
            return hash;
        }
        
        /**
         * Utility.Hash.hash64Hex(text) : Converts text into FNV-1a hash and returns in hexadecimal format
         * @param text input text, e.g.
         * @return hashed output in hexadecimal format
         */
        public static String hash64Hex(String text) {
            return Long.toHexString(hash64(text));
        }
    }
    
    
    /**
     * LENGTH FILTER
     * For setting maximum character limit for jField and jPasswordField
     */
    public static class LengthFilter extends DocumentFilter {
        private int maxChars;

        public LengthFilter(int maxChars) {
            this.maxChars = maxChars;
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (fb.getDocument().getLength() + string.length() <= maxChars) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (fb.getDocument().getLength() - length + (text != null ? text.length() : 0) <= maxChars) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
