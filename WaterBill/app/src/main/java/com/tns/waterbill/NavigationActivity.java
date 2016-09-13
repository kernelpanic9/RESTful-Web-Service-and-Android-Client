package com.tns.waterbill;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class NavigationActivity extends AppCompatActivity {


    private Button checkUsage;
    private Button updateUsage;
    private String sessionId, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //getting sessionId to communicate with REST server
        Bundle extras = getIntent().getExtras();
        sessionId = extras.getString("sessionId");
        username = extras.getString("username");
        Log.i("yyyyyyy", username);


        //checking the usage function
        checkUsage = (Button)findViewById( R.id.button );
        checkUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewBillActivity.class);
                intent.putExtra( "sessionId", sessionId );
                intent.putExtra("username", username);
                startActivity( intent );
            }
        });




        //update usage function
        updateUsage = (Button)findViewById(R.id.button2);
        updateUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UpdateUsageActivity.class);
                intent.putExtra( "sessionId", sessionId );
                intent.putExtra("username", username);
                startActivity( intent );
            }
        });






    }
}
