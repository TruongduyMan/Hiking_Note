package greenwichvn.duyman.hiking_note;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataAnnotation {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trip trips);

    @Query("SELECT * FROM trips ORDER BY id DESC")
    List<Trip> getAll();

    @Query("UPDATE trips SET title = :title, description = :description, location = :location, startFrom = :startFrom WHERE id = :id")
    public void updateTrips(int id, String title, String description, String location, String startFrom);
    @Delete
    public void deleteTrips(Trip trips);
    @Query("UPDATE trips SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);
}
