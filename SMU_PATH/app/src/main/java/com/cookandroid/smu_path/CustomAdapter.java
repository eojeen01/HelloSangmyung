package com.cookandroid.smu_path;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Schedule_data> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<Schedule_data> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_academic_num.setText(arrayList.get(position).getAcademic_num());
        holder.tv_department.setText(String.valueOf(arrayList.get(position).getDepartment()));
        holder.tv_professor.setText(arrayList.get(position).getProfessor());
        holder.tv_lecture.setText(arrayList.get(position).getLecture());
        holder.tv_time.setText(String.valueOf(arrayList.get(position).getTime()));
        holder.tv_lecture_room.setText(arrayList.get(position).getLecture_room());
        holder.tv_school_year.setText(arrayList.get(position).getSchool_year());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_academic_num;
        TextView tv_school_year;
        TextView tv_lecture;
        TextView tv_professor;
        TextView tv_department;
        TextView tv_lecture_room;
        TextView tv_time;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_academic_num = itemView.findViewById(R.id.tv_academic_num);
            this.tv_school_year = itemView.findViewById(R.id.tv_school_year);
            this.tv_lecture = itemView.findViewById(R.id.tv_lecture);
            this.tv_professor = itemView.findViewById(R.id.tv_professor);
            this.tv_department = itemView.findViewById(R.id.tv_department);
            this.tv_lecture_room = itemView.findViewById(R.id.tv_lecture_room);
            this.tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
