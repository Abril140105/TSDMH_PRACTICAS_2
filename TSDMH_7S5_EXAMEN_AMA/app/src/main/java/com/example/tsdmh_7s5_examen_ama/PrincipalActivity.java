package com.example.tsdmh_7s5_examen_ama;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrincipalActivity extends AppCompatActivity {

    // Componentes de la UI
    TextView tvEstado, tvPorcentaje, tvResultadoDetalle;
    ProgressBar progressBar;
    Button btnIniciar, btnCancelar, btnVolver;
    Spinner spinnerCategorias;
    LinearLayout layoutControles, layoutResultados;

    // Hilos y concurrencia (remplazo moderno de AsyncTask)
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    final Handler handler = new Handler(Looper.getMainLooper());
    Future<?> tarea;

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

        // Vincular vistas
        tvEstado = findViewById(R.id.tvEstado);
        tvPorcentaje = findViewById(R.id.tvPorcentaje);
        progressBar = findViewById(R.id.progressBar);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnVolver = findViewById(R.id.btnVolver);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        layoutControles = findViewById(R.id.layoutControles);
        layoutResultados = findViewById(R.id.layoutResultados);
        tvResultadoDetalle = findViewById(R.id.tvResultadoDetalle);

        // Llenar el Spinner con al menos 4 categorías obligatorias de la rúbrica
        String[] categorias = {"Base de Datos", "Red", "Sincronización", "Reportes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategorias.setAdapter(adapter);

        // Botón Iniciar Tarea (Simulando el flujo del AsyncTask)
        btnIniciar.setOnClickListener(v -> {
            String categoriaSeleccionada = spinnerCategorias.getSelectedItem().toString();
            iniciarTareaSimulada(categoriaSeleccionada);
        });

        // Botón Cancelar Tarea
        btnCancelar.setOnClickListener(v -> {
            if (tarea != null && !tarea.isDone()) {
                tarea.cancel(true);
            }
        });

        // Botón Volver (Regresa a la pantalla de controles)
        btnVolver.setOnClickListener(v -> {
            layoutResultados.setVisibility(View.GONE);
            layoutControles.setVisibility(View.VISIBLE);
            tvEstado.setText("Listo para iniciar");
            tvPorcentaje.setText("0%");
            progressBar.setProgress(0);
        });
    }

    private void iniciarTareaSimulada(String categoria) {
        tvEstado.setText("Cargando datos (" + categoria + ")...");
        btnIniciar.setEnabled(false);
        btnCancelar.setEnabled(true);
        spinnerCategorias.setEnabled(false);
        tvPorcentaje.setText("0%");

        int pasos = 100;
        tarea = executor.submit(() -> {
            for (int i = 0; i < pasos; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    handler.post(this::alCancelar);
                    return;
                }
                try {
                    Thread.sleep(60); // Simula el tiempo de procesamiento en segundo plano
                } catch (InterruptedException e) {
                    handler.post(this::alCancelar);
                    return;
                }

                // Equivalente a onProgressUpdate
                final int progreso = (i * 100) / pasos;
                handler.post(() -> {
                    tvPorcentaje.setText(progreso + "%");
                    progressBar.setProgress(progreso);
                });
            }

            // Equivalente a onPostExecute -> Mostramos el panel de resultados en la misma actividad
            handler.post(() -> alFinalizar(categoria));
        });
    }

    private void alCancelar() {
        tvEstado.setText("Tarea cancelada");
        tvPorcentaje.setText("0%");
        progressBar.setProgress(0);
        btnIniciar.setEnabled(true);
        btnCancelar.setEnabled(false);
        spinnerCategorias.setEnabled(true);
    }

    private void alFinalizar(String categoria) {
        // Restablecer controles de fondo
        btnIniciar.setEnabled(true);
        btnCancelar.setEnabled(false);
        spinnerCategorias.setEnabled(true);
        progressBar.setProgress(0);

        // Ocultar sección de controles y mostrar la sección de resultados en la misma Actividad
        layoutControles.setVisibility(View.GONE);
        layoutResultados.setVisibility(View.VISIBLE);
        tvResultadoDetalle.setText("Proceso completado exitosamente.\nCategoría seleccionada:\n• " + categoria);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        executor.shutdown();
    }
}