package sonda.banyumastd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class Pengaturan extends Activity {
    private EditText etno1, etno2, etno3, etno4, etno1nama, etno2nama, etno3nama, etno4nama, etwa;
    private Switch switchwa;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        Button btimport1 = findViewById(R.id.btimport1);
        Button btimport2 = findViewById(R.id.btimport2);
        Button btimport3 = findViewById(R.id.btimport3);
        Button btimport4 = findViewById(R.id.btimport4);
        Button btsimpan = findViewById(R.id.btsimpan);
        final EditText nama = findViewById(R.id.nama);
        final EditText email = findViewById(R.id.email);
        etno1 = findViewById(R.id.etno1);
        etno2 = findViewById(R.id.etno2);
        etno3 = findViewById(R.id.etno3);
        etno4 = findViewById(R.id.etno4);
        etno1nama = findViewById(R.id.etno1nama);
        etno2nama = findViewById(R.id.etno2nama);
        etno3nama = findViewById(R.id.etno3nama);
        etno4nama = findViewById(R.id.etno4nama);
        etwa = findViewById(R.id.etwa);
        switchwa = findViewById(R.id.switchwa);

        prefs = this.getSharedPreferences("sonda.banyumastd", Context.MODE_PRIVATE);

        nama.setText(prefs.getString("shared_nama", ""));
        email.setText(prefs.getString("shared_email", ""));
        etno1.setText(prefs.getString("shared_etno1", ""));
        etno2.setText(prefs.getString("shared_etno2", ""));
        etno3.setText(prefs.getString("shared_etno3", ""));
        etno4.setText(prefs.getString("shared_etno4", ""));
        etno1nama.setText(prefs.getString("shared_etno1nama", ""));
        etno2nama.setText(prefs.getString("shared_etno2nama", ""));
        etno3nama.setText(prefs.getString("shared_etno3nama", ""));
        etno4nama.setText(prefs.getString("shared_etno4nama", ""));
        etwa.setText(prefs.getString("shared_etwa", ""));

        if (etwa.getText().toString().equals("")) {
            switchwa.setChecked(false);
        }else if (etwa.getText().toString().equals("OFF")){
            switchwa.setChecked(false);
        }else{
            switchwa.setChecked(true);
        }

        btsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("shared_nama", nama.getText().toString());
                editor.putString("shared_email", email.getText().toString());
                editor.putString("shared_etno1", etno1.getText().toString());
                editor.putString("shared_etno2", etno2.getText().toString());
                editor.putString("shared_etno3", etno3.getText().toString());
                editor.putString("shared_etno4", etno4.getText().toString());
                editor.putString("shared_etno1nama", etno1nama.getText().toString());
                editor.putString("shared_etno2nama", etno2nama.getText().toString());
                editor.putString("shared_etno3nama", etno3nama.getText().toString());
                editor.putString("shared_etno4nama", etno4nama.getText().toString());
                editor.putString("shared_etwa", etwa.getText().toString());
                editor.commit();
                //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Pengaturan.this, MainActivity.class);
                intent.putExtra("data1", etno1.getText().toString());
                intent.putExtra("data2", etno2.getText().toString());
                intent.putExtra("data3", etno3.getText().toString());
                intent.putExtra("data4", etno4.getText().toString());
                startActivity(intent);
            }
        });

        btimport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 85500);
            }
        });
        btimport2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 85500);
            }
        });
        btimport3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 85500);
            }
        });
        btimport4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 85500);
            }
        });

        switchwa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    etwa.setText("ON");
                } else {
                    etwa.setText("OFF");
                }
            }
        });


        cancel();
        clear();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 85500:
                    Cursor cursor = null;
                    try {
                        String phoneNo = null;
                        String name = null;
                        Uri uri = data.getData();
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        phoneNo = cursor.getString(phoneIndex);
                        name = cursor.getString(nameIndex);
                        if (etno1.getText().toString().equals("")) {
                            etno1nama.setText(name);
                            etno1.setText(phoneNo);

                        } else if (etno2.getText().toString().equals("")) {
                            etno2nama.setText(name);
                            etno2.setText(phoneNo);

                        } else if (etno3.getText().toString().equals("")) {
                            etno3nama.setText(name);
                            etno3.setText(phoneNo);

                        } else {
                            etno4nama.setText(name);
                            etno4.setText(phoneNo);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e("pickcontact", "Failed to pick contact");
        }
    }

    public void cancel() {
        Button cancelButton = this.findViewById(R.id.btcancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clear() {
        Button clearButton = this.findViewById(R.id.btclear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etno1.setText("");
                etno2.setText("");
                etno3.setText("");
                etno4.setText("");
                etno1nama.setText("");
                etno2nama.setText("");
                etno3nama.setText("");
                etno4nama.setText("");

            }
        });
    }

    public void openTentang(View view) {
        startActivity(new Intent(Pengaturan.this, Tentang.class));
    }


}
