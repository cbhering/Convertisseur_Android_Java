package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

//CRÉATION DE LA CLASSE DE L'ACTIVITÉ AVEC L'IMPLÉMENTATION DE LISTENERS POUR EDIT_TEXT ET POUR
//RADIOGROUP
public class ActivityTemperature extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    //VARIABLES DES UNITÉS DES TEMPÉRATURES DE DÉBUT ET DE DESTIN
    String uniteDebut = "F";
    String uniteDestin = "C";

    //VARIABLES DES TEMPÉRATURES DE DÉMARRAGE ET DE DESTIN APRÈS LA CONVERSION
    Float tempDebut = null;
    Float tempDestin = null;

    //FORMATAGE DES NOMBRES DÉCIMAUX DESQUELS L'APPLICATION AURA BESOIN
    DecimalFormat formatNombre = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //PERMET L'AFFICHAGE DE L'ACTIVITÉ TEMPÉRATURE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        //PERMET DE RÉCUPÉRER LES ÉLÉMENTS DE RADIOGROUP
        RadioGroup rgDebut = (RadioGroup) findViewById(R.id.radioGroupDebut2);
        RadioGroup rgDestin = (RadioGroup) findViewById(R.id.radioGroupDestin2);

        //PERMET DE RÉCUPÉRER L'ÉLÉMENT EDIT_TEXT DANS L'ACTIVITÉ
        EditText debutTemp = (EditText) findViewById(R.id.editText);

        //ON ASSIGNE DES LISTENERS AUX RADIOGROUPS
        rgDebut.setOnCheckedChangeListener(this);
        rgDestin.setOnCheckedChangeListener(this);

        //ON ASSIGNE UN LISTENER AU EDIT_TEXT
        debutTemp.addTextChangedListener(this);
    }

    //CRÉATION DES MÉTHODES DEMANDÉES PAR L'INTERFACE TEXT_WATCHER, LESQUELLES DOIVENT ÊTRE
    //DÉCLARÉES MÊME SI ON NE LES UTILISE PAS.

    //LA MÉTHODE EN DESSOUS EST LANCÉE JUSTE APRÈS LA MODIFICATION DU TEXTE
    public void afterTextChanged(Editable t) {
        //RÉCUPÈRE L'EDIT TEXT
        EditText debutTemp = (EditText) findViewById(R.id.editText);

        //GESTION DES EXCEPTIONS
        //D'ABORD, SI LE TEXTE EST VIDE
        if (debutTemp.getText().toString().isEmpty()) {
            //RÉCUPÉRATION DU TEXT VIEW DU RÉSULTAT
            TextView destinTemp = (TextView) findViewById(R.id.texteTemporaireResultat2);
            //PUIS, UNE CHAINE VIDE EST ASSIGNÉE AU TEXT VIEW
            destinTemp.setText("");
            //DONC, LA TEMPÉRATURE AU DÉBUT EST NULLE
            tempDebut = null;
        }
        //SINON, SI L'EDIT_TEXT NE CONTIENT QUE LES SIGNES '+' OU '-'
        else if ((debutTemp.getText().toString().equals("-")) || (debutTemp.getText().toString().equals("+"))) {
            //IL N'Y A PAS DE VALEUR, DONC ON NE FAIT RIEN
            tempDebut = null;
        }
        //SINON, ÇA VEUT DIRE QU'IL Y A DES NOMBRES DANS L'EDIT_TEXT
        else {
            //ON FAIT LA CONVERSION DU CONTENU DE EDIT_TEXT VERS FLOAT ET ON L'ASSIGNE À LA VARIABLE TEMPDEBUT
            tempDebut = Float.valueOf(debutTemp.getText().toString());
            //ON APPELE LA MÉTHODE OPTIONSCALCULS (EN DESSOUS), QUI PERMET DE SAVOIR LE CALCUL À EFFECTUER
            optionsCalculs(uniteDebut, uniteDestin, tempDebut);
        }
    }

    //MÉTHODE LANCÉE AVANT LA MODIFICATION DU TEXTE
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //RIEN À FAIRE POUR LE MOMENT
    }

    //MÉTHODE LANCÉE LORSQUE LE TEXTE EST MODIFIÉ
    public void onTextChanged(CharSequence arg0, int start, int before, int count) {
        //RIEN À FAIRE POUR LE MOMENT
    }

    //CETTE MÉTHODE EST APPELÉE QUAND LE RADIO BOUTON EST CLIQUÉ.
    //ON ENVOIE EN PARAMÈTRE LE GROUPE DES BOUTONS ET LE BOUTON CHOISI
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //rbActif CONTIENT LE BOUTON ACTIF
        RadioButton rbActif = (RadioButton) findViewById(checkedId);

        //CHOIX EN FONCTION DU RADIO BOUTON ACTIF
        switch (rbActif.getId()) {
            case R.id.radioButtonC:
                //BOUTON DE LA TEMPÉRATURE DE CELSIUS
                uniteDebut = "C";
                break;
            case R.id.radioButtonF:
                //BOUTON DE LA TEMPÉRATURE DE FAHRENHEIT
                uniteDebut = "F";
                break;
            case R.id.radioButtonK:
                //BOUTON DE LA TEMPÉRATURE DE KELVIN
                uniteDebut = "K";
                break;
            case R.id.radioButtonVersC:
                //BOUTON DE LA TEMPÉRATURE VERS CELSIUS
                uniteDestin = "C";
                break;
            case R.id.radioButtonVersF:
                //BOUTON DE LA TEMPÉRATURE DE FAHRENHEIT
                uniteDestin = "F";
                break;
            case R.id.radioButtonVersK:
                //BOUTON DE LA TEMPÉRATURE DE KELVIN
                uniteDestin = "K";
                break;
        }
        //SI LA TEMPÉRATURE DE DÉMARRAGE N'EST PAS VIDE/NULL
        if (tempDebut != null) {
            //ON APPELE LA MÉTHODE OPTIONSCALCULS (EN DESSOUS), QUI VA PERMETTRE DE VÉRIFIER LE CALCUL À EFFECTUER
            optionsCalculs(uniteDebut, uniteDestin, tempDebut);
        }
    }

    //DÉFINITION DE LA MÉTHODE QUI VA PERMETTRE DE SAVOIR LE CALCUL À EFFECTUER
    public void optionsCalculs(String ud, String uf, Float temperatureDepart) {

        //LES PARAMÈTRES SONT ASSIGNÉS AUX VARIABLES
        uniteDebut = ud;
        uniteDestin = uf;
        tempDebut = temperatureDepart;

        RadioButton rDebutF = (RadioButton) findViewById(R.id.radioButtonF);
        RadioButton rFinC = (RadioButton) findViewById(R.id.radioButtonVersC);

        rDebutF.setChecked(true);
        rFinC.setChecked(true);

        //RÉCUPÈRE L'INSTANCE DU TEXTVIEW
        TextView destinTemp = (TextView) findViewById(R.id.texteTemporaireResultat2);

        //IMPLÉMENTATION D'UN SWITCH BASÉ SUR L'UNITÉ DE DÉPART. DANS CHAQUE SITUATION, L'UNITÉ DE
        //CONVERSION SERA TESTÉ ET LA MÉTHODE CORRESPONDANTE SERA APPELÉE. LES POSSIBILITÉS DE
        //CONVERSION SONT DE C VERS F, C VERS K, F VERS C, F VERS K, K VERS C ET K VERS F.

        switch (uniteDebut) {
            case ("C"):
                if (uniteDestin.equals("F")) {
                    tempDestin = ConversionCaF(tempDebut);
                } else if (uniteDestin.equals("K")) {
                    tempDestin = ConversionCaK(tempDebut);
                } else {
                    tempDestin = tempDebut;
                }
                break;
            case ("F"):
                if (uniteDestin.equals("C")) {
                    tempDestin = ConversionFaC(tempDebut);
                } else if (uniteDestin.equals("K")) {
                    tempDestin = ConversionFaK(tempDebut);
                } else {
                    tempDestin = tempDebut;
                }
                break;
             default:
                 if (uniteDestin.equals("C")) {
                     tempDestin = ConversionKaC(tempDebut);
                 } else if (uniteDestin.equals("F")) {
                     tempDestin = ConversionKaF(tempDebut);
                 } else {
                     tempDestin = tempDebut;
                 }
                 break;
        }
        //AFFICHAGE DU RÉSULTAT DANS LE TEXTVIEW
        destinTemp.setText(formatNombre.format(tempDestin).toString());
    }

    //CELSIUS VERS FAHRENHEIT
    public float ConversionCaF(Float temperatureC) {
        return((temperatureC*9/5)+32);
    }

    //CELSIUS VERS KELVIN
    public float ConversionCaK(Float temperatureC) {
        return(temperatureC+273.15f);
    }

    //FAHRENHEIT VERS CELSIUS
    public float ConversionFaC(Float temperatureF) {
        return((temperatureF-32)*5/9);
    }

    //FAHRENHEIT VERS KELVIN
    public float ConversionFaK(Float temperatureF) {
        return(((temperatureF-32)*5/9)+273.15f);
    }

    //KELVIN VERS CELSIUS
    public float ConversionKaC(Float temperatureK) {
        return(temperatureK-273.15f);
    }

    //KELVIN VERS FAHRENHEIT
    public float ConversionKaF(Float temperatureK) {
        return(((temperatureK-273.15f)*9/5)+32);
    }


}
