package com.example.calapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the main layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(16, 16, 16, 16);

        // Create and add the display TextView
        display = new TextView(this);
        display.setTextSize(36);
        display.setGravity(android.view.Gravity.END);
        display.setPadding(16, 16, 16, 16);
        display.setBackgroundColor(0xFFEEEEEE);
        display.setText("0");
        LinearLayout.LayoutParams displayParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mainLayout.addView(display, displayParams);

        // Create the button grid
        LinearLayout buttonGrid = new LinearLayout(this);
        buttonGrid.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams buttonGridParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonGridParams.setMargins(0, 16, 0, 0);
        mainLayout.addView(buttonGrid, buttonGridParams);

        // Define button labels
        String[][] buttonLabels = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", "C", "=", "+"}
        };

        // Create and add buttons to the grid
        for (String[] row : buttonLabels) {
            LinearLayout buttonRow = new LinearLayout(this);
            buttonRow.setOrientation(LinearLayout.HORIZONTAL);
            buttonRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            for (String label : row) {
                Button button = new Button(this);
                button.setText(label);
                button.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClick(((Button) v).getText().toString());
                    }
                });
                buttonRow.addView(button);
            }

            buttonGrid.addView(buttonRow);
        }

        setContentView(mainLayout);
    }

    private void onButtonClick(String buttonText) {
        switch (buttonText) {
            case "=":
                onEqualsClick();
                break;
            case "C":
                onClearClick();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                onOperatorClick(buttonText);
                break;
            default:
                onNumberClick(buttonText);
                break;
        }
    }

    private void onNumberClick(String number) {
        if (isNewInput) {
            currentInput = number;
            isNewInput = false;
        } else {
            currentInput += number;
        }
        updateDisplay();
    }

    private void onOperatorClick(String op) {
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
            operator = op;
            isNewInput = true;
        }
    }

    private void onEqualsClick() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        currentInput = "Error";
                        updateDisplay();
                        return;
                    }
                    break;
            }
            currentInput = String.valueOf(result);
            operator = "";
            isNewInput = true;
            updateDisplay();
        }
    }

    private void onClearClick() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        isNewInput = true;
        updateDisplay();
    }

    private void updateDisplay() {
        display.setText(currentInput.isEmpty() ? "0" : currentInput);
    }
}