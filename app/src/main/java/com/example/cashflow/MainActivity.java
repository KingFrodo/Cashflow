package com.example.cashflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        readCsv();
        textView = (TextView) findViewById(R.id.cash);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        Spinner ausgabeEinnahme = (Spinner) findViewById(R.id.ausgabenEinnahmen);
        ausgabeEinnahme.setAdapter(new ArrayAdapter<EinnahmeAusgabe>(this, android.R.layout.simple_spinner_item, EinnahmeAusgabe.values()));
        Spinner kategorien = (Spinner) findViewById(R.id.kategorie);
        kategorien.setAdapter(new ArrayAdapter<Kategorien>(this, android.R.layout.simple_spinner_item, Kategorien.values()));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        Log.e("Create", "App created    METHOD: onCreate");
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

                try (FileWriter fw = new FileWriter(file)) {
                    fw.write(dateStr + ";");
                    fw.write("+ " + euroDouble + ";");
                    fw.write(kategorieStr + ";");
                    fw.write("\n");
                    fw.flush();
                    fw.close();
                } catch(IOException ex){
                    ex.printStackTrace();
                }
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
                //arrayAdapter funktioniert nicht richtig

                try (FileWriter fw = new FileWriter(file)) {
                    fw.write(dateStr + ";");
                    fw.write("+ " + euroDouble + ";");
                    fw.write(kategorieStr + ";");
                    fw.write("\n");
                    fw.flush();
                    fw.close();

                    //man kann nicht writen
                } catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }

        Log.e("Button Click", "Button got clicked   METHOD: okClick");
    }

    public void readCsv(){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(";");
                String euroStr = split[1];
                String date = split[0];
                String category = split[2];

                arrayList.add(date + " | " + euroStr + " | " + category);

                if(euroStr.contains("+")){
                    euroStr.replace("+", "");
                    double euro = Double.parseDouble(euroStr);
                    sum = sum + euro;
                }

                else{
                    euroStr.replace("-", "");
                    double euro = Double.parseDouble(euroStr);
                    sum = sum - euro;
                }
            }

            Collections.reverse(arrayList);

            for (String string : arrayList) {
                arrayAdapter.insert(string, arrayAdapter.getCount());
            }

            arrayAdapter.notifyDataSetChanged();
        }


        catch(IOException ex){
            ex.printStackTrace();
        }

        //readCSV

        Log.e("Read", "Read CSV file    METHOD: readCSV");
    }
}
