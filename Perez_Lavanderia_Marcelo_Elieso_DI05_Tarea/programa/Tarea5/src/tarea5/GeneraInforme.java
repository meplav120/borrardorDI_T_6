package tarea5;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.HashMap;


/**
 *
 * @author meper
 */
public class GeneraInforme {
   
   
    public static void ejecutar(int desde,int hasta) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/gym", "root","");
            InputStream image= GeneraInforme.class.getResourceAsStream("/resource/logogym.jpg");
            Map parametros=new HashMap();
            parametros.put("desde",desde);
            parametros.put("hasta",hasta);
            parametros.put("icono", image);
           

            JasperPrint print=JasperFillManager.fillReport("informes/asistenciaMEPL.jasper", parametros,conexion);
            JasperExportManager.exportReportToPdfFile(print,"informes/informePrueba.pdf");
        } catch (ClassNotFoundException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println("Fallo al cargar JDBC");
            System.exit(1);
        } catch (InstantiationException ex) {
            System.out.println("Imposible conectar.");
            System.exit(1);
        } catch (IllegalAccessException ex) {
            System.out.println("Excepcion acceso ilegal.");
            System.exit(1);
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar a BD");
            System.exit(1);
        } catch (JRException ex) {
            ex.printStackTrace();
            System.out.println("Excepcion JRE" + ex.getMessage());
            System.exit(1);
        }
   
    }

}
