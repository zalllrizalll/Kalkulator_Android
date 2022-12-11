package com.example.kalkulatorandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SharedPreferenceAdapter extends RecyclerView.Adapter<SharedPreferenceAdapter.ViewHolder> {

    private final ArrayList<ItemList> listKalkulator;
    private Context context;
    private SharedPreferences sharedPreferences;

    // Constructor
    public SharedPreferenceAdapter(ArrayList<ItemList> listKalkulator)
    {
        this.listKalkulator = listKalkulator;
    }

    @NonNull
    @Override
    public SharedPreferenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        this.sharedPreferences = context.getSharedPreferences("shared_preference", Context.MODE_PRIVATE);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.item_list, parent, false));

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ItemList kalkulator = listKalkulator.get(position);
        holder.txtInput1.setText(kalkulator.getInput1());
        holder.txtOperasi.setText(kalkulator.getOperasi());
        holder.txtInput2.setText(kalkulator.getInput2() + "    =");
        holder.txtHasil.setText(kalkulator.getHasil());
        // Menambahkan setOnLongClickListener ke Layout
        holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Mendapatkan posisi item yang dipilih
                final int position = listKalkulator.indexOf(kalkulator);

                // Menampilkan dialog konfirmasi
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Hapus Riwayat")
                        .setMessage("Apakah anda yakin ingin menghapus riwayat ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Menghapus item dari list
                                listKalkulator.remove(position);

                                // Notifikasi adanya perubahan data
                                notifyDataSetChanged();

                                // Menyimpan perubahan data ke SharedPreference
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(listKalkulator);
                                editor.putString("task_list", json);
                                editor.apply();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert.create().show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKalkulator.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtInput1,txtOperasi, txtInput2, txtHasil;
        public RelativeLayout mLayout;

        public ViewHolder (View itemView)
        {
            super(itemView);
            txtInput1 = (TextView) itemView.findViewById(R.id.ViewInput1);
            txtOperasi = (TextView) itemView.findViewById(R.id.ViewOperasi);
            txtInput2 = (TextView) itemView.findViewById(R.id.ViewInput2);
            txtHasil = (TextView) itemView.findViewById(R.id.ViewHasil);
            this.mLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
        }
    }
}
