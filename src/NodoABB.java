
import java.io.File;
import javax.swing.JFileChooser;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class NodoABB {

    public Multa multa;
    public NodoABB izq, der;

    public NodoABB(Multa multa) {
        this.multa = multa;
    }
}