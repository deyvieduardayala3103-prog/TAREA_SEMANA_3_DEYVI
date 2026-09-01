package com.example.tarea_semana_3_deyvi;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class ProgramaFragment extends Fragment {

    private EditText etId, etDia, etHora, etActividad;
    private TextView tvResultado;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programa, container, false);

        etId = view.findViewById(R.id.etId);
        etDia = view.findViewById(R.id.etDia);
        etHora = view.findViewById(R.id.etHora);
        etActividad = view.findViewById(R.id.etActividad);
        tvResultado = view.findViewById(R.id.tvResultado);

        MaterialButton btnRegistrar = view.findViewById(R.id.btnRegistrar);
        MaterialButton btnConsultar = view.findViewById(R.id.btnConsultar);
        MaterialButton btnActualizar = view.findViewById(R.id.btnActualizar);
        MaterialButton btnEliminar = view.findViewById(R.id.btnEliminar);

        dbHelper = new DatabaseHelper(requireContext());

        btnRegistrar.setOnClickListener(v -> registrar());
        btnConsultar.setOnClickListener(v -> consultar());
        btnActualizar.setOnClickListener(v -> actualizar());
        btnEliminar.setOnClickListener(v -> eliminar());

        return view;
    }

    private void registrar() {
        String dia = etDia.getText().toString().trim();
        String hora = etHora.getText().toString().trim();
        String actividad = etActividad.getText().toString().trim();

        if (TextUtils.isEmpty(dia) || TextUtils.isEmpty(hora) || TextUtils.isEmpty(actividad)) {
            Toast.makeText(requireContext(), "Completa Día, Hora y Actividad", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = dbHelper.registrarActividad(dia, hora, actividad);
        if (id != -1) {
            Toast.makeText(requireContext(), "Actividad registrada. ID: " + id, Toast.LENGTH_SHORT).show();
            limpiarCampos();
            consultar();
        } else {
            Toast.makeText(requireContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
        }
    }

    private void consultar() {
        Cursor cursor = dbHelper.consultarActividades();
        StringBuilder resultado = new StringBuilder();

        if (cursor.getCount() == 0) {
            resultado.append("No hay actividades registradas.");
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                String dia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DIA));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HORA));
                String actividad = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ACTIVIDAD));

                resultado.append("ID: ").append(id)
                        .append("\n")
                        .append(dia).append(" - ").append(hora)
                        .append("\n")
                        .append(actividad)
                        .append("\n\n");
            }
        }

        cursor.close();
        tvResultado.setText(resultado.toString().trim());
    }

    private void actualizar() {
        String idTexto = etId.getText().toString().trim();
        String dia = etDia.getText().toString().trim();
        String hora = etHora.getText().toString().trim();
        String actividad = etActividad.getText().toString().trim();

        if (TextUtils.isEmpty(idTexto)) {
            Toast.makeText(requireContext(), "Ingresa el ID que deseas actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(dia) || TextUtils.isEmpty(hora) || TextUtils.isEmpty(actividad)) {
            Toast.makeText(requireContext(), "Completa Día, Hora y Actividad", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idTexto);
        int filas = dbHelper.actualizarActividad(id, dia, hora, actividad);

        if (filas > 0) {
            Toast.makeText(requireContext(), "Actividad actualizada", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            consultar();
        } else {
            Toast.makeText(requireContext(), "No existe una actividad con ese ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminar() {
        String idTexto = etId.getText().toString().trim();

        if (TextUtils.isEmpty(idTexto)) {
            Toast.makeText(requireContext(), "Ingresa el ID que deseas eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idTexto);
        int filas = dbHelper.eliminarActividad(id);

        if (filas > 0) {
            Toast.makeText(requireContext(), "Actividad eliminada", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            consultar();
        } else {
            Toast.makeText(requireContext(), "No existe una actividad con ese ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etId.setText("");
        etDia.setText("");
        etHora.setText("");
        etActividad.setText("");
        etDia.requestFocus();
    }
}
