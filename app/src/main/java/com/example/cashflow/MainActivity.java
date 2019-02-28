package com.example.cashflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DecimalFormat df = new DecimalFormat("#.##");
    File file = new File("values.csv");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner ausgabeEinnahme = (Spinner) findViewById(R.id.ausgabenEinnahmen);
        ausgabeEinnahme.setAdapter(new ArrayAdapter<EinnahmeAusgabe>(this, android.R.layout.simple_spinner_item, EinnahmeAusgabe.values()));
        Spinner kategorien = (Spinner) findViewById(R.id.kategorie);
        kategorien.setAdapter(new ArrayAdapter<Kategorien>(this, android.R.layout.simple_spinner_item, Kategorien.values()));
    }

    public void okClick(View view){
        TextView date = (TextView) findViewById(R.id.date); //FileWriter true -> danach sort reverse
        String dateStr = date.getText().toString();

        TextView euro = (TextView) findViewById(R.id.euro);
        String euroStr = euro.getText().toString();
        double euroDouble = Double.parseDouble(df.format(euroStr));

        Spinner ausgabeEinnahme = (Spinner) findViewById(R.id.ausgabenEinnahmen);
        String ausgabeEinnahmeStr = ausgabeEinnahme.toString();

        Spinner kategorie = (Spinner) findViewById(R.id.kategorie);
        String kategorieStr = kategorie.toString();

        if(dateStr.equals("") || dateStr.equals(" ") || dateStr.equals(null) || euroStr.equals("") || euroStr.equals(" ") || euroStr.equals(null) || kategorieStr.equals(Kategorien.valueOf("NOTHING"))){
        }

        else{
            TextView cash = (TextView) findViewById(R.id.cash);
            String cashStr = cash.getText().toString();
            String[] split = cashStr.split("\\s+");
            
        }
    }
}
