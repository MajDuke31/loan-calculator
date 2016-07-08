package edu.mobileappdevii.exercises.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class LoanCalculatorActivity extends AppCompatActivity {
    // Constants used when saving/restoring state
    private static final String LOAN_AMOUNT = "LOAN_AMOUNT";
    private static final String INTEREST_RATE = "INTEREST_RATE";

    private EditText amountEditText; // Accepts user input for loan amount
    private EditText interestRateEditText; // Accepts user input for interest rate
    private EditText fiveYearPremiumEditText; // Holds the calculated five year premium
    private EditText tenYearPremiumEditText; // Holds the calculated ten year premium
    private EditText fifteenYearPremiumEditText; // Holds the calculated fifteen year premium
    private EditText twentyYearPremiumEditText; // Holds the calculated twenty year premium
    private EditText twentyFiveYearPremiumEditText; // Holds the calculated twenty five year premium
    private EditText thirtyYearPremiumEditText; // Holds the calculated thirty year premium
    private double loanAmount;      // Holds the loan amount entered by the user
    private double interestRate;    // Holds the interest rate entered by the user
    private double equatedMonthlyInstallment; // Holds the equated monthly installment calculation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);
        // Check if app just started or being restored from memory
        if (savedInstanceState == null)
        {
            // Initialize fields
            loanAmount = 0.0;
            interestRate = 0.0;
        } else // Being restored from memory
        {
            // Initialize fields from saved values
            loanAmount = savedInstanceState.getDouble(LOAN_AMOUNT);
            interestRate = savedInstanceState.getDouble(INTEREST_RATE);
        }

        // Get references to all the items on the GUI
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        interestRateEditText = (EditText) findViewById(R.id.interestRateEditText);

        fiveYearPremiumEditText = (EditText) findViewById(R.id.fiveYearPremiumEditText);
        tenYearPremiumEditText = (EditText) findViewById(R.id.tenYearPremiumEditText);
        fifteenYearPremiumEditText = (EditText) findViewById(R.id.fifteenYearPremiumEditText);
        twentyYearPremiumEditText = (EditText) findViewById(R.id.twentyYearPremiumEditText);
        twentyFiveYearPremiumEditText = (EditText) findViewById(R.id.twentyFiveYearPremiumEditText);
        thirtyYearPremiumEditText = (EditText) findViewById(R.id.thirtyYearPremiumEditText);

        // Handle when the loan amount changes
        amountEditText.addTextChangedListener(amountEditTextWatcher);
        interestRateEditText.addTextChangedListener(interestRateEditTextWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loan_calculator, menu);
        return true;
    }

    // Update the monthly premiums for each of the loan lengths
    private void updateMonthlyPremiums() {
        // Calculate the interest rate
        double r = interestRate / 1200.0;

        // Calculate the 'n' month values for 5, 10 , 15, 20, 25 and 30 years
        double fiveMonthPeriod = 5 * 12;
        double tenMonthPeriod = 10 * 12;
        double fifteenMonthPeriod = 15 * 12;
        double twentyMonthPeriod = 20 * 12;
        double twentyFiveMonthPeriod = 25 * 12;
        double thirtyMonthPeriod = 30 * 12;

        // Update the monthly premiums with the Equated Monthly Installment for each period
        equatedMonthlyInstallment = calculateEquatedMonthlyInstallment(loanAmount, r, fiveMonthPeriod);
        fiveYearPremiumEditText.setText(String.format("%.02f", equatedMonthlyInstallment));

        equatedMonthlyInstallment = calculateEquatedMonthlyInstallment(loanAmount, r, tenMonthPeriod);
        tenYearPremiumEditText.setText(String.format("%.02f", equatedMonthlyInstallment));

        equatedMonthlyInstallment = calculateEquatedMonthlyInstallment(loanAmount, r, fifteenMonthPeriod);
        fifteenYearPremiumEditText.setText(String.format("%.02f", equatedMonthlyInstallment));

        equatedMonthlyInstallment = calculateEquatedMonthlyInstallment(loanAmount, r, twentyMonthPeriod);
        twentyYearPremiumEditText.setText(String.format("%.02f", equatedMonthlyInstallment));

        equatedMonthlyInstallment = calculateEquatedMonthlyInstallment(loanAmount, r, twentyFiveMonthPeriod);
        twentyFiveYearPremiumEditText.setText(String.format("%.02f", equatedMonthlyInstallment));

        equatedMonthlyInstallment = calculateEquatedMonthlyInstallment(loanAmount, r, thirtyMonthPeriod);
        thirtyYearPremiumEditText.setText(String.format("%.02f", equatedMonthlyInstallment));
    }

    // Calculates the Equated Monthly Installment or EMI
    private double calculateEquatedMonthlyInstallment(double amount, double rate, double period) {
        double denominator = Math.pow(1 + rate, period) - 1;

        // Check for divide by zero condition
        if (denominator == 0.0) {
            // Return the value 0.0 to avoid dividing by zero which returns NaN (Not a number)
            return 0.0;
        } else {
            // We can safely divide by the calculated amount
            return (amount * rate * Math.pow(1 + rate, period)) / denominator;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(LOAN_AMOUNT, loanAmount);
        outState.putDouble(INTEREST_RATE, interestRate);
    }

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Convert the EditText to a double
            // 's' contains a copy of the text
            try {
                loanAmount = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                loanAmount = 0.0;
            }

            updateMonthlyPremiums();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher interestRateEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Convert the EditText to a double
            // 's' contains a copy of the text
            try {
                interestRate = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                interestRate = 0.0;
            }

            updateMonthlyPremiums();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
