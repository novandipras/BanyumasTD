package sonda.banyumastd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    final static String DB_NAME = "db_rumahsakit";

    public Database(Context context) {
        super(context, DB_NAME, null, 3);
// TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS rumkit(_id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, telpon TEXT, alamat TEXT, detail TEXT, img BLOB)";
        db.execSQL(sql);

        ContentValues values = new ContentValues();
        values.put("_id", "1");
        values.put("nama", "RSUD BANYUMAS");
        values.put("telpon", "0281 796182");
        values.put("alamat", "Jl. Rumah Sakit No.1 Banyumas");
        values.put("detail", "www.rsudbms.banyumaskab.go.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "2");
        values.put("nama", "RSUD Prof Dr. Margono Soekarjo");
        values.put("telpon", "0281 632708");
        values.put("alamat", "Jl. Dr Gumbreg No. 1 Purwokerto");
        values.put("detail", "www.rsmargono.jatengprov.go.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "3");
        values.put("nama", "RS Umum Tk III Wijayakusuma");
        values.put("telpon", "0281 633062");
        values.put("alamat", "Jl. Prof.Dr. HR Bunyamin Purwokerto");
        values.put("detail", "www.rswk.co.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "4");
        values.put("nama", "RS Umum Santa Elizabeth");
        values.put("telpon", "0281 625857");
        values.put("alamat", "Jl. dr. Angka No. 40 Purwokerto");
        values.put("detail", "www.rsuelisabethpwt.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "5");
        values.put("nama", "RS Umum Islam Purwokerto");
        values.put("telpon", "0281 630019");
        values.put("alamat", "Jl. H. Masyuri Rejasari No 39 Purwokerto");
        values.put("detail", "www.rsipurwokerto.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "6");
        values.put("nama", "RS Umum Hidayah Purwokerto");
        values.put("telpon", "0281 636269");
        values.put("alamat", "Jl. Supriyadi No. 22 Purwokerto");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "7");
        values.put("nama", "RS Umum Bunda");
        values.put("telpon", "0281 635424");
        values.put("alamat", "Jl. Pramuka No. 249 Purwokerto");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "8");
        values.put("nama", "RS Umum Ananda Purwokerto");
        values.put("telpon", " 0281 636417");
        values.put("alamat", "Jl. Pemuda No. 30 Purwokerto");
        values.put("detail", "www.rsananda.co.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "9");
        values.put("nama", "RS Umum Sinar Kasih");
        values.put("telpon", "0281 638370");
        values.put("alamat", "Jl. Martadireja II Purwokerto");
        values.put("detail", "rssinarkasih.info");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);


        values.put("_id", "10");
        values.put("nama", "RS Umum Daerah Ajibarang");
        values.put("telpon", "0281 6570004");
        values.put("alamat", "Jl. Raya Pancasan No.1,Ajibarang Banyumas");
        values.put("detail", "rsudajibarang@banyumaskab.go.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "11");
        values.put("nama", "RS Umum Wishnu Husada");
        values.put("telpon", "0281 6844850");
        values.put("alamat", "Jl. Raya Notog Mt.200,Patikraja Banyumas");
        values.put("detail", "www.wishnuhusada.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "12");
        values.put("nama", "RS Umum Wiradadi Husada");
        values.put("telpon", "0281 6846225");
        values.put("alamat", "Jl. Menteri Supeno No. 25, Sokaraja Banyumas");
        values.put("detail", "www.rswiradadi.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "13");
        values.put("nama", "RS Umum Siaga Medika Banyumas");
        values.put("telpon", "0281 796645");
        values.put("alamat", "Jl. Pramuka No. 55 Banyumas");
        values.put("detail", "www.siagamedika.co.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "14");
        values.put("nama", "RS Khusus Bedah Orthopedi");
        values.put("telpon", "0281 6844166");
        values.put("alamat", "JL Soepardjo Rustam No. 99 Sokaraja Kab. Banyumas");
        values.put("detail", "www.rsop.co.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "15");
        values.put("nama", "RS Ibu dan Anak Amanah");
        values.put("telpon", "497548");
        values.put("alamat", "Jl Raya Kebokura No.37 Kec Sumpiuh Kab Banyumas");
        values.put("detail", "");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);


        values.put("_id", "16");
        values.put("nama", "RS Ibu dan Anak Bunda Arif");
        values.put("telpon", "0281 636555");
        values.put("alamat", "Jl. Jatiwinangun No. 16 Purwokerto Kab.Banyumas");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "17");
        values.put("nama", "RS Khusus Bedah Jatiwinangun");
        values.put("telpon", "0281 638169");
        values.put("alamat", "Jl. Jatiwinangun No.54, Purwokerto");
        values.put("detail", "www.rsipurwokerto.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "18");
        values.put("nama", "RS Umum An-Ni'mah");
        values.put("telpon", "0281 513267");
        values.put("alamat", "Jalan Raya Klapagading Kulon Rt 01 Rw 14 Kec Wangon Banyumas");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "19");
        values.put("nama", "RS Khusus Bedah Mitra Ariva");
        values.put("telpon", "0281 571328");
        values.put("alamat", "Jl. Raya Ajibarang WetanAjibarang Banyumas");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "20");
        values.put("nama", "RS Ibu dan Anak Budhi Asih");
        values.put("telpon", "0281 641310");
        values.put("alamat", "Komplek Stadion Mini");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "21");
        values.put("nama", "RS Umum Medika Lestari Banyumas");
        values.put("telpon", "0282 5291299");
        values.put("alamat", "Jl. Raya Nasional Pageralang RT 03 RW 03 Kemranjen Banyumas");
        values.put("detail", "-");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);


        values.put("_id", "22");
        values.put("nama", "RS Khusus Gigi dan Mulut Universitas Jenderal Soedirman");
        values.put("telpon", "0281 641233");
        values.put("alamat", "Jl. Dr. Soeparno Purwokerto");
        values.put("detail", "rsgmp.unsoed.ac.id");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "23");
        values.put("nama", "RS Umum Dadi Keluarga");
        values.put("telpon", "0281 6847366");
        values.put("alamat", "Jl. Perumnas Teluk No. 42 Kel. Teluk Kec. Purwokerto Selatan");
        values.put("detail", "www.rsdk-PWT.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "24");
        values.put("nama", "RS Hermina Purwokerto");
        values.put("telpon", "0281 7772525");
        values.put("alamat", "Jl. Yos Sudarso RT 003 RW 001 Karanglewas Lor Kec.Purwokerto Barat, Kab. Banyumas");
        values.put("detail", "www.herminahospitalgroup.com");
        values.put("img", R.drawable.rumahsakit);
        db.insert("rumkit", "_id", values);

        values.put("_id", "25");
        values.put("nama", "Kantor Kepolisian Resor (Polres) Banyumas");
        values.put("telpon", "0281622259");
        values.put("alamat", "Jl. Letjen Pol R Sumarto no.100 Purwanegara, Karang Jambu, Purwanegara, Purwokerto Timur, Kab.Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "26");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Purwokerto Timur");
        values.put("telpon", "0281635015");
        values.put("alamat", "JL. Gatot subroto no.1 kebon dalem, Purwokerto lor,purwokerto timur kab.Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "27");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Purwokerto Barat");
        values.put("telpon", "0281 639211");
        values.put("alamat", "JL. Laksda Yos Sudarso Karanglewas Lor, Purwokerto Barat, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "28");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Purwokerto Utara");
        values.put("telpon", "0281 635015");
        values.put("alamat", "JL. DR Angka, Glemmpang,sukanegara, purwokerto timur");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "29");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Purwokerto Selatan");
        values.put("telpon", "0281 6843835");
        values.put("alamat", "JL.Prof M Yamin 6, Karang klesem, karang pucung, Purwokerto Selatan");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "30");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Cilongok");
        values.put("telpon", "0281 655436");
        values.put("alamat", "JL Raya CIlongok no.171, kalimanggis selatan, cilongok,kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "31");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Banyumas");
        values.put("telpon", "0281796110");
        values.put("alamat", "JL.Bhayangkara, Kedungter kidul, kec Banyumas, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "32");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Somagede");
        values.put("telpon", "0281 796640");
        values.put("alamat", "JL.Raya Banjarnegara-Banyumas,Somagede,Kec Banyumas,Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "33");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Baturraden");
        values.put("telpon", " 0281 681711");
        values.put("alamat", "JL. Raya Baturaden Km.8 Rempoah,Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "34");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Karanglewas");
        values.put("telpon", "0281 5752685");
        values.put("alamat", "JL.Raya Karang Kemiri, Karang Lewas, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "35");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Sumpiuh");
        values.put("telpon", "0282 497530");
        values.put("alamat", "JL.Raya Sumpiuh, Sumpiuh,Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "36");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Kalibagor");
        values.put("telpon", "0281 6438189");
        values.put("alamat", "JL.Ajibarang Secang, Kalibagor, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "37");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Kebasen");
        values.put("telpon", "0281 6847691");
        values.put("alamat", "JL.Raya PUK no.30 Kebasen, kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "38");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Pekuncen");
        values.put("telpon", "0281 6439272");
        values.put("alamat", "JL.Ajibarang Brebes, Banjaranyar, Pekuncen,Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "39");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Patikraja");
        values.put("telpon", "0281 694280");
        values.put("alamat", "JL.Statiun Notog no.38,Patikraja, kec.Banyumas, kab,Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "40");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Sokaraja");
        values.put("telpon", "0281 694280");
        values.put("alamat", "JL.Jenderal Sudirman no.99, sokaraja tengah, kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "41");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Sumbang");
        values.put("telpon", "0281 6843192");
        values.put("alamat", "JL.Raya Sumbang,Sumbang,Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "42");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Wangon");
        values.put("telpon", "0281 513320");
        values.put("alamat", "JL.Raya utara 09 wangon, kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "43");
        values.put("nama", "Kantor Kepolisian Sektor (Polsek) Kemranjen");
        values.put("telpon", "0282 5291337");
        values.put("alamat", "JL.Raya Buntu Jogja no.42 Kemranjen, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.polisi);
        db.insert("rumkit", "_id", values);

        values.put("_id", "44");
        values.put("nama", "Pemadam Kebakaran Purwokerto");
        values.put("telpon", "0281 632611");
        values.put("alamat", "JL.Brigjen Encu no.104A, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.damkar);
        db.insert("rumkit", "_id", values);

        values.put("_id", "45");
        values.put("nama", "Pemadam Kebakaran Wangon");
        values.put("telpon", "0281 5311447");
        values.put("alamat", "JL.Raya Utara no.Wangon , Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.damkar);
        db.insert("rumkit", "_id", values);

        values.put("_id", "46");
        values.put("nama", "Pemadam Kebakaran Kemranjen");
        values.put("telpon", "02825293100");
        values.put("alamat", "JL. Raya Kecila-Kemranjen, kemranjen, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.damkar);
        db.insert("rumkit", "_id", values);

        values.put("_id", "47");
        values.put("nama", "Pemadam Kebakaran Kembaran");
        values.put("telpon", "02817771474");
        values.put("alamat", "JL. Raya Larangan, Kembaran, Kab Banyumas");
        values.put("detail", "  ");
        values.put("img", R.drawable.damkar);
        db.insert("rumkit", "_id", values);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rumkit");
        onCreate(db);

    }

}
