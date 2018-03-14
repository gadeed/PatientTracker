package com.demo.user1.patient;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar)findViewById(R.id.contactTool);
        setSupportActionBar(toolbar);

         this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         this.getSupportActionBar().setTitle("My Profile");
    }
}
