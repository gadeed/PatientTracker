package com.demo.user1.patient;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalHistoryFragment extends Fragment {

    ListView mainlist;
    ArrayList<MedicalHistoryModel> CustomersArrayList;
   DatabeseHelper helper;
    ListAdapter listAdapter;
    EditText etpatientNo,etpatientName;

    public MedicalHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_medical_history, container, false);
        helper=new DatabeseHelper(getActivity());

        etpatientNo = (EditText)layout.findViewById(R.id.patientNo);
        etpatientName = (EditText)layout.findViewById(R.id.patientName);
        Button btSearch = (Button)layout.findViewById(R.id.btSearch);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientNo = etpatientNo.getText().toString();
                String patientName = etpatientName.getText().toString();

                if(!patientNo.isEmpty() && !patientName.isEmpty()){
                   Toast.makeText(getActivity(),"Please Search One at Once",Toast.LENGTH_SHORT).show();
                }
                else if(!patientNo.isEmpty() && patientName.isEmpty()) {
                    getPatientMedicalHistorybyNo(patientNo);
                }
                else if(patientNo.isEmpty() && !patientName.isEmpty()) {
                    getPatientMedicalHistorybyName(patientName);
                }
                else {
                    Toast.makeText(getActivity(),"Please Fill One Field",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return layout;
    }



    public class ListAdapter extends BaseAdapter {

        LayoutInflater lInflater;
        List<MedicalHistoryModel> data;
        ArrayList<MedicalHistoryModel> arraylist;

        public ListAdapter(Fragment activity, List<MedicalHistoryModel> data) {
            this.lInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
            this.arraylist = new ArrayList<MedicalHistoryModel>();
            this.arraylist.addAll(data);
            // CustomersArrayList.addAll(data);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if (convertView == null) {
                listViewHolder = new ViewHolder();
                convertView = lInflater.inflate(R.layout.medicalhistorylist, parent, false);
                listViewHolder.patientNumber = (TextView) convertView.findViewById(R.id.p_Number);
                listViewHolder.patientName = (TextView) convertView.findViewById(R.id.p_Name);
                listViewHolder.medicalCase = (TextView) convertView.findViewById(R.id.p_medicalCase);
                listViewHolder.note = (TextView) convertView.findViewById(R.id.p_note);
                listViewHolder.Date = (TextView) convertView.findViewById(R.id.p_medicaldate);
                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }

            MedicalHistoryModel object = data.get(position);

            // ID = object.getCustid();
            //listViewHolder.patientId.setText(String.valueOf(object.ge()));
            listViewHolder.patientNumber.setText(object.getPatientNumber());
            listViewHolder.patientName.setText(object.getPatientName());
            listViewHolder.medicalCase.setText(object.getMedicalCase());
            listViewHolder.note.setText(object.getNote());
            listViewHolder.Date.setText(object.getDate());
            return convertView;
        }

        class ViewHolder {
            TextView patientNumber;
            TextView patientName;
            TextView medicalCase;
            TextView note;
            TextView Date;
        }

    }

 ///////// Search by ID ////////////
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void getPatientMedicalHistorybyNo(String P_No){
        CustomersArrayList = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        //String query = "SELECT PatientNumber,Name,MedicalCase,note,MedicalDate FROM Medication_Table m JOIN Patient_Table p ON m.PatientNumber = p.PatientId WHERE PatientNumber = '"+P_No+"'";
        Cursor cursor =   db.rawQuery("SELECT PatientNumber,Name,MedicalCase,note,MedicalDate FROM Medication_Table m JOIN Patient_Table p ON m.PatientNumber = p.PatientId WHERE PatientNumber = ?",new String[]{P_No},null);
        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do  {
                CustomersArrayList.add(new MedicalHistoryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while(cursor.moveToNext());
            cursor.close();
            showHistoryDialog();
        }
        else {
            Toast.makeText(getActivity(),"No Patients History",Toast.LENGTH_SHORT).show();
        }
    }

    ////// search by Name
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void getPatientMedicalHistorybyName(String P_Name){
        CustomersArrayList = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
       // String query = "SELECT PatientNumber,Name,MedicalCase,note,MedicalDate FROM Medication_Table m JOIN Patient_Table p ON m.PatientNumber = p.PatientId WHERE PatientNumber = '"+P_No+"'";
        Cursor cursor =   db.rawQuery("SELECT PatientNumber,Name,MedicalCase,note,MedicalDate FROM Medication_Table m JOIN Patient_Table p ON m.PatientNumber = p.PatientId WHERE Name = ?",new String[]{P_Name},null);
        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do  {
                CustomersArrayList.add(new MedicalHistoryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while(cursor.moveToNext());
            cursor.close();
            showHistoryDialog();
        }
        else {
            Toast.makeText(getActivity(),"No Patients History",Toast.LENGTH_SHORT).show();
        }
    }

    public void showHistoryDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.medical_history_dialog);
        mainlist = (ListView) dialog.findViewById(R.id.mainlist);
        ListAdapter listAdapter = new ListAdapter(MedicalHistoryFragment.this,CustomersArrayList);
        listAdapter.notifyDataSetChanged();
        mainlist.setAdapter(listAdapter);

        dialog.show();
    }



























    /*public ArrayList<PatientModel> getAllPatients(){
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<PatientModel> patientList = new ArrayList<>();

        String query = "SELECT * FROM Patient_Table";
        Cursor cursor =   db.rawQuery(query,null);
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do  {
                patientList.add(new PatientModel(cursor.getString(1), cursor.getInt(2),cursor.getString(3)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return patientList;
    }*/
}
