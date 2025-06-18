package com.example.mobile_programming;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapterHistory extends RecyclerView.Adapter<TransactionAdapterHistory.ViewHolder> {
    private List<Transaction> listTransaction;
    private Context context;
    public TransactionAdapterHistory(List<Transaction> listTransaction, Context context){
        this.listTransaction = listTransaction;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaksi = listTransaction.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailTransactionActivity.class);
                i.putExtra("Category",transaksi.getCategory());
                i.putExtra("Date",transaksi.getDate());
                i.putExtra("Amount",transaksi.getAmount());
                i.putExtra("Desc",transaksi.getDescription());
                context.startActivity(i);
            }
        });
        holder.category.setText(transaksi.getCategory());
        holder.date.setText(transaksi.getDate());
        holder.amount.setText(Double.toHexString(transaksi.getAmount()));
        holder.desc.setText(transaksi.getDescription());
    }

    @Override
    public int getItemCount() {
        return  listTransaction.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category, date, amount, desc;
        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
