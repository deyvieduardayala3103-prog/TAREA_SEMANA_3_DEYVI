package com.example.tarea_semana_3_deyvi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Nuevo Horizonte");

        bottomNavigation = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            cargarFragment(new ProgramaFragment());
            bottomNavigation.setSelectedItemId(R.id.menu_programa);
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.menu_programa) {
                fragment = new ProgramaFragment();
            } else if (id == R.id.menu_lectura) {
                fragment = new LecturaFragment();
            } else {
                return false;
            }

            cargarFragment(fragment);
            return true;
        });
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
