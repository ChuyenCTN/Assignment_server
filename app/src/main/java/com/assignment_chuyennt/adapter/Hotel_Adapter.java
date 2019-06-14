package com.assignment_chuyennt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment_chuyennt.R;
import com.assignment_chuyennt.models.Hotel_model;
import com.assignment_chuyennt.models.IP_Address;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Hotel_Adapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<Hotel_model> hotelModelList;

    public Hotel_Adapter(Context context, int resource, List<Hotel_model> hotelModelList) {
        super(context, resource, hotelModelList);
        this.context = context;
        this.resource = resource;
        this.hotelModelList = hotelModelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
            holder = new ViewHolder();
            holder.imgAvatarHotel = (ImageView) convertView.findViewById(R.id.img_avatar_hotel);
            holder.tvNameHotel = (TextView) convertView.findViewById(R.id.tv_name_hotel);
            holder.tvOwnerHotel = (TextView) convertView.findViewById(R.id.tv_owner_hotel);
            holder.tvCity = (TextView) convertView.findViewById(R.id.tv_city);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Hotel_model hotelModel = hotelModelList.get(position);
        String image = hotelModel.getImage();
        String image2 = String.valueOf(image.substring(8));
        String image3 = "http://"+ IP_Address.ip_Address+":3000/images/" + image2;
        Picasso.with(context).load(String.valueOf(image3)).into(holder.imgAvatarHotel);
        holder.tvNameHotel.setText(hotelModel.getName());
        holder.tvOwnerHotel.setText("Owner: " + hotelModel.getOwner());
        holder.tvCity.setText("City: " + hotelModel.getCity());
        return convertView;
    }

    class ViewHolder {
        private ImageView imgAvatarHotel;
        private TextView tvNameHotel;
        private TextView tvOwnerHotel;
        private TextView tvCity;
        private TextView tvTotalfloorHotel;
        private TextView tvLicensenumberHotel;
        private TextView tvAddress;
    }
}
