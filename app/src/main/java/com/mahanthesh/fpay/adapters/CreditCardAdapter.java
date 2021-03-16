package com.mahanthesh.fpay.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cooltechworks.creditcarddesign.CreditCardView;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.model.SavedCardModel;

import java.util.List;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.CreditCardViewHolder> {

    private List<SavedCardModel> savedCardModelList;

    public void setSavedCardModelList(List<SavedCardModel> savedCardModelList) {
        this.savedCardModelList = savedCardModelList;
    }

    @NonNull
    @Override
    public CreditCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_credit_card, parent, false);
        return new CreditCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCardViewHolder holder, int position) {
        holder.creditCardView.setCardHolderName(savedCardModelList.get(position).getCardHolderName());
        holder.creditCardView.setCardNumber(savedCardModelList.get(position).getCardHolderNumber());
        holder.creditCardView.setCVV(savedCardModelList.get(position).getCardCVV());
        holder.creditCardView.setCardExpiry(savedCardModelList.get(position).getCardExpiry());
    }

    @Override
    public int getItemCount() {
        if(savedCardModelList == null)
            return 0;
        else
            return savedCardModelList.size();
    }

    public class CreditCardViewHolder extends RecyclerView.ViewHolder {

        private CreditCardView creditCardView;


        public CreditCardViewHolder(@NonNull View itemView) {
            super(itemView);

            creditCardView = (CreditCardView) itemView.findViewById(R.id.credit_card_view);
        }
    }
}
