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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsFragment extends Fragment {
    DatabeseHelper databeseHelper;
    ListView lvDoctors;
     ListAdapter adapter;
    String DoctorId;
    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public DoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_doctors, container, false);
        databeseHelper = new DatabeseHelper(getActivity());
        FloatingActionButton fab = (FloatingActionButton)layout.findViewById(R.id.btAddEvent);

        lvDoctors = (ListView) layout.findViewById(R.id.rvDoctors);
     /*   recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewEvents.setHasFixedSize(true);*/

        ArrayList<DoctorsModel> list;
        list = databeseHelper.getAlldoctors();
        adapter = new ListAdapter(DoctorsFragment.this,list);
        lvDoctors.setAdapter(adapter);


        lvDoctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.tvDid);
                DoctorId =textView.getText().toString();
                // Toast.makeText(getActivity(),"kkkkkkkkkk",Toast.LENGTH_SHORT).show();
                displayPatientInfo((DoctorId));
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
        paymentDialog.setContentView(R.layout.doctor_reg_dialog);

        final EditText dtName = (EditText)paymentDialog.findViewById(R.id.etName);
        final EditText dtSpecial = (EditText)paymentDialog.findViewById(R.id.etSpecialist);

        final EditText dtPhone = (EditText)paymentDialog.findViewById(R.id.etPhone);

        final EditText dtEmail = (EditText)paymentDialog.findViewById(R.id.etEmail);

        Button btnok = (Button)paymentDialog.findViewById(R.id.btnOk);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = dtName.getText().toString();
                String special = dtSpecial.getText().toString();
                String phone = dtPhone.getText().toString();
                String email = dtEmail.getText().toString();

                if (name.isEmpty()|| special.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else if(!isValidEmail(email)){
                    Toast.makeText(getActivity(), "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }else if(phone.length()<6){
                Toast.makeText(getActivity(), "PHone number must be six Digit", Toast.LENGTH_SHORT).show();
                }
                else {
                    databeseHelper.AddDoctor(name,special,phone,email);
                    dtName.setText("");
                    Toast.makeText(getActivity(), "One Row Saved", Toast.LENGTH_SHORT).show();
                    ArrayList<DoctorsModel> list;
                    list = databeseHelper.getAlldoctors();
                    adapter = new ListAdapter(DoctorsFragment.this,list);
                    lvDoctors.setAdapter(adapter);
                    paymentDialog.hide();
                }
            }
        });
        paymentDialog.show();

    }

    public class ListAdapter extends BaseAdapter {

        LayoutInflater lInflater;
        List<DoctorsModel> data;
        ArrayList<DoctorsModel> arraylist;

        public ListAdapter(Fragment activity, List<DoctorsModel> data) {
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
                convertView = lInflater.inflate(R.layout.doctors_list, parent, false);
                listViewHolder.tvName = (TextView) convertView.findViewById(R.id.tveventname);
                listViewHolder.tvDid = (TextView) convertView.findViewById(R.id.tvDid);
                listViewHolder.ivDoctor = (ImageView) convertView.findViewById(R.id.ivdoctor);
                convertView.setTag(listViewHolder);
            } else {
                listViewHolder = (ViewHolder) convertView.getTag();
            }

            DoctorsModel object = data.get(position);

            // ID = object.getCustid();
            //get first letter of each String item
            String firstLetter = String.valueOf(object.getDoctorName().charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            //int color = generator.getColor(getItem(position));
            int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            listViewHolder.tvName.setText(object.getDoctorName());
           listViewHolder.tvDid.setText(String.valueOf(object.getDoctorId()));
            listViewHolder.ivDoctor.setImageDrawable(drawable);

            return convertView;
        }

        class ViewHolder {
            TextView tvDid;
            TextView tvName;
            ImageView ivDoctor;
        }
    }
 /*   public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.RecyclerViewHolder>{

        private ArrayList<DoctorModel> eventlist = new ArrayList<>();

        public RecyclerAdaptor(ArrayList<DoctorModel> arrayList){
            this.eventlist= arrayList;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.doctors_list,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(row);
            return recyclerViewHolder;

        }

        @Override
        public void onBindViewHolder(RecyclerAdaptor.RecyclerViewHolder holder, int position) {
            DoctorModel doctorModel = eventlist.get(position);
            holder.tvName.setText(doctorModel.getName());

            //get first letter of each String item
            String firstLetter = String.valueOf(doctorModel.getName().charAt(0));
*//*
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getColor(getItem(position));
            //int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            holder.ivDoctor.setImageDrawable(drawable);*//*
        }

        @Override
        public int getItemCount() {
            return eventlist.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder{
            TextView tvName;
            ImageView ivDoctor;
            RecyclerViewHolder(View view){
                super(view);
                tvName = (TextView)view.findViewById(R.id.tveventname);
                ivDoctor = (ImageView)view.findViewById(R.id.ivdoctor);
            }

        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void displayPatientInfo(final String id){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.doctors_info_list);

        TextView tvd_Id = (TextView)dialog.findViewById(R.id.d_Number);
        TextView tvp_Name = (TextView)dialog.findViewById(R.id.d_Name);
        TextView tvp_special = (TextView)dialog.findViewById(R.id.d_special);
        TextView tvp_email = (TextView)dialog.findViewById(R.id.d_email);
        TextView tvp_mobile = (TextView)dialog.findViewById(R.id.d_mobile);
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
                db.delete("Doctor_Table","DoctorId=?",new String[]{id});
                adapter = new ListAdapter(DoctorsFragment.this,databeseHelper.getAlldoctors());
                adapter.notifyDataSetChanged();
                lvDoctors.setAdapter(adapter);

                dialog.dismiss();
            }
        });

        SQLiteDatabase db = databeseHelper.getReadableDatabase();
        //String query = "SELECT PatientId FROM Patient_Table WHERE PatientId ='"+Number+"'";
        Cursor cursor =   db.rawQuery("SELECT * FROM Doctor_Table WHERE DoctorId = ?",new String[]{id},null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            tvd_Id.setText(cursor.getString(0));
            tvp_Name.setText(cursor.getString(1));
            tvp_special.setText(cursor.getString(2));
            tvp_email.setText(cursor.getString(3));
            tvp_mobile.setText(cursor.getString(4));
            cursor.close();
        }
        dialog.show();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
