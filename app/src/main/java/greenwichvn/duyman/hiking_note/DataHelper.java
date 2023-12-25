package greenwichvn.duyman.hiking_note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DATABASE_NAME = "Trip.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "trip";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "trip_name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_FROM = "start_from";


    public DataHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_FROM + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTrip(String trip_name, String description, String location, String startFrom){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, trip_name);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_FROM, startFrom);

        db.insert(TABLE_NAME, null, cv);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    //Cursor addAll
}
