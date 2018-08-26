/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author cvem8
 */
public class Arista {
    private String valores; //El valor que me lleva al estado sea regex o transicion normal
    
    
    
     public Arista()
        {
            valores = "";
        }

        /// <summary>
        /// Le ingresamos una lista con los valores
        /// </summary>
        /// <param name="list"></param>
        public Arista(String val)
        {
            valores = val;
        }
    
    /**
     * @return the valores
     */
    public String getValores() {
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(String valores) {
        this.valores = valores;
    }
}
