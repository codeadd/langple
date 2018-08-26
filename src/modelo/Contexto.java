package modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by gatito on 14/05/16.
 */
public class Contexto {

    private ArrayList<ArrayList<String>> matriz;
    private String nombre;
    public Contexto(String nombre) {
        this.setNombre(nombre);
        setMatriz(new ArrayList<>());
        getMatriz().add(new ArrayList<>());
        getMatriz().get(0).add(0,"Tipo"); // fila 0 y columna 0
        getMatriz().get(0).add(1,"Nombre"); // fila 0 y columna 1
        getMatriz().get(0).add(2,"Valor"); // fila 0 y columna 2
        getMatriz().get(0).add(3,"Parametro"); // fila 0 y columna 3
    }

    /**
     * Insertamos una fila en la tabla de contexto
     * @param tipo
     * @param nombre
     * @param valor
     * @param parametro
     */
    public void insertarFila(String tipo, String nombre, String valor, String parametro){
        this.matriz.add(new ArrayList<>(Arrays.asList(tipo,nombre,valor,parametro)));

    }

    public void imprimir(){
        for (int i = 0; i < getMatriz().size(); i++) {
            ArrayList<String> temp = getMatriz().get(i);
            for (String s : temp) {
                System.out.print(" " + s);
            }
            System.out.println("");
        }
    }

    public String imprimir2(){
        
        ArrayList<ArrayList<String>> matemp = this.matriz;
        String cad=buscarCadena();
        String result="";
        ArrayList<String> lineas=new ArrayList<>();
        for (int i = 0; i < getMatriz().size(); i++) {
            ArrayList<String> temp = getMatriz().get(i);
            for (String s : temp) {
                if(s.length()<cad.length()){
                    int n=cad.length()-s.length();
                    
                    for (int j = 0; j < n; j++) {
                        s+=" ";
                    }
                    s+=" ";
                }else if(s.length()==cad.length()){
                    s+=" ";
                }
                result+=s;
            }
            //result+="\n";
            lineas.add(result);
            result="";
        }
        String ma="";
        for (String linea : lineas) {
            if(linea.length()>ma.length()){
                ma=linea;
            }
        }
        
        String separador = "+";
        for (int i = 0; i < ma.length(); i++) {
            separador+="-";
        }
        separador+="+";
        String result2 = separador+"\n";
        
        for (String linea : lineas) {
            result2+="|"+linea+"|"+"\n";
        }
        result2+=separador+"\n";
        
        return result2;
    }
    
    public String buscarCadena(){
        String cad="";
        for (int i = 0; i < getMatriz().size(); i++) {
            ArrayList<String> temp = getMatriz().get(i);
            for (String s : temp) {
                if(s.length()>cad.length()){
                    cad=s;
                }
            }
            
        }
        return cad;
    }
    
    public ArrayList<ArrayList<String>> getMatriz() {
        return matriz;
    }

    public void setMatriz(ArrayList<ArrayList<String>> matriz) {
        this.matriz = matriz;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
