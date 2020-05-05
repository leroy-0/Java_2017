package bean;




import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.sql.*;
public class LoginDao {

	public static boolean validate(LoginBean bean, HttpServletResponse response, HttpSession session) {
		boolean status = false;
		try {
			Connection con = ConnectionProvider.getCon();

			Statement st = con.createStatement();
			System.out.println("select * from members where uname='" + bean.getUname() + "' and pass='" + bean.getPass() + "';");
			ResultSet rs;
			rs = st.executeQuery("select * from members where uname='" + bean.getUname() + "' and pass='" + bean.getPass() + "';");
			status = rs.next();
			if (status) {
				SecureRandom random = new SecureRandom();
				byte bytes[] = new byte[20];
				random.nextBytes(bytes);
				String token = bytes.toString();
				session.setAttribute("session", "TRUE");
				session.setAttribute("name", bean.getUname());
				session.setAttribute("token", token);
				setToken(session);
				response.sendRedirect("success.jsp");
			} else {
				response.sendRedirect("failed.jsp");
			}
		} catch (Exception e) {
		}
		return status;
	}
	public static boolean setToken(HttpSession session) throws Exception
	{
		try
		{
			String test = "UPDATE members SET token_session ='" +  session.getAttribute("token") + "'" + " WHERE uname ='"+ session.getAttribute("name") + "';";
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

}
