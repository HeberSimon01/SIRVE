/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// NodoMulta.java
public class NodoMulta {
    public Multa multa;
    public NodoMulta siguiente;
    public NodoMulta anterior;

    public NodoMulta(Multa multa) {
        this.multa = multa;
        this.siguiente = null;
        this.anterior = null;
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }

    public NodoMulta getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoMulta siguiente) {
        this.siguiente = siguiente;
    }

    public NodoMulta getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoMulta anterior) {
        this.anterior = anterior;
    }
}