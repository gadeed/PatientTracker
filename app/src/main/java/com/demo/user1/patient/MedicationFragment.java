package com.demo.user1.patient;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicationFragment extends Fragment {

    EditText etp_number,etm_case,etdesc,etnote;
    Spinner spdoctor;
    TextView tvdate;
    Button btSave;
    String DoctorName;
    String p_date;
    String p_note;

    Calendar calendar;
    int day,month,year;

    DatabeseHelper databeseHelper;


    public MedicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_medication, container, false);

        databeseHelper = new DatabeseHelper(getActivity());

        etp_number = (EditText)layout.findViewById(R.id.etnumber);
        etm_case = (EditText)layout.findViewById(R.id.etmcase);
        etdesc = (EditText)layout.findViewById(R.id.etdesc);
        etnote = (EditText)layout.findViewById(R.id.etnote);
        tvdate = (TextView)layout.findViewById(R.id.tvdate);
        spdoctor = (Spinner)layout.findViewById(R.id.spdoctor);
        btSave = (Button)layout.findViewById(R.id.btnSave);


        // Claneder that reads current time
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        month= month+1;

        tvdate.setText(year+"-"+month+"-"+day);

        // clicking start date textview
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getStartDate();
            }
        });


        ///////////////// Doctors ADAPTOR ///////////////////////////////////
        ArrayAdapter<DoctorsModel> adapter = new ArrayAdapter<DoctorsModel>(getActivity(), android.R.layout.simple_spinner_dropdown_item, databeseHelper.fillDoctors());
        spdoctor.setAdapter(adapter);

        spdoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DoctorsModel spinnerObject = (DoctorsModel) parent.getItemAtPosition(position);
                DoctorName = String.valueOf(spinnerObject.getDoctorName());

                //Toast.makeText(getActivity(),crime_Id,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p_number = etp_number.getText().toString();
                String p_case = etm_case.getText().toString();
                String p_desc = etdesc.getText().toString();
                String p_note = etnote.getText().toString();
                p_date = tvdate.getText().toString();
                String p_doctor = DoctorName;

                if(p_number.isEmpty() || p_case.isEmpty() || p_desc.isEmpty() || p_note.isEmpty() || p_doctor.isEmpty()){

                    Toast.makeText(getContext(),"Please Fill All Fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    databeseHelper.AddToMedication(p_number,p_case,p_desc,p_note,p_date,p_doctor);
                    Toast.makeText(getContext(),"Medication Saved",Toast.LENGTH_SHORT).show();
                    clearEditTexts();
                }
            }
        });

        return  layout;
    }

    //////// Clear all EditTexts ////////////
    public void clearEditTexts(){
        etp_number.setText("");
        etdesc.setText("");
        etm_case.setText("");
        etnote.setText("");
    }

    // this method gets the start date
    public void getStartDate(){

        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                tvdate.setText(year+"-"+month+"-"+dayOfMonth);
                p_date = tvdate.getText().toString();
            }
        },year,month,day);
        pickerDialog.show();
    }



}
