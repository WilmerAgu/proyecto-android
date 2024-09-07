package com.example.sqlitep2.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sqlitep2.R;
import com.example.sqlitep2.data.model.Product;
import java.util.List;

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private List<Product> products;
        private OnItemClickListener listener;

        public ProductAdapter(List<Product> products) {
            this.products = products;
        }

        public interface OnItemClickListener {
            void onItemClick(Product product);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Product product = products.get(position);
            holder.productName.setText(product.getName());
            holder.productDescripcion.setText(String.format("$%.2f", product.getPrice()));


            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public void updateProducts(List<Product> products) {
            this.products = products;
            notifyDataSetChanged();  // Notifica al adapter que los datos han cambiado
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView productName;
            public TextView productDescripcion;
            public ImageView productImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.productName);
                productDescripcion = itemView.findViewById(R.id.productPrice);
            }
        }
    }