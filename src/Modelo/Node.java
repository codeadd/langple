/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import sun.java2d.pipe.RenderingEngine;

/**
 *
 * @author Camilo
 */
public class Node <E>implements Serializable{
    private E data;
    private Node<E> padre;
    private ArrayList<Node<E>> children;
    public int x;
    private int y;
    private int radio;

    public Node() {
    }
    
    public Node(E data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public Node(E data,Node<E> padre) {
        this.data = data;
        this.children = new ArrayList<>();
        this.padre=padre;
    }
    /*public void drawNode(Graphics g,int x, int y){
        this.x=x;
        this.y=y;
        
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setFont(new Font("Comic Sans MS", 0, 8));
        g2d.setColor(new Color(158,253,56));
        
        g2d.drawRect(x, y, 12, 12);
        g2d.setColor(new Color(0,165,80));
        g2d.drawString(""+this.data, x+5, y+10);
    }
    
    public void crearArista(Graphics g,int x1,int y1, int x2, int y2){
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(new Color(0,165,80));
        g2d.drawLine(x1, y1, x2, y2);
    }*/
    
    public void setPos(int x, int y,int radio){
        this.x=x;
        this.y=y;
        this.radio=radio;
    }
    
    public Rectangle getBounds(){
       return new Rectangle(this.x, this.y, this.radio*2, this.radio*2);
    }
    
    public void verificarInt(Node e){
        int cambio=4;
        for (Node<E> node : children) {
            if(node!=e){
                if(e.getBounds().intersects(node.getBounds())){
                    node.setPos(x+cambio, node.getY(), node.getRadio());
                }
            }
            cambio+=2;
        }
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
    public ArrayList<Node<E>> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<Node<E>> children) {
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
