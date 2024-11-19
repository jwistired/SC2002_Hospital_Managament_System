package models;

import java.io.Serializable;
import java.util.HashMap;
import utils.SerializationUtil;

/**
 * Abstract class representing a user in the hospital management system.
 * This class serves as the base class for different types of users (e.g., Patient, Doctor, Pharmacist).
 * It includes common user properties such as ID, name, password, role, and personal details.
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String userID;
    protected String name;
    protected String hashedPassword;
    protected String salt;
    protected String role;
    protected String contactNo;
    protected String email;
    protected String bloodType;
    protected String gender;
    protected String dateOfBirth;
    protected boolean firstLogin;

    /**
     * Constructs a User object with the specified user ID, name, password, and role.
     * The password is hashed and salted for security.
     *
     * @param userID   The user's ID.
     * @param name     The user's name.
     * @param password The user's password.
     * @param role     The user's role (e.g., "Patient", "Doctor", etc.).
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

    /**
     * Gets the user's date of birth.
     *
     * @return The user's date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the user's gender.
     *
     * @return The user's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the user's blood type.
     *
     * @return The user's blood type.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Gets the user's ID.
     *
     * @return The user's ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the hashed password of the user.
     *
     * @return The hashed password.
     */
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

    /**
     * Gets the salt associated with the user's password.
     *
     * @return The salt for password hashing.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Gets the user's role.
     *
     * @return The user's role (e.g., "Patient", "Doctor", etc.).
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the user's contact number.
     *
     * @return The user's contact number.
     */
    public String getContactNumber() {
        return contactNo;
    }

    /**
     * Sets the user's contact number.
     *
     * @param contactNo The new contact number.
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address.
     */
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
     * Sets the user's salt for password hashing.
     *
     * @param salt The new salt.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Saves the user's information into the persistent storage (serialization file).
     * This updates the serialized "users.ser" file with the current user's data.
     */
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
