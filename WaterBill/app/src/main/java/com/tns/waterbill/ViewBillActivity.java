package com.tns.waterbill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class ViewBillActivity extends AppCompatActivity {

    private String sessionId, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);


        //getting sessionId to communicate with REST server
        Bundle extras = getIntent().getExtras();
        sessionId = extras.getString("sessionId");
        username = extras.getString("username");

        Log.i( "*****", "clicked");
        HttpClient client = new DefaultHttpClient();
        Log.i( "*****", "httpclient");
        try {
            //Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
            String s = "http://192.168.43.133:8080/RESTfullWebService/rest/computerVision/usage/" + sessionId + "/" + username;
            URI website = new URI( s );

            Log.i( "*****", s);
            HttpGet request = new HttpGet();
            Log.i( "*****", "http get");
            request.setURI( website );
            Log.i( "*****", "set uri");
            HttpResponse response = client.execute( request );
            Log.i( "*****", "http execute");
            InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
            BufferedReader br = new BufferedReader( isr );
            String usage = br.readLine();
            Log.i( "*****", "read usage");
            Log.i( "usage", usage);

            Toast.makeText(getApplicationContext(), usage, Toast.LENGTH_SHORT).show();

            //visualizing
            TextView username = (TextView)findViewById( R.id.textView6 );
            username.setText( sessionId );
            TextView usageT = (TextView)findViewById( R.id.textView7 );
            usageT.setText( usage );
            TextView lastUpdate = (TextView)findViewById( R.id.textView8 );
            lastUpdate.setText( usage );
            TextView amountT = (TextView)findViewById( R.id.textView9 );
            amountT.setText( usage );


        } catch (URISyntaxException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }
}
