package com.example.productlist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private  ArrayList<Produit> produits;
    //private Context context;
    private Activity context;
    byte[] decodedString;

    public ProductAdapter(Activity context , ArrayList<Produit> produits) {
        this.produits = produits;
        this.context = context;
    }

    @Override
    public int getCount() {
        return produits.size();
    }

    @Override
    public Object getItem(int position) {
        return produits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //LayoutInflater: instancier le fichier XML
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.productlayout, null);
        }
        //************************************
        TextView txtLibelle = convertView.findViewById(R.id.Lib);
        TextView txtCodeBarre=convertView.findViewById(R.id.codeB);
        TextView txtPrice=convertView.findViewById(R.id.prix);
        TextView Disponible=convertView.findViewById(R.id.diponible);
        CheckBox check =convertView.findViewById(R.id.check);
        ImageView imageView=convertView.findViewById(R.id.image)  ;

        produits.get(position).setId(position);

        txtLibelle.setText("LIbelle: "+produits.get(position).getLibelle());
        txtCodeBarre.setText("code: "+produits.get(position).getCodeBarre());
        txtPrice.setText("prix: "+produits.get(position).getPrix());
        if(produits.get(position).getDisponible()) {
            Disponible.setText("disponible");
        }
        else{
            Disponible.setText("Hors stock");
        }
        check.setChecked(produits.get(position).getCheckTask());

        decodedString = Base64.decode(produits.get(position).getImage(),Base64.DEFAULT);
        Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        imageView.setImageBitmap(decodedByte);
        check.setOnCheckedChangeListener((vw, isChecked) -> (produits.get(position)).setCheckTask(
                isChecked));
        return convertView;
    }

    public  ArrayList<Produit> getChecked() {
        ArrayList<Produit> lesTaches = new ArrayList<>();
        for (Produit p : produits) {
            if (p.getCheckTask())
                lesTaches.add(p);
        }
        return lesTaches;
    }
    public void  ClearSelection()
    {
        for (Produit p: produits)
            p.setCheckTask(false);
    }

    public void remove(Produit i){
        produits.remove(i);
    }

    @Override
    public boolean isEmpty() {

        return produits.isEmpty();
    }
}
