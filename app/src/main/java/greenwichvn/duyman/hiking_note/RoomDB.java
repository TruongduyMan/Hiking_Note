package greenwichvn.duyman.hiking_note;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Trip.class, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB data;
    private static String DATABASE_NAME = "TripNoteApp";

    public synchronized static RoomDB getInstance(Context context){
        if(data == null){
            data = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return data;
    }

    public abstract DataAnnotation mainDAO();
}
