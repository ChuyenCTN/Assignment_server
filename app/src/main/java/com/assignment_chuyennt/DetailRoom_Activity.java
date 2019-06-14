package com.assignment_chuyennt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment_chuyennt.models.IP_Address;
import com.squareup.picasso.Picasso;

public class DetailRoom_Activity extends AppCompatActivity {
    private Toolbar toolBarDetail;
    private ImageView imgDetailRoom;
    private TextView tvNumberRoomDetail;
    private TextView tvHotelRoomDetail;
    private TextView tvFloorRoomDetail;
    private TextView tvSinggleRoomDetail;
    private TextView tvStatusRoomDetail;
    private TextView tvPriceRoomDetail;
    private TextView tvDetailRoomDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        toolBarDetail = (Toolbar) findViewById(R.id.toolBar_Detail);
        imgDetailRoom = (ImageView) findViewById(R.id.img_detail_room);
        tvNumberRoomDetail = (TextView) findViewById(R.id.tv_number_room_detail);
        tvHotelRoomDetail = (TextView) findViewById(R.id.tv_hotel_room_detail);
        tvFloorRoomDetail = (TextView) findViewById(R.id.tv_floor_room_detail);
        tvSinggleRoomDetail = (TextView) findViewById(R.id.tv_singgle_room_detail);
        tvStatusRoomDetail = (TextView) findViewById(R.id.tv_status_room_detail);
        tvPriceRoomDetail = (TextView) findViewById(R.id.tv_price_room_detail);
        tvDetailRoomDetail = (TextView) findViewById(R.id.tv_detail_room_detail);


        Intent intent = getIntent();
        tvDetailRoomDetail.setText("Detail: " + intent.getStringExtra("detail"));
        tvFloorRoomDetail.setText("Floor: " + intent.getStringExtra("floor"));
        tvHotelRoomDetail.setText("Hotel: " + intent.getStringExtra("hotelname"));
        tvPriceRoomDetail.setText("Price: " + intent.getStringExtra("price") + " VND");
        if ((intent.getStringExtra("singleroom")).equalsIgnoreCase("true")) {
            tvSinggleRoomDetail.setText("Single room: Yes");
        } else {
            tvSinggleRoomDetail.setText("Single room: No");
        }
        if ((intent.getStringExtra("status")).equalsIgnoreCase("1")) {
            tvStatusRoomDetail.setText("Status: Đang trống");
        } else if ((intent.getStringExtra("status")).equalsIgnoreCase("2")) {
            tvStatusRoomDetail.setText("Status: Đã đặt");
        } else {
            tvStatusRoomDetail.setText("Status: Không sử dụng");
        }
        tvNumberRoomDetail.setText(intent.getStringExtra("roomnumber"));
        String image = intent.getStringExtra("image");
        String image2 = String.valueOf(image.substring(8));
        String image3 = "http://"+ IP_Address.ip_Address+":3000/images/" + image2;
        Picasso.with(getApplicationContext()).load(String.valueOf(image3)).into(imgDetailRoom);
    }

    public void back_activity(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
