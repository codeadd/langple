/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author brayanstiven
 */
public class Editor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /*#########################################
                HILO DE EJECUCION DEL PROGRAMA
          #########################################
        */
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Frame().setVisible(true);//CREA Y HACE VISIBLE LA VENTANA
            }
            
        });
    }
    
}
