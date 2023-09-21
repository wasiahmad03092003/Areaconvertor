package com.example.aeraconvertor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText valueEditText;
    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private Button convertButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueEditText = findViewById(R.id.valueEditText);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.area_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertArea();
            }
        });
    }

    private void convertArea() {
        String valueStr = valueEditText.getText().toString();
        if (valueStr.isEmpty()) {
            resultTextView.setText("Please enter a value.");
            return;
        }

        double value = Double.parseDouble(valueStr);
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();

        double convertedValue = convert(value, fromUnit, toUnit);
        displayResult(convertedValue, toUnit);
    }

    private double convert(double value, String fromUnit, String toUnit) {
        // Define conversion factors for various area units
        double squareMeterToSquareFoot = 10.7639;
        double squareMeterToAcre = 0.000247105;
        double squareFootToSquareMeter = 0.092903;
        double squareFootToAcre = 0.0000229568;
        double acreToSquareMeter = 4046.86;
        double acreToSquareFoot = 43560;

        if (fromUnit.equals("Square Meter") && toUnit.equals("Square Foot")) {
            return value * squareMeterToSquareFoot;
        } else if (fromUnit.equals("Square Meter") && toUnit.equals("Acre")) {
            return value * squareMeterToAcre;
        } else if (fromUnit.equals("Square Foot") && toUnit.equals("Square Meter")) {
            return value * squareFootToSquareMeter;
        } else if (fromUnit.equals("Square Foot") && toUnit.equals("Acre")) {
            return value * squareFootToAcre;
        } else if (fromUnit.equals("Acre") && toUnit.equals("Square Meter")) {
            return value * acreToSquareMeter;
        } else if (fromUnit.equals("Acre") && toUnit.equals("Square Foot")) {
            return value * acreToSquareFoot;
        } else {
            return value; // Default to no conversion
        }
    }

    private void displayResult(double convertedValue, String toUnit) {
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(convertedValue) + " " + toUnit;
        resultTextView.setText(result);
    }
}
