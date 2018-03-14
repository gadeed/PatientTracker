package com.demo.user1.patient;

/**
 * Created by USER 1 on 2/3/2018.
 */

public class DoctorModel {
    private String name;
    private String special;
    private String phone;
    private String email;

    public DoctorModel(String name, String special, String phone, String email) {
        this.name = name;
        this.special = special;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSpecial() {
        return special;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
