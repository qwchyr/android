package cn.itcast.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.notepad.bean.NotepadBean;
import cn.itcast.notepad.utils.DBUtils;

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatebase;
    //创建数据库
    public SQLiteHelper(Context context){
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERION);
        sqLiteDatebase = this.getWritableDatabase();
    }
    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DBUtils.DATABASE_TABLE
                    +"("+DBUtils.NOTEPAD_ID
                    +" integer primary key autoincrement,"
                    + DBUtils.NOTEPAD_CONTENT
                    +" text," + DBUtils.NOTEPAD_TIME+ " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //添加数据
    public boolean insertDate(String userContent,String userTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME,userTime);
        return sqLiteDatebase.insert(DBUtils.DATABASE_TABLE,null,contentValues) > 0;
    }
    //删除数据
    public boolean deleteData(String id){
        String sql = DBUtils.NOTEPAD_ID + "=?";
        String [] contentValuseArray = new String[] {String.valueOf(id)};
        return
          sqLiteDatebase.delete(DBUtils.DATABASE_TABLE,sql,contentValuseArray) > 0;
    }
    //修改数据
    public boolean updateData(String id, String content, String userYear){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,content);
        contentValues.put(DBUtils.NOTEPAD_TIME,userYear);
        String sql = DBUtils.NOTEPAD_ID + "=?";
        String [] strings = new String[]{id};
        return
          sqLiteDatebase.update(DBUtils.DATABASE_TABLE,contentValues,sql,strings) > 0;
    }
    //查询数据
    public List<NotepadBean> query(){
        List<NotepadBean> list = new ArrayList<NotepadBean>();
        Cursor cursor = sqLiteDatebase.query(DBUtils.DATABASE_TABLE,null,null,null,null,null,DBUtils.NOTEPAD_ID+" desc");
        if (cursor != null){
            while (cursor.moveToNext()){
                NotepadBean noteInfo = new NotepadBean();
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBUtils.NOTEPAD_ID)));
                String content = cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_CONTENT));
                String time = cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_TIME));
                noteInfo.setId(id);
                noteInfo.setNotepadContent(content);
                noteInfo.setNotepadTime(time);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }
}
