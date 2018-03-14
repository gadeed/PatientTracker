package com.demo.user1.patient;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail,etPass;
    Button btLogin;
    DatabeseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM Doctor_Table";
        Cursor cursor =   db.rawQuery(query,null);
        if(cursor != null && cursor.getCount()>0) {

            etEmail = (EditText)findViewById(R.id.userEmail);
            etPass = (EditText)findViewById(R.id.userPass);

            cursor.close();
        }
        else {
            startActivity( new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}
