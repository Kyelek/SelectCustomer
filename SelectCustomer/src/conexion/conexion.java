/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.*;


/**
 *
 * @author Kyelek
 */
public class conexion {
    public static final String URL = "jdbc:mysql://localhost:3306/scustomer" ;
    public static final String USER = "root";
    public static final String CLAVE = "";
    
    public Connection getConexion(){
        
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=(Connection) DriverManager.getConnection(URL,USER,CLAVE);
            System.out.println("Conexión realizada con exito");
        }catch(Exception e){
            System.out.println("No se ha realizado la conexión");
        }
        return con;
          
    }
    
}
