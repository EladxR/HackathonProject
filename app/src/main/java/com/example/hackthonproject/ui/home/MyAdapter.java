package com.example.hackthonproject.ui.home;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackthonproject.R;
import com.example.hackthonproject.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Request> list;

    public MyAdapter(Context context, ArrayList<Request> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Request myRequest = list.get(position);

        holder.name.setText(myRequest.getUsername());
        holder.title.setText(myRequest.getTitle());
        holder.dateTimeText.setText(myRequest.getDate() + "   " + myRequest.getTime());
        holder.description.setText(myRequest.getDescription());
        holder.location.setText(myRequest.getLocation());
        holder.phoneNumber.setText(myRequest.getUserID());
        FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users").child(myRequest.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                // holder.profileImage.setImageURI(Uri.parse(snapshot.child("profileImage").getValue().toString()));
                if (snapshot.child("profileImage").exists()) {
                    Picasso.get().load((String) snapshot.child("profileImage").getValue()).placeholder(R.drawable.contact_image1).into(holder.profileImage);
                }
                holder.phoneNumber.setText(snapshot.child("phoneNumber").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


public static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView title;
    TextView description;
    TextView dateTimeText;
    TextView location;
    ImageView profileImage;
    TextView phoneNumber;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.usernameCard);
        this.title = itemView.findViewById(R.id.titleCard);
        this.dateTimeText = itemView.findViewById(R.id.dateAndTimeCard);
        this.location = itemView.findViewById(R.id.locationCard);
        this.description = itemView.findViewById(R.id.descriptionCard);
        this.profileImage = itemView.findViewById(R.id.imageCard);
        this.phoneNumber = itemView.findViewById(R.id.phoneCard);

    }
}
}
