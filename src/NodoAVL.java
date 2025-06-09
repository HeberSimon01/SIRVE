/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class NodoAVL {
    public Vehiculo vehiculo; // Objeto Vehiculo almacenado en el nodo
    public NodoAVL izq, der; // Referencias a los nodos hijo izquierdo y derecho
    public int altura; // Altura del nodo
    // Constructor
    public NodoAVL(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.altura = 1; // Nuevo nodo es inicialmente agregado a la hoja
    }
}
