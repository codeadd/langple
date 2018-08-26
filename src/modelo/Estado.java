/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author cvem8
 */
public class Estado {

    private String nombre;
    private HashMap<Estado, Arista> transiciones;
    private Boolean isFinal;
    private Boolean isInicial;

    public Estado(String nombre, Boolean inicial, Boolean fin) {
        this.nombre = nombre;
        this.isFinal = fin;
        this.isInicial = inicial;
        transiciones = new HashMap<>();
    }

    public Estado() {
        this.nombre = "";
        this.transiciones = new HashMap<>();
        this.isFinal = false;
        this.isInicial = false;
    }

    public Boolean Add(Estado estado, Arista arista) {
        // ***** 
        Boolean exist = false;
        for (Map.Entry<Estado, Arista> entry : transiciones.entrySet()) {   //Recorremos  clave y valor para verificar
            if (entry.getKey().getNombre().equals(estado.getNombre())) {
                if (entry.getValue().getValores().equals(arista.getValores())) {  //cual es el estado al que va la respectiva transicion
                    exist = true;
                }
            }

        }
        if (exist) {
            System.err.print("Esa transicion ya existe en ese estado  -> " + estado.getNombre() + " arista" + arista.getValores() +" \n");
            return exist;
        }
        else{
            this.transiciones.put(estado, arista);
            return exist;
        }
        

    }

    /**
     * Imprime los estados a los que lleva cada transicion con su respectiva
     * transicion
     */
    public void PrintTransitions() {
        System.out.println("NOMBRE " + this.nombre);
        transiciones.forEach((Estado est, Arista arist) -> {
            System.out.println("Nombre -> " + est.getNombre() + " Arista ->  " + arist.getValores());
        });
    }

    /**
     *
     * @param valor
     * @return El estado a donde dirije o null en caso de no encontrar un
     * transicion con ese valor
     */
    public Estado GetTransicion(String valor) {
        Estado temp = null;

        for (Map.Entry<Estado, Arista> entry : transiciones.entrySet()) {   //Recorremos  clave y valor para verificar
            if (entry.getValue().getValores().equals(valor)) {  //cual es el estado al que va la respectiva transicion
                temp = entry.getKey();
            }

        }
        if (temp == null) {
            return null;
        } else {
            return temp;
        }

    }

    public int GetNumTransitions() {
        return transiciones.size();
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the transiciones
     */
    public HashMap<Estado, Arista> getTransiciones() {
        return transiciones;
    }

    /**
     * @param transiciones the transiciones to set
     */
    public void setTransiciones(HashMap<Estado, Arista> transiciones) {
        this.transiciones = transiciones;
    }

    /**
     * @return the isFinal
     */
    public Boolean getIsFinal() {
        return isFinal;
    }

    /**
     * @param isFinal the isFinal to set
     */
    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * @return the isInicial
     */
    public Boolean getIsInicial() {
        return isInicial;
    }

    /**
     * @param isInicial the isInicial to set
     */
    public void setIsInicial(Boolean isInicial) {
        this.isInicial = isInicial;
    }
}
