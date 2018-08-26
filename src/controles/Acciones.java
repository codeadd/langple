/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;


import herramientas.JFontChooser;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import modelo.Generador2;
import vista.Frame;

/**
 *
 * @author brayanstiven
 */
public class Acciones {
    
    private final Frame editor;//INSTANCIA DEL FRAME
    private String ultimaBusqueda = "";//ULTIMA BUSQUEDA DE PALABRA EN EL EDITOR
     
    /**
     *Constructor
     *@param Frame clase del editor
     */
    
    public Acciones(Frame editor){
        this.editor = editor;//IGUALO AL EDITOR DE LA CLASE Y EL QUE ENTRA POR PARAMETRO
    }
    
    
    public void nuevo(){//NUEVO DOCUMENTO
        if(editor.documentHasChanged() == true){ //SI EL DOCUMENTO HA SIDO MODIFICADO
            //VERIFICAR SI EL USUARIO QUERE GUARDAR 
            int option = JOptionPane.showConfirmDialog(editor,"¿QUIERE GUARDAR CAMBIOS?");
            
            switch (option){
                case JOptionPane.YES_OPTION://EN CASO AFIRMATIVO
                    guardar(editor.getG().getErrores());//GUARDAMOS
                break;
                case JOptionPane.CANCEL_OPTION://DE OTRO MODO
                    return;//NO HACEMOS NADA
            }
        }
        
        editor.setTitle("Editor-Sin Titulo");//ESTABLECEMOS DE NUEVO EL TITULO DEL EDITOR
        editor.getAreaTexto().setText("");//LIMPIAMOS EL AREA DE TEXTO
        editor.getLinenumbers().setText("");//LIMPIAMOS EL PANEL DE LINEAS
        
        editor.getUndoManager().die();//REINICIAMOS EL MANEJADOR DE OPERACIONES
        editor.updateControls();//ACUALIZAMOS LOS CONTROLES DEL EDITOR
        
        editor.setCurrentFile(null);//HACEMOS NULL EL ARCHIVO ACTUAL
        editor.setDocumentChanged(false);//Y DECIMOS QUE EL DOCUMENTO NO HA SIDO CAMBIADO
    }
    

    public String abrir(){//ABRIR DOCUMENTO
        if(editor.documentHasChanged() == true){//SI EL DOCUMENTO HA SIDO CAMBIADO
            int opcion=JOptionPane.showConfirmDialog(editor, "¿QUIERE GUARDAR?");//PREGUNTAMOS SI SE QUIERE GUARDAR
            
            switch (opcion){
                case JOptionPane.YES_OPTION://EN CASI AFIRMATIVO
                    guardar(editor.getG().getErrores());//GUARDAMOS
                    break;
                case JOptionPane.CANCEL_OPTION://DE LO CONTRARIO
                    return "";//NO LO HACEMOS
            }
        }
        
        String x="";
        JFileChooser fc = getJFileChooser();//CREAMOS UN EXPLORADOR DE ARCHIVOS PARA ELEGIR EL QUE DESEAMOS ABRIR
        int state = fc.showOpenDialog(editor);//ESTADO DEL EXPLORADOR
        
        if(state == JFileChooser.APPROVE_OPTION){//SI SE ELIGIO ABRIR
            File f=fc.getSelectedFile();//CREAMOS UN FILE AL QUE LE ASIGNAMOS EL ARCHIVO SELECCIONADO
            try{
                BufferedReader buff=new BufferedReader(new FileReader(f));//CREAMOS UN LECTOR
                editor.getAreaTexto().read(buff, null);//LEEMOS EL ARCHIVO Y LO ASIGNAMOS AL AREA DE TEXTO
                buff.close();//CERRAMOS EL LECTOR
                
                editor.getAreaTexto().getDocument().addUndoableEditListener(editor.getEventHandler());//ASIGNAMOS EVENTOS
                editor.getUndoManager().die();//REINICIAMOS EL MANEJADOR DE OPERACIONES
                editor.updateControls();//ACTUALIZAMOS CONTROLES
                
                editor.setTitle(""+f.getName());//CAMBIAMOS EL NOMBRE DEL EDITOR                
                editor.setCurrentFile(f);//HACEMOS EL ARCHIVO F COMO ACTUAL
                editor.setDocumentChanged(false);//DECIMOS QUE EL DOCUMENTO NO HA SIDO CAMBIADO
                
                x=editor.getAreaTexto().getText();
            }catch(IOException ex){//EN CASO DE ERROR
                JOptionPane.showMessageDialog(editor,
                                                ex.getMessage(),
                                                ex.toString(),
                                                JOptionPane.ERROR_MESSAGE);
            }
        }
        return x;
    }
    
    public void guardar(Boolean error){//ACCION GUARDAR
        if(editor.getCurrentFile() == null){//SI EL ARCHIVO ACTUAL ES NULL
            guardarComo(error);//LLAMAMOS A GUARDAR COMO... PARA CREAR UN NUEVO ARCHIVO EN EL DISCO DURO
        }else if(editor.documentHasChanged() == true){//SI NO ES NULL Y HA SIDO EDITADO
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(editor.getCurrentFile()));//CREAMOS UN ESCRITOR
                editor.getAreaTexto().write(bw);//ESCRIBIRMOS LO QUE HAY EN EL ARCHIVO
                bw.close();//CERRAMOS EL ESCRITOR
                if(error==false){
                    String ruta=editor.getCurrentFile().getPath();
                    ruta=ruta.replace(".incg", ".comp");
                    Generador2 g=new Generador2();
                    g.analizar(editor.getCurrentFile().getPath());
                    g.guardarCodigo(ruta);
                }
                
                editor.setDocumentChanged(false);//DECIMOS QUE EL DOCUMENTO NO HA SIDO CAMBIADO
            }catch(IOException ex){//EN CASO DE ERROR
                JOptionPane.showMessageDialog(editor, 
                                              ex.getMessage(),
                                              ex.toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void guardarComo(Boolean error){//GUARDAR COMO
        JFileChooser fc = getJFileChooser();////CREAMOS UN EXPLORADOR DE ARCHIVOS
        
        int state = fc.showSaveDialog(editor);//ESTADO DEL EXPLORADOR
        
        if(state == JFileChooser.APPROVE_OPTION){//SI SE ELIGIO GUARDAR
            File f = fc.getSelectedFile();//CREAMOS UN FILE
            
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));//CREAMOS UN ESCRITOR
                editor.getAreaTexto().write(bw);//ESCRIBIMOS EL FICHERO
                bw.close();//CERRAMOS EL ESCRITOR
                
                editor.setTitle(""+f.getName());//SETEAMOS EL TITULO
                editor.setCurrentFile(f);//HACEMOS ACTUAL EL ARCHIVO Q GUARDAMOS
                editor.setDocumentChanged(false);//DECIMOS QUE EL DOCUMENTO NO HA SIDO MODIFICADO
                
                if(error==false){
                    String ruta=editor.getCurrentFile().getPath();
                    ruta=ruta.replace(".incg", ".comp");
                    Generador2 g=new Generador2();
                    g.analizar(editor.getCurrentFile().getPath());
                    g.guardarCodigo(ruta);
                }
            }catch(IOException ex){//EN CASO DE ERROR
                JOptionPane.showMessageDialog(editor,
                                              ex.getMessage(),
                                              ex.toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void salir(){//ACCION SALIR
        if(editor.documentHasChanged() == true){//SI EL DOCUMENTO HA SIDO CAMBIADO
            int option =JOptionPane.showConfirmDialog(editor, "¿DESEA GUARDAR?");//PREGUNTAMOS SI SE DESEA GUARDAR
            
            switch (option){
                case JOptionPane.YES_OPTION://EN CASO AFIRMATIVO
                    guardar(editor.getG().getErrores());//SE GUARDA EL DOCUMENTO
                    break;
                case JOptionPane.CANCEL_OPTION://EN CASO NEGATIVO
                    return;//NO LO HACEMOS
            }
            
        }
        System.exit(0);//TERMINAMOS EL PROGRAMA
    }
    
    public void deshacer(){//ACCION DESHACER
        try{
            editor.getUndoManager().undo();//APLICAMOS LA FUNCION UNDO DE UNDOMANAGER
        }catch(CannotUndoException ex){//EN CASO DE ERROR
            System.err.println(ex);
        }
        editor.updateControls();//ACTUALIZAMOS LOS CONTROLES DEL EDITOR
    }
    
    public void rehacer(){//ACCION REHACER
        try{
            editor.getUndoManager().redo();//APLICAMOS LA FUNCION REDO DEL UNDOMANAGER
        }catch(CannotRedoException ex){//EN CASO DE ERROR
            System.err.println(ex);
        }
        editor.updateControls();//ACTUALIZAMOS LOS CONTROLES DEL EDITOR
    }
    
    public void buscar(){//ACCION BUSCAR
        String text = JOptionPane.showInputDialog(editor,
                "Texto:","Buscar",JOptionPane.QUESTION_MESSAGE);//PEDIMOS LO QUE SE DESEA BUSCAR Y 
                                                                //LO GUARDAMOS EN UN STRING
        
        if(text != null){//SI EL STRING NO ES NULL
            String textAreaContent = editor.getAreaTexto().getText();//MIRAMOS SI EL TEXTO CONTIENE LA PALABRA INGRESADA
            int pos = textAreaContent.indexOf(text);//BUSCAMOS LA POSICION DE LA COINCIDENCIA
            
            if( pos > -1){//SI LA PALABRA EXISTE
                editor.getAreaTexto().select(pos, pos+text.length());//SELECCIONAMOS LA PALABRA
            }
            
            ultimaBusqueda = text;//ULTIMA BUSQUEDA ES LA QUE ACABAMOS DE REALIZAR
        }
    }
    
    public void buscarSiguiente(){//ACCION BUSCAR SIGUIENTE
        if(ultimaBusqueda.length() > 0){//SI LA ULTIMA BUSQUEDA EXISTE
            String textAreaContent = editor.getAreaTexto().getText();//CITAMOS EL TEXTO DEL EDITOR
            int pos = editor.getAreaTexto().getCaretPosition();//CITAMOS LA POSICION DEL CURSOR
            
            pos= textAreaContent.indexOf(ultimaBusqueda,pos);//ASIGNAMOS LA NUEVA POSICION SIN CONTAR LA DE LA ULTIMA
                                                         //BUSQUEDA
            
            if(pos > -1){//SI HAY COINCIDENCIA
                editor.getAreaTexto().select(pos, pos+ultimaBusqueda.length());//SELECCIONAMOS EL TEXTO
            }
        }else{//SI NO HAY ULTIMA BUSQUEDA
            buscar();//ACCION BUSCAR
        }
    }
  
    public void elegirFuente(){//ACCION SELECCIONAR FUENTE
        Font font = JFontChooser.showDialog(editor, "FUENTES", editor.getAreaTexto().getFont());//CREAMOS UNA FUENTE
                                                                                            //QUE SERA SELECCIONADA
                                                                                            //CON LA CLASE JFONTCHOOSER
        
        if(font != null){//SI LA FUENTE NO ES NULL
            editor.getAreaTexto().setFont(font);//CAMBIAMOS EL ESTILO DE FUENTE DEL EDITOR
        }
    }
    public void elegirFondo(){//ACCION SELECCIONAR COLOR DE FONDO
        //CREAMOS COLOR DE FONDO MEDIANTE UN JCOLORCHOOSER
        Color color=JColorChooser.showDialog(editor, "BACKGROUND COLOR", editor.getAreaTexto().getForeground());//
        
        if(color != null){//SI EL COLOR NO ES NULL
            editor.getAreaTexto().setBackground(color);//CAMBIAMOS EL COLOR DE FONDO
        }
    }
    
    private static JFileChooser getJFileChooser(){//CITAMOS UN EXPLORADOR DE ARCHIVOS
        JFileChooser fc = new JFileChooser();//CREAMOS UN EXPLORADOR DE ARCHIVOS
        fc.setDialogTitle("Elegir Archivo");//ASIGNAMOS UN TITULO
        fc.setMultiSelectionEnabled(false);//LE DECIMOS QUE NO PUEDE ELEGIR VARIOS ARCHIVOS A LA VEZ
        fc.setFileFilter(textFileFilter);//FILTRAMOS LOS CONTENIDOS
        return fc;
    }
    
    //METODO PARA FILTRAR CONTENIDOS DEL EXPLORADOR DE ARCHIVOS
    private static javax.swing.filechooser.FileFilter textFileFilter = new javax.swing.filechooser.FileFilter() {
 
        @Override
        public boolean accept(File f) {
            //acepta directorios y archivos de extensión .txt
            return f.isDirectory() || f.getName().toLowerCase().endsWith("incg");//DECIMOS QUE LOS ARCHIVOS DEBEN TERMINAR EN TXT
        }
 
        @Override
        public String getDescription() {
            //la descripción del tipo de archivo aceptado
            return "Incognita";
        }
    };
 
}
