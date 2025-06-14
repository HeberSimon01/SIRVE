


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaTraspasos<T extends Traspaso> implements Iterable<T> {

    private Nodo<T> cabeza = null;
    private int tamano = 0;

    private static class Nodo<T> {
        T datos;
        Nodo<T> siguiente;

        Nodo(T datos) {
            this.datos = datos;
            this.siguiente = null;
        }
    }

    public void agregar(T datos) {
        Nodo<T> nuevoNodo = new Nodo<>(datos);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cabeza.siguiente = cabeza; // Apunta a sí mismo para circularidad
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != cabeza) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
            nuevoNodo.siguiente = cabeza;
        }
        tamano++;
    }

    public int obtenerTamano() {
        return tamano;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteradorListaTraspasos();
    }

    private class IteradorListaTraspasos implements Iterator<T> {
        private Nodo<T> actual = cabeza;
        private int contador = 0;

        @Override
        public boolean hasNext() {
            return contador < tamano;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T datos = actual.datos;
            actual = actual.siguiente;
            contador++;
            return datos;
        }
    }

    public T buscarPorId(int id) {
        if (cabeza == null) {
            return null; // Lista vacía
        }

        Nodo<T> actual = cabeza;
        do {
            if (actual.datos.getId() == id) {
                return actual.datos; // Retorna el objeto encontrado
            }
            actual = actual.siguiente;
        } while (actual != cabeza);

        return null; // No se encontró el ID
    }

    public boolean eliminarPorId(int id, javax.swing.JTextArea textM) {
        long startTime = System.currentTimeMillis();

        if (cabeza == null) {
            long endTime = System.currentTimeMillis();
            double duracion = (endTime - startTime) / 1000.0;
            textM.setText("Tiempo de eliminación: " + duracion + " segundos (lista vacía)");
            return false;
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        do {
            if (actual.datos.getId() == id) {
                if (anterior == null) { // Eliminar cabeza
                    if (actual.siguiente == cabeza) {
                        cabeza = null; // Solo había un elemento
                    } else {
                        Nodo<T> ultimo = cabeza;
                        while (ultimo.siguiente != cabeza) {
                            ultimo = ultimo.siguiente;
                        }
                        cabeza = cabeza.siguiente;
                        ultimo.siguiente = cabeza;
                    }
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamano--;

                long endTime = System.currentTimeMillis();
                double duracion = (endTime - startTime) / 1000.0;
                textM.setText("Tiempo de eliminación: " + duracion + " segundos (registro eliminado)");
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        } while (actual != cabeza);

        long endTime = System.currentTimeMillis();
        double duracion = (endTime - startTime) / 1000.0;
        textM.setText("Tiempo de eliminación: " + duracion + " segundos (ID no encontrado)");
        return false;
    }
}
