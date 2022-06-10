package com.cookandroid.smu_path;

public class Schedule_data {
    //Json으로부터 파싱 된 영화 데이터를 담기 위한 클래스입니다.
    //getter/setter 함수를 구현하여 외부에서 각 멤버 변수에 접근 가능하도록 구현합니다.
    private String Academic_num;
    private String Lecture;
    private String School_year;
    private String Professor;
    private String Department;
    private String Time;
    private String Lecture_room;

    public Schedule_data(){}

    public String getAcademic_num() {
        return Academic_num;
    }

    public String getLecture() {
        return Lecture;
    }

    public String getSchool_year() {
        return School_year;
    }

    public String getProfessor() {
        return Professor;
    }

    public String getDepartment() {
        return Department;
    }

    public String getLecture_room() {
        return Lecture_room;
    }

    public String getTime() {
        return Time;
    }

    public void setAcademic_num(String academic_num) {
        this.Academic_num = academic_num;
    }

    public void setSchool_year(String school_year) {
        School_year = school_year;
    }

    public void setLecture(String lecture) {
        this.Lecture = lecture;
    }

    public void setProfessor(String professor) {
        this.Professor = professor;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public void setLecture_room(String lecture_room) {
        this.Lecture_room = lecture_room;
    }

    public void setTime(String time) {
        this.Time = time;
    }
}
