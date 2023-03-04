package pe.com.gmdsa.sedapal.util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseManager extends SQLiteOpenHelper {

    private static DatabaseManager instance;
    private static final String DATABASE_NAME = "androidapp.sqlite";
    private static final int DATABASE_VERSION = 1;
    private String DB_PATH;
    private String DB_PATH_EXPORT;
    private Context mContext;
    private SQLiteDatabase dataBase;


    public static synchronized DatabaseManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new DatabaseManager(ctx);
        }

        return instance;
    }

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        DB_PATH_EXPORT = Environment.getExternalStorageDirectory() + "/";

        // for production
        DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/";

        // use only for tests purpose, Assets
		/*
		DB_PATH = Environment.getExternalStorageDirectory() + "/";
		configForAssetsDatabase();
		*/
    }

    public void configForAssetsDatabase() {
        if (!isDataBaseExist()) {
            try {
                InputStream input = mContext.getAssets().open(DATABASE_NAME);
                String outFileName = DB_PATH + DATABASE_NAME;
                OutputStream output = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = input.read(buffer)) > 0)
                    output.write(buffer, 0, length);

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDataBaseExist() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    public void removeDatabase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        dbFile.delete();
    }

    public static int getVersion(Context ctx) {
        DatabaseManager db = new DatabaseManager(ctx);
        db.openDataBase();
        db.getReadableDatabase();
        int version = db.getDataBase().getVersion();
        db.close();
        return version;
    }

    public SQLiteDatabase getDataBase() {
        return dataBase;
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        dataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }

    public void beginTransaction() {
        dataBase.beginTransaction();
    }

    public void setTransactionSuccessful() {
        dataBase.setTransactionSuccessful();
    }

    public void endTransaction() {
        dataBase.endTransaction();
    }

    @Override
    public synchronized void close() {
        if (dataBase != null)
            dataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TableSQL.CREATE.PARAMETRIC_TABLE);
        db.execSQL(TableSQL.CREATE.DEVICE_TABLE);
        db.execSQL(TableSQL.CREATE.USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub

        db.execSQL(TableSQL.DROP.USER_TABLE);

        onCreate(db);
    }

}