package com.area.jersey;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FacebookHelper {

    public String setUserTokenCallback(String appId)
    {
        try {
            URL myURL = new URL("https://www.facebook.com/v2.10/dialog/oauth?" +
            "client_id=" + appId +
                    "&display=popup" +
                    "&response_type=token" +
                    "&redirect_uri=http://localhost:8080/area/fb/tokeniser");
            HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
            myURLConnection.setRequestMethod("GET");
            myURLConnection.connect();
            InputStream in = myURL.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            System.out.println("Value" + myURLConnection.getResponseCode());
            System.out.println(myURLConnection.getResponseMessage());
            System.out.println(result.toString());
            return result.toString();
        }
        catch (MalformedURLException e) {
            // new URL() failed
            // ...
        }
        catch (IOException e) {
            // openConnection() failed
            // ...
        }

        return null;
    }

    public String getTokenWithCode()
    {
        return "";
    }
}
