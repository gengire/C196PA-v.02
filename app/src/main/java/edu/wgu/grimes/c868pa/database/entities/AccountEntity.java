//*********************************************************************************
//  File:             AccountEntity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "accounts")
public class AccountEntity implements HasId {
    /**
     * Assessment primary key
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    private int id;

    /**
     * The username of the account
     */
    private String username;

    /**
     * The password hash of the account
     */
    private String password;

    /**
     * The date the user last logged in
     */
    private Date lastLogin;

    /**
     * Constructor for new accounts lacking primary key
     * @param username The username of the account
     * @param password The password hash of the account
     * @param lastLogin The date the user last logged in
     */
    @Ignore
    public AccountEntity(String username, String password, Date lastLogin) {
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    /**
     * Constructor for updated accounts (or new with non generated primary key)
     * @param id The id of the account
     * @param username The username of the account
     * @param password The password hash of the account
     * @param lastLogin The date the user last logged in
     */
    public AccountEntity(int id, String username, String password, Date lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + password + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
