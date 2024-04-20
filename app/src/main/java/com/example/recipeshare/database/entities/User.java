package com.example.recipeshare.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recipeshare.database.RecipeLogDatabase;

import java.util.Objects;

@Entity(tableName = RecipeLogDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }

    @NonNull
    @Override
    public String toString() {
        return username + '\n' +
                "Password: " + password + '\n' +
                "Admin: " + isAdmin + '\n' +
                "UserID: " + id + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=-=-\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User users = (User) o;
        return id == users.id && isAdmin == users.isAdmin && Objects.equals(username, users.username) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin);
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
