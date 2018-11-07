import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PracticaCookie")
public class PracticaCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Map<String,String> listaUsuarios = new LinkedHashMap<String,String>() {
		private static final long serialVersionUID = 1L;
	{
    	put("alfa", "bravo");
    	put("charlie", "delta");
    }};
    
    Map<String,String> listaProfesiones = new LinkedHashMap<String,String>() {
		private static final long serialVersionUID = 1L;
	{
    	put("alfarero", "Alfarero");
    	put("brujo", "Brujo");
    	put("curtidor", "Curtidor");
    }};

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listaProfesiones", listaProfesiones);
		String pagina = "/log-in.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagina);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		String usuario = request.getParameter("usuario");
		String clave = request.getParameter("clave");
		
	   if(listaUsuarios.containsKey(usuario) && listaUsuarios.get(usuario).equals(clave)) {
			   Cookie[] listaCookies = request.getCookies();
			   String contador_visitas = "1";
			   
			   if(listaCookies != null) {
				   for (int i=0; i < listaCookies.length; i++) {
					   Cookie cookie = listaCookies[i];
					   String cookieId = cookie.getName();
					   
					   if(cookieId.equals(usuario)) {
						   String value = cookie.getValue();
						   String[] values = value.split("#");
						   contador_visitas = "" + (Integer.parseInt(values[0]) + 1);
						   request.setAttribute("contador_visitas", contador_visitas);
					   } else {
						   request.setAttribute("profesion", "");
						   request.setAttribute("contador_visitas", contador_visitas);
					   }
				   }
				   request.setAttribute("listaProfesiones", listaProfesiones);
				   String pagina = "/welcome.jsp";
				   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagina);
				   dispatcher.include(request, response);
			   } else {
				   request.setAttribute("contador_visitas", contador_visitas);
				   request.setAttribute("profesion", "");
				   request.setAttribute("listaProfesiones", listaProfesiones);
				   String pagina = "/welcome.jsp";
				   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagina);
				   dispatcher.include(request, response);
			   }
	   } else {
		   out.println("<div style=\"color: red;\">ERROR, USUARIO Y/O CONTRASEÑA INCORRECTOS</div>");
		   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/log-in.jsp");
		   dispatcher.include(request, response);
	   }
	}
}
