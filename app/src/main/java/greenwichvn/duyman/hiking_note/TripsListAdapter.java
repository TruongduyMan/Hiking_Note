package greenwichvn.duyman.hiking_note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TripsListAdapter extends RecyclerView.Adapter<TripsViewHolder>{
    Context context;
    List<Trip> list;
    TripsClickListener listener;

    public TripsListAdapter(Context context, List<Trip> list, TripsClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TripsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.trips_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtTitle.setSelected(true);

        holder.txtTrips.setText(list.get(position).getDescription());
        holder.txtTrips.setSelected(true);

        holder.txtLocation.setText(list.get(position).getLocation());
        holder.txtLocation.setSelected(true);

        holder.txtStartFrom.setText(list.get(position).getStartFrom());
        holder.txtStartFrom.setSelected(true);

        holder.txtDate.setText(list.get(position).getDate());
        holder.txtDate.setSelected(true);

        if(list.get(position).isPinned()){
            holder.imgPin.setImageResource(R.drawable.ic_action_pin);
        }else {
            holder.imgPin.setImageResource(0);
        }

        int color_code = getRandomColor();
        holder.tripsContainer.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.tripsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked(list.get(holder.getAdapterPosition()));
            }
        });

        holder.tripsContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClicked(list.get(holder.getAdapterPosition()), holder.tripsContainer);
                return true;
            }
        });
    }
    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blueLight);
        colorCode.add(R.color.greenLight);
        colorCode.add(R.color.greenLight1);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.pinkLight);
        colorCode.add(R.color.olu);
        colorCode.add(R.color.grayGreen);
        colorCode.add(R.color.RoyalBlue);
        colorCode.add(R.color.PictonBlue);
        colorCode.add(R.color.green);

        Random random = new Random();
        int randomColor = random.nextInt(colorCode.size());

        return colorCode.get(randomColor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterTripsList(List<Trip> filter){
        list = filter;
        notifyDataSetChanged();
    }
}

class TripsViewHolder extends RecyclerView.ViewHolder {
    CardView tripsContainer;
    TextView txtTitle, txtTrips, txtDate, txtLocation, txtStartFrom;
    ImageView imgPin;
    public TripsViewHolder(@NonNull View itemView) {
        super(itemView);

        tripsContainer = itemView.findViewById(R.id.tripsContainer);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtTrips = itemView.findViewById(R.id.txtTrips);
        txtLocation = itemView.findViewById(R.id.txtLocation);
        txtStartFrom = itemView.findViewById(R.id.txtStartFrom);
        txtDate = itemView.findViewById(R.id.txtDate);
        imgPin = itemView.findViewById(R.id.imgPin);
    }
}
