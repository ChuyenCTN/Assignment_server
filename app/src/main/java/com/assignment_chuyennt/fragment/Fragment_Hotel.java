package com.assignment_chuyennt.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.assignment_chuyennt.Detail_Activity;
import com.assignment_chuyennt.HttpHandler;
import com.assignment_chuyennt.Playlist_Room_Activity;
import com.assignment_chuyennt.R;
import com.assignment_chuyennt.adapter.Hotel_Adapter;
import com.assignment_chuyennt.models.Hotel_model;
import com.assignment_chuyennt.models.IP_Address;
import com.assignment_chuyennt.models.Room_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_Hotel extends Fragment {
    private String TAG = Fragment_Hotel.class.getSimpleName();
    private ListView lvHotel;
    private ProgressDialog progressDialog;
    private static String JsonurlRoom = "http://" + IP_Address.ip_Address + ":3000/api/rooms";
    private static String Jsonurl = "http://" + IP_Address.ip_Address + ":3000/api/hotels";
    private SwipeRefreshLayout layoutRefreshH;

    ArrayList<Hotel_model> HotelJsonList;
    ArrayList<Room_model> RoomJSonModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        lvHotel = (ListView) view.findViewById(R.id.lv_hotel);
        layoutRefreshH = (SwipeRefreshLayout) view.findViewById(R.id.layout_RefreshH);
        HotelJsonList = new ArrayList<>();
        RoomJSonModelList = new ArrayList<>();
        new getDataJSON().execute();
        lvHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Detail_Activity.class);
                intent.putExtra("name", HotelJsonList.get(position).getName());
                intent.putExtra("totalfloor", HotelJsonList.get(position).getTotalfloor());
                intent.putExtra("image", HotelJsonList.get(position).getImage());
                intent.putExtra("licensenumber", HotelJsonList.get(position).getLicensenumber());
                intent.putExtra("city", HotelJsonList.get(position).getCity());
                intent.putExtra("address", HotelJsonList.get(position).getAddress());
                intent.putExtra("owner", HotelJsonList.get(position).getOwner());
                intent.putExtra("_id", HotelJsonList.get(position).getId());
                startActivity(intent);
            }
        });
        lvHotel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Playlist_Room_Activity.class);
                intent.putExtra("name", HotelJsonList.get(position).getName());
                intent.putExtra("_id", HotelJsonList.get(position).getId());

                startActivity(intent);
                return true;
            }
        });
        layoutRefreshH.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               lvHotel.removeAllViewsInLayout();

                layoutRefreshH.setRefreshing(false);
            }
        });

        return view;
    }

    public class getDataJSON extends AsyncTask<Void, Void, Void> {
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
            String jsonString1 = httpHandler.makeServiceCall(JsonurlRoom);
            String jsonString = httpHandler.makeServiceCall(Jsonurl);
            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hotels = jsonArray.getJSONObject(i);
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
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Json paring error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            if (jsonString1 != null) {
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("data");


                    for (int i1 = 0; i1 < jsonArray1.length(); i1++) {
                        JSONObject rooms = jsonArray1.getJSONObject(i1);
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
                } catch (JSONException e) {
                    e.printStackTrace();
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

            Hotel_Adapter adapter = new Hotel_Adapter(getContext(), R.layout.item_hotel, HotelJsonList);
            lvHotel.setAdapter(adapter);
        }
    }



}
