package com.example.project.Activities;
import com.example.project.Fragments.Fragement_Home;
import com.example.project.Fragments.Materials;
import com.example.project.Fragments.Placements;
import com.example.project.Fragments.Suggestions;
import com.example.project.Fragments.Training;
import com.example.project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class BottomBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        Toolbar toolbar=findViewById(R.id.myToolBar);

        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav=findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemReselectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemReselectedListener navListener=new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch (item.getItemId()){
                case R.id.menu_home:
                    selectedFragment=new Fragement_Home();
                    break;

                case R.id.menu_placements:
                    selectedFragment=new Placements();
                    break;

                case R.id.menu_training:
                    selectedFragment=new Training();
                    break;

                case R.id.menu_materials:
                    selectedFragment=new Materials();
                    break;

                case R.id.menu_suggestions:
                    selectedFragment=new Suggestions();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.LogoutMenu:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
        return true;
    }
}
