/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Camilo
 */
public class Node <E>implements Serializable{
    private E data;
    private Node<E> padre;
    private LinkedList<Node<E>> children;
    private int x;
    private int y;
    private int radio;
    

    public Node(E data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    public Node(E data,Node<E> padre) {
        this.data = data;
        this.children = new LinkedList<>();
        this.padre=padre;
    }
    public Node() {
        this.children =  new LinkedList<>();
    }

    public void addChild(E child){
        Node temp = new Node(child);
        //temp.setPadre(this);
        children.add(temp);
    }
    
    public void addChild(E child,Node<E> padre){
        Node temp = new Node(child);
        temp.setPadre(padre);
        children.add(temp);
    }
    public void setPos(int x,int y, int radio){
        this.x=x;
        this.y=y;
        this.radio=radio;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(this.x,this.y,this.radio*2,this.radio*2);
    }
    
    /**
     * @return the data
     */
    public E getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * @return the children
     */
    public LinkedList<Node<E>> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(LinkedList<Node<E>> children) {
        this.children = children;
    }
    
    public void addChild(Node<E>child)
    {
        children.add(child);
    }
    
    public boolean isLeaf()
    {
        return children.isEmpty();
    }
    
    public int getGrade()
    {
        return children.size();
    }

    /**
     * @return the padre
     */
    public Node<E> getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Node<E> padre) {
        this.padre = padre;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the radio
     */
    public int getRadio() {
        return radio;
    }

    /**
     * @param radio the radio to set
     */
    public void setRadio(int radio) {
        this.radio = radio;
    }
}
