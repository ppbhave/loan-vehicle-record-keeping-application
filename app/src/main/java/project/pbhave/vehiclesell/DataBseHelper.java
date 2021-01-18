package project.pbhave.vehiclesell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBseHelper extends SQLiteOpenHelper{

    private static final String Dbname="task1.db";
    private static final String tbl="TestTable";
    private static final String col0="ID";
    private static final String col1="UserName";
    private static final String col2="NOplate";
    private static final String col3="kilometers";
    private static final String col4="Liters";
    private static final String col5="Pump";
    private static final String col6="directory";
    private static final String col7="name";


    public DataBseHelper(Context context) {
        super(context,Dbname,null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE if not exists " + tbl + "("+"ID "+"INTEGER PRIMARY KEY AUTOINCREMENT,"+" UserName "+"TEXT,"+" NOplate "+"TEXT," + "kilometers "+ "INTEGER,"+" Liters "+"INTEGER,"+" Pump "+"TEXT,"+" directory "+"TEXT,"+" name "+"TEXT"+")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+tbl);
    }

    public long insertData(String user, String NOplate, String kms, String liters, String pump, String directory, String name)
    throws SQLException{
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        // cv.put(col1,id);
         cv.put(col1,user);
        cv.put(col2,NOplate);
        cv.put(col3,kms);
        cv.put(col4,liters);
        cv.put(col5,pump);
        cv.put(col6,directory);
        cv.put(col7,name);

        long result=db.insert(tbl,null,cv);
        db.close();
        return  result;
    }



    public Cursor getID() {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select max(ID) from "+tbl,null);
    }
    public Cursor Retrive(){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select * from "+tbl,null);
    }
    public Cursor RetriveForImage(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] sargs={id+""};
        return db.query(tbl,new String[]{col6,col7},col0 + " =?",sargs,null,null,null,null);
    }
}

