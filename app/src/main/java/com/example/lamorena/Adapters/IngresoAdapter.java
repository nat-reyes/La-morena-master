package com.example.lamorena.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamorena.Activities.IngresoItem;
import com.example.lamorena.R;

import java.util.List;

public class IngresoAdapter extends RecyclerView.Adapter<IngresoAdapter.ViewHolder> implements Filterable {

    private List<IngresoItem> mData;
    private LayoutInflater mInflater;
    private IngresoAdapter.ItemClickListener mClickListener;


    public IngresoAdapter(Context context, List<IngresoItem> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }


    @Override
    public IngresoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_cardview_ingresos, parent, false);
        return new IngresoAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull IngresoAdapter.ViewHolder holder, int position) {

          IngresoItem row = mData.get(position);

        holder.textViewTitulo.setText(row.getTitle());
        holder.textViewSubt.setText(row.getSubtitle());

    }




    @Override
    public int getItemCount() {
        return mData.size();

    }

    public Filter getFilter() {
        // consulta en firebase
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         public TextView textViewTitulo, textViewSubt;
        public ImageView stateImag;

        ViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.tvPlacaRevision);
            textViewSubt = itemView.findViewById(R.id.tvEstado);
            stateImag = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }



    }

    // convenience method for getting data at click position

    // allows clicks events to be caught
    void setClickListener(IngresoAdapter.ItemClickListener itemClickListener) {
    //    this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
