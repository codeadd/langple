/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * @author cvem8165
 */
public class Gramatica {

    private LinkedList<String> simbolos;
    private HashMap<String, LinkedList<String>> produccion;
    private HashMap<String, LinkedList<String>> primeros;
    private HashMap<String, LinkedList<String>> siguientes;
    private String[][] tabla;
    NaryTree arbolContexto;
    LinkedList<String> terminales;
    NaryTree arbol_derivacion;
    LinkedList<String> tiposVariables = new LinkedList<>(Arrays.asList("int","str","bl","flt"));
    LinkedList<String> sentenciasControl = new LinkedList<>(Arrays.asList("if","else","while","for"));
    private Boolean errores;
    public Gramatica() {
        simbolos = new LinkedList<String>();
        produccion = new HashMap<String, LinkedList<String>>();
        arbol_derivacion = new NaryTree();
    }

    public Gramatica(LinkedList<String> simbolos, HashMap<String, LinkedList<String>> produccion, NaryTree arbol_derivacion) {
        this.simbolos = simbolos;
        this.produccion = produccion;
        this.arbol_derivacion = arbol_derivacion;
    }

    /*#################################
            PRIMEROS
      ################################
     */
    public HashMap<String, LinkedList<String>> first() {
        HashMap<String, LinkedList<String>> primero = new HashMap<>();

        for (String s : simbolos) {
            primero.put(s, new LinkedList<String>());
        }

        for (Map.Entry<String, LinkedList<String>> entry : produccion.entrySet()) {

            for (String s : entry.getValue()) {
                String[] partida = s.split(" ");
                if (partida[0].equals("€")) {
                    /*
                    Si el primer simbolo del a proudccion mas a la izquierda es vacio, quiere decir que el primero de vacio
                    es vacio, pero tambien tendremos que aplicar la regla que dice que si el primero de a1 contiene vacio debemos
                    sacar los primeros de todos los simbolos siguientes y unirlos entre en si.
                    
                    Si a1 es vacio y a la vez es el ultimo simbolo de esa produccion entonces pues sacariamos los primeros de los simbolos que siguen
                    pero como tiene mas simbolos que le sigan y el seria el ultimo entonces quedaria siendo como al comienzo, el primero de vacio seria vacio
                     */
                    LinkedList<String> tempV;  // llamamos a firstv y guardamos en la lista
                    tempV = firstV(partida);
                    for (String string : tempV) {
                        if (!primero.get(entry.getKey()).contains(string)) {
                            primero.get(entry.getKey()).add(string);
                        }
                    }

                    /*if(!primero.get(entry.getKey()).contains("€")){
                        //primero.get(entry.getKey()).add("€");
                        
                        
                    }*/
                } else if (!simbolos.contains(partida[0])) {
                    if (!primero.get(entry.getKey()).contains(partida[0])) {
                        primero.get(entry.getKey()).add(partida[0]);
                    }
                } else {
                    LinkedList<String> temp = firstI(partida[0]);
                    if (temp.contains("€")) {
                        temp = firstV(partida);
                    }
                    for (String string : temp) {
                        if (!primero.get(entry.getKey()).contains(string)) {
                            primero.get(entry.getKey()).add(string);
                        }
                    }
                }
            }
        }

        return primero;
    }

    private LinkedList<String> firstI(String prod) {

        LinkedList<String> entry = produccion.get(prod);
        LinkedList<String> primero = new LinkedList<>();
        if (entry == null) {
            System.out.println("----------------" + prod);
            //como es un simbolo terminal, entonces el primero de un terminal es si mismo;
            if (!simbolos.contains(prod)) {
                if (!primero.contains(prod)) {  //veriifcamos si primero ya contiene ese simbolo, sino entonces lo agregamos
                    primero.add(prod);
                }

            }
        } else {
            for (String s : entry) {
                String[] partida = s.split(" ");
                if (partida[0].equals("€")) {
                    LinkedList<String> tempV;
                    tempV = firstV(partida);
                    for (String string : tempV) {
                        if (!primero.contains(string)) {
                            primero.add(string);
                        }
                    }

                } else if (!simbolos.contains(partida[0])) {
                    if (!primero.contains(partida[0])) {
                        primero.add(partida[0]);
                    }
                } else {

                    LinkedList<String> temp = firstI(partida[0]);

                    if (temp.size() > 0) {
                        for (String s2 : temp) {
                            if (!primero.contains(s2)) {
                                primero.add(s2);
                            }
                        }
                    }
                }
            }
        }

        return primero;
    }

    /**
     * Cuando los primeros del simbolo inicial contienen vacio, entonces
     * calculamos nuevamente los primeros basados en la penultima regla
     *
     * @param partida
     * @return
     */
    private LinkedList<String> firstV(String[] partida) {  //CUando los primeros de esa produccion continen vacio entonces calcuamos los nuevos primeros que  seria la uni
        LinkedList<String> primeros = new LinkedList<>();
        for (int i = 0; i < partida.length; i++) {
            LinkedList<String> temp = firstI(partida[i]);
            if (i != partida.length - 1) {
                if (temp.contains("€")) {
                    temp.remove("€");
                }
                for (String string : temp) {
                    if (!primeros.contains(string)) {
                        primeros.add(string);
                    }

                }
            } else {
                for (String string : temp) {
                    if (!primeros.contains(string)) {
                        primeros.add(string);
                    }
                }
            }

        }

        return primeros;
    }

    private LinkedList<String> firstU(String pro) {
        LinkedList<String> primero = new LinkedList<>();

        String[] partida = pro.split(" ");
        if (partida[0].equals("€")) {
            /*
                    Si el primer simbolo del a proudccion mas a la izquierda es vacio, quiere decir que el primero de vacio
                    es vacio, pero tambien tendremos que aplicar la regla que dice que si el primero de a1 contiene vacio debemos
                    sacar los primeros de todos los simbolos siguientes y unirlos entre en si.
                    
                    Si a1 es vacio y a la vez es el ultimo simbolo de esa produccion entonces pues sacariamos los primeros de los simbolos que siguen
                    pero como tiene mas simbolos que le sigan y el seria el ultimo entonces quedaria siendo como al comienzo, el primero de vacio seria vacio
             */
            LinkedList<String> tempV;  // llamamos a firstv y guardamos en la lista
            tempV = firstV(partida);
            for (String string : tempV) {
                if (!primero.contains(string)) {
                    primero.add(string);
                }
            }

            /*if(!primero.get(entry.getKey()).contains("€")){
                        //primero.get(entry.getKey()).add("€");
                        
                        
                    }*/
        } else if (!simbolos.contains(partida[0])) {
            if (!primero.contains(partida[0])) {
                primero.add(partida[0]);
            }
        } else {
            LinkedList<String> temp = firstI(partida[0]);
            if (temp.contains("€")) {
                temp = firstV(partida);
            }
            for (String string : temp) {
                if (!primero.contains(string)) {
                    primero.add(string);
                }
            }
        }

        return primero;
    }

    /*#################################
            SIGUIENTES
      ################################
     */

    public HashMap<String, LinkedList<String>> follow() { //Para entender el algoritmo seguir paso a paso el del libro
        HashMap<String, LinkedList<String>> siguiente = new HashMap<>();
        primeros = first();
        for (String s : simbolos) {
            siguiente.put(s, new LinkedList<>());
        }

        LinkedList<String> inicial = siguiente.get(simbolos.get(0));
        inicial.add("$");

        for (String simbolo : simbolos) {
            for (Map.Entry<String, LinkedList<String>> entry : produccion.entrySet()) {

                for (String valor : entry.getValue()) {
                    Boolean contains = false; //variable que nos dira si la produccion contiene a un simbolo especifico

                    String[] partida = valor.split(" ");
                    for (int i = 0; i < partida.length; i++) {
                        if (partida[i].equals(simbolo)) {
                            contains = true;
                            break;
                        }
                    }

                    //Es necesario hacer lo anterior ya que pueden haber simbolos no temrinales compuestos es decir SE, TEP; no solo S, o T
                    if (contains) {
                        for (int i = 0; i < partida.length; i++) {
                            if (partida[i].equals(simbolo)) {
                                if (!(i == partida.length - 1)) {
                                    String t = partida[i + 1];
                                    if (!simbolos.contains(t)) {
                                        if (!siguiente.get(simbolo).contains(t)) {
                                            siguiente.get(simbolo).add(t);
                                        }
                                    } else {
                                        LinkedList<String> f = primeros.get(t);
                                        for (String s : f) {
                                            if (!siguiente.get(simbolo).contains(s)) {
                                                if (!s.equals("€")) {
                                                    siguiente.get(simbolo).add(s);
                                                }

                                            }
                                        }
                                        if (f.contains("€")) {
                                            //f.remove("€");
                                            LinkedList<String> fol = follow(entry.getKey(), siguiente);
                                            if (fol.size() > 0) {  //Si se ecntonraron siguientes
                                                for (String sf : fol) {
                                                    if (!siguiente.get(simbolo).contains(sf)) {
                                                        siguiente.get(simbolo).add(sf);
                                                    }
                                                }
                                            } else {  // sino encontramos siguientes entonces buscamos el siguiente en la tabla que tenemos, esto suele
                                                //ocurrir cuando la produccion es la inciial y no aparece en las demas producciones
                                                fol = siguiente.get(entry.getKey());
                                                for (String sf : fol) {
                                                    if (!siguiente.get(simbolo).contains(sf)) {
                                                        siguiente.get(simbolo).add(sf);
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    LinkedList<String> fol = follow(entry.getKey(), siguiente);
                                    if (fol.size() > 0) {  //Si se ecntonraron siguientes
                                        for (String sf : fol) {
                                            if (!siguiente.get(simbolo).contains(sf)) {
                                                siguiente.get(simbolo).add(sf);
                                            }
                                        }
                                    } else {  // sino encontramos siguientes entonces buscamos el siguiente en la tabla que tenemos
                                        fol = siguiente.get(entry.getKey());
                                        for (String sf : fol) {
                                            if (!siguiente.get(simbolo).contains(sf)) {
                                                siguiente.get(simbolo).add(sf);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        siguientes = siguiente;
        return siguiente;
    }

    private LinkedList<String> follow(String simbolo, HashMap<String, LinkedList<String>> sig) {

        System.out.println(">>>>>>>>>> LLAMAMOS AL RECURSIVO POR EL SIMBOLO " + simbolo);
        LinkedList<String> siguiente = new LinkedList<>();
        for (Map.Entry<String, LinkedList<String>> entry : produccion.entrySet()) {


            for (String valor : entry.getValue()) {
                Boolean contains = false;  //variable que nos dira si el simbolo se encuentra en esa produccion
                String[] partida = valor.split(" ");
                for (int i = 0; i < partida.length; i++) {
                    if (partida[i].equals(simbolo)) {
                        if (!contains) {
                            contains = true;
                            break;
                        }


                    }
                }
                Boolean contin = false; // si salta esa iteracion o no
                if (contains) {
                    for (int i = 0; i < partida.length; i++) {
                        if (partida[i].equals(simbolo)) {
                            if (i + 1 == partida.length) {
                                if (entry.getKey().equals(simbolo)) {
                                    contin = true;
                                }
                            }
                        }
                    }
                    if (contin) {
                        continue;
                    }
                }


                //Es necesario hacer lo anterior ya que pueden haber simbolos no temrinales compuestos es decir SE, TEP; no solo S, o T
                if (contains) {
                    System.out.println("Simbolo " + simbolo + " Produccion " + valor);
                    for (int i = 0; i < partida.length; i++) {
                        if (partida[i].equals(simbolo)) {
                            if (!(i == partida.length - 1)) {
                                String t = partida[i + 1];
                                if (!simbolos.contains(t)) {
                                    if (!siguiente.contains(t)) {
                                        siguiente.add(t);
                                    }
                                    if (!sig.get(simbolo).contains(t)) {
                                        sig.get(simbolo).add(t);
                                    }
                                } else {
                                    LinkedList<String> f = primeros.get(t);
                                    for (String s : f) {
                                        if (!siguiente.contains(s)) {
                                            if (!s.equals("€")) {
                                                siguiente.add(s);
                                            }

                                        }
                                        if (!sig.get(simbolo).contains(s)) {
                                            if (!s.equals("€")) {
                                                sig.get(simbolo).add(s);
                                            }

                                        }
                                    }
                                    if (f.contains("€")) {
                                        f.remove("€");
                                        LinkedList<String> sigTemp = sig.get(entry.getKey());
                                        if (sigTemp.size() > 0) { //Si se ecntonraron siguientes
                                            for (String s : sigTemp) {
                                                if (!siguiente.contains(s)) {
                                                    siguiente.add(s);
                                                }
                                                if (!sig.get(simbolo).contains(s)) {
                                                    sig.get(simbolo).add(s);
                                                }

                                            }

                                        } else {// sino encontramos siguientes entonces buscamos el siguiente en la tabla que tenemos, esto suele
                                            //ocurrir cuando la produccion es la inciial y no aparece en las demas producciones
                                            sigTemp = follow(entry.getKey(), sig);
                                            if (sigTemp.size() > 0) {
                                                for (String s : sigTemp) {
                                                    if (!siguiente.contains(s)) {
                                                        siguiente.add(s);
                                                    }
                                                    if (!sig.get(simbolo).contains(s)) { //Tambien tomamos encuenta agregarlos a la tabala hash por si en algun 
                                                        sig.get(simbolo).add(s); //momento necesita de ese simbolo para evitar la recursion infinita
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                LinkedList<String> sigTemp = sig.get(entry.getKey());
                                if (sigTemp.size() > 0) {
                                    for (String s : sigTemp) {
                                        if (!siguiente.contains(s)) {
                                            siguiente.add(s);
                                        }
                                        if (!sig.get(simbolo).contains(s)) {
                                            sig.get(simbolo).add(s);
                                        }
                                    }
                                } else {
                                    sigTemp = follow(entry.getKey(), sig);
                                    if (sigTemp.size() > 0) {
                                        for (String s : sigTemp) {
                                            if (!siguiente.contains(s)) {
                                                siguiente.add(s);
                                            }
                                            if (!sig.get(simbolo).contains(s)) {
                                                sig.get(simbolo).add(s);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return siguiente;
    }
    
    /*#################################
            Prediccion
      ################################
     */


    public HashMap<String, HashMap<String, LinkedList<String>>> pred() {
        HashMap<String, HashMap<String, LinkedList<String>>> prediccion = new HashMap<>();
        follow();  // llamamos los siguientes para que esten construidos
        //Agregamos  los simbolos
        for (Map.Entry<String, LinkedList<String>> entry : produccion.entrySet()) {  //recorremos las producciones y armamos nuestro conjunto de prediccion
            prediccion.put(entry.getKey(), new HashMap<>());
        }

        //Agregamos las producciones respectiva con cada simbolo
        for (Map.Entry<String, LinkedList<String>> entry : produccion.entrySet()) {  //recorremos las producciones y armamos nuestro conjunto de prediccion
            HashMap<String, LinkedList<String>> tempP = prediccion.get(entry.getKey());
            for (String prod : entry.getValue()) {  // iteramos cada produccion de ese simbolo
                LinkedList<String> tempPrim = firstU(prod); //sacamos los primeros de esa produccion
                if (tempPrim.contains("€")) {  // si los primeros contienen a vacio
                    tempPrim.remove("€");  // entonces quitamos vacio
                    LinkedList<String> tempSig = siguientes.get(entry.getKey());  // sacamos los siguientes del simbolo no terminal en donde nos encontramos
                    for (String sig : tempSig) {  // y los unimos con los primeros que sacamos antes
                        if (!tempPrim.contains(sig)) {  // si primero no contiene ese simbolo de los siguientes lo agregamos
                            tempPrim.add(sig);  //lo agregamos
                        }
                    }
                }

                tempP.put(prod, tempPrim);  // y añadimos esos priemros como el conjunto de prediccion de nuestra produccion
            }
        }

        //imprimimos el conjunto de prediccion
        for (Map.Entry<String, HashMap<String, LinkedList<String>>> entry : prediccion.entrySet()) {
            System.out.println("Simbolo " + entry.getKey());

            entry.getValue().forEach((prod, pred) -> {
                System.out.println(entry.getKey() + "::== " + prod);
                System.out.print("PRED(");
                for (String pr : pred) {
                    System.out.print(" " + pr + "");
                }
                System.out.print(")");
                System.out.println("");
            });
            System.out.println("");
        }

        for (Map.Entry<String, HashMap<String, LinkedList<String>>> entry : prediccion.entrySet()) {

            LinkedList<String> temp = new LinkedList<>();
            entry.getValue().forEach((prod, pred) -> {

                for (String pr : pred) {
                    if (!temp.contains(pr)) {
                        temp.add(pr);
                    } else {
                        JOptionPane.showMessageDialog(null, "LA GRAMATICA NO SE PUEDE ANALIZAR POR LL->1  POR LA PRODUCCION  : " + entry.getKey() + "->" + prod);
                        break;
                    }
                }

            });
            System.out.println("");
        }
        
        
        
        /*for (Map.Entry<String, LinkedList<String>> entry : prediccion.entrySet()) {
            System.out.println("Produccion " + entry.getKey());
            
        }*/
        return prediccion;
    }

    //TABLA
    public String[][] tabla() {

        LinkedList<String> terminalestemp = new LinkedList<>();
        HashMap<String, HashMap<String, LinkedList<String>>> prediccion = pred();

        for (Map.Entry<String, LinkedList<String>> entry : produccion.entrySet()) {
            for (String term : entry.getValue()) {
                String[] temp = term.split(" ");
                for (int i = 0; i < temp.length; i++) {
                    if (!simbolos.contains(temp[i])) {
                        if (!terminalestemp.contains(temp[i]) && !temp[i].equals("€")) {
                            terminalestemp.add(temp[i]);
                        }
                    }
                }
            }
        }
        terminalestemp.add("$");

        String[][] tablatemp = new String[simbolos.size() + 1][terminalestemp.size() + 1];

        //INICIALIZA TABLA
        for (int i = 0; i < tablatemp.length; i++) {
            for (int j = 0; j < tablatemp[i].length; j++) {
                tablatemp[i][j] = "err";

            }
        }

        //SIMBOLOS
        for (int i = 1; i < tablatemp.length; i++) {
            tablatemp[i][0] = simbolos.get(i - 1);
        }
        //TERMINALES
        for (int i = 1; i < tablatemp[0].length; i++) {
            tablatemp[0][i] = terminalestemp.get(i - 1);
        }

        //TABLA
        for (int i = 1; i < tablatemp.length; i++) {
            HashMap<String, LinkedList<String>> temp = prediccion.get(tablatemp[i][0]);
            for (Map.Entry<String, LinkedList<String>> entry : temp.entrySet()) {
                for (String pred : entry.getValue()) {
                    if (terminalestemp.contains(pred)) {
                        for (int j = 1; j < tablatemp[i].length; j++) {
                            if (tablatemp[0][j].equals(pred)) {
                                tablatemp[i][j] = tablatemp[i][0] + "->" + entry.getKey();
                            }
                        }
                    }
                }
            }

        }
        //MOSTRAR TABLA
        for (int i = 0; i < tablatemp.length; i++) {
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            for (int j = 0; j < tablatemp[i].length; j++) {

                System.out.print("" + tablatemp[i][j] + "");
                System.out.print("  |   ");
            }
            System.out.println("");
        }
        this.terminales = terminalestemp;
        this.tabla = tablatemp;

        return tablatemp;
    }

    public String buscar(String term, String prod) {
        String accion = "err";

        for (int i = 1; i < tabla.length; i++) {
            for (int j = 1; j < tabla[i].length; j++) {
                if (tabla[i][0].equals(prod) && tabla[0][j].equals(term)) {
                    accion = tabla[i][j];
                }
            }
        }

        return accion;
    }

    //RECONOCER
    public String[][] reconocer(String cad) {
        LinkedList<String> listapila = new LinkedList<String>();
        LinkedList<String> listacadena = new LinkedList<String>();
        LinkedList<String> listaaccion = new LinkedList<String>();
        cad += " $";  //agregamos el piso de la cola
        String[] cadenaSplit = cad.split(" ");

        Queue<String> entrada = new LinkedList<>();
        Stack<String> pila = new Stack<>();
        pila.push("$"); // agregamos el piso de la pila
        pila.push(simbolos.getFirst());
        arbol_derivacion.setRoot(new Node(simbolos.getFirst()));

        for (int i = 0; i < cadenaSplit.length; i++) {
            entrada.add(cadenaSplit[i]);

        }
        String accion = "";
        setErrores((Boolean) false);

        while (!(pila.peek().equals("$") && entrada.peek().equals("$"))) {  //mientras lo que este en la fila y cola no sea $, entonces iteramos

            String term = entrada.peek();
            String prod = pila.peek();
            if (pila.isEmpty() && entrada.isEmpty()) {
                listapila.add("Pila vacia");
                listacadena.add("Entrada vacia");
                listaaccion.add("err");
                break;
            }
            if (pila.isEmpty()) {
                listapila.add("Entrada vacia");
                listaaccion.add("err");
                break;
            }
            if (entrada.isEmpty()) {
                listacadena.add("Entrada vacia");
                listaaccion.add("err");
                break;
            }
            System.out.println("PILA:  " + prod);
            System.out.println("COLA:  " + term);

            listapila.add(pila.toString());
            listacadena.add(entrada.toString());
            listaaccion.add(accion);

            if (term.equals(prod)) {
                pila.pop();
                entrada.poll();
            } else {
                accion = buscar(term, prod);
                System.out.println("ACTION " + accion);
                if (!accion.equals("err")) {  // si la accion es un err, entonces esw porque la cadena no es valida

                    String[] accionSplit = accion.split("->");
                    String[] accionFinal = accionSplit[1].split(" ");
                    String nodo = pila.pop();//SACAMOS DE LA PILA LA PROD ACTUAL Y LA REEMPLAZAMOS POR LA ACCION
                    System.out.println("LO QUE SACA DE LA PILA :   " + nodo);
                    if (!accionSplit[1].equals("€")) {//SI LA ACCION NO ES VACIO SE AÑADE LA PRODUCCION QUE ESTA EN LA TABLA DE LO CONTRARIO SOLO SE REMUEVE DE LA PILA
                        for (int i = accionFinal.length - 1; i > -1; i--) {
                            pila.push(accionFinal[i]);

                        }
                        Node var = arbol_derivacion.find(nodo);
                        if (var != null) {
                            for (int i = 0; i < accionFinal.length; i++) {


                                var.addChild(new Node(accionFinal[i],var));
                                System.out.println("PADRE : " + var.getData());
                                System.out.println("LO QUE AÑADO AL ARBOL : " + accionFinal[i]);

                            }
                        }
                    } else {
                        Node var = arbol_derivacion.find(nodo);

                        if (var != null) {
                            var.addChild(new Node(accionSplit[1],var));
                            System.out.println("PADRE : " + var.getData());
                            System.out.println("Los hijos de " + var.getData() + " son ");
                            for (Object tempNode: var.getChildren()) {
                                Node nodeT = (Node) tempNode;
                                System.out.print(" " + nodeT.getData());
                            }
                            System.out.println("");
                            System.out.println("LO QUE AÑADO AL ARBOL : " + accionSplit[1]);
                        } else {
                            System.out.println("VALOR DEL NODO VAR : " + nodo);
                        }
                    }

                } else {
                    if (simbolos.contains(pila.peek())) {
                        LinkedList<String> primTemp = primeros.get(pila.peek());
                        JOptionPane.showMessageDialog(null, "Se esperaba " + primTemp.toString() + " y se encontro " + entrada.peek());
                        setErrores((Boolean) true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Se esperaba " + pila.peek() + " y se encontro " + entrada.peek());
                        setErrores((Boolean) true);
                    }
                    pila.clear();
                    pila.push("$");
                    pila.push(simbolos.getFirst());
                    entrada.poll();
                    arbol_derivacion = new NaryTree();
                    arbol_derivacion.setRoot(new Node(simbolos.getFirst()));

                }
            }

        }
        listapila.add(pila.toString());
        listacadena.add(entrada.toString());
        listaaccion.add("Termino de analizar");


        String[][] matriz = new String[listapila.size()][3];

        for (int i = 0; i < listapila.size(); i++) {
            matriz[i][0] = listapila.get(i);
            matriz[i][1] = listacadena.get(i);
            matriz[i][2] = listaaccion.get(i);
        }
        System.out.println(arbol_derivacion.preOrder());
        return matriz;

    }

    public NaryTree getArbolContexto() {
        return arbolContexto;
    }

    public void setArbolContexto(NaryTree arbolContexto) {
        this.arbolContexto = arbolContexto;
    }

    public LinkedList<String> getTerminales() {
        return terminales;
    }

    public void setTerminales(LinkedList<String> terminales) {
        this.terminales = terminales;
    }

    public NaryTree getArbol_derivacion() {
        return arbol_derivacion;
    }

    public void setArbol_derivacion(NaryTree arbol_derivacion) {
        this.arbol_derivacion = arbol_derivacion;
    }

    public void leerArchivo(String ruta) {

        arbolContexto = new NaryTree(); // arbol de contexto

        try {
            FileReader f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);

            String linea = b.readLine();

            Contexto principal = new Contexto("principal");
            Node root = new Node<>(principal);
            arbolContexto.setRoot(root);

            while (linea != null) {
                String[] lineaTemp; // lo que contiene esa linea spliteado
                linea = linea.replaceAll("\\s+", " ");
                if(linea.charAt(0) == ' '){  // si en la posicion cero tiene un espacio entonces lo quitamos
                    linea = linea.replaceFirst(" ", "");
                }
                lineaTemp = linea.split(" ");
                LinkedList temp = llenarContexto2(b,linea, principal);
                for (Object tempObj: temp) {
                    Node temp2 = (Node) tempObj;
                    root.addChild(temp2);
                }
                linea = b.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for ( Object temp2:arbolContexto.preOrder2() ) {
            Node cast=(Node)temp2;
            Contexto contemp =(Contexto)cast.getData();
            System.out.println(""+contemp.getNombre());
            //contemp.imprimir();
            System.out.println(""+contemp.imprimir2());
        }

    }




    private LinkedList  llenarContexto2(BufferedReader buffer, String linea, Contexto conPa) throws IOException {


        LinkedList<Node> nodeLinkedList = new LinkedList();

        while(linea != null){
            linea = linea.replaceAll("\\s+", " ");
            if(linea.indexOf(" ") == 0){  // si en la posicion cero tiene un espacio entonces lo quitamos
                linea = linea.replaceFirst(" ", "");
            }
            String [] lineaTemp = linea.split(" ");
            System.out.println("----- **** Valor de la linea " + linea);
            if(lineaTemp[0].equals("fun")){
                Contexto con;
                con = new Contexto("fun");
                System.out.println("+Entro  a fun");
                String tipo = lineaTemp[1];
                String nombre;
                nombre = linea.substring(linea.indexOf(tipo)+tipo.length(), linea.indexOf("("));  //sacamos el nombre hasta antes de que encuentre el parentesis
                nombre = nombre.replaceAll("\\s+", ""); // quitamos los espacios que puderina haber
                String valor = "--";  // el valor que ingresamos por que es un funcion es --
                String parametro = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")"));

                System.out.println("Tipo = " + tipo + " Nombre = " + nombre + " Valor :" + valor + " Parametros : " + parametro); // lo insertamos al contexto papa
                conPa.insertarFila(tipo, nombre, valor, parametro); // insertamos la fila en el contexto respectivo
                Node node = new Node(con);
                nodeLinkedList.add(node);
                linea = buffer.readLine(); // leemos una nueva linea y enviamos para sacar los demas contextos
                LinkedList temp = llenarContexto2(buffer,linea, con);
                for (Object tempObj: temp) {
                    Node temp2 = (Node) tempObj;
                    node.addChild(temp2);
                }
                linea = buffer.readLine();

            }
            else if(sentenciasControl.contains(lineaTemp[0])  || lineaTemp[0].contains("else")){
                if(lineaTemp[0].equals("for")){
                    System.out.println("+Entro  a for");
                    Contexto con;
                    con = new Contexto("for");
                    String tipo = linea.substring(linea.indexOf("(")+1,linea.indexOf("&"));
                    tipo = tipo.replaceAll("\\s+", "");
                    String nombre = linea.substring(linea.indexOf(tipo)+tipo.length(),linea.indexOf("="));
                    nombre = nombre.replaceAll("\\s+", ""); // quitamos los espacios que puderina haber
                    String valor = linea.substring(linea.indexOf("=")+1, linea.indexOf(";"));
                    String parametro = "--";
                    System.out.println("Tipo = " + tipo + " Nombre = " + nombre + " Valor :" + valor + " Parametros : " + parametro);
                    con.insertarFila(tipo, nombre, valor, parametro); // insertamos la fila en el contexto respectivo
                    Node node = new Node(con);
                    nodeLinkedList.add(node);
                    linea = buffer.readLine(); // leemos una nueva linea y enviamos para sacar los demas contextos
                    LinkedList temp = llenarContexto2(buffer,linea, con);
                    for (Object tempObj: temp) {
                        Node temp2 = (Node) tempObj;
                        node.addChild(temp2);
                    }
                    linea = buffer.readLine();
                }
                else{
                    Contexto con;
                    if(lineaTemp[0].contains("else")){
                        con = new Contexto("else"); // sino es for entonces puede ser un while etc ... entonces le colocamos el nombre del contexto
                    }
                    else{
                        con = new Contexto(lineaTemp[0]); // sino es for entonces puede ser un while etc ... entonces le colocamos el nombre del contexto
                    }
                    Node node = new Node(con);
                    nodeLinkedList.add(node);
                    linea = buffer.readLine(); // leemos una nueva linea y enviamos para sacar los demas contextos
                    LinkedList temp = llenarContexto2(buffer,linea, con);
                    for (Object tempObj: temp) {
                        //Node padre=arbolContexto.obtenerPadre2(con);
                        Node temp2 = (Node) tempObj;
                        node.addChild(temp2);
                    }
                    linea = buffer.readLine();
                }
            }
            else if(tiposVariables.contains(lineaTemp[0])){ // sino entonces verificamos si es una declaracion
                llenarContexto3(linea,conPa);
                linea = buffer.readLine(); // leemos una nueva linea y enviamos para sacar los demas contextos
            }
            else{
                linea = buffer.readLine(); // leemos una nueva linea y enviamos para sacar los demas contextos
            }

            if(linea!=null){
                if(linea.contains("}")){
                    linea = null;
                }
            }

        }


        return nodeLinkedList;

    }

    private void llenarContexto3(String linea, Contexto con){
        linea = linea.replaceAll("\\s+", " ");
        if(linea.charAt(0) == ' '){  // si en la posicion cero tiene un espacio entonces lo quitamos
            linea = linea.replaceFirst(" ", "");
        }
        System.out.println("Lineaa en las VARIABLES!!" + linea);
        System.out.println("+Entro  a variables");
        String [] lineaTemp = linea.split(" ");
        String tipo = lineaTemp[0];
        String nombre = "";
        String valor = "";
        nombre = linea.substring(linea.indexOf(lineaTemp[0])+lineaTemp[0].length(),linea.indexOf("=")); //el nombre se encuentra en despues del tipo,
        //ubicamos el tipo y luego le sumamos la longitud del tipo para que no lo tome en cuenta y para cuando encuentra el igual;

        nombre = nombre.replaceAll("\\s+", ""); // quitamos los espacios que puderina haber
        valor = linea.substring(linea.indexOf("=")+1, linea.lastIndexOf(";"));
        String parametro = "--";
        System.out.println("Tipo = " + tipo + " Nombre = " + nombre + " Valor :" + valor + " Parametros : " + parametro);

        con.insertarFila(tipo, nombre, valor, parametro); // insertamos la fila en el contexto respectivo
    }



    /**
     * @return the simbolos
     */
    public LinkedList<String> getSimbolos() {
        return simbolos;
    }

    /**
     * @param simbolos the simbolos to set
     */
    public void setSimbolos(LinkedList<String> simbolos) {
        this.simbolos = simbolos;
    }

    /**
     * @return the produccion
     */
    public HashMap<String, LinkedList<String>> getProduccion() {
        return produccion;
    }

    /**
     * @param produccion the produccion to set
     */
    public void setProduccion(HashMap<String, LinkedList<String>> produccion) {
        this.produccion = produccion;
    }

    /**
     * @return the errores
     */
    public Boolean getErrores() {
        return errores;
    }

    /**
     * @param errores the errores to set
     */
    public void setErrores(Boolean errores) {
        this.errores = errores;
    }

}
