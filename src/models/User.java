package models;

import java.io.Serializable;
import java.util.HashMap;
import utils.SerializationUtil;

/**
 * Abstract class representing a user in the hospital management system.
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String userID;
    protected String name;
    protected String hashedPassword;
    protected String salt;
    protected String role;
    protected String contactNumber;
    protected String email;
    protected boolean firstLogin;

    /**
     * Constructs a User object.
     *
     * @param userID   The user's ID.
     * @param name     The user's name.
     * @param password The user's password.
     * @param role     The user's role.
     */
    public User(String userID, String name, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.salt = utils.PasswordUtil.getSalt();
        this.hashedPassword = utils.PasswordUtil.hashPassword(password, this.salt);
        this.firstLogin = true;
    }

    // Getters and Setters

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets the user's hashed password.
     *
     * @param hashedPassword The new hashed password.
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public String getRole() {
        return role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Checks if the user is logging in for the first time.
     *
     * @return true if it's the first login, false otherwise.
     */
    public boolean isFirstLogin() {
        return firstLogin;
    }

    /**
     * Sets the first login flag.
     *
     * @param firstLogin true if it's the first login, false otherwise.
     */
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    /**
     * Sets the user's salt.
     *
     * @param salt The new salt.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void saveModel() {
        try {
            HashMap<String, User> users = (HashMap<String, User>) SerializationUtil.deserialize("users.ser");
            users.put(this.getUserID(), this);
            SerializationUtil.serialize(users, "users.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
