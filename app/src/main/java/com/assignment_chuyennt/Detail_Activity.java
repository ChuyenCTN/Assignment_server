package com.assignment_chuyennt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment_chuyennt.models.IP_Address;
import com.squareup.picasso.Picasso;

public class Detail_Activity extends AppCompatActivity {
    private Toolbar toolBarDetail;
    private ImageView imgDetail;
    private TextView tvNameDetail;
    private TextView tvOwnerDetail;
    private TextView tvCityDetail;
    private TextView tvTotalfloorDetail;
    private TextView tvLicensenumberDetail;
    private TextView tvAddressDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolBarDetail = (Toolbar) findViewById(R.id.toolBar_Detail);
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        tvNameDetail = (TextView) findViewById(R.id.tv_name_detail);
        tvOwnerDetail = (TextView) findViewById(R.id.tv_owner_detail);
        tvCityDetail = (TextView) findViewById(R.id.tv_city_detail);
        tvTotalfloorDetail = (TextView) findViewById(R.id.tv_totalfloor_detail);
        tvLicensenumberDetail = (TextView) findViewById(R.id.tv_licensenumber_detail);
        tvAddressDetail = (TextView) findViewById(R.id.tv_address_detail);

        Intent intent=getIntent();
        tvNameDetail.setText(intent.getStringExtra("name"));
        tvAddressDetail.setText("Address: "+intent.getStringExtra("address"));
        tvCityDetail.setText("City: "+intent.getStringExtra("city"));
        tvLicensenumberDetail.setText("License number: "+intent.getStringExtra("licensenumber"));
        tvOwnerDetail.setText("Owner: "+intent.getStringExtra("owner"));
        tvTotalfloorDetail.setText("Total floor: "+intent.getStringExtra("totalfloor"));
        String image = intent.getStringExtra("image");
        String image2 = String.valueOf(image.substring(8));
        String image3 = "http://"+ IP_Address.ip_Address+":3000/images/" + image2;
        Picasso.with(getApplicationContext()).load(String.valueOf(image3)).into(imgDetail);

    }

    public void back_activity(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
