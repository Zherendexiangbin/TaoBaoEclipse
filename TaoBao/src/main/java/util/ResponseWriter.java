package util;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseWriter {
	public static void write(HttpServletResponse response, String responseBody) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.write(responseBody);
		writer.flush();
		writer.close();
	}
}
