package sonda.banyumastd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListViewDB extends Activity {
    protected ListView lv;
    protected ListAdapter adapter;
    SQLiteDatabase db;
    Cursor cursor;
    EditText et_db;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_db);

        db = (new Database(this)).getWritableDatabase();
        lv = findViewById(R.id.lv);
        et_db = findViewById(R.id.et);

        try {
            cursor = db.rawQuery("SELECT * FROM rumkit ORDER BY nama DESC", null);
            adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
            lv.setAdapter(adapter);
            lv.setTextFilterEnabled(true);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    detail(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    public void search_db(View v) {
        String edit_db = et_db.getText().toString();
        if (!edit_db.equals("")) {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit WHERE nama LIKE ?", new String[]{"%" + edit_db + "%"});
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                if (adapter.getCount() == 0) {
                    Toast.makeText(this, "Tidak ditemukan data dengan kata kunci " + edit_db + "", Toast.LENGTH_SHORT).show();
                } else {
                    lv.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit ORDER BY nama ASC", null);
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                lv.setAdapter(adapter);
                lv.setTextFilterEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void search_dbRS(View v) {
        et_db.setText("RS");
        String edit_db = et_db.getText().toString();
        if (!edit_db.equals("")) {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit WHERE nama LIKE ?", new String[]{"%" + edit_db + "%"});
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                if (adapter.getCount() == 0) {
                    Toast.makeText(this, "Tidak ditemukan data dengan kata kunci " + edit_db + "", Toast.LENGTH_SHORT).show();
                } else {
                    lv.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit ORDER BY nama ASC", null);
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                lv.setAdapter(adapter);
                lv.setTextFilterEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void search_dbPolisi(View v) {
        et_db.setText("Polisi");
        String edit_db = et_db.getText().toString();
        if (!edit_db.equals("")) {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit WHERE nama LIKE ?", new String[]{"%" + edit_db + "%"});
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                if (adapter.getCount() == 0) {
                    Toast.makeText(this, "Tidak ditemukan data dengan kata kunci " + edit_db + "", Toast.LENGTH_SHORT).show();
                } else {
                    lv.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit ORDER BY nama ASC", null);
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                lv.setAdapter(adapter);
                lv.setTextFilterEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void search_dbDamkar(View v) {
        et_db.setText("Pemadam");
        String edit_db = et_db.getText().toString();
        if (!edit_db.equals("")) {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit WHERE nama LIKE ?", new String[]{"%" + edit_db + "%"});
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                if (adapter.getCount() == 0) {
                    Toast.makeText(this, "Tidak ditemukan data dengan kata kunci " + edit_db + "", Toast.LENGTH_SHORT).show();
                } else {
                    lv.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                cursor = db.rawQuery("SELECT * FROM rumkit ORDER BY nama ASC", null);
                adapter = new SimpleCursorAdapter(this, R.layout.isi_lv, cursor, new String[]{"nama", "telpon", "img"}, new int[]{R.id.tv_nama, R.id.tv_telpon, R.id.imV});
                lv.setAdapter(adapter);
                lv.setTextFilterEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void detail(int position) {
        int im = 0;
        String _id = "";
        String nama = "";
        String telpon = "";
        String alamat = "";
        String detail = "";


        if (cursor.moveToFirst()) {
            cursor.moveToPosition(position);
            im = cursor.getInt(cursor.getColumnIndex("img"));
            nama = cursor.getString(cursor.getColumnIndex("nama"));
            telpon = cursor.getString(cursor.getColumnIndex("telpon"));
            alamat = cursor.getString(cursor.getColumnIndex("alamat"));
            detail = cursor.getString(cursor.getColumnIndex("detail"));

        }

        Intent iIntent = new Intent(this, Detail.class);
        iIntent.putExtra("dataIM", im);
        iIntent.putExtra("dataNama", nama);
        iIntent.putExtra("datatelpon", telpon);
        iIntent.putExtra("dataalamat", alamat);
        iIntent.putExtra("datadetail", detail);


        setResult(RESULT_OK, iIntent);
        startActivityForResult(iIntent, 99);
    }

}