package com.example.applligent.nagoriengineering.view.company;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ItemDao;
import com.example.applligent.nagoriengineering.databinding.ActivityCompanyDetailsBinding;
import com.example.applligent.nagoriengineering.model.Item;

import java.util.List;

public class CompanyDetailsActivity extends AppCompatActivity {
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
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(oem);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        itemList = itemDao.getParts(oem);
        headerData();
        rowData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")

    public void headerData() {
        TableRow data = new TableRow(this);
        data.setId(-1);
        data.setBackgroundColor(Color.GRAY);
        data.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        String[] headers = {"SL No", "OEM Part Number", "TEL Part Number", "Engine", "Application", "MRP", "Orientation Alpha", "Orientation Beta", "Str Pre", "Setting pr", "Lift", "Reman"};
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

                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    //intent.putExtra("tel_part_number", itemList.get(v.getId()).telPartNumber);
                    int rowId = v.getId();
                    Item item = itemList.get(rowId);
                    intent.putExtra("item_id", item.getId());
                    startActivity(intent);
                }
            });
            row.setId(i);
            row.setBackgroundColor(Color.GRAY);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            Item item = itemList.get(i);
            row.addView(getRowLabel(i, i * 1, item.getSNo() + ""));
            row.addView(getRowLabel(i, i * 2, item.getTelPartNumber()));
            row.addView(getRowLabel(i, i * 3, item.getOem()));
            row.addView(getRowLabel(i, i * 4, item.getEngine()));
            row.addView(getRowLabel(i, i * 5, item.getApplication()));
            row.addView(getRowLabel(i, i * 6, Float.toString(item.getMrp())));
            row.addView(getRowLabel(i, i * 7, Integer.toString(item.getOrientationAlpha())));
            row.addView(getRowLabel(i, i * 8, Integer.toString(item.getOrientationBeta())));
            row.addView(getRowLabel(i, i * 9, item.getStrPre()));
            row.addView(getRowLabel(i, i * 10, item.getSettingPre()));
            row.addView(getRowLabel(i, i * 11, item.getLift()));
            row.addView(getRowLabel(i, i * 12, item.getReman()));

            binding.tableLayout.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }


    }


    private TextView getHeaderLabel(int id, String text) {
        TextView label = new TextView(this);
        label.setId(100 * id);
        label.setBackgroundColor(ContextCompat.getColor(this, R.color.table_header));
        label.setText(text);
        label.setTypeface(null, Typeface.BOLD);
        label.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
        label.setPadding(24, 24, 24, 24);
        return label;
    }

    private TextView getRowLabel(int rowId, int id, String text) {
        TextView label = new TextView(this);
        label.setId(1000 * id);
        label.setText(text);
        label.setTextColor(ContextCompat.getColor(this,R.color.primary_text));
        label.setPadding(24, 24, 24, 24);
        if(rowId%2==0)
            label.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        else
            label.setBackgroundColor(ContextCompat.getColor(this,R.color.table_row));

        return label;
    }


}
