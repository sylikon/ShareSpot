package com.maciek.facebooktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private CheckBox checkBoxHasSpot;
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
        checkBoxHasSpot = (CheckBox)findViewById(R.id.checkBoxHasSpot);
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

        buttonCofrim.setOnClickListener(this);
        checkBoxHasSpot.setOnClickListener(this);
        checkBoxToRent.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/users");


    }




    @Override
    public void onClick(View v) {




        switch (v.getId()) {
            case R.id.buttonConfirm:
                String username = editTextName.getText().toString();
                Boolean hasSpot = checkBoxHasSpot.isChecked();

                User user = new User();
                Spot spot = new Spot();
                user.setName(username);
                user.setHasSpot(hasSpot);
                user.setSpot(spot);
                try {


                    if (hasSpot) {
                        Double placeNumber = Double.parseDouble(editTextSpot.getText().toString());
                        int level = Integer.parseInt(editTextLevel.getText().toString());
                        Boolean isAvailable = checkBoxToRent.isChecked();
                        spot.setNumber(placeNumber);
                        spot.setLevel(level);
                        spot.setAvailable(isAvailable);
                        if (isAvailable) {
                            Double value = Double.parseDouble(editTextValue.getText().toString());
                            spot.setPrice(value);
                            user.setSpot(spot);
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            startActivity(new Intent(this, SpotMenu.class));
                        } else {
                            user.setSpot(spot);
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                            startActivity(new Intent(this, SpotMenu.class));
                        }
                    } else {
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                        startActivity(new Intent(this, SpotMenu.class));
                    }
                }catch (Exception e){
                    Log.i(TAG, e.getMessage());
                }


                break;
            case R.id.checkBoxHasSpot:
                if(checkBoxHasSpot.isChecked()){
                    editTextSpot.setVisibility(View.VISIBLE);
                    textViewSpot.setVisibility(View.VISIBLE);
                    editTextLevel.setVisibility(View.VISIBLE);
                    textViewLevel.setVisibility(View.VISIBLE);
                    checkBoxToRent.setVisibility(View.VISIBLE);
                    textViewToRent.setVisibility(View.VISIBLE);

                }else {
                    editTextSpot.setVisibility(View.INVISIBLE);
                    editTextSpot.setText("");
                    textViewSpot.setVisibility(View.INVISIBLE);
                    editTextLevel.setVisibility(View.INVISIBLE);
                    editTextLevel.setText("");
                    textViewLevel.setVisibility(View.INVISIBLE);
                    checkBoxToRent.setVisibility(View.INVISIBLE);
                    if(checkBoxToRent.isChecked())
                        checkBoxToRent.toggle();
                    textViewToRent.setVisibility(View.INVISIBLE);
                    editTextValue.setVisibility(View.INVISIBLE);
                    editTextValue.setText("");
                    textViewValue.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.checkBoxToRent:
                if(checkBoxToRent.getVisibility() == View.VISIBLE && checkBoxToRent.isChecked()){
                    editTextValue.setVisibility(View.VISIBLE);
                    textViewValue.setVisibility(View.VISIBLE);
                }
                else {
                    editTextValue.setVisibility(View.INVISIBLE);
                    editTextValue.setText("");
                    textViewValue.setVisibility(View.INVISIBLE);
                }

                break;


        }


    }



}
