/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restad;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author oriol.nin
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of restad.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "<html><head/><body><h1>Hello World </h1></body></html";
    }
    /**
     * POST method to register a new image
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param crea_date
     * @return
     */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String registerImage(@FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("keywords") String keywords,
            @FormParam("author") String author,
            @FormParam("creation") String crea_date){
         //TODO write your implementation code here:
        
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            Random ran = new Random();
            String id = String.valueOf(ran.nextInt(100)+1);
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            conn = DriverManager.getConnection("jdbc:sqlite:F:\\windows\\ADPractica4\\loquesea.db");
           //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
           PreparedStatement statement = conn.prepareStatement("insert into imagenes values (?, ?, ?, ?, ?, ? , ?)");
           statement.setString(1,id );
           statement.setString(2, "Jordi");
           statement.setString(3, title);
           statement.setString(4, description);
           statement.setString(5, keywords);
           statement.setString(6, author);
           statement.setString(7, crea_date);
           statement.executeUpdate();
            return "<html><head/><body><h1>Registre Correcte :)!</h1></body></html>";
        }
        catch(SQLException ex){
            System.out.println(ex);
            return "<html><head/><body><h1>Registre Incorrecte :(!</h1></body></html>";
        }
    }
    /**
     * POST method to register a new image
     * @param id
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param crea_date
     * @return
     */
    @Path("modify")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String modifyImage (@FormParam ("id") String id,
            @FormParam ("title") String title,
            @FormParam ("description") String description,
            @FormParam ("keywords") String keywords,
            @FormParam ("author") String author,
            @FormParam("creation") String creation){
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:F:\\windows\\ADPractica4\\loquesea.db");                
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
           //conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
           
           PreparedStatement statement = conn.prepareStatement("update imagenes set titulo = ?, descripcion = ?, palabras_clave = ?, autor = ? where id_imagen = ?;");
           statement.setString(1, title);
           statement.setString(2, description);
           statement.setString(3, keywords);
           statement.setString(4, author);
           statement.setString(5, id);

           statement.executeUpdate();
           return "<html><head/><body><h1>Modificacio Correcte :)!</h1>"
                   + "<h2>Titol: "+ title +"</h2></br>"
                   + "<h2>Autor: "+ author+ "</h2></br>"
                   + "<p>"+ description + "</p></br>"
                   + "<h2>Keywords: "+keywords+"</h2></br>"
                   + "<h2>Data de creacio: " + creation + "</h2></body></html>";
        }
        catch(SQLException e){
            System.out.println(e);
            return "<html><head/><body><h1>Modificacio Incorrecte :(!</h1></body></html>";
        }
    }
    
        
    /**
    * GET method to list images
    * @return
    */
    @Path("list")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String listImages () {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
           //conn = DriverManager.getConnection("jdbc:sqlite:F:\\windows\\ADPractica4\\loquesea.db");                
           //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
           conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
           
           String id_imatge;
           String titol;
           String descripcio;
           String clau;
           String autor;
           String creacio;
           String html = "<html><head/><body><h1>Llistat Correcte :)!</h1>"; 
           
           PreparedStatement statement =  conn.prepareStatement("select * from imagenes");
           
           ResultSet rs = statement.executeQuery();

           while(rs.next()){
               titol = rs.getString("titulo");
               id_imatge = rs.getString("id_imagen");
               descripcio = rs.getString("descripcion");
               clau = rs.getString("palabras_clave");
               autor = rs.getString("autor");
               creacio = rs.getString("creacion");
               
               html = html + "<div><h2>" + titol +"</h2>"
                       +"<p>Descripció: " + descripcio +"</p>"
                       +"<p>Autor: " + autor +"</p>"
                       +"<p>Data de creació: " + creacio +"</p></div></br></br>";
           }
           html = html + "</body></html>";
           
           return html;
           
        } catch(SQLException e){
              System.err.println(e.getMessage());
        } 
        finally{
            try{
                if(conn != null)
                    conn.close();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        
        return "<html><head/><body><h1>Llistar Incorrecte :(!</h1></body></html>";
    }
    
    /**
     * @param id
     * @return
     */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String SearchByID (@PathParam("id") int id){
        Connection conn = null;
        String html = "<html></head><body>";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:F:\\windows\\ADPractica4\\loquesea.db"); 
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where id_imagen = ?");
            statement.setString(1, String.valueOf(id));
            ResultSet rs = statement.executeQuery();
            html = html + "<div>";
            html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
            html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
            html = html + "<p>" + rs.getInt("descripcion") + "</p> <br>";
            html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
            html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
            html = html + "</div></body></html>";
            return html;
        }
        catch(SQLException e){
            System.out.println(e);
            return "<html></header><body><h1> No hi ha cap Imatge amb aquest ID o hi ha hagut un error</h1></body></html>";
        }
    }
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
