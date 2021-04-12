package com.example.blooddonation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;

public class RegisterActivity extends AppCompatActivity {

    EditText txtname, txtphone, txtemail, txtpassword, txtconfirm;
    ImageView imagemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtname = findViewById(R.id.txt_my_name);
        txtphone = findViewById(R.id.txt_my_phone);
        txtemail = findViewById(R.id.txt_my_email);
        txtpassword = findViewById(R.id.txt_my_password);
        txtconfirm = findViewById(R.id.txtconfirm);
        imagemap = findViewById(R.id.imagemap);
    }

    public void Registeration(View view) {
        if (txtphone.getText().toString().isEmpty()) {
            txtphone.setError("Enter your phone");
            txtphone.requestFocus();
        } else {
            if (txtpassword.getText().toString().isEmpty()) {
                txtpassword.setError("please  enter your password");
                txtpassword.requestFocus();
            } else {
                if (txtpassword.getText().toString().equals(txtconfirm.getText().toString())) {
                    Database db = new Database();
                    Connection con = db.ConnectDB();
                    if (con == null)
                        Toast.makeText(this, " please check internet access", Toast.LENGTH_SHORT).show();

                    else {
                        String msg = db.RunDML("insert into Users values('" + txtname.getText() +"','" +txtemail.getText().toString().toLowerCase() + "','" +txtpassword.getText()+ "','" +txtphone.getText()+ "','','','')");
                        if (msg.equals("done")) {
                            AlertDialog.Builder n = new AlertDialog.Builder(RegisterActivity.this);
                            n.setTitle("Registeration Done:)");
                            n.setPositiveButton("Goto Login", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            }).setNegativeButton("Thanks", null);

                            n.create().show();
                        } else
                            Toast.makeText(this, "Error is " + msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "confirm password not matching", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}



