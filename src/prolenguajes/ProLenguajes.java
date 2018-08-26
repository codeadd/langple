/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prolenguajes;

import Modelo.Automata;
import Modelo.Generador;
import Modelo.Generador2;
import Modelo.Gramatica;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author cvem8
 */
public class ProLenguajes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        Automata Lenguajes = new Automata();
        Lenguajes.AddState("Inicio",true, false);
        Lenguajes.AddState("DIF", false, true);
        Lenguajes.AddState("ERR_DIF", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("MN", false, true);
        Lenguajes.AddState("MEOG", false, true);
        Lenguajes.AddState("MAY",false, true);
        Lenguajes.AddState("MAOG", false, true);
        Lenguajes.AddState("FL", false, true);
        Lenguajes.AddState("ERR_BL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("BL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("PA", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("PC", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("LC", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("LA", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("FLOL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("BLL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_TRUE", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_TRUE2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_TRUE3", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FALSE", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FALSE2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FALSE3", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FALSE4", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ENTL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("CADL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("IG", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("IG2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("SUM", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("RES", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("DIV", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("POR", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("COMTL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_ENTL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_CADL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_NOT", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_NOT2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("NO", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_AND", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_AND2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("Y", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_OR", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("O", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_WHILE", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_WHILE2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_WHILE3", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_WHILE4", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("MTR", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_THEN", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_THEN2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_THEN3", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ENTC", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_ELSE", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_ELSE2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_ELSE3", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("SN", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_F", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FLT", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("FLO", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FLTL", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FOR", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("PARA", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_FUN", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("FUN", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_STR", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_STR2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("CAD", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_IF", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("SI", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_INT", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ENT", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ID", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_ID", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_SAY", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("DIGA", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_VOID", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_VOID2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_VOID3", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("VACIO", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_RET", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("ERR_RET2", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("RET", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("COM", Boolean.FALSE, Boolean.TRUE);
        Lenguajes.AddState("PIPE", Boolean.FALSE, Boolean.TRUE);

        /*###################################
          #         TRANSICIONES            #
          ###################################

        //Las que posean x son las expresiones regulares
        Lenguajes.Add("Inicio", "ERR_ID", "&");
        Lenguajes.Add("ERR_ID", "ID", "[a-z|A-Z]([0-9]|[a-z|A-Z])+");
        Lenguajes.Add("Inicio", "ERR_DIF", "!");
        Lenguajes.Add("ERR_DIF", "DIF", "=");
        Lenguajes.Add("Inicio", "MN", "<");
        Lenguajes.Add("MN", "MEOG", "=");
        Lenguajes.Add("Inicio", "MAY", ">");
        Lenguajes.Add("MAY", "MAOG", "=");
        Lenguajes.Add("Inicio", "FL", ";");
        Lenguajes.Add("Inicio", "PA", "(");
        Lenguajes.Add("Inicio", "PC", ")");
        Lenguajes.Add("Inicio", "LC", "}");
        Lenguajes.Add("Inicio", "LA", "{");
        Lenguajes.Add("Inicio", "ERR_FLTL", "$");
        Lenguajes.Add("ERR_FLTL", "FLOL", "([0-9]+\\.[0-9]+)+");
        Lenguajes.Add("Inicio", "ERR_TRUE", "T");
        Lenguajes.Add("ERR_TRUE", "ERR_TRUE2", "r");
        Lenguajes.Add("ERR_TRUE2", "ERR_TRUE3", "u");
        Lenguajes.Add("ERR_TRUE3", "BLL", "e");
        Lenguajes.Add("Inicio", "ERR_FALSE", "F");
        Lenguajes.Add("ERR_FALSE", "ERR_FALSE2", "a");
        Lenguajes.Add("ERR_FALSE2", "ERR_FALSE3", "l");
        Lenguajes.Add("ERR_FALSE3", "ERR_FALSE4", "s");
        Lenguajes.Add("ERR_FALSE4", "BLL", "e");
        Lenguajes.Add("Inicio", "ERR_ENTL", "#");
        Lenguajes.Add("ERR_ENTL", "ENTL", "[0-9]+");
        Lenguajes.Add("Inicio", "ERR_CADL", "\"");
        Lenguajes.Add("ERR_CADL", "CADL", "([0-9]|[a-z|A-Z])*");
        Lenguajes.Add("Inicio", "IG", "=");
        Lenguajes.Add("IG", "IG2", "=");
        Lenguajes.Add("Inicio", "SUM", "+");
        Lenguajes.Add("Inicio", "RES", "-");
        Lenguajes.Add("Inicio", "DIV", "/");
        Lenguajes.Add("DIV", "COMTL", "/");
        Lenguajes.Add("Inicio", "POR", "*");
        Lenguajes.Add("Inicio", "ERR_NOT", "n");
        Lenguajes.Add("ERR_NOT", "ERR_NOT2", "o");
        Lenguajes.Add("ERR_NOT2", "NO", "t");
        Lenguajes.Add("Inicio", "ERR_AND", "a");
        Lenguajes.Add("ERR_AND", "ERR_AND2", "n");
        Lenguajes.Add("ERR_AND2", "Y", "d");
        Lenguajes.Add("Inicio", "ERR_OR", "o");
        Lenguajes.Add("ERR_OR", "O", "r");
        Lenguajes.Add("Inicio", "ERR_WHILE", "w");
        Lenguajes.Add("ERR_WHILE", "ERR_WHILE2", "h");
        Lenguajes.Add("ERR_WHILE2", "ERR_WHILE3", "i");
        Lenguajes.Add("ERR_WHILE3", "ERR_WHILE4", "l");
        Lenguajes.Add("ERR_WHILE4", "MTR", "e");
        Lenguajes.Add("Inicio", "ERR_THEN", "t");
        Lenguajes.Add("ERR_THEN", "ERR_THEN2", "h");
        Lenguajes.Add("ERR_THEN2", "ERR_THEN3", "e");
        Lenguajes.Add("ERR_THEN3", "ENTC", "n");
        Lenguajes.Add("Inicio", "ERR_ELSE", "e");
        Lenguajes.Add("ERR_ELSE", "ERR_ELSE2", "l");
        Lenguajes.Add("ERR_ELSE2", "ERR_ELSE3", "s");
        Lenguajes.Add("ERR_ELSE3", "SN", "e");
        Lenguajes.Add("Inicio", "ERR_F", "f");
        Lenguajes.Add("ERR_F", "ERR_FLT", "l");
        Lenguajes.Add("ERR_FLT", "FLO", "t");
        Lenguajes.Add("ERR_F", "ERR_FOR", "o");
        Lenguajes.Add("ERR_FOR", "PARA", "r");
        Lenguajes.Add("ERR_F", "ERR_FUN", "u");
        Lenguajes.Add("ERR_FUN", "FUN", "n");
        Lenguajes.Add("Inicio", "ERR_STR", "s");
        Lenguajes.Add("ERR_STR", "ERR_STR2", "t");
        Lenguajes.Add("ERR_STR2", "CAD", "r");
        Lenguajes.Add("Inicio", "ERR_IF", "i");
        Lenguajes.Add("ERR_IF", "SI", "f");
        Lenguajes.Add("ERR_IF", "ERR_INT", "n");
        Lenguajes.Add("ERR_INT", "ENT", "t");
        Lenguajes.Add("Inicio", "ERR_BL", "b");
        Lenguajes.Add("ERR_BL", "BL", "l");
        Lenguajes.Add("ERR_STR", "ERR_SAY", "a");
        Lenguajes.Add("ERR_SAY", "DIGA", "y");
        Lenguajes.Add("Inicio", "ERR_VOID", "v");
        Lenguajes.Add("ERR_VOID", "ERR_VOID2", "o");
        Lenguajes.Add("ERR_VOID2", "ERR_VOID3", "i");
        Lenguajes.Add("ERR_VOID3", "VACIO", "d");
        Lenguajes.Add("Inicio", "ERR_RET", "r");
        Lenguajes.Add("ERR_RET", "ERR_RET2", "e");
        Lenguajes.Add("ERR_RET2", "RET", "t");
        Lenguajes.Add("Inicio", "COM", ",");
        Lenguajes.Add("Inicio", "PIPE", "|");


        Gramatica g = new Gramatica();

        g.getSimbolos().add("LENG");
        g.getSimbolos().add("BLOQUE");
        g.getSimbolos().add("ASIGNACION");
        g.getSimbolos().add("TIPODATO");
        g.getSimbolos().add("OPERACION");
        g.getSimbolos().add("OPERADOR");

        g.getSimbolos().add("IF");
        g.getSimbolos().add("C");
        g.getSimbolos().add("E");
        g.getSimbolos().add("G");
        g.getSimbolos().add("B");
        g.getSimbolos().add("H");
        g.getSimbolos().add("WH");

        g.getSimbolos().add("SA");

        g.getSimbolos().add("NT");

        g.getSimbolos().add("CM");

        g.getSimbolos().add("FUNC");
        g.getSimbolos().add("DEC");
        g.getSimbolos().add("DEC2");
        g.getSimbolos().add("IDF");

        ;
        g.getSimbolos().add("COMA");

        g.getSimbolos().add("FR");


        g.getProduccion().put("LENG",  new LinkedList<>(Arrays.asList("FUNC BLOQUE")));
        g.getProduccion().put("BLOQUE",  new LinkedList<>(Arrays.asList("ASIGNACION","IF","WH","FR","CM","SA","€")));
        g.getProduccion().put("ASIGNACION", new LinkedList<>(Arrays.asList("TIPODATO ID IG OPERACION FL BLOQUE","ID IG OPERACION FL BLOQUE")));
        g.getProduccion().put("TIPODATO", new LinkedList<>(Arrays.asList("ENT","FLO","CAD","BL","VACIO")));
        g.getProduccion().put("OPERADOR", new LinkedList<>(Arrays.asList("SUM", "RES", "POR", "DIV","€")));
        g.getProduccion().put("OPERACION", new LinkedList<>(Arrays.asList("ENTL OPERADOR OPERACION", "FLTL OPERADOR OPERACION","IDF OPERADOR OPERACION", "CADL OPERADOR OPERACION", "BLL OPERADOR OPERACION", "€")));

        g.getProduccion().put("IF", new LinkedList<>(Arrays.asList("SI B C E")));
        g.getProduccion().put("C", new LinkedList<>(Arrays.asList("Y B","O B","€")));
        g.getProduccion().put("E", new LinkedList<>(Arrays.asList("ENTC LA BLOQUE LC G BLOQUE")));
        g.getProduccion().put("G", new LinkedList<>(Arrays.asList("SN LA BLOQUE LC","€")));
        g.getProduccion().put("B", new LinkedList<>(Arrays.asList("NT OPERACION H OPERACION")));
        g.getProduccion().put("H", new LinkedList<>(Arrays.asList("IG2","MAY","MAOG","MEOG","DIF","MN")));
        g.getProduccion().put("NT", new LinkedList<>(Arrays.asList("NO","€")));

        g.getProduccion().put("IDF", new LinkedList<>(Arrays.asList("ID","FUN ID PA DEC2 PC")));

        //While
        g.getProduccion().put("WH", new LinkedList<>(Arrays.asList("MTR B ENTC LA BLOQUE LC BLOQUE")));

        g.getProduccion().put("CM", new LinkedList<>(Arrays.asList("COMTL BLOQUE")));

        g.getProduccion().put("SA", new LinkedList<>(Arrays.asList("DIGA PA OPERACION PC FL BLOQUE")));

        //Funciones
        g.getProduccion().put("FUNC", new LinkedList<>(Arrays.asList("FUN TIPODATO ID PA DEC PC LA BLOQUE RET OPERACION FL LC FUNC","€")));
        g.getProduccion().put("DEC", new LinkedList<>(Arrays.asList("TIPODATO ID COMA DEC","€")));

        g.getProduccion().put("DEC2", new LinkedList<>(Arrays.asList("ID COMA DEC2","€")));

        g.getProduccion().put("COMA", new LinkedList<>(Arrays.asList("COM","€")));

        //For
        g.getProduccion().put("FR", new LinkedList<>(Arrays.asList("PARA PA ASIGNACION PIPE B PIPE ASIGNACION PC LA BLOQUE LC BLOQUE")));
        //  for(&x = 5; | &x < 5 | &x = &x +10;){ }



        Gramatica g2=new Gramatica();
        g2.getSimbolos().add("A");
        g2.getSimbolos().add("B");
        g2.getSimbolos().add("C");
        g2.getSimbolos().add("D");

        g2.getProduccion().put("A", new LinkedList<>(Arrays.asList("B C D")));
        g2.getProduccion().put("B", new LinkedList<>(Arrays.asList("a C b","€")));
        g2.getProduccion().put("C", new LinkedList<>(Arrays.asList("c A d","e B f","g D h","€")));
        g2.getProduccion().put("D", new LinkedList<>(Arrays.asList("i")));

        Gramatica g3=new Gramatica();
        g3.getSimbolos().add("S");
        g3.getSimbolos().add("A");
        g3.getSimbolos().add("B");
        g3.getSimbolos().add("C");

        g3.getProduccion().put("S", new LinkedList<>(Arrays.asList("A B")));
        g3.getProduccion().put("A", new LinkedList<>(Arrays.asList("a","€")));
        g3.getProduccion().put("B", new LinkedList<>(Arrays.asList("b C d")));
        g3.getProduccion().put("C", new LinkedList<>(Arrays.asList("c","€")));

        Gramatica g4=new Gramatica();
        g4.getSimbolos().add("S");
        g4.getSimbolos().add("A");
        g4.getSimbolos().add("L");
        g4.getSimbolos().add("E");


        g4.getProduccion().put("S", new LinkedList<>(Arrays.asList("A","a")));
        g4.getProduccion().put("A", new LinkedList<>(Arrays.asList("b ( E ) S L")));
        g4.getProduccion().put("L", new LinkedList<>(Arrays.asList("c S","€")));
        g4.getProduccion().put("E", new LinkedList<>(Arrays.asList("0","1")));

        Gramatica chucho=new Gramatica();
        chucho.getSimbolos().add("X");
        chucho.getSimbolos().add("Y");
        chucho.getSimbolos().add("E");
        chucho.getSimbolos().add("A");
        chucho.getSimbolos().add("B");
        chucho.getSimbolos().add("O");
        chucho.getSimbolos().add("N");

        chucho.getProduccion().put("X", new LinkedList<>(Arrays.asList("if ( B ) E Y")));
        chucho.getProduccion().put("Y", new LinkedList<>(Arrays.asList("else E","€")));
        chucho.getProduccion().put("E", new LinkedList<>(Arrays.asList("X","A")));
        chucho.getProduccion().put("A", new LinkedList<>(Arrays.asList("id = O")));
        chucho.getProduccion().put("B", new LinkedList<>(Arrays.asList("false","true","id == entero")));
        chucho.getProduccion().put("O", new LinkedList<>(Arrays.asList("entero N")));
        chucho.getProduccion().put("N", new LinkedList<>(Arrays.asList("+ O","€")));


        Gramatica g5 = new Gramatica();
        g5.getSimbolos().add("S");
        g5.getSimbolos().add("S2");
        g5.getSimbolos().add("Q");
        g5.getSimbolos().add("Q2");
        g5.getSimbolos().add("R");
        g5.getSimbolos().add("F");

        g5.getProduccion().put("S", new LinkedList<>(Arrays.asList("Q S2")));
        g5.getProduccion().put("S2", new LinkedList<>(Arrays.asList("or Q S2","€")));
        g5.getProduccion().put("Q", new LinkedList<>(Arrays.asList("R Q2")));
        g5.getProduccion().put("Q2", new LinkedList<>(Arrays.asList("R Q2","€")));
        g5.getProduccion().put("R", new LinkedList<>(Arrays.asList("F and","x","y")));
        g5.getProduccion().put("F", new LinkedList<>(Arrays.asList("z")));

        HashMap<String,LinkedList<String>> first = g5.follow();

        for (Map.Entry<String, LinkedList<String>> entry : first.entrySet()) {
            System.out.println("SIGUIENTES de : --> "+entry.getKey());

            for (String arg : entry.getValue()) {
                System.out.print(" "+arg);
            }
            System.out.println("");

        }
        g5.tabla();
        //chucho.reconocer("if ( id == entero ) id = entero else id = entero + entero + entero");
        //g3.reconocer("a b c d");
        g5.reconocer("z and or z and or z and or y x");

        //g.reconocer("");*/
        Generador2 g=new Generador2();
        Generador g2=new Generador();
        //g.leerArchivo("src/ejemplo(lenguajes).incg");
        g.analizar("src/vars.incg");
        System.out.println(""+g.imprimir2());
        g.gaurdarCodigo("src/vars.comp");
        //g2.leerArchivo("src/vars.incg");
        //g.analizarsentencias("");
        //g.analizar("str &cad=\"hola_mundo\";");
    }
    
}
