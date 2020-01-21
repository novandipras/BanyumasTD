package sonda.banyumastd;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class alamat extends Activity {

    Geocoder geocoder;
    TextView myaddress,tvLong,tvLat;
    List<Address> addresses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat);
        tvLat =findViewById(R.id.tvLat);
        tvLong =findViewById(R.id.tvLong);
        myaddress = findViewById(R.id.myaddress);

        geocoder = new Geocoder(this, Locale.getDefault());

        String tvlatcoor = tvLat.getText().toString();
        String tvlongcoor = tvLong.getText().toString();


        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(tvlatcoor), Double.parseDouble(tvlongcoor), 1);
            String address = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            String city = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalcode = addresses.get(0).getPostalCode();
            String fulladdress = address + ", " + area + ", " + city + ", " + country + ", " + postalcode;

            myaddress.setText(area);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Cannot get Address",Toast.LENGTH_SHORT).show();

        }

    }
}
