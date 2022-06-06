package com.cookandroid.smu_path;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {

    protected static final String TAG = "DataAdapter";

    // TODO : TABLE 이름을 명시해야함
    protected static final String TABLE_NAME = "Schedule";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public List getTableData()
    {
        try
        {
            String sql ="SELECT * FROM " + TABLE_NAME;


            // Table 이름
//            String sql ="SELECT * FROM Schedule WHERE Academic_num =" + search +
//                    "OR Lecture ="+ search +
//                    "OR School_year ="+ search +
//                    "OR Professor ="+ search +
//                    "OR Department ="+ search +
//                    "OR Time ="+ search +
//                    "OR Lecture_room ="+ search;

            // 모델 넣을 리스트 생성
            List userList = new ArrayList();

            // TODO : 모델 선언
            Schedule_data scheduleData = null;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    scheduleData = new Schedule_data();

                    // TODO : Record 기술
                    // id, name, account, privateKey, secretKey, Comment
                    scheduleData.setLecture(mCur.getString(0));
                    scheduleData.setProfessor(mCur.getString(1));
                    scheduleData.setSchool_year(mCur.getString(2));
                    scheduleData.setTime(mCur.getString(3));
                    scheduleData.setDepartment(mCur.getString(4));
                    scheduleData.setAcademic_num(mCur.getString(5));
                    scheduleData.setLecture_room(mCur.getString(6));

                    // 리스트에 넣기
                    userList.add(scheduleData);
                }

            }
            return userList;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }




}
