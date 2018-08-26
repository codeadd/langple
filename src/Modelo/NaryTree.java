/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;

/**
 *
 * @author Camilo
 */
public class NaryTree<E> implements Serializable {

    private Node<E> root;

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }

    public NaryTree() {
    }

    public void add(E element) {
        if (root == null) {
            root = new Node<>(element);
        } else {
            root.addChild(new Node(element));
        }
    }

    public boolean add(E element, E parent) {
        if (root == null) {
            return false;
        }
        Node<E> p = find(parent);
        if (p != null) {
            p.addChild(new Node(element, p));
            //p.verificarInt(find(element));
            return true;
        }
        return false;
    }

    public Node<E> validar(E elemento) {
        if (!(elemento instanceof Node)) {
            return null;
        }
        Node<E> nodo = (Node<E>) elemento;
        return nodo.getPadre() == nodo ? null : nodo;
    }

    public boolean esRaiz(E elemento) {
        return elemento == getRoot();
    }

    public Node<E> obtenerPadre(E elemento) {
        return validar(elemento).getPadre();
    }

    public ArrayList<Node> obtenerHijos(Node elemento) {
        Node nodo = validar((E) elemento);
        return nodo.getChildren();
    }

    /*public Node<E> obtenerElemento(Rectangle r){
        Node nodo=new Node();
        buscar(r,getRoot(),nodo);
        return nodo;
    }
    
    public void buscar(Rectangle r,Node raiz,Node nodo){
        if(r.getBounds().intersects(raiz.getBounds())){
            nodo=raiz;
        }
        
        for(Object x:raiz.getChildren()){
            Node nod=(Node)x;
            buscar(r,nod,nodo);
        }
    }*/
    public Node<E> find2(Rectangle r) {
        return find2(r, root);
    }

    private Node<E> find2(Rectangle r, Node<E> tree) {
        if (tree.getBounds().intersects(r)) {
            return tree;
        } else {
            for (Node<E> child : tree.getChildren()) {
                Node<E> res = find2(r, child);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    public Node<E> find3(Node r) {
        return find3(r, root);
    }

    private Node<E> find3(Node<E> r, Node<E> tree) {
        if (tree != r) {
            if (tree.getBounds().intersects(r.getBounds())) {
                return tree;
            } else {
                for (Node<E> child : tree.getChildren()) {
                    Node<E> res = find3(r, child);
                    if (res != null) {
                        return res;
                    }
                }
            }
        }
        return null;
    }
    
    public Node<E> find(E element) {
        return find(element, root);
    }

    private Node<E> find(E element, Node<E> tree) {
        if (tree.getData().equals(element)) {
            return tree;
        } else {
            for (Node<E> child : tree.getChildren()) {
                Node<E> res = find(element, child);
                if (res != null && res.getChildren().size() == 0) {
                    return res;
                } else if (res != null) { // si tiene hijos entonces
                    for (Node e : res.getChildren()) {  // buscamos si uno de sus hijos tiene un dato como el
                        if (e.getData().equals(res.getData())) { // si son iguales entonces
                            if (e.getChildren().size() == 0) { // sino tiene hijos el hijo lo retornamos
                                return e;
                            } else { // sino buscamos en el hijo por si tal ves se cumple la condicion de que tiene un hijo con el mismo dato que el
                                return find(element, e);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public String preOrder() {
        return preOrder(root);
    }

    private String preOrder(Node<E> tree) {
        if (tree != null) {
            String x = tree.getData() + " ";
            for (Node<E> child : tree.getChildren()) {
                x += preOrder(child);
            }
            return x;
        }
        return " ";
    }

    public LinkedList<Node> preOrder2() {
        return preOrder2(root, new LinkedList());
    }

    private LinkedList preOrder2(Node<E> tree, LinkedList l) {
        if (tree != null) {
            l.add(tree);
            for (Node<E> child : tree.getChildren()) {
                l = preOrder2(child, l);
            }
            return l;
        }
        return new LinkedList();
    }

    public int Nivel(E data) {
        Node<E> nodo = find(data);
        return Nivel(root, nodo, 0);
    }

    public int Nivel(Node<E> tree, Node<E> data, int n) {
        if (!tree.isLeaf()) {
            if (tree.getChildren().contains(data)) {
                n++;
            } else {
                for (Node<E> child : tree.getChildren()) {
                    n = Nivel(child, data, n);
                    if (n != 0) {
                        n++;
                        break;
                    }
                }
            }
        }
        return n;
    }

    public int Altura() {
        if (root != null) {
            if (!root.isLeaf()) {
                return Altura(root);
            }
            return 0;
        }
        return -1;
    }

    /*public void paint(Graphics g){
        //paint(g,root);
        paint2(g);
    }
    /*
    private void paint(Graphics g,Node<E> tree){
       
        Queue<Node<E>> q = new  LinkedList<>();
        int nivel=1;
        int cambiox=10;
        int cambioy=15;
        q.add(root);
        
        while(!q.isEmpty()){
            Node<E> aux=q.remove();
            
            if(aux==root){
                x=nivel*cambiox;
                y=nivel*cambioy;
                aux.drawNode(g, 350, 10);
                nivel=2;
            }
            
            y=nivel*cambioy;
            for(Node<E> aux2 : aux.getChildren() ){
                
                x=nivel*cambiox;
                aux2.drawNode(g, x, y);
                q.add(aux2);
            }
            
            nivel++;
        }
    }
    
    private void paint2(Graphics g){
        
        int a=Altura();
        JOptionPane.showMessageDialog(null,""+a);
        int i=0;
        int y=50;
        int x=350;
        for(i=0;i<a;i++){
            LinkedList<Node<E>> temp=DatosNivel(i);
            y+=20;
            JOptionPane.showMessageDialog(null, ""+y);
            int tam=temp.size();
            int rang = 800/tam;
            LinkedList<Integer> pos = new LinkedList<>();
            int j=0;
            int acum=rang/2;
            for(j=0;j<tam;j++){
                pos.add(acum);
                acum+=rang;
            }
            int indice=0;
            for(Node<E> child : temp){
                x+=40;
                JOptionPane.showMessageDialog(null, ""+x + "dat"+child.getData());
                child.drawNode(g, x, pos.get(i));
                indice+=1;
            }
            
            x=300;
        }
        
    }*/
    private int Altura(Node<E> tree) {
        int i = 1;

        for (Node<E> child : tree.getChildren()) {
            if (i <= Altura(child)) {
                i = Altura(child) + 1;
            }
        }
        return i;
    }

    public LinkedList<Node<E>> DatosNivel(int n) {
        LinkedList<Node<E>> l = new LinkedList();
        if (n == 0) {
            l.add(root);
            return l;
        }
        return DatosNivel(n, root, l);
    }

    private LinkedList<Node<E>> DatosNivel(int n, Node<E> tree, LinkedList l) {
        if (n == 1) {
            for (Node<E> child : tree.getChildren()) {
                l.add(child);
            }
        } else {
            for (Node<E> child : tree.getChildren()) {
                l = DatosNivel(n - 1, child, l);
            }
        }
        return l;
    }

    /*public void Guardar()
    {
        try {
            FileOutputStream ar=new FileOutputStream("src//Archivos//ArbolNario.bin");
            ObjectOutputStream obj=new ObjectOutputStream(ar);
            obj.writeObject(this);
            obj.close();
            ar.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }*/
}
