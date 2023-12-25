package greenwichvn.duyman.hiking_note;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private SearchView searchView;
    RecyclerView recHome;
    TripsListAdapter adapter;
    List<Trip> trips = new ArrayList<>();
    // DataHelper dataHelper;
    RoomDB database;
    Trip selectedTrip;
    private FloatingActionButton floatingAddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        database = RoomDB.getInstance(this);
        trips = database.mainDAO().getAll();

        updateRecycler(trips);

        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String newText) {
                List<Trip> filteredTripsList = new ArrayList<>();
                for (Trip singleTrip: trips){
                    if(singleTrip.getTitle().toLowerCase().contains(newText.toLowerCase())
                            || singleTrip.getDescription().toLowerCase().contains(newText.toLowerCase())
                            || singleTrip.getLocation().toLowerCase().contains(newText.toLowerCase())
                            || singleTrip.getStartFrom().toLowerCase().contains(newText.toLowerCase())){

                        filteredTripsList.add(singleTrip);
                    }
                }
                adapter.filterTripsList(filteredTripsList);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Trip new_Trips = null;
                if (data != null) {
                    new_Trips = (Trip) data.getSerializableExtra("trip");
                }
                database.mainDAO().insert(new_Trips);
                trips.clear();
                trips.addAll(database.mainDAO().getAll());
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == 102) {
            if(resultCode == Activity.RESULT_OK){
                Trip newTrips = null;
                if (data != null) {
                    newTrips = (Trip) data.getSerializableExtra("trip");
                }
                database.mainDAO().updateTrips(newTrips.getId(),
                        newTrips.getTitle(), newTrips.getDescription(),
                        newTrips.getLocation(), newTrips.getStartFrom());
                trips.clear();
                trips.addAll(database.mainDAO().getAll());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Trip> trips) {
        recHome.setHasFixedSize(true);
        recHome.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new TripsListAdapter(MainActivity.this, trips, tripsClickListener);
        recHome.setAdapter(adapter);
    }

    private final TripsClickListener tripsClickListener = new TripsClickListener() {
        @Override
        public void onClicked(Trip trips) {
            Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
            intent.putExtra("old_trip", trips);

            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClicked(Trip trips, CardView cardView) {
           selectedTrip = new Trip();
           selectedTrip = trips;
           showPopup(cardView);
        }
    };
    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    private void initViews() {
        searchView = findViewById(R.id.searchView_Home);
        recHome = findViewById(R.id.recHome);
        floatingAddButton = findViewById(R.id.btnFloatAdd);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pin) {
            if(selectedTrip.isPinned()){
                database.mainDAO().pin(selectedTrip.getId(), false);
                Toast.makeText(MainActivity.this, "Unpinned", Toast.LENGTH_SHORT).show();
            }else{
                database.mainDAO().pin(selectedTrip.getId(), true);
                Toast.makeText(MainActivity.this, "Pinned", Toast.LENGTH_SHORT).show();
            }
            trips.clear();
            trips.addAll(database.mainDAO().getAll());
            adapter.notifyDataSetChanged();

            return true;
        } else if (item.getItemId() == R.id.delete) {
            database.mainDAO().deleteTrips(selectedTrip);
            trips.remove(selectedTrip);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Trip deleted", Toast.LENGTH_SHORT).show();

            return true;

        }else {
            return false;
        }
    }
}