package com.example.keutin.retrofityelptest3.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.keutin.retrofityelptest3.Model.Category;
import com.example.keutin.retrofityelptest3.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BarInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);

        Intent intentres = getIntent();
        final TextView txtname = findViewById(R.id.txtinfo_name);
        final TextView txtphone = findViewById(R.id.txtinfo_tel);
        final TextView txtbooouvert = findViewById(R.id.txtinfo_ouvert);
        final TextView txtadr = findViewById(R.id.txtinfo_adresse);
        final TextView txtrating = findViewById(R.id.txtinfo_rating);
        final ImageView imgBusiness = findViewById(R.id.imageviewinfo_photo);
        final ListView lvCateg = findViewById(R.id.Lv_categ);


        txtname.setText(intentres.getStringExtra("name"));
        txtphone.setText(intentres.getStringExtra("tel"));
        txtadr.setText(intentres.getStringExtra("adr"));
        txtrating.setText("rating : " + intentres.getStringExtra("rating"));
        txtbooouvert.setText(intentres.getStringExtra("isclosed"));
        Picasso.with(BarInfoActivity.this).load(intentres.getStringExtra("urlimg")).into(imgBusiness);

        ArrayList<String> lescateg = new ArrayList<String>();
        int x = 0;
        while (Integer.parseInt(intentres.getStringExtra("nbCateg")) > x  ){
            lescateg.add(intentres.getStringExtra("categ" + Integer.toString(x)));
            x+=1;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lescateg );

        lvCateg.setAdapter(arrayAdapter);

        final Button MapButton = findViewById(R.id.buttoninfo_ToCarte);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarInfoActivity.this, BarInfoMapActivity.class);
                intent.putExtra("latitude", intentres.getStringExtra("latitude"));
                intent.putExtra("longitude", intentres.getStringExtra("longitude"));
                intent.putExtra("name", intentres.getStringExtra("name"));
                startActivity(intent);
            }
        });

        final Button MenuButton = findViewById(R.id.buttoninfoMenu);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
