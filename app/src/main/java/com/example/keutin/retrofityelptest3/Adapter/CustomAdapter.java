package com.example.keutin.retrofityelptest3.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keutin.retrofityelptest3.Model.Business;
import com.example.keutin.retrofityelptest3.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private List<Business> data;
    private Context context;

    public CustomAdapter(Context context,List<Business> data){
        this.context = context;
        this.data = data;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtname;
        TextView txtphone;
        TextView txtcity;
        TextView txtadress1;
        TextView txtrating;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtname = mView.findViewById(R.id.txt_business_name);
            txtphone = mView.findViewById(R.id.txt_business_phone);
            txtcity = mView.findViewById(R.id.txt_business_city);
            txtadress1 = mView.findViewById(R.id.txt_business_address1);
            txtrating = mView.findViewById(R.id.txt_business_rating);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_business, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtname.setText(data.get(position).getName());
        holder.txtphone.setText(data.get(position).getDisplayPhone());
        holder.txtcity.setText(data.get(position).getLocation().getCity());
        holder.txtadress1.setText(data.get(position).getLocation().getAddress1());
        holder.txtrating.setText("Rating : " + data.get(position).getRating().toString());
            }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
