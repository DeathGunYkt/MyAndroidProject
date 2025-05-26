package com.example.passmanager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PasswordListActivity extends AppCompatActivity {
    private ListView passwordListView;
    private Button addPasswordButton;
    private DatabaseHelper databaseHelper;
    private PasswordAdapter passwordAdapter;
    private List<Password> passwordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        passwordListView = findViewById(R.id.passwordListView);
        addPasswordButton = findViewById(R.id.addPasswordButton);
        databaseHelper = new DatabaseHelper(this);
        passwordList = new ArrayList<>();

        // Загрузка паролей из базы данных
        loadPasswords();

        passwordAdapter = new PasswordAdapter(this, passwordList);
        passwordListView.setAdapter(passwordAdapter);

        addPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordListActivity.this, AddPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadPasswords() {
        // Получение паролей из базы данных
        List<Password> passwordsFromDb = databaseHelper.getAllPasswords();
        if (passwordsFromDb != null) {
            passwordList.addAll(passwordsFromDb);
        } else {
            Toast.makeText(this, "Ошибка загрузки паролей", Toast.LENGTH_SHORT).show();
        }
    }
}