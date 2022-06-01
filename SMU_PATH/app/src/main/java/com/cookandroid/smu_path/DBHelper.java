package com.cookandroid.smu_path;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG ="DBHelper";
    private static String databasePath = "";
    final static String databaseName = "Data_Schedule.db";  //DB이름
    final static int DB_VERSION = 1; //DB버전
    private SQLiteDatabase mDatabase;
    private Context mContext;

    // 데이터베이스 파일 열기
    public boolean OpenDatabaseFile() throws SQLException {

        if(!CheckDatabaseFileExist()){
            CreateDatabase();
        }

        String mPath = databasePath + databaseName;
        try{
            mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        catch(SQLException sqlException){
            Log.e(TAG, "[ERROR]" + "Can't Open Database");
        }
        return mDatabase != null;
    }

    // 데이터베이스 파일 존재 여부 확인
    public boolean CheckDatabaseFileExist(){
        File file = new File(databasePath + databaseName);
        return file.exists();
    }

    // Database 만들기
    public void CreateDatabase() throws SQLException{

        this.getReadableDatabase();
        this.close();

        try{
            CopyDatabaseFile();
            Log.e(TAG,  "[SUCCESS] " + databaseName + " are Created");
        }
        catch(IOException ioException){
            // Error Message
            Log.e(TAG, "[ERROR] " + "Unable to create " + databaseName);
            throw new Error(TAG);
        }
    }

    // 데이터베이스 복사
    public void CopyDatabaseFile() throws IOException{

        InputStream inputStream  = mContext.getAssets().open(databaseName);
        String outputFileName = databasePath + databaseName;
        OutputStream outputStream = new FileOutputStream(outputFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public List getTableData() {

        try{
            // 테이블 정보를 저장할 List
            List mList = new ArrayList();

            // 쿼리
            String sql = "SELECT * FROM " + databaseName;

            // 테이블 데이터를 읽기 위한 Cursor
            Cursor mCursor = mDatabase.rawQuery(sql, null);

            // 테이블 끝까지 읽기
            if (mCursor != null){

                // 다음 Row로 이동
                while(mCursor.moveToNext()){

                    // 해당 Row 저장
                    ListViewAdapterData dataView = new ListViewAdapterData();

                    dataView.setDepartment(mCursor.getString(0));
                    dataView.setLecture_room(mCursor.getString(1));
                    dataView.setTime(mCursor.getString(2));
                    dataView.setProfessor(mCursor.getString(3));
                    dataView.setSchool_year(mCursor.getString(4));
                    dataView.setAcademic_num(mCursor.getString(5));
                    dataView.setLecture(mCursor.getString(6));

                    // List에 해당 Row 추가
                    mList.add(dataView);
                }

            }
            return mList;

        }
        catch (SQLException mSQLException){
            // Error Message
            Log.e(TAG, mSQLException.toString());
            throw mSQLException;
        }

    }

    //생성자
    public DBHelper(@Nullable Context context) {
        super(context, databaseName,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        //테이블의 구조는 여기서 설계
//        String qry = "CREATE TABLE student(num INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL)";
//        sqLiteDatabase.execSQL(qry);
//        qry = "INSERT INTO student(name) VALUES('황수고')";
//        sqLiteDatabase.execSQL(qry);
//        qry = "INSERT INTO student(name) VALUES('김영우')";
//        sqLiteDatabase.execSQL(qry);
//        qry = "INSERT INTO student(name) VALUES('이샹슈')";
//        sqLiteDatabase.execSQL(qry);


    }


    //버전 업데이트 될때마다 호출 되는데 마지막에 onCreate도 같이 실행되기 때문에 여기서 먼저 DB에 존재하는 테이블들을 지워줘야함.
    //한마디로 초기화역할
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry = "DROP TABLE IF EXISTS Data_Schedule";
        sqLiteDatabase.execSQL(qry);

        onCreate(sqLiteDatabase);

    }
}