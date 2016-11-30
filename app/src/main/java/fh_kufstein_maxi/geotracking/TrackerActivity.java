package fh_kufstein_maxi.geotracking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class TrackerActivity extends AppCompatActivity implements LocationListener{

    private  int PERMISSON_CALLBACK = 1000;
    protected LocationManager lm;
    File file;
    String filename = "geodata.txt";
    int counter = 0;
    public static double cod [] = new double[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        file = new File(getApplicationContext().getFilesDir(), filename);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        //if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSON_CALLBACK);
        //}

        TextView tv = (TextView) findViewById(R.id.textView2);
        int zeile = 1;
        tv.setText("");
        for (int i = 0; i < 9; i = i + 2) {
            if (cod[i] != 0) {
                tv.append("\n");
                tv.append(zeile + "  " + cod[i] + " | " + cod[i + 1]);
            }
            zeile = zeile + 1;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (PERMISSON_CALLBACK == requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        counter = counter+1;

        if(counter ==1) {
            Log.i("LOCATION", location.getLatitude() + "/" + location.getLongitude());


            double h0 = cod[0];
            double h1 = cod[1];
            double h2 = cod[2];
            double h3 = cod[3];
            double h4 = cod[4];
            double h5 = cod[5];
            double h6 = cod[6];
            double h7 = cod[7];

            cod[2] = h0;
            cod[3] = h1;
            cod[4] = h2;
            cod[5] = h3;
            cod[6] = h4;
            cod[7] = h5;
            cod[8] = h6;
            cod[9] = h7;

            cod[0] = location.getLatitude();
            cod[1] = location.getLongitude();

            TextView tv = (TextView) findViewById(R.id.textView2);
            int zeile = 1;
            tv.setText("");
            for (int i = 0; i < 9; i = i + 2) {
                if (cod[i] != 0) {
                    tv.append("\n");
                    tv.append(zeile + "  " + cod[i] + "|" + cod[i + 1]);
                }
                zeile = zeile + 1;
            }
            counter = 0;
        }

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

    public void fileOutput(View v) {
        /*
        FileOutputStream outputStream;
        for(int i = 0; i<5; i++){
        String print = cod[i];

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(print.getBytes());
                outputStream.write("\n".getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
       Intent intent = new Intent(this, StartActivity.class);
       startActivity(intent);


    }
}
