package greenwichvn.duyman.hiking_note;

import androidx.cardview.widget.CardView;

public interface TripsClickListener {
    void onClicked(Trip trips);
    void onLongClicked(Trip trips, CardView cardView);
}
