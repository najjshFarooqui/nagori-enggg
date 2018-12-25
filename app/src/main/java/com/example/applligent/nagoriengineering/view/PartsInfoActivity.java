package com.example.applligent.nagoriengineering.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.SubItemDao;
import com.example.applligent.nagoriengineering.databinding.ActivityPartsInfoBinding;
import com.example.applligent.nagoriengineering.model.SubItem;

import java.util.List;

public class PartsInfoActivity extends AppCompatActivity {
    SubItem subItem;
    ActivityPartsInfoBinding binding;
    String telPart;
    TableLayout tableLayout;
    SubItemDao subItemDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Serviceble Parts");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        subItemDao = MyNagoriApplication.getDatabase().subItemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parts_info);
        telPart = getIntent().getStringExtra("tel_part_number");
        binding.tcPartLabel.setText("TC PART NO: " + telPart);

        TableRow info = new TableRow(this);
        info.setBackgroundColor(Color.GRAY);
        info.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TableRow headingRow = new TableRow(this);
        headingRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        headingRow.setId(-1);
        headingRow.addView(getHeaderLabel(1 * 100, "PART NO."));
        headingRow.addView(getHeaderLabel(2 * 100, "DESCRIPTION"));
        headingRow.addView(getHeaderLabel(3 * 100, "MRP"));
        binding.tableLayout.addView(headingRow, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


        List<SubItem> subItemDetail = subItemDao.getSubParts(telPart);
        for (int i = 0; i < subItemDetail.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setId(i);
            subItem = subItemDetail.get(i);
            tableRow.addView(getRowLabel(i, i * 1000, subItem.partNumber));
            tableRow.addView(getRowLabel(i, i * 1000, subItem.description));
            tableRow.addView(getRowLabel(i, i * 1000, subItem.mrp + ""));
            binding.tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
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
        label.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
        label.setPadding(24, 24, 24, 24);
        if (rowId % 2 == 0)
            label.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        else
            label.setBackgroundColor(ContextCompat.getColor(this, R.color.table_row));

        return label;
    }

}

