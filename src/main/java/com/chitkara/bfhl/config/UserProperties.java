package com.chitkara.bfhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.user")
public class UserProperties {

    private String fullName;
    private String dob;
    private String email;
    private String rollNumber;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String buildUserId() {
        String normalizedName = fullName.trim().toLowerCase().replaceAll("\\s+", "_");
        return normalizedName + "_" + dob.trim();
    }
}
