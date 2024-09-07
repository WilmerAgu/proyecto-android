package com.example.sqlitep2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sqlitep2.data.adapter.ProductAdapter;
import com.example.sqlitep2.data.dao.ProductDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.Product;
import java.util.List;

public class list_product extends AppCompatActivity {
    private static final String TAG = "ListProductActivity";
    private static final int REQUEST_CODE_CREATE_PRODUCT = 1;

    private DatabaseManager dbManager;
    private ProductDao productDao;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);


        dbManager = DatabaseManager.getInstance(this);
        productDao = new ProductDao(dbManager.openDatabase());


        recyclerView = findViewById(R.id.recyclerViewProduct);
        adapter = new ProductAdapter(getProductsFromDatabase());
        adapter.setOnItemClickListener(product -> {
            Toast.makeText(this, "Producto seleccionado: " + product.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button buttonReturn = findViewById(R.id.exit_list_product);
        buttonReturn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE_PRODUCT && resultCode == RESULT_OK) {
            updateProductList();
        }
    }

    private void updateProductList() {
        List<Product> products = getProductsFromDatabase();
        adapter.updateProducts(products);
    }

    private List<Product> getProductsFromDatabase() {
        List<Product> allProducts = productDao.getAllProducts();
        dbManager.closeDatabase();
        return allProducts;
    }
}
