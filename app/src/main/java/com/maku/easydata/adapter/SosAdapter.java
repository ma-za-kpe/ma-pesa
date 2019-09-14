package com.maku.easydata.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maku.easydata.R;
import com.maku.easydata.ShareAirtimeActivity;
import com.maku.easydata.interfaces.APIService;
import com.maku.easydata.models.serverrequest.SosRequest;
import com.maku.easydata.models.serverresponse.SosResponse2;
import com.maku.easydata.models.serverresponse.SosResult;
import com.maku.easydata.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SosAdapter extends RecyclerView.Adapter<SosAdapter.ViewHolder> {

    private static final String TAG = "SosAdapter";

    private ArrayList<SosResult> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private APIService mAPIService;

    public SosAdapter(Context context, ArrayList<SosResult> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public SosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sos_row, parent, false);
        /*Initialise the api service here*/
        mAPIService = ApiUtils.getAPIService();
        return new SosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SosAdapter.ViewHolder holder, final int i) {

        String name = mData.get(i).getNickName();
        int amount = mData.get(i).getAmount();
        String network = mData.get(i).getNetwork();
        /*values to pass to onclick function*/
        final String code = mData.get(i).getCountryCode();
        final String phone = mData.get(i).getPhoneNumber();
        final String amo = String.valueOf(amount);

        /*delete sos*/
        final String id = mData.get(i).getId();

        /*set values in the views*/
        holder.mTextViewNom.setText(name);
        holder.mTextViewAmount.setText(amo);
        holder.mTextViewNetwork.setText(network);
        holder.mButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + id);
                ((ShareAirtimeActivity) mContext).onClickCalled(code, phone, amo);
                int adapterPos = holder.getAdapterPosition();

                removeItem(adapterPos);

                serverDelete(id);
            }

            private void removeItem(int position) {
                mData.remove(position);
                notifyItemRemoved(position);
            }

            private void serverDelete(String id) {
                mAPIService.deleteSos(id).enqueue(new Callback<SosResult>() {
                    @Override
                    public void onResponse(Call<SosResult> call, Response<SosResult> response) {

                        if (response.isSuccessful())  {
                            Log.d(TAG, "onResponse: " + response.message());
                            Toast.makeText(mContext, "Sos removed", Toast.LENGTH_LONG).show();
//                            process(response);
                        }else{
                            Log.d(TAG, "onResponse: there is no response"  + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<SosResult> call, Throwable t) {
                        Log.e(TAG,t.toString());
                    }
                });
            }

        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewNom;
        TextView mTextViewAmount;
        TextView mTextViewNetwork;
        Button mButtonShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewNom = itemView.findViewById(R.id.nom);
            mTextViewAmount = itemView.findViewById(R.id.amount);
            mTextViewNetwork = itemView.findViewById(R.id.network);
            mButtonShare = itemView.findViewById(R.id.shareAT);
        }
    }
}
