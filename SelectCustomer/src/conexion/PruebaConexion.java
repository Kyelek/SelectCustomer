/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import conexion.conexion;
import com.sun.jdi.connect.spi.Connection;
/**
 *
 * @author Kyelek
 */
public class PruebaConexion {
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable(){
        
            public void run(){
            
                conexion  conecta = new conexion();
                Connection con = (Connection) conecta.getConexion();
            }
        });
    }
}
