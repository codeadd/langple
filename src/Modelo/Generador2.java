/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cvem8
 */
public class Generador2 {

    private LinkedList<String> tiposVariables;
    private LinkedList<String> sentenciasControl;
    private LinkedList<String> operadoresnormales;
    private LinkedList<String> operadoresLogicos;
    private LinkedList<String> findl;
    private LinkedList<String> escapesdeleng;
    private ArrayList<ArrayList<String>> matriz;

    private String code = "";
    private int contadorEtiquetas = 0;
    private int contadorLabels = 0;

    public Generador2() {
        code = "";
        contadorEtiquetas = 0;
        contadorLabels = 0;
        tiposVariables = new LinkedList<>(Arrays.asList("int", "str", "bl", "flt"));
        sentenciasControl = new LinkedList<>(Arrays.asList("if", "else", "while", "for"));
        operadoresnormales = new LinkedList<>(Arrays.asList("+", "-", "*", "/"));
        operadoresLogicos = new LinkedList<>(Arrays.asList("==", "!=", "<=", ">=", "and", "or", "<", ">"));
        //añadir ,"<", ">" a operadores logicos
        findl = new LinkedList<>(Arrays.asList(";", "(", "{", "}", ")"));
        escapesdeleng = new LinkedList<>(Arrays.asList("#", "&", "$"));
        setMatriz(new ArrayList<>());
        getMatriz().add(new ArrayList<>());
        getMatriz().get(0).add(0, "Instruccion"); // fila 0 y columna 0
        getMatriz().get(0).add(1, "Operando1"); // fila 0 y columna 1
        getMatriz().get(0).add(2, "Operando2"); // fila 0 y columna 2
        getMatriz().get(0).add(3, "Resultado"); // fila 0 y columna 3
    }

    public void analizar(String ruta) {
        try {
            FileReader filereader = new FileReader(ruta);
            BufferedReader buffer = new BufferedReader(filereader);

            String linea = buffer.readLine();

            while (linea != null) {
                String[] lineaTemp; // lo que contiene esa linea spliteado
                linea = linea.replaceAll("\\s+", " ");
                if (linea.indexOf(" ") == 0) {  // si en la posicion cero tiene un espacio entonces lo quitamos
                    linea = linea.replaceFirst(" ", "");
                }
                lineaTemp = linea.split(" ");
                //linea = b.readLine();
                analizarSentencias(linea, buffer);

                linea = buffer.readLine();

            }

            System.out.println("--------------Empiezp codigo------------------");
            code+="halt";
            System.out.println("" + code);
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gaurdarCodigo(String ruta){
        File f=new File(ruta);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(code);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Generador2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void insertarFila(String Intruccion, String Operando1, String Operando2, String Resultado) {
        this.matriz.add(new ArrayList<>(Arrays.asList(Intruccion, Operando1, Operando2, Resultado)));

    }

    public String imprimir2() {

        ArrayList<ArrayList<String>> matemp = this.matriz;
        String cad = buscarCadena();
        String result = "";
        ArrayList<String> lineas = new ArrayList<>();
        for (int i = 0; i < getMatriz().size(); i++) {
            ArrayList<String> temp = getMatriz().get(i);
            for (String s : temp) {
                if (s.length() < cad.length()) {
                    int n = cad.length() - s.length();

                    for (int j = 0; j < n; j++) {
                        s += " ";
                    }
                    s += " ";
                } else if (s.length() == cad.length()) {
                    s += " ";
                }
                result += s;
            }
            //result+="\n";
            lineas.add(result);
            result = "";
        }
        String ma = "";
        for (String linea : lineas) {
            if (linea.length() > ma.length()) {
                ma = linea;
            }
        }

        String separador = "+";
        for (int i = 0; i < ma.length(); i++) {
            separador += "-";
        }
        separador += "+";
        String result2 = separador + "\n";

        for (String linea : lineas) {
            result2 += "|" + linea + "|" + "\n";
        }
        result2 += separador + "\n";

        return result2;
    }

    public String buscarCadena() {
        String cad = "";
        for (int i = 0; i < getMatriz().size(); i++) {
            ArrayList<String> temp = getMatriz().get(i);
            for (String s : temp) {
                if (s.length() > cad.length()) {
                    cad = s;
                }
            }

        }
        return cad;
    }

    public void analizarSentencias(String linea, BufferedReader buffer) throws IOException {

        while (linea != null) {
            linea = linea.replaceAll("\\s+", " ");
            if (linea.indexOf(" ") == 0) {  // si en la posicion cero tiene un espacio entonces lo quitamos
                linea = linea.replaceFirst(" ", "");
            }
            String x = linea;
            String[] sep = x.split(" ");
            if (sep[0].equals("if")) {
                String temp = x.substring(x.indexOf("if") + 2, x.indexOf("then"));
                temp = temp.replaceAll(" ", "");
                System.out.println("" + temp);
                if (temp.indexOf("<=") > -1) {
                    temp = temp.replace("<=", " " + "<=" + " ");
                } else if (temp.indexOf(">=") > -1) {
                    temp = temp.replace(">=", " " + ">=" + " ");
                } else {
                    for (String s : operadoresLogicos) {

                        temp = temp.replace(s, " " + s + " ");
                    }
                }
                for (String s : escapesdeleng) {
                    temp = temp.replace(s, "");
                }
                int contop = 0;

                String[] spli = temp.split(" ");
                for (String s : spli) {
                    if (operadoresLogicos.contains(s)) {
                        contop++;
                    }
                }
                System.out.println("" + temp);
                String nombre = "";
                String lbl = "";
                if (contop > 0) {
                    for (int i = 0; i < contop; i++) {
                        if (i == 0) {
                            nombre = generarEtiquieta();
                            code += nombre + " = " + spli[0] + spli[1] + spli[2] + "\n";
                            insertarFila(spli[1], spli[0], spli[2], nombre);
                        }
                    }
                    lbl = generarLabel();
                    code += "if_false " + nombre + " goto " + lbl + "\n";
                    linea = buffer.readLine();  // leemos una nueva linea
                    analizarSentencias(linea, buffer); // llamamos para que analice la linea que sigue
                    linea = buffer.readLine();
                    code += "label " + lbl + ":\n"; // cerramos la etiqueta

                } else {
                    System.out.println("ERROR -----------------asd ------------- " + contop);
                }
            } else if (sep[0].equals("while")) {
                String temp = x.substring(x.indexOf("while") + 5, x.indexOf("then"));
                temp = temp.replaceAll(" ", "");
                System.out.println("" + temp);
                if (temp.indexOf("<=") > -1) {
                    temp = temp.replace("<=", " " + "<=" + " ");
                } else if (temp.indexOf(">=") > -1) {
                    temp = temp.replace(">=", " " + ">=" + " ");
                } else {
                    for (String s : operadoresLogicos) {

                        temp = temp.replace(s, " " + s + " ");
                    }
                }
                for (String s : escapesdeleng) {
                    temp = temp.replace(s, "");
                }
                int contop = 0;

                String[] spli = temp.split(" ");
                for (String s : spli) {
                    if (operadoresLogicos.contains(s)) {
                        contop++;
                    }
                }

                System.out.println("" + temp);
                String nombre = "";
                String lbl = "";
                String lbl2 = generarLabel();
                code += lbl2 + ":\n";
                if (contop > 0) {
                    for (int i = 0; i < contop; i++) {
                        if (i == 0) {
                            nombre = generarEtiquieta();
                            code += nombre + " = " + spli[0] + spli[1] + spli[2] + "\n";
                            insertarFila(spli[1], spli[0], spli[2], nombre);
                        }
                    }
                    lbl = generarLabel();
                    code += "if_false " + nombre + " goto " + lbl + "\n";
                    linea = buffer.readLine();  // leemos una nueva linea
                    analizarSentencias(linea, buffer); // llamamos para que analice la linea que sigue
                    linea = buffer.readLine();
                    code += "goto " + lbl2 + ":\n";
                    code += "label " + lbl + ":\n"; // cerramos la etiqueta
                } else {
                    System.out.println("ERROR -----------------asd ------------- " + contop);
                }

            } else if (sep[0].equals("for")) {
                
                String temp = x.substring(x.indexOf("(") + 1, x.indexOf(")"));
                temp = temp.replaceAll("\\s+ ", " ");
                System.out.println("" + temp);
                
                for (String s : escapesdeleng) {
                    temp = temp.replace(s, "");
                }
                
                
                String[] temp2=temp.split("\\|");
                for (String string : temp2) {
                    System.out.println("temp "+ string);
                }
                for(String s: temp2){
                    for (String tipo : tiposVariables) {
                        
                        if(s.contains(tipo)){
                            System.out.println("antes " +s);
                            s=s.replace(tipo, "");
                            System.out.println("vALOR DE S despues " + s);
                        }
                    }
                }
                
                System.out.println("Temp 2 posicion cero" + temp);
                analizarVariables(temp2[0]);
                
                
                int contop = 0;
                
                for (String s : operadoresLogicos) {
                    if(temp2[1].contains(s)){
                        contop++;
                    }
                }
                if(temp2[1].indexOf(" ") == 0){
                    temp2[1] = temp2[1].replaceFirst(" ", "");
                }
                String[] spli=temp2[1].split(" ");

                System.out.println("imprimientdo for");
                System.out.println("" + temp);
                String nombre = "";
                String lbl = "";
                String lbl2 = generarLabel();
                code += lbl2 + ":\n";
                if (contop > 0) {
                    for (int i = 0; i < contop; i++) {
                        if (i == 0) {
                            nombre = generarEtiquieta();
                            code += nombre + " = " + spli[0] + spli[1] + spli[2] + "\n";
                            insertarFila(spli[1], spli[0], spli[2], nombre);
                        }
                    }
                    lbl = generarLabel();
                    code += "if_false " + nombre + " goto " + lbl + "\n";
                    analizarVariables(temp2[2]);
                    linea = buffer.readLine();  // leemos una nueva linea
                    analizarSentencias(linea, buffer); // llamamos para que analice la linea que sigue
                    linea = buffer.readLine();
                    code += "goto " + lbl2 + ":\n";
                    code += "label " + lbl + ":\n"; // cerramos la etiqueta
                } else {
                    System.out.println("ERROR -----------------asd ------------- " + contop);
                }


            } else if (sep[0].equals("else")) {
                String lbl = generarLabel();
                linea = buffer.readLine();  // leemos una nueva linea
                analizarSentencias(linea, buffer); // llamamos para que analice la linea que sigue
                linea = buffer.readLine();
                code += "goto " + lbl + "\n"; // cerramos la etiqueta
                code += "label " + lbl + ":\n"; // cerramos la etiqueta
            } else if (sep[0].equals("fun")) {
               
            }else if(sep[0].contains("say")){
                code+=sep[0] + "\n";
                linea = buffer.readLine();
                
            } else if (sep[0].contains("&") || tiposVariables.contains(sep[0])) {  // es porque son variables
                analizarVariables(linea);
                linea = buffer.readLine();
            } else {
                linea = buffer.readLine();
            }
            if (linea != null) {
                if (linea.contains("}")) {
                    linea = null;
                }
            }
        }

    }

    public void analizarVariables(String linea) {
        String x = linea;
        System.out.println("Analizando variables " + linea);
        for (String tiposVariable : getTiposVariables()) {
            x = x.replaceAll(tiposVariable, "");
            x = x.replace(" ", "");
        }
        x = x.replace("=", " = ");
        x = x.replaceAll("\\s+", " ");
        System.out.println("" + x);
        for (String s : getOperadoresnormales()) {
            x = x.replace(s, " " + s + " ");
        }
        for (String s : getEscapesdeleng()) {
            x = x.replace(s, "");
        }
        for (String s : getFindl()) {
            x = x.replace(s, " " + s);
        }

        /*Recordar organizar lo de las cadenas */
        String[] sep = x.split(" ");

        int nop = 0;

        for (String s : sep) {
            System.out.println("" + s);
            if (getOperadoresnormales().contains(s)) {
                nop++;
            }
        }
        System.out.println("n opera:" + nop);
        int posini = 2;
        String nombre = "";
        String nombreh = "";
        if (nop > 0) {

            for (int i = 0; i < nop; i++) {
                if (i == 0) {
                    nombre = generarEtiquieta();
                    setCode(getCode() + nombre + " = " + sep[posini] + sep[posini + 1] + sep[posini + 2] + "\n");
                    insertarFila(sep[posini + 1], sep[posini], sep[posini + 2], nombre);
                    posini += 3;
                } else {
                    nombreh = nombre;
                    nombre = generarEtiquieta();
                    setCode(getCode() + nombre + " = " + nombreh + sep[posini] + sep[posini + 1] + "\n");
                    insertarFila(sep[posini], nombreh, sep[posini + 1], nombre);
                    posini += 2;
                }
            }
            setCode(getCode() + sep[0] + sep[1] + nombre + "\n");
            insertarFila(sep[1], nombre, "----", sep[0]);
        } else if (sep[1].equals("=")) {
            setCode(getCode() + sep[0] + sep[1] + sep[2] + "\n");
            insertarFila(sep[1], sep[2], "----", sep[0]);
        } else if (sep[1].equals(";")) {
            setCode(getCode() + sep[0] + "\n");
            insertarFila("----", "----", "----", sep[0]);
        } else {
            setCode(getCode() + "0x00afdsajkhdjkash");
            insertarFila("ERROR", "----", "---", "0x00afdsajkhdjkash");
        }

    }

    public String generarEtiquieta() {
        String etiqu = "t" + getContadorEtiquetas();
        setContadorEtiquetas(getContadorEtiquetas() + 1);
        return etiqu;
    }

    public String generarLabel() {
        String lbl = "L" + getContadorLabels();
        setContadorLabels(getContadorLabels() + 1);
        return lbl;
    }

    /**
     * @return the tiposVariables
     */
    public LinkedList<String> getTiposVariables() {
        return tiposVariables;
    }

    /**
     * @param tiposVariables the tiposVariables to set
     */
    public void setTiposVariables(LinkedList<String> tiposVariables) {
        this.tiposVariables = tiposVariables;
    }

    /**
     * @return the sentenciasControl
     */
    public LinkedList<String> getSentenciasControl() {
        return sentenciasControl;
    }

    /**
     * @param sentenciasControl the sentenciasControl to set
     */
    public void setSentenciasControl(LinkedList<String> sentenciasControl) {
        this.sentenciasControl = sentenciasControl;
    }

    /**
     * @return the operadoresnormales
     */
    public LinkedList<String> getOperadoresnormales() {
        return operadoresnormales;
    }

    /**
     * @param operadoresnormales the operadoresnormales to set
     */
    public void setOperadoresnormales(LinkedList<String> operadoresnormales) {
        this.operadoresnormales = operadoresnormales;
    }

    /**
     * @return the operadoresLogicos
     */
    public LinkedList<String> getOperadoresLogicos() {
        return operadoresLogicos;
    }

    /**
     * @param operadoresLogicos the operadoresLogicos to set
     */
    public void setOperadoresLogicos(LinkedList<String> operadoresLogicos) {
        this.operadoresLogicos = operadoresLogicos;
    }

    /**
     * @return the findl
     */
    public LinkedList<String> getFindl() {
        return findl;
    }

    /**
     * @param findl the findl to set
     */
    public void setFindl(LinkedList<String> findl) {
        this.findl = findl;
    }

    /**
     * @return the escapesdeleng
     */
    public LinkedList<String> getEscapesdeleng() {
        return escapesdeleng;
    }

    /**
     * @param escapesdeleng the escapesdeleng to set
     */
    public void setEscapesdeleng(LinkedList<String> escapesdeleng) {
        this.escapesdeleng = escapesdeleng;
    }

    /**
     * @return the matriz
     */
    public ArrayList<ArrayList<String>> getMatriz() {
        return matriz;
    }

    /**
     * @param matriz the matriz to set
     */
    public void setMatriz(ArrayList<ArrayList<String>> matriz) {
        this.matriz = matriz;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the contadorEtiquetas
     */
    public int getContadorEtiquetas() {
        return contadorEtiquetas;
    }

    /**
     * @param contadorEtiquetas the contadorEtiquetas to set
     */
    public void setContadorEtiquetas(int contadorEtiquetas) {
        this.contadorEtiquetas = contadorEtiquetas;
    }

    /**
     * @return the contadorLabels
     */
    public int getContadorLabels() {
        return contadorLabels;
    }

    /**
     * @param contadorLabels the contadorLabels to set
     */
    public void setContadorLabels(int contadorLabels) {
        this.contadorLabels = contadorLabels;
    }
}
