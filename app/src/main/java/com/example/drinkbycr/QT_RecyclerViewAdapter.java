package com.example.drinkbycr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QT_RecyclerViewAdapter extends RecyclerView.Adapter<QT_RecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<String> questions;


    public QT_RecyclerViewAdapter(Context context, ArrayList<String> questions, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.questions = questions;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public QT_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new QT_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull QT_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.rvQuestion.setText(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rvQuestion;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            rvQuestion =itemView.findViewById(R.id.rvQuestion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
