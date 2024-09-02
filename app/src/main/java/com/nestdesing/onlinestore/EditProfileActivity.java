package com.nestdesign.onlinestore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText dniField, nombreField, apellidoField, direccionField, telefonoField, ciudadField, paisField;
    private Button saveChangesButton;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dniField = findViewById(R.id.dniField);
        nombreField = findViewById(R.id.nombreField);
        apellidoField = findViewById(R.id.apellidoField);
        direccionField = findViewById(R.id.direccionField);
        telefonoField = findViewById(R.id.telefonoField);
        ciudadField = findViewById(R.id.ciudadField);
        paisField = findViewById(R.id.paisField);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        sessionManager = new SessionManager(this);

        loadUserProfile();

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileChanges();
            }
        });
    }

    private void loadUserProfile() {
        String token = sessionManager.getToken();
        String email = sessionManager.getEmail();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.getUserProfile(token, email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    dniField.setText(user.getDni());
                    nombreField.setText(user.getNombre());
                    apellidoField.setText(user.getApellido());
                    direccionField.setText(user.getDireccion());
                    telefonoField.setText(user.getTelefono());
                    ciudadField.setText(user.getCiudad());
                    paisField.setText(user.getPais());
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfileChanges() {
        String token = sessionManager.getToken();
        String email = sessionManager.getEmail();

        String dni = dniField.getText().toString().trim();
        String nombre = nombreField.getText().toString().trim();
        String apellido = apellidoField.getText().toString().trim();
        String direccion = direccionField.getText().toString().trim();
        String telefono = telefonoField.getText().toString().trim();
        String ciudad = ciudadField.getText().toString().trim();
        String pais = paisField.getText().toString().trim();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.updateUserProfile(token, email, dni, nombre, apellido, direccion, telefono, ciudad, pais);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().isSuccess()) {
                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}



