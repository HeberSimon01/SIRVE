
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
// NodoABB.java
// NodoABB.java
public class NodoABB {
    Vehiculo vehiculo;
    NodoABB izquierda;
    NodoABB derecha;

    public NodoABB(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.izquierda = null;
        this.derecha = null;
    }
}