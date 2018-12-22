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
    TableLayout tableLayout;
    Item item;
    ItemDao itemDao;
    ActivityCompanyDetailsBinding binding;
    int itemId;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        itemDao = MyNagoriApplication.getDatabase().itemDao();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_details);
        tableLayout=(TableLayout)findViewById(R.id.main_table);
        itemId = getIntent().getIntExtra("oem", -1);
        List<Item> itemList=itemDao.getParts(Integer.toString(itemId));
        tableData();


        for(int i=0; i<itemList.size();i++){
         TableRow data = new TableRow(this);
         data.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
                 intent.putExtra("details", item.id);
                 startActivity(intent);
             }
         });
         }
        }

    @SuppressLint("ResourceType")
    public void tableData(){
        TableRow data = new TableRow(this);
        data.setId(1);
        data.setBackgroundColor(Color.GRAY);
        data.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView sno = new TextView(this);
        sno.setId(0);
        sno.setText("Sno");
        sno.setTextColor(Color.WHITE);
        sno.setPadding(5, 5, 5, 5);

        TextView oem = new TextView(this);
        oem.setId(1);
        oem.setText("OEM");
        oem.setTextColor(Color.WHITE);
        oem.setPadding(5, 5, 5, 5);

        TextView telPart = new TextView(this);
        telPart.setId(2);
        telPart.setText("TEL PART NUMBER");
        telPart.setTextColor(Color.WHITE);
        telPart.setPadding(5, 5, 5, 5);

        TextView emgine = new TextView(this);
        emgine.setId(3);
        emgine.setText("Engine");
        emgine.setTextColor(Color.WHITE);
        emgine.setPadding(5, 5, 5, 5);

        TextView application = new TextView(this);
        application.setId(4);
        application.setText("Application");
        application.setTextColor(Color.WHITE);
        application.setPadding(5, 5, 5, 5);

        TextView mrp = new TextView(this);
        mrp.setId(5);
        mrp.setText("MRP");
        mrp.setTextColor(Color.WHITE);
        mrp.setPadding(5, 5, 5, 5);

        TextView orientationAlpha = new TextView(this);
        orientationAlpha.setId(6);
        orientationAlpha.setText("Orientation Alpha");
        orientationAlpha.setTextColor(Color.WHITE);
        orientationAlpha.setPadding(5, 5, 5, 5);

        TextView orientationBeta = new TextView(this);
        orientationBeta.setId(7);
        orientationBeta.setText("Orientation Beta");
        orientationBeta.setTextColor(Color.WHITE);
        orientationBeta.setPadding(5, 5, 5, 5);

        TextView strPre = new TextView(this);
        strPre.setId(8);
        strPre.setText("Str pre");
        strPre.setTextColor(Color.WHITE);
        strPre.setPadding(5, 5, 5, 5);

        TextView settingPr = new TextView(this);
        settingPr.setId(9);
        settingPr.setText("Setting Pr");
        settingPr.setTextColor(Color.WHITE);
        settingPr.setPadding(5, 5, 5, 5);

        TextView lift = new TextView(this);
        lift.setId(10);
        lift.setText("Lift");
        lift.setTextColor(Color.WHITE);
        lift.setPadding(5, 5, 5, 5);

        TextView reman = new TextView(this);
        reman.setId(11);
        reman.setText("Reman");
        reman.setTextColor(Color.WHITE);
        reman.setPadding(5, 5, 5, 5);

    }
}
