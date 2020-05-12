package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //CRÉATION DES VARIABLES BUTTON POUR CHAQUE BOUTON DE LA CLASSE BUTTON
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        //ON ASSIGNE UN LISTENER À CHAQUE BOUTON
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    //DÉCLARATION D'UNE NOUVELLE VARIABLE POUR L'ACTIVITÉ QUI SERA LANCÉE
    Class activity;


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                activity = ActivityTemperature.class;
                Toast.makeText(this, "Bienvenu au Convertisseur de Température!", Toast.LENGTH_LONG).show();
                break;
            case R.id.button2:
                activity = ActivityDistance.class;
                Toast.makeText(this, "Bienvenu au Convertisseur de Distance!", Toast.LENGTH_LONG).show();
                break;
            case R.id.button3:
                activity = ActivityMasse.class;
                Toast.makeText(this, "Bienvenu au Convertisseur de Masse!", Toast.LENGTH_LONG).show();;
                break;
            case R.id.button4:
                this.finishAffinity();
                System.exit(0);

        }
        //DÉMARRAGE DE L'ACTIVITÉ DÉFINIE PAR LA VARIABLE ACTIVITY
        startActivity(new Intent(MenuActivity.this,activity));

    }

}


