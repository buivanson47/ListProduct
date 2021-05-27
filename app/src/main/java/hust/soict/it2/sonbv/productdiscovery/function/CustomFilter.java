package hust.soict.it2.sonbv.productdiscovery.function;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import hust.soict.it2.sonbv.productdiscovery.adapter.ProductAdapter;
import hust.soict.it2.sonbv.productdiscovery.model.Product;

public class CustomFilter extends Filter {
    ProductAdapter adapter;
    ArrayList<Product> listProduct;

    public CustomFilter(ProductAdapter adapter, ArrayList<Product> listProduct) {
        this.adapter = adapter;
        this.listProduct = listProduct;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toLowerCase(Locale.getDefault()).trim();

            ArrayList<Product> listRes = new ArrayList<>();
            for (Product product : listProduct) {
                String namePrd = product.getName().toLowerCase(Locale.getDefault()).trim();
                Vector<CharSequence> nameChild = new Vector<>();
                int begin = 0;
                for (int i = 0; i < constraint.length(); i++) {
                    if (constraint.charAt(i) == ' ') {
                        nameChild.add(constraint.subSequence(begin, i));
                        begin = i + 1;
                    }
                }
                nameChild.add(constraint.subSequence(begin, constraint.length()));

                boolean check = true;
                for (CharSequence s : nameChild) {
                    if (!namePrd.contains(s)) {
                        check = false;
                        break;
                    }
                }
                if(check) listRes.add(product);
            }
            results.count = listRes.size();
            results.values = listRes;
        } else {
            results.count = listProduct.size();
            results.values = listProduct;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.submitData((ArrayList<Product>) results.values);

    }
}
