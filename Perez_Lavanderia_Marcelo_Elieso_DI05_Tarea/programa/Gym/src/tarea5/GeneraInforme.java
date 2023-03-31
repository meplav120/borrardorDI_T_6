package tarea5;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author meper
 */
public class GeneraInforme {
   
   
    public static void ejecutar(int desde,int hasta) {
        //Ruta del informe respecto del proyecto NetBeans
            String archivoJasper="informes/asistenciaMEPL.jasper";
            // Hacemos que la ruta del informe sea la carpeta gym-informes que crearemos
            // dentro de la carpeta Documents del usuario que está utilizando la aplicación.
            String ruta = System.getProperty("user.home") + File.separator+ "Documents" +
                    File.separator + "Gym-informes";
            File directorio=new File(ruta);
            
          
        try {
 
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/gym", "root","");
            InputStream image= GeneraInforme.class.getResourceAsStream("C:\\Users\\meper\\Desktop\\Perez_Lavanderia_Marcelo_Elieso_DI05_Tarea\\programa\\Gym\\informes\\resource\\logogym.jpg");
          
            //Cargamos los parametros del informe en la taba Hash
            Map parametros=new HashMap();
            parametros.put("desde",desde);
            parametros.put("hasta",hasta);
            parametros.put("icono", image);
           
            //generamos el informe en memoria
            JasperPrint print=JasperFillManager.fillReport(archivoJasper, parametros,conexion);
            //Si el directorio no existe, se crea
            if(!directorio.exists()){
                directorio.mkdir();
            }
            //Añadimos a la ruta el nombre del archivo informe.pdf
            ruta+=File.separator+"iforme.pdf";
            //Exportar el informe a PDF
            JasperExportManager.exportReportToPdfFile(print,ruta);
            //Abre el archivo PDF generado
            File path=new File(ruta);
                try {
                    Desktop.getDesktop().open(path);
                } catch (IOException e) {
                   JOptionPane.showMessageDialog(null,e.toString(),"Error",JOptionPane.WARNING_MESSAGE);
                }
            
            
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
