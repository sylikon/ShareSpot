package com.maciek.facebooktest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonSingup;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewLogin;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        buttonSingup = (Button)findViewById(R.id.buttonSingup);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        textViewLogin = (TextView)findViewById(R.id.textViewLogin);

        buttonSingup.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("/users");

        mAuth = FirebaseAuth.getInstance();


    }

    private void loginUser(){
        String email = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stop from further execution
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Loginining User...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user logged in
                    progressDialog.hide();
                    //Toast.makeText(LoginActivity.this, "User Logged in", Toast.LENGTH_SHORT).show();
                    isRegistered();
                    startActivity(new Intent(getApplicationContext(), WorkActivity.class));
                }else {
                    //user failed logging in
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this, "Loginning failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSingup:
                loginUser();
                break;
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));

        }
    }

    public boolean isRegistered(){
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot:dataSnapshot.getChildren() ){
                    User user = userSnapshot.getValue(User.class);
                    if(user.getName()!="")
                        startActivity(new Intent(getApplicationContext(), SpotMenu.class));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return false;
    }



}
