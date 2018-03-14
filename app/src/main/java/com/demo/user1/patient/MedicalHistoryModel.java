package com.demo.user1.patient;

/**
 * Created by Bazictips on 03-Mar-16.
 */
public class MedicalHistoryModel {

    private String PatientNumber;
    private String PatientName;
    private String MedicalCase;
    private String note;
    private String Date;

    public MedicalHistoryModel(String patientNumber, String patientName, String medicalCase, String note, String date) {
        PatientNumber = patientNumber;
        PatientName = patientName;
        MedicalCase = medicalCase;
        this.note = note;
        Date = date;
    }

    public String getPatientNumber() {
        return PatientNumber;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getMedicalCase() {
        return MedicalCase;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return Date;
    }
}

