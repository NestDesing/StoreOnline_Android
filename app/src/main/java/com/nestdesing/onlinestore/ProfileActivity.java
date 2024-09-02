package com.nestdesign.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView dniLabel, emailLabel, nombreLabel, apellidoLabel, direccionLabel, telefonoLabel, ciudadLabel, paisLabel;
    private Button editProfileButton, logoutButton;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dniLabel = findViewById(R.id.dniLabel);
        emailLabel = findViewById(R.id.emailLabel);
        nombreLabel = findViewById(R.id.nombreLabel);
        apellidoLabel = findViewById(R.id.apellidoLabel);
        direccionLabel = findViewById(R.id.direccionLabel);
        telefonoLabel = findViewById(R.id.telefonoLabel);
        ciudadLabel = findViewById(R.id.ciudadLabel);
        paisLabel = findViewById(R.id.paisLabel);
        editProfileButton = findViewById(R.id.editProfileButton);
        logoutButton = findViewById(R.id.logoutButton);

        sessionManager = new SessionManager(this);

        loadProfileData();

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void loadProfileData() {
        String token = sessionManager.getToken();
        String email = sessionManager.getEmail();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.getUserProfile(token, email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    dniLabel.setText(user.getDni());
                    emailLabel.setText(user.getEmail());
                    nombreLabel.setText(user.getNombre());
                    apellidoLabel.setText(user.getApellido());
                    direccionLabel.setText(user.getDireccion());
                    telefonoLabel.setText(user.getTelefono());
                    ciudadLabel.setText(user.getCiudad());
                    paisLabel.setText(user.getPais());
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
