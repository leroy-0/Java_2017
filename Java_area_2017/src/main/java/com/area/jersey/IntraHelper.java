package com.area.jersey;

import bean.RegToken;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class IntraHelper {


    public static String checkSession(HttpSession session, String url)
    {
        try {

            url = "https://intra.epitech.eu/" + RegToken.getToken(session) + "/?format=json";
        }
        catch (Exception e)
        {

        }
        return (url);
    }

    public static List<ProjectsObject> getInfoProject(String url, String Object, String Array) throws Exception
    {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        StringBuilder result = readPages(con);
        JSONObject JsonParse = new JSONObject(result.toString());
        List<ProjectsObject> list = new ArrayList<ProjectsObject>();
        JSONArray array =  JsonParse.getJSONObject(Object).getJSONArray(Array);


        for(int i = 0 ; i < array.length() ; i++){
            ProjectsObject tmp = new ProjectsObject();
            tmp.title = array.getJSONObject(i).getString("title");
            tmp.timeline_start = array.getJSONObject(i).getString("timeline_start");
            tmp.timeline_end = array.getJSONObject(i).getString("timeline_end");
            list.add(tmp);
        }

        return list;
    }

    public static StringBuilder readPages(HttpsURLConnection con) throws Exception
    {
        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return (result);
    }

    public static boolean readResponseServer(OutputStreamWriter writer, URLConnection conn) throws  Exception
    {
        StringBuffer answer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            answer.append(line);
        }
        writer.close();
        reader.close();
        System.out.println(answer.toString());
        if (answer.toString().substring(0, "{    \"login\":".length()).equals("{    \"login\":"))
            return (true);
        else
            return (false);
    }

    public static String GetEmailOfUser(String token) throws Exception {
        try {

            String url = "https://intra.epitech.eu/" + token + "/user/?format=json";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            StringBuilder result = readPages(con);
            JSONObject JsonParse = new JSONObject(result.toString());
            return (JsonParse.getString("internal_email"));
        }
        catch (Exception E) {
            token = null;
            return null;
        }
    }
}
