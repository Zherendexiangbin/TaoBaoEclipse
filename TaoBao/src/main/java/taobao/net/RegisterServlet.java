package taobao.net;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import entity.Commodity;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("请求："+action);
		
        
        if (action != null && action.equals("login")) {
            handleLogin(request, response);
        } else if (action != null && action.equals("register")) {
            handleRegister(request, response);
        } else {
            // 处理其他非法请求
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取登录表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        // 在此处进行登录逻辑处理
     // 连接数据库并执行查询
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int isValidUser = 0;

        try {
            // 建立数据库连接
            conn = getConnection();
            
            // 准备SQL语句
            String sql = "SELECT * FROM user WHERE username =? AND password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // 执行查询
            rs = stmt.executeQuery();

            // 如果有结果，则表示用户名和密码匹配
//            if (rs.next()) {
//                isValidUser = true;
//            }
            while(rs.next()) {
            	String name = rs.getString(1);
            	String pwd = rs.getString(2);
            	if(name.equals(username)&&pwd.equals(password)) {
            		isValidUser = 1;//登陆成功，置为1
            	}else if(!(name.equals(username))||!(pwd.equals(password))){
            		isValidUser = 2;//用户名或密码错误
            	}
            }

            // 关闭资源
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 根据验证结果，发送响应给客户端
        if (isValidUser == 1) {
//            response.getWriter().write("Login successful");
            List<Commodity> productList = getProductsFromDatabase();
            Gson gson = new Gson();
            String productJsonString = gson.toJson(productList);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(productJsonString);
            out.flush();
        } else {
            response.getWriter().write("Invalid username or password");
        }
    }

    //从数据库中获取商品信息的函数：
    private List<Commodity> getProductsFromDatabase() {
    	List<Commodity> products = new ArrayList<>();

    	 try {
 			Class.forName("com.mysql.jdbc.Driver");
 		} catch (ClassNotFoundException e) {
 			e.printStackTrace();
 		}
    	String url = "jdbc:mysql://localhost:3306/taobao"; // 替换为你的数据库连接信息
        String username = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM product";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String id = resultSet.getString("image");
                String name = resultSet.getString("pro_name");
                String about = resultSet.getString("about");
                String price = resultSet.getString("price");

                Commodity product = new Commodity(id,name,about,price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
	}
    

	private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取注册表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
     // 连接数据库并执行插入
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 建立数据库连接
            conn = getConnection();
            
            // 检查用户是否已存在
            boolean isExistingUser = checkExistingUser(conn, username);

            // 如果用户不存在，则将用户信息插入到数据库中
            if (!isExistingUser) {
                // 准备SQL语句
                String sql = "INSERT INTO user(username, password) VALUES(?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                // 执行插入
                int rowsAffected = stmt.executeUpdate();

                // 根据插入结果发送响应给客户端
                if (rowsAffected > 0) {
                    response.getWriter().write("Registration successful");
                } else {
                    response.getWriter().write("Registration failed");
                }
            } else {
                response.getWriter().write("Username already exists");
            }

            // 关闭资源
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
 // 建立数据库连接
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/taobao"; // 替换为你的数据库连接信息
        String username = "root";
        String password = "";

        return DriverManager.getConnection(url, username, password);
    }
    
 // 检查用户是否已存在
    private boolean checkExistingUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 准备SQL语句
            String sql = "SELECT * FROM user WHERE username=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // 执行查询
            rs = stmt.executeQuery();

            // 如果有结果，则表示用户已存在
            if (rs.next()) {
                return true;
            }
        } finally {
            // 关闭资源
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return false;
    }

}
