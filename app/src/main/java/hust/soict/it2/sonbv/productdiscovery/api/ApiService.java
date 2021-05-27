package hust.soict.it2.sonbv.productdiscovery.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import hust.soict.it2.sonbv.productdiscovery.model.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    // https://run.mocky.io/v3/7af6f34b-b206-4bed-b447-559fda148ca5

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @GET("v3/7af6f34b-b206-4bed-b447-559fda148ca5")
    Call<ArrayList<Product>> getListProduct();

}
