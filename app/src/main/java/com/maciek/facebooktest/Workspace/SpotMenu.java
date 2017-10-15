package com.maciek.facebooktest.Workspace;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maciek.facebooktest.Login.LoginActivity;
import com.maciek.facebooktest.R;
import com.maciek.facebooktest.UserPackage.User;

import java.util.ArrayList;
import java.util.List;

public class SpotMenu extends AppCompatActivity {

    private ListView listView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<User> userList;

    private String TAG = "User Action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_menu);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/users");
        listView = (ListView)findViewById(R.id.listViewSpot);
        userList = new ArrayList<>();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

//        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
//
//        TabHost.TabSpec tabWeekend = tabHost.newTabSpec("miejsce na weekend");
//        TabHost.TabSpec tabLongterm = tabHost.newTabSpec("miejsce na długo");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logOut();
                return true;
            case R.id.save:
                startActivity(new Intent(getApplicationContext(), WorkActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }



    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot userSnapshot:dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    if(user.getSpot().isAvailable()){
                        userList.add(user);
                    }

                }
                SpotList adapter = new SpotList(SpotMenu.this, userList);
                listView.setAdapter(adapter);

                //klikniecie w element listy pobiera jego wlasciwosci, umozliwia rejerstracje
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(TAG, "Użytkownik: " + userList.get(position).getName() + " ma miejsce postojowe: " +
                                userList.get(position).getSpot().getNumber() + " za: " + userList.get(position).getSpot().getPrice());
                        Log.i(TAG, "Key: " + userList.get(position));
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
