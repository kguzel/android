package com.example.mihribanguzel.uygulamal8_160915054;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button button = (Button) findViewById(R.id.drawLineButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               drawLine();
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            }

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub

                        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);
                    }
                });
    }



    private void drawLine(){

        EditText lat1=(EditText)findViewById(R.id.lat1);
        EditText long1=(EditText)findViewById(R.id.long1);

        EditText lat2=(EditText)findViewById(R.id.lat2);
        EditText long2=(EditText)findViewById(R.id.long2);

        LatLng xLocation = new LatLng( Double.parseDouble(lat1.getText().toString()), Double.parseDouble(long1.getText().toString()));
        mMap.addMarker(new MarkerOptions().position(xLocation).title("ilk nokta"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(xLocation,6));

        LatLng x2Location = new LatLng( Double.parseDouble(lat2.getText().toString()), Double.parseDouble(long2.getText().toString()));
        mMap.addMarker(new MarkerOptions().position(x2Location).title("ikinci nokta"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(x2Location,6));



        // Polylines are useful for marking paths and routes on the map.
        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(Double.parseDouble(lat1.getText().toString()), Double.parseDouble(long1.getText().toString())))  // Sydney
                .add(new LatLng(Double.parseDouble(lat2.getText().toString()), Double.parseDouble(long2.getText().toString())))
        );

    }


}
