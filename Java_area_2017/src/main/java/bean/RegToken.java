package bean;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegToken {

    public static boolean setToken(HttpSession session, String token) throws Exception
    {
        try
        {
            String test = "UPDATE members SET token_intra ='" +   token + "'" + " WHERE uname ='"+ session.getAttribute("name") + "';";
            System.out.println("test : "  + test);
            ResultSet rs;
            Connection con = ConnectionProvider.getCon();

            Statement st = con.createStatement();
            st.execute(test);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean setTokenFb(HttpSession session, String token) throws Exception
    {
        try
        {
            String test = "UPDATE members SET access_token_fb ='" +   token + "'" + " WHERE uname ='"+ session.getAttribute("name") + "';";
            System.out.println("test : "  + test);
            ResultSet rs;
            Connection con = ConnectionProvider.getCon();

            Statement st = con.createStatement();
            st.execute(test);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }


    public static String getToken(HttpSession session) throws Exception
    {
        String my_token ="";
        try
        {
            String test = "select * from members where token_session ='" +   session.getAttribute("token") + "'" + " AND uname ='"+ session.getAttribute("name") + "';";
            ResultSet rs;
            Connection con = ConnectionProvider.getCon();

            Statement st = con.createStatement();
            rs = st.executeQuery(test);

            while (rs.next())
                my_token = rs.getString("token_intra");
            if (my_token == null ||my_token.equals("403-auth")) {
                return ("");
            }
            else
                return (my_token);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return (my_token);
    }

    public static String getTokenFb(HttpSession session) throws Exception
    {
        String my_token ="";
        try
        {
            String test = "select * from members where token_session ='" +   session.getAttribute("token") + "'" + " AND uname ='"+ session.getAttribute("name") + "';";
            ResultSet rs;
            Connection con = ConnectionProvider.getCon();

            Statement st = con.createStatement();
            rs = st.executeQuery(test);

            while (rs.next())
                my_token = rs.getString("access_token_fb");
            System.out.println(my_token);
            return (my_token);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return (my_token);
    }
}
