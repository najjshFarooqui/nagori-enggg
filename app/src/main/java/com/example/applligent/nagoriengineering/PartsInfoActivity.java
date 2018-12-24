package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.databinding.ActivityPartsInfoBinding;

import org.w3c.dom.Text;

public class PartsInfoActivity extends AppCompatActivity {
    Item item;
    ItemDao itemDao;
    ActivityPartsInfoBinding binding;
    String  telPart;
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parts_info);
        telPart = getIntent().getStringExtra("tel_part_number");
        tableLayout=(TableLayout)findViewById(R.id.tableLayout);

        TableRow info = new TableRow(this);
       info.setBackgroundColor(Color.GRAY);
       info.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView partNo = new TextView(this);
        partNo.setText("PART NO.");

        TextView description = new TextView(this);
        description.setText("DESCRIPTION");

        TextView mrp = new TextView(this);
        mrp.setText("MRP");


}
