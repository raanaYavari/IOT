package com.raana.iot.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.raana.iot.R;
import com.raana.iot.data.model.BusStation;
import com.raana.iot.data.model.Driver;
import com.raana.iot.ui.base.BaseActivity;

import org.neshan.core.Bounds;
import org.neshan.core.LngLat;
import org.neshan.core.Variant;
import org.neshan.layers.VectorElementEventListener;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.AnimationStyle;
import org.neshan.styles.AnimationStyleBuilder;
import org.neshan.styles.AnimationType;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.ElementClickData;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.logs_rv)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheetLayout;
    @BindView(R.id.bus_station_name)
    AppCompatTextView busStationName;
    @BindView(R.id.current_location_layout)
    RelativeLayout currentLocationLayout;

    MainPresenter<MainView> presenter = new MainPresenter<>();
    final int REQUEST_CODE = 123;
    private MapView map;
    private Context context;
    private BusLineAdapter adapter;
    private BottomSheetBehavior bottomSheetBehavior;

    Location userLocation;
    LatLng currentLocation;
    FusedLocationProviderClient fusedLocationClient;
    VectorElementLayer userMarkerLayer;
    VectorElementLayer markerLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        context = this.getContext();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        map = findViewById(R.id.map);
        //set map focus position
        LngLat focalPoint = new LngLat(53.529929, 35.164676);
        map.setFocalPointPosition(focalPoint, 0f);
        map.setZoom(14f, 1f);
        //add basemap layer
        map.getLayers().add(NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        initMap();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocationPermission();
    }

    private void initMap() {
        /* getLayers().insert()
         when you insert a layer in index i, index (i - 1) should exist
         keep base map layer at index 0
         ********
         getLayers().add()
         suppose map has k layers right now, new layer adds in index (k + 1)
         */
        userMarkerLayer = NeshanServices.createVectorElementLayer();
        markerLayer = NeshanServices.createVectorElementLayer();

        map.getLayers().add(userMarkerLayer);
        map.getLayers().add(markerLayer);


        // add Standard_day map to layers

        //set map focus position
        map.setFocalPointPosition(new LngLat(53.529929, 35.164676), 0f);
        map.setZoom(4.5f, 0f);

        //Iran Bound
        map.getOptions().setPanBounds(new Bounds(
                new LngLat(43.505859, 24.647017),
                new LngLat(63.984375, 40.178873))
        );
        getLastLocation();
        currentLocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });
        presenter.getBusStations();

    }

    private boolean getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, REQUEST_CODE);
                return false;
            }
        }
        return true;
    }


    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        fusedLocationClient
                .getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            onLocationChange(task.getResult());
                            Log.i("TAG", "lat " + task.getResult().getLatitude() + " lng " + task.getResult().getLongitude());
                        } else {
                            Toast.makeText(MainActivity.this, "موقعیت یافت نشد.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onLocationChange(Location location) {
        this.userLocation = location;
//        addUserMarker(new LngLat(userLocation.getLongitude(), userLocation.getLatitude()));
        map.setFocalPointPosition( 
                new LngLat(userLocation.getLongitude(), userLocation.getLatitude()),
                0.25f);
        map.setZoom(15, 0.25f);
    }

    private void addUserMarker(LngLat loc) {
        userMarkerLayer.clear();

        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
        animStBl.setPhaseInDuration(0.5f);
        animStBl.setPhaseOutDuration(0.5f);
        AnimationStyle animSt = animStBl.buildStyle();

        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(20f);
//        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        markStCr.setAnimationStyle(animSt);
        MarkerStyle markSt = markStCr.buildStyle();

        Marker marker = new Marker(loc, markSt);

        userMarkerLayer.add(marker);
    }

    @Override
    public void initWorkPlaceItem(List<BusStation> myBusStationList) {
        for (final BusStation busStation : myBusStationList) {
            View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_place, null);
            TextView placeNameMarker = marker.findViewById(R.id.marker_place_name);
            placeNameMarker.setText(busStation.getName());

            AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
            animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
            animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
            animStBl.setPhaseInDuration(0.5f);
            animStBl.setPhaseOutDuration(0.5f);
            AnimationStyle animSt = animStBl.buildStyle();

            MarkerStyleCreator markStCr = new MarkerStyleCreator();
            markStCr.setSize(200f);
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(createBitmapFromView(context, marker)));
            markStCr.setAnimationStyle(animSt);
            MarkerStyle markSt = markStCr.buildStyle();

            // Creating marker
            Marker place = new Marker(new LngLat(busStation.getLocation().getCoordinates().get(1), busStation.getLocation().getCoordinates().get(0)), markSt);
            place.setMetaDataElement("id", new Variant(busStation.getId()));
            place.setMetaDataElement("lat", new Variant(busStation.getLocation().getCoordinates().get(0)));
            place.setMetaDataElement("lng", new Variant(busStation.getLocation().getCoordinates().get(1)));
            place.setMetaDataElement("name", new Variant(busStation.getName()));
            // Adding marker to markerLayer, or showing marker on map!
            markerLayer.add(place);

        }

        markerLayer.setVectorElementEventListener(
                new VectorElementEventListener() {
                    @Override
                    public boolean onVectorElementClicked(final ElementClickData clickInfo) {

                        currentLocation = new LatLng(clickInfo.getVectorElement().getMetaDataElement("lat").getDouble(), clickInfo.getVectorElement().getMetaDataElement("lng").getDouble());
                        busStationName.setText(clickInfo.getVectorElement().getMetaDataElement("name").getString());
                        presenter.getDivers(clickInfo.getVectorElement().getMetaDataElement("id").getString());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context, clickInfo.getVectorElement().getMetaDataElement("id").getString(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        return true;
                    }

                }
        );

    }

    @Override
    public void initDrivers(List<Driver> drivers) {
        adapter = new BusLineAdapter(context, drivers, currentLocation);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    private Bitmap createBitmapFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


}