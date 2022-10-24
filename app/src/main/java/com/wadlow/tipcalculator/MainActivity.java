package com.wadlow.tipcalculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private TipCalculator tipCalc;
    public NumberFormat money = NumberFormat.getCurrencyInstance( );
    private EditText billEditText;
    private EditText tipEditText;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.w( "MainActivity", "Inside onCreate" );
        super.onCreate( savedInstanceState );
        tipCalc = new TipCalculator( 0.17f, 100.0f );
        setContentView( R.layout.activity_main );
        Configuration config = getResources( ).getConfiguration( );
        modifyLayout( config );

        billEditText = ( EditText ) findViewById( R.id.amount_bill );
        tipEditText = ( EditText ) findViewById( R.id.amount_tip_percent );

        TextChangeHandler tch = new TextChangeHandler( );
        billEditText.addTextChangedListener( tch );
        tipEditText.addTextChangedListener(tch);
    }

    public void onConfigurationChanged( Configuration newConfig ) {
        Log.w( "MainActivity", "Inside onConfigurationChanged" );
        super.onConfigurationChanged( newConfig );
        modifyLayout( newConfig );
    }

    public void modifyLayout( Configuration newConfig ) {
        Log.w( "MainActivity", "Inside modifyLayout" );
        if( newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE )
            setContentView( R.layout.activity_main_landscape );
        else if( newConfig.orientation == Configuration.ORIENTATION_PORTRAIT )
            setContentView( R.layout.activity_main );
    }

    public void calculate( ) {
        String billString = billEditText.getText( ).toString( );
        String tipString = tipEditText.getText( ).toString( );

        TextView tipTextView =
                ( TextView ) findViewById( R.id.amount_tip );
        TextView totalTextView =
                (TextView) findViewById( R.id.amount_total );
        try {
            // convert billString and tipString to floats
            float billAmount = Float.parseFloat( billString );
            int tipPercent = Integer.parseInt( tipString );
            // update the Model
            tipCalc.setBill( billAmount );
            tipCalc.setTip( .01f * tipPercent );
            // ask Model to calculate tip and total amounts
            float tip = tipCalc.tipAmount();
            float total = tipCalc.totalAmount();
            // update the View with formatted tip and total amounts
            tipTextView.setText( money.format( tip ) );
            totalTextView.setText( money.format( total ) );
        } catch( NumberFormatException nfe ) {
            // pop up an alert view here
        }
    }

    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged( Editable e ) {
            calculate( );
        }

        public void beforeTextChanged( CharSequence s, int start,
                                       int count, int after ) {
        }

        public void onTextChanged( CharSequence s, int start,
                                   int before, int after ) {
        }
    }
}