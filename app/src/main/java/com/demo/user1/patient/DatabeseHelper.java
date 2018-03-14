package com.demo.user1.patient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by USER 1 on 2/2/2018.
 */

public class DatabeseHelper extends SQLiteOpenHelper {

    //database version
    private static final int DB_VERSION = 1;
    // database Name
    private static final String PATIENT_TRACKER = "PATIENT_TRACKER.DB";
    //
    private static final String Patient_Table = "Patient_Table";
    private static final String PatientId = "PatientId";
    private static final String PatientName = "Name";
    private static final String Gender = "Gender";
    private static final String PatientAge = "PatientAge";
    private static final String PatientMedicalCase = "PatientMedicalCase";
    private static final String PatientPhone = "PatientPhone";

    private static final String Doctor_Table = "Doctor_Table";
    private static final String DoctorId = "DoctorId";
    private static final String DoctorName = "DoctorName";
    private static final String Special = "Special";
    private static final String Phone = "Phone";
    private static final String Email = "Email";

    private static final String Medication_Table = "Medication_Table";
    private static final String MedicationId = "MedicationId";
    private static final String PatientNumber = "PatientNumber";
    private static final String MedicalCase = "MedicalCase";
    private static final String description = "description";
    private static final String note = "note";
    private static final String MedicalDate = "MedicalDate";
    private static final String doctorName = "doctorName";



    public DatabeseHelper(Context context) {
        super(context, PATIENT_TRACKER, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createMember = "CREATE TABLE " + Patient_Table + "(" + PatientId + " TEXT NOT NULL PRIMARY KEY," + PatientName + " TEXT,"+ Gender +" TEXT,"+PatientAge + " INTEGER," + PatientMedicalCase + " TEXT,"+PatientPhone + " TEXT)";
        String createEvent = "CREATE TABLE " + Doctor_Table + "(" + DoctorId + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + DoctorName + " TEXT,"+ Special+" TEXT,"+ Phone+" TEXT,"+ Email+" TEXT)";
        String createMedication = "CREATE TABLE " + Medication_Table + "(" + MedicationId + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + PatientNumber + " TEXT,"+ MedicalCase+" TEXT,"+ description+" TEXT,"+note+" TEXT,"+ MedicalDate+" TEXT,"+ doctorName+" TEXT, FOREIGN KEY(PatientNumber) REFERENCES Patient_Table(PatientId))";

        db.execSQL(createMember);
        db.execSQL(createEvent);
        db.execSQL(createMedication);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE TABLE "+Patient_Table+" IF EXISTS");
        db.execSQL("DELETE TABLE "+Doctor_Table+" IF EXISTS");
        db.execSQL("DELETE TABLE "+Medication_Table+" IF EXISTS");
        onCreate(db);
    }


    ///////////// THESE METHODS MEMBERS TABLE METHODS ////////////
    public void AddPatient(String number,String name,String gender,Integer age,String Medicalcase){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientId,number);
        values.put(PatientName,name);
        values.put(Gender,gender);
        values.put(PatientAge,age);
        values.put(PatientMedicalCase,Medicalcase);
        database.insert(Patient_Table,null,values);
        database.close();
    }

    public ArrayList<PatientModel> getAllPatients(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PatientModel> patientList = new ArrayList<>();

        String query = "SELECT * FROM "+Patient_Table;
        Cursor cursor =   db.rawQuery(query,null);
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do  {
                patientList.add(new PatientModel(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return patientList;
    }


    ///////////// THESE METHODS EVENTS TABLE METHODS ////////////
    public void AddDoctor(String name,String special,String phone,String email){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DoctorName,name);
        values.put(Special,special);
        values.put(Phone,phone);
        values.put(Email,email);
        database.insert(Doctor_Table,null,values);
        database.close();
    }

    public ArrayList<DoctorsModel> getAlldoctors(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DoctorsModel> doctorModelArrayList = new ArrayList<>();

        String query = "SELECT * FROM "+Doctor_Table;
        Cursor cursor =   db.rawQuery(query,null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do  {
                doctorModelArrayList.add(new DoctorsModel(cursor.getInt(0),cursor.getString(1)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return doctorModelArrayList;
    }

    ///////////// THESE METHODS MEMBERS OF MEDICATIOON TABLE ////////////
    public void AddToMedication(String p_number,String p_case,String p_desc,String p_note,String p_date,String p_doctor){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientNumber,p_number);
        values.put(MedicalCase,p_case);
        values.put(description,p_desc);
        values.put(note,p_note);
        values.put(MedicalDate,p_date);
        values.put(doctorName,p_doctor);
        database.insert(Medication_Table,null,values);
        database.close();
    }

    public ArrayList<DoctorsModel> fillDoctors(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DoctorsModel> doctorsList = new ArrayList<>();

        String query = "SELECT DoctorId,DoctorName FROM Doctor_Table";
        Cursor cursor =   db.rawQuery(query,null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                doctorsList.add(new DoctorsModel(cursor.getInt(0),cursor.getString(1)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return doctorsList;
    }

}
