package main.java.org.ce.ap.server.modules;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * this class holds the user information.
 * this class implements Serializable for serializing.
 *
 * @author Eid Mohammad Ahmadi.
 * @version 2.0.
 */
public class User implements Serializable {
    //variables:
    private String username;
    private String name;
    private String lastname;
    private byte[] password;
    private String dateOfBirth;
    private final LocalDateTime dateOfJoin;
    private String biography;
    //constructor:

    /**
     * this class will give the necessary information of user information
     * and initialize that.
     *
     * @param username    username.
     * @param password    password.
     * @param name        name.
     * @param dateOfBirth date of birth.
     */
    public User(String username, byte[] password, String name, String dateOfBirth) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        dateOfJoin = LocalDateTime.now();
    }
    //methods:

    /**
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username.
     *
     * @param username new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * set name.
     *
     * @param name new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return lastname.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * set lastname.
     *
     * @param lastname new lastname.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return password.
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * set password.
     *
     * @param password new password.
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * @return date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * set date of birth.
     *
     * @param dateOfBirth new date of birth.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return date of join to tweeter.
     */
    public LocalDateTime getDateOfJoin() {
        return dateOfJoin;
    }

    /**
     * @return biography.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * set biography.
     *
     * @param biography new biography.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }
}
