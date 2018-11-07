import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Desconectar")
public class Desconectar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Desconectar() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		String usuario = (String) request.getParameter("usuario_welcome");
		String profesion = (String) request.getParameter("profesion");
		String contador_visitas = (String) request.getParameter("contador_welcome");
		Cookie[] listaCookies = request.getCookies();
		if(listaCookies != null) {
			for (int i=0; i< listaCookies.length; i++) {
				String nombre_cookie = listaCookies[i].getName();
				if(!nombre_cookie.equals(usuario)) {
					String value = contador_visitas + "#" + profesion;
					Cookie cookie = new Cookie(usuario, value);
					cookie.setPath("/");
					response.addCookie(cookie);
				} else {
					Cookie cookie = listaCookies[i];
					String nombre = cookie.getName();
					String value = contador_visitas + "#" + profesion;
					cookie.setValue(value);
					cookie.setPath("/");
					response.addCookie(cookie);
					String valor = cookie.getValue();
					out.println("Nombre de la cookie: " + nombre + "<br>Valor de la cookie: " + valor);
				}
			}
		} else {
			String value = contador_visitas + "#" + profesion;
			Cookie cookie = new Cookie(usuario, value);
			cookie.setPath("/");
			response.addCookie(cookie);
			out.println("No hay cookies con ese nombre pero he guardado una.");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/log-in.jsp");
		dispatcher.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
