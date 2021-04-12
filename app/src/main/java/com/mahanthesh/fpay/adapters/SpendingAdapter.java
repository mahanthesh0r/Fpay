package com.mahanthesh.fpay.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.TransactionModel;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder> {

    private List<TransactionModel> transactionModelList;
    private NumberFormat nf = NumberFormat.getInstance();
    private static final String TAG = "SpendingAdapter";
    private int MAX_LOAD = 0;

    public void setTransactionModelList(List<TransactionModel> transactionModelList) {
        this.transactionModelList = transactionModelList;
    }

    public SpendingAdapter(int max){
        this.MAX_LOAD = max;
    }

    @NonNull
    @Override
    public SpendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_spending, parent, false);
        return new SpendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpendingViewHolder holder, int position) {

            if(transactionModelList.get(position).getUserInfo().getFirstName() != null)
                holder.textViewName.setText(transactionModelList.get(position).getUserInfo().getFirstName());
            if(transactionModelList.get(position).getUserInfo().getPhoneNo() != null)
                holder.textViewPhone.setText(transactionModelList.get(position).getUserInfo().getPhoneNo());
            if(transactionModelList.get(position).getCredited()){
                holder.textViewAmount.setTextColor(Color.parseColor("#35D806"));
                holder.textViewAmount.setText("+ " + nf.format(transactionModelList.get(position).getAmount()));
            } else {
                holder.textViewAmount.setTextColor(Color.RED);
                holder.textViewAmount.setText("- " + nf.format(transactionModelList.get(position).getAmount()));
            }
    }

    @Override
    public int getItemCount() {
        if(transactionModelList == null)
            return 0;
//        else if(MAX_LOAD != 0)
//            return MAX_LOAD;
        else
            return transactionModelList.size();
    }

    public class SpendingViewHolder extends RecyclerView.ViewHolder{

        private TextView  textViewName, textViewPhone, textViewAmount;
        private ImageView imageViewUser;

        public SpendingViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.tv_name);
            textViewPhone = itemView.findViewById(R.id.tv_phone);
            textViewAmount = itemView.findViewById(R.id.tv_amount);
            imageViewUser = itemView.findViewById(R.id.iv_profile);
        }
    }
}
