package hust.soict.it2.sonbv.productdiscovery.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hust.soict.it2.sonbv.productdiscovery.adapter.ProductAdapter;
import hust.soict.it2.sonbv.productdiscovery.R;
import hust.soict.it2.sonbv.productdiscovery.api.ApiService;
import hust.soict.it2.sonbv.productdiscovery.database.ProductDbHelper;
import hust.soict.it2.sonbv.productdiscovery.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Product> listProduct;
    private ProductAdapter mAdapter;
    public static final String DB_NAME = "myDATABASE.db";
    private ProductDbHelper productDbHelper;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productDbHelper = new ProductDbHelper(MainActivity.this, DB_NAME);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        buildRecyclerView();
        setIconBack();
        getDataProduct();
        searchProduct();
    }

    private void setIconBack() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void searchProduct() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview_product);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listProduct = new ArrayList<>();
        mAdapter = new ProductAdapter(listProduct, MainActivity.this);

        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getDataProduct() {
        ApiService.apiService.getListProduct()
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        listProduct = response.body();
                        mAdapter.submitData(listProduct);

                        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
                        if (!(isTableExist(db, "product"))) {
                            productDbHelper.insertDataToDB(listProduct);
                            // Toast.makeText(MainActivity.this, "Save DATA into DB", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        // Toast.makeText(MainActivity.this, "NO INTERNET", Toast.LENGTH_SHORT).show();
                        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
                        if (isTableExist(db, "product")) {
                            listProduct = productDbHelper.getAllProduct();
                            mAdapter.submitData(listProduct);
                        } else
                            Toast.makeText(MainActivity.this, "Get data from DB error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(ProgressBar.INVISIBLE);

                    }
                });
    }


    // Kiem tra bang duoc tao hay chua
    private boolean isTableExist(SQLiteDatabase db, String nameTable) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{nameTable});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }


}