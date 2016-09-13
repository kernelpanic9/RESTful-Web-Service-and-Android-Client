package com.tns.waterbill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class UpdateUsageActivity extends AppCompatActivity {

    private Button updateButton;
    private String sessionId, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_usage);

        //getting sessionId to communicate with REST server
        Bundle extras = getIntent().getExtras();
        sessionId = extras.getString("sessionId");
        username = extras.getString("username");
        Log.i("******", "got session id");

        Log.i("******", "getting value");
        final EditText text = (EditText)findViewById(R.id.editText);
        //final

        updateButton = (Button)findViewById( R.id.button );
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = text.getText().toString();
                Log.i("******", value);
                if( value != null && !value.equals("") ){
                    double usage = Double.parseDouble(value);
                    HttpClient client = new DefaultHttpClient();
                    try {
                        URI uri = new URI("http://192.168.43.133:8080/RESTfullWebService/rest/computerVision/usage/" + sessionId + "/" + username + "/" + usage);
                        HttpPut request = new HttpPut( );
                        request.setURI( uri );
                        HttpResponse response = client.execute( request );

                        InputStreamReader isr = new InputStreamReader( response.getEntity().getContent() );
                        BufferedReader br = new BufferedReader( isr );

                        String result = br.readLine();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();


                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }
        });
    }
}
