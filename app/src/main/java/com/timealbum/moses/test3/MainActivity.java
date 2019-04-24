package com.timealbum.moses.test3;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.timealbum.moses.test3.Adapter.RecyclerViewAdapter;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.alterac.blurkit.BlurLayout;




public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    BlurLayout blurLayout;
    SwipeToLoadLayout swipeToLoadLayout;

    ArrayAdapter<String> mAdapter;

    //test recycleView
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);

        ListView listView = (ListView) findViewById(R.id.swipe_target);

        swipeToLoadLayout.setOnRefreshListener((OnRefreshListener) this);

        swipeToLoadLayout.setOnLoadMoreListener(this);

        mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1);

        listView.setAdapter(mAdapter);

        autoRefresh();
        */

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        /*
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        */
// 設定右上角的 menu
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
// Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitle("hello");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);


        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab_add_task);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //naviartion view

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        navigationView = findViewById(R.id.navigation_view);


        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        CircleImageView drawerHeaderImage = (CircleImageView) headerView.findViewById(R.id.circularImageId);
        TextView user = (TextView) headerView.findViewById(R.id.loginTextId);
        user.setText("asdkfalsdf");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "home clicked", Toast.LENGTH_SHORT).show();
                        break;
                }


                return false;
            }
        });


        //set ercyclerView
        List<ItemObject> rowListItem = getAllItemList();
        final LinearLayoutManager lLayout = new LinearLayoutManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(lLayout);
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(this,rowListItem);
        mRecyclerView.setAdapter(rcAdapter);


        //getUID();
        Log.d(TAG, "onCreate: " + getDateFromString("2017-10-15T09:27:37Z"));
    }

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public Date getDateFromString(String datetoSaved){
        try {
            Date date = format.parse(datetoSaved);
            return date ;
        } catch (ParseException e){
            return null ;
        }
    }

    public void btn_newActivity(View view) {


        Intent intent = new Intent(MainActivity.this, newActivity.class);

        startActivity(intent);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startjob_click(View view){
        /*
        Log.d(TAG, "startjob_clicked");
        ComponentName componentName = new ComponentName(this,activity_job.class);
        JobInfo jobInfo = new JobInfo.Builder(123,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                //.setPersisted(true)
                //.setPeriodic(15 * 60 * 1000)
                .addTriggerContentUri(new JobInfo.TriggerContentUri(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultcode = jobScheduler.schedule(jobInfo);
        if(resultcode == jobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "startjob_click: job scheduled");
        }else{
            Log.d(TAG, "job scheduling failed");
        }
        */
        PhotosContentJob.scheduleJob(MainActivity.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void stopjob_click(View view){
        //JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        //jobScheduler.cancel(123);
        PhotosContentJob.cancelJob(MainActivity.this);
        Log.d(TAG, "stopjob_click: job scheduler stop!!!!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);



    }
    // Navigation bar item select action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "click " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case android.R.id.home:
                //drawerLayout.openDrawer(GravityCompat.START);
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_job:
                Toast.makeText(this,"job click",Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getUID(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public static class JobIds {
        public static final int PHOTOS_CONTENT_JOB = 125;
        public static final int NET_CONNECTIVITY_JOB = 126;
    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("Peter James", "Vildansvagen 19, Lund Sweden", R.drawable.face));
        allItems.add(new ItemObject("Henry Jacobs", "3 Villa Crescent London, UK", R.drawable.face));
        allItems.add(new ItemObject("Bola Olumide", "Victoria Island Lagos, Nigeria", R.drawable.face));
        allItems.add(new ItemObject("Chidi Johnson", "New Heaven Enugu, Nigeria", R.drawable.face));
        allItems.add(new ItemObject("DeGordio Puritio", "Italion Gata, Padova, Italy", R.drawable.face));
        allItems.add(new ItemObject("Gary Cook", "San Fransisco, United States", R.drawable.face));
        allItems.add(new ItemObject("Edith Helen", "Queens Crescent, New Zealand", R.drawable.face));
        allItems.add(new ItemObject("Kingston Dude", "Ivory Lane, Abuja, Nigeria", R.drawable.face));
        allItems.add(new ItemObject("Edwin Bent", "Johnson Road, Port Harcourt, Nigeria", R.drawable.face));
        allItems.add(new ItemObject("Grace Praise", "Federal Quarters, Abuja Nigeria", R.drawable.face));

        return allItems;
    }


}
