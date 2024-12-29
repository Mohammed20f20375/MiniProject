package com.example.bookingapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
    Button btnAdd, btnView, btnClear, btnMenu;
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
            Intent intent = new Intent(Main_Activity.this, MenuActivity.class);
            startActivity(intent);
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
            Cursor res = dbh.getAllBookings();
            if (res.getCount() == 0) {
                // No bookings found
                Toast.makeText(this, "No bookings available", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare a string to display the bookings
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getString(0)).append("\n");
                buffer.append("Name: ").append(res.getString(1)).append("\n");
                buffer.append("Mobile: ").append(res.getString(2)).append("\n");
                buffer.append("Guests: ").append(res.getString(3)).append("\n");
                buffer.append("Date: ").append(res.getString(4)).append("\n");
                buffer.append("Time: ").append(res.getString(5)).append("\n\n");
            }

            // Show bookings in a dialog
            showAlertDialog("Bookings", buffer.toString());
        });
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
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
    }
}
