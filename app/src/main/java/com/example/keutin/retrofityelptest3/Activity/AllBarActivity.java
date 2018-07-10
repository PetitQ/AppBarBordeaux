package com.example.keutin.retrofityelptest3.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keutin.retrofityelptest3.Adapter.CustomAdapter;
import com.example.keutin.retrofityelptest3.BuildConfig;
import com.example.keutin.retrofityelptest3.Model.Business;
import com.example.keutin.retrofityelptest3.Model.Category;
import com.example.keutin.retrofityelptest3.Model.YelpAnswersResponse;
import com.example.keutin.retrofityelptest3.Network.GetDataService;
import com.example.keutin.retrofityelptest3.Network.RetrofitClientInstance;
import com.example.keutin.retrofityelptest3.R;
import com.example.keutin.retrofityelptest3.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bar);

        final ProgressDialog progressDialog = new ProgressDialog(AllBarActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        YelpAnswersResponse yelpAnswersResponse;
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<YelpAnswersResponse> call = service.getBusinessData("Bearer " + BuildConfig.ApiKey, "Bars", "Bordeaux", "50");
        final EditText EditTextRecherche = findViewById(R.id.edittextinfo_recherche);
        final Button rechercheButton = findViewById(R.id.buttoninfo_recherche);
        call.enqueue(new Callback<YelpAnswersResponse>() {
            @Override
            public void onResponse(Call<YelpAnswersResponse> call, Response<YelpAnswersResponse> response) {
                progressDialog.dismiss();
                YelpAnswersResponse test = response.body();
                generateDataList(response.body().getBusinesses());

                rechercheButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                List<Business> ListBusiRecherche = new ArrayList<Business>();
                                for (Business busi: response.body().getBusinesses()){
                                    if (TestPresenceCaractere(busi,EditTextRecherche.getText().toString()) == true)
                                        ListBusiRecherche.add(busi);
                                }
                                EditTextRecherche.setText("");
                                generateDataList(ListBusiRecherche);
                    }
                });
            }

            @Override
            public void onFailure(Call<YelpAnswersResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AllBarActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });



        final Button MenuButton = findViewById(R.id.buttonRetourMenu);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllBarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean TestPresenceCaractere (Business business, String uneChaine){
        Boolean res = false;
            if(business.getName().indexOf(uneChaine) >= 0){
                res = true;
            }
        return res;
    }

    private void generateDataList(final List<Business> Response) {
        RecyclerView recyclerView = findViewById(R.id.RV_business);
        recyclerView.removeAllViewsInLayout();
        RecyclerView.Adapter adapter = new CustomAdapter(this,Response);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllBarActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(AllBarActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(AllBarActivity.this, BarInfoActivity.class);
                        intent.putExtra("name", Response.get(position).getName());
                        intent.putExtra("tel", Response.get(position).getDisplayPhone());
                        intent.putExtra("urlimg", Response.get(position).getImageUrl());
                        intent.putExtra("latitude", Double.toString(Response.get(position).getCoordinates().getLatitude()));
                        intent.putExtra("longitude", Double.toString(Response.get(position).getCoordinates().getLongitude()));
                        intent.putExtra("nbCateg" , Integer.toString(Response.get(position).getCategories().size()));
                        intent.putExtra("adr", Response.get(position).getLocation().getAddress1());
                        intent.putExtra("rating",Double.toString( Response.get(position).getRating()));

                        int x = 0;
                        for (Category categ: Response.get(position).getCategories()) {
                            intent.putExtra("categ" + Integer.toString(x), Response.get(position).getCategories().get(x).getTitle());
                            x+=1;
                        }

                        if (Response.get(position).getIsClosed() == true){
                            intent.putExtra("isclosed", "OUVERT");
                        }
                        else {
                            intent.putExtra("isclosed", "FERME");
                        }

                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }


}
