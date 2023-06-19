package taobao.net;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserService;
import util.ResponseWriter;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestBody = (String) request.getAttribute("requsetBody");
		Gson gson = new Gson();
		User user = gson.fromJson(requestBody, User.class);
		
		JsonObject reponseJson = new JsonObject();
		if (UserService.CheckLogin(user)) {
			reponseJson.addProperty("state", "success");
		} else {
			reponseJson.addProperty("state", "fail");
		}
		
		ResponseWriter.write(response, reponseJson.toString());
	}
}
