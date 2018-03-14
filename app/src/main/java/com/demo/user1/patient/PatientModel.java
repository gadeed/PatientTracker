package com.demo.user1.patient;

/**
 * Created by USER 1 on 2/2/2018.
 */

public class PatientModel {
    private String patientId;
    private String patientName;
    private String PatientAge;
    private String PatientMedicalCase;

    public PatientModel(String patientId, String patientName, String patientAge, String patientMedicalCase) {
        this.patientId = patientId;
        this.patientName = patientName;
        PatientAge = patientAge;
        PatientMedicalCase = patientMedicalCase;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAge() {
        return PatientAge;
    }

    public String getPatientMedicalCase() {
        return PatientMedicalCase;
    }
}
