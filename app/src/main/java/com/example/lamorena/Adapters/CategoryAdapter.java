package com.example.lamorena.Adapters;

import android.app.Activity;
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

import com.example.lamorena.Entities.Category;
import com.example.lamorena.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCategory> implements Filterable {

    ArrayList<Category> categories;
    ArrayList<Category> categoriesFiltered;
    private Activity mActivity;
    private Context mcontext;
    private Gson gson;
    private OnCategoryClickListener categoryClickListener;
    ViewGroup parent;

    public CategoryAdapter(ArrayList<Category> categories, Activity mActivity, Context mcontext, OnCategoryClickListener categoryClickListener) {
        this.categories = categories;
        this.mActivity = mActivity;
        this.mcontext = mcontext;
        this.categoriesFiltered= categories;
        gson = new Gson();
        this.categoryClickListener = categoryClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    categoriesFiltered = categories;
                } else {
                    ArrayList <Category> filteredList = new ArrayList<>();
                    for (Category row : categories) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    categoriesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = categoriesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categoriesFiltered = (ArrayList<Category>) filterResults.values;
//                if(categoriesFiltered.isEmpty()) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_preview_item,parent,false);
        return new ViewHolderCategory(view,categoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategory holder, int position) {
        //holder.nombre.setText(categoriesFiltered.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoriesFiltered.size();
    }



    public class ViewHolderCategory extends RecyclerView.ViewHolder implements View.OnClickListener {

         private ImageView imageView;
         private TextView nombre,cant;
         OnCategoryClickListener categoryClickListener;
        public ViewHolderCategory(@NonNull View itemView, OnCategoryClickListener categoryClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.categoryImage);
            nombre = (TextView) itemView.findViewById(R.id.categoryName);
            //cant = (TextView) itemView.findViewById(R.id.categoryCant);
            this.categoryClickListener = categoryClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            categoryClickListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryClickListener {
        void onCategoryClick (int pos);
    }
}
