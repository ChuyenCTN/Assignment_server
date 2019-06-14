package com.assignment_chuyennt.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.assignment_chuyennt.DetailRoom_Activity;

import com.assignment_chuyennt.HttpHandler;
import com.assignment_chuyennt.R;

import com.assignment_chuyennt.adapter.Room_Adapter;
import com.assignment_chuyennt.models.Hotel_model;
import com.assignment_chuyennt.models.IP_Address;
import com.assignment_chuyennt.models.Room_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_Room extends Fragment {
    private ListView lvRoom;
    private String TAG = Fragment_Hotel.class.getSimpleName();
    private ProgressDialog progressDialog;
    private static String JsonurlRoom = "http://"+ IP_Address.ip_Address+":3000/api/rooms";
    private static String JsonurlHotel = "http://"+IP_Address.ip_Address+":3000/api/hotels";
    ArrayList<Room_model> RoomJSonModelList;
    ArrayList<Hotel_model> HotelJsonList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        lvRoom = (ListView) view.findViewById(R.id.lv_room);
        RoomJSonModelList = new ArrayList<>();
        HotelJsonList = new ArrayList<>();
        new getDataJSON1().execute();

        lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailRoom_Activity.class);
                intent.putExtra("floor", RoomJSonModelList.get(position).getFloor());
                intent.putExtra("singleroom", RoomJSonModelList.get(position).getSingleroom());
                intent.putExtra("price", RoomJSonModelList.get(position).getPrice());
                intent.putExtra("status", RoomJSonModelList.get(position).getStatus());
                intent.putExtra("image", RoomJSonModelList.get(position).getImage());
                intent.putExtra("detail", RoomJSonModelList.get(position).getDetail());
                intent.putExtra("hotelid", RoomJSonModelList.get(position).getHotelid());
                intent.putExtra("roomnumber", RoomJSonModelList.get(position).getNumberroom());
                for (int i = 0; i <= HotelJsonList.size(); i++) {
                    if ((RoomJSonModelList.get(position).getHotelid()).equalsIgnoreCase(HotelJsonList.get(i).getId())) {
                        intent.putExtra("hotelname", HotelJsonList.get(i).getName());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        return view;

    }

    public class getDataJSON1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();

            String jsonString = httpHandler.makeServiceCall(JsonurlRoom);
            String jsonStringHotel = httpHandler.makeServiceCall(JsonurlHotel);
            Log.e(TAG, "Response from url: " + jsonString);
            if (jsonStringHotel != null) {
                try {
                    JSONObject jsonObjectH = new JSONObject(jsonStringHotel);
                    JSONArray jsonArrayH = jsonObjectH.getJSONArray("data");

                    for (int i = 0; i < jsonArrayH.length(); i++) {
                        JSONObject hotels = jsonArrayH.getJSONObject(i);
                        String id = hotels.getString("_id");
                        String name = hotels.getString("name");
                        String owner = hotels.getString("owner");
                        String city = hotels.getString("city");
                        String totalfloor = hotels.getString("totalfloor");
                        String licensenumber = hotels.getString("licensenumber");
                        String address = hotels.getString("address");
                        String image = hotels.getString("image");

                        Hotel_model model = new Hotel_model(name, owner, city, licensenumber, address, image, totalfloor, id);
                        HotelJsonList.add(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject rooms = jsonArray.getJSONObject(i);
                        String roomnumber = rooms.getString("roomnumber");
                        String floor = rooms.getString("floor");
                        String singleroom = rooms.getString("singleroom");
                        String price = rooms.getString("price");
                        String status = rooms.getString("status");
                        String image = rooms.getString("image");
                        String detail = rooms.getString("detail");
                        String hotelid = rooms.getString("hotelid");

                        Room_model roomModel = new Room_model(roomnumber, image, floor, singleroom, price, detail, hotelid, status);
                        RoomJSonModelList.add(roomModel);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Json paring error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Could not get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Could not get json from server", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();


            Room_Adapter adapter = new Room_Adapter(getContext(), R.layout.item_room, RoomJSonModelList, HotelJsonList,"");
            lvRoom.setAdapter(adapter);
        }
    }
}

