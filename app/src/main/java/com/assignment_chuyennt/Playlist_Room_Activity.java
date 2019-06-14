package com.assignment_chuyennt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.assignment_chuyennt.adapter.Room_Adapter;
import com.assignment_chuyennt.models.Hotel_model;
import com.assignment_chuyennt.models.IP_Address;
import com.assignment_chuyennt.models.Room_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Playlist_Room_Activity extends AppCompatActivity {
    private String TAG = Playlist_Room_Activity.class.getSimpleName();
    private Toolbar toolBarPlaylist;
    private TextView tvNameHotelPlaylist;
    private ListView lvRoomPlaylist;
    private TextView tvEmptyRoom;
    private static String JsonurlRoom = "http://" + IP_Address.ip_Address + ":3000/api/rooms";
    private static String id = "";
    private static String name = "";
    ArrayList<Hotel_model> HotelJsonList;
    ArrayList<Room_model> RoomJSonModelList;
    ArrayList<Room_model> RoomList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist__room);
        toolBarPlaylist = (Toolbar) findViewById(R.id.toolBar_playlist);
        tvNameHotelPlaylist = (TextView) findViewById(R.id.tv_nameHotel_playlist);
        lvRoomPlaylist = (ListView) findViewById(R.id.lv_room_playlist);
        tvEmptyRoom = (TextView) findViewById(R.id.tv_empty_room);
        Intent intent = getIntent();
        tvNameHotelPlaylist.setText(intent.getStringExtra("name"));
        id = intent.getStringExtra("_id");
        name = intent.getStringExtra("name");
        RoomJSonModelList = new ArrayList<>();
        HotelJsonList = new ArrayList<>();
        RoomList = new ArrayList<>();
        new getDataJSON().execute();
        lvRoomPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailRoom_Activity.class);
                intent.putExtra("floor", RoomList.get(position).getFloor());
                intent.putExtra("singleroom", RoomList.get(position).getSingleroom());
                intent.putExtra("price", RoomList.get(position).getPrice());
                intent.putExtra("status", RoomList.get(position).getStatus());
                intent.putExtra("image", RoomList.get(position).getImage());
                intent.putExtra("detail", RoomList.get(position).getDetail());
                intent.putExtra("hotelid", RoomList.get(position).getHotelid());
                intent.putExtra("roomnumber", RoomList.get(position).getNumberroom());
                for (int i = 0; i <= HotelJsonList.size(); i++) {
                    if ((RoomList.get(position).getHotelid()).equalsIgnoreCase(HotelJsonList.get(i).getId())) {
                        intent.putExtra("hotelname", HotelJsonList.get(i).getName());
                        break;
                    }
                }
                startActivity(intent);
            }
        });
    }

    public class getDataJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Playlist_Room_Activity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonString1 = httpHandler.makeServiceCall(JsonurlRoom);
            Log.e(TAG, "Response from url: " + jsonString1);

            if (jsonString1 != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString1);
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
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
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Could not get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Could not get json from server", Toast.LENGTH_LONG).show();
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
            String idhotel = String.valueOf(id);
            for (int i = 0; i < RoomJSonModelList.size(); i++) {
                if (idhotel.equalsIgnoreCase(RoomJSonModelList.get(i).getHotelid())) {
                    Room_model roomModel = new Room_model(RoomJSonModelList.get(i).getNumberroom(), RoomJSonModelList.get(i).getImage(), RoomJSonModelList.get(i).getFloor(), RoomJSonModelList.get(i).getSingleroom(), RoomJSonModelList.get(i).getPrice(), RoomJSonModelList.get(i).getDetail(), RoomJSonModelList.get(i).getHotelid(), RoomJSonModelList.get(i).getStatus());
                    RoomList.add(roomModel);
                }
            }
            if (RoomList.size() <= 0) {
                tvEmptyRoom.setText("There is no room for this hotel");

            } else {
                Hotel_model hotelModel = new Hotel_model(name, "", "", "", "", "", "", id);
                HotelJsonList.add(hotelModel);
                Room_Adapter adapter = new Room_Adapter(getApplicationContext(), R.layout.item_room, RoomList, HotelJsonList, "hotel");
                lvRoomPlaylist.setAdapter(adapter);
            }
        }
    }

    public void back_activity(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
