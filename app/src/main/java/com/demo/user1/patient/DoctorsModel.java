package com.demo.user1.patient;

/**
 * Created by USER 1 on 2/28/2018.
 */

public class DoctorsModel {

    private Integer doctorId;
    private String doctorName;

    public DoctorsModel(Integer doctorId, String doctorName) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    @Override
    public String toString() {
        return doctorName;
    }
}
