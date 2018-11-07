<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.LinkedHashMap, java.util.Iterator"
     session="false" %>
    <%
    String usuario = (String) request.getParameter("usuario");
	String profesion = (String) request.getAttribute("profesion");
	String contador = (String) request.getAttribute("contador_visitas");
	
    Cookie[] listaCookies = request.getCookies();
    
    if(listaCookies != null) {
    	for(int i = 0; i < listaCookies.length; i++) {
        	if(listaCookies[i].getName().equals(usuario)) {
        		String value = listaCookies[i].getValue();
    		   	String[] values = value.split("#");
    		   	profesion = values[1];
        	}
        }
    }
    LinkedHashMap<String,String> listaProfesiones = new LinkedHashMap<String,String>();  
    listaProfesiones = (LinkedHashMap<String,String>) request.getAttribute("listaProfesiones");

    String salida = "";
    Iterator<String> iteradorConjuntoClaves = listaProfesiones.keySet().iterator();
    while (iteradorConjuntoClaves.hasNext()) {
    	String clave = iteradorConjuntoClaves.next();
  		String valor = listaProfesiones.get(clave);
  		if(profesion.equals(clave)) {
  			salida += "<input type='radio' name='profesion' value='" + clave+"' checked/>" + valor+"" + "\n";
  		} else {
  			salida += "<input type='radio' name='profesion' value='" + clave+"'/>" + valor+"" + "\n";
  		}
    }
    %>
<!DOCTYPE html>
<html>
<head>
<title>Welcome!!</title>
<meta charset="utf-8">
</head>
<body>
	<p>Bienvenido <b><%=usuario %></b></p>
	<form action="Desconectar">
		<%=salida %>
		<input type="submit" value="Desconectar"/>
		<input type="hidden" name="usuario_welcome" value="<%=usuario %>"/>
		<input type="hidden" name="contador_welcome" value="<%=contador %>"/>
	</form>
	<p>Nº de visitas: <%=contador %></p>
</body>
</html>