package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private EditText totalBillWithTax, noOfPpl;

    private TextView tipAmt, totalWithTip, totalPerPerson;

    private RadioGroup radGrp;
    private RadioButton rad12, rad15, rad18, rad20;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieving the TextView fields
        totalBillWithTax = findViewById(R.id.billtotwithtaxval);
        tipAmt = findViewById(R.id.tipamtval);
        totalWithTip = findViewById(R.id.totwithtipval);

        //retrieving the EditText fields
        noOfPpl = findViewById(R.id.noofpplval);
        totalPerPerson = findViewById(R.id.totperpersonval);

        //retrieving the radio button and it's group
        radGrp = findViewById(R.id.radioGroup);
        rad12 = findViewById(R.id.tip12);
        rad15 = findViewById(R.id.tip15);
        rad18 = findViewById(R.id.tip18);
        rad20 = findViewById(R.id.tip20);

    }

    public void calculateTipAmt(View view)
    {
        //retrieving the provided total bill value by the user
        String totalBillVal = totalBillWithTax.getText().toString();
        if(totalBillVal.contains("$"))
            totalBillVal =  totalBillVal.substring(1);

        if(TextUtils.isEmpty(totalBillVal))
        {
            //initial display of radio buttons
            rad12.setChecked(false);
            rad15.setChecked(false);
            rad18.setChecked(false);
            rad20.setChecked(false);

            return;
        }

        double billVal = Double.parseDouble(totalBillVal);
        double tipVal = 0.0, totAmtWithTip;

        // calculate the total amount with tip
        if (view.getId() == R.id.tip12)
            tipVal = billVal * 12 / 100;

        else if (view.getId() == R.id.tip15)
            tipVal = billVal * 15 / 100;

        else if (view.getId() == R.id.tip18)
            tipVal = billVal * 18 / 100;

        else if (view.getId() == R.id.tip20)
            tipVal = billVal * 20 / 100;

        // calculating total amount with tip
        totAmtWithTip = billVal + tipVal;

        //setting the respective fields with the calculated value
        totalBillWithTax.setText(String.format("$%.2f", billVal));
        tipAmt.setText(String.format("$%.2f", tipVal));
        totalWithTip.setText(String.format("$%.2f", totAmtWithTip));
    }

    public void calculatePerPerson(View v)
    {
        // check if total bill amount is available
        if(TextUtils.isEmpty(totalBillWithTax.getText().toString()))
        {
            Toast.makeText(this, "Please enter total bill amount", Toast.LENGTH_SHORT).show();
            return;
        }

        // check if tip percent is selected
        if(radGrp.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(this, "Please select a tip percent", Toast.LENGTH_SHORT).show();
            return;
        }

        // check if number of people are specified
        if(TextUtils.isEmpty(noOfPpl.getText().toString()))
        {
            Toast.makeText(this, "Please specify number of people", Toast.LENGTH_SHORT).show();
            return;
        }

        if((noOfPpl.getText().toString()).equalsIgnoreCase("0"))
        {
            Toast.makeText(this, "Please specify a positive whole number for number of people value", Toast.LENGTH_SHORT).show();
            return;
        }

        // calculate total per person
        double totalAmtVal = Double.parseDouble(totalWithTip.getText().toString().substring(1));
        double perPersonVal =  totalAmtVal / Double.parseDouble(noOfPpl.getText().toString());

        //setting the calculated total per person value field
        totalPerPerson.setText(String.format("$%.2f", perPersonVal));
    }

    public void clear(View v)
    {
        //unsetting all the radio buttons
        rad12.setChecked(false);
        rad15.setChecked(false);
        rad18.setChecked(false);
        rad20.setChecked(false);

        //clearing the TextView fields
        tipAmt.setText("");
        totalWithTip.setText("");
        totalPerPerson.setText("");

        //clearing the EditText fields
        totalBillWithTax.setText("");
        noOfPpl.setText("");

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // saving the instance before there is change in orientation
        //preserving the value of calculated tip amount value
        outState.putString("tipAmount", tipAmt.getText().toString());
        //preserving the value of calculated total bill with tip amount value
        outState.putString("totalWithTip", totalWithTip.getText().toString());
        //preserving the value of calculated total per person amount value
        outState.putString("totalPerPerson", totalPerPerson.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // restoring the state as previous before there is change in orientation
        super.onRestoreInstanceState(savedInstanceState);

        //setting the value of calculated tip amount value that was preserved before
        tipAmt.setText(savedInstanceState.getString("tipAmount"));
        //setting the value of calculated total bill with tip amount value that was preserved before
        totalWithTip.setText(savedInstanceState.getString("totalWithTip"));
        //setting the value of calculated total per person amount value that was preserved before
        totalPerPerson.setText(savedInstanceState.getString("totalPerPerson"));
    }
}