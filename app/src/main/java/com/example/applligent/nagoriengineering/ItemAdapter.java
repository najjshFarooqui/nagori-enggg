package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> implements Filterable {
    List<Item> items;
    List<Item> itemsFull;
    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Item searchItem : itemsFull) {
                    if (searchItem.oem.toLowerCase().contains(filterPattern)) {
                        filteredList.add(searchItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public ItemAdapter(List<Item> items) {
        this.items = items;
        itemsFull = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public ItemAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.activity_part_list, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.bindTo(items.get(i));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    protected class ItemHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView companyName;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            companyName = itemView.findViewById(R.id.companyName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = items.get(getAdapterPosition());
                    Intent intent = new Intent(itemView.getContext(), CompanyDetailsActivity.class);
                    intent.putExtra("oem", item.id);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        protected void bindTo(Item items) {

            companyName.setText(items.oem);
        }
    }
}

