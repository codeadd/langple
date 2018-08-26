/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*######### PAQUETE DE LA CLASE #########*/
package vista;

/*########  CLASES QUE SE UTILIZARAN PARA EL PROGRAMA ##########*/
import controles.Acciones;
import controles.Control;
import herramientas.Arboles;
import herramientas.Constantes;
import herramientas.Tablas;
import herramientas.Tbl;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
import modelo.Automata;
import modelo.Gramatica;
/**
 *
 * @author brayanstiven
 */
public final class Frame extends JFrame{
    //################################
   //#      BARRA DE MENU           #
  //################################
    private JMenuBar barraMenu;//BARRA MENU
    //###   MENU INICIO     ###
    private JMenu menuInicio;//MENU PRINCIPAL
    private JMenuItem jmNuevo;//ITEM NUEVO
    private JMenuItem jmGuardar;//ITEM GUARDAR
    private JMenuItem jmSalir;//ITEM SALIR
    
    //###   MENU AYUDA      ###
    private JMenu menuAyuda;//MENU AYUDA
    private JMenuItem jmAcerca;//ITEM ACERCA DE
    
    //################################
   //#      BARRA DE HERRAMIENTAS   #
  //################################
    private JToolBar barraHerramientas;
    
    private JPanel barraEstado;//(barra de estado)
    //################################
   //#      MENU DE CLICK DERECHO   #
  //################################
    private JPopupMenu menuClickDerecho;//(menu emergente)
    
    
    //################################
   //#      EDITOR DE TEXTO         #
  //################################
    private JTextPane areaTexto;//AREA QUE CONTIENE EL CODIGO
    private JTextArea linenumbers;//NUMERO DE LINEA 
    private int numLin = 1;//NUMERO DE LINEAS DEL EDITOR
    
    //################################
   //#      OTRAS VARIABLES         #
  //################################
    
    private boolean hasChanged = false; //si se ha cambiado algo en el archivo
    private File currentFile = null; //archivo actual
    private JLabel rutaArchivo; //etiqueta de la ruta del archivo
    private JLabel tamanoArchivo; //etiqueta del tamaño del archivo
    private JLabel CaretPos; //posicion del cursor en el editor
    
    private final EventHandler eventHandler;//CLASE QUE MANEJA LOS EVENTOS
    private final Acciones actionPerformer;//CLASE PARA EL MANEJO DE ARCHIVOS Y FUNCIONES DEL EDITOR
    private final UndoManager undoManager;//PARA FUNCIONALIDADES BASICAS Y CONTROL DEL EDITOR(COPY,CUT...ETC);
    
    private JCheckBoxMenuItem itemLineWrap;//OPCION DE AJUSTE DE LINEA(BARRA DE MENU) PARA EL EDITOR
    private JCheckBoxMenuItem itemShowToolBar;//OPCION PARA MOSTRAR BARRA DE HERRAMIENTAS
    private JCheckBoxMenuItem itemFixedToolBar;//PARA FIJAR LA BARRA DE HERRAMIENTAS(SI ESTA DESACTIVADA SE PUEDE UBICAR
                                               //               EN CUALQUIER LUGAR, SOLO CON ARRASTRARLA)
    private JCheckBoxMenuItem itemShowStatusBar;//OPCION PARA VISUALIZAR LA BARRA DE ESTADO
    
    
    private JMenuItem mbItemUndo;//ITEM DE MENU DESHACER PARA LA BARRA MENU
    private JMenuItem mbItemRedo;//ITEM DE MANU REHACER PARA LA BARRA MENU
    private JMenuItem mpItemUndo;//ITEM DE MENU DESHACER PARA EL MENU CLICK DERECHO
    private JMenuItem mpItemRedo;//ITEM DE MENU REHACER PARA EL MENU CLICK DERECHO
    
    private JButton buttonUndo;//BOTON DESHACER
    private JButton buttonRedo;//BOTON REHACER
    
    private JLabel sbFilePath;//LABEL CON LA RUTA DEL ARCHIVO
    private JLabel sbFileSize;//LABEL CON EL TAMANO DEL ARCHIVO
    private JLabel sbCaretPos;//LABEL CON LA POSICION DEL CURSOR
    
    //################################
   //#      EDITOR PARTE 2          #
  //################################
    private JPanel panelPrincip;//PANEL PRINCIPAL CONTIENE EL EDITOR(JTextPane) Y EL NUMERADOR DE LINEAS(JTextArea)
    private StyledDocument textEditor;//COMPONENTE DE TEXTO QUE ESTARA EN EL EDITOR (ESTE COMPONENTE ES EL QUE PERMITE EL COLOREADO DE SINTAXIS)
    private Control cp;//CONTROLADOR PARA EL COLOREADO DE SINTAXIS
    private JSplitPane splitgeneral;
    //################################
   //#      AUTOMATA Y GRAMATICA    #
  //################################
    private Automata Lenguajes=new Automata();//AUTOMATA DEL LENGUAJE PARA EL ANALISIS LEXICO
    private Gramatica g=new Gramatica();
    private String tokenString;
    String[][] matrizsintactica;
    public Frame(){
       
        lookAndFeel();//METODO PARA EL ASIGNAR EL THEME DEL SISTEMA AL EDITOR
        buildAutomata();//CONSTRUYO EL AUTOMATA
        buildGramatica();
        eventHandler = new EventHandler();//INICIALIZO EL CONTROLADOR DE EVENTOS
        actionPerformer=new Acciones(this);//INICIALIZO MI HERRAMIENTA PARA EL MANEJO DE DOCUMENTOS(ABRIR,GUARDAR...)
        undoManager=new UndoManager();//INICIALIZO MI CONTROLADOR DE FUNCIONES(COPY,CUT...) DEL EDITOR
        undoManager.setLimit(50);//ESTABLECER EL LIMITE DE ACCIONES A REALIZAR
        cp=new Control(this);//INICIALIZO MI CONTROL PARA EL COLOREADO DE SINTAXIS
        

        //AÑADO UN LISTENER AL FRAME PARA SU FUNCION DE CERRADO CONTENIDA EN MI ACTIONPERFORMED
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                actionPerformer.salir();
            }
            
        });
      
        this.setSize(800,600);//DEFINO EL TAMAÑO DE MI FRAME
        this.setLayout(new BorderLayout());//DEFINO EL LAYOUT(ORDEN DE LOS ELEMENTOS) DE MI FRAME
        this.setIconImage(new ImageIcon(getClass().getResource(Constantes.RUTA_PROG_30)).getImage());
        this.setTitle("Editor de texto - Sin titulo");//TITULO DEL FRAME
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//ACCION DE CERRADO SI SE DA CLICK EN LA 'X' ROJA
        this.setLocationRelativeTo(null);//POSICION RELATIVA(AL ASIGNAR NULL APARECE EN EL CENTRO DE LA PANTALLA)
        //##### METODOS DE CONSTRUCION####//
        buildMenuBar();//CONSTRUYO MI BARRA DE MENU
    
        buildEditor();//CONSTRUYO MI EDITOR
        buildToolBar();//CONSTRUYO MI BARRA DE HERRAMIENTAS
        buildStatusBar();//CONSTRUYO MY BARRA DE ESTADO
        buildPopupMenu();//CONSTRUYO MI MENU DEL CLICK DERECHO
        
        this.setJMenuBar(barraMenu);//ASIGNO EL MENUBAR A MI FRAME
        Container c=this.getContentPane();//CITO EL CONTENEDOR DE OBJETOS DE MI FRAME
        c.add(barraHerramientas,BorderLayout.NORTH);//AÑADO LA BARRA DE HERRAMIENTAS A MI CONTENDOR
        c.add(splitgeneral,BorderLayout.CENTER);
    }

    public void lookAndFeel(){
        String sSistemaOperativo = System.getProperty("os.name");//PARA SABER EN QUE SISTEMA OPERATIVO ESTAMOS
        System.out.println(sSistemaOperativo);
        try {
            if(sSistemaOperativo.equals("Windows 8.1")){//SI EL SISTEMA OPERATIVO ES WINDOWS 8.1
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//ASIGNO EL TEMA DE SISTEMA DE WINDOWS
                                                                                    //AL EDITOR
                SwingUtilities.updateComponentTreeUI(this);//ACTUALIZO LOS COMPONENTES DE LA INTERFAZ
            }else{//DE OTRA MANERA
                 UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");//ASIGNO EL TEMA GTK 
                                                                                        //NORMALMENTE SE ENCUENTRA EN LA MAYORIA DE 'SO'
                 SwingUtilities.updateComponentTreeUI(this);//ACTUALIZO LOS COMPONENTES DE LA INTERFAZ
            }
            //####### CAPTURA DE ERRORES ######//
        } catch (UnsupportedLookAndFeelException ex) {}
          catch (ClassNotFoundException ex) {}
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}
        
    }
    
    public void buildAutomata(){
        
        /*###############################
          #        ESTADOS              #
          ###############################*/
        
        
        getLenguajes().AddState("Inicio",true, false);
        getLenguajes().AddState("DIF", false, true);
        getLenguajes().AddState("ERR_DIF", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("MN", false, true);
        getLenguajes().AddState("MEOG", false, true);
        getLenguajes().AddState("MAY",false, true);
        getLenguajes().AddState("MAOG", false, true);
        getLenguajes().AddState("FL", false, true);
        getLenguajes().AddState("ERR_BL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("BL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("PA", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("PC", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("LC", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("LA", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("FLOL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("BLL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_TRUE", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_TRUE2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_TRUE3", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FALSE", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FALSE2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FALSE3", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FALSE4", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ENTL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("CADL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("IG", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("IG2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("SUM", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("RES", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("DIV", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("POR", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("COMTL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_ENTL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_CADL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_NOT", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_NOT2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("NO", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_AND", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_AND2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("Y", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_OR", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("O", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_WHILE", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_WHILE2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_WHILE3", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_WHILE4", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("MTR", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_THEN", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_THEN2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_THEN3", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ENTC", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_ELSE", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_ELSE2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_ELSE3", Boolean.FALSE, Boolean.TRUE); 
        getLenguajes().AddState("SN", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_F", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FLT", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("FLO", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FLTL", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FOR", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("PARA", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_FUN", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("FUN", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_STR", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_STR2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("CAD", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_IF", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("SI", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_INT", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ENT", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ID", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_ID", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_SAY", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("DIGA", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_VOID", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_VOID2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_VOID3", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("VACIO", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_RET", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("ERR_RET2", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("RET", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("COM", Boolean.FALSE, Boolean.TRUE);
        getLenguajes().AddState("PIPE", Boolean.FALSE, Boolean.TRUE);
        
        /*###################################
          #         TRANSICIONES            #
          ###################################*/
         
        //Las que posean x son las expresiones regulares
        getLenguajes().Add("Inicio", "ERR_ID", "&");
        getLenguajes().Add("ERR_ID", "ID", "[a-z|A-Z]([0-9]|[a-z|A-Z])+");
        getLenguajes().Add("Inicio", "ERR_DIF", "!");
        getLenguajes().Add("ERR_DIF", "DIF", "=");
        getLenguajes().Add("Inicio", "MN", "<");
        getLenguajes().Add("MN", "MEOG", "=");
        getLenguajes().Add("Inicio", "MAY", ">");
        getLenguajes().Add("MAY", "MAOG", "=");
        getLenguajes().Add("Inicio", "FL", ";");
        getLenguajes().Add("Inicio", "PA", "(");
        getLenguajes().Add("Inicio", "PC", ")");
        getLenguajes().Add("Inicio", "LC", "}");
        getLenguajes().Add("Inicio", "LA", "{");
        getLenguajes().Add("Inicio", "ERR_FLTL", "$");
        getLenguajes().Add("ERR_FLTL", "FLOL", "([0-9]+\\.[0-9]+)+");
        getLenguajes().Add("Inicio", "ERR_TRUE", "T");
        getLenguajes().Add("ERR_TRUE", "ERR_TRUE2", "r");
        getLenguajes().Add("ERR_TRUE2", "ERR_TRUE3", "u");
        getLenguajes().Add("ERR_TRUE3", "BLL", "e");
        getLenguajes().Add("Inicio", "ERR_FALSE", "F");
        getLenguajes().Add("ERR_FALSE", "ERR_FALSE2", "a");
        getLenguajes().Add("ERR_FALSE2", "ERR_FALSE3", "l");
        getLenguajes().Add("ERR_FALSE3", "ERR_FALSE4", "s");
        getLenguajes().Add("ERR_FALSE4", "BLL", "e");
        getLenguajes().Add("Inicio", "ERR_ENTL", "#");
        getLenguajes().Add("ERR_ENTL", "ENTL", "[0-9]+");
        getLenguajes().Add("Inicio", "ERR_CADL", "\"");
        getLenguajes().Add("ERR_CADL", "CADL", "([0-9]|[a-z|A-Z])*");
        getLenguajes().Add("Inicio", "IG", "=");
        getLenguajes().Add("IG", "IG2", "=");
        getLenguajes().Add("Inicio", "SUM", "+");
        getLenguajes().Add("Inicio", "RES", "-");
        getLenguajes().Add("Inicio", "DIV", "/");
        getLenguajes().Add("DIV", "COMTL", "/");
        getLenguajes().Add("Inicio", "POR", "*");
        getLenguajes().Add("Inicio", "ERR_NOT", "n");
        getLenguajes().Add("ERR_NOT", "ERR_NOT2", "o");
        getLenguajes().Add("ERR_NOT2", "NO", "t");
        getLenguajes().Add("Inicio", "ERR_AND", "a");
        getLenguajes().Add("ERR_AND", "ERR_AND2", "n");
        getLenguajes().Add("ERR_AND2", "Y", "d");
        getLenguajes().Add("Inicio", "ERR_OR", "o");
        getLenguajes().Add("ERR_OR", "O", "r");
        getLenguajes().Add("Inicio", "ERR_WHILE", "w");
        getLenguajes().Add("ERR_WHILE", "ERR_WHILE2", "h");
        getLenguajes().Add("ERR_WHILE2", "ERR_WHILE3", "i");
        getLenguajes().Add("ERR_WHILE3", "ERR_WHILE4", "l");
        getLenguajes().Add("ERR_WHILE4", "MTR", "e");
        getLenguajes().Add("Inicio", "ERR_THEN", "t");
        getLenguajes().Add("ERR_THEN", "ERR_THEN2", "h");
        getLenguajes().Add("ERR_THEN2", "ERR_THEN3", "e");
        getLenguajes().Add("ERR_THEN3", "ENTC", "n");
        getLenguajes().Add("Inicio", "ERR_ELSE", "e");
        getLenguajes().Add("ERR_ELSE", "ERR_ELSE2", "l");
        getLenguajes().Add("ERR_ELSE2", "ERR_ELSE3", "s");
        getLenguajes().Add("ERR_ELSE3", "SN", "e");
        getLenguajes().Add("Inicio", "ERR_F", "f");
        getLenguajes().Add("ERR_F", "ERR_FLT", "l");
        getLenguajes().Add("ERR_FLT", "FLO", "t");
        getLenguajes().Add("ERR_F", "ERR_FOR", "o");
        getLenguajes().Add("ERR_FOR", "PARA", "r");
        getLenguajes().Add("ERR_F", "ERR_FUN", "u");
        getLenguajes().Add("ERR_FUN", "FUN", "n");
        getLenguajes().Add("Inicio", "ERR_STR", "s");
        getLenguajes().Add("ERR_STR", "ERR_STR2", "t");
        getLenguajes().Add("ERR_STR2", "CAD", "r");
        getLenguajes().Add("Inicio", "ERR_IF", "i");
        getLenguajes().Add("ERR_IF", "SI", "f");
        getLenguajes().Add("ERR_IF", "ERR_INT", "n");
        getLenguajes().Add("ERR_INT", "ENT", "t");
        getLenguajes().Add("Inicio", "ERR_BL", "b");
        getLenguajes().Add("ERR_BL", "BL", "l");
        getLenguajes().Add("ERR_STR", "ERR_SAY", "a");
        getLenguajes().Add("ERR_SAY", "DIGA", "y");
        getLenguajes().Add("Inicio", "ERR_VOID", "v");
        getLenguajes().Add("ERR_VOID", "ERR_VOID2", "o");
        getLenguajes().Add("ERR_VOID2", "ERR_VOID3", "i");
        getLenguajes().Add("ERR_VOID3", "VACIO", "d");
        getLenguajes().Add("Inicio", "ERR_RET", "r");
        getLenguajes().Add("ERR_RET", "ERR_RET2", "e");
        getLenguajes().Add("ERR_RET2", "RET", "t");
        getLenguajes().Add("Inicio", "COM", ",");
        getLenguajes().Add("Inicio", "PIPE", "|");
        
 
    }
    
    public void buildGramatica(){
        /*g.getSimbolos().add("D");
        g.getSimbolos().add("T");
        g.getSimbolos().add("V");
        
        g.getProduccion().put("D", new LinkedList<>(Arrays.asList("T ID IG V FL")));
        g.getProduccion().put("T", new LinkedList<>(Arrays.asList("ENT","FLO","CAD","BL")));
        g.getProduccion().put("V", new LinkedList<>(Arrays.asList("ENTL","FLOL","CADL","BLL")));*/
        
        getG().getSimbolos().add("LENG");
        getG().getSimbolos().add("BLOQUE");
        getG().getSimbolos().add("ASIGNACION");
        getG().getSimbolos().add("TIPODATO");
        getG().getSimbolos().add("OPERACION");
        getG().getSimbolos().add("OPERADOR");
        
        getG().getSimbolos().add("IF");
        getG().getSimbolos().add("C");
        getG().getSimbolos().add("E");
        getG().getSimbolos().add("G");
        getG().getSimbolos().add("B");
        getG().getSimbolos().add("H");
        getG().getSimbolos().add("WH");
        
        getG().getSimbolos().add("SA");
        
        getG().getSimbolos().add("NT");
        
        getG().getSimbolos().add("CM");
        
        getG().getSimbolos().add("FUNC");
        getG().getSimbolos().add("DEC");
        getG().getSimbolos().add("DEC2");
        getG().getSimbolos().add("IDF");
                
                ;
        getG().getSimbolos().add("COMA");
        
        getG().getSimbolos().add("FR");
        
        
        getG().getProduccion().put("LENG",  new LinkedList<>(Arrays.asList("FUNC BLOQUE")));
        getG().getProduccion().put("BLOQUE",  new LinkedList<>(Arrays.asList("ASIGNACION","IF","WH","FR","CM","SA","€")));
        getG().getProduccion().put("ASIGNACION", new LinkedList<>(Arrays.asList("TIPODATO ID IG OPERACION FL BLOQUE","ID IG OPERACION FL BLOQUE")));
        getG().getProduccion().put("TIPODATO", new LinkedList<>(Arrays.asList("ENT","FLO","CAD","BL","VACIO")));
        getG().getProduccion().put("OPERADOR", new LinkedList<>(Arrays.asList("SUM", "RES", "POR", "DIV","€")));
        getG().getProduccion().put("OPERACION", new LinkedList<>(Arrays.asList("ENTL OPERADOR OPERACION", "FLTL OPERADOR OPERACION","IDF OPERADOR OPERACION", "CADL OPERADOR OPERACION", "BLL OPERADOR OPERACION", "€")));
        
        getG().getProduccion().put("IF", new LinkedList<>(Arrays.asList("SI B C E")));
        getG().getProduccion().put("C", new LinkedList<>(Arrays.asList("Y B","O B","€")));
        getG().getProduccion().put("E", new LinkedList<>(Arrays.asList("ENTC LA BLOQUE LC G BLOQUE")));
        getG().getProduccion().put("G", new LinkedList<>(Arrays.asList("SN LA BLOQUE LC","€")));
        getG().getProduccion().put("B", new LinkedList<>(Arrays.asList("NT OPERACION H OPERACION")));
        getG().getProduccion().put("H", new LinkedList<>(Arrays.asList("IG2","MAY","MAOG","MEOG","DIF","MN")));
        getG().getProduccion().put("NT", new LinkedList<>(Arrays.asList("NO","€")));
        
        getG().getProduccion().put("IDF", new LinkedList<>(Arrays.asList("ID","FUN ID PA DEC2 PC")));
        
        //While
        getG().getProduccion().put("WH", new LinkedList<>(Arrays.asList("MTR B ENTC LA BLOQUE LC BLOQUE")));
        
        getG().getProduccion().put("CM", new LinkedList<>(Arrays.asList("COMTL BLOQUE")));
        
        getG().getProduccion().put("SA", new LinkedList<>(Arrays.asList("DIGA PA OPERACION PC FL BLOQUE")));
        
        //Funciones
        getG().getProduccion().put("FUNC", new LinkedList<>(Arrays.asList("FUN TIPODATO ID PA DEC PC LA BLOQUE RET OPERACION FL LC FUNC","€")));
        getG().getProduccion().put("DEC", new LinkedList<>(Arrays.asList("TIPODATO ID COMA DEC","€")));
        
        getG().getProduccion().put("DEC2", new LinkedList<>(Arrays.asList("ID COMA DEC2","€")));
        
        getG().getProduccion().put("COMA", new LinkedList<>(Arrays.asList("COM","€")));
        
        //For
        getG().getProduccion().put("FR", new LinkedList<>(Arrays.asList("PARA PA ASIGNACION PIPE B PIPE ASIGNACION PC LA BLOQUE LC BLOQUE")));
        //  for(&x = 5; | &x < 5 | &x = &x +10;){ }        
    }
    
    public void buildEditor(){
        splitgeneral=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgeneral.setResizeWeight(0.999);
        panelPrincip = new JPanel();//INICIALIZO EL CONTENEDOR PRINCIPAL
        panelPrincip.setLayout(new BorderLayout());//ASIGNO UN LAYOUT(ORDEN) AL CONTENEDOR
        
        //EDITOR
        areaTexto = new JTextPane();//INICIALIZO MI AREA DE TEXTO
        areaTexto.setFont(new Font("Monospaced",Font.BOLD,14));//FUENTE POR DEFECTO PARA MI EDITOR(MONOSPACED ES DE LINUX)
        
        //AÑADO ESCUCHAS A MI EDITOR CON MI MANEJADOR DE EVENTOS
        areaTexto.addCaretListener(eventHandler);//ESCUCHA DEL CURSOR DEL EDITOR
        areaTexto.addKeyListener((KeyListener) eventHandler);//ESCUCHA DEL TECLADO
        areaTexto.addMouseListener(eventHandler);//ESCUCHA DEL MOUSE
        areaTexto.getDocument().addUndoableEditListener(eventHandler);//ESCUCHA PARA EL MANEJADOR DE FUNCIONES POR DEFECTO DEL EDITOR
        
        //PROPIEDADES DEL EDITOR
        areaTexto.setBackground(Constantes.BLANCO);//COLOR DE FONDO DEL EDITOR
        areaTexto.setForeground(Constantes.NEGRO_1);//COLOR DE FUENTE DEL EDITOR
        textEditor = getAreaTexto().getStyledDocument();//ASIGNO A MI COMPONENTE DE TEXTO EL ESTILO DE TEXTO DE MI EDITOR
        //PROPIEDADES DE MI AREA NUMERADORA
        linenumbers = new JTextArea(1,5);//ASIGNO TAMAÑP
        linenumbers.setFont(new Font("Monospaced",Font.BOLD,14));//ASGINO FUENTE
        linenumbers.setBackground(Constantes.NEGRO_2);//COLOR DE FONDO
        linenumbers.setEditable(false);//HACEMOS QUE NO SE PUEDA EDITAR
        linenumbers.setForeground(Constantes.BLANCO);
        linenumbers.append(getNumLin()+"\n");//AÑADIMOS UN SALTO DE LINEA
        panelPrincip.add(areaTexto,BorderLayout.CENTER);//AÑADIMOS AL PANEL EL EDITOR CON LAYOUT CENTRADO
        panelPrincip.add(linenumbers,BorderLayout.WEST);//AÑADIMOS EL NUMERADOR CON LAYOUT A LA IZQUIERDA(OESTE)
        //SCROLLPANE PARA MI PANEL (DE ESTA MANERA MUEVO AL TIEMPO MI EDITOR Y MI NUMERADOR)
        //EN EL CONSTRUCTOR LE DECIMOS QUE AÑADA SCROLL VERTICAL Y HORIZONTAL CUANDO SEA NECESARIO
        JScrollPane barrasTexto = new JScrollPane(panelPrincip,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //AÑADIMOS EL MANEJADOR DE VENTOS AL JSCROLLPANE
        barrasTexto.getHorizontalScrollBar().addAdjustmentListener((AdjustmentListener) eventHandler);
        
        //AÑADIMOS PUNCIONES DE TECLADO AL EDITOR
        areaTexto.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK), "none");
        areaTexto.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK), "none");
        areaTexto.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK), "none");
        
        splitgeneral.add(barrasTexto);
    }

    public void buildMenuBar(){
        barraMenu = new JMenuBar();//INICIALIZO MI BARRA DE MENU
        barraMenu.setBounds(0, 0, this.getWidth(), 50);//ASIGNO TAMAÑO A LA BARRA DE MENU
        JMenu menuFile = new JMenu("Archivo");//CREO UN ELEMENTO DE MENU
        JMenuItem itemNew = new JMenuItem("Nuevo");//ITEM DE MENU ('NUEVO')
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));//FUNCION DE TECLADO
        itemNew.setActionCommand("cmd_new");//COMANDO DE ACCION
        itemNew.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_NEW_20)));//ICONO DEL ITEM
        
        JMenuItem itemOpen = new JMenuItem("Abrir");//ITEM 'OPEN'
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));//FUNCION DE TECLADO
        itemOpen.setActionCommand("cmd_open");//COMANDO DE ACCION
        itemOpen.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_OPEN_20)));//ICONO DEL ITEM
        
        JMenuItem itemSave=new JMenuItem("Guardar");//ITEM 'GUARDAR'
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));//FUCION DE TECLADO
        itemSave.setActionCommand("cmd_save");//COMANDO DE ACCION
        itemSave.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_SAVE_20)));//ICONO DEL ITEM
        
        JMenuItem itemSaveAs=new JMenuItem("Guardar Como");//ITEM 'GUARDAR COMO...'
        itemSaveAs.setActionCommand("cmd_saveas");//COMANDO DE ACCION
        itemSaveAs.addActionListener(eventHandler);//ASIGNO CONTROL DE EVENTOS
        itemSaveAs.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_SAVEAS_20)));//ICONO DEL ITEM

        JMenuItem itemExit=new JMenuItem("Salir");//ITEM 'SALIR'
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK));//FUCION DE TECLADO
        itemExit.setActionCommand("cmd_exit");//COMANDO DE ACCION
        itemExit.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_EXIT_20)));//ICONO DEL ITEM
        
        menuFile.addSeparator();
        menuFile.add(itemNew);//AÑADON EL ITEM NUEVO AL MENU
        menuFile.add(itemOpen);//AÑADO EL ITEM OPEN AL MENU
        menuFile.add(itemSave);//AÑADO EL ITEM SAVE AL MENU
        menuFile.add(itemSaveAs);//AÑADO EL ITEM SAVEAS AL MENU
        menuFile.addSeparator();//AÑADO UN SEPARADOR(LINEA SEPARADORA)
        menuFile.addSeparator();//AÑADO UN SEPARADOR
        menuFile.add(itemExit);//AÑADO EL ITEM EXIT AL MENU
        
        //---------------------------------
        
        JMenu menuEdit=new JMenu("Editar");//MENU EDITAR
        
        mbItemUndo = new JMenuItem("Deshacer");//ITEM DESHACER
        mbItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));//FUCION DE TECLADO
        mbItemUndo.setEnabled(false);//DESHABILITADO INICIALMENTE
        mbItemUndo.setActionCommand("cmd_undo");//ACCION DE COMANDO
        mbItemUndo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_UNDO_20)));//ICONO DEL ITEM
        
        mbItemRedo=new JMenuItem("Rehacer");//ITEM REHACER
        mbItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));//FUNCION DE TECLADO
        mbItemRedo.setEnabled(false);//DESHABILITADO INICIALMENTE
        mbItemRedo.setActionCommand("cmd_redo");//COMANDO DE ACCION
        mbItemRedo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_REDO_20)));//ICONO DEL ITEM
        
        JMenuItem itemCut= new JMenuItem("Cortar");//ITEM CORTAR
        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));//FUNCION DE TECLADO
        itemCut.setActionCommand("cmd_cut");//COMANDO DE ACCION
        itemCut.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_CUT_20)));//ICONO DEL ITEM
        
        JMenuItem itemCopy = new JMenuItem("Copiar");//ITEM COPIAR
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));//FUCNION DE TECLADO
        itemCopy.setActionCommand("cmd_copy");//COMANDO DE ACCION
        itemCopy.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_COPY_20)));//ICONO DEL ITEM
        
        JMenuItem itemPaste = new JMenuItem("Pegar");//ITEM PEGAR
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));//FUNCION DE TECLADO
        itemPaste.setActionCommand("cmd_paste");//COMANDO DE ACCION
        itemPaste.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_PASTE_20)));//ICONO DEL ITEM
        
        JMenuItem itemSearch = new JMenuItem("Buscar");//ITEM BUSCAR
        itemSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));//FUNCION DE TECLADO
        itemSearch.setActionCommand("cmd_search");//COMANDO DE ACCION
        itemSearch.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_BUSRCAR_20)));//ICONO DEL ITEM
 
        JMenuItem itemSearchNext = new JMenuItem("Buscar siguiente");//ITEM BUSCAR SIGUIENTE
        itemSearchNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));//FUNCION DE TECLADO
        itemSearchNext.setActionCommand("cmd_searchnext");//COMANDO DE ACCION
        itemSearchNext.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_BUSRCAR_20)));//ICONO DEL ITEM
        

        JMenuItem itemSelectAll = new JMenuItem("Seleccionar todo");//ITEM SELECCIONAR TODO
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));//FUNCION DE TECLADO
        itemSelectAll.setActionCommand("cmd_selectall");//COMANDO DE ACCION
        itemSelectAll.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_SELECIONAR_TODO_20)));//ICONO DEL ITEM
        //AÑADO ITEMS
        menuEdit.add(mbItemUndo);//AÑADO ITEM DESHACER AL MENU
        menuEdit.add(mbItemRedo);//AÑADO ITEM REHACER AL MENU
        menuEdit.addSeparator();//AÑADO SEPARADOR
        menuEdit.add(itemCut);//AÑADO ITEM  COPIAR
        menuEdit.add(itemCopy);//AÑADO ITEM CORTAR
        menuEdit.add(itemPaste);//AÑADO ITEM PEGAR
        menuEdit.addSeparator();//AÑADO SEPARADOR
        menuEdit.add(itemSearch);//AÑADO ITEM BUSCAR AL MENU
        menuEdit.add(itemSearchNext);//AÑADO ITEM BUSCAR SIGUIENTE

        menuEdit.addSeparator();//AÑADO SEPARADOR
        menuEdit.add(itemSelectAll);//AÑADO ITEM SELCCIONAR TODO
        
        //--------------------------------
        
        JMenu menuTools = new JMenu("Opciones");//MENU OPCIONES
        
        itemLineWrap = new JCheckBoxMenuItem("Ajuste de Linea");//ITEM AJUSTE DE LINEA
        itemLineWrap.setSelected(true);//SELECIONADA POR DEFECTO
        itemLineWrap.setActionCommand("cmd_linewrap");//COMANDO DE ACCION
        
        itemShowToolBar=new JCheckBoxMenuItem("Ver Barra De Herramientas");//ITEM VER BARRA DE HERRAMIENTAS
        itemShowToolBar.setSelected(true);//SELECIONADA POR DEFECTO
        itemShowToolBar.setActionCommand("cmd_showToolbar");//COMANDO DE ACCION
        
        itemFixedToolBar = new JCheckBoxMenuItem("Fijar Barra De Herramientas");//ITEM FIJAR BARRA DE HERRAMIENTAS
        itemFixedToolBar.setSelected(true);//SELECCIONADA POR DEFECTO
        itemFixedToolBar.setActionCommand("cmd_fixedtoolbar");//COMANDO DE ACCION
        
        itemShowStatusBar = new JCheckBoxMenuItem("Ver barra de estado");//ITEM VER BARRA DE ESTADO
        itemShowStatusBar.setSelected(true);//SELECCIONADA POR DEFECTO
        itemShowToolBar.setActionCommand("cmd_showstatusbar");//COMANDO DE ACCION
        
        JMenuItem itemFont = new JMenuItem("Fuente de letra");//ITEM PARA SELECCIONAR LA FUENTE
        itemFont.setActionCommand("cmd_font");//COMANDO DE ACCION
        itemFont.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_FONT_20)));//ICONO DEL ITEM
        
        JMenuItem itemBackgroundColor = new JMenuItem("Color de fondo");//ITEM DE COLOR DE FONDO
        itemBackgroundColor.setActionCommand("cmd_backgroundcolor");//COMANDO DE ACCION
        itemBackgroundColor.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_COLOR_FONDO_20)));//ICONO DEL ITEM
        
        //AÑADO ITEMS AL MENU HERRAMIENTAS
        menuTools.add(itemLineWrap);//AÑADO AJUSTE DE LINEA
        menuTools.add(itemShowToolBar);//AÑADO VER BARRA DE HERRAMIENTAS
        menuTools.add(itemFixedToolBar);//AÑADO FIJAR BARRA DE HERRAMIENTAS
        menuTools.add(itemShowStatusBar);//AÑADO BARRA DE ESTADO
        menuTools.addSeparator();//AÑADO SEPARADOR
        menuTools.add(itemFont);//AÑADO SELECCIONAR FUENTE
        menuTools.add(itemBackgroundColor);//AÑADO SELECIONAR COLOR FONDO
        
        JMenu menuHelp = new JMenu("Ayuda");//MANU AYUDA
        
        JMenuItem itemAbout = new JMenuItem("Acerca de");//ITEM ACERCA DE
        itemAbout.setActionCommand("cmd_about");//COMANDO DE ACCION
        
        menuHelp.add(itemAbout);//AÑADO ITEM ACERCA DE
        
        barraMenu.add(menuFile);//AÑADO A MI BARRA DE MENU MI MENU FILE
        barraMenu.add(Box.createHorizontalStrut(5));//CREO UNA CAJA CONTENEDORA
        barraMenu.add(menuEdit);//AÑADO A MI BARRA DE MENU MI MENU EDIT
        barraMenu.add(Box.createHorizontalStrut(5));//CREO UNA CAJA CONTENEDORA
        barraMenu.add(menuTools);//AÑADO A MI BARRA DE MENU MI MENU HERRAMIENTAS
        barraMenu.add(Box.createHorizontalStrut(5));
        barraMenu.add(menuHelp);//AÑADO A MI BARRA DE MANU MI MENU AYUDA
        
        //AÑADIR EVENTOS
        for(Component c1 : barraMenu.getComponents()){//RECORRO LOS COMPONETES DE MI BARRA MENU
            if(c1.getClass().equals(javax.swing.JMenu.class)){//FILTRO POR EL TIPO DE CLASE
                for(Component c2 : ((JMenu) c1).getMenuComponents()){//RECORRO LOS ELEMENTOS DE MENU
                    if(!c2.getClass().equals(javax.swing.JPopupMenu.Separator.class)){//DECIMOS QUE NO TOME LOS ITEMS DEL POPUPMENU
                        ((JMenuItem) c2).addActionListener(eventHandler);//AÑADO EL CONTROLADOR DE EVENTOS
                    }
                }
            }
        }
    }
    
    public void buildToolBar(){
        barraHerramientas = new JToolBar();//INICIALIZO MI BARRA DE HERRAMIENTAS
        barraHerramientas.setFloatable(false);

        JButton buttonNew = new JButton();//BOTON 'NUEVO'
        buttonNew.setToolTipText("Nuevo");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        buttonNew.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_NEW_30)));//ICONO BOTON
        buttonNew.setActionCommand("cmd_new");//COMANDO DE ACCION
        
        JButton buttonOpen = new JButton();//BOTON 'ABRIR'
        buttonOpen.setToolTipText("Abrir");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        buttonOpen.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_ABRIR_30)));//ICONO BOTON
        buttonOpen.setActionCommand("cmd_open");//COMANDO DE ACCION
        
        JButton buttonSave =  new JButton();//BOTON 'GUARDAR'
        buttonSave.setToolTipText("Guardar");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        buttonSave.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_SAVE_30)));//ICONO BOTON
        buttonSave.setActionCommand("cmd_save");//COMANDO DE ACCION
        
        JButton buttonSaveAs = new JButton();//BOTON 'GUARDAR COMO...'
        buttonSaveAs.setToolTipText("Guardar como....");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonSaveAs.setText("saveAs");
        buttonSaveAs.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_SAVEAS_30)));//ICONO BOTON
        buttonSaveAs.setActionCommand("cmd_saveas");//COMANDO DE ACCION
        
        buttonUndo=new JButton();//BOTON DESHACER
        buttonUndo.setEnabled(false);//DESHABILITADO POR DEFECTO
        buttonUndo.setToolTipText("Deshacer");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonUndo.setText("Undo");
        buttonUndo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_UNDO_30)));//ICONO BOTON
        buttonUndo.setActionCommand("cmd_undo");//COMANDO DE ACCION
        
        buttonRedo=new JButton();//BOTON REHACER
        buttonRedo.setEnabled(false);//DESHABILITADO POR DEFECTO
        buttonRedo.setToolTipText("Rehacer");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonRedo.setText("Redo");
        buttonRedo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_REDO_30)));//ICONO BOTON
        buttonRedo.setActionCommand("cmd_redo");//COMANDO DE ACCION
        
        JButton buttonCut =  new JButton();//BORTON CORTAR
        buttonCut.setToolTipText("Cortar");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonCut.setText("Cut");
        buttonCut.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_CUT_30)));//ICONO BOTON
        buttonCut.setActionCommand("cmd_cut");//COMANDO DE ACCION
        
        JButton buttonCopy= new JButton();//BOTON COPIAR
        buttonCopy.setToolTipText("Copiar");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonCopy.setText("Copy");
        buttonCopy.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_COPY_30)));//ICONO BOTON
        buttonCopy.setActionCommand("cmd_copy");//COMANDO DE ACCION
        
        JButton buttonPaste=new JButton();//BOTON PEGAR
        buttonPaste.setToolTipText("Pegar");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        buttonPaste.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_PASTE_30)));//ICONO BOTON
        buttonPaste.setActionCommand("cmd_paste");//COMNDO DE ACCION
        
        JButton buttonTokens=new JButton();//BOTON TABLA
        buttonTokens.setToolTipText("TOKENS");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        buttonTokens.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_TABLA_30)));//ICONO BOTNO
        //AÑADO EVENTO DE ACCION MEDIANTE UNA FUNCION LAMBDA
        buttonTokens.addActionListener(e->{
            //CREO UNA TABLA
            Tablas t=new Tablas("TOKENS",getLenguajes().getListatokens(),getLenguajes().getListaerrores(),getLenguajes().getListaliterales());
            //LA HAGO VISIBLE
            t.setVisible(true);
        });
        
        JButton buttonTabla=new JButton();//BOTON TABLA
        buttonTabla.setToolTipText("TABLA");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        buttonTabla.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_TABLA_30)));//ICONO BOTNO
        //AÑADO EVENTO DE ACCION MEDIANTE UNA FUNCION LAMBDA
        buttonTabla.addActionListener(e->{
            //CREO UNA TABLA
            Tablas t=new Tablas("TABLA",getG().tabla());
            //LA HAGO VISIBLE
            t.setVisible(true);
        });
        
        
        JButton buttonPila=new JButton();//BOTON TABLA
        buttonPila.setToolTipText("PILA");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        buttonPila.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_RUN_30)));//ICONO BOTNO
        //AÑADO EVENTO DE ACCION MEDIANTE UNA FUNCION LAMBDA
        buttonPila.addActionListener(e->{
            /*g.tabla();
            g.reconocer(tokenString);*/
            //CREO UNA TABLA
            getG().tabla();
            matrizsintactica=getG().reconocer(getTokenString());
            Tablas t=new Tablas("SINTACTICO", matrizsintactica);
            //LA HAGO VISIBLE
            t.setVisible(true);
            getG().leerArchivo(this.getCurrentFile().getPath());
            //this.actionPerformer.guardar(this.g.getErrores());
        });
        
        JButton arbol_d=new JButton();//BOTON TABLA
        arbol_d.setToolTipText("arbol");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        arbol_d.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_INFO_30)));//ICONO BOTNO
        //AÑADO EVENTO DE ACCION MEDIANTE UNA FUNCION LAMBDA
        arbol_d.addActionListener(e->{
           if(getG().getErrores()==false){
               Arboles a =  new Arboles(getG().getArbol_derivacion(), 0);
               a.setVisible(true);
           }
        });
        
        JButton arbol_c=new JButton();//BOTON TABLA
        arbol_c.setToolTipText("arbol");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        arbol_c.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_INFO_30)));//ICONO BOTNO
        //AÑADO EVENTO DE ACCION MEDIANTE UNA FUNCION LAMBDA
        arbol_c.addActionListener(e->{
           if(getG().getErrores()==false){
               Arboles a =  new Arboles(getG().getArbolContexto(), 1);
               a.setVisible(true);
           }
        });
        
        JButton buttonInfo=new JButton();//BOTON TABLA
        buttonInfo.setToolTipText("PILA");//TEXTO QUE APARECE AL COLOCAR EL MOUSE ENCIMA
        //buttonPaste.setText("Paste");
        buttonInfo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_INFO_30)));//ICONO BOTNO
        //AÑADO EVENTO DE ACCION MEDIANTE UNA FUNCION LAMBDA
        buttonInfo.addActionListener(e->{
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("file:///run/media/brayan/ACB2BBB8B2BB857C/Users/brayanstiven/Documents/NetBeansProjects/Editor(Beta4)/index.html"));
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        //AÑADO ELEMENTOS A MI BARRA DE HERRAMIENTAS
        barraHerramientas.addSeparator();
        barraHerramientas.addSeparator();
        barraHerramientas.add(buttonNew);//AÑADO BOTON NUEVO
        barraHerramientas.add(buttonOpen);//AÑADO BOTON ABRIR
        barraHerramientas.add(buttonSave);//AÑADO BOTON GUARDAR
        barraHerramientas.add(buttonSaveAs);//AÑADO BOTON GUARDAR COMO...
        barraHerramientas.addSeparator();//AÑADO SEPARADOR
        barraHerramientas.addSeparator();//AÑADO SEPARADOR
        barraHerramientas.add(buttonUndo);//AÑADO BOTON DESHACER
        barraHerramientas.add(buttonRedo);//AÑADO BOTON REHACER
        barraHerramientas.addSeparator();//AÑADO SEPARADOR
        barraHerramientas.addSeparator();
        barraHerramientas.add(buttonCut);//AÑADOR BOTON CORTAR
        barraHerramientas.add(buttonCopy);//AÑADOR BOTON COPIAR
        barraHerramientas.add(buttonPaste);//AÑADOR BOTON PEGAR
        barraHerramientas.addSeparator();//ANADO SEPARADOR
        barraHerramientas.addSeparator();
        barraHerramientas.add(buttonTokens);
        barraHerramientas.add(buttonTabla);//AÑADO BOTON TABLA
        barraHerramientas.add(buttonPila);
        barraHerramientas.addSeparator();
        barraHerramientas.addSeparator();
        barraHerramientas.add(arbol_d);
        barraHerramientas.add(arbol_c);
        barraHerramientas.add(buttonInfo);

        
        //AÑADO CONTROLADOR DE EVENTOS A MIS BOTONES
        for(Component c: barraHerramientas.getComponents()){//RECORRO MI BARA DE HERRAMENTAS
            if(c.getClass().equals(javax.swing.JButton.class)){//FILTRO LOS ELEMENTOS POR TIPO BOTON
                JButton jb = (JButton) c;//BOTON TEMPORAL
                jb.setMargin(new Insets(0,0,0,0));//CAMBIO MARGENES DEL BOTON
                jb.addActionListener(eventHandler);//AÑADO CONTROLADOR DE EVENTOS
            }
        }
        
    }
    
    public void buildStatusBar(){
        barraEstado = new JPanel();//PANEL DE BARA DE ESTADO
        barraEstado.setLayout(new BoxLayout(barraEstado, BoxLayout.LINE_AXIS));//ASIGNO UN LAYOUT
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLoweredBevelBorder(),
                            BorderFactory.createEmptyBorder(5,5,5,5)));//CREO UN ESTILO DE BORDE
        
        sbFilePath = new JLabel(".....");//INICIALIZO LABEL RUTA DE ARCHIVO
        sbFileSize = new JLabel("");//INCIIALIZO LABLE TAMAÑO ARCHIVO
        sbCaretPos = new JLabel("...");//INICIALIZO LABEL POSICION CURSOR
        //statusBar.setBackground(Constantes.GRIS_OSCURO);
        
        //AÑADO ELEMENTOS AL PANEL
        barraEstado.add(sbFilePath);//AÑADO LABEL RUTA ARCHIVO
        barraEstado.add(Box.createRigidArea(new Dimension(10,0)));
        barraEstado.add(sbFileSize);//AÑADO LABEL TAMAÑO ARCHIVO
        barraEstado.add(Box.createRigidArea(new Dimension(10,0)));
        barraEstado.add(Box.createHorizontalGlue());
        barraEstado.add(sbCaretPos);//AÑADO LABEL POSICION CURSOR
        splitgeneral.add(barraEstado);
    }
    
    public void buildPopupMenu(){
        menuClickDerecho = new JPopupMenu();//INICIALIZO MENU DE CLICK DERECHO
        //jPopupMenu.setBackground(Constantes.GRIS_OSCURO);
        mpItemUndo = new JMenuItem("Deshacer");//ITEM DESHACER
        mpItemUndo.setEnabled(false);//DESHABILITADO POR DEFECTO
        mpItemUndo.setActionCommand("cmd_undo");//COMANDO DE ACCION
        mpItemUndo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_UNDO_20)));//ICONO DE ITEM
        
        mpItemRedo=new JMenuItem("Rehacer");//ITEM REHACER
        mpItemRedo.setEnabled(false);//DESHABILITADO POR DEFECTO
        mpItemRedo.setActionCommand("cmd_redo");//COMANDO DE ACCION
        mpItemRedo.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_REDO_20)));//ICONO ITEM
        
        JMenuItem itemCut = new JMenuItem("Cortar");//ITEM CORTAR
        itemCut.setActionCommand("cmd_cut");//COMANDO DE ACCION
        itemCut.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_CUT_20)));//ICONO ITEM
        
        JMenuItem itemCopy = new JMenuItem("Copiar");//ITEM COPIAR
        itemCopy.setActionCommand("cmd_copy");//COMANDO DE ACCION
        itemCopy.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_COPY_20)));//ICONO ITEM
        
        JMenuItem itemPaste = new JMenuItem("Pegar");//ITEM PEGAR
        itemPaste.setActionCommand("cmd_paste");//COMANDO DE ACCION
        itemPaste.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_PASTE_20)));//ICONO ITEM

        JMenuItem itemSearch = new JMenuItem("Buscar");//ITEM BUSCAR
        itemSearch.setActionCommand("cmd_search");//COMANDO DE ACCION
        itemSearch.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_BUSRCAR_20)));//ICONO ITEM
        
        JMenuItem itemSearchNext = new JMenuItem("Buscar siguiente");//ITEM BUSCAR SIGUENTE
        itemSearchNext.setActionCommand("cmd_searchnext");//COMANDO DE ACCION
        itemSearchNext.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_BUSRCAR_20)));//ICONO ITEM
        
        JMenuItem itemSelectAll = new JMenuItem("Seleccionar todo");//ITEM SELECCIONAR TODO
        itemSelectAll.setActionCommand("cmd_selectall");//COMANDO DE ACCION
        itemSelectAll.setIcon(new ImageIcon(getClass().getResource(Constantes.RUTA_SELECIONAR_TODO_20)));//ICONO ITEM
 
        //AÑADI ELEMENTOS AL POPUMENU
        menuClickDerecho.add(mpItemUndo);//AÑADO ITEM DESHACER
        menuClickDerecho.add(mpItemRedo);//AÑADO ITEM REHACER
        menuClickDerecho.addSeparator();//AÑADO SEPARADOR
        menuClickDerecho.add(itemCut);//AÑADO CORTAR
        menuClickDerecho.add(itemCopy);//AÑADO COPIAR
        menuClickDerecho.add(itemPaste);//AÑADO PEGAR
        menuClickDerecho.addSeparator();//AÑADO SEPARADOR
        menuClickDerecho.add(itemSearch);//AÑADO ITEM BUSCAR
        menuClickDerecho.add(itemSearchNext);//AÑADO ITEM BUSCAR SIGUIENTE
        menuClickDerecho.addSeparator();//AÑADO SEPARADOR
        menuClickDerecho.add(itemSelectAll);//AÑADOR SELECCIONAR TODO
        
        //AÑADO CONTROLADOR DE EVENTOS A LOS COMPONENTES
        for(Component c : menuClickDerecho.getComponents()){//RECORRO LOS ELEMENTOS DEL POPUPMENU
            if(c.getClass().equals(javax.swing.JMenuItem.class)){//FILTRO LOS ELEMENTOS POR MENUITEM
                ((JMenuItem) c).addActionListener(eventHandler);//AÑADO EL CONTROLADOR DE EVENTOS;
            }
        }
    }
    
    private void showPopuMenu(MouseEvent me){
        //FUNCION PARA MOSTRAR EL POPUP MENU
        if(me.isPopupTrigger() == true){//SI SE DISPARA UN CLICK DERECHO
            menuClickDerecho.show(me.getComponent(), me.getX(), me.getY());//SE MUESTRA EL POPUPMENU DONDE 
                                                                           //DONDE OCURRIO EL EVENTO
        }
    }
    
    
    //ACTUALIZO LOS CONTROLES
    public void updateControls(){
        boolean canUndo = undoManager.canUndo();//BOOLEANO PARA SABER EL ESTADO DEL DESHACER DEL UNDOMANAGER
        boolean canRedo = undoManager.canRedo();//BOOLEANO PARA SABER EL ESTADO DEL REHACER DEL UNDOMANAGER
        
        //ASIGNO EL BOOLEANO CORRESPONDIENTE AL LOS ELEMENTOS PARA SABER SI SE PUEDE 
        //EJECUTAR LA FUNCION DEL ITEM Y HABILITAR O DESABILITAR LA OPCION
        mbItemUndo.setEnabled(canUndo);
        mbItemRedo.setEnabled(canRedo);
        
        buttonUndo.setEnabled(canUndo);
        buttonRedo.setEnabled(canRedo);
        
        mpItemUndo.setEnabled(canUndo);
        mpItemRedo.setEnabled(canRedo);
    }
   
    
    /**
     * @return the eventHandler
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * @return the actionPerformer
     */
    public Acciones getActionPerformer() {
        return actionPerformer;
    }

    /**
     * @return the undoManager
     */
    public UndoManager getUndoManager() {
        return undoManager;
    }

  
    /**
     * @return the textEditor
     */
    public StyledDocument getTextEditor() {
        return textEditor;
    }

    /**
     * @param textEditor the textEditor to set
     */
    public void setTextEditor(StyledDocument textEditor) {
        this.textEditor = textEditor;
    }

  
    /**
     * @return the cp
     */
    public Control getCp() {
        return cp;
    }

    /**
     * @param cp the cp to set
     */
    public void setCp(Control cp) {
        this.cp = cp;
    }

    /**
     * @return the currentFile
     */
    public File getCurrentFile() {
        return currentFile;
    }

    /**
     * @param currentFile the currentFile to set
     */
    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    /**
     * @return the Lenguajes
     */
    public Automata getLenguajes() {
        return Lenguajes;
    }

    /**
     * @param Lenguajes the Lenguajes to set
     */
    public void setLenguajes(Automata Lenguajes) {
        this.Lenguajes = Lenguajes;
    }

    /**
     * @return the g
     */
    public Gramatica getG() {
        return g;
    }

    /**
     * @param g the g to set
     */
    public void setG(Gramatica g) {
        this.g = g;
    }

    /**
     * @return the tokenString
     */
    public String getTokenString() {
        return tokenString;
    }

    /**
     * @param tokenString the tokenString to set
     */
    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    
    /*##############################################################
        CLASE PARA INTERNA PARA MANEJAR LOS EVENTOS DEL PROGRAMA
      ##############################################################
    */
    
    class EventHandler extends MouseAdapter implements ActionListener,
                                                       CaretListener,
                                                       UndoableEditListener,
                                                       KeyListener,
                                                       AdjustmentListener{

        public void res(){
             try{
                cp.colores();//COLOCO COLRES
                LinkedList<String> tokens = getLenguajes().Reconocer(areaTexto.getText());//AÑALIZADOR LEXICO
                setTokenString("");
        
                for (int i = 0; i < tokens.size(); i++) {
                    if(!(i+1 == tokens.size())){
                        setTokenString(getTokenString() + tokens.get(i) + " ");
                    }else{
                        setTokenString(getTokenString() + tokens.get(i));
                    }

                }
                
                /*g.reconocer(tokenString);*/
            }catch(BadLocationException ex){
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null,ex);
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String ac = e.getActionCommand();//CITO EL COMANDO DE ACCION DEL EVENTO
            
            //REALIZO LA CORRESPONDIENTE ACCION UBICADA EN EL ACTIONEVENT 
            if(ac.equals("cmd_new") == true){
                actionPerformer.nuevo();//NUEVO DOCUMENTO
            }else if(ac.equals("cmd_open")==true){
                String x=actionPerformer.abrir();//ABRIR DOCUMENTO
                areaTexto.setStyledDocument(textEditor);
                res();
                areaTexto.setText(x);
            }else if(ac.equals("cmd_save")){
                actionPerformer.guardar(getG().getErrores());//GUARDAR DOCUMENTO
                
            }else if(ac.equals("cmd_saveas")){
                actionPerformer.guardarComo(getG().getErrores());//GUARDAR COMO ...
            }else if(ac.equals("cmd_exit")){
                actionPerformer.salir();//SALIR
            }else if(ac.equals("cmd_undo")){
                actionPerformer.deshacer();//DESHACER
            }else if(ac.equals("cmd_redo")){
                actionPerformer.rehacer();//REHACER
            }else if(ac.equals("cmd_cut")){
                areaTexto.cut();//CORTAR
            }else if(ac.equals("cmd_copy")){
                areaTexto.copy();//COPIAR
                System.out.println(""+getSize());
            }else if(ac.equals("cmd_paste")){
                areaTexto.paste();//PEGAR
            }else if(ac.equals("cmd_search")){
                actionPerformer.buscar();//BUSCAR
            }else if(ac.equals("cmd_searchnext")){
                actionPerformer.buscarSiguiente();//BUSCAR SIGUIENTE
            }else if(ac.equals("cmd_selectall")){
                areaTexto.selectAll();//SELECCIONAR TODO
            }else if(ac.equals("cmd_showtoolbar")){
                barraHerramientas.setVisible(!barraHerramientas.isVisible());//VER BARRA DE HERRAMIENTAS
            }else if(ac.equals("cmd_fixedtoolbar")){
                barraHerramientas.setFloatable(!barraHerramientas.isFloatable());//FIJAR BARRA DE HERRAMIENTAS
            }else if(ac.equals("cmd_showstatusbar")){
                barraEstado.setVisible(!barraEstado.isVisible());//VER BARRA DE ESTADO
            }else if(ac.equals("cmd_font")){
                actionPerformer.elegirFuente();//SELECCIONAR FUENTE
            }else if(ac.equals("cmd_backgroundcolor")){
                actionPerformer.elegirFondo();//SELECCIONAR COLOR FONDO
            }else if(ac.equals("cmd_about")){
                JOptionPane.showMessageDialog(new JFrame(),"Editor","Acerca de",JOptionPane.INFORMATION_MESSAGE);//ACERCA DE
            }
        }

        int aux=0;//AUXILIAR PARA CONTAR LINEAS
        
        @Override
        public void caretUpdate(CaretEvent e) {
            
            //ACTUALIZAR CURSOR
            int n = areaTexto.getDocument().getDefaultRootElement().getElementCount();//CITO EL NUMERO DELINEAS DEL DOCUMENTO
            if(n != aux){
                setNumLin(n);//ASIGNO N A EL NUMERO DE LINEAS
                linenumbers.setText("");//LIMPIO EL TEXTO
                for(int i = 1;i<=numLin;i++){//CICLO HASTA EL NUMERO DE LINEAS
                    linenumbers.append(i+"\n");//AÑADO LAS LINEAS
                }
                aux=n;//LE DIGO A AUZ QUE ES IGUAL A N
            }           
        }

        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            updateControls();//ACTUALIZO CONTROLES
            
            hasChanged = true;//DIGO QUE MI DOCUMENTO HA SIDO MODIFICADO
        }

        @Override
        public void mousePressed(MouseEvent e) {
            showPopuMenu(e);//ENVIO EL EVENTO AL METODO MOSTRAR POPUPMENU
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopuMenu(e);//ENVIO EL EVENTO AL METODO MOSTRAR POPUPMENU
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        
        
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            SimpleAttributeSet attr = new SimpleAttributeSet();//ATRIBUTO PARA CREAR TEXTO
            if(e.getKeyCode() == KeyEvent.VK_TAB){//SI EL EVENTO ES UN TAB
                try {
                    e.consume();//CONSUMO EL EVENTO 
                    areaTexto.getStyledDocument().insertString(areaTexto.getCaretPosition(), "    ", attr);//INSERTO UN PEDAZO DE TEXTO
                } catch (BadLocationException ble) {
                    System.out.println("Bad location!!!");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
           res();
        }

        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            areaTexto.validate();//VALIDO COMPONENTES
            areaTexto.updateUI();//ACTUALIZO COMPONENTES
        }
    }
 
    /*########## GET'S Y SET'S #############*
    
    /**
     * @return the hasChanged
     */
    public boolean documentHasChanged() {
        return hasChanged;
    }

    /**
     * @param hasChanged the hasChanged to set
     */
    public void setDocumentChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    /**
     * @return the areaTexto
     */
    public JTextPane getAreaTexto() {
        return areaTexto;
    }

    /**
     * @param areaTexto the areaTexto to set
     */
    public void setAreaTexto(JTextPane areaTexto) {
        this.areaTexto = areaTexto;
    }

    /**
     * @return the linenumbers
     */
    public JTextArea getLinenumbers() {
        return linenumbers;
    }

    /**
     * @param linenumbers the linenumbers to set
     */
    public void setLinenumbers(JTextArea linenumbers) {
        this.linenumbers = linenumbers;
    }


    /**
     * @return the numLin
     */
    public int getNumLin() {
        return numLin;
    }

    /**
     * @param numLin the numLin to set
     */
    public void setNumLin(int numLin) {
        this.numLin = numLin;
    }

    /**
     * @return the filePath
     */
    public JLabel getFilePath() {
        return rutaArchivo;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(JLabel filePath) {
        this.rutaArchivo = filePath;
    }

    /**
     * @return the fileSize
     */
    public JLabel getFileSize() {
        return tamanoArchivo;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(JLabel fileSize) {
        this.tamanoArchivo = fileSize;
    }

    /**
     * @return the CaretPos
     */
    public JLabel getCaretPos() {
        return CaretPos;
    }

    /**
     * @param CaretPos the CaretPos to set
     */
    public void setCaretPos(JLabel CaretPos) {
        this.CaretPos = CaretPos;
    }
}
