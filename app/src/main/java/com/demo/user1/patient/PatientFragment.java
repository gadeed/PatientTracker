package com.demo.user1.patient;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {

    DatabeseHelper databeseHelper;
    ListView rvMemebrs;
   ListAdapter adapter;
    String PatientId;
    String Gender;


    public PatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_patient, container, false);

        databeseHelper = new DatabeseHelper(getActivity());
        FloatingActionButton fab = (FloatingActionButton)layout.findViewById(R.id.btAddMember);

        rvMemebrs = (ListView)layout.findViewById(R.id.rvMembers);

         ArrayList<PatientModel> list;
         list = databeseHelper.getAllPatients();
         adapter = new ListAdapter(PatientFragment.this,list);
         rvMemebrs.setAdapter(adapter);


        rvMemebrs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.tvPid);
                PatientId =textView.getText().toString();
               // Toast.makeText(getActivity(),"kkkkkkkkkk",Toast.LENGTH_SHORT).show();
                displayPatientInfo(PatientId);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentProcess();
            }
        });
        return layout;
    }


    public void paymentProcess(){
        final Dialog paymentDialog = new Dialog(getActivity());
        paymentDialog.setContentView(R.layout.patient_reg_dialog);
        final EditText dtNumber = (EditText)paymentDialog.findViewById(R.id.etNumber);
        final EditText dtName = (EditText)paymentDialog.findViewById(R.id.etName);
        final EditText dtAge = (EditText)paymentDialog.findViewById(R.id.etage);
        final EditText dtCase = (EditText)paymentDialog.findViewById(R.id.etcase);
        final Spinner spGender = (Spinner)paymentDialog.findViewById(R.id.spgender);
        Button btnok = (Button)paymentDialog.findViewById(R.id.btnOk);

        ArrayAdapter<CharSequence> gender=  ArrayAdapter.createFromResource(getActivity(),R.array.gender,
                android.R.layout.simple_spinner_item);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(gender);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Gender = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = dtNumber.getText().toString();
                String name = dtName.getText().toString();
                String age = dtAge.getText().toString();
                String M_case = dtCase.getText().toString();

                if (number.isEmpty() || name.isEmpty() || age.isEmpty()|| M_case.isEmpty()) {
                    Toast.makeText(getActivity(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (!getPatientId(number)) {
                        databeseHelper.AddPatient(number, name, Gender,Integer.parseInt(age), M_case);
                        dtNumber.setText("");
                        dtName.setText("");
                        dtAge.setText("");
                        dtCase.setText("");

                        Toast.makeText(getActivity(), "One Row Saved", Toast.LENGTH_SHORT).show();
                        ArrayList<PatientModel> list;
                        list = databeseHelper.getAllPatients();
                        adapter = new ListAdapter(PatientFragment.this,list);
                        rvMemebrs.setAdapter(adapter);
                        paymentDialog.hide();
                    }
                    else {
                        Toast.makeText(getActivity(), "Sorry,Patient Number is Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        paymentDialog.show();

    }
    public class ListAdapter extends BaseAdapter {

        LayoutInflater lInflater;
        List<PatientModel> data;
        ArrayList<PatientModel> arraylist;

        public ListAdapter(Fragment activity, List<PatientModel> data) {
            this.lInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
            // this.arraylist = new ArrayList<ItemListObject>();
            // this.arraylist.addAll(data);
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
                convertView = lInflater.inflate(R.layout.patients_list, parent, false);
                listViewHolder.PatientId = (TextView) convertView.findViewById(R.id.tvPid);
                listViewHolder.Patientname = (TextView) convertView.findViewById(R.id.tvname);

                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }

            PatientModel object = data.get(position);

            // ID = object.getCustid();
            listViewHolder.PatientId.setText(object.getPatientId());
            listViewHolder.Patientname.setText(object.getPatientName());

            return convertView;
        }

        class ViewHolder {
            TextView PatientId;
            TextView Patientname;
        }
    }

   /* public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.RecyclerViewHolder>{

        private ArrayList<PatientModel> membersList = new ArrayList<>();

        public RecyclerAdaptor(ArrayList<PatientModel> arrayList){
            this.membersList= arrayList;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.patients_list,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(row);
            return recyclerViewHolder;

        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            PatientModel patientModel = membersList.get(position);
            holder.tvName.setText(patientModel.getPatientName());
           // holder.tvNumber.setText(patientModel.get());
        }

        @Override
        public int getItemCount() {
            return membersList.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder{
            TextView tvName,tvNumber;
            RecyclerViewHolder(View view){
                super(view);

                tvName = (TextView)view.findViewById(R.id.tvname);
               // tvNumber = (TextView)view.findViewById(R.id.tvnumber);
            }

        }
    }*/

/////// this method returns true if Patient Number exists and returns false if Not ////////
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean getPatientId(String Number){
        boolean exist = false;
        SQLiteDatabase db = databeseHelper.getReadableDatabase();
        //String query = "SELECT PatientId FROM Patient_Table WHERE PatientId ='"+Number+"'";
        Cursor cursor =   db.rawQuery("SELECT PatientId FROM Patient_Table WHERE PatientId = ?",new String[]{Number},null);
        if(cursor != null && cursor.getCount() > 0) {
            exist = true;
            cursor.close();
        }
        return exist;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void displayPatientInfo(final String patient_id){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.patient_info_list);

        TextView tvPNumber = (TextView)dialog.findViewById(R.id.p_Number);
        TextView tvPName = (TextView)dialog.findViewById(R.id.p_Name);
        TextView tvPgender = (TextView)dialog.findViewById(R.id.p_gender);
        TextView tvPAge = (TextView)dialog.findViewById(R.id.p_age);
        TextView tvPMCase = (TextView)dialog.findViewById(R.id.p_medicalCase);
        Button btDelete = (Button)dialog.findViewById(R.id.btDelete);
        Button btCancel = (Button)dialog.findViewById(R.id.btCancel);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databeseHelper.getReadableDatabase();
                db.delete("Patient_Table","PatientId=?",new String[]{patient_id});
                adapter = new ListAdapter(PatientFragment.this,databeseHelper.getAllPatients());
                adapter.notifyDataSetChanged();
                rvMemebrs.setAdapter(adapter);

                dialog.dismiss();
            }
        });

        SQLiteDatabase db = databeseHelper.getReadableDatabase();
        //String query = "SELECT PatientId FROM Patient_Table WHERE PatientId ='"+Number+"'";
        Cursor cursor =   db.rawQuery("SELECT * FROM Patient_Table WHERE PatientId = ?",new String[]{patient_id},null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            tvPNumber.setText(cursor.getString(0));
            tvPName.setText(cursor.getString(1));
            tvPgender.setText(cursor.getString(2));
            tvPAge.setText(cursor.getString(3));
            tvPMCase.setText(cursor.getString(4));
            cursor.close();
        }
        dialog.show();
    }

}
