package bean;
import facebook4j.internal.org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
public class RegistrationDao {


    static String getAccessToken(String code)
    {
        String line, outputString = "";
        String accessToken = null;
        try {

            URL url = new URL("https://graph.facebook.com/oauth/access_token?client_id="
                    + FbLoginProvider.appId + "&redirect_uri=" + FbLoginProvider.redirect_url
                    + "&client_secret=" + FbLoginProvider.appSecret
                    + "&code=" + code + "&scope=email,public_profile");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            JSONObject root = new JSONObject(outputString);
            accessToken = root.getString("access_token");
            System.out.println(accessToken);
        }
        catch (Exception e)
        {

        }
        return (accessToken);
    }

    public static void    setRegFb(JSONObject fbp, HttpServletResponse response, HttpSession session, String access_token)
    {
        try {

            RegisterBean user = new RegisterBean();

            user.setPass(fbp.getString("id"));
            String info[] = fbp.getString("name").split(" ");
            user.setUname(info[0]);
            user.setFname(info[1]);
            user.setLname(info[0]);
            user.setEmail(fbp.getString("email"));
            RegistrationDao.registerFb(user, response, session, access_token);
        }
        catch (Exception e)
        {

        }
    }
    public static boolean getinfoFb(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        try {
            String code = request.getParameter("code");
            String accessToken = getAccessToken(code);
            URL  url = new URL("https://graph.facebook.com/me?access_token=" + accessToken + "&fields=email,id,name,birthday,gender");
            System.out.println("URL :" + url);
            URLConnection conn1 = url.openConnection();
            String outputString = "";
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }
            reader.close();
            System.out.println(outputString);
            JSONObject fbp = new JSONObject(outputString);
            setRegFb(fbp, response, session, accessToken);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return (true);
    }

    public static boolean register(RegisterBean bean, HttpServletResponse response, HttpSession session){
        boolean state = false;
        try{
            Connection con=ConnectionProvider.getCon();
            int status;
            Statement st = con.createStatement();
            System.out.println(bean.getUname());
            if (st.executeQuery("select * from members where email='" + bean.getEmail() + "';").next() ||st.executeQuery("select * from members where uname='" + bean.getUname() + "';").next())
            {
                System.out.println("failed registration");
                return false;
            }
            status = st.executeUpdate("insert into members(first_name, last_name, email, uname, pass, regdate) values ('" + bean.getFname()+ "','" + bean.getLname()+ "','" + bean.getEmail() + "','" + bean.getUname() + "','" + bean.getPass() + "', CURDATE())");
            if (status > 0)
            {
                session.invalidate();
                response.sendRedirect("welcome.jsp");
            }
            else {
                response.sendRedirect("registrationFailed.jsp");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  state;
    }

    public static boolean registerFb(RegisterBean bean, HttpServletResponse response, HttpSession session, String access_token){
        boolean state = false;
        try{
            Connection con=ConnectionProvider.getCon();
            int status;
            Statement st = con.createStatement();
            System.out.println(bean.getUname());
            if (st.executeQuery("select * from members where email='" + bean.getEmail() + "';").next() ||st.executeQuery("select * from members where uname='" + bean.getUname() + "';").next())
            {
                LoginBean user = new LoginBean();

                user.setUname(bean.getUname());
                user.setPass(bean.getPass());
                LoginDao.validate(user, response, session);
                RegToken.setTokenFb(session, access_token);
                return (true);
            }
            status = st.executeUpdate("insert into members(first_name, last_name, email, uname, pass, regdate, access_token_fb) values ('" + bean.getFname()+ "','" + bean.getLname()+ "','" + bean.getEmail() + "','" + bean.getUname() + "','" + bean.getPass() + "', CURDATE()" + ",'" + access_token + "')");
            if (status > 0)
            {
                LoginBean user = new LoginBean();

                user.setUname(bean.getUname());
                user.setPass(bean.getPass());
                LoginDao.validate(user, response, session);
                RegToken.setTokenFb(session, access_token);
                return (true);
            }
            else {
                response.sendRedirect("registrationFailed.jsp");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  (true);
    }
}
