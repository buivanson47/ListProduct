package hust.soict.it2.sonbv.productdiscovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;

import hust.soict.it2.sonbv.productdiscovery.adapter.ImagePagerAdapter;
import hust.soict.it2.sonbv.productdiscovery.adapter.InformationDetailPagerAdapter;
import hust.soict.it2.sonbv.productdiscovery.adapter.OtherProductAdapter;
import hust.soict.it2.sonbv.productdiscovery.R;
import hust.soict.it2.sonbv.productdiscovery.database.ProductDbHelper;
import hust.soict.it2.sonbv.productdiscovery.model.Product;

import static hust.soict.it2.sonbv.productdiscovery.activity.MainActivity.DB_NAME;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNameInfo;
    private TextView tvPriceSale;
    private TextView tvCode;
    private TextView tvPriceTitle;
    private TextView tvPriceInfo;
    private TextView tvNameTitle;
    private Context context;
    private Product product;

    SpringDotsIndicator springDotsIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = DetailActivity.this;
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("KEY_PRODUCT");
        setDataProductSelected();

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setTabLayoutInformationDetail();
        buildRecyclerViewForOtherProduct();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.price_shopping);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.background_price_shopping));
    }


    private void setTabLayoutInformationDetail() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_description));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_Parameter));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_compare));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewInfoPager = findViewById(R.id.info_detail_pager);
        final InformationDetailPagerAdapter adapter = new InformationDetailPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewInfoPager.setAdapter(adapter);
        viewInfoPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewInfoPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setDataProductSelected() {
        tvNameTitle = findViewById(R.id.name_title);
        tvNameInfo = findViewById(R.id.info_name);
        tvCode = findViewById(R.id.info_code);
        tvPriceTitle = findViewById(R.id.price_title);
        tvPriceInfo = findViewById(R.id.info_price_cur);
        tvPriceSale = findViewById(R.id.info_price_sale);

        tvNameTitle.setText(product.getName());
        tvPriceTitle.setText(product.getPrice() + " đ");
        tvNameInfo.setText(product.getName());
        tvPriceInfo.setText(product.getPrice() + " đ");
        tvCode.setText(product.getCode());
        tvPriceSale.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        String[] images = new String[]{product.getImageUrl(), product.getImageUrl(),
                product.getImageUrl(), product.getImageUrl()};
        ViewPager viewPager = findViewById(R.id.viewpager);
        springDotsIndicator = findViewById(R.id.spring_dots_indicator);
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(context, images);
        viewPager.setAdapter(imagePagerAdapter);
        springDotsIndicator.setViewPager(viewPager);
    }

    private void buildRecyclerViewForOtherProduct() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_other_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
        ArrayList<Product> listOtherProduct = new ArrayList<>();
        ProductDbHelper productDbHelper = new ProductDbHelper(this, DB_NAME);
        listOtherProduct = productDbHelper.getOtherProduct();
        OtherProductAdapter adapter = new OtherProductAdapter(listOtherProduct, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_detail, menu);
        return true;
    }
}