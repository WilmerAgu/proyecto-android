package com.example.sqlitep2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlitep2.data.dao.ProductDao;
import com.example.sqlitep2.data.dao.UserDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.Product;
import com.example.sqlitep2.data.model.User;

public class create_product extends AppCompatActivity {
    private DatabaseManager dbManager;
    private ProductDao productDao;
    private EditText editTextProductName;
    private EditText editTextProductPrice;
    private Button buttonCreateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        // Inicializar vistas
        editTextProductName = findViewById(R.id.txt_product_name);
        editTextProductPrice = findViewById(R.id.txt_product_price);
        buttonCreateProduct = findViewById(R.id.btn_save_product);

        // Inicializar DatabaseManager y ProductDao
        dbManager = DatabaseManager.getInstance(this);
        productDao = new ProductDao(dbManager.openDatabase());

        // Configurar botón para crear producto
        buttonCreateProduct.setOnClickListener(v -> createProduct());

        // Configurar botón de regreso
        Button buttonReturn = findViewById(R.id.exit_create_product);
        buttonReturn.setOnClickListener(v -> finish());
    }

    private void createProduct() {
        String name = editTextProductName.getText().toString();
        String priceString = editTextProductPrice.getText().toString();

        // Validar campos
        if (name.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear nuevo producto
        Product product = new Product(name, (int) price);

        // Insertar producto en la base de datos
        long id = productDao.insert(product);

        if (id > 0) {
            Toast.makeText(this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK); // Indica que se ha creado un producto exitosamente
            finish(); // Cierra la actividad actual
        } else {
            Toast.makeText(this, "Error al crear producto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDatabase();
    }
}