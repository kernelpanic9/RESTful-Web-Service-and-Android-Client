package com.tns.waterbill;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("*******", "Inserting");
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("water_bill", android.content.Context.MODE_PRIVATE, null);
        Log.i("*******", "Inserting");
        db.execSQL("create table if not exists auth( username varchar(15), sessionid varchar(40) )");
        Log.i("*******", "creating tables");


        try {
            //*********************************************************************//
            //clears the database: used to test
            //db.execSQL("delete from auth"); // where username='app-user'");
            //*********************************************************************//

            //db.execSQL("insert into auth values('test', 'restsesttetstttte')");
            //Log.i("*******", "Inserted");

            Cursor c = db.rawQuery("select * from auth", null);
            //Log.i("*******", "+++++++");
            c.moveToFirst();

            //Log.i("*******", String.valueOf(c.isClosed()));
            if( c.getCount() < 1 ){
                Log.i("*******", "no session in app");
                Intent intent = new Intent( getBaseContext(), LoginActivity.class );
                startActivity( intent );
            }
            else {
                String sessionId = c.getString(c.getColumnIndex("sessionid"));
                String username = c.getString( c.getColumnIndex("username") );
                Log.i("*******", sessionId);


                if (sessionId.contains("restses")) {
                    Intent intent = new Intent(getBaseContext(), NavigationActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("sessionId", sessionId);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        }
        catch( Exception e ){
            Log.i("****", e.getMessage());
        }


        /*
        MyDatabaseHelper dbh = new MyDatabaseHelper( this );
        dbh.setSessionId("123456789");
        String sessionId = dbh.getSessionId();
        if( sessionId.equals("exception")){
            Log.i("**********", "MainA: ");
        }
        else if( sessionId.equals("no-cursor") ){
            Intent intent = new Intent( getBaseContext(), LoginActivity.class );
            startActivity( intent );
        }
        else{
            Intent intent = new Intent( getBaseContext(), NavigationActivity.class );
            intent.putExtra("sessionId", sessionId);
            startActivity( intent );
        }
        */

    }
}
