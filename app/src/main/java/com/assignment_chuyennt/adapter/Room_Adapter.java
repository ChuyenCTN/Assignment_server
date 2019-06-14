package com.assignment_chuyennt.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment_chuyennt.R;
import com.assignment_chuyennt.models.Hotel_model;
import com.assignment_chuyennt.models.IP_Address;
import com.assignment_chuyennt.models.Room_model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Room_Adapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<Room_model> roomModelList;
    private List<Hotel_model> hotelModelList;
    private String type = "";

    public Room_Adapter(Context context, int resource, List<Room_model> roomModelList, List<Hotel_model> hotelModelList, String type) {
        super(context, resource, roomModelList);
        this.context = context;
        this.resource = resource;
        this.hotelModelList = hotelModelList;
        this.roomModelList = roomModelList;
        this.type = type;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
            holder = new ViewHolder();
            holder.imgAvatarRoom = (ImageView) convertView.findViewById(R.id.img_avatar_room);
            holder.tvNumberRoom = (TextView) convertView.findViewById(R.id.tv_number_room);
            holder.tvHotelRoom = (TextView) convertView.findViewById(R.id.tv_hotel_room);
            holder.tvPriceRoom = (TextView) convertView.findViewById(R.id.tv_price_room);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Room_model room_model = roomModelList.get(position);
        String image = room_model.getImage();
        String image2 = String.valueOf(image.substring(8));
        String image3 = "http://" + IP_Address.ip_Address + ":3000/images/" + image2;
        Picasso.with(context).load(String.valueOf(image3)).into(holder.imgAvatarRoom);
        holder.tvNumberRoom.setText(room_model.getNumberroom());
        holder.tvPriceRoom.setText("Price: " + room_model.getPrice() + " VND");
        if (type.equalsIgnoreCase("hotel")) {
            holder.tvHotelRoom.setText("Floor: " + room_model.getFloor());
        } else {
            for (int i = 0; i <= hotelModelList.size(); i++) {
                if (room_model.getHotelid().equalsIgnoreCase(hotelModelList.get(i).getId())) {
                    holder.tvHotelRoom.setText("Hotel: " + hotelModelList.get(i).getName());
                    break;
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView imgAvatarRoom;
        private TextView tvNumberRoom;
        private TextView tvHotelRoom;
        private TextView tvPriceRoom;


    }
}