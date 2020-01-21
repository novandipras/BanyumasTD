package sonda.banyumastd;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Detail extends Activity {
    ImageView Im;
    TextView tv_nama, tv_telpon, tv_detail, tv_alamat, tv_size, id, namaIm;
    Gallery gallery;
    ImageSwitcher imageSwitcher;
    Integer[] imageIDs = new Integer[3];
    int msg_im;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent iIdentifikasi = getIntent();
        msg_im = iIdentifikasi.getIntExtra("dataIM", 0);
        String msg_nama = iIdentifikasi.getStringExtra("dataNama");
        String msg_telpon = iIdentifikasi.getStringExtra("datatelpon");
        String msg_alamat = iIdentifikasi.getStringExtra("dataalamat");
        String msg_detail = iIdentifikasi.getStringExtra("datadetail");

        Im = findViewById(R.id.iv_detail);
        tv_nama = findViewById(R.id.tvNama);
        tv_telpon = findViewById(R.id.tvtelpon);
        tv_alamat = findViewById(R.id.tvalamat);
        tv_detail = findViewById(R.id.tvdetail);

        Im.setImageResource(msg_im);
        tv_nama.setText(msg_nama);
        tv_telpon.setText(msg_telpon);
        tv_alamat.setText(msg_alamat);
        tv_detail.setText(msg_detail);

        Button Calling = findViewById(R.id.buttoncalling);
        Calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        Button GPS = findViewById(R.id.buttongps);
        GPS.setOnClickListener(new View.OnClickListener() {
            String namatempat = tv_nama.getText().toString();
            String lokasi = tv_alamat.getText().toString();
            String carilokasi = namatempat + "," + lokasi;

            @Override
            public void onClick(View v) {
                String url = "https://www.google.com/maps/dir/Current+Location/" + carilokasi;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void makePhoneCall() {
        String number = tv_telpon.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(Detail.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Detail.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(Detail.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }


}
