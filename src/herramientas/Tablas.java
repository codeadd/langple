/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author brayan
 */
public class Tablas extends JDialog{
    
    JTable tabla;
    
    public Tablas(){
        tabla=new JTable();
    }
    
    public Tablas(String nombre,LinkedList<String> tokens,LinkedList<String> errores,LinkedList<String> literales){
        this.setSize(new Dimension(600,400));
        this.setTitle(nombre);
        JPanel panelgeneral=new JPanel(new BorderLayout());
        tabla=new JTable();
        if(tokens!=null){
            if(tokens.size()>0){
                String[][] datos = new String[tokens.size()][3];

                DefaultTableModel model=new DefaultTableModel();
                model.setRowCount(tokens.size());
                model.setColumnCount(3);
                model.setColumnIdentifiers("ERRORES,TOKENS,LITERALES".split(","));
                tabla.setModel(model);
                for (int i = 0; i < errores.size(); i++) {
                    datos[i][0]=errores.get(i);
                }
                for (int i = 0; i < tokens.size(); i++) {
                    datos[i][1]=tokens.get(i);
                }
                for (int i = 0; i < literales.size(); i++) {
                    datos[i][2]=literales.get(i);
                }
                for (int i = 0; i < datos.length; i++) {
                    for (int j = 0; j < datos[i].length; j++) {
                        tabla.setValueAt(datos[i][j], i, j);
                    }
                }
                
                              
            }
        }
        
        JButton aceptar=new JButton("Aceptar");
                aceptar.addActionListener(e->{
                    this.dispose();
        });
        
        JScrollPane barras = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
        panelgeneral.add(barras,BorderLayout.CENTER);
        panelgeneral.add(aceptar,BorderLayout.SOUTH);
        
        this.add(panelgeneral);
    }
    
    
    public Tablas(String nombre,String[][] matriz){
        this.setSize(new Dimension(600,400));
        this.setTitle(nombre);
        JPanel panelgeneral=new JPanel(new BorderLayout());
        tabla=new JTable();
        
        if(matriz!=null){
            
                String[][] datos = new String[matriz.length][matriz[0].length];
                datos=matriz;
                DefaultTableModel model=new DefaultTableModel();
                model.setRowCount(matriz.length);
                model.setColumnCount(matriz[0].length);
                tabla.setModel(model);
                for (int i = 0; i < datos.length; i++) {
                    for (int j = 0; j < datos[i].length; j++) {
                        tabla.setValueAt(datos[i][j], i, j);
                    }
                }
                
                              
        }
        
        
        
        JButton aceptar=new JButton("Aceptar");
                aceptar.addActionListener(e->{
                    this.dispose();
        });
        
        JScrollPane barras = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
        panelgeneral.add(barras,BorderLayout.CENTER);
        panelgeneral.add(aceptar,BorderLayout.SOUTH);
        
        this.add(panelgeneral);
    }
    
    public Tablas(LinkedList<String> pila,LinkedList<String> cadena,LinkedList<String> accion){
        this.setSize(new Dimension(600,400));
        JPanel panelgeneral=new JPanel(new BorderLayout());
        tabla=new JTable();
        if(pila!=null){
            if(pila.size()>0){
                String[][] datos = new String[pila.size()][3];

                DefaultTableModel model=new DefaultTableModel();
                model.setRowCount(pila.size());
                model.setColumnCount(3);
                model.setColumnIdentifiers("PILA,CADENA,ACCION".split(","));
                tabla.setModel(model);
                for (int i = 0; i < pila.size(); i++) {
                    datos[i][0]=pila.get(i);
                }
                for (int i = 0; i < cadena.size(); i++) {
                    datos[i][1]=cadena.get(i);
                }
                for (int i = 0; i < accion.size(); i++) {
                    datos[i][2]=accion.get(i);
                }
                for (int i = 0; i < datos.length; i++) {
                    for (int j = 0; j < datos[i].length; j++) {
                        tabla.setValueAt(datos[i][j], i, j);
                    }
                }
                
                              
            }
        }
        
        JButton aceptar=new JButton("Aceptar");
                aceptar.addActionListener(e->{
                    this.dispose();
        });
        
        JScrollPane barras = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
        panelgeneral.add(barras,BorderLayout.CENTER);
        panelgeneral.add(aceptar,BorderLayout.SOUTH);
        
        this.add(panelgeneral);
    }
    
}
