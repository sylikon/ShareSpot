package com.maciek.facebooktest.Workspace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maciek.facebooktest.R;
import com.maciek.facebooktest.UserPackage.Spot;
import com.maciek.facebooktest.UserPackage.User;

public class WorkActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String TAG = "User input";

    private Button buttonCofrim;
    private EditText editTextName;
    private EditText editTextValue;
    private EditText editTextLevel;
    private EditText editTextSpot;

    private CheckBox checkBoxToRent;
    private TextView textViewLevel;
    private TextView textViewToRent;
    private TextView textViewValue;
    private TextView textViewSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        buttonCofrim = (Button)findViewById(R.id.buttonConfirm);

        editTextSpot = (EditText) findViewById(R.id.editTextSpot);
        editTextLevel = (EditText) findViewById(R.id.editTextLevel);
        editTextValue = (EditText)findViewById(R.id.editTextValue);
        editTextName = (EditText)findViewById(R.id.editTextName);
        checkBoxToRent = (CheckBox)findViewById(R.id.checkBoxToRent);

        textViewLevel = (TextView)findViewById(R.id.textViewLevel);
        textViewToRent =(TextView)findViewById(R.id.textViewToRent);
        textViewValue = (TextView)findViewById(R.id.textViewValue);
        textViewSpot = (TextView)findViewById(R.id.textViewSpotNumber);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        buttonCofrim.setOnClickListener(this);
        checkBoxToRent.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonConfirm:
                registerUser();
                break;

            case R.id.checkBoxToRent:
                if (checkBoxToRent.isChecked()) {
                    editTextValue.setVisibility(View.VISIBLE);
                    textViewValue.setVisibility(View.VISIBLE);
                }
                else {
                    editTextValue.setVisibility(View.INVISIBLE);
                    editTextValue.getText().clear();
                    textViewValue.setVisibility(View.INVISIBLE);
                }
                break;


        }


    }

    private void registerUser() {
        String username = editTextName.getText().toString().trim();
        String spotNumber = editTextSpot.getText().toString();
        String price = editTextValue.getText().toString();
        Boolean isAvailable = checkBoxToRent.isChecked();
        String phoneNumber = editTextLevel.getText().toString();
        Spot spot;
        User user;


        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(spotNumber)) {
            Toast.makeText(this, "Please enter spot number", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Please enter price", Toast.LENGTH_SHORT).show();
            return;
        } else if (checkBoxToRent.isChecked()) {
            if (TextUtils.isEmpty(price)) {
                Toast.makeText(this, "Please enter price", Toast.LENGTH_SHORT).show();
                return;
            } else {
                spot = new Spot(spotNumber, isAvailable, Double.parseDouble(price));
                user = new User(username, spot);
                databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
            }
        } else {

            spot = new Spot(spotNumber, isAvailable);
            user = new User(username, spot);

            databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
            Toast.makeText(this, "dodano", Toast.LENGTH_SHORT).show();
        }
    }


}
