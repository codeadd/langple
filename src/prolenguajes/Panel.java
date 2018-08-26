/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prolenguajes;

import Modelo.Gramatica;
import Modelo.Node;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.sound.midi.Receiver;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author braya
 */
public class Panel extends JScrollPane {

    Gramatica g5;
    private int radio = 8;
    private int espacioVertical = 30;
    private HashMap<Node, Point> coordenadas;
    private int espacioH = 80;

    public Panel() {

        this.setLayout(null);
        this.setSize(900, 600);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.coordenadas = new HashMap<>();
        contruirG();

    }

    public void contruirG() {
        //this.setSize(1000,800);
        g5 = new Gramatica();
        g5.getSimbolos().add("S");
        g5.getSimbolos().add("S2");
        g5.getSimbolos().add("Q");
        g5.getSimbolos().add("Q2");
        g5.getSimbolos().add("R");
        g5.getSimbolos().add("F");

        g5.getProduccion().put("S", new LinkedList<>(Arrays.asList("Q S2")));
        g5.getProduccion().put("S2", new LinkedList<>(Arrays.asList("or Q S2", "€")));
        g5.getProduccion().put("Q", new LinkedList<>(Arrays.asList("R Q2")));
        g5.getProduccion().put("Q2", new LinkedList<>(Arrays.asList("R Q2", "€")));
        g5.getProduccion().put("R", new LinkedList<>(Arrays.asList("F and", "x", "y")));
        g5.getProduccion().put("F", new LinkedList<>(Arrays.asList("z")));

        HashMap<String, LinkedList<String>> first = g5.follow();

        for (Map.Entry<String, LinkedList<String>> entry : first.entrySet()) {
            System.out.println("SIGUIENTES de : --> " + entry.getKey());

            for (String arg : entry.getValue()) {
                System.out.print(" " + arg);
            }
            System.out.println("");

        }
        g5.tabla();
        //chucho.reconocer("if ( id == entero ) id = entero else id = entero + entero + entero");
        //g3.reconocer("a b c d");
        g5.reconocer("z and or z and or z and or y x");
        darcoordenadas(g5.arbol_derivacion.getRoot(), getWidth() / 2, 10, espacioH);
        //darcoordenadas2(g5.arbol_derivacion.getRoot(),3);
        
        MouseAdapter mu = new MouseAdapter() {
            Rectangle r = new Rectangle();
            Node nd;
            private Point startPoint;
            Point offset = new Point(0, 0);

            @Override
            public void mousePressed(MouseEvent e) {
                r = new Rectangle(e.getX(), e.getY(), 2 * radio, 2 * radio);
                //nd=g5.arbol_derivacion.obtenerElemento(r);
                System.out.println("--------------------click");
                nd = g5.arbol_derivacion.find2(r);
                if (nd != null) {
                    System.out.println("Dato : " + nd.getData());
                    System.out.println("Coor x : " + nd.getBounds().x + " y:" + nd.getBounds().y);
                    //System.out.println("" + g5.arbol_derivacion.getRoot().getData());
                    //Node ro = g5.arbol_derivacion.getRoot();
                    //System.out.print("coor x:" + ro.getX() + " Y:" + ro.getY());
                    //System.out.println("coor rect x:" + ro.getBounds().x + " y:" + ro.getBounds().y);
                }
                startPoint = e.getPoint();
                startPoint.x -= offset.x;
                startPoint.y -= offset.y;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startPoint = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();
                if (nd != null) {
                    int x = p.x;//- startPoint.x;
                    int y = p.y;//- startPoint.y;
                    r.x = x;
                    r.y = y;
                    nd.setPos(x, y, radio);
                    g5.arbol_derivacion.find2(r).setPos(nd.getX(), nd.getY(), radio);
                    System.out.println("++++++++ND x: " + nd.getX() + " Y : " + nd.getY());
                    //g5.arbol_derivacion.obtenerElemento(r).setPos(nd.getX(), nd.getY(), radio);
                }
                repaint();
            }

        };

        this.addMouseListener(mu);
        this.addMouseMotionListener(mu);

    }

    public void añadirEvt() {
        Rectangle x = new Rectangle();
        /*this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                x.x=e.getX();
                x.y=e.getY();
                x.height=radio*2;
                x.width=radio*2;
            }
            
        });*/
    }

    /*@Override
    public void mouseDragged(MouseEvent e) {
        Rectangle rect = new Rectangle(e.getX(), e.getY(), 2*radio, 2*radio);
        g5.arbol_derivacion.obtenerElemento(rect).setPos(e.getY(), e.getX(), radio);
        Node n=g5.arbol_derivacion.obtenerElemento(rect);
        System.out.println("nodo : "+n.getData()+" pos X : "+n.getX()+" pos Y:"+n.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawString("arbol binario", (this.getWidth() / 2) - 150, 20);
        //g5.arbol_derivacion.paint(g);
        //repaint();
        if (g5.arbol_derivacion != null) {
            dibujar2(g, g5.arbol_derivacion.getRoot());
        }
        //Dimension size=new Dimension();
        //size.setSize(getWidth(), getHeight());
        //this.setPreferredSize(size);
        
        repaint();
    }

    public void dibujar2(Graphics g, Node raiz) {
        g.drawOval(raiz.getX(), raiz.getY(), raiz.getRadio() * 2, raiz.getRadio() * 2);
        Rectangle r=raiz.getBounds();
        g.setColor(Color.GREEN);
        g.drawRect(r.x, r.y, r.width, r.height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans MS", 0, 8));
        //JTextArea label =new JTextArea(raiz.getData()+"");
        //label.setBounds(raiz.getX()-radio, raiz.getY()-radio, 2*radio, 2*radio);
        //label.setBorder(BorderFactory.createLineBorder(Color.black));
        //this.add(label);
        g.drawString(raiz.getData() + "", raiz.getX()+3, raiz.getY() +10);
        ArrayList<Node> hijos = g5.arbol_derivacion.obtenerHijos(raiz);
        for (Node hijo : hijos) {
            Point punto = coordenadas.get(g5.arbol_derivacion.obtenerPadre(hijo));
            Node pad=g5.arbol_derivacion.obtenerPadre(hijo);
            //dibujarLinea(g, x - espacioH, y + espacioVertical, punto.x, punto.y);
            //dibujarLinea(g,hijo.getX(),hijo.getY(),pad.getX(),pad.getY());
            g.drawLine(hijo.getX(), hijo.getY(), pad.getX(), pad.getY()+radio*2);
            //dibujarLinea(g, raiz.getX(), raiz.getY(), punto.x, punto.y);
            dibujar2(g, hijo);
        }
    }

    /*public void darcoordenadas2(Node raiz,int cambio) {
        cambio+=10;
        ArrayList<Node> hijos = g5.arbol_derivacion.obtenerHijos(raiz);
        //Node nd=g5.arbol_derivacion.find3(raiz);
        //if(nd!=null){
        raiz.setPos(raiz.getX()+cambio, raiz.getY(), radio);
        //}
        for (Node hijo : hijos) {
            darcoordenadas2(hijo,cambio);
        }
    }*/

    
    public void darcoordenadas(Node raiz, int x, int y, int espacioH) {
        
        //JTextArea label =new JTextArea(raiz.getData()+"");
        //label.setBounds(x-radio, y-radio, 2*radio, 2*radio);
        //label.setBorder(BorderFactory.createLineBorder(Color.black));
        //this.add(label);
        //this.add(new JButton("boton"));
        //g.drawOval(x - radio, y - radio, 2 * radio, 2 * radio);
        //g.setFont(new Font("Comic Sans MS", 0, 8));
        //g.drawString(raiz.getData() + "", x - 6, y + 6);
        ArrayList<Node> hijos = g5.arbol_derivacion.obtenerHijos(raiz);
        coordenadas.put(raiz, new Point(x, y));
        raiz.setPos(x, y, radio);
        //Node nd=g5.arbol_derivacion.find2(raiz.getBounds());
        for (Node hijo : hijos) {
            Point punto = coordenadas.get(g5.arbol_derivacion.obtenerPadre(hijo));
            //dibujarLinea(g, x - espacioH, y + espacioVertical, punto.x, punto.y);
            //hijo.setPos(x-espacioH, y+espacioVertical, radio);
            darcoordenadas(hijo, x - espacioH, y + espacioVertical, (espacioH/hijos.size())+10);
            x += espacioH;
        }
    }

    public void dibujarLinea(Graphics g, int x1, int y1, int x2, int y2) {
        double d = Math.sqrt(espacioVertical * espacioVertical + (x2 - x1) * (x2 - x1));
        int xx1 = (int) (x1 - radio * (x1 - x2) / d);
        int yy1 = (int) (y1 - radio * (y1 - y2) / d);
        int xx2 = (int) (x2 + radio * (x1 - x2) / d);
        int yy2 = (int) (y2 + radio * (y1 - y2) / d);

        g.drawLine(xx1, yy1, xx2, yy2);
    }

    public Dimension getSize(){
        return getPreferredSize();
    }
    
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        Panel p = new Panel();
        JScrollPane scp = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scp.setSize(p.getPreferredSize());
        scp.setSize(p.getPreferredSize());
        //scp.setSize(2000,600);
        
        //JTextField text=new JTextField("texto");//TEXT AREA PARA INGRESAR DATOS
        //text.setBounds(650, 50 ,50, 25);//UBICACION Y TAMAÑO DEL TEXT AREA
        //p.add(label);
        //p.add(text);

        jf.getContentPane().add(scp);
        jf.pack();
        jf.setSize(1000, 600);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
    }

}
