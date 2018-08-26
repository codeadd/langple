/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import java.awt.BorderLayout;
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
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelo.Contexto;
import modelo.NaryTree;
import modelo.Node;

/**
 *
 * @author braya
 */
public class Arboles extends JDialog {

    NaryTree arbol_e;

    public Arboles(NaryTree arbol, int n) {
        this.arbol_e = arbol;
        JPanel panelGeneral = new JPanel(new BorderLayout());
        JScrollPane barras = new JScrollPane();
        if (n == 0) {
            this.setTitle("Arbol Derivacion");
            this.setSize(new Dimension(600, 400));
            if (arbol_e != null) {
                Arbol_D der = new Arbol_D(arbol_e);
                //der.setSize(1000, 800);
                der.setPreferredSize(new Dimension(10000, 10000));
                der.revalidate();
                barras = new JScrollPane(der, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                //barras.setPreferredSize(new Dimension(10000,800));
            }
        } else if (n == 1) {
            this.setTitle("Arbol Contextos");
            this.setSize(new Dimension(600, 400));
            if (arbol_e != null) {
                Arbol_C cont = new Arbol_C(arbol_e);
                cont.setPreferredSize(new Dimension(20000, 20000));
                cont.revalidate();
                barras = new JScrollPane(cont, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            }
        }
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(e -> {
            this.dispose();
        });

        panelGeneral.add(barras, BorderLayout.CENTER);
        panelGeneral.add(aceptar, BorderLayout.SOUTH);
        this.add(panelGeneral);
    }

    class Arbol_D extends JPanel {

        private int radio = 10;
        private int espacioVertical = 100;
        private HashMap<Node, Point> coordenadas;
        private int espacioH = 200;
        public NaryTree arbol_d;

        public Arbol_D(NaryTree arbol) {
            this.arbol_d = arbol;
            this.setLayout(null);
            this.setSize(900, 600);
            this.coordenadas = new HashMap<>();
            darcoordenadas(arbol.getRoot(), getWidth() / 2, 10, espacioH);

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
                    nd = arbol.find2(r);
                    if (nd != null) {
                        System.out.println("Dato : " + nd.getData());
                        System.out.println("Coor x : " + nd.getBounds().x + " y:" + nd.getBounds().y);
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
                        arbol.find2(r).setPos(nd.getX(), nd.getY(), radio);
                        System.out.println("++++++++ND x: " + nd.getX() + " Y : " + nd.getY());
                        //g5.arbol_derivacion.obtenerElemento(r).setPos(nd.getX(), nd.getY(), radio);
                    }
                    repaint();
                }

            };

            this.addMouseListener(mu);
            this.addMouseMotionListener(mu);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawString("arbol binario", (this.getWidth() / 2) - 150, 20);
            if (arbol_d != null) {
                dibujar2(g, arbol_d.getRoot());
            }
            repaint();
        }

        public void dibujar2(Graphics g, Node raiz) {
            g.drawOval(raiz.getX(), raiz.getY(), raiz.getRadio() * 2, raiz.getRadio() * 2);
            Rectangle r = raiz.getBounds();
            //g.setColor(Color.GREEN);
            //g.drawRect(r.x, r.y, r.width, r.height);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Comic Sans MS", 0, 6));
            g.drawString(raiz.getData() + "", raiz.getX() + 3, raiz.getY() + 10);
            LinkedList hijos = arbol_d.obtenerHijos(raiz);
            for (Object hijo : hijos) {
                Node h = (Node) hijo;
                Point punto = coordenadas.get(arbol_d.obtenerPadre(hijo));
                Node pad = arbol_d.obtenerPadre(hijo);
                g.setColor(Color.GRAY);
                g.drawLine(h.getX(), h.getY(), pad.getX(), pad.getY() + radio * 2);
                dibujar2(g, (Node) hijo);
            }
        }

        public void darcoordenadas(Node raiz, int x, int y, int espacioH) {
            LinkedList hijos = arbol_d.obtenerHijos(raiz);
            coordenadas.put(raiz, new Point(x, y));
            raiz.setPos(x, y, radio);
            for (Object hijo : hijos) {
                Point punto = coordenadas.get(arbol_d.obtenerPadre(hijo));
                darcoordenadas((Node) hijo, x - espacioH, y + espacioVertical, (espacioH / hijos.size()) + 10);
                x += espacioH;
            }
        }

    }

    class Arbol_C extends JPanel {

        private int radio = 10;
        private int espacioVertical = 50;
        private HashMap<Node, Point> coordenadas;
        private int espacioH = 200;
        public NaryTree arbol_c;

        public Arbol_C(NaryTree arbol) {
            this.arbol_c = arbol;
            this.setLayout(null);
            this.setSize(900, 600);
            this.coordenadas = new HashMap<>();
            darcoordenadas(arbol_c.getRoot(), getWidth() / 2, 10, espacioH);
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
                    nd = arbol_c.find2(r);
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
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                    if (nd != null) {
                        Contexto c = (Contexto) nd.getData();
                        JOptionPane.showMessageDialog(null, c.imprimir2());
                    }
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
                        arbol_c.find2(r).setPos(nd.getX(), nd.getY(), radio);
                        //System.out.println("++++++++ND x: " + nd.getX() + " Y : " + nd.getY());
                        //g5.arbol_derivacion.obtenerElemento(r).setPos(nd.getX(), nd.getY(), radio);
                    }
                    repaint();
                }

            };
            this.addMouseListener(mu);
            this.addMouseMotionListener(mu);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawString("arbol binario", (this.getWidth() / 2) - 150, 20);

            if (arbol_c != null) {
                dibujar2(g, arbol_c.getRoot());
            }
            repaint();
        }

        public void dibujar2(Graphics g, Node raiz) {
            g.drawOval(raiz.getX(), raiz.getY(), raiz.getRadio() * 2, raiz.getRadio() * 2);
            //Rectangle r = raiz.getBounds();
            //g.setColor(Color.GREEN);
            //g.drawRect(r.x, r.y, r.width, r.height);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Comic Sans MS", 0, 6));
            Contexto c = (Contexto) raiz.getData();
            g.drawString(c.getNombre() + "", raiz.getX() + 3, raiz.getY() + 10);
            LinkedList hijos = arbol_c.obtenerHijos(raiz);
            for (Object hijo : hijos) {
                Node h = (Node) hijo;
                Node pad = arbol_c.findpadre(h);
                g.setColor(Color.GRAY);
                g.drawLine(h.getX(), h.getY(), pad.getX(), pad.getY() + radio * 2);
                dibujar2(g, (Node) hijo);
            }
        }

        public void darcoordenadas(Node raiz, int x, int y, int espacioH) {
            LinkedList hijos = arbol_c.obtenerHijos(raiz);
            coordenadas.put(raiz, new Point(x, y));
            raiz.setPos(x, y, radio);
            for (Object hijo : hijos) {
                Point punto = coordenadas.get(arbol_c.obtenerPadre(hijo));
                darcoordenadas((Node) hijo, x - espacioH, y + espacioVertical, (espacioH / hijos.size()) + 10);
                x += espacioH;
            }
        }

    }
}
