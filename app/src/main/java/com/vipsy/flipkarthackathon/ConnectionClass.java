package com.vipsy.flipkarthackathon;

/**
 * Created by saiyaswanth on 13-03-2016.
 */
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionClass
{

    static String send(String url)
    {
        Log.d("send method ","reached");

        String responce = null;
        try {
            Log.d("ConnectionClass","reached");
            URL urlString = new URL(url);
            Log.e("connection class", "18");
            HttpURLConnection connection = (HttpURLConnection) urlString
                    .openConnection();
            Log.e("connection class", "22");
            InputStream stream = connection.getInputStream();
            Log.e("connection class", "25");
            int i = 0;
            StringBuffer buffer = new StringBuffer("");
            while ((i = stream.read()) != -1)
            {
                buffer.append((char) i);
            }

            responce = buffer.toString();
        }
        catch(Exception e)
        {
            Log.e("connection class", "40");
            System.out.println(e);
        }
        return responce;
    }
}

