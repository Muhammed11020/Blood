package com.example.blooddonation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.blooddonation.Database;
import com.example.blooddonation.LoginActivity;
import com.example.blooddonation.R;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyProfileFragment extends Fragment {
    TextView txtViewMyName,txtViewMyMail, txtViewMyDateOfBirth, txtViewMyBloodType, txtViewMyPass,txtViewMyPhone,txtViewTitle;
    int unHide;
    int hide;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        final TextView textView = root.findViewById(R.id.txt_my_profile);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        txtViewTitle = root.findViewById(R.id.my_profile);
        txtViewMyName = root.findViewById(R.id.txt_my_name);
        txtViewMyMail = root.findViewById(R.id.txt_my_email);
        txtViewMyPass = root.findViewById(R.id.txt_my_password);
        txtViewMyDateOfBirth = root.findViewById(R.id.txt_my_date_of_birth);
        txtViewMyBloodType = root.findViewById(R.id.txt_my_blood_type);
        txtViewMyPhone = root.findViewById(R.id.txt_my_phone);
        //unHide = txtViewMyName.getInputType();
        hide = txtViewMyPass.getInputType();



        Database d = new Database();
        d.ConnectDB();
        String phone = " 0";
        ResultSet r = d.ResultSearch("select * from Users where (Email='"+LoginActivity.mail+"')");
        try {
            if (r.next()) {
               txtViewMyName.setText("Name : " + r.getString(1).toString());
                txtViewMyMail.setText("Mail: " +r.getString(2).toString());
                txtViewMyPass.setText(r.getString(3).toString());
                txtViewMyPhone.setText("Phone: " + r.getString(4).toString());
                txtViewMyDateOfBirth.setText("Date of Birth: " + r.getString(6).toString());
                txtViewMyBloodType.setText("Blood Type: " + r.getString(7).toString());

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        txtViewMyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtViewMyPass.getInputType() == unHide)
                    txtViewMyPass.setInputType(hide);
                else {
                    txtViewMyPass.setInputType(unHide);
                }


            }
        });


        return root;
        }


    }

