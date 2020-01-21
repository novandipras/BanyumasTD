package sonda.banyumastd;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences sharedpreferences;
    private static final int REQUEST_CALL = 1;
    EditText nopolisi, etSMS, etLOKASI;
    TextView no1, no2, no3, no4;
    public static final String mypreference = "mypref";
    public static final String NO1 = "nomor1key";
    public static final String NO2 = "nomor2key";
    public static final String NO3 = "nomor3key";
    public static final String NO4 = "nomor4key";
    LocationManager locationManager;
    String latitude, longitude;
    Geocoder geocoder;
    TextView myaddress, mylatitude, mylongitude, lokasisaya;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylatitude = findViewById(R.id.mylatitude);
        mylongitude = findViewById(R.id.mylongitude);
        lokasisaya = findViewById(R.id.lokasisaya);
        myaddress = findViewById(R.id.myaddress);
        nopolisi = findViewById(R.id.nopolisi);
        etSMS = findViewById(R.id.etSMS);
        etLOKASI = findViewById(R.id.etLOKASI);
        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);


        geocoder = new Geocoder(this, Locale.getDefault());

        ImageButton btDarurat = findViewById(R.id.btDarurat);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        //Toast.makeText(this, "Activity: onCreate()", Toast.LENGTH_SHORT).show();

        PermissionToConnect();

        btDarurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    //  Toast.makeText(this, "Access", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        alamataddress();
        //Toast.makeText(this, "Activity: onStart()", Toast.LENGTH_SHORT).show();
        no1.setText(getIntent().getStringExtra("data1"));
        no2.setText(getIntent().getStringExtra("data2"));
        no3.setText(getIntent().getStringExtra("data3"));
        no4.setText(getIntent().getStringExtra("data4"));
        ceknomor();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "Activity: onResume()", Toast.LENGTH_SHORT).show();
        setSharedpreferences();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this, "Activity: onPause()", Toast.LENGTH_SHORT).show();
        saveSharedprefences();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "Activity: onStop()", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latit = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latit);
                longitude = String.valueOf(longi);
                etLOKASI.setText("https://www.google.com/maps/dir/Current+Location/" + latitude + "," + longitude + "\n" + "TDB");
                mylatitude.setText(latitude);
                mylongitude.setText(longitude);

            } else if (location1 != null) {
                double latit = location1.getLatitude();
                double longi = location1.getLongitude();
                latitude = String.valueOf(latit);
                longitude = String.valueOf(longi);
                etLOKASI.setText("https://www.google.com/maps/dir/Current+Location/" + latitude + "," + longitude + "\n" + "TDB");
                mylatitude.setText(latitude);
                mylongitude.setText(longitude);

            } else if (location2 != null) {
                double latit = location2.getLatitude();
                double longi = location2.getLongitude();
                latitude = String.valueOf(latit);
                longitude = String.valueOf(longi);
                etLOKASI.setText("https://www.google.com/maps/dir/Current+Location/" + latitude + "," + longitude + "\n" + "TDB");
                mylatitude.setText(latitude);
                mylongitude.setText(longitude);
            } else {

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void makePhoneCall() {
        sms();
        cekarea();
        String number = nopolisi.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void cekarea() {
        if (myaddress.getText().toString().equals("Wangon")) {
            nopolisi.setText("0281 513320");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }
        //V4

//1Ajibarang
        else if (myaddress.getText().toString().equals("53163")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        }
//2Banyumas
        else if (myaddress.getText().toString().equals("'53192")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        }
//3Baturaden
        else if (myaddress.getText().toString().equals("53151")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53126")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        }
//4Cilongok
        else if (myaddress.getText().toString().equals("53162")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        }
//5Gumelar
        else if (myaddress.getText().toString().equals("53165")) {
            nopolisi.setText("0281 622259");//polresbanyumas
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        }
//6Jatilawang
        else if (myaddress.getText().toString().equals("53174")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        }
//7Kalibagor
        else if (myaddress.getText().toString().equals("53191")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        }
//8Karanglewas
        else if (myaddress.getText().toString().equals("53161")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        }
//9Kebasen
        else if (myaddress.getText().toString().equals("53172")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        }
//10Kedung Banteng
        else if (myaddress.getText().toString().equals("53152")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        }
//11Kembaran
        else if (myaddress.getText().toString().equals("53182")) {
            nopolisi.setText("0281 622259"); //polresbanyumas
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        }
//12Kemranjen
        else if (myaddress.getText().toString().equals("53194")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        }
//13Lumbir
        else if (myaddress.getText().toString().equals("53177")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        }
//14Patikraja
        else if (myaddress.getText().toString().equals("53171")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        }
//15Pekuncen
        else if (myaddress.getText().toString().equals("53164")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }
//16Purwojati
        else if (myaddress.getText().toString().equals("53175")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        }
//17Purwokerto Barat
        else if (myaddress.getText().toString().equals("53131")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53132")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53133")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53134")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53135")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53136")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53137")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        }
//18Purwokerto Selatan
        else if (myaddress.getText().toString().equals("53141")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53142")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53143")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53144")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53145")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53146")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53147")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        }
//19Purwokerto Timur
        else if (myaddress.getText().toString().equals("53111")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("53112")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("53113")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("53114")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("53115")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("53116")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }
//20Purwokerto Utara
        else if (myaddress.getText().toString().equals("53121")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53122")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53123")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53124")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53125")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53126")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("53127")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        }
//21Rawalo
        else if (myaddress.getText().toString().equals("53173")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }
//22Sokaraja
        else if (myaddress.getText().toString().equals("53181")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        }
//23Somagede
        else if (myaddress.getText().toString().equals("53193")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }
//24Sumbang
        else if (myaddress.getText().toString().equals("53195")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }
//25Sumpiuh
        else if (myaddress.getText().toString().equals("53183")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }
//26Tambak
        else if (myaddress.getText().toString().equals("53196")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        }
//27Wangon
        else if (myaddress.getText().toString().equals("53176")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }



        //V3
        //1PURWOKERTO SELATAN
        if (myaddress.getText().toString().equals("Karangpucung")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karangklesem")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karangreja")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tanjung")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Teluk")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Berkoh")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purwokerto Kidul")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purwokerto Selatan")) {
            nopolisi.setText("0281 6843835");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Selatan", Toast.LENGTH_SHORT).show();
        }
        //2PURWOKERTO UTARA
        else if (myaddress.getText().toString().equals("Bancarkembar")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Grendeng")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangwangkal")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pabuaran")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sumampir")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Bobosan")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purwanegara")) {
            nopolisi.setText("0281 639782");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Utara", Toast.LENGTH_SHORT).show();
        }
        //3PURWOKERTO BARAT
        else if (myaddress.getText().toString().equals("Kedungwuluh")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kober")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Bantarsoka")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Rejasari")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pasir kidul")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanglewas Lor")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pasir Muncang")) {
            nopolisi.setText("0281 639211");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Barat", Toast.LENGTH_SHORT).show();
        }
        //4PURWOKERTO TIMUR
        else if (myaddress.getText().toString().equals("Purwokerto Timur")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Mersi")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Arcawinangun")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purwokerto Lor")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokanegara")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kranji")) {
            nopolisi.setText("0281 635015");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwokerto Timur", Toast.LENGTH_SHORT).show();
        }
        //5AJIBARANG
        else if (myaddress.getText().toString().equals("Ajibarang")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Ajibarang Kulon")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Ajibarang Wetan")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banjarsari")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banjarsari Kidul")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Ciberung")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Darmakradenan")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Jingkang")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalibenda")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangbawang")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kracak")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Lesmana")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pancasan")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pancurenang")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pandansari")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sawangan")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tipar")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tipar Kidul")) {
            nopolisi.setText("0281 571278");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Ajibarang", Toast.LENGTH_SHORT).show();
        }
        //6BANYUMAS
        else if (myaddress.getText().toString().equals("Banyumas")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Binangun")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Danaraja")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Dawuhan")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalisube")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangrau")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedunggede")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedunguter")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kejawar")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Papringan")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pasinggangan")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pekunden")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sudagaran")) {
            nopolisi.setText("0281 796110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Banyumas", Toast.LENGTH_SHORT).show();
        }
        //7Baturraden
        else if (myaddress.getText().toString().equals("Baturraden")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Purwosari")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangmangu")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangsalam Lor")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karangtengah")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kebumen")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kemutug Kidul")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kemutug Lor")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Ketenger")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kutasari")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Pamijen")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pandak")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Rempoah")) {
            nopolisi.setText("0281 681711");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Baturraden", Toast.LENGTH_SHORT).show();
        }
        //8CILONGOK
        else if (myaddress.getText().toString().equals("Cilongok")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Batuanten")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cikidang")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cipete")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gununglurah")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Jatisaba")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalisari")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanglo")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangtengah")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kasegeran")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("langgongsari")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("pageraji")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Panembangan")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Panusupan")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pejogol")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pernasidi")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Rancamaya")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sambirata")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokawera")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sudimara")) {
            nopolisi.setText("0281 655436");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Cilongok", Toast.LENGTH_SHORT).show();
        }
        //9GUMELAR(NOTLPPOLSEKBLUMADAMASIHIKUTPOLRES)
        else if (myaddress.getText().toString().equals("Gumelar")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cihonje")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cilangkap")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gancang")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangkemojing")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungurang")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Paningkaban")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Samudra")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Samudra Kulon")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tlaga")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Gumelar", Toast.LENGTH_SHORT).show();
        }
        //10JATILAWANG
        else if (myaddress.getText().toString().equals("Jatilawang")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Adisara")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Bantar")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gentawangi")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gunung Wetan")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanganyar")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanglewas")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungwringin")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Margasana")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Margasana")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pekuncen")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tinggarjaya")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tunjung")) {
            nopolisi.setText("0281 511070");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Jatilawang", Toast.LENGTH_SHORT).show();
        }
        //10Kalibagor
        else if (myaddress.getText().toString().equals("Kalibagor")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalicupak Kidul")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalicupak Lor")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kaliori")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalisogra Wetan")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangdadap")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pajerukan")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pekaja")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Petir")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Srowot")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Suro")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Wlahar Wetan")) {
            nopolisi.setText("0281 6438189");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kalibagor", Toast.LENGTH_SHORT).show();
        }

        //11Karanglewas
        else if (myaddress.getText().toString().equals("Babakan")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Jipang")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanggude Kulon")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangkemiri")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanglewas Kidul")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kediri")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pangebatan")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pasir Kulon")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pasir Lor")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pasir Wetan")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Singasari")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sunyalangu")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tamansari")) {
            nopolisi.setText("0281 655398");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Karanglewas", Toast.LENGTH_SHORT).show();
        }
        //12KEBASEN
        else if (myaddress.getText().toString().equals("Kebasen")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Adisana")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Bangsa")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cindaga")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gambarsari")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalisalak")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kaliwedi")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangsari")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Mandiracan")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Randegan")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sawangan")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tumiyang")) {
            nopolisi.setText("0281 6847691");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kebasen", Toast.LENGTH_SHORT).show();
        }
        //13KEDUNGBANTENG
        else if (myaddress.getText().toString().equals("Kedung Banteng")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Baseh")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Beji")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Dawuhan Kulon")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalikesur")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalisalak")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangnangka")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangsalam Kidul")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kebocoran")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungbanteng")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kenitan")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kutaliman")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Melung")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Windujaya")) {
            nopolisi.setText("0283 3335653");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kedung Banteng", Toast.LENGTH_SHORT).show();
        }
        //14KEMBARAN(NOTLPPOLSEKBLUMADAMASIHIKUTPOLRES)
        else if (myaddress.getText().toString().equals("Kembaran")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Bantarwuni")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Bojongsari")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Dukuhwaluh")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangsari")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangsoka")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangtengah")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kramat")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Ledug")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Linggasari")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pliken")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purbadana")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purwodadi")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sambeng Kulon")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sambeng Wetan")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Tambaksari Kidul")) {
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kembaran", Toast.LENGTH_SHORT).show();
        }
        //15KEMRANJEN
        else if (myaddress.getText().toString().equals("Kemranjen")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Alasmalang")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Grujugan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanggintung")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Krangjati")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangsalam")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kebarongan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kecila")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungpring")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Nusamangir")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pageralang")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Petarangan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sibalung")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sibrama")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sidamulya")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sirau")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Kemranjen", Toast.LENGTH_SHORT).show();
        }
        //16LUMBIR
        else if (myaddress.getText().toString().equals("Lumbir")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Besuki")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Candok")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cidora")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cingebul")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Cirahab")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Dermaji")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanggayam")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedunggede")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Parungkamal")) {
            nopolisi.setText("0281 363110");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Lumbir", Toast.LENGTH_SHORT).show();
        }
        //17PATIKRAJA
        else if (myaddress.getText().toString().equals("Patikraja")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karanganyar")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangendep")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungrandu")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungwringin")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungwuluh Kidul")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedungwuluh Lor")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Notog")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pegalongan")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sawangan Wetan")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sidabowa")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokawera Kidul")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Wlahar Kulon")) {
            nopolisi.setText("0281 694280");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Patikraja", Toast.LENGTH_SHORT).show();
        }
        //18PEKUNCEN
        else if (myaddress.getText().toString().equals("Pekuncen")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banjaranyar")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Candinegara")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Cibangkong")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Cikawung")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Cikembulan")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Glempang")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karangkemiri")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karangklesem")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Krajan")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kranggan")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Pasiraman Kidul")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Pasiraman Lor")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Petahuan")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Semedo")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Tumiyang")) {
            nopolisi.setText("0281 6439272");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Pekuncen", Toast.LENGTH_SHORT).show();
        }
        //19PURWOJATI
        else if (myaddress.getText().toString().equals("Purwojati")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gerduren")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kaliputih")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalitapen")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kaliurip")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kaliwangi")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangmangu")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangtalun kidul")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangtalun Lor")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Klapasawit")) {
            nopolisi.setText("0281 6578098");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Purwojati", Toast.LENGTH_SHORT).show();
        }
        //20RAWALO
        else if (myaddress.getText().toString().equals("Rawalo")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banjarparakan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Losari")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Menganti")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Pesawahan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Sanggreman")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Sidamulih")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Tambaknegara")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Tipar")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Rawalo", Toast.LENGTH_SHORT).show();
        }
        //22SOKARAJA
        else if (myaddress.getText().toString().equals("Sokaraja")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Banjaranyar")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banjarsari Kidul")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Jompo Kulon")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kalikidang")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangduren")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangnanas")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangrau")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kedondong")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Klahang")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Lemberang")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pamijen")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokaraja Kidul")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokaraja Kulon")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokaraja Lor")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokaraja Tengah")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Sokaraja Wetan")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Wiradadi")) {
            nopolisi.setText("0281 6440980");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sokaraja", Toast.LENGTH_SHORT).show();
        }
        //23SOMAGEDE
        else if (myaddress.getText().toString().equals("Somagede")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kanding")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kemawi")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Klinting")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Piasa Kulon")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Plana")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Sokawera")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Somakaton")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Tanggeran")) {
            nopolisi.setText("0281 796640");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Somagede", Toast.LENGTH_SHORT).show();
        }
        //24SUMBANG
        else if (myaddress.getText().toString().equals("Sumbang")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Banjarsari Kulon")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Banjarsari Wetan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Banteran")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Ciberem")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Datar")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Gandatapa")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karangcegak")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karanggintung")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("karangturi")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kawungcarang")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kebanggan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kedungmalang")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kotayasa")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Limpakuwus")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Sikapat")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Silado")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Susukan")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Tambaksogra")) {
            nopolisi.setText("0281 636065");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumbang", Toast.LENGTH_SHORT).show();
        }
        //25SUMPIUH
        else if (myaddress.getText().toString().equals("Sumpiuh")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banjarpanepen")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Bogangin")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Karanggedang")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kebokura")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kemiri")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Ketanda")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kradenan")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Kuntili")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Lebeng")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Nusadadi")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Pandak")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Selandaka")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Selanegara")) {
            nopolisi.setText("0282 497530");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Sumpiuh", Toast.LENGTH_SHORT).show();
        }
        //26TAMBAK
        else if (myaddress.getText().toString().equals("Tambak")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        }else if (myaddress.getText().toString().equals("Buniayu")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gebangsari")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gumelar Kidul")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Gumelar Lor")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Kamulyan")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangpetir")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Karangpucung")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Pesantren")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Plangkapan")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Prembun")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Purwodadi")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Watuagung")) {
            nopolisi.setText("0281 472544");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Tambak", Toast.LENGTH_SHORT).show();
        }
        //27WANGON
        else if (myaddress.getText().toString().equals("Wangon")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        } else if (myaddress.getText().toString().equals("Banteran")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Cikakak")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Jambu")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Jurangbahas")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Klapagading")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Klapagading Kulon")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Pangadegan")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Randegan")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Rawaheng")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Windunegara")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }  else if (myaddress.getText().toString().equals("Wlahar")) {
            nopolisi.setText("0281 635330");
            Toast.makeText(MainActivity.this, "Menghubungi Polsek Wangon", Toast.LENGTH_SHORT).show();
        }

        else {
            //POLRES BANYUMAS
            nopolisi.setText("0281 622259");
            Toast.makeText(MainActivity.this, "Menghubungi POLRES BANYUMAS", Toast.LENGTH_SHORT).show();
        }


    }

    public void sms() {
        String number1 = no1.getText().toString();
        String number2 = no2.getText().toString();
        String number3 = no3.getText().toString();
        String number4 = no4.getText().toString();
        String message1 = etSMS.getText().toString();
        String message2 = etLOKASI.getText().toString();
        String message = message1 + " " + message2;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number1, null, message, null, null);
            smsManager.sendTextMessage(number2, null, message, null, null);
            smsManager.sendTextMessage(number3, null, message, null, null);
            smsManager.sendTextMessage(number4, null, message, null, null);
            Toast.makeText(MainActivity.this, "Meminta Pertolongan", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Pesan Pertolongan Berhasil Dikirim", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            //Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void PermissionToConnect() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

    }


    public void openPengaturan(View view) {
        startActivity(new Intent(MainActivity.this, Pengaturan.class));
    }

    public void openListView(View view) {
        startActivity(new Intent(MainActivity.this, ListViewDB.class));
    }

    public void opennavmap(View view) {
        startActivity(new Intent(MainActivity.this, navmap.class));
    }

    public void ceknomor() {

        if (no1.getText().toString().equals("")) {
            /**
             new AlertDialog.Builder(this)
             .setTitle("NOMOR MASIH KOSONG, SILAHKAN ISI DI PENGATURAN?")
             .setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override public void onClick(DialogInterface dialog, int which){
            startActivity(new Intent(MainActivity.this, Pengaturan.class));
            }
            })
             .create().show();
             **/
        } else {
//           Toast.makeText(MainActivity.this, "DATA SUDAH TERISI", Toast.LENGTH_SHORT).show();
            saveSharedprefences();
        }

    }

    public void saveSharedprefences() {
        String n1 = no1.getText().toString();
        String n2 = no2.getText().toString();
        String n3 = no3.getText().toString();
        String n4 = no4.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(NO1, n1);
        editor.putString(NO2, n2);
        editor.putString(NO3, n3);
        editor.putString(NO4, n4);
        editor.apply();
    }

    public void setSharedpreferences() {

        no1.setText(sharedpreferences.getString(NO1, ""));
        no2.setText(sharedpreferences.getString(NO2, ""));
        no3.setText(sharedpreferences.getString(NO3, ""));
        no4.setText(sharedpreferences.getString(NO4, ""));

    }

    public void alamataddress() {
        String tvlatcoor = mylatitude.getText().toString();
        String tvlongcoor = mylongitude.getText().toString();


        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(tvlatcoor), Double.parseDouble(tvlongcoor), 1);
            String address = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getSubLocality();//kelurahan
            String city = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();//indonesia
            String postalcode = addresses.get(0).getPostalCode();
            String fulladdress = address + ", " + area + ", " + city + ", " + country + ", " + postalcode;

            myaddress.setText(area);
            lokasisaya.setText(fulladdress);


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Cannot get Address", Toast.LENGTH_SHORT).show();

        }
    }
}
