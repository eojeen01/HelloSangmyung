package com.cookandroid.smu_path;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.Holder>{
    ArrayList<Schedule_data> items = new ArrayList<>();

    public UserListAdapter(ArrayList<Schedule_data> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tv_academic_num.setText("학수번호 :   "+items.get(position).getAcademic_num());
        holder.tv_department.setText("학과 :   "+items.get(position).getDepartment());
        holder.tv_professor.setText("교수 :   "+items.get(position).getProfessor());
        holder.tv_lecture.setText("강의명 :   "+items.get(position).getLecture());
        holder.tv_time.setText("강의시간 :   "+items.get(position).getTime());
        holder.tv_lecture_room.setText("강의실 :   "+items.get(position).getLecture_room());
        holder.tv_school_year.setText("학년 :   "+items.get(position).getSchool_year());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_academic_num;
        TextView tv_school_year;
        TextView tv_lecture;
        TextView tv_professor;
        TextView tv_department;
        TextView tv_lecture_room;
        TextView tv_time;
        public Holder(@NonNull View itemView) {
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
