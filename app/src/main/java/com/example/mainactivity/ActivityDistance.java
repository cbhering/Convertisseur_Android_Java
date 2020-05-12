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
public class ActivityDistance extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    //VARIABLES DES UNITÉS DES DISTANCES DE DÉBUT ET DE DESTIN
    String uniteDebut = "cm";
    String uniteDestin = "m";

    //VARIABLES DES DISTANCES DE DÉMARRAGE ET DE DESTIN APRÈS LA CONVERSION
    Double distDebut = null;
    Double distDestin = null;

    //FORMATAGE DES NOMBRES DÉCIMAUX DESQUELS L'APPLICATION AURA BESOIN
    DecimalFormat formatNombre = new DecimalFormat("0.0000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //PERMET L'AFFICHAGE DE L'ACTIVITÉ DISTANCE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        //PERMET DE RÉCUPÉRER LES ÉLÉMENTS DE RADIOGROUP
        RadioGroup rgDebut = (RadioGroup) findViewById(R.id.radioGroupDebut2);
        RadioGroup rgDestin = (RadioGroup) findViewById(R.id.radioGroupDestin2);

        //PERMET DE RÉCUPÉRER L'ÉLÉMENT EDIT_TEXT DANS L'ACTIVITÉ
        EditText debutDist = (EditText) findViewById(R.id.editText2);

        //ON ASSIGNE DES LISTENERS AUX RADIOGROUPS
        rgDebut.setOnCheckedChangeListener(this);
        rgDestin.setOnCheckedChangeListener(this);

        //ON ASSIGNE UN LISTENER AU EDIT_TEXT
        debutDist.addTextChangedListener(this);
    }

    //CRÉATION DES MÉTHODES DEMANDÉES PAR L'INTERFACE TEXT_WATCHER, LESQUELLES DOIVENT ÊTRE
    //DÉCLARÉES MÊME SI ON NE LES UTILISE PAS.

    //LA MÉTHODE EN DESSOUS EST LANCÉE JUSTE APRÈS LA MODIFICATION DU TEXTE
    public void afterTextChanged(Editable t) {
        //RÉCUPÈRE L'EDIT TEXT
        EditText debutDist = (EditText) findViewById(R.id.editText2);

        //GESTION DES EXCEPTIONS
        //D'ABORD, SI LE TEXTE EST VIDE
        if (debutDist.getText().toString().isEmpty()) {
            //RÉCUPÉRATION DU TEXT VIEW DU RÉSULTAT
            TextView destinDist = (TextView) findViewById(R.id.texteTemporaireResultat2);
            //PUIS, UNE CHAINE VIDE EST ASSIGNÉE AU TEXT VIEW
            destinDist.setText("");
            //DONC, LA DISTANCE AU DÉBUT EST NULLE
            distDebut = null;
        }
        //SINON, SI L'EDIT_TEXT NE CONTIENT QUE LES SIGNES '+' OU '-'
        else if ((debutDist.getText().toString().equals("-")) || (debutDist.getText().toString().equals("+"))) {
            //IL N'Y A PAS DE VALEUR, DONC ON NE FAIT RIEN
            distDebut = null;
        }
        //SINON, ÇA VEUT DIRE QU'IL Y A DES NOMBRES DANS L'EDIT_TEXT
        else {
            //ON FAIT LA CONVERSION DU CONTENU DE EDIT_TEXT VERS FLOAT ET ON L'ASSIGNE À LA VARIABLE DISTDEBUT
            distDebut = Double.parseDouble(debutDist.getText().toString());
            //ON APPELE LA MÉTHODE OPTIONSCALCULS (EN DESSOUS), QUI PERMET DE SAVOIR LE CALCUL À EFFECTUER
            optionsCalculs(uniteDebut, uniteDestin, distDebut);
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
            case R.id.radioButtonCM:
                //BOUTON DE LA DISTANCE EN CENTIMÈTRES
                uniteDebut = "cm";
                break;
            case R.id.radioButtonM:
                //BOUTON DE LA DISTANCE EN MÈTRES
                uniteDebut = "m";
                break;
            case R.id.radioButtonKM:
                //BOUTON DE LA DISTANCE EN KILOMÈTRES
                uniteDebut = "km";
                break;
            case R.id.radioButtonPOUCE:
                //BOUTON DE LA DISTANCE EN POUCES
                uniteDebut = "pouce";
                break;
            case R.id.radioButtonPIED:
                //BOUTON DE LA DISTANCE EN PIEDS
                uniteDebut = "pied";
                break;
            case R.id.radioButtonMILE:
                //BOUTON DE LA DISTANCE EN MILES
                uniteDebut = "mile";
                break;
            case R.id.radioButtonVersCM:
                //BOUTON DE LA DISTANCE VERS CENTIMÈTRES
                uniteDestin = "cm";
                break;
            case R.id.radioButtonVersM:
                //BOUTON DE LA DISTANCE VERS MÈTRES
                uniteDestin = "m";
                break;
            case R.id.radioButtonVersKM:
                //BOUTON DE LA DISTANCE VERS KILOMÈTRES
                uniteDestin = "km";
                break;
            case R.id.radioButtonVersPOUCE:
                //BOUTON DE LA DISTANCE VERS POUCE
                uniteDestin = "pouce";
                break;
            case R.id.radioButtonVersPIED:
                //BOUTON DE LA DISTANCE VERS PIEDS
                uniteDestin = "pied";
                break;
            case R.id.radioButtonVersMILE:
                //BOUTON DE LA DISTANCE VERS MILE
                uniteDestin = "mile";
                break;
        }
        //SI LA DISTANCE DE DÉMARRAGE N'EST PAS VIDE/NULL
        if (distDebut != null) {
            //ON APPELE LA MÉTHODE OPTIONSCALCULS (EN DESSOUS), QUI VA PERMETTRE DE VÉRIFIER LE CALCUL À EFFECTUER
            optionsCalculs(uniteDebut, uniteDestin, distDebut);
        }
    }

    //DÉFINITION DE LA MÉTHODE QUI VA PERMETTRE DE SAVOIR LE CALCUL À EFFECTUER
    public void optionsCalculs(String ud, String uf, Double distanceDepart) {

        //LES PARAMÈTRES SONT ASSIGNÉS AUX VARIABLES
        uniteDebut = ud;
        uniteDestin = uf;
        distDebut = distanceDepart;

        RadioButton rDebutCm = (RadioButton) findViewById(R.id.radioButtonCM);
        RadioButton rFinM = (RadioButton) findViewById(R.id.radioButtonVersM);

        rDebutCm.setChecked(true);
        rFinM.setChecked(true);

        //RÉCUPÈRE L'INSTANCE DU TEXTVIEW
        TextView destinDist = (TextView) findViewById(R.id.texteTemporaireResultat2);

        //IMPLÉMENTATION D'UN SWITCH BASÉ SUR L'UNITÉ DE DÉPART. DANS CHAQUE SITUATION, L'UNITÉ DE
        //CONVERSION SERA TESTÉ ET LA MÉTHODE CORRESPONDANTE SERA APPELÉE. LES POSSIBILITÉS DE
        //CONVERSION SONT DE C VERS F, C VERS K, F VERS C, F VERS K, K VERS C ET K VERS F.

        switch (uniteDebut) {
            case ("cm"):
                if (uniteDestin.equals("m")) {
                    distDestin = ConversionCmM(distDebut);
                } else if (uniteDestin.equals("km")) {
                    distDestin = ConversionCmKm(distDebut);
                } else if (uniteDestin.equals("pouce")) {
                    distDestin = ConversionCmPouce(distDebut);
                } else if (uniteDestin.equals("pied")) {
                    distDestin = ConversionCmPied(distDebut);
                } else if (uniteDestin.equals("mile")) {
                    distDestin = ConversionCmMile(distDebut);
                }
                else {
                    distDestin = distDebut;
                }
                break;
            case ("m"):
                if (uniteDestin.equals("cm")) {
                    distDestin = ConversionMCm(distDebut);
                } else if (uniteDestin.equals("km")) {
                    distDestin = ConversionMKm(distDebut);
                } else if (uniteDestin.equals("pouce")) {
                    distDestin = ConversionMPouce(distDebut);
                } else if (uniteDestin.equals("pied")) {
                    distDestin = ConversionMPied(distDebut);
                } else if (uniteDestin.equals("mile")) {
                    distDestin = ConversionMMile(distDebut);
                }
                else {
                    distDestin = distDebut;
                }
                break;
            case ("km"):
                if (uniteDestin.equals("cm")) {
                    distDestin = ConversionKmCm(distDebut);
                } else if (uniteDestin.equals("m")) {
                    distDestin = ConversionKmM(distDebut);
                } else if (uniteDestin.equals("pouce")) {
                    distDestin = ConversionKmPouce(distDebut);
                } else if (uniteDestin.equals("pied")) {
                    distDestin = ConversionKmPied(distDebut);
                } else if (uniteDestin.equals("mile")) {
                    distDestin = ConversionKmMile(distDebut);
                }
                else {
                    distDestin = distDebut;
                }
                break;
            case ("pouce"):
                if (uniteDestin.equals("cm")) {
                    distDestin = ConversionPouceCm(distDebut);
                } else if (uniteDestin.equals("m")) {
                    distDestin = ConversionPouceM(distDebut);
                } else if (uniteDestin.equals("km")) {
                    distDestin = ConversionPouceKm(distDebut);
                } else if (uniteDestin.equals("pied")) {
                    distDestin = ConversionPoucePied(distDebut);
                } else if (uniteDestin.equals("mile")) {
                    distDestin = ConversionPouceMile(distDebut);
                }
                else {
                    distDestin = distDebut;
                }
                break;
            case ("pied"):
                if (uniteDestin.equals("cm")) {
                    distDestin = ConversionPiedCm(distDebut);
                } else if (uniteDestin.equals("m")) {
                    distDestin = ConversionPiedM(distDebut);
                } else if (uniteDestin.equals("km")) {
                    distDestin = ConversionPiedKm(distDebut);
                } else if (uniteDestin.equals("pouce")) {
                    distDestin = ConversionPiedPouce(distDebut);
                } else if (uniteDestin.equals("mile")) {
                    distDestin = ConversionPiedMile(distDebut);
                }
                else {
                    distDestin = distDebut;
                }
                break;
            default:
                if (uniteDestin.equals("cm")) {
                    distDestin = ConversionMileCm(distDebut);
                } else if (uniteDestin.equals("m")) {
                    distDestin = ConversionMileM(distDebut);
                } else if (uniteDestin.equals("km")) {
                    distDestin = ConversionMileKm(distDebut);
                } else if (uniteDestin.equals("pouce")) {
                    distDestin = ConversionMilePouce(distDebut);
                } else if (uniteDestin.equals("pied")) {
                    distDestin = ConversionMilePied(distDebut);
                }
                else {
                    distDestin = distDebut;
                }
                break;
        }
        //AFFICHAGE DU RÉSULTAT DANS LE TEXTVIEW
        destinDist.setText(formatNombre.format(distDestin).toString());
    }

    //CENTIMÈTRES VERS MÈTRES
    public double ConversionCmM(double distanceCm) {
        return(distanceCm/100);
    }

    //CENTIMÈTRES VERS KILOMÈTRES
    public double ConversionCmKm(double distanceCm) {
        return(distanceCm/100000);
    }

    //CENTIMÈTRES VERS POUCES
    public double ConversionCmPouce(double distanceCm) {
        return(distanceCm/2.54);
    }

    //CENTIMÈTRES VERS PIEDS
    public double ConversionCmPied(double distanceCm) {
        return(distanceCm/30.48);
    }

    //CENTIMÈTRES VERS MILES
    public double ConversionCmMile(double distanceCm) { return(distanceCm/160934);}

    //MÈTRES VERS CENTIMÈTRES
    public double ConversionMCm(double distanceM) {
        return(distanceM*100);
    }

    //MÈTRES VERS KILOMÈTRES
    public double ConversionMKm(double distanceM) {
        return(distanceM/1000);
    }

    //MÈTRES VERS POUCES
    public double ConversionMPouce(double distanceM) {
        return(distanceM*39.37);
    }

    //MÈTRES VERS PIEDS
    public double ConversionMPied(double distanceM) {return(distanceM*3.28084);}

    //MÈTRES VERS MILES
    public double ConversionMMile(double distanceM) {return(distanceM/1609);}

    //KILOMÈTRES VERS CENTIMÈTRES
    public double ConversionKmCm(double distanceKM) {return(distanceKM*100000);}

    //KILOMÈTRES VERS MÈTRES
    public double ConversionKmM(double distanceKM) {return(distanceKM*1000);}

    //KILOMÈTRES VERS POUCES
    public double ConversionKmPouce(double distanceKM) {return(distanceKM*39370.1);}

    //KILOMÈTRES VERS PIEDS
    public double ConversionKmPied(double distanceKM) {return(distanceKM*3280.84);}

    //KILOMÈTRES VERS MILES
    public double ConversionKmMile(double distanceKM) {return(distanceKM*0.621371);}

    //POUCES VERS CENTIMÈTRES
    public double ConversionPouceCm(double distancePOUCE) {return(distancePOUCE*2.54);}

    //POUCES VERS MÈTRES
    public double ConversionPouceM(double distancePOUCE) {return(distancePOUCE/39.37);}

    //POUCES VERS KILOMÈTRES
    public double ConversionPouceKm(double distancePOUCE) {return(distancePOUCE/39370);}

    //POUCES VERS PIEDS
    public double ConversionPoucePied(double distancePOUCE) {return(distancePOUCE/12);}

    //POUCES VERS MILES
    public double ConversionPouceMile(double distancePOUCE) {return(distancePOUCE/63360);}

    //PIEDS VERS CENTIMÈTRES
    public double ConversionPiedCm(double distancePIEDS) {return(distancePIEDS*30.48);}

    //PIEDS VERS MÈTRES
    public double ConversionPiedM(double distancePIEDS) {return(distancePIEDS*0.3048);}

    //PIEDS VERS KILOMÈTRES
    public double ConversionPiedKm(double distancePIEDS) {return(distancePIEDS*0.0003048);}

    //PIEDS VERS POUCES
    public double ConversionPiedPouce(double distancePIEDS) {return(distancePIEDS*12);}

    //PIEDS VERS MILES
    public double ConversionPiedMile(double distancePIEDS) {return(distancePIEDS/5280);}

    //MILES VERS CENTIMÈTRES
    public double ConversionMileCm(double distanceMILES) {return(distanceMILES*160934);}

    //MILES VERS MÈTRES
    public double ConversionMileM(double distanceMILES) {return(distanceMILES*1609.34);}

    //MILES VERS KILOMÈTRES
    public double ConversionMileKm(double distanceMILES) {return(distanceMILES*1.60934);}

    //MILES VERS POUCES
    public double ConversionMilePouce(double distanceMILES) {return(distanceMILES*63360);}

    //MILES VERS PIEDS
    public double ConversionMilePied(double distanceMILES) {return(distanceMILES*5280);}
}
