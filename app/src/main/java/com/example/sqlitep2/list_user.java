package com.example.sqlitep2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitep2.data.adapter.UserAdapter;
import com.example.sqlitep2.data.dao.UserDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.User;

import java.util.List;

public class list_user extends AppCompatActivity {
    private static final String TAG = "ListUserActivity";
    private UserDao userDao;

    private RecyclerView recyclerView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseManager dbManager = DatabaseManager.getInstance(this);
        userDao = new UserDao(dbManager.openDatabase());

        // Insertar usuarios solo si no existen
        insertSampleUsers();

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        List<User> allUsers = userDao.getAllUsers();
        adapter = new UserAdapter(allUsers);
        adapter.setOnItemClickListener(user -> {Toast.makeText(this, "Usuario seleccionado: " + user.getUsername(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Botón Regresar
        Button buttonReturn = findViewById(R.id.exit_list_user);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la anterior
            }
        });
    }

    private void insertSampleUsers() {
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean usersInserted = prefs.getBoolean("users_inserted", false);

        if (!usersInserted) {
            List<User> existingUsers = userDao.getAllUsers();
            if (existingUsers.isEmpty()) {
                User user1 = new User("Carlos Rodríguez", "carlos.rodriguez@gmail.com", "https://icons.iconarchive.com/icons/icons-land/vista-people/72/Office-Customer-Male-Light-icon.png");
                User user2 = new User("María López", "maria.lopez@hotmail.com", "https://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/72/man-icon.png");
                User user3 = new User("Andrés Gómez", "andres.gomez@outlook.com", "https://icons.iconarchive.com/icons/hopstarter/sleek-xp-basic/72/Administrator-icon.png");

                userDao.insert(user1);
                userDao.insert(user2);
                userDao.insert(user3);

                Log.d(TAG, "Usuarios insertados.");
            } else {
                Log.d(TAG, "Usuarios ya existen en la base de datos.");
            }

            prefs.edit().putBoolean("users_inserted", true).apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseManager.getInstance(this).closeDatabase();
    }
}
