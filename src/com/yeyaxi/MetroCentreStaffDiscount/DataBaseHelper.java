package com.yeyaxi.MetroCentreStaffDiscount;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Database handler
 * 
 * @author Yaxi Ye
 * 
 * @see Reference: Using your own SQLite database in Android applications 
 * (Link: http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/)
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper{
	
	//set default path to database
	private static String DB_PATH = "/data/data/com.yeyaxi.MetroCentreStaffDiscount/databases/";
	private static String DB_NAME = "metro_staff_discount.sqlite";
	private static final String DB_TABLE_NAME = "fts_discount_info";
	// Only display shop name and shop info, the discount value is for other purposes.
	private static final String[] DB_COLUMN_NAME = {"ShopName", "Note"};
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	private static final int DB_VERSION = 3;
	private static final String TAG = "Database";

	/**
	 * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.myContext = context;
		// TODO Auto-generated constructor stub
	}
	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
    
    public void openDataBase() throws SQLException{
    	 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
    
//    /**
//     * getShopInfo
//     * @param name
//     * @return shopInfo
//     */
//    public Merchant getShopInfo(String name) {
//    	SQLiteDatabase db = this.getReadableDatabase();
//    	
//    	// Database query: SELECT * FROM fts_discount_info WHERE ShopName MATCH 'name OR *name OR name*';
//    	
//    	Cursor cursor = db.query(DB_TABLE_NAME, null, "ShopName MATCH " + "'" + name + " OR " + "*" + name + " OR " + name + "*'", null, null, null, null);
//
//    	if(cursor.moveToFirst()) {
//
//    		Merchant shopInfo = new Merchant(cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
//
//    		return shopInfo;
//
//    	}else{
//    		// If neither FTS nor MATCH * search return a valid result, return Shop not found.
//    		return new Merchant("Shop Not Found",0.0,"");
//
//    	}
//
//    }
    
    public String getDBTableName() {
    	return DB_TABLE_NAME;
    }
    
    public String[] getDBColumnNames() {
    	
    	return DB_COLUMN_NAME;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		newVersion = DB_VERSION;
		
		if (newVersion > oldVersion) {
			
			try {
				
				Log.d(TAG, "Upgrading Database...");
				copyDataBase();
				Log.d(TAG, "Database Updated.");
				
			} catch (IOException e) {
				
				Log.e(TAG, "Database copy error.");
				
			}
		}
		
	}

}
