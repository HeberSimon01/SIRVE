// ABB.java
import java.util.ArrayList;
import java.util.List;
public class ABB { // Renombrado de ArbolABB a ABB
    private NodoABB raiz;
    private int nextId = 1; // Para generar IDs únicos automáticamente

    public ABB() { // Constructor renombrado
        this.raiz = null;
    }
    
    // Método para  insertar un vehículo en el ABB
    public void insertar(Vehiculo vehiculo) {
            vehiculo.setId(nextId++);
    System.out.println("DEBUG - Insertando Vehículo: Placa=" + vehiculo.getPlaca() + ", ID Asignado=" + vehiculo.getId()); // <-- ESTA LÍNEA ES CLAVE
    this.raiz = insertarRecursivo(this.raiz, vehiculo);

        vehiculo.setId(nextId++); // Asignar ID único
        this.raiz = insertarRecursivo(this.raiz, vehiculo);
    }
    private NodoABB insertarRecursivo(NodoABB actual, Vehiculo vehiculo) {
        if (actual == null) {
            return new NodoABB(vehiculo);
        }
        int comparacion = vehiculo.compareTo(actual.vehiculo);
        if (comparacion < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, vehiculo);
        } else if (comparacion > 0) {
            actual.derecha = insertarRecursivo(actual.derecha, vehiculo);
        } else {
            // Manejar duplicados si es necesario (ej: no insertar o actualizar)
            System.out.println("Vehículo con placa duplicada: " + vehiculo.getPlaca());
        }
        return actual;
    }
    // Método para obtener todos los vehículos ordenados (recorrido inorden)
    public List<Vehiculo> obtenerVehiculosOrdenados() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        inorden(raiz, listaVehiculos);
        return listaVehiculos;
    }
    private void inorden(NodoABB nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            inorden(nodo.izquierda, lista);
            lista.add(nodo.vehiculo);
            inorden(nodo.derecha, lista);
        }
    }
    // Método para obtener vehículos filtrados por departamento
    public List<Vehiculo> obtenerVehiculosPorDepartamento(String departamento) {
        List<Vehiculo> listaFiltrada = new ArrayList<>();
        inordenFiltrado(raiz, listaFiltrada, departamento);
        return listaFiltrada;
    }
    private void inordenFiltrado(NodoABB nodo, List<Vehiculo> lista, String departamento) {
        if (nodo != null) {
            inordenFiltrado(nodo.izquierda, lista, departamento);
            if (nodo.vehiculo.getDepartamento().equalsIgnoreCase(departamento)) {
                lista.add(nodo.vehiculo);
            }
            inordenFiltrado(nodo.derecha, lista, departamento);
        }
    }
    // Método para resetear el árbol y los IDs (útil si se carga un nuevo set de datos)
    public void reset() {
        this.raiz = null;
        this.nextId = 1;
    }
    public Vehiculo buscarPorPlaca(String placa) {
    return buscarPorPlacaRecursivo(raiz, placa);
}

private Vehiculo buscarPorPlacaRecursivo(NodoABB actual, String placa) {
    
    if (actual == null) {
        return null; // No se encontró el vehículo
    }
    int comparacion = placa.compareTo(actual.vehiculo.getPlaca());
    if (comparacion < 0) {
        return buscarPorPlacaRecursivo(actual.izquierda, placa);
    } else if (comparacion > 0) {
        return buscarPorPlacaRecursivo(actual.derecha, placa);
    } else {
        return actual.vehiculo; // Se encontró el vehículo
    }
}
public Vehiculo buscarPorId(int idBuscado) {
    System.out.println("DEBUG - Buscando Vehículo con ID: " + idBuscado);
    return buscarPorIdRecursivo(raiz, idBuscado);
}

private Vehiculo buscarPorIdRecursivo(NodoABB nodo, int idBuscado) {
    if (nodo == null) {
        return null;
    }

    // Si encontramos el ID, devolvemos el vehículo
    if (nodo.vehiculo.getId() == idBuscado) {
        System.out.println("DEBUG - Comparando ID: " + idBuscado + " con ID de nodo: " + nodo.vehiculo.getId() + " (Placa: " + nodo.vehiculo.getPlaca() + ") -> ¡ID ENCONTRADO!"); // <-- Añadir este mensaje
        return nodo.vehiculo;
    }

    System.out.println("DEBUG - Comparando ID: " + idBuscado + " con ID de nodo: " + nodo.vehiculo.getId() + " (Placa: " + nodo.vehiculo.getPlaca() + ")"); // <-- Mantener este mensaje

    // Buscar en el subárbol izquierdo
    Vehiculo encontradoIzquierda = buscarPorIdRecursivo(nodo.izquierda, idBuscado);
    if (encontradoIzquierda != null) {
        return encontradoIzquierda;
    }

    // Buscar en el subárbol derecho
    Vehiculo encontradoDerecha = buscarPorIdRecursivo(nodo.derecha, idBuscado);
    if (encontradoDerecha != null) {
        return encontradoDerecha;
    }

    return null; // No se encontró el ID en este subárbol
}

// NUEVO MÉTODO: Eliminar un nodo por la placa (que es la clave de ordenamiento)
public boolean eliminar(String placaAEliminar) {
    System.out.println("DEBUG - Intentando eliminar Placa (entrada): " + placaAEliminar);
    placaAEliminar = placaAEliminar.toUpperCase();
    System.out.println("DEBUG - Eliminando Placa (normalizada): " + placaAEliminar);

    // Guardamos la raíz original para comparar después - YA NO NECESARIO CON LA RE-BÚSQUEDA
    // NodoABB raizOriginal = this.raiz; 
    
    // Llamar al método recursivo de eliminación
    this.raiz = eliminarRecursivo(this.raiz, placaAEliminar);

    // Verificar si la placa existe *después* de intentar eliminar.
    // Si buscarPorPlaca devuelve null, significa que se eliminó.
    if (buscarPorPlaca(placaAEliminar) == null) { // <-- ESTA ES LA LÍNEA CRÍTICA
        System.out.println("DEBUG - Eliminación de '" + placaAEliminar + "' exitosa (Verificado por re-búsqueda).");
        return true;
    } else {
        System.out.println("DEBUG - Eliminación de '" + placaAEliminar + "' fallida (El vehículo todavía se encontró después de intentar eliminar).");
        return false;
    }
}

// En tu clase ABB
private NodoABB eliminarRecursivo(NodoABB actual, String placaAEliminar) {
    if (actual == null) {
        System.out.println("DEBUG - eliminarRecursivo: Nodo actual es null. No se encontró el elemento.");
        return null; // El árbol está vacío o el elemento no se encontró
    }

    String placaActualNodo = actual.vehiculo.getPlaca().toUpperCase(); // Normalizar la placa del nodo para la comparación
    System.out.println("DEBUG - eliminarRecursivo: Comparando placa a eliminar '" + placaAEliminar + "' con placa de nodo '" + placaActualNodo + "' (ID: " + actual.vehiculo.getId() + ")");
    
    int comparacion = placaAEliminar.compareTo(placaActualNodo);

    if (comparacion < 0) { // La placa a eliminar es menor, buscar en el subárbol izquierdo
        actual.izquierda = eliminarRecursivo(actual.izquierda, placaAEliminar);
        return actual;
    } else if (comparacion > 0) { // La placa a eliminar es mayor, buscar en el subárbol derecho
        actual.derecha = eliminarRecursivo(actual.derecha, placaAEliminar);
        return actual;
    } else {
        // ¡Se encontró el nodo a eliminar!
        System.out.println("DEBUG - eliminarRecursivo: ¡Placa '" + placaAEliminar + "' encontrada para eliminación! Procediendo a eliminar."); // <-- MENSAJE CORRECTO CUANDO SE ENCUENTRA

        // Caso 1: Nodo hoja (sin hijos)
        if (actual.izquierda == null && actual.derecha == null) {
            System.out.println("DEBUG - eliminarRecursivo: Eliminando nodo hoja (Placa: " + placaActualNodo + ")");
            return null;
        }
        // Caso 2: Solo un hijo (derecho)
        if (actual.izquierda == null) {
            System.out.println("DEBUG - eliminarRecursivo: Eliminando nodo con solo hijo derecho (Placa: " + placaActualNodo + ")");
            return actual.derecha;
        }
        // Caso 3: Solo un hijo (izquierdo)
        if (actual.derecha == null) {
            System.out.println("DEBUG - eliminarRecursivo: Eliminando nodo con solo hijo izquierdo (Placa: " + placaActualNodo + ")");
            return actual.izquierda;
        }
        // Caso 4: Dos hijos
        System.out.println("DEBUG - eliminarRecursivo: Eliminando nodo con dos hijos (Placa: " + placaActualNodo + ")");
        // Encontrar el sucesor inorden (el menor valor en el subárbol derecho)
        Vehiculo sucesorVehiculo = encontrarMenorValor(actual.derecha);
        System.out.println("DEBUG - eliminarRecursivo: Sucesor inorden encontrado (Placa: " + sucesorVehiculo.getPlaca() + ", ID: " + sucesorVehiculo.getId() + ")");
        // Copiar el contenido del sucesor al nodo actual
        actual.vehiculo = sucesorVehiculo; // Sobreescribe el objeto Vehiculo del nodo actual
        // Eliminar el sucesor (que ahora es un duplicado en el subárbol derecho)
        System.out.println("DEBUG - eliminarRecursivo: Eliminando duplicado de sucesor inorden (Placa: " + sucesorVehiculo.getPlaca() + ")");
        actual.derecha = eliminarRecursivo(actual.derecha, sucesorVehiculo.getPlaca().toUpperCase());
        return actual;
    }
}

// Método auxiliar para encontrar el Vehiculo con la placa más pequeña en un subárbol
private Vehiculo encontrarMenorValor(NodoABB raizNodo) {
    // Recorre a la izquierda hasta el nodo más a la izquierda
    if (raizNodo == null) return null;
    while (raizNodo.izquierda != null) {
        raizNodo = raizNodo.izquierda;
    }
    return raizNodo.vehiculo;
}
 public String generarDotLimitado(int limiteNodos) {
        if (raiz == null) {
            return "digraph G { \n" +
                   "    node [shape=record];\n" +
                   "    null [label=\"Árbol Vacío\"];\n" +
                   "}";
        }

        StringBuilder dot = new StringBuilder();
        dot.append("digraph G { \n");
        dot.append("    node [shape=record];\n");
        dot.append("    rankdir=\"TB\";\n"); // De arriba hacia abajo (Top to Bottom)

        // Lista para almacenar los nodos que serán incluidos en la visualización
        List<NodoABB> nodosParaVisualizar = new ArrayList<>();
        // Contador para saber cuántos nodos hemos añadido
        int[] contador = {0}; // Usamos un array para pasarlo por referencia en la recursión

        // Llama al método auxiliar para obtener los primeros 'limiteNodos'
        obtenerNodosParaDotLimitado(raiz, nodosParaVisualizar, contador, limiteNodos);

        // Ahora, construye el DOT solo con los nodos seleccionados
        // Primero declara todos los nodos seleccionados
for (NodoABB nodo : nodosParaVisualizar) {
    dot.append("    \"node").append(nodo.vehiculo.getId()).append("\" [label=\"{ID: ").append(nodo.vehiculo.getId())
       .append(" | Placa: ").append(nodo.vehiculo.getPlaca()).append("}\"];\n");
}

// Luego, añade las conexiones entre ellos (solo si ambos nodos de la conexión están en la lista)
// Esto puede ser un poco más complejo si quieres que las aristas apunten a "nulos" si el hijo no está en el límite.
// La forma más simple es solo conectar nodos que están presentes.
// Otra opción es llamar a generarDotRec pero con una comprobación adicional.
// generarDotRecLimitado(raiz, dot, limiteNodos, new int[]{0}); // <--- ¡Esta línea es la que te causa el error!
// Mejoramos el llamado a generarDotRecLimitado para que use la lista de nodos ya seleccionados
// Se necesita un Set para búsqueda rápida
java.util.Set<Integer> idsVisualizados = new java.util.HashSet<>();
for(NodoABB n : nodosParaVisualizar) {
    idsVisualizados.add(n.vehiculo.getId());
}
generarEnlacesDotLimitado(raiz, dot, idsVisualizados);


        dot.append("}");
        return dot.toString();
    }


    // Método auxiliar para obtener los primeros 'limiteNodos' en un recorrido in-orden
    private void obtenerNodosParaDotLimitado(NodoABB nodo, List<NodoABB> nodos, int[] contador, int limite) {
        if (nodo == null || contador[0] >= limite) {
            return;
        }

        // Recorrido en orden (izquierda, raíz, derecha)
        obtenerNodosParaDotLimitado(nodo.izquierda, nodos, contador, limite);

        if (contador[0] < limite) {
            nodos.add(nodo);
            contador[0]++;
        }

        if (contador[0] < limite) { // Continúa solo si aún no hemos alcanzado el límite
            obtenerNodosParaDotLimitado(nodo.derecha, nodos, contador, limite);
        }
    }
    
    // Nuevo método para generar solo los enlaces entre los nodos que ya fueron seleccionados para visualización
    private void generarEnlacesDotLimitado(NodoABB nodo, StringBuilder dot, java.util.Set<Integer> idsVisualizados) {
        if (nodo == null || !idsVisualizados.contains(nodo.vehiculo.getId())) {
            return; // Si el nodo no está en la lista de visualización, no genera sus enlaces
        }

        // Si tiene hijo izquierdo y el hijo está en la lista de visualización
        if (nodo.izquierda != null && idsVisualizados.contains(nodo.izquierda.vehiculo.getId())) {
            dot.append("    \"node").append(nodo.vehiculo.getId()).append("\" -> \"node").append(nodo.izquierda.vehiculo.getId()).append("\" [label=\"L\"];\n");
            generarEnlacesDotLimitado(nodo.izquierda, dot, idsVisualizados);
        } else if (nodo.izquierda != null) { // Si tiene hijo izquierdo pero no está en la visualización, apunta a un "null"
            dot.append("    \"nullL").append(nodo.vehiculo.getId()).append("\" [shape=point, width=0.1, height=0.1];\n");
            dot.append("    \"node").append(nodo.vehiculo.getId()).append("\" -> \"nullL").append(nodo.vehiculo.getId()).append("\" [label=\"L\", arrowhead=none];\n");
        }


        // Si tiene hijo derecho y el hijo está en la lista de visualización
        if (nodo.derecha != null && idsVisualizados.contains(nodo.derecha.vehiculo.getId())) {
            dot.append("    \"node").append(nodo.vehiculo.getId()).append("\" -> \"node").append(nodo.derecha.vehiculo.getId()).append("\" [label=\"R\"];\n");
            generarEnlacesDotLimitado(nodo.derecha, dot, idsVisualizados);
        } else if (nodo.derecha != null) { // Si tiene hijo derecho pero no está en la visualización, apunta a un "null"
            dot.append("    \"nullR").append(nodo.vehiculo.getId()).append("\" [shape=point, width=0.1, height=0.1];\n");
            dot.append("    \"node").append(nodo.vehiculo.getId()).append("\" -> \"nullR").append(nodo.vehiculo.getId()).append("\" [label=\"R\", arrowhead=none];\n");
        }
    }

}