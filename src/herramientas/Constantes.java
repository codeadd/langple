/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herramientas;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author brayan
 */
public class Constantes {
    
      //############################################
     //         CONSTANTES PROPIEADES
    //#############################################
    public static final String RUTA_PROPIEDADES="controles/propiedades/tipoArchivo.properties";
    public static final ArrayList<String> SIG_TIP=new ArrayList<>(Arrays.asList("ID","ERR_ID"));
    public static final ArrayList<String> SIG_VAR=new ArrayList<>(Arrays.asList("FL","LC"));
    public static final ArrayList<String> SIG_LOG=new ArrayList<>(Arrays.asList("IG2","MAOG","MAY","MEOG","MN","DIF"));
    public static final ArrayList<String> SIG_VALS=new ArrayList<>(Arrays.asList("FLOL","ENTL","CADL"));
    public static final ArrayList<String> SIG_OP=new ArrayList<>(Arrays.asList("SUM","RES","POR","DIV"));


    
      //############################################
     //         CONSTANTES FUENTES
    //#############################################
    //arreglo con nombres de fuente disponibles
    public static final String[] FONT_NAMES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    //arreglo con estilos de fuente
    public static final String[] FONT_STYLES = {
        "Normal", "Bold", "Italic", "Bold Italic"
    };
    //arreglo con tamaños de fuente
    public static final String[] FONT_SIZES = {
        "8", "9", "10", "11", "12", "13", "14", "16", "18", "20", "24", "28", "32", "48", "72"
    };
      //############################################
     //         CONSTANTES COLORES
    //#############################################
    
    public static final Color ROJO_CLARO = new Color(hex("F44336"));
    public static final Color AZUL_CLARO = new Color(hex("2196F3"));
    public static final Color TEAL = new Color(hex("009688"));
    public static final Color VERDE = new Color(hex("4CAF50"));
    public static final Color VERDE_CLARO = new Color(hex("8BC34A"));
    public static final Color LIMA = new Color(hex("CDDC39"));
    public static final Color AMARILLO = new Color(hex("FFEB3B"));
    
    public static final Color GRIS_OSCURO = new Color(hex("50514F"));
    public static final Color ROJO_CLARO_2 = new Color(hex("F25F5C"));
    public static final Color AMARILLO_CLARO = new Color(hex("FFE066"));
    public static final Color AZUL = new Color(hex("247BA0"));
    public static final Color AZUL_CIELO =  new Color(hex("70C1B3"));
    public static final Color ROSA = new Color(hex("FB266C"));
    public static final Color GRIS_BLANCO = new Color(hex("EEF8FA"));
    public static final Color VERDE_SUBLIME = new Color(hex("A6DA27"));
    
    //------------------ colores de fondo
    public static final Color NEGRO_1 = new Color(hex("262626"));
    public static final Color NEGRO_2 =new Color(hex("212121"));
    public static final Color BLANCO = new Color(hex("FFFFFF"));
    public static final Color CAFE_CLARO = new Color(hex("BFB38F"));
    public static final Color FONDO = new Color(hex("FFF9C4"));
    
    
      //############################################
     //             IMAGENES RUTAS
    //#############################################
    //public static final String RUTA_ICONOS_FOLDER = "/res/iconos/";
    //----------------iconos 20x20--------------
    public static final String RUTA_NEW_20 = "/res/iconos/20_20/file-code.png";
    public static final String RUTA_OPEN_20 = "/res/iconos/20_20/box.png";
    public static final String RUTA_SAVE_20 = "/res/iconos/20_20/box-in.png";
    public static final String RUTA_SAVEAS_20 = "/res/iconos/20_20/saveas.png";
    public static final String RUTA_PRINT_20 = "/res/iconos/20_20/pdf.png";
    public static final String RUTA_EXIT_20 = "/res/iconos/20_20/error.png";
    public static final String RUTA_UNDO_20 = "/res/iconos/20_20/undo.png";
    public static final String RUTA_REDO_20 = "/res/iconos/20_20/redo.png";
    public static final String RUTA_CUT_20 = "/res/iconos/20_20/cut.png";
    public static final String RUTA_COPY_20= "/res/iconos/20_20/copy.png";
    public static final String RUTA_PASTE_20 = "/res/iconos/20_20/paste.png";
    public static final String RUTA_BUSRCAR_20 = "/res/iconos/20_20/search.png";
    public static final String RUTA_GOTO_LINE_20 = "/res/iconos/20_20/marker.png";
    public static final String RUTA_SELECIONAR_TODO_20 = "/res/iconos/20_20/text.png";
    public static final String RUTA_FONT_20 = "/res/iconos/20_20/file-word.png";
    public static final String RUTA_COLOR_20 = "/res/iconos/20_20/edit.png";
    public static final String RUTA_COLOR_FONDO_20 = "/res/iconos/20_20/img.png";
    public static final String RUTA_INFO_20 = "/res/iconos/20_20/info.png";
    public static final String RUTA_PROG_20 = "/res/iconos/20_20/window-editor.png";
    
    //-------------iconos 30x30-------------
    public static final String RUTA_NEW_30 ="/res/iconos/30_30/file-code.png";
    public static final String RUTA_ABRIR_30 = "/res/iconos/30_30/box.png";
    public static final String RUTA_SAVE_30 = "/res/iconos/30_30/box-in.png";
    public static final String RUTA_SAVEAS_30 = "/res/iconos/30_30/drive.png";
    public static final String RUTA_PRINT_30 = "/res/iconos/30_30/file-pdf.png";
    public static final String RUTA_UNDO_30 = "/res/iconos/30_30/undo.png";
    public static final String RUTA_REDO_30 = "/res/iconos/30_30/redo.png";
    public static final String RUTA_CUT_30 = "/res/iconos/30_30/cut.png";
    public static final String RUTA_COPY_30 = "/res/iconos/30_30/copy.png";
    public static final String RUTA_PASTE_30 = "/res/iconos/30_30/paste.png";
    public static final String RUTA_TABLA_30 = "/res/iconos/30_30/file-excel.png";
    public static final String RUTA_INFO_30 = "/res/iconos/30_30/info2.png";
    public static final String RUTA_RUN_30 = "/res/iconos/30_30/run.png";
    public static final String RUTA_PROG_30 = "/res/iconos/30_30/window.png";
    
      //############################################
     //     EXPRESIONES REGULARES
    //#############################################
    public static final String EXP_VARIABLE = "\\&[a-z|A-Z]([0-9]|[a-z|A-Z])*";
    public static final String EXP_COMENTARIO_UNA_LINEA = "\\/\\/.*";
    public static final String EXP_CADENA="\".*\"";
    public static final String EXP_COMENTARIO_MULTILINEA="\\/\\*.*?\\*\\/";
     public static final String EXP_PRINCIPALES = "(if|else|then|for|while|fun|say|ret)";
    public static final String EXP_SECUNDARIAS="(str|double|int|bl|flt|char)";
    public static final String EXP_LOG="(True|False|OR|not|and|==|>=|<=|!=)";
    public static final String EXP_OPRD="(=|\\+|\\*|-|/)";
    
      //############################################
     //METODO PARA CONVERTIR COLORES HEXADECIMALES
    //#############################################
    
    public static int hex(String color){
        return Integer.parseInt(color,16);
    }
    
}
