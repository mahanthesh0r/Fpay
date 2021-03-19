package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.adapters.SpendingAdapter;
import com.mahanthesh.fpay.model.TransactionModel;
import com.mahanthesh.fpay.viewModel.TransactionViewModel;

import java.util.List;

public class AllTransactionActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSpendings;
    private TransactionViewModel transactionViewModel;
    private SpendingAdapter spendingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();


    }

    private void init(){
        recyclerViewSpendings = findViewById(R.id.rv_transaction_history);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        spendingAdapter = new SpendingAdapter(0);
        recyclerViewSpendings.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerViewSpendings.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewSpendings.setAdapter(spendingAdapter);
        fetchSpendings();
    }

    private void fetchSpendings(){
        transactionViewModel.getTransactionListViewModel().observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModelList) {
                spendingAdapter.setTransactionModelList(transactionModelList);
                spendingAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}