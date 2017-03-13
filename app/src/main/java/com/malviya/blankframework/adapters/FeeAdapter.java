package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.models.TableFeeMasterDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

    public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableFeeMasterDataModel> mList;

    public FeeAdapter(Context context, ArrayList<TableFeeMasterDataModel> pList) {
        mContext = context;
        mList = pList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.activity_fee_row, parent,false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    }


    @Override
    public int getItemCount()
    {
        return 10;
       // return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewPaymentValue;
        public Button btnViewDetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.textview_fee_row_date_vaule);
            textViewPaymentValue = (TextView) itemView.findViewById(R.id.textview_fee_row_part_payment_value);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_view_details);
        }
    }
}
