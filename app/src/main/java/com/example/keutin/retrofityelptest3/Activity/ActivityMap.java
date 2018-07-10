package com.example.keutin.retrofityelptest3.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.keutin.retrofityelptest3.BuildConfig;
import com.example.keutin.retrofityelptest3.Model.Business;
import com.example.keutin.retrofityelptest3.Model.Category;
import com.example.keutin.retrofityelptest3.Model.YelpAnswersResponse;
import com.example.keutin.retrofityelptest3.Network.GetDataService;
import com.example.keutin.retrofityelptest3.Network.RetrofitClientInstance;
import com.example.keutin.retrofityelptest3.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMap extends AppCompatActivity {
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, BuildConfig.ApiKeyMap);
        setContentView(R.layout.activity_map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        final ProgressDialog progressDialog = new ProgressDialog(ActivityMap.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<YelpAnswersResponse> call = service.getBusinessData("Bearer " + BuildConfig.ApiKey, "Bars", "Bordeaux", "50");

        call.enqueue(new Callback<YelpAnswersResponse>() {
            @Override
            public void onResponse(Call<YelpAnswersResponse> call, Response<YelpAnswersResponse> response) {
                progressDialog.dismiss();
                generateDataList(response.body().getBusinesses());
            }

            @Override
            public void onFailure(Call<YelpAnswersResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityMap.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void generateDataList(final List<Business> Response) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {


                //Cleaning the map if it had anything on it before
                mapboxMap.removeAnnotations();

                //Creating a list of markers
                List<MarkerOptions> markers = new ArrayList<>();
                Double latitude;
                Double longitude;
                //Filling up the list
                for (Business Business: Response) {
                    latitude = Business.getCoordinates().getLatitude();
                    longitude = Business.getCoordinates().getLongitude();
                    markers.add(new MarkerOptions()
                            .title(Business.getName())
                            .position(new LatLng(latitude,longitude))

                    );
                }

                //Adding all the markers to the map
                mapboxMap.addMarkers(markers);

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
