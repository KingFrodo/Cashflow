package com.example.cashflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DecimalFormat df = new DecimalFormat("#.##");
    File file = new File("values.csv");
    double sum = 0;
    TextView textView;
    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.cash);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        Spinner ausgabeEinnahme = (Spinner) findViewById(R.id.ausgabenEinnahmen);
        ausgabeEinnahme.setAdapter(new ArrayAdapter<EinnahmeAusgabe>(this, android.R.layout.simple_spinner_item, EinnahmeAusgabe.values()));
        Spinner kategorien = (Spinner) findViewById(R.id.kategorie);
        kategorien.setAdapter(new ArrayAdapter<Kategorien>(this, android.R.layout.simple_spinner_item, Kategorien.values()));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
    }

    public void okClick(View view){
        TextView date = (TextView) findViewById(R.id.date); //FileWriter true -> danach sort reverse
        String dateStr = date.getText().toString();

        TextView euro = (TextView) findViewById(R.id.euro);
        String euroStr = euro.getText().toString();
        double euroDouble = Double.parseDouble(euroStr);

        Spinner ausgabeEinnahme = (Spinner) findViewById(R.id.ausgabenEinnahmen);
        String ausgabeEinnahmeStr = ausgabeEinnahme.getSelectedItem().toString();

        Spinner kategorie = (Spinner) findViewById(R.id.kategorie);
        String kategorieStr = kategorie.getSelectedItem().toString();

        if(dateStr.equals("") || dateStr.equals(" ") || dateStr.equals(null) || euroStr.equals("") || euroStr.equals(" ") || euroStr.equals(null) || kategorieStr.equals(Kategorien.valueOf("NOTHING"))){
        }

        else{
            if(ausgabeEinnahmeStr.equals("EINNAHME")){
                sum = sum + euroDouble;
                String sumStr = "" + sum;
                textView.setText("Cash " + sumStr + " €");

                arrayAdapter.clear();

                arrayList.add(dateStr + " | " + "+ " + euroDouble + " | " + kategorieStr);
                Collections.reverse(arrayList);

                for (String string : arrayList) {
                    arrayAdapter.insert(string, arrayAdapter.getCount());
                }

                arrayAdapter.notifyDataSetChanged();
            }

            else {
                sum = sum - euroDouble;
                String sumStr = df.format(sum);
                textView.setText("Cash " + sumStr + " €");

                arrayAdapter.clear();

                arrayList.add(dateStr + " | " + "- " + euroDouble + " | " + kategorieStr);
                Collections.reverse(arrayList);

                for (String string : arrayList) {
                    arrayAdapter.insert(string, arrayAdapter.getCount());
                }

                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
