package com.example.hackthonproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hackthonproject.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    public static String username; // changes on data change
    public static String profileImage=null;
    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;
    private boolean welcomeOnlyOnce=false;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Logging in..");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth=FirebaseAuth.getInstance();

        user=mAuth.getCurrentUser();

        if(user==null){
            ToLoginActivity();
            loadingBar.dismiss();
            finish();
        }else{
            //first get data

            FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    username= String.valueOf(dataSnapshot.child("username").getValue());
                    if(dataSnapshot.child("firstTime").exists() && String.valueOf(dataSnapshot.child("firstTime").getValue()).equals("no")) {
                        if(!welcomeOnlyOnce)
                            //welcome toast
                            Toast.makeText(MainActivity.this, "Welcome " + username, Toast.LENGTH_LONG).show();
                        welcomeOnlyOnce=true;

                    }else{ // no user name init-> to welcome activity
                        SendToWelcomeActivity();
                        loadingBar.dismiss();
                        finish(); // so user will not be able to go back here
                    }
                    if(dataSnapshot.child("profileImage").exists()){
                        profileImage=String.valueOf(dataSnapshot.child("profileImage").getValue());
                    }

                    // finished loading
                    loadingBar.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

    private void SendToWelcomeActivity() {
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }

    private void ToLoginActivity() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.settingsOption){

        }
        if(item.getItemId()==R.id.signOutOption){
            mAuth.signOut();
            ToLoginActivity();
        }
        if(item.getItemId()==R.id.editProfileOption){
            //ToEditProfileActivity();
        }

        return true;
    }

}