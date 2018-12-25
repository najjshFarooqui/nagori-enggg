package com.example.applligent.nagoriengineering.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        subItemDao = MyNagoriApplication.getDatabase().subItemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parts_info);
        telPart = getIntent().getStringExtra("tel_part_number");

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

        List<SubItem> subItemDetail = subItemDao.getSubParts(telPart);
        for (int i = 0; i < subItemDetail.size(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setId(i);
            subItem = subItemDetail.get(i);
            tableRow.addView(getRowLabel(i*100, subItem.partNumber));
            tableRow.addView(getRowLabel(i*100, subItem.description));
            tableRow.addView(getRowLabel(i*100, subItem.mrp+""));
            binding.tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));


        }
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

