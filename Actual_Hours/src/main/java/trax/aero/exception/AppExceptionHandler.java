package trax.aero.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppExceptionHandler extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Analyze the servlet exception
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (servletName == null) {
            servletName = "Unknown";
        }

        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

        if (requestUri == null) {
            requestUri = "Unknown";
        }

        StringWriter errors = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errors));

        // Set response content type
        response.setContentType("application/json");

        StringBuilder sb = new StringBuilder();
        sb.append("\n{");
        sb.append("\n\"exceptionName\": ").append("\"" + throwable.getClass().getName() + "\"");
        sb.append("\n\"errorCode\": ").append("\"" + statusCode + "\"");
        sb.append("\n\"errorDescription\": ").append(errors.toString());
        sb.append("\n\"requestedURI\": ").append("\"" + requestUri + "\"");
        sb.append("\n}");

        PrintWriter out = response.getWriter();

        out.write(sb.toString());
    }

}
