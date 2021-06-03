package com.example.hackthonproject.ui.home;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackthonproject.MainActivity;
import com.example.hackthonproject.NewRequest;
import com.example.hackthonproject.R;
//import com.example.hackthonproject.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //private FragmentHomeBinding binding;
    ListView listView;
    ArrayList<String> list;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    DatabaseReference database;
    FirebaseUser user;
    FirebaseAuth mAuth;
    MaterialSpinner spinner;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
/*        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        spinner=view.findViewById(R.id.HomeSpinnerChooseCourse);
        recyclerView = view.findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        database = database = FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this.getContext(), list);
        recyclerView.setAdapter(myAdapter);

        database.child("Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageButton btnNewRequest = view.findViewById(R.id.btnAddNewRequest);
        btnNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toNewRequest=new Intent((Activity)getActivity(), NewRequest.class);
                toNewRequest.putExtra("username",MainActivity.username);
                startActivity(toNewRequest);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        DatabaseReference rootRef= FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        rootRef.child("Users").child(user.getUid()).child("courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<String> courses=new ArrayList<>();
                courses.add("Choose Course");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    courses.add(dataSnapshot.getValue().toString());
                }
                spinner.setItems(courses);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }


}

