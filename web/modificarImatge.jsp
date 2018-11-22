<%-- 
    Document   : modificarImatge
    Created on : 31-oct-2018, 10:42:10
    Author     : oriol.nin
--%>

<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Imatge</title>
    </head>
    <body>
        <div>
            <h1 id="headerModicarImatge">Modificar Imatge</h1>
            <p id="dades"><strong>Introdueix les dades que es demanen a continuació</strong></p>
            <%
                Connection conn = null;
                String id_modificar = request.getParameter("id_imatge");
                Class.forName("org.sqlite.JDBC");
                String user = (String) session.getAttribute("user");

                try{
                    //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
                    conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
                    //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
                    System.out.print(id_modificar);
                    PreparedStatement statement =  conn.prepareStatement("select * from imagenes where id_imagen = ?");
                    statement.setString(1, id_modificar);
                    
                    String titol;
                    String descripcio;
                    String clau;
                    String autor;
                    String path;
                    
                    ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                        titol = rs.getString("titulo");
                        descripcio = rs.getString("descripcion");
                        clau = rs.getString("palabras_clave");
                        autor = rs.getString("autor");
                        path = "imatges/" + titol +  id_modificar + ".jpeg";
                        
                        out.println("<br><img src=\"" + path + "\"><br><br>");
                        
                        out.println("<form method=\"post\" action=\"webresources/generic/modify\" id=\"modificarImatge\">");
                        out.println("<label for=\"title\">Títol: " + titol + "</label>");
                        out.println("<input type=\"hidden\" value=\"" + titol + "\" name=\"title\" id=\"title\"><br>");
                                                
                        out.println("<label for=\"description\">Descripció: </label><br>");
                        out.println("<textarea cols='30' rows='10' name=\"description\" id=\"description\">" + descripcio + "</textarea><br>");
                       
                        
                        out.println("<label for=\"author\">Autor: </label>");
                        out.println("<input type=\"text\" name=\"author\" id=\"author\" value=\"" + autor + "\"><br>");

                        out.println("<label for=\"keywords\">Paraules Clau: </label>");
                        out.println("<input type=\"text\" name=\"keywords\" id=\"keywords\" value=\"" + clau + "\"><br>"
                                + "<input type=\"hidden\" value=\"" + id_modificar + "\" name=\"id\" id=\"id\">");
                        
                        
                        
                        out.println("<input type=\"submit\" id=\"modificar\" value=\"Modificar\">");
                        out.println("</form>");
                    }
                }
                catch(SQLException e)
                {
                  System.err.println(e.getMessage());
                }   
                finally{
                   try
                  {
                    if(conn != null)
                      conn.close();
                  }
                  catch(SQLException e)
                  {
                    // connection close failed.
                    System.err.println(e.getMessage());
                  }
                }
            %>
                
        </div>
    </body>
</html>