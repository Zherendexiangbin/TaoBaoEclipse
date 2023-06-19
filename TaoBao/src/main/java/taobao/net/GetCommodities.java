package taobao.net;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ResponseWriter;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import dao.CommodityDao;
import entity.Commodity;

/**
 * Servlet implementation class GetCommodities
 */
@WebServlet("/getCommodities")
public class GetCommodities extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommodities() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Commodity> commodities = CommodityDao.getAll();
		Gson gson = new Gson();
		ResponseWriter.write(response, gson.toJson(commodities));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
