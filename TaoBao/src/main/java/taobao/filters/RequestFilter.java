package taobao.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet Filter implementation class RequestFilter
 */

@WebFilter("*")
public class RequestFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 2150412083482114387L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public RequestFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		BufferedReader reader = request.getReader();
		StringBuilder req = new StringBuilder();
		String line = null;
		
		while ((line = reader.readLine()) != null) {
			req.append(line);
		}
		
		request.setAttribute("requestBody", req.toString());
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
