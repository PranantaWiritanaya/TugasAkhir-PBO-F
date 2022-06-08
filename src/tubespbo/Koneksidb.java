/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubespbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Microsoft
 */
public class Koneksidb {
        
        private static java.sql.Connection koneksi;
    
    public static java.sql.Connection getkoneksi(){
        if (koneksi ==null){
            try{
                //Connection con = DriverManager.getConnection("jdbc:mysql:localhost:3306/login");
                //Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/Login";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url,user, password);
                System.out.println("Connection Sukses");   
            }catch (SQLException ex){
                System.out.println("Tidak konek");
            }
        }
        return koneksi;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    Formlogin log=new Formlogin();
    log.setVisible(true);
   }
}   


