/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brayan
 */
public class Generador {

    public Generador() {
    }

    //private ArrayList<String> sentencias =new ArrayList<>(Arrays.asList("x=y","x = y op z"));
    LinkedList<String> tiposVariables = new LinkedList<>(Arrays.asList("int", "str", "bl", "flt"));
    LinkedList<String> sentenciasControl = new LinkedList<>(Arrays.asList("if", "else", "while", "for"));
    LinkedList<String> operadoresnormales = new LinkedList<>(Arrays.asList("+", "-", "*", "/", "sqrt", "^", "="));
    LinkedList<String> operadoresLogicos = new LinkedList<>(Arrays.asList("==", "<", ">", "!=", "<=", ">=", "and", "or"));
    LinkedList<String> findl = new LinkedList<>(Arrays.asList(";", "(", "{","}",")"));
    LinkedList<String> escapesdeleng = new LinkedList<>(Arrays.asList("#", "&", "$"));
    String code = "";

    public void leerArchivo(String ruta) {
        FileReader f;
        String cad = "";
        try {
            f = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(f);

            cad += bf.readLine();
            String linea = cad;
            while (linea != null) {
                cad += linea;
                cad += "\n";
                //analizar(linea);
                linea = linea.replaceAll("\\s+", " ");
                //linea.replaceFirst(" ", "");
                boolean contienesentencias = false;
                for (String s2 : sentenciasControl) {
                    if (linea.contains(s2)) {
                        contienesentencias = true;
                    }
                }
                if(contienesentencias){
                    analizarsentencias(linea);
                }
                else if (!contienesentencias) {
                
                    for (String s : tiposVariables) {
                        if (linea.contains(s)) {
                            analizar(linea);
                        }
                    }
               }

                linea = bf.readLine();
            }
            code += "LF : halt \n";
            System.out.println("imprimiendo codio");
            System.out.println("" + code);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println(""+cad);
    }

    public void analizarCadena() {
        String x = "int &x=#2+#3-#4";

        ArrayList<ArrayList<String>> mat = new ArrayList<>();
        mat.add(new ArrayList<>());
        mat.get(0).add("Operacion");
        mat.get(0).add("Argumento1");
        mat.get(0).add("Argumento2");
        mat.get(0).add("Resultado");

        //HashMap<String,ArrayList<String>> datos = new HashMap<>();
        for (String tiposVariable : tiposVariables) {
            if (x.contains(tiposVariable)) {
                x = x.replaceAll(tiposVariable, "");
                x = x.replaceFirst(" ", "");
            }
        }

        int contador = 0;
        String t1 = x.substring(0, x.indexOf("="));
        t1 = t1.replace(" ", "");
        String nombre = "t" + contador;

        for (String s : operadoresnormales) {

        }
        String esp = x.replace("+", " + ");
        esp = esp.replace("=", " = ");
        esp = esp.replace("-", " - ");
        esp = esp.replace("&", "");
        esp = esp.replace("#", "");

        String[] verf = esp.split(" ");

        /*for (String s : verf) {
            System.out.println("" + s);
        }*/
        int contarparam = 0;
        String resp = "r" + contarparam;
        String temp = "";
        LinkedList<String> hay = new LinkedList<>();
        for (int i = 2; i < verf.length; i++) {
            if (contarparam != 1) {
                temp += verf[i] + " ";
                if (verf[i].equals("+") || verf[i].equals("-")) {
                    contarparam++;
                    contador++;
                }
            } else {
                contarparam = 0;
                temp += resp;
                hay.add(temp);
                temp = verf[i] + " ";
                resp = "r" + contador;
            }
        }
        for (String s : hay) {
            System.out.println("" + s);
        }

        //ArrayList<String> dato = new ArrayList<>();
        //datos.put(nombre, dato);
        System.out.println("" + x);
        System.out.println("" + esp);
        /*String[] cad=x.split(" ");
        int contador=0;
        String Clave=""+contador;*/

    }

    public void analizaotras() {
        String x = "if #5==&var and &var then{";
        String cad = "";
        if (x.contains("if ")) {
            int contador = 1;

            for (String s : operadoresLogicos) {
                x = x.replace(s, " " + s + " ");
            }
            for (String s : findl) {
                x = x.replace(s, " " + s);
            }
            for (String s : escapesdeleng) {
                x = x.replace(s, "");
            }

            String[] spli = x.split(" ");
            int nroop = 0;
            int posini = 1;
            for (Object s : spli) {
                System.out.println("" + s);
                for (String s2 : operadoresLogicos) {
                    if (s.equals(s2)) {
                        nroop++;
                    }
                }
            }
            String nombre = "";
            String nombreh = "";
            String vars = "";
            int contadorvar = 1;
            if (nroop > 0) {
                for (int i = 0; i < posini; i++) {
                    if (i == 0) {
                        if (!(spli[posini].equals("then")) && !(spli[posini + 1].equals("then"))) {
                            nombre = "t" + contadorvar;
                            cad += nombre + " = " + spli[posini] + spli[posini + 1] + spli[posini + 2] + "\n";

                            posini += 3;
                            vars += nombre + " ";
                            System.out.println("if2" + cad);
                            //System.out.println("entro "+cad);
                            contadorvar++;
                        } else {
                            nombre = "t" + contador;
                            vars += nombre + " ";
                            cad += nombre + " = " + spli[posini] + "\n";
                            posini += 1;
                            System.out.println("else if 2" + cad);
                            contadorvar++;
                        }
                    } else if (!(spli[posini].equals("then"))) {

                        if (!(spli[posini].equals("then")) && !(spli[posini + 1].equals("then"))) {
                            if (spli[posini].equals("and") && spli[posini].equals("or")) {
                                vars += spli[posini] + " ";
                            } else {

                                nombre = "t" + contadorvar;
                                cad += nombre + " = " + spli[posini] + spli[posini + 1] + spli[posini + 2] + "\n";
                                //cad += "L" + contador + ": if_false ";
                                posini += 3;
                                vars += nombre + " ";
                                contadorvar++;
                            }

                        } else if (spli[posini].equals("and") && spli[posini].equals("or")) {
                            vars += spli[posini] + " ";
                        } else {

                            nombre = "t" + contador;
                            vars += nombre + " ";
                            cad += nombre + " = " + spli[posini] + "\n";
                            posini += 1;
                            contadorvar++;
                        }

                        //cad += "L" + contador + ": if_false ";
                    }

                    //nombre=
                }
                vars = vars.replace("\\s+", " ");
                System.out.println("variables " + vars);
                //String[] vars2=vars.split(" ");
                cad += "L" + contador + ": if_false " + vars + " goto LF" + "\n";

                System.out.println("" + cad);
            } else {
                cad += "no traducido" + "\n";
            }

            System.out.println("" + x);
        }

    }
    
    public void analizarsentencias(String linea) {
        String x=linea;
        //String x = "if not #5 then{";
        //String x="for (int &var = #0;| &var <= #10 | &var = &var + #1; ){";
        //String x="while &i <= #5 then {";
        if(x.contains("fun")){
            int contador = 1;
            for (String s : tiposVariables) {
                x=x.replace(s, "");
            }
        }else if(x.contains("while")){
            int contador=1;
            for (String s : tiposVariables) {
                x=x.replace(s, "");
            }
            for (String s : findl) {
                x=x.replace(s,"");
            }
            for(String s:escapesdeleng){
                x=x.replace(s,"");
            }
            x=x.replace("while", "");
            if(x.indexOf(" " )== 0){
                x=x.replaceFirst(" ", "");
            }
            
            String[] spli=x.split(" ");
            int contadorvar=1;
            String cad="";
            int op=0;
            for (String s : spli) {
                if(operadoresLogicos.contains(s)){
                    op++;
                }
            }
            if(op>0){
                String nombre1="t"+contadorvar;
                cad+=nombre1+" = "+spli[0]+spli[1]+spli[2]+"\n";
                cad+="L"+contador+" if_false "+nombre1+" goto LF:\n";
            }else{

                cad+="L"+contador+" if_false "+spli[0]+" goto LF:\n";
            }    
            
            System.out.println(""+x);
            System.out.println(""+cad);
            code+=cad;
        }
        else if(x.contains("for")){
            int contador=1;
            for (String s : tiposVariables) {
                x=x.replace(s, "");
            }
            for (String s : findl) {
                x=x.replace(s,"");
            }
            for(String s:escapesdeleng){
                x=x.replace(s,"");
            }
            x=x.replace("|", "");
            x=x.replace("for", "");
            x=x.replace("\\s+","");
            x=x.replaceFirst("  ", "");
            x=x.replaceFirst("  ", " ");
            System.out.println(""+x);
            String[] spli=x.split(" ");
            for (String s : spli) {
                System.out.println(""+s);
            }
            String cad="";
            int contadorvar=1;
            String nombre1="t"+contadorvar;
            cad+=spli[0]+spli[1]+spli[2]+"\n";
            contadorvar++;
            String nombre2="t"+contadorvar;
            cad+=nombre2+" = "+spli[3]+spli[4]+spli[5]+"\n";
            contadorvar++;
            String nombre3="t"+contadorvar;
            cad+=nombre3+" = "+spli[6]+spli[9]+spli[10]+"\n";
            String temp=spli[8]+" = "+nombre3+"\n";
            cad+=temp;
            cad+="L"+contador+": if_false"+nombre2+" goto LF : \n"+nombre3+"\n"+temp+""+"goto L"+contador;
            System.out.println(""+cad);
            code+=cad;
        }
        else if (x.contains("if")) {
            int contador = 1;

            for (String s : operadoresLogicos) {
                x = x.replace(s, " " + s + " ");
            }
            for (String s : findl) {
                x = x.replace(s, " " + s);
            }
            for (String s : escapesdeleng) {
                x = x.replace(s, "");
            }

            String[] spli = x.split(" ");
            int nroop = 0;
            int posini = 1;
            for (Object s : spli) {
                System.out.println("" + s);
                for (String s2 : operadoresLogicos) {
                    if (s.equals(s2)) {
                        nroop++;
                    }
                }
            }

            String nombre;
            String nombreh;
            String cad = "";
            int contadorvar = 1;
            // int posini=1;
            if (nroop > 0) {
                for (int i = 0; i < nroop; i++) {
                    if (i == 0) {
                        if (spli[posini] != "not" && spli[posini + 1] != "then") {
                            nombre = "t" + contador;
                            cad += nombre + " = " + spli[posini] + spli[posini + 1] + spli[posini + 2] + "\n";
                            cad += "L" + contador + ": if_false " + nombre + " goto LF" + "\n";
                            contador++;
                            contadorvar++;
                            posini += 3;
                        } else if (spli[posini].equals("not")) {
                            nombre = "t" + contadorvar;
                            cad += nombre + " = " + spli[posini + 1] + spli[posini + 2] + spli[posini + 3] + "\n";
                            contadorvar++;
                            nombreh = nombre;
                            nombre = "t" + contadorvar;
                            cad += nombre + " = " + "!" + nombreh + "\n";
                            cad += "L" + contador + ": if_false " + nombre + " goto LF" + "\n";
                            contador++;
                            contadorvar++;
                            posini += 3;
                        }
                    }
                }
            } else if (spli[posini].equals("not")) {
                nombre = "t" + contadorvar;
                cad += nombre + " = " + "!" + spli[posini + 1] + "\n";
                cad += "L" + contador + ": if_false " + nombre + " goto LF" + "\n";
                contador++;
                contadorvar++;
            } else {
                //nombre = "t"+contadorvar;
                //cad+=nombre+" = "+"!"+spli[posini+1];
                cad += "L" + contador + ": if_false " + spli[posini] + " goto LF" + "\n";
                contador++;
                //contadorvar++;
            }

            System.out.println("" + cad);
            code+=cad;

        }
       
    }

    public void analizar(String linea) {
        //String x = "bl &x=true;";
        String x = linea;
        //String ev="";
        for (String tiposVariable : tiposVariables) {
            x = x.replaceAll(tiposVariable, "");
            x = x.replace(" ", "");
        }

        System.out.println("" + x);
        for (String s : operadoresnormales) {
            x = x.replace(s, " " + s + " ");
        }
        /*x = x.replace("+", " + ");
        x = x.replace("-", " - ");
        x = x.replace("=", " = ");
        x = x.replace(";", " ; ");*/

        for (String s : operadoresLogicos) {
            x = x.replace(s, " " + s + " ");
        }

        for (String s : escapesdeleng) {
            x = x.replace(s, "");
        }
        for (String s : findl) {
            x = x.replace(s, " " + s);
        }
        /*x = x.replaceAll("&", "");
        x = x.replaceAll("#", "");*/

        System.out.println("" + x);

        String[] sep = x.split(" ");
        int nop = 0;
        for (String s : sep) {
            System.out.println("" + s);
            /*for (String s2 : operadoresnormales) {
                if (s.equals(s2)) {
                    nop++;
                }
            }*/
            if (s.equals("+") || s.equals("-") || s.equals("*")||  s.equals("/")) {
                nop++;
            }
        }
        System.out.println("n opera:" + nop);
        String nombre = "";
        String nombreh = "";
        int contador = 1;
        int posini = 2;
        if (nop > 0) {
            for (int i = 0; i < nop; i++) {
                if (i == 0) {
                    nombre = "t" + contador;
                    code += nombre + " = " + sep[posini] + sep[posini + 1] + sep[posini + 2] + "\n";
                    posini += 3;
                    contador++;
                } else {
                    nombreh = nombre;
                    nombre = "t" + contador;
                    code += nombre + " = " + nombreh + sep[posini] + sep[posini + 1] + "\n";
                    posini += 2;
                    contador++;
                }
            }
            code += sep[0] + sep[1] + nombre + "\n";
        } else if (sep[1].equals("=")) {
            code += sep[0] + sep[1] + sep[2] + "\n";
        } else if (sep[1].equals(";")) {
            code += "read " + sep[0] + "\n";
        } else {
            code += "0x000000000a0s00";
        }

        //System.out.println("" + code);
    }

    public void cuadruplica(String op, String arg1, String arg2, String resultado) {
    }

}
