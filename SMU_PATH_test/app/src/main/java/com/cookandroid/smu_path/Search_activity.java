package com.cookandroid.smu_path;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Search_activity extends AppCompatActivity {
    private EditText search_icon;
    List<Schedule_data> schedulelist = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, schedulelist);
//        jsonParsing(getJsonString());
        ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("가");
        arraylist.add("나");
        arraylist.add("다");
        arraylist.add("라");

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);

        ListView list = (ListView)findViewById(R.id.list_View);
        list.setAdapter(Adapter);
        출처: https://seinarin.tistory.com/32 [행복을 찾아서:티스토리]
        search_icon = (EditText) findViewById(R.id.search_icon);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        listView = findViewById(R.id.list_View);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        search_icon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = search_icon.getText().toString();
                search(text);
            }
        });

    }
    // 검색을 수행하는 메소드
    public void search(String charText) {

        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home: //툴바 뒤로가기버튼 눌렸을 때 동작
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    // Json 파일을 읽어와 파일 내용을 String 변수에 담아 return 하는 getJsonString() 함수입니다.
    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("Schedule.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    private void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray scheduleArray = jsonObject.getJSONArray("Schedule");

            for(int i=0; i<scheduleArray.length(); i++)
            {
                JSONObject scheduleObject = scheduleArray.getJSONObject(i);

                Schedule_data schedule = new Schedule_data();

                schedule.setLecture(scheduleObject.getString("Lecture"));
                schedule.setAcademic_num(scheduleObject.getString("Academic_num"));
                schedule.setDepartment(scheduleObject.getString("Department"));
                schedule.setProfessor(scheduleObject.getString("Professor"));
                schedule.setSchool_year(scheduleObject.getString("School_year"));
                schedule.setTime(scheduleObject.getString("Time"));
                schedule.setLecture_room(scheduleObject.getString("Lecture_room"));

                schedulelist.add(schedule);
//                listView.setAdapter(adapter);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

