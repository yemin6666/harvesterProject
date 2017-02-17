package harvester.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public abstract class HttpSimpleProcessing extends HttpServlet{

	private static final long serialVersionUID = -3789410625647097032L;
	private static Logger log = Logger.getLogger(HttpSimpleProcessing.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String result = processing(req, resp);
			if (result != null) {
				resp.setContentType("text/html; charset=utf-8");
				PrintWriter out = resp.getWriter();
				out.print(result);
			}
		} catch (Exception e) {
			log.error(e);
		}
		
	}
	
	protected abstract String processing(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
