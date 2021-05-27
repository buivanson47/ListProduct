package hust.soict.it2.sonbv.productdiscovery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

import hust.soict.it2.sonbv.productdiscovery.R;
import hust.soict.it2.sonbv.productdiscovery.activity.DetailActivity;
import hust.soict.it2.sonbv.productdiscovery.function.CustomFilter;
import hust.soict.it2.sonbv.productdiscovery.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    private ArrayList<Product> listProduct;
    private Context mContext;
    private CustomFilter filter;

    public ProductAdapter(ArrayList<Product> listProduct, Context context) {
        this.listProduct = listProduct;
        this.mContext = context;
    }

    public void submitData(ArrayList<Product> listProduct) {
        this.listProduct = listProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product curProduct = listProduct.get(position);
        holder.bindView(curProduct);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(this, listProduct);
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvStrike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.image_product);
            tvName = itemView.findViewById(R.id.name_product);
            tvPrice = itemView.findViewById(R.id.price_product);
            tvStrike = itemView.findViewById(R.id.price_strikeThr);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getLayoutPosition();
                    Product curProduct = listProduct.get(index);
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra("KEY_PRODUCT", (Serializable) curProduct);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void bindView(Product curProduct) {
            Glide.with(mContext)
                    .load(curProduct.getImageUrl())
                    .centerCrop()
                    .into(imgProduct);
            tvName.setText(curProduct.getName());
            tvPrice.setText(curProduct.getPrice() + " đ");
            tvStrike.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }


}
