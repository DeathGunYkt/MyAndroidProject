package com.example.passmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {
    private EditText serviceEditText, usernameEditText, passwordEditText;
    private Button saveButton;
    private CheckBox showPasswordCheckBox;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        serviceEditText = findViewById(R.id.serviceEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        saveButton = findViewById(R.id.saveButton);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        databaseHelper = new DatabaseHelper(this);

        // Обработка чекбокса "Показать пароль"
        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Показывать пароль
                    passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Скрывать пароль
                    passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                // Перемещаем курсор в конец текста
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = serviceEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (databaseHelper.addPassword(service, username, password)) {
                    Toast.makeText(AddPasswordActivity.this, "Пароль сохранён", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddPasswordActivity.this, "Ошибка сохранения пароля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}