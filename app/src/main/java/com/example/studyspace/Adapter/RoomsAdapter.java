package com.example.studyspace.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studyspace.R;
import com.example.studyspace.TimeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
    private final List<String> rooms;
    private final Context context;
    private final String building;
    private int userId;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName;

        public ViewHolder(View view) {
            super(view);
            roomName = view.findViewById(R.id.room_name);
        }
    }

    public RoomsAdapter(List<String> rooms, Context context, String building, int userId) {
        this.rooms = rooms;
        this.context = context;
        this.building = building;
        this.userId = userId;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.roomName.setText(rooms.get(position));

        holder.itemView.setOnClickListener(view -> {
            // When a room is clicked, go to the TimeActivity
            Intent intent = new Intent(context, TimeActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("room", rooms.get(position));
            intent.putExtra("building", building);
            Log.d("RoomAdapter", building + ": " + rooms.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}