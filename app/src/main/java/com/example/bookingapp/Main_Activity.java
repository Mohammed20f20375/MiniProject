package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main_Activity extends AppCompatActivity {

    EditText etCustomerName, etMobileNumber, etNumberOfGuests;
    CalendarView calendarView;
    TimePicker timePicker;
    Button btnAdd, btnView, btnClear, btnMenu , Logout;
    dbHelper dbh;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new dbHelper(this);
        initializeViews();


        btnMenu = findViewById(R.id.btn_menu); // Initialize btnMenu
        btnMenu.setOnClickListener(view -> {
            // Start the MenuActivity
            Intent l = new Intent(Main_Activity.this, MenuActivity.class);
            startActivity(l);
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) ->
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year);

        addData();
        viewBookings();
        clearData();
    }

    public void initializeViews() {
        etCustomerName = findViewById(R.id.et_customer_name);
        etMobileNumber = findViewById(R.id.et_mobile_number);
        etNumberOfGuests = findViewById(R.id.et_number_of_guests);
        calendarView = findViewById(R.id.calendar_view);
        timePicker = findViewById(R.id.time_picker);
        btnAdd = findViewById(R.id.btn_add);
        btnView = findViewById(R.id.btn_view);
        btnClear = findViewById(R.id.btn_clear);
        Logout= findViewById(R.id.logout);
    }

    public void addData() {
        btnAdd.setOnClickListener(view -> {
            String name = etCustomerName.getText().toString().trim();
            String mobile = etMobileNumber.getText().toString().trim();
            String guests = etNumberOfGuests.getText().toString().trim();
            String time = String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute());

            if (name.isEmpty() || mobile.isEmpty() || guests.isEmpty() || selectedDate == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbh.insertData(name, mobile, guests, selectedDate, time);
            Toast.makeText(this, result ? "Booking Added" : "Error Adding Booking", Toast.LENGTH_SHORT).show();
        });
    }

    public void viewBookings() {
        btnView.setOnClickListener(view -> {

        });
    }

    public void clearData() {
        btnClear.setOnClickListener(view -> {
            etCustomerName.setText("");
            etMobileNumber.setText("");
            etNumberOfGuests.setText("");
            timePicker.setHour(0);
            timePicker.setMinute(0);
            Toast.makeText(this, "Fields Cleared", Toast.LENGTH_SHORT).show();
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss= new Intent(Main_Activity.this,Login.class);
                startActivity(ss);
                finish();
            }
        });


    }
}
