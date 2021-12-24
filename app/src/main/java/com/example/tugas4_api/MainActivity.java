package com.example.tugas4_api;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Button btnReact, btnVue, btnAngularJS, btnLaravel,btnVaadin, btnSpring;
    TextView txtNamaFramework,txtDeskripsi, txtAuthor, txtWebsite;
    ImageView imgLogo;
    FloatingActionButton btnRefresh;
    View lyCurrency;
    ProgressBar loadingIndicator;
    private String namaFramework = "React";
    JSONObject dataTerbaru;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        ambilDataFramework();
    }

    private void inisialisasiView() {
        txtAuthor = findViewById(R.id.txtAuthor);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtDeskripsi = findViewById(R.id.txtDeskripsi);
        txtNamaFramework = findViewById(R.id.txtNamaFramework);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        lyCurrency = findViewById(R.id.lyCurrency);
        loadingIndicator = findViewById(R.id.loadingIndicator);

        btnReact = findViewById(R.id.btnReact);
        btnReact.setOnClickListener(view -> tampilkanDataFramework("React"));

        btnVue = findViewById(R.id.btnVue);
        btnVue.setOnClickListener(view -> tampilkanDataFramework("Vue"));

        btnAngularJS = findViewById(R.id.btnAngularJS);
        btnAngularJS.setOnClickListener(view -> tampilkanDataFramework("AngularJS"));

        btnLaravel = findViewById(R.id.btnLaravel);
        btnLaravel.setOnClickListener(view -> tampilkanDataFramework("Laravel"));

        btnVaadin = findViewById(R.id.btnVaadin);
        btnVaadin.setOnClickListener(view -> tampilkanDataFramework("Vaadin"));

        btnSpring = findViewById(R.id.btnSpring);
        btnSpring.setOnClickListener(view -> tampilkanDataFramework("Spring Framework"));

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> ambilDataFramework());
    }

    private void ambilDataFramework() {
        loadingIndicator.setVisibility(View.VISIBLE);
        String baseURL = "https://ewinsutriandi.github.io/mockapi/web_framework.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MAIN",response.toString());
                        dataTerbaru = response;
                        tampilkanDataFramework(namaFramework);
                        loadingIndicator.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Gagal mengambil data",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void tampilkanDataFramework(String namaFramework) {
        this.namaFramework = namaFramework;
        // tampilkan mata uang terpilih
        txtNamaFramework.setText(namaFramework);
        try { // try catch untuk antisipasi error saat parsing JSON
            // siapkan kemudian tampilkan nilai tukar
            JSONObject data = dataTerbaru.getJSONObject(namaFramework);
            txtAuthor.setText(data.getString("original_author"));
            txtWebsite.setText(data.getString("official_website"));
            txtDeskripsi.setText(data.getString("description"));
            Glide.with(MainActivity.this)
                    // LOAD URL DARI INTERNET
                    .load(data.getString("logo"))
                    // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                    .placeholder(R.drawable.ic_launcher_background)
                    //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
                    .error(R.drawable.ic_launcher_background)
                    .into(imgLogo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}