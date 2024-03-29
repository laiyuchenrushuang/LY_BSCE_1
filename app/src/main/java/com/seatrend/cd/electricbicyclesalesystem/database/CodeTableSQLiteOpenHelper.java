package com.seatrend.cd.electricbicyclesalesystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seatrend.cd.electricbicyclesalesystem.common.MyApplication;


public class CodeTableSQLiteOpenHelper extends SQLiteOpenHelper{

    private static String DB_NAME="codeTable.db";
    public static String TABLE_NAME="CODE_TABLE";
    private  static int VERSION=1;
    /**
     * "xtlb": "00",
     "dmlb": "1007",
     "dmz": "01",
     "mldh": "01",
     "dmsm1": "大型汽车",
     "mlmc": "大型汽车",
     "dmsm2": "黄底黑字",
     "dmsm3": null,
     "zt": "1",
     "dmsm4": null
     */
    private static String CREATE_TABLE_SQL="create table %s(id integer primary key autoincrement,xtlb varchar(10),dmlb varchar(10),dmz varchar(10)" +
            ",dmsm1 varchar(100),mlmc varchar(100),dmsm2 varchar(100),dmsm3 varchar(100),zt varchar(10),dmsm4 varchar(100))";
    private static String CAREATE_TABLE =String.format(CREATE_TABLE_SQL,TABLE_NAME);
    private static CodeTableSQLiteOpenHelper helper=null;
    public CodeTableSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    public static CodeTableSQLiteOpenHelper getInstance(){
        if(helper == null){
            helper=new CodeTableSQLiteOpenHelper(MyApplication.Companion.getMyApplicationContext());

        }

        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CAREATE_TABLE);
        //Log.i("kkkkk","数据库已创建");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
