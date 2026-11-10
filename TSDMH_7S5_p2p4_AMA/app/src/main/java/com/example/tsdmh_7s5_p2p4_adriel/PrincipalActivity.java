package com.example.tsdmh_7s5_p2p4_adriel;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class PrincipalActivity extends AppCompatActivity {

    Button btnImagenFragment, btnFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnFormFragment = findViewById(R.id.btnFormFragment);
        btnImagenFragment = findViewById(R.id.btnImagenFragment);
        if (savedInstanceState == null){
            LoadFragment(new ImagenFragment());
        }
        btnImagenFragment.setOnClickListener(v -> LoadFragment(new ImagenFragment()));
        btnFormFragment.setOnClickListener(v -> LoadFragment(new FormularioFragment()));
    }

    private void LoadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .setReorderingAllowed(true)
                .commit();
    }
}