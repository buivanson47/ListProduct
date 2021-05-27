package hust.soict.it2.sonbv.productdiscovery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hust.soict.it2.sonbv.productdiscovery.model.Product;

public class ProductDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "product";

    public ProductDbHelper(@Nullable Context context, String DB_NAME) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE product ( " +
                "id VARCHAR(255) NOT NULL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "imageUrl VARCHAR(255) NOT NULL, " +
                "dateAdded VARCHAR(255) NOT NULL, " +
                "dateUpdated VARCHAR(255) NOT NULL, " +
                "price FLOAT DEFAULT (0), " +
                "brand VARCHAR(255) NOT NULL, " +
                "code VARCHAR(255) NOT NULL" +
                ")";
        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> listProduct = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM product", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageUrl = cursor.getString(2);
            String dateAdded = cursor.getString(3);
            String dateUpdated = cursor.getString(4);
            Float price = cursor.getFloat(5);
            String brand = cursor.getString(6);
            String code = cursor.getString(7);

            listProduct.add(new Product(id, name, imageUrl, dateAdded, dateUpdated, price, brand, code));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listProduct;
    }

    public ArrayList<Product> getOtherProduct() {
        ArrayList<Product> listProduct = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT*FROM product LIMIT 5", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageUrl = cursor.getString(2);
            String dateAdded = cursor.getString(3);
            String dateUpdated = cursor.getString(4);
            Float price = cursor.getFloat(5);
            String brand = cursor.getString(6);
            String code = cursor.getString(7);

            listProduct.add(new Product(id, name, imageUrl, dateAdded, dateUpdated, price, brand, code));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return listProduct;
    }

    public Product getProductByID(int ID) {
        Product product = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, price from product where id = ?",
                new String[]{ID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String imageUrl = cursor.getString(2);
            String dateAdded = cursor.getString(3);
            String dateUpdated = cursor.getString(4);
            Float price = cursor.getFloat(5);
            String brand = cursor.getString(6);
            String code = cursor.getString(7);
            product = new Product(id, name, imageUrl, dateAdded, dateUpdated, price, brand, code);
        }
        cursor.close();
        db.close();
        return product;
    }

    public void insertDataToDB(ArrayList<Product> listProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < listProduct.size(); i++) {
            Product product = listProduct.get(i);
            db.execSQL("INSERT INTO product (id, name, imageUrl, dateAdded, dateUpdated, price, brand, code) VALUES (?,?,?,?,?,?,?,?)",
                    new String[]{product.getId() + "", product.getName(), product.getImageUrl(),
                            product.getDateAdded(), product.getDateUpdated(), product.getPrice() + "",
                            product.getBrand(), product.getCode()});
        }
        db.close();
    }


}
