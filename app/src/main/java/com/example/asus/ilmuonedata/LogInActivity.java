package com.example.asus.ilmuonedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.ilmuonedata.Common.Common;
import com.example.asus.ilmuonedata.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn;
    TextView demoText;

    //User session manager class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        session = new UserSessionManager(getApplicationContext());

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        String edt_phone = edtPhone.getText().toString();
                        String edt_password = edtPassword.getText().toString();

                        if(edt_phone.equals("") || edt_phone == "")
                        {
                            Toast.makeText(LogInActivity.this, "Please input your phone number!", Toast.LENGTH_SHORT).show();
                        }
                        else if(edt_password.equals("") || edt_password == "")
                        {
                            Toast.makeText(LogInActivity.this, "Please input your password!", Toast.LENGTH_SHORT).show();
                        }
                        else if (dataSnapshot.child(edt_phone).exists()) {
                            //Get User Information
                            User user = dataSnapshot.child(edt_phone).getValue(User.class);
                            user.setPhone(edt_phone); //Set Phone

                            if(edt_password.equals("") || edt_password == null)
                            {
                                Toast.makeText(LogInActivity.this, "Please input your password!", Toast.LENGTH_SHORT).show();
                            }
                            else if (user.getPassword().equals(edt_password)) {
                                Common.currentUser = user;
                                String username = Common.currentUser.getName();
                                String phone = Common.currentUser.getPhone();
                                session.createUserLoginSession(phone,username);

                                Intent homeIntent = new Intent(LogInActivity.this,Home.class);

                                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(LogInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                startActivity(homeIntent);
                                finish();

                            } else {
                                Toast.makeText(LogInActivity.this, "The password you entered did not match our records. Please double check and try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(LogInActivity.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
