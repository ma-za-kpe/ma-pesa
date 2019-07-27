package com.maku.easydata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maku.easydata.R;
import com.maku.easydata.models.DataModel;

import java.util.ArrayList;

public class DataRecyclerviewAdapter extends RecyclerView.Adapter<DataRecyclerviewAdapter.ViewHolder>  {


    //vars
    private ArrayList<DataModel> mData = new ArrayList<>();
    private Context mContext;

    public DataRecyclerviewAdapter(ArrayList<DataModel> mdata, Context context) {
        mData = mdata;
        mContext = context;
    }

    @NonNull
    @Override
    public DataRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_data, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataRecyclerviewAdapter.ViewHolder holder, int position) {

        final DataModel dataModel = mData.get(position);

        String item = mData.get(position).getName();
//        String[] data = mData.get(position).split("\\s+");

        holder.name.setText(item);
        holder.price.setText(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
