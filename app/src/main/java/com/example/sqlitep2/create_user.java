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

import com.example.sqlitep2.data.dao.UserDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.User;
public class create_user extends AppCompatActivity {
    DatabaseManager dbManager;
    private UserDao userDao;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextImageUrl;
    private Button buttonCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTextUsername = findViewById(R.id.txt_user_name);
        editTextEmail = findViewById(R.id.txt_user_email);
        editTextImageUrl = findViewById(R.id.txt_user_img);
        buttonCreateUser = findViewById(R.id.btn_save_user);

        dbManager = DatabaseManager.getInstance(this);
        userDao = new UserDao(dbManager.openDatabase());

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        Button buttonReturn = findViewById(R.id.exit_create_user);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createUser() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String imageUrl = editTextImageUrl.getText().toString();


        if (username.isEmpty() || email.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(username, email, imageUrl);

        long id = userDao.insert(user);

        if (id > 0) {
            Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDatabase();
    }
}