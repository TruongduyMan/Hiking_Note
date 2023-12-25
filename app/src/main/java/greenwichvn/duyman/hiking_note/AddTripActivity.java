package greenwichvn.duyman.hiking_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {
    private EditText edtTitle, edtDescription, edtLocation, edtStartFrom;
    private ImageView imgSave;
    Trip trips;
    boolean isOldTripNote = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initViews();

        trips = new Trip();
        try{
            trips = (Trip) getIntent().getSerializableExtra("old_trip");
            edtTitle.setText(trips.getTitle());
            edtDescription.setText(trips.getDescription());
            edtStartFrom.setText(trips.getStartFrom());
            edtLocation.setText(trips.getLocation());
            isOldTripNote = true;
        }catch(Exception ex){
            ex.printStackTrace();
        }

/*
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper data = new DataHelper(AddTripActivity.this);
                data.addTrip(edtTitle.getText().toString().trim(),
                        edtDescription.getText().toString().trim(),
                        edtLocation.getText().toString().trim(),
                        edtStartFrom.getText().toString().trim());
            }
        });
*/

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String description = edtDescription.getText().toString();
                String location = edtLocation.getText().toString();
                String startFrom = edtStartFrom.getText().toString();

                if (description.isEmpty()){
                    Toast.makeText(AddTripActivity.this,
                            "This note must have description", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if(!isOldTripNote){
                    trips = new Trip();
                }
                trips.setTitle(title);
                trips.setDescription(description);
                trips.setLocation(location);
                trips.setStartFrom(startFrom);
                trips.setDate(dateFormat.format(date));

                Intent intent = new Intent();
                intent.putExtra("trip", trips);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initViews() {
        edtTitle = findViewById(R.id.edtTripTitle);
        edtLocation = findViewById(R.id.edtLocation);
        edtStartFrom = findViewById(R.id.edtStartFrom);
        edtDescription = findViewById(R.id.edtTripDescription);
        imgSave = findViewById(R.id.imgViewSave);
    }
}