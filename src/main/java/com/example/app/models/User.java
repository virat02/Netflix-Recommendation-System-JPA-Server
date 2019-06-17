package com.example.app.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    protected String firstName;
    protected String lastName;

    @Column(unique = true)
    protected String username;
    protected String password;

    @Column(insertable = false, updatable = false)
    protected String dtype;

    @Column(unique = true)
    protected String email;

    protected String dob;
    
    @OneToMany(mappedBy = "AUser", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<Address> userAddresses;
    
    @OneToMany(mappedBy = "PUser", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<Phone> userPhoneNumbers;

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public List<Phone> getUserPhoneNumbers() {
		return userPhoneNumbers;
	}

	public void setUserPhoneNumbers(List<Phone> userPhoneNumbers) {
		this.userPhoneNumbers = userPhoneNumbers;
	}

	public List<Address> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(List<Address> userAddresses) {
		this.userAddresses = userAddresses;
	}

	public User() {
        super();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
