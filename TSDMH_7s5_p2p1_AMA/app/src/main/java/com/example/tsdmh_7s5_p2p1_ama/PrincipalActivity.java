package com.example.tsdmh_7s5_p2p1_ama;

import static com.example.tsdmh_7s5_p2p1_ama.R.id.p1sp2;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {
    Spinner p1sp1, p1sp2;

    ArrayList<String> miLista = new ArrayList<>();
    ArrayAdapter<String> adaptador;
    @SuppressLint("MissingInflatedId")
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
        miLista.add("Manzana");
        miLista.add("Pera");
        miLista.add("Fresa");
        miLista.add("Mango");
        miLista.add("Melon");


        p1sp1 = findViewById(R.id.p1sp1);
        p1sp2 = findViewById(R.id.p1sp2);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,miLista);
        p1sp2.setAdapter(adaptador);
        p1sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String valor = parent.getItemAtPosition(position).toString();
                Toast.makeText(PrincipalActivity.this, "Valor seleccionado: " + valor, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        p1sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cadena = parent.getItemAtPosition(position).toString();
                Toast.makeText(PrincipalActivity.this, "La fruta seleccionada es: " + cadena, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }

    public void Btnclick(View v){
        if(v.getId() == R.id.p1btn1){
            Toast.makeText(this, "Se presiono el boton 1", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.p1btn2){
            Toast.makeText(this, "Se presiono el boton 2", Toast.LENGTH_SHORT).show();
        }
    }
}