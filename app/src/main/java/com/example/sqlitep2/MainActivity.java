package com.example.sqlitep2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilitar el modo Edge-to-Edge
        EdgeToEdge.enable(this);

        // Configurar el layout de la actividad
        setContentView(R.layout.activity_main);

        // Ajustar los padding en función de las barras del sistema (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular los botones del layout
        Button btnUsuarios = findViewById(R.id.btn_list_user);
        Button btnProductos = findViewById(R.id.btn_list_product);
        Button btnCreateUser = findViewById(R.id.btn_crear_usuario);
        Button btnCreateProduct = findViewById(R.id.btn_crear_producto);

        // Configurar los listeners para los botones
        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para listar usuarios
                Intent intent = new Intent(MainActivity.this, list_user.class);
                startActivity(intent);
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para listar productos
                Intent intent = new Intent(MainActivity.this, list_product.class);
                startActivity(intent);
            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para crear usuario
                Intent intent = new Intent(MainActivity.this, create_user.class);
                startActivity(intent);
            }
        });

        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad para crear producto
                Intent intent = new Intent(MainActivity.this, create_product.class);
                startActivity(intent);
            }
        });
    }
}
