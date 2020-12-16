package com.raana.iot.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.raana.iot.R;
import com.raana.iot.data.AppDataManager;
import com.raana.iot.data.model.DistanceMatrix;
import com.raana.iot.data.model.Driver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusLineAdapter extends RecyclerView.Adapter<BusLineAdapter.MyHolder> {

    private Context context;
    private List<Driver> drivers;
    private LatLng currentLocation;

    public BusLineAdapter(Context context, List<Driver> drivers, LatLng currentLocation) {
        this.context = context;
        this.drivers = drivers;
        this.currentLocation = currentLocation;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_line_item, parent, false);
        return new BusLineAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.desc.setText(drivers.get(position).getBusLine().getName());
        holder.name.setText(drivers.get(position).getBusLine().getNumberId());
        calculateDistance(currentLocation.latitude+","+currentLocation.longitude, drivers.get(position).getLocation().getCoordinates().get(0) + ","+
                drivers.get(position).getLocation().getCoordinates().get(1),holder.time);
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time)
        AppCompatTextView time;
        @BindView(R.id.bus_name)
        AppCompatTextView name;
        @BindView(R.id.bus_desc)
        AppCompatTextView desc;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void calculateDistance(String origin, String destination, final AppCompatTextView timeTxt) {
        AppDataManager.getInstance(context).distanceApi(origin, destination, new Callback<DistanceMatrix>() {
            @Override
            public void onResponse(Call<DistanceMatrix> call, Response<DistanceMatrix> response) {
                timeTxt.setText(response.body().getRows().get(0).getElements().get(0).getDuration().getText());
            }

            @Override
            public void onFailure(Call<DistanceMatrix> call, Throwable t) {

            }
        });
    }


}
