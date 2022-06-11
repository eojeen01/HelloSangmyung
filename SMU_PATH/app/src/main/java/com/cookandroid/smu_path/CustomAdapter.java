package com.cookandroid.smu_path;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Schedule_data> arrayList;
    private Context context;
    private ArrayList<String> lecList;

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
        lecList = new ArrayList<>();
        if(arrayList.get(position).getLecture_room().contains("N")){
            lecList.add("식물과학관");
        }
        else if(arrayList.get(position).getLecture_room().contains("C")){
            lecList.add("본관");
        }
        else if(arrayList.get(position).getLecture_room().contains("I")){
            lecList.add("한누리관");
        }
        else if(arrayList.get(position).getLecture_room().contains("E")){
            lecList.add("송백관");
        }
        else if(arrayList.get(position).getLecture_room().contains("F")){
            lecList.add("종합실기관");
        }
        else if(arrayList.get(position).getLecture_room().contains("D")){
            lecList.add("디자인관");
        }
        else if(arrayList.get(position).getLecture_room().contains("K")){
            lecList.add("학생회관");
        }
        else if(arrayList.get(position).getLecture_room().contains("B")){
            lecList.add("상록관");
        }
        else if(arrayList.get(position).getLecture_room().contains("A")){
            lecList.add("청록관");
        }
        else if(arrayList.get(position).getLecture_room().contains("S")){
            lecList.add("계당관");
        }
        else if(arrayList.get(position).getLecture_room().contains("K")){
            lecList.add("스포츠센터");
        }
        else if(arrayList.get(position).getLecture_room().contains("Z")){
            lecList.add("사이버강의");
        }

        holder.tv_academic_num.setText("학수번호 : "+arrayList.get(position).getAcademic_num());
        holder.tv_department.setText("학과 : "+arrayList.get(position).getDepartment());
        holder.tv_professor.setText("교수님 : "+arrayList.get(position).getProfessor());
        holder.tv_lecture.setText("강의명 : "+arrayList.get(position).getLecture());
        holder.tv_time.setText("강의시간 : "+arrayList.get(position).getTime());
        holder.tv_lecture_room.setText("강의실 : "+arrayList.get(position).getLecture_room()+lecList);
        holder.tv_school_year.setText("학년 : "+arrayList.get(position).getSchool_year());

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
