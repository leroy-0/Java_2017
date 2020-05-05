package com.area.jersey;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import bean.RegToken;
import org.json.*;

@Path("/IntraNoAuth")
public class IntraServiceNoAuth {

    @Path("/notification")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NotifcationObject> getNotification(@Context HttpServletRequest req, @HeaderParam("token") String token) throws Exception
    {
        try {
            StringBuilder result;
            String url = "";
            url = IntraHelper.checkSession(req.getSession(), url);
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            result = IntraHelper.readPages(con);
            JSONObject JsonParse = new JSONObject(result.toString());
            JSONArray array = JsonParse.getJSONArray("history");
            List<NotifcationObject> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                NotifcationObject tmp = new NotifcationObject();
                tmp.Title = array.getJSONObject(i).getString("title");
                list.add(tmp);
            }
            return (list);
        }
        catch (Exception E)
        {
            List<NotifcationObject> list = new ArrayList<>();
            NotifcationObject tmp = new NotifcationObject();
            tmp.Title = "Error";
            list.add(tmp);
            return (list);
        }
    }

    @Path("/failed")
    @GET
    public String    failed() throws Exception
    {
        return ("Authentication failed<br> <form action=\"/\">\n" +
                "    <input class='button' type=\"submit\" value=\"Go to Homepage\" />\n" +
                "</form>");
    }


    @Path("/sucess")
    @GET
    public String    sucess() throws Exception
    {
        return ("Authentication granted<br> <form action=\"/\">\n" +
                "    <input class='button' type=\"submit\" value=\"Go to Homepage\" />\n" +
                "</form>");
    }

    @Path("/login")
    @GET
    public void login(@Context HttpServletRequest req, @Context HttpServletResponse response, @QueryParam("token") String token) throws Exception
    {
        try {
            RegToken.setToken(req.getSession(), token);
            System.out.println(token);
            System.out.println("name = " + req.getSession().getAttribute("name"));
            StringBuilder result;
            String url = "https://intra.epitech.eu/" + RegToken.getToken(req.getSession()) + "/?format=json";
            URL obj = new URL(url);
            System.out.println("url = " + url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            result = IntraHelper.readPages(con);
           response.sendRedirect("http://127.0.0.1:8080/area/IntraNoAuth/sucess");
        }
        catch (Exception E)
        {
            RegToken.setToken(req.getSession(), "403-auth");
            response.sendRedirect("http://127.0.0.1:8080/area/IntraNoAuth/failed");
        }
    }
    @Path("/projects")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectsObject> getProjects(@Context HttpServletRequest req, @HeaderParam("token") String token) throws Exception
    {
        try {
            String url = "";
            url = IntraHelper.checkSession(req.getSession(), url);
            return (IntraHelper.getInfoProject(url, "board", "projets"));
        }
        catch (Exception E)
        {
            List<ProjectsObject> list = new ArrayList<ProjectsObject>();
            ProjectsObject tmp = new ProjectsObject();
            tmp.title = "Error";
            list.add(tmp);
            return (list);
        }
    }


    @Path("/inscription")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.TEXT_PLAIN})
    public String inscriptionToModule(@Context HttpServletRequest req, @HeaderParam("token") String token,  MyObject info) throws  Exception {

        try {
            URL myUrl;

                myUrl = new URL("https://intra.epitech.eu/" + RegToken.getToken(req.getSession()) + "/" + info.module + "/" + info.year + "/"
                        + info.codeModule + "/" + info.codeInstance + "/register/?format=json");
            URLConnection conn = myUrl.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            JSONObject obj = new JSONObject();
            String login = IntraHelper.GetEmailOfUser(token);
            obj.put("login", login);

            writer.write(obj.toString());
            writer.flush();
            if (IntraHelper.readResponseServer(writer, conn))
                return ("You have Been register to " + info.module + "/" + info.year + "/" + info.codeModule + "/" + info.codeInstance);
            else
                return ("You havent been register to " + info.module + "/" + info.year + "/" + info.codeModule + "/" + info.codeInstance);
        }
        catch (Exception E)
        {
            return ("Error with the request");
        }
    }


    @Path("/module")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<InscriptionObject>  getModules(@Context HttpServletRequest req, @HeaderParam("token") String token) throws Exception
    {
        try {
            String url = "";
            StringBuilder result;
           url = IntraHelper.checkSession(req.getSession(), url);
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            result = IntraHelper.readPages(con);
            JSONObject JsonParse = new JSONObject(result.toString());
            List<InscriptionObject> list = new ArrayList<InscriptionObject>();
            JSONArray array = JsonParse.getJSONObject("board").getJSONArray("modules");
            for (int i = 0; i < array.length(); i++) {
                InscriptionObject tmp = new InscriptionObject();
                tmp.title = array.getJSONObject(i).getString("title");
                tmp.timeline_start = array.getJSONObject(i).getString("timeline_start");
                tmp.timeline_end = array.getJSONObject(i).getString("timeline_end");
                String[] test = array.getJSONObject(i).getString("title_link").split("/");
                MyObject tmpM = new MyObject();
                tmpM.module = test[1];
                tmpM.year = test[2];
                tmpM.codeModule = test[3];
                tmpM.codeInstance = test[4];
                tmp.url = tmpM;
                list.add(tmp);
            }
            return list;
        }
        catch (Exception E)
        {
            List<InscriptionObject> tmp = new ArrayList<>();
            InscriptionObject tmp1 = new InscriptionObject();
            tmp1.title = "Error";
            tmp.add(tmp1);
            return (tmp);
        }
    }
}
