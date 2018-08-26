/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author 
 */
public class NaryTree <E>implements Serializable{
    private Node<E>root;

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }

    public NaryTree( ){
    }

    public void add(E element)
    {
        if(root==null)
            root=new Node<>(element);
        else
            root.addChild(new Node(element));
    }
    
    public boolean add(E element,E parent)
    {
        if(root==null)
            return false;
        Node<E> p=find(parent);
        if(p!=null)
        {
            p.addChild(new Node(element,p));
            return true;
        }
        return false;
    }
    
    public Node<E> validar(E elemento){
        if(!(elemento instanceof Node)){
            return null;
        }
        Node<E> nodo =(Node<E>)elemento;
        return nodo.getPadre()==nodo?null:nodo;
    }
    
    public boolean esRaiz(E elemento){
        return elemento==getRoot();
    }
    
    public Node<E> obtenerPadre(E elemento){
        return validar(elemento).getPadre();
    }
    public Node<E> obtenerPadre2(E elemento){
        return validar(elemento);
    }
    
    
    public LinkedList<Node> obtenerHijos(Node elemento){
        Node nodo=validar((E)elemento);
        return nodo.getChildren();
    }
    
    
    public Node<E>find(E element)
    {
        return find(element,root);
    }
    
    private Node<E>find(E element,Node<E>tree)
    {

        if(tree.getData().equals(element)) {
            if (tree != root) {
                if (tree.getChildren().size() == 0) {
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
            } else {
                return tree;
            }
        }
        else{
            for(Node<E>child:tree.getChildren())
            {
                Node<E> res=find(element,child);
                if(res!=null && res.getChildren().size()==0) {
                    return res;
                }else if(res!=null){ // si tiene hijos entonces
                    for (Node e:res.getChildren()) {  // buscamos si uno de sus hijos tiene un dato como el
                        if(e.getData().equals(res.getData())){ // si son iguales entonces
                            if(e.getChildren().size()==0){ // sino tiene hijos el hijo lo retornamos
                                return e;
                            }else{ // sino buscamos en el hijo por si tal ves se cumple la condicion de que tiene un hijo con el mismo dato que el
                                return find(element,e);
                            }
                        }
                    }
                }

            }
        }
        return null;
    }
   
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
    
    public Node<E> findpadre(Node<E> r) {
        return findpader(r, root);
    }

    private Node<E> findpader(Node<E> r, Node<E> tree) {
        if (tree.getChildren().contains(r)) {
            return tree;
        } else {
            for (Node<E> child : tree.getChildren()) {
                Node<E> res = findpader(r, child);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    public String preOrder()
    {
        return preOrder(root);
    }
    
    private String preOrder(Node<E> tree)
    {
        if(tree!=null){
            String x=tree.getData()+" ";
            for(Node<E>child:tree.getChildren())
                x+=preOrder(child);
            return x;
        }
        return " ";
    }
    
    public String preOrder3()
    {
        return preOrder3(root);
    }
    
    private String preOrder3(Node<E> tree)
    {
        if(tree!=null){
            Contexto c=(Contexto)tree.getData();
            String x="";
            if(tree!=root){
            Contexto p=(Contexto)findpadre(tree).getData();
                x="Nombre : "+c.getNombre()+" Padre   "+p.getNombre()+" Coordenadas x:"+tree.getX()+" Y:"+tree.getY()+"\n";
            }else{
                x="Nombre : "+c.getNombre()+" Padre : es raiz"+" Coordenadas x:"+tree.getX()+" Y:"+tree.getY()+"\n";
            }
            for(Node<E>child:tree.getChildren())
                x+=preOrder3(child);
            return x;
        }
        return " ";
    }
    
    public LinkedList<Node> preOrder2()
    {
        return preOrder2(root,new LinkedList());
    }
    
    private LinkedList preOrder2(Node<E>tree,LinkedList l)
    {
        if(tree!=null)
        {
            l.add(tree);
            for(Node<E>child:tree.getChildren())
                l=preOrder2(child,l);
            return l;
        }
        return new LinkedList();
    }

    public int Nivel(E data)
    {
        Node<E>nodo=find(data);
        return Nivel(root,nodo, 0);
    }
    
    public int Nivel(Node<E>tree,Node<E> data,int n)
    {
        if(!tree.isLeaf())
        {
            if(tree.getChildren().contains(data))
                n++;
            else
                for(Node<E>child:tree.getChildren())
                {
                    n=Nivel(child,data,n);
                    if(n!=0)
                    {
                        n++;
                        break;
                    }
                }
        }
        return n;
    }
    
    public int Altura()
    {
        if(root!=null)
        {
            if(!root.isLeaf())
                return Altura(root);
            return 0;
        }
        return -1;
    }
    
    private int Altura(Node<E>tree)
    {
        int i=1;
            for(Node<E>child:tree.getChildren())
                if(i<=Altura(child))
                {
                    i= Altura(child)+1;
                }
        return i;
    }
    
    public LinkedList DatosNivel(int n)
    {
        LinkedList l=new LinkedList();
        if(n==0)
        {    
            l.add(root.getData());
            return l;
        }
        return DatosNivel(n,root,l);
    }
    
    private LinkedList DatosNivel(int n,Node<E>tree,LinkedList l)
    {
        if(n==1)
            for(Node<E>child:tree.getChildren())
                l.add(child.getData());
        else
            for(Node<E>child:tree.getChildren())
                l=DatosNivel(n-1,child,l);
        return l;
    }
    
    public void Guardar()
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
    }
}

