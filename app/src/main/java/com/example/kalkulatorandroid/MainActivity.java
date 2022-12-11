package com.example.kalkulatorandroid;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ItemList> listKalkulator;
    private RecyclerView mRec_hitung;
    private SharedPreferenceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RadioGroup operasiGroup;
    RadioButton operasiTambah, operasiKurang, operasiKali, operasiBagi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menampilkan Data dari Shared Preference
        loadData();

        // Setting koneksi antara Recycler View dan Adapter
        buildRecyclerView();

        operasiGroup = findViewById(R.id.operasiHitung);
        operasiTambah = findViewById(R.id.tambah);
        operasiKurang = findViewById(R.id.kurang);
        operasiKali = findViewById(R.id.kali);
        operasiBagi = findViewById(R.id.bagi);

        // Insert Nilai
        setInsertButton();

        operasiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(operasiTambah.isChecked())
                {
                    Toast.makeText(MainActivity.this, "Tambah", Toast.LENGTH_SHORT).show();
                } else if (operasiKurang.isChecked())
                {
                    Toast.makeText(MainActivity.this, "Kurang", Toast.LENGTH_SHORT).show();
                } else if (operasiKali.isChecked())
                {
                    Toast.makeText(MainActivity.this, "Kali", Toast.LENGTH_SHORT).show();
                } else if (operasiBagi.isChecked())
                {
                    Toast.makeText(MainActivity.this, "Bagi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buildRecyclerView() {
        mRec_hitung = findViewById(R.id.rec_hitung);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SharedPreferenceAdapter(listKalkulator);

        mRec_hitung.setLayoutManager(mLayoutManager);
        mRec_hitung.setAdapter(mAdapter);

    }

    private void loadData(){
        // Menampilkan data
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preference", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ItemList>>() {}.getType();
        listKalkulator = gson.fromJson(json, type);

        if(listKalkulator == null){
            listKalkulator = new ArrayList<>();
        }

    }

    private void setInsertButton(){
        // Insert nilai
        Button hitung = findViewById(R.id.btnHitung);
        hitung.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                EditText input1 = findViewById(R.id.edt1);
                EditText input2 = findViewById(R.id.edt2);
                TextView hslHitung = findViewById(R.id.ViewHasil);
                int mHasil = hitung(input1.getText().toString(), input2.getText().toString());
                hslHitung.setText("Hasil  :  " + mHasil);
                saveData();
            }
        });
    }
    private int hitung(String minput1, String minput2) {
        // Operasi hitung
        int hasil = 0;
        if(operasiTambah.isChecked()) {
            hasil = Integer.parseInt(minput1) + Integer.parseInt(minput2);
        } else if (operasiKurang.isChecked()) {
            hasil = Integer.parseInt(minput1) - Integer.parseInt(minput2);
        } else if (operasiKali.isChecked()) {
            hasil = Integer.parseInt(minput1) * Integer.parseInt(minput2);
        } else if (operasiBagi.isChecked()) {
            hasil = Integer.parseInt(minput1) / Integer.parseInt(minput2);
        }

        // Menampilkan operasi hitung yang dipilih
        String operasi = "";
        if(operasiTambah.isChecked()){
            operasi = "+";
        } else if (operasiKurang.isChecked()) {
            operasi = "-";
        } else if (operasiKali.isChecked()) {
            operasi = "x";
        } else if (operasiBagi.isChecked()) {
            operasi = ":";
        }

        listKalkulator.add(new ItemList(minput1, operasi, minput2, String.valueOf(hasil)));
        mAdapter.notifyItemInserted(listKalkulator.size());
        return hasil;
    }

    private void saveData(){
        // Menyimpan data
        SharedPreferences shared_pref = getSharedPreferences("shared_preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared_pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listKalkulator);
        editor.putString("task_list", json);
        editor.apply();
    }

}