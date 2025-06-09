// ABB.java
import java.util.ArrayList;
import java.util.List;
public class ABB { // Renombrado de ArbolABB a ABB
    private NodoABB raiz;
    private int nextId = 1; // Para generar IDs únicos automáticamente

    public ABB() { // Constructor renombrado
        this.raiz = null;
    }
    // Método para insertar un vehículo en el ABB
    public void insertar(Vehiculo vehiculo) {
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
}