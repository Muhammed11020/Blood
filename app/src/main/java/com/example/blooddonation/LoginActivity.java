package com.example.blooddonation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LoginActivity extends AppCompatActivity {

    TextView txtforget;
    EditText editTextMail;
    EditText editTextPass;
    CheckBox chckboxRemmemberMe;
    public static String name,myName,myMail,myPass,myPhone,myCronic,myDateOfBirth,myBloodType;
    public static String mail;
    public static ResultSet resultSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtforget= findViewById(R.id.txtforget);
        editTextMail = findViewById(R.id.edt_text_login_mail);
        editTextPass = findViewById(R.id.edt_txt_login_pass);
        chckboxRemmemberMe = findViewById(R.id._chck_box_remmember_me);
         String mystring =" Forget Password";
        SpannableString content = new SpannableString ( mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
          txtforget.setText(content);
        SharedPreferences sh = getSharedPreferences("remmeber me",MODE_PRIVATE);
        mail = sh.getString("Email",null);
        if (mail!=null)
        {
            name = sh.getString("Name",null);
            startActivity(new Intent(LoginActivity.this,MainUsersActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void Login(View view) {

        if (editTextMail.getText().toString().isEmpty())
        {
            editTextMail.setError("Enter your Mail or your Phone");
            editTextMail.requestFocus();
        }
        else if (editTextPass.getText().toString().isEmpty())
            {
                editTextPass.setError("please  enter your password");
                editTextPass.requestFocus();
            }
        else
            {
                    Database db1 = new Database();
                    Connection con1 = db1.ConnectDB();
                    if (con1 == null)
                        Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    else {
                        ResultSet rs = db1.ResultSearch("select * from Users where (Email='"+editTextMail.getText().toString().toLowerCase()+"' or Phone='"+editTextMail.getText()+"') and Password='"+editTextPass.getText()+"'");
                        try {
                            if(rs.next())
                            {
                                if (chckboxRemmemberMe.isChecked())
                                {
                                    getSharedPreferences("remmeber me",MODE_PRIVATE).edit()
                                            .putString("Email" ,editTextMail.getText().toString().toLowerCase())
                                            .putString("Name",rs.getString(1).toString()).commit();
                                }
                                mail = editTextMail.getText().toString();
                                name = rs.getString(1).toString();
                                startActivity(new Intent(LoginActivity.this,MainUsersActivity.class));
                            }
                            else
                            {
                                AlertDialog.Builder n = new AlertDialog.Builder(LoginActivity.this);
                                n.setTitle("Login)");
                                n.setMessage("Invalid Login, Please try again");
                                n.setIcon(R.drawable.login1);
                                n.setPositiveButton("Create new account", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                                    }
                                }).setNegativeButton("Login again", null);

                                n.create().show();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }

    }


    public void Register(View view) {startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    public void forgetPassword(View view) {
        LayoutInflater ll = LayoutInflater.from(LoginActivity.this);
        View vv = ll.inflate(R.layout.forgetpassword,null);
        AlertDialog.Builder nn = new AlertDialog.Builder(LoginActivity.this);
        nn.setView(vv);
        nn.setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database db = new Database();
                db.ConnectDB();
                EditText forgetPass = vv.findViewById(R.id.edt_txt_forget_pass);
                ResultSet rs = db.ResultSearch("select * from Users where (Email='"+forgetPass.getText().toString().toLowerCase()+"')");
                try {
                    if(rs.next())
                    {
                        Random rpass = new Random();
                        int pass = rpass.nextInt(99999-10000+1)+10000;
                        db.RunDML("update Users set Password='"+pass+"'where Email='"+forgetPass.getText().toString().toLowerCase()+"'");

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    final String username = "blooddonnation@gmail.com";
                                    final String password = "123456asdASD";

                                    Properties props = new Properties();
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.smtp.starttls.enable", "true");
                                    props.put("mail.smtp.host", "smtp.gmail.com");
                                    props.put("mail.smtp.port", "587");

                                    Session session = Session.getInstance(props,
                                            new javax.mail.Authenticator() {
                                                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                                    return new javax.mail.PasswordAuthentication(
                                                            username, password);
                                                }
                                            });
                                    // TODO Auto-generated method stub
                                    Message message = new MimeMessage(session);
                                    message.setFrom(new InternetAddress("blooddonnation@gmail.com"));
                                    message.setRecipients(Message.RecipientType.TO,
                                            InternetAddress.parse(forgetPass.getText().toString()));
                                    message.setSubject("Forget password Blood Donation App");
                                    try {
                                        message.setText("\tHi,"
                                                + "\n\n Dear :"+rs.getString(1).toString() + "\n Your new Password is " + pass + "\n \n Thanks,\nBlood Donnation App.");
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }

                                    Transport.send(message);
                                    System.out.println("Done");


                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }).start();
                        Toast.makeText(LoginActivity.this, "The new Password has beem sent to Your mail,\n Please check your mail",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Envalid Email",Toast.LENGTH_LONG).show();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        }).setNegativeButton("Cancel",null);
        nn.create().show();

    }

}