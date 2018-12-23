package com.example.applligent.nagoriengineering;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.databinding.ActivityCompanyDetailsBinding;

import java.util.List;

public class CompanyDetailsActivity extends AppCompatActivity {
    Item item;
    ItemDao itemDao;
    ActivityCompanyDetailsBinding binding;
    String oem;
    List<Item> itemList;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_details);
        oem = getIntent().getStringExtra("oem");
        itemList = itemDao.getParts(oem);
        headerData();
        rowData();



    }

    @SuppressLint("ResourceType")

    public void headerData() {
        TableRow data = new TableRow(this);
        data.setId(-1);
        data.setBackgroundColor(Color.GRAY);
        data.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        String[] headers = {"Sno.", "OEM", "Tel Part Number", "Engine", "Application", "Mrp", "Orientation Alpha", "Orientation Beta", "Str Pre", "Setting pr", "Lift", "Reman"};
        for (int i = 0; i < headers.length; i++) {
            data.addView(getHeaderLabel(i, headers[i]));
        }
        binding.tableLayout.addView(data, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }


    public void rowData() {

        for (int i = 0; i < itemList.size(); i++) {
            TableRow row = new TableRow(this);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item=new Item();
                    Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
                    intent.putExtra("tel_part_number", item.telPartNumber);
                    startActivity(intent);
                }
            });
            row.setId(i);
            row.setBackgroundColor(Color.GRAY);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            Item item = itemList.get(i);
            row.addView(getRowLabel(i * 1, item.sNo + ""));
            row.addView(getRowLabel(i * 2, item.oem));
            row.addView(getRowLabel(i * 3, item.telPartNumber));
            row.addView(getRowLabel(i * 4, item.engine));
            row.addView(getRowLabel(i * 5, item.application));
            row.addView(getRowLabel(i * 6, Float.toString(item.mrp)));
            row.addView(getRowLabel(i * 7, Integer.toString(item.orientationAlpha)));
            row.addView(getRowLabel(i * 8, Integer.toString(item.orientationBeta)));
            row.addView(getRowLabel(i * 9, item.strPre));
            row.addView(getRowLabel(i * 10, item.settingPre));
            row.addView(getRowLabel(i * 11, item.lift));
            row.addView(getRowLabel(i * 12, item.reman));
            binding.tableLayout.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }


    }


    private TextView getHeaderLabel(int id, String text) {
        TextView label = new TextView(this);
        label.setId(100 * id);
        label.setText(text);
        label.setTextColor(Color.WHITE);
        label.setPadding(5, 5, 5, 5);
        return label;

    }

    private TextView getRowLabel(int id, String text) {
        TextView label = new TextView(this);
        label.setId(100 * id);
        label.setText(text);
        label.setTextColor(Color.WHITE);
        label.setPadding(5, 5, 5, 5);
        return label;


    }

}
