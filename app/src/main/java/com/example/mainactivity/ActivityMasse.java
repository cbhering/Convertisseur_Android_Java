package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.DecimalFormat;


//CRÉATION DE LA CLASSE DE L'ACTIVITÉ AVEC L'IMPLÉMENTATION DE LISTENERS POUR EDIT_TEXT ET POUR
//SWITCH
public class ActivityMasse extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    //VARIABLES DES UNITÉS DES TEMPÉRATURES DE DÉBUT ET DE DESTIN
    String uniteDebut = "kg";
    String uniteDestin = "livre";

    //VARIABLES DES TEMPÉRATURES DE DÉMARRAGE ET DE DESTIN APRÈS LA CONVERSION
    Double masseDebut = null;
    Double masseDestin = null;

    //FORMATAGE DES NOMBRES DÉCIMAUX DESQUELS L'APPLICATION AURA BESOIN
    DecimalFormat formatNombre = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //PERMET L'AFFICHAGE DE L'ACTIVITÉ MASSE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masse);

        //PERMET DE RÉCUPÉRER LES ÉLÉMENTS DU SWITCH
        Switch masseSwitch = (Switch) findViewById(R.id.switch1);

        //PERMET DE RÉCUPÉRER L'ÉLÉMENT EDIT_TEXT DANS L'ACTIVITÉ
        EditText debutMasse = (EditText) findViewById(R.id.editText3);

        //ON ASSIGNE UN LISTENER AU SWITCH
        masseSwitch.setOnCheckedChangeListener(this);

        //ON ASSIGNE UN LISTENER AU EDIT_TEXT
        debutMasse.addTextChangedListener(this);
    }

    //CRÉATION DES MÉTHODES DEMANDÉES PAR L'INTERFACE TEXT_WATCHER, LESQUELLES DOIVENT ÊTRE
    //DÉCLARÉES MÊME SI ON NE LES UTILISE PAS.

    //LA MÉTHODE EN DESSOUS EST LANCÉE JUSTE APRÈS LA MODIFICATION DU TEXTE
    public void afterTextChanged(Editable t) {
        //RÉCUPÈRE L'EDIT TEXT
        EditText debutMasse = (EditText) findViewById(R.id.editText3);
        //TEXTVIEW JUSTE EN DESSUS DU SWITCH POUR QUE L'UTILISATEUR SACHE EXACTEMENT
        //L'OPÉRATION (UNITÉS DE DÉPART ET DE DESTIN) QUI EST EN TRAIN D'ÊTRE FAITE.
        TextView affichageUnites = (TextView) findViewById(R.id.textView16);

        //GESTION DES EXCEPTIONS
        //D'ABORD, SI LE TEXTE EST VIDE
        if (debutMasse.getText().toString().isEmpty()) {
            //RÉCUPÉRATION DU TEXT VIEW DU RÉSULTAT
            TextView destinMasse = (TextView) findViewById(R.id.texteTemporaireResultat3);
            //PUIS, UNE CHAINE VIDE EST ASSIGNÉE AU TEXT VIEW
            destinMasse.setText("");
            //DONC, LA MASSE AU DÉBUT EST NULLE
            masseDebut = null;
        }
        //SINON, SI L'EDIT_TEXT NE CONTIENT QUE LES SIGNES '+' OU '-'
        else if ((debutMasse.getText().toString().equals("-")) || (debutMasse.getText().toString().equals("+"))) {
            //IL N'Y A PAS DE VALEUR, DONC ON NE FAIT RIEN
            masseDebut = null;
        }
        //SINON, ÇA VEUT DIRE QU'IL Y A DES NOMBRES DANS L'EDIT_TEXT
        else {
            //ON FAIT LA CONVERSION DU CONTENU DE EDIT_TEXT VERS FLOAT ET ON L'ASSIGNE À LA VARIABLE MASSEDEBUT
            masseDebut = Double.valueOf(debutMasse.getText().toString());
            //L'UNITÉ DE DÉPART PAR DÉFAUT EST KG (SWITCH OFF). QUAND L'UTILISATEUR TAPE UNE VALEUR
            //LE TEXTVIEW AFFICHE LA CHAINE (À L'AIDE D'UN RESSOURCES STRING) DE LA CONVERSION
            affichageUnites.setText(getString(R.string.switchKG));
            //ON APPELE LA MÉTHODE OPTIONSCALCULS (EN DESSOUS), QUI PERMET DE SAVOIR LE CALCUL À EFFECTUER
            optionsCalculs(uniteDebut, uniteDestin, masseDebut);
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

    //CETTE MÉTHODE EST APPELÉE QUAND LE BOUTON SWITCH EST GLISSÉ VERS ON OU OFF.
    //ON ENVOIE EN PARAMÈTRE LE BOUTON ET LA MÉTHODE BOOLÉENE ISCHECKED.
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //s CONTIENT LE BOUTON ACTIF
        Switch s = (Switch) findViewById(R.id.switch1);
        TextView affichageUnites = (TextView) findViewById(R.id.textView16);

        //IF/ELSE POUR VÉRIFIER SI LE SWITCH EST DANS L'ÉTAT ON OU OFF. SI LE SWITCH EST OFF,
        //L'UNITÉ DE DÉPART PAR DÉFAUT EST KG. SI LE SWITCH EST ON, L'UNITÉ DE DÉPART PAR DÉFAUT
        //EST LIVRE.
        if (s.isChecked() == false) {
            uniteDebut = "kg";
            affichageUnites.setText(getString(R.string.switchKG));
        }
        else {
            uniteDebut = "Livre";
            affichageUnites.setText(getString(R.string.switchLIVRE));
        }


        //SI LA MASSE/POIDS DE DÉMARRAGE N'EST PAS VIDE/NULL
        if (masseDebut != null) {
            //ON APPELE LA MÉTHODE OPTIONSCALCULS (EN DESSOUS), QUI VA PERMETTRE DE VÉRIFIER LE CALCUL À EFFECTUER
            optionsCalculs(uniteDebut, uniteDestin, masseDebut);
        }
    }

    //DÉFINITION DE LA MÉTHODE QUI VA PERMETTRE DE SAVOIR LE CALCUL À EFFECTUER
    public void optionsCalculs(String ud, String uf, Double masseDepart) {

        //LES PARAMÈTRES SONT ASSIGNÉS AUX VARIABLES
        uniteDebut = ud;
        uniteDestin = uf;
        masseDebut = masseDepart;

        //RÉCUPÈRE L'INSTANCE DU TEXTVIEW
        TextView destinMasse = (TextView) findViewById(R.id.texteTemporaireResultat3);

        //IMPLÉMENTATION D'UN IF/ELSE BASÉ SUR L'UNITÉ DE DÉPART. DANS CHAQUE SITUATION, L'UNITÉ DE
        //CONVERSION SERA TESTÉ ET LA MÉTHODE CORRESPONDANTE SERA APPELÉE. LES POSSIBILITÉS DE
        //CONVERSION SONT DE KG VERS LIVRE, LIVRE VERS KG.
        Switch switch1 = (Switch) findViewById(R.id.switch1);

        if (uniteDebut.equals(("kg"))) {
            uniteDestin.equals("livre");
            masseDestin = ConversionKgLivre(masseDebut);
        } else {
            uniteDebut.equals("livre");
            uniteDestin.equals("kg");
            masseDestin = ConversionLivreKg(masseDebut);
        }

        //AFFICHAGE DU RÉSULTAT DANS LE TEXTVIEW
        destinMasse.setText(formatNombre.format(masseDestin).toString());
    }

    //KG VERS LIVRE
    public double ConversionKgLivre(double masseKG) {
        return (masseKG*2.2046);
    }

    //LIVRE VERS KG
    public double ConversionLivreKg(double masseLivre) {
        return(masseLivre/2.2046);
    }



}

