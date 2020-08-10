package com.simplicitydev.smartrailwayqr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerLocation extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_location);
        Bundle b=getIntent().getExtras();

        Button exit;

        String pnr=b.getString("pnr");
        FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseReference=mFirebaseDatabase.getReference(pnr);

        exit=findViewById(R.id.exitapp);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},0);
            return;
        }

        LocationManager lm;
        lm= (LocationManager) getSystemService(LOCATION_SERVICE);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.removeValue();
                finish();
                finishAffinity();
            }
        });

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat=location.getLatitude();
                double lol=location.getLongitude();

                String loc=lat+", "+lol;

               // String cName=b.toString();
                mDatabaseReference.setValue(loc);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
