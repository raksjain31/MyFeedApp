package developer.rakshitjain.myfeedpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog pd;
    ArrayList<FeedList> feedlist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       //config for RV
        recyclerView = findViewById(R.id.recycler_view);


        new JsonTask().execute("http://www.mocky.io/v2/59b3f0b0100000e30b236b7e");


        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
    //    recyclerView.setAdapter(adapter);



    }
    //inflate Sorting Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return true;

    }

//Sorting


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.Sort_Date:
                Collections.sort(feedlist, new Comparator<FeedList>() {
                    @Override
                    public int compare(FeedList feedList, FeedList t1) {
                        int comparison=0;
                        comparison = feedList.getEvent_date().compareTo(t1.getEvent_date());
                        return comparison;
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case R.id.Sort_Likes:
                    Collections.sort(feedlist);
                    adapter.notifyDataSetChanged();
                break;
            case R.id.Sort_Views:
                Collections.sort(feedlist, new Comparator<FeedList>() {
                    @Override
                    public int compare(FeedList feedList, FeedList t1) {
                        int comparison=0;
                        comparison = feedList.getViews().compareTo(t1.getViews());
                        return comparison;
                    }
                });
                adapter.notifyDataSetChanged();

                break;
            case R.id.Sort_Share:
                Collections.sort(feedlist, new Comparator<FeedList>() {
                    @Override

                    public int compare(FeedList feedList, FeedList t1) {
                        int comparison=0;
                        comparison = feedList.getShares().compareTo(t1.getShares());
                        return comparison;
                    }
                });
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadUrlData() {
     final ProgressDialog progressDialog = new ProgressDialog(this);
     progressDialog.setMessage("Loading...");
     progressDialog.show();


    }




    private class JsonTask extends AsyncTask<String, String, String> {

        String response;
        static final String URL_STRING = "http://www.mocky.io/v2/59b3f0b0100000e30b236b7e";

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait !!");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            String response = "";
            HttpURLConnection conn;
            StringBuilder jsonResults = new StringBuilder();
            try {
                //setting URL to connect with
                URL url = new URL(URL_STRING);
                //creating connection
                conn = (HttpURLConnection) url.openConnection();
            /*
            converting response into String
            */
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                response = jsonResults.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }



        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(MainActivity.this,
                    "Connection successful.",Toast.LENGTH_SHORT).show();
            try{
                if(result!=null && !result.equals("")){
                    /*
                    converting JSON response string into JSONArray
                    */

                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray responseArray = jsonObj.getJSONArray("posts");
                    if(responseArray.length()>0){
                        /*
                        Iterating JSON object from JSON Array one by one
                        */
                        for(int i=0;i<responseArray.length();i++){
                            JSONObject feedobject = responseArray.getJSONObject(i);
                            //creating object of model class(FeedList)
                            FeedList list = new FeedList();
                            /*
                            fetching data based on key from JSON and setting into model class
                            */
                            list.setId(feedobject.optString("id"));
                            list.setThumbnail_image(feedobject.optString("thumbnail_image"));
                            list.setEvent_name(feedobject.optString("event_name"));
                            list.setEvent_date(feedobject.optString("event_date"));
                            list.setViews(feedobject.optString("views"));
                            list.setLikes(feedobject.optString("likes"));
                            list.setShares(feedobject.optString("shares"));

                            //adding data into List
                            feedlist.add(list);

                        }

                        //calling RecyclerViewAdapter constructor by passing context and list
                        adapter = new MyFeedAdapter(MainActivity.this,feedlist);

                        //setting adapter on recyclerView
                        recyclerView.setAdapter(adapter);

                        // to notify adapter about changes in list data(if changes)
                        adapter.notifyDataSetChanged();
                        pd.dismiss();
                    }
                }else {
                    Toast.makeText(MainActivity.this,
                            "Error in fetching data.",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }


        }
    }

