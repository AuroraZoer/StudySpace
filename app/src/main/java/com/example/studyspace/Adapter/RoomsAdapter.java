package com.example.studyspace.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyspace.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
    private List<String> rooms;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName;
        public ViewHolder(View view) {
            super(view);
            roomName = view.findViewById(R.id.room_name);
        }
    }

    public RoomsAdapter(List<String> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.roomName.setText(rooms.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 在这里处理点击事件，比如跳转到新的界面
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}