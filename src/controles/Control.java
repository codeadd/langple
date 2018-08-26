package controles;

import herramientas.Constantes;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import vista.Frame;

public class Control {

    private Frame editor;//REFERENCIA DE MI FRAME
    
    //CONTRUCTOR POR DEFECTO
    public Control() {
    }
    //CONSTRUCTOR QUE RECIBE EL EDITOR
    public Control(Frame editor) {
        this.editor = editor;//IGUALO EL EDITOR DE LA CLASE AL QUE ENTRA COMO PARAMETRO
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+              COLOREADO DE SINTAXIS                  +
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void colores() throws BadLocationException {
        limpiarTexto();//LIMPIO LOS COLORES DE MI TEXTO
        expresionregular(Constantes.ROJO_CLARO_2, Constantes.EXP_PRINCIPALES, true);//EVALUO LAS EXPRESIONES PRIMARIAS
        expresionregular(Constantes.TEAL, Constantes.EXP_SECUNDARIAS, true);//EVALUO LAS EXPRESIONES SECUNDARIAS
        expresionregular(Constantes.LIMA, Constantes.EXP_CADENA, true);//EVALUO LAS STRING O CADENAS
        expresionregular(Constantes.VERDE, Constantes.EXP_COMENTARIO_UNA_LINEA, true);//EVALUO LOS COMENTARIOS
        expresionregular(Constantes.AZUL_CLARO, Constantes.EXP_VARIABLE, true);//EVALUO LAS VARIABLES
        expresionregular(Constantes.ROSA, Constantes.EXP_LOG, true);//EVALUO LAS EXPRESIONES LOGICAS
        expresionregular(Constantes.VERDE_SUBLIME, Constantes.EXP_OPRD, true);//EVALUO OPERADORES DE ASIGNACION
    }

    public void expresionregular(Color color, String exp, boolean bold) {
        Pattern p = Pattern.compile(exp);//COMPILO LA EXPRESION REGULAR
        Matcher m = p.matcher(editor.getAreaTexto().getText());//MIRO LAS COINCIDENCIAS EN EL AREA DE TEXTO
        while (m.find()) {
            actualizarTexto(m.start(), m.end() - m.start(), color, bold);//ACTUALIZO EL TEXTO Y COLOCO COLORES
        }
    }

    public void limpiarTexto() {
        //ACTUALIZO EL TEXTO CON EL COLOR NEGRO
        actualizarTexto(0, editor.getAreaTexto().getText().length(), Constantes.NEGRO_1, true);
    }

    private void actualizarTexto(int i, int length, Color c, boolean bold) {
        StyleContext sc = StyleContext.getDefaultStyleContext();//CREO UN CONTEXTO PARA EL ESTILO DEL TEXTO
        SimpleAttributeSet aset = new SimpleAttributeSet();//CREO UN ATRIBUTO ASET QUE ME CONTIENE EL CONJUNTO DE TEXTO
                                                           //QUE SERA TRANSFORMADO
        StyleConstants.setForeground(aset, c);//ENVIO EL TEXTO A MUTAR Y EL COLOR
                                              //Y LO CAMBIO MEDIANTE LA CLASE 'StyleConstants' DE JAVA
        if (bold) {//SI EL TIPO DE FUENTE ESTA EN NEGRITA
            StyleConstants.setBold(aset, true);//CAMBIO AL TEXTO MUTADO EL VALOR DE NEGRITA
        } else {
            StyleConstants.setBold(aset, false);
        }
        editor.getTextEditor().setCharacterAttributes(i, length, aset, true);//LE CAMBIO EL TEXTO A MI EDITOR
                                                                             //CON EL TEXTO MUTADO
                                                                             //EL VALOR TRUE ES PARA SABER SI SE
                                                                             //REEMPLAZA EL TEXTO O NO
    }
    
    public int getNumeroLinea() {//METODO PARA EL NUMERO DE LINEAS DEL EDITOR
        JTextPane component = editor.getAreaTexto();//CREO UN TEXTPANE TEMPORAL Y LE ASIGNO EL EDITOR
        int caret = component.getCaretPosition();//POSICION DEL CURSOR
        int posLine;//LINEA DE POSICION DEL CURSOR
        int y = 0;//VARIABLE PARA CALCULAR LA LINEA DE POSICION
        try {
            Rectangle caretCoords = component.modelToView(caret);//RECTANGULO PARA LA POSICION DEL CURSOR
            y = (int) caretCoords.getY();//ASIGNO LA POSICION Y DEL CURSOR
        } catch (BadLocationException ex) {
        }
        int lineHeight = component.getFontMetrics(component.getFont()).getHeight();//ALTO DE LINEA(DEPENDE DE LA FUENTE)
        posLine = (y / lineHeight) + 1;//CALCULAMOS LA LINEA DE POSICION
        return posLine;//RETORNAMOS
    }
    
}
