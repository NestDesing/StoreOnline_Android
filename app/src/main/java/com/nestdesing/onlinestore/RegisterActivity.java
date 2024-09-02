package com.nestdesign.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText dniField, emailField, nombreField, apellidoField, direccionField, telefonoField, ciudadField, paisField, passwordField;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dniField = findViewById(R.id.dniField);
        emailField = findViewById(R.id.emailField);
        nombreField = findViewById(R.id.nombreField);
        apellidoField = findViewById(R.id.apellidoField);
        direccionField = findViewById(R.id.direccionField);
        telefonoField = findViewById(R.id.telefonoField);
        ciudadField = findViewById(R.id.ciudadField);
        paisField = findViewById(R.id.paisField);
        passwordField = findViewById(R.id.passwordField);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String dni = dniField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String nombre = nombreField.getText().toString().trim();
        String apellido = apellidoField.getText().toString().trim();
        String direccion = direccionField.getText().toString().trim();
        String telefono = telefonoField.getText().toString().trim();
        String ciudad = ciudadField.getText().toString().trim();
        String pais = paisField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.registerUser(dni, email, nombre, apellido, direccion, telefono, ciudad, pais, password);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().isSuccess()) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
