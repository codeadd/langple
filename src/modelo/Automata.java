/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cvem8
 */
public class Automata {

    private LinkedList<Estado> estados;
    private Estado inicial;
    private LinkedList<String> alfabeto;
    private String[][] matTrans; //asi se crea una matriz de 2 dimensiones
    private LinkedList<Estado> estFinales;
    private LinkedList<String> reservadas;
    private LinkedList<String> listatokens;
    private LinkedList<String> listaerrores;
    private LinkedList<String> listaliterales;
    String exp;
    String exent;
    String expflt;
    String expcad;

    public Automata() {
        estFinales = new LinkedList<>();
        estados = new LinkedList<>();
        alfabeto = new LinkedList<>();
        inicial = new Estado();
        reservadas = new LinkedList<>();
        reservadas.add("ENT");
        reservadas.add("FLOT");
        reservadas.add("BL");
        reservadas.add("CAD");
        reservadas.add("SI");
        reservadas.add("MTR");
        //reservadas.add("PARA");
        //reservadas.add("ENTC");
        reservadas.add("FUN");
        reservadas.add("SN");
        reservadas.add("O");
        reservadas.add("Y");
        reservadas.add("NO");
        reservadas.add("RET");
        reservadas.add("VACIO");

        exp = "[a-z|A-Z]([0-9]|[a-z|A-Z])*";
        exent = "[0-9]+";
        expflt = "([0-9]+\\.[0-9]+)+";
        expcad = "(.)+";

    }

    /**
     * @return the estados
     */
    public LinkedList<Estado> getEstados() {
        return estados;
    }

    /**
     * @param estados the estados to set
     */
    public void setEstados(LinkedList<Estado> estados) {
        this.estados = estados;
    }

    /**
     * @return the alfabeto
     */
    public LinkedList<String> getAlfabeto() {
        return alfabeto;
    }

    /**
     * @param alfabeto the alfabeto to set
     */
    public void setAlfabeto(LinkedList<String> alfabeto) {
        this.alfabeto = alfabeto;
    }

    /**
     * @return the matTrans
     */
    public String[][] getMatTrans() {
        return matTrans;
    }

    /**
     * @param matTrans the matTrans to set
     */
    public void setMatTrans(String[][] matTrans) {
        this.matTrans = matTrans;
    }

    /**
     * @return the estFinales
     */
    public LinkedList<Estado> getEstFinales() {
        return estFinales;
    }

    /**
     * @param estFinales the estFinales to set
     */
    public void setEstFinales(LinkedList<Estado> estFinales) {
        this.estFinales = estFinales;
    }

    public void AddState(String estado, Boolean inicial, Boolean fin) {

        if (GetState(estado) == null) // verificamos que el estado no este en el automata para agregarlo
        {
            Estado temp = new Estado(estado, inicial, fin);
            if (inicial) {
                this.setInicial(temp);
                System.out.println("El ESTAOD INICIAL ES " + this.inicial.getNombre());
            }
            estados.add(temp);
            if (fin) {
                estFinales.add(temp);
            }
        } else //sino esta 
        {
            System.out.println("El estado que intenta ingresar ya existe " + estado);
            //Console.ReadKey(true);

        }
        //Actualizamos la Matriz de transiciones

    }

    public void Add(String origen, String destino, String valor) {
        Boolean aceptado = false; //esta variable veriica si el alfabeto contiene esos valroes de la transicion

        Arista aristaTemp = new Arista(valor); //le asignamos el valor a la arista
        // es necesario el ToList() para sacar un copia de la lista y no copiar la misma
        Estado origenTemp = GetState(origen);
        Estado destinoTemp = GetState(destino);
        if (origenTemp != null && destinoTemp != null) {
            if (!origenTemp.Add(destinoTemp, aristaTemp)) { //sino existe
                System.out.println("El estado de origen " + origen + " con destino " + destino + " con valor -> " + valor + " se agrego exitosamente");
            }
            //Console.ReadKey(true);

        } else {
            if (origenTemp == null) {
                System.err.print("ERROR El estado de origen " + origen + " no se encuentra en el automata\n");
                //Console.ReadKey(true);

            }
            if (destinoTemp == null) {
                System.err.print("ERROR El estado de destino " + destino + " no se encuentra en el automata\n");
                //Console.ReadKey(true);
            }
        }
        //Generamos la Matriz de Transiciones

    }

    private Estado GetState(String estado) {
        for (Estado estado1 : estados) {
            if (estado1.getNombre().equals(estado)) {
                return estado1;
            }
        }
        return null;
    }

    public void PrintState(String nombre) {
        Estado temp = GetState(nombre);
        if (temp == null) {
            System.out.println("no se encontro");
        } else {
            temp.PrintTransitions();
        }
    }

    public LinkedList<String> Reconocer(String reco) {
        LinkedList<String> tokens = new LinkedList<>(); //Lista donde guardaremos los tokens 
        LinkedList<String> errores = new LinkedList<>(); //Lista donde guardaremos los tokens 
        LinkedList<String> literales = new LinkedList<>();//Lista de literales
        
        Boolean first = true;
        Estado temp, temp2;
        reco = reco.replaceAll("\\s+", " "); //quitamos todos los espacios en blaco y los cambiamos por uno solo
        reco = reco.replaceAll("\n+", "");
        System.out.println("Reco " + reco);
        /*for (int j = 0; j < reco.length(); j++) {
            cola.add("" + reco.charAt(j));
            lista2.add("" + reco.charAt(j));
        }
         */
        temp = inicial;
        String val;
        for (int i = 0; i < reco.length(); i++) {
            val = "" + reco.charAt(i);
            System.out.println("Iteracion #" + i + " con valor de cadena " + val);
            System.out.println("VALOR DE TEMP " + temp.getNombre());
            temp2 = temp.GetTransicion(val);
            if (temp2 != null) {
                temp = temp2;
                if (i + 1 == reco.length()) { //si ya es el ultimo token entonces guardelo
                    tokens.add(temp.getNombre());
                    if (temp.getNombre().contains("ERR")) {
                        errores.add(temp.getNombre());
                    }
                }
            } else {
                System.out.println("Temporal 2 es null");
                if (!temp.getNombre().equals("Inicio")) { // no debemos guardar cuando regrese a inicio
                    System.out.println("Estado a guardar" + temp.getNombre());

                    if (reservadas.contains(temp.getNombre())) {
                        System.out.println("<<<<<<<<<<<<<<<<< pertenece a las reser");
                        if (!val.equals(" ")) {
                            try {
                                String tempEspacio = "" + reco.charAt(i + 1);
                                if (!tempEspacio.equals(" ")) {
                                    int j = i + 1;
                                    while (!" ".equals("" + reco.charAt(j))) {
                                        j++;
                                    }
                                    i = j;

                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("SE DESBORDO");
                                i = reco.length();
                                tokens.add("ERR_ESPACIO");
                                errores.add("ERR_ESPACIO");

                            }
                        }

                    }

                    if (temp.getNombre().equals("ERR_ID")) {
                        System.out.println("%%%%%%%%%%%%%%%%%%%%       Estoy en error id");
                        Pattern p = Pattern.compile(exp);
                        //Matcher m = p.matcher(val);
                        String cade = "";

                        try {

                            int j = i;
                            while (!" ".equals("" + reco.charAt(j)) && !";".equals("" + reco.charAt(j)) && !"=".equals("" + reco.charAt(j)) && !",".equals("" + reco.charAt(j)) && !")".equals("" + reco.charAt(j))  && !"(".equals("" + reco.charAt(j))) {
                                cade += reco.charAt(j);
                                j++;
                            }
                            i = j - 1;
                            literales.add("&"+cade);
                            Matcher m = p.matcher(cade);
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  " + cade);
                            while (m.find()) {
                                System.out.println("\n#########################    " + m.group());
                                tokens.add("ID");
                            }

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("SE DESBORDO");

                        }
                        val = "";
                    } else if (temp.getNombre().equals("ERR_ENTL")) {
                        System.out.println("%%%%%%%%%%%%%%%%%%%%       Estoy en error entero");
                        Pattern p = Pattern.compile(exent);
                        //Matcher m = p.matcher(val);
                        String cade = "";
                        try {

                            int j = i;
                            while (!" ".equals("" + reco.charAt(j)) && !";".equals("" + reco.charAt(j)) && !"=".equals("" + reco.charAt(j)) && !")".equals("" + reco.charAt(j))  && !"(".equals("" + reco.charAt(j))) {
                                cade += reco.charAt(j);
                                j++;
                            }
                            i = j - 1;
                            literales.add("#"+cade);
                            Matcher m = p.matcher(cade);
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  " + cade);
                            while (m.find()) {
                                System.out.println("\n#########################    " + m.group());
                                tokens.add("ENTL");
                            }

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("SE DESBORDO");

                        }
                        val = "";    
                    } else if (temp.getNombre().equals("ERR_FLTL")) {
                        System.out.println("%%%%%%%%%%%%%%%%%%%%       Estoy en error flotante");
                        Pattern p = Pattern.compile(expflt);
                        //Matcher m = p.matcher(val);
                        String cade = "";
                        try {

                            int j = i;
                            while (!" ".equals("" + reco.charAt(j)) && !";".equals("" + reco.charAt(j)) && !"=".equals("" + reco.charAt(j)) && !")".equals("" + reco.charAt(j))  && !"(".equals("" + reco.charAt(j))) {
                                cade += reco.charAt(j);
                                j++;
                            }
                            i = j - 1;
                            literales.add("$"+cade);
                            Matcher m = p.matcher(cade);
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  " + cade);
                            while (m.find()) {
                                System.out.println("\n#########################    " + m.group());
                                tokens.add("FLTL");
                            }

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("SE DESBORDO");

                        }
                        val = "";
                    } else if (temp.getNombre().equals("ERR_CADL")) {
                        System.out.println("%%%%%%%%%%%%%%%%%%%%       Estoy en error cadena");
                        Pattern p = Pattern.compile(expcad);
                        //Matcher m = p.matcher(val);
                        String cade = "";
                        try {

                            int j = i;
                            while (!"\"".equals("" + reco.charAt(j))) {
                                cade += reco.charAt(j);
                                j++;
                            }
                            i = j;
                            literales.add("\""+cade+"\"");
                            Matcher m = p.matcher(cade);
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  " + cade);
                            if (m.find()) {
                                System.out.println("\n#########################    " + m.group());
                                tokens.add("CADL");
                            } else if (cade.equals("")) {
                                tokens.add("CADL");
                            }

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("SE DESBORDO");

                        }
                        val = "";
                    }else if(temp.getNombre().equals("COMTL")){
                        
                        String cade="";
                        Boolean accept=false;
                        try {
                            
                            int j = i;
                            while (!("/".equals("" + reco.charAt(j)) && "/".equals("" + reco.charAt(j+1))) ) {
                                j++;
                                if(j+1 == reco.length()){
                                    break;
                                }
                                
                                if(("/".equals("" + reco.charAt(j)) && "/".equals("" + reco.charAt(j+1)))){
                                    accept = true;
                                }
                                if("/".equals("" + reco.charAt(j))){
                                   if(!"/".equals("" + reco.charAt(j+1))){
                                       break;
                                   } 
                                }
                                
                            }
                            if(accept){
                                tokens.add("COMTL");
                            }else{
                                tokens.add("ERR_COMTL");
                            }
                            
                            i = j+1;

                            

                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("SE DESBORDO");

                        }
                    
                        val="";
                    }
                    else {
                        if (temp.getNombre().contains("ERR")) {  //ADICIONAMOS A LOS TOKENS DE ERROR
                            errores.add(temp.getNombre());
                        }
                        tokens.add(temp.getNombre());
                    }

                }

                temp = inicial;  // volvemos a inicial y probamos si tiene transicion por ese valor
                temp2 = temp.GetTransicion(val);
                if (temp2 != null) { //si la tiene entonces movemos a temp a donde quedo temp2
                    temp = temp2;
                    if (i + 1 == reco.length()) { //si ya es el ultimo token entonces guardelo
                        if (temp.getNombre().contains("ERR")) {
                            errores.add(temp.getNombre());
                        }
                        tokens.add(temp.getNombre());
                    }

                }
                

            }

        }
        tokens.forEach((String t) -> {
            System.out.print(t + " ");
        });
        errores.forEach((String t) -> {
            System.out.print(t + " ");
        });
        System.out.println("");
        
        this.listatokens=new LinkedList<>(tokens);
        this.listaerrores=new LinkedList<>(errores);
        this.listaliterales=new LinkedList<>(literales);
        
        return tokens;
    }

    /**
     * @return the inicial
     */
    public Estado getInicial() {
        return inicial;
    }

    /**
     * @param inicial the inicial to set
     */
    public void setInicial(Estado inicial) {
        this.inicial = inicial;
    }

    /**
     * @return the listatokens
     */
    public LinkedList<String> getListatokens() {
        return listatokens;
    }

    /**
     * @param listatokens the listatokens to set
     */
    public void setListatokens(LinkedList<String> listatokens) {
        this.listatokens = listatokens;
    }

    /**
     * @return the listaerrores
     */
    public LinkedList<String> getListaerrores() {
        return listaerrores;
    }

    /**
     * @param listaerrores the listaerrores to set
     */
    public void setListaerrores(LinkedList<String> listaerrores) {
        this.listaerrores = listaerrores;
    }

    /**
     * @return the listaliterales
     */
    public LinkedList<String> getListaliterales() {
        return listaliterales;
    }

    /**
     * @param listaliterales the listaliterales to set
     */
    public void setListaliterales(LinkedList<String> listaliterales) {
        this.listaliterales = listaliterales;
    }

}
