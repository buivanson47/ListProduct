package hust.soict.it2.sonbv.productdiscovery.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hust.soict.it2.sonbv.productdiscovery.R;
import hust.soict.it2.sonbv.productdiscovery.model.Product;

public class OtherProductAdapter extends RecyclerView.Adapter<OtherProductAdapter.ViewHolder> {
    private ArrayList<Product> listOtherProduct;
    private Context mContext;

    public OtherProductAdapter(ArrayList<Product> listOtherProduct, Context mContext) {
        this.listOtherProduct = listOtherProduct;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OtherProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_other_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OtherProductAdapter.ViewHolder holder, int position) {
        Product curProduct = listOtherProduct.get(position);
        holder.bindView(curProduct);
    }

    @Override
    public int getItemCount() {
        return listOtherProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgOtherProduct;
        private TextView tvOtherName;
        private TextView tvOtherPrice;
        private TextView tvOtherPriceSale;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOtherProduct = itemView.findViewById(R.id.img_other_product);
            tvOtherName = itemView.findViewById(R.id.name_other_product);
            tvOtherPrice = itemView.findViewById(R.id.price_other_product);
            tvOtherPriceSale = itemView.findViewById(R.id.price_sale_othProduct);
        }

        public void bindView(Product curProduct) {
            Glide.with(mContext)
                    .load(curProduct.getImageUrl())
                    .centerCrop()
                    .into(imgOtherProduct);
            tvOtherName.setText(curProduct.getName());
            tvOtherPrice.setText(curProduct.getPrice() + " đ");
            tvOtherPriceSale.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
