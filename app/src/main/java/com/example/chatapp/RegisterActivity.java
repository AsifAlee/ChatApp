package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText userName,password,email;
    Button registerButton;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = (MaterialEditText)findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        registerButton = findViewById(R.id.regiterBtn);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = userName.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String passwordTxt = password.getText().toString().trim();

                if(TextUtils.isEmpty(usernameText) ||TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordTxt)){
                    Toast.makeText(getApplicationContext(),"All fields are required"
                    ,Toast.LENGTH_SHORT).show();
                }
                else if(passwordTxt.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password must be at least six characters",
                            Toast.LENGTH_SHORT).show();

                }

                else {
                    Register(usernameText,emailText,passwordTxt);
                }
            }
        });


    }

    public void Register(final String username, String email, final String password){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId);
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id",userId);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");
                            hashMap.put("status","offline");
                            hashMap.put("search",username.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Intent intent = new Intent(RegisterActivity.this
                                                , LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else{
                                        Toast.makeText(getApplicationContext(),"Cant Register with this username"
                                        ,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        }
                    }
                });
    }
}
