/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.List;

public class AVL {
    private NodoAVL raiz; // Raíz del árbol AVL

    // Método para obtener la altura de un nodo
    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    // Método para obtener el factor de balance
    private int factorBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.izq) - altura(nodo.der);
    }

    // Rotación a la derecha
    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;

        // Realizar rotación
        x.der = y;
        y.izq = T2;

        // Actualizar alturas
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;

        // Retornar nuevo nodo raíz
        return x;
    }

    // Rotación a la izquierda
    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;

        // Realizar rotación
        y.izq = x;
        x.der = T2;

        // Actualizar alturas
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;

        // Retornar nuevo nodo raíz
        return y;
    }

    // Método para insertar un nuevo vehículo
    public void insertar(Vehiculo vehiculo) {
        raiz = insertar(raiz, vehiculo);
    }

    private NodoAVL insertar(NodoAVL nodo, Vehiculo vehiculo) {
        // 1. Realizar la inserción normal en el árbol binario de búsqueda
        if (nodo == null) {
            return new NodoAVL(vehiculo);
        }

        if (vehiculo.getPlaca().compareTo(nodo.vehiculo.getPlaca()) < 0) {
            nodo.izq = insertar(nodo.izq, vehiculo);
        } else if (vehiculo.getPlaca().compareTo(nodo.vehiculo.getPlaca()) > 0) {
            nodo.der = insertar(nodo.der, vehiculo);
        } else {
            // Duplicados no son permitidos
            return nodo;
        }

        // 2. Actualizar la altura del nodo ancestro
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));

        // 3. Obtener el factor de balance
        int balance = factorBalance(nodo);

        // Si el nodo se vuelve desbalanceado, hay 4 casos

        // Caso Izquierda Izquierda
        if (balance > 1 && vehiculo.getPlaca().compareTo(nodo.izq.vehiculo.getPlaca()) < 0) {
            return rotarDerecha(nodo);
        }

        // Caso Derecha Derecha
        if (balance < -1 && vehiculo.getPlaca().compareTo(nodo.der.vehiculo.getPlaca()) > 0) {
            return rotarIzquierda(nodo);
        }

        // Caso Izquierda Derecha
        if (balance > 1 && vehiculo.getPlaca().compareTo(nodo.izq.vehiculo.getPlaca()) > 0) {
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }

        // Caso Derecha Izquierda
        if (balance < -1 && vehiculo.getPlaca().compareTo(nodo.der.vehiculo.getPlaca()) < 0) {
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }

        // Retornar el nodo (sin cambios)
        return nodo;
    }

    // Método para realizar un recorrido inorden
    public List<Vehiculo> inorden() {
        List<Vehiculo> lista = new ArrayList<>();
        inorden(raiz, lista);
        return lista;
    }

    private void inorden(NodoAVL nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            inorden(nodo.izq, lista);
            lista.add(nodo.vehiculo);
            inorden(nodo.der, lista);
        }
    }
}

