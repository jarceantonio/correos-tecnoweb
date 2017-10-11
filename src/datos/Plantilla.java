/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Antonio Arce
 */
public abstract class Plantilla {
    
    public Conexion conexion;
    
    public abstract String insertar();
    public abstract String actualizar();
    public abstract String eliminar();
    public abstract String listar();
    public abstract int cantidadAtributos();
    public abstract Object[] columnas();
    
    
    private boolean consultar(String consulta){
        try {
            Connection conn = Conexion.getConnection();
            PreparedStatement st = conn.prepareStatement(consulta);         
            boolean resultado = !st.execute();
            st.close();
            return resultado;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean guardar(){
        return consultar(insertar());
    }
    
    public boolean modificar(){
        return consultar(actualizar());
    }
    
    public boolean borrar(){
        return consultar(eliminar());
    }
    
    public DefaultTableModel listarTodos(){
        List<Object> lista = new ArrayList<>();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnas());
        try {
            Connection con = Conexion.getConnection();
            Statement consulta = con.createStatement();
            ResultSet resultado = consulta.executeQuery(listar());
            while (resultado.next()) {                
                Object[] obj = new Object[cantidadAtributos()];
                for (int i = 1; i <= cantidadAtributos(); i++) {
                    obj[i-1] = resultado.getObject(i);
                }
                model.addRow(obj);
            }
            return model;
            
        } catch (SQLException e) {
            return null;
        }
    }
    
    
}
