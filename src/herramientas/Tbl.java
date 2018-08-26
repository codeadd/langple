/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author brayan
 */
public class Tbl extends JFrame {
    
    LinkedList<String> tokens;
    LinkedList<String> errores;
    LinkedList<String> literales;
    JPanel panelGeneral;
    JList listaTokens;
    JList listaErrores;
    JList listaLiterales;
    JButton boton;
    
    public Tbl(LinkedList<String> tokens,LinkedList<String> errores,LinkedList<String> literales){
        this.tokens=tokens;
        this.errores=errores;
        this.literales=literales;
        
        this.setSize(new Dimension(600,400));
        this.setLayout(new BorderLayout());
        this.setTitle("Tablas");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        buildListas();
        
        this.add(panelGeneral,BorderLayout.CENTER);
        boton=new JButton();
        boton.setText("OK");
        boton.addActionListener(e->{this.dispose();});
        this.add(boton,BorderLayout.SOUTH);
    }
    
    public void buildListas(){
        panelGeneral=new JPanel();
        panelGeneral.setSize(new Dimension(500,300));
        panelGeneral.setLayout(new BorderLayout());
        listaTokens=new JList(tokens.toArray());
        listaTokens.setBorder(BorderFactory.createLineBorder(Constantes.NEGRO_1));
        listaErrores=new JList(errores.toArray());
        listaErrores.setBorder(BorderFactory.createLineBorder(Constantes.NEGRO_1));
        listaLiterales=new JList(literales.toArray());
        listaLiterales.setBorder(BorderFactory.createLineBorder(Constantes.NEGRO_1));
        /*panelGeneral.add(listaTokens,BorderLayout.CENTER);
        panelGeneral.add(listaErrores,BorderLayout.EAST);
        panelGeneral.add(listaLiterales,BorderLayout.WEST);*/
        JScrollPane scroll=new JScrollPane(listaTokens, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scroll1=new JScrollPane(listaLiterales, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scroll2=new JScrollPane(listaErrores, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        panelGeneral.add(scroll,BorderLayout.CENTER);
        panelGeneral.add(scroll1,BorderLayout.WEST);
        panelGeneral.add(scroll2,BorderLayout.EAST);
    }
    
}
