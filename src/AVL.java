/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Archivo: AVL.java
// Colócalo en el mismo paquete que ABB.java

// AVL.java
import java.util.ArrayList;
import java.util.List;
import java.util.Stack; // For iterative traversal if needed for dot generation

public class AVL {
    private NodoAVL raiz;

    public AVL() {
        this.raiz = null;
    }

    // --- Helper methods for AVL balance ---

    private int altura(NodoAVL nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int obtenerFactorBalance(NodoAVL nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    // Rotación Derecha
    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        y.altura = max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = max(altura(x.izquierda), altura(x.derecha)) + 1;

        return x; // Nueva raíz del subárbol
    }

    // Rotación Izquierda
    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        x.altura = max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = max(altura(y.izquierda), altura(y.derecha)) + 1;

        return y; // Nueva raíz del subárbol
    }

    // --- Core AVL operations ---

    // Insertar
    public void insertar(Vehiculo vehiculo) {
        raiz = insertar(raiz, vehiculo);
    }

    private NodoAVL insertar(NodoAVL nodo, Vehiculo vehiculo) {
        if (nodo == null) {
            return (new NodoAVL(vehiculo));
        }

        // Compare by placa (assuming placa is the key)
        int comparacion = vehiculo.getPlaca().compareTo(nodo.vehiculo.getPlaca());

        if (comparacion < 0) {
            nodo.izquierda = insertar(nodo.izquierda, vehiculo);
        } else if (comparacion > 0) {
            nodo.derecha = insertar(nodo.derecha, vehiculo);
        } else {
            // Placa duplicada (o actualizar, dependiendo de tu lógica)
            System.out.println("Vehículo con placa " + vehiculo.getPlaca() + " ya existe en el AVL. No se insertó duplicado.");
            return nodo;
        }

        // Actualizar la altura del nodo actual
        nodo.altura = 1 + max(altura(nodo.izquierda), altura(nodo.derecha));

        // Obtener el factor de balance para verificar si el nodo está desbalanceado
        int factorBalance = obtenerFactorBalance(nodo);

        // Realizar rotaciones si está desbalanceado

        // Caso Izquierda-Izquierda (LL)
        if (factorBalance > 1 && vehiculo.getPlaca().compareTo(nodo.izquierda.vehiculo.getPlaca()) < 0) {
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Derecha (RR)
        if (factorBalance < -1 && vehiculo.getPlaca().compareTo(nodo.derecha.vehiculo.getPlaca()) > 0) {
            return rotarIzquierda(nodo);
        }

        // Caso Izquierda-Derecha (LR)
        if (factorBalance > 1 && vehiculo.getPlaca().compareTo(nodo.izquierda.vehiculo.getPlaca()) > 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Izquierda (RL)
        if (factorBalance < -1 && vehiculo.getPlaca().compareTo(nodo.derecha.vehiculo.getPlaca()) < 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Eliminar
    public void eliminar(String placa) {
        raiz = eliminar(raiz, placa);
    }

    private NodoAVL eliminar(NodoAVL nodo, String placa) {
        if (nodo == null) {
            return nodo; // El nodo no existe
        }

        int comparacion = placa.compareTo(nodo.vehiculo.getPlaca());

        // Recorrer el árbol para encontrar el nodo a eliminar
        if (comparacion < 0) {
            nodo.izquierda = eliminar(nodo.izquierda, placa);
        } else if (comparacion > 0) {
            nodo.derecha = eliminar(nodo.derecha, placa);
        } else {
            // Nodo encontrado, realizar la eliminación
            // Caso 1: Nodo con 0 o 1 hijo
            if ((nodo.izquierda == null) || (nodo.derecha == null)) {
                NodoAVL temp = (nodo.izquierda != null) ? nodo.izquierda : nodo.derecha; // <-- CORRECCIÓN AQUÍ

                // Caso sin hijos
                if (temp == null) {
                    nodo = null; // Eliminar el nodo
                } else { // Caso un hijo
                    nodo = temp; // Reemplazar el nodo con su hijo
                }
            } else {
                // Caso 2: Nodo con dos hijos
                // Encontrar el sucesor inorden (el menor en el subárbol derecho)
                NodoAVL temp = valorMinimoNodo(nodo.derecha);

                // Copiar los datos del sucesor inorden a este nodo
                nodo.vehiculo = temp.vehiculo;

                // Eliminar el sucesor inorden
                nodo.derecha = eliminar(nodo.derecha, temp.vehiculo.getPlaca());
            }
        }

        // Si el árbol tenía solo un nodo o si se eliminó la raíz y era el único nodo
        if (nodo == null) {
            return nodo;
        }

        // Actualizar la altura del nodo actual
        nodo.altura = 1 + max(altura(nodo.izquierda), altura(nodo.derecha));

        // Obtener el factor de balance
        int factorBalance = obtenerFactorBalance(nodo);

        // Realizar rotaciones si está desbalanceado
        // Caso Izquierda-Izquierda (LL)
        if (factorBalance > 1 && obtenerFactorBalance(nodo.izquierda) >= 0) {
            return rotarDerecha(nodo);
        }

        // Caso Izquierda-Derecha (LR)
        if (factorBalance > 1 && obtenerFactorBalance(nodo.izquierda) < 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Derecha (RR)
        if (factorBalance < -1 && obtenerFactorBalance(nodo.derecha) <= 0) {
            return rotarIzquierda(nodo);
        }

        // Caso Derecha-Izquierda (RL)
        if (factorBalance < -1 && obtenerFactorBalance(nodo.derecha) > 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Encuentra el nodo con el valor mínimo en un subárbol
    private NodoAVL valorMinimoNodo(NodoAVL nodo) {
        NodoAVL actual = nodo;
        while (actual.izquierda != null) {
            actual = actual.izquierda;
        }
        return actual;
    }

    // Buscar por Placa
    public Vehiculo buscarPorPlaca(String placa) {
        return buscarPorPlaca(raiz, placa);
    }

    private Vehiculo buscarPorPlaca(NodoAVL nodo, String placa) {
        if (nodo == null) {
            return null; // El vehículo no se encuentra
        }

        int comparacion = placa.compareTo(nodo.vehiculo.getPlaca());

        if (comparacion == 0) {
            return nodo.vehiculo; // Encontrado
        } else if (comparacion < 0) {
            return buscarPorPlaca(nodo.izquierda, placa);
        } else {
            return buscarPorPlaca(nodo.derecha, placa);
        }
    }

    // Obtener todos los vehículos (inorden para orden natural por placa)
    public List<Vehiculo> obtenerTodosLosVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        inordenTraversal(raiz, vehiculos);
        return vehiculos;
    }

    private void inordenTraversal(NodoAVL nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            inordenTraversal(nodo.izquierda, lista);
            lista.add(nodo.vehiculo);
            inordenTraversal(nodo.derecha, lista);
        }
    }

    // Verificar si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }

    // Resetear el árbol
    public void reset() {
        this.raiz = null;
    }

    // --- Graphviz DOT code generation ---

    public String generarDotLimitado(int limiteNodos) {
        StringBuilder dot = new StringBuilder();
        dot.append("digraph AVL {\n");
        dot.append("    node [shape=record, style=filled, fillcolor=lightblue];\n");

        if (raiz == null) {
            dot.append("    null_root [label=\"Árbol Vacío\"];\n");
        } else {
            generarDotLimitado(raiz, dot, new int[]{0}, limiteNodos);
        }
        dot.append("}\n");
        return dot.toString();
    }

    private void generarDotLimitado(NodoAVL nodo, StringBuilder dot, int[] count, int limiteNodos) {
        if (nodo == null || count[0] >= limiteNodos) {
            return;
        }

        dot.append("    ").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" [label=\"{ID: ").append(nodo.vehiculo.getId())
           .append(" | PLACA: ").append(nodo.vehiculo.getPlaca())
           .append(" | Altura: ").append(nodo.altura)
           .append(" | FB: ").append(obtenerFactorBalance(nodo)).append("}\"];\n");
        count[0]++;

        if (nodo.izquierda != null && count[0] < limiteNodos) {
            dot.append("    ").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" -> ").append(nodo.izquierda.vehiculo.getPlaca().replace("-", "_")).append(";\n");
            generarDotLimitado(nodo.izquierda, dot, count, limiteNodos);
        } else if (nodo.izquierda == null) {
            dot.append("    null_l").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" [label=\"null\", shape=point];\n");
            dot.append("    ").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" -> null_l").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(";\n");
        }


        if (nodo.derecha != null && count[0] < limiteNodos) {
            dot.append("    ").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" -> ").append(nodo.derecha.vehiculo.getPlaca().replace("-", "_")).append(";\n");
            generarDotLimitado(nodo.derecha, dot, count, limiteNodos);
        } else if (nodo.derecha == null) {
            dot.append("    null_r").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" [label=\"null\", shape=point];\n");
            dot.append("    ").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(" -> null_r").append(nodo.vehiculo.getPlaca().replace("-", "_")).append(";\n");
        }
    }
    // Método para obtener vehículos en preorden
public List<Vehiculo> obtenerVehiculosPreOrden() {
    List<Vehiculo> listaVehiculos = new ArrayList<>();
    preordenTraversal(raiz, listaVehiculos);
    return listaVehiculos;
}

private void preordenTraversal(NodoAVL nodo, List<Vehiculo> lista) {
    if (nodo != null) {
        lista.add(nodo.vehiculo);
        preordenTraversal(nodo.izquierda, lista);
        preordenTraversal(nodo.derecha, lista);
    }
}

// Método para obtener vehículos en posorden
public List<Vehiculo> obtenerVehiculosPosOrden() {
    List<Vehiculo> listaVehiculos = new ArrayList<>();
    posordenTraversal(raiz, listaVehiculos);
    return listaVehiculos;
}

private void posordenTraversal(NodoAVL nodo, List<Vehiculo> lista) {
    if (nodo != null) {
        posordenTraversal(nodo.izquierda, lista);
        posordenTraversal(nodo.derecha, lista);
        lista.add(nodo.vehiculo);
    }
}

}