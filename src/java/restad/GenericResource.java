/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restad;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * REST Web Service
 *
 * @author oriol.nin
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    String htmini = "<html><head/><body>";
    String htmfini = "</body></html>";

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
     * @param uploadedInputStream
     * @param fileDetail
     * @return
     */
    @Path("register")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public String registerImage(@FormDataParam("title") String title,
            @FormDataParam("description") String description,
            @FormDataParam("keywords") String keywords,
            @FormDataParam("author") String author,
            @FormDataParam("creation") String crea_date,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail){
         //TODO write your implementation code here:
         
         
        String html = htmini;
         
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            Random ran = new Random();
            String id = String.valueOf(ran.nextInt(10000)+1);
           //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
           //conn = DriverManager.getConnection("jdbc:sqlite:F:\\windows\\ADPractica4\\loquesea.db");
           conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
           
           String uploadedFileLocation = "/Users/Jordi/NetBeansProjects/ImRest/web/imatges/" + title + id + ".jpeg";
           writeToFile(uploadedInputStream, uploadedFileLocation);
           
           PreparedStatement statement = conn.prepareStatement("insert into imagenes values (?, ?, ?, ?, ?, ? , ?)");
           statement.setString(1,id );
           statement.setString(2, "Jordi");
           statement.setString(3, title);
           statement.setString(4, description);
           statement.setString(5, keywords);
           statement.setString(6, author);
           statement.setString(7, crea_date);
           statement.executeUpdate();
           html = html + "<h1>Registre Correcte :)!</h1>";
        }
        catch(SQLException ex){
            System.out.println(ex);
            html = html + "<h1>Registre Incorrecte :(!</h1>";
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
        html = html + "<a href=\"../../index.html\" id=\"refToMenu\">Tornar al menu</a>"+htmfini;
        return html;
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
        
        String html = htmini;
        
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
           
           PreparedStatement statement = conn.prepareStatement("update imagenes set titulo = ?, descripcion = ?, palabras_clave = ?, autor = ? where id_imagen = ?;");
           statement.setString(1, title);
           statement.setString(2, description);
           statement.setString(3, keywords);
           statement.setString(4, author);
           statement.setString(5, id);

           statement.executeUpdate();
           html = html + "<h1>Modificacio Correcte :)!</h1>"
                   + "<p>Titol: " + title + "</p></br>"
                   + "<p>Autor: " + author + "</p></br>"
                   + "<p>" + description + "</p></br>"
                   + "<p>Keywords: " + keywords + "</p></br>"
                   + "<p>Data de creacio: " + creation + "</p>";
        }
        catch(SQLException e){
            System.out.println(e);
            html = html + "<h1>Modificacio Incorrecte :(!</h1>";
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
        html = html + htmfini;
        return html;
    }
    
    /**
     * POST method to register a new image
     * @param id
     * @return
     */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String deleteImage (@FormParam ("id") String id) {
        
        String html = htmini;
        
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
           
           PreparedStatement statement =  conn.prepareStatement("select * from imagenes where id_imagen = ?");
           statement.setString(1,id);
           ResultSet rs = statement.executeQuery();
           String titol = rs.getString("titulo");
           String id_imatge = rs.getString("id_imagen");
               
           
           File imatge = new File("imatges/" + titol + id_imatge + ".jpeg");
           
           statement =  conn.prepareStatement("delete from imagenes where id_imagen = ?");
           statement.setString(1, id);
           statement.executeUpdate();
           statement.executeUpdate();
           html = html + "<h1>Esborrat Correcte :)!</h1>";
        }
        catch(SQLException ex){
            System.out.println(ex);
            html = html + "<h1>Esborrat Incorrecte :(!</h1>";
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
        html = html + htmfini;
        return html;
    }
        
    /**
    * GET method to list images
    * @return
    */
    @Path("list")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String listImages () {
        
        String html = htmini;
        
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
           String path_imatge;
           html = html + "<h1>Llistat Correcte :)!</h1>"; 
           
           PreparedStatement statement =  conn.prepareStatement("select * from imagenes");
           
           ResultSet rs = statement.executeQuery();

           while(rs.next()){
               titol = rs.getString("titulo");
               id_imatge = rs.getString("id_imagen");
               descripcio = rs.getString("descripcion");
               clau = rs.getString("palabras_clave");
               autor = rs.getString("autor");
               creacio = rs.getString("creacion");
               path_imatge = "../../imatges/" + titol + id_imatge + ".jpeg";
               html = html + "<div><h2>" + titol +"</h2>"
                       + "<img src=\"" + path_imatge + "\" alt=" + titol + ">"
                       +"<p>Descripcio: " + descripcio +"</p>"
                       +"<p>Autor: " + autor +"</p>"
                       +"<p>Data de creacio: " + creacio +"</p></div></br></br>";
           }
           html = html + htmfini;
                      
        } catch(SQLException e){
              System.err.println(e.getMessage());
              html = "<html><head/><body><h1>Llistar Incorrecte :(!</h1></body></html>";

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
        return html;
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
        String html = htmini;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            //conn = DriverManager.getConnection("jdbc:sqlite:F:\\windows\\ADPractica4\\loquesea.db"); 
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where id_imagen = ?");
            statement.setString(1, String.valueOf(id));
            ResultSet rs = statement.executeQuery();
            html = html + "<div>";
            html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
            html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
            html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
            html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
            html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
            html = html + "</div>";
            html = html + htmfini;
        }
        catch(SQLException e){
            System.out.println(e);
            html = "<html></header><body><h1> No hi ha cap Imatge amb aquest ID o hi ha hagut un error</h1></body></html>";
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
        return html;
    }
    /**
     * GET mthod to search images by title
     * @param title
     * @param return
     */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByTitle(@PathParam("title") String title){
        String html = htmini;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where titulo = ?"); 
            statement.setString(1, title);
            
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                while (rs.next()) {                
                    html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                }
                html = html + htmfini; 
            }
            else return "<html></header><body><h1> No hi ha cap Imatge amb aquest titol o hi ha hagut un error</h1></body></html>";
      
        } catch(SQLException e) {
            System.out.println(e);
           return "<html></header><body><h1> No hi ha cap Imatge amb aquest titol o hi ha hagut un error</h1></body></html>";
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
        return html;
    }
    /**
     * GET mthod to search images by creation date
     * @param creaDate
     * @param return
     */
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchCreationDate(@PathParam("date") String date){
        String html = htmini;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where creacion = ?"); 
            statement.setString(1, date);
            
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                while (rs.next()) {                
                    html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                }
                html = html + htmfini; 
            }
            else html = "<html></header><body><h1> No hi ha cap Imatge amb aquesta data de creacio o hi ha hagut un error</h1></body></html>";
        } catch(SQLException e) {
            System.out.println(e);
            html = "<html></header><body><h1> No hi ha cap Imatge amb aquesta data de creacio o hi ha hagut un error</h1></body></html>";
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
        return html;
    }
    /**
     * GET mthod to search images by author
     * @param author
     * @param return
     */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByAuthor(@PathParam("author") String author){
        String html = htmini;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where autor = ?"); 
            statement.setString(1, author);
            
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                while (rs.next()) {                
                    html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                }
                html = html + htmfini; 
            }
            else html = "<html></header><body><h1> No hi ha cap Imatge amb aquest autor o hi ha hagut un error</h1></body></html>";
        } catch(SQLException e) {
            System.out.println(e);
            html = "<html></header><body><h1> No hi ha cap Imatge amb aquest autor o hi ha hagut un error</h1></body></html>";
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
        return html;
    }
    /**
     * GET mthod to search images by keywords
     * @param keywords
     * @param return
     */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String searchByKeywords(@PathParam("keywords") String keywords){
        String html = htmini;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            //conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes"); 
            
            
            boolean trobat = false;
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(rs.getString("palabras_clave").contains(keywords)){
                    trobat = true;
                    html = html + "<div>";
                    html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                    html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                    html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                    html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                    html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                    html = html + "</div>";
                }
                while (rs.next()) {     
                    if(rs.getString("palabras_clave").contains(keywords)){
                        trobat = true;
                        html = html + "<div>";
                        html = html + "<h2>Titol: " + rs.getString("titulo") + "</h2><br>";
                        html = html + "<p>Autor: " + rs.getString("autor") + "<p><br>";
                        html = html + "<p>" + rs.getString("descripcion") + "</p> <br>";
                        html = html + "<p>Paraules clau: " + rs.getString("palabras_clave") +  "</p><br>";
                        html = html + "<p>Data de creacio: " + rs.getString("creacion") + "</p><br>";
                        html = html + "</div>";
                    } 
                }
                html = html + htmfini; 
            }
            else if(!trobat) html = "<html></header><body><h1> No hi ha cap Imatge amb aquestes paraules clau o hi ha hagut un error</h1></body></html>";
        } catch(SQLException e) {
            System.out.println(e);
            html = "<html></header><body><h1> No hi ha cap Imatge amb aquestes paraules clau o hi ha hagut un error</h1></body></html>";
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
        return html;
    }
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
    
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {
        
        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
