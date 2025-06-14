/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// NodoAVL.java
public class NodoAVL {
    Vehiculo vehiculo;
    NodoAVL izquierda;
    NodoAVL derecha;
    int altura; // Height of the node

    public NodoAVL(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.izquierda = null;
        this.derecha = null;
        this.altura = 1; // New node is a leaf, height is 1
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public NodoAVL getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoAVL izquierda) {
        this.izquierda = izquierda;
    }

    public NodoAVL getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoAVL derecha) {
        this.derecha = derecha;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}