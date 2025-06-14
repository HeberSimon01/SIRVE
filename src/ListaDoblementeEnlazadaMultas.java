
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class ListaDoblementeEnlazadaMultas {
    private Nodo cabeza;
    private Nodo cola;

    private static class Nodo {
        Multa multa;
        Nodo siguiente;
        Nodo anterior;

        public Nodo(Multa multa) {
            this.multa = multa;
            this.siguiente = null;
            this.anterior = null;
        }
    }

    public ListaDoblementeEnlazadaMultas() {
        this.cabeza = null;
        this.cola = null;
    }

    // Método para agregar una multa al final de la lista
public void agregarMulta(Multa multa, JTextArea textM) {
    long startTime = System.currentTimeMillis(); // Tiempo de inicio en milisegundos

    Nodo nuevoNodo = new Nodo(multa);
    if (cabeza == null) {
        cabeza = nuevoNodo;
        cola = nuevoNodo;
    } else {
        cola.siguiente = nuevoNodo;
        nuevoNodo.anterior = cola;
        cola = nuevoNodo;
    }

    long endTime = System.currentTimeMillis(); // Tiempo de fin en milisegundos
    double durationInSeconds = (endTime - startTime) / 1000.0; // Calcular la duración en segundos

    // Mostrar el tiempo en el JTextArea
    textM.setText("Tiempo de adición: " + durationInSeconds + " segundos");
}


    // Método para obtener todas las multas como una lista de objetos (útil para JTable)
    public Object[][] obtenerDatosParaTabla() {
        if (cabeza == null) {
            return new Object[0][0]; // Retorna un array vacío si la lista está vacía
        }

        int contador = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }

        Object[][] datos = new Object[contador][6]; // ID, Placa, Fecha, Descripcion, Monto, Departamento
        actual = cabeza;
        int i = 0;
        while (actual != null) {
            datos[i][0] = actual.multa.getId();
            datos[i][1] = actual.multa.getPlaca();
            datos[i][2] = actual.multa.getFecha();
            datos[i][3] = actual.multa.getDescripcion();
            datos[i][4] = actual.multa.getMonto();
            datos[i][5] = actual.multa.getDepartamento();
            actual = actual.siguiente;
            i++;
        }
        return datos;
    }

    // Método para limpiar la lista (útil antes de una nueva carga)
    public void limpiar() {
        cabeza = null;
        cola = null;
        Multa.resetNextId(); // Reiniciar el ID al limpiar la lista
    }

public Object[][] buscarPorPlaca(String placaBuscada, JTextArea textM) {
    long startTime = System.currentTimeMillis(); // Tiempo de inicio en milisegundos

    if (cabeza == null) {
        return new Object[0][0];
    }

    java.util.List<Object[]> resultados = new java.util.ArrayList<>();
    Nodo actual = cabeza;

    while (actual != null) {
        if (actual.multa.getPlaca().equalsIgnoreCase(placaBuscada)) {
            resultados.add(new Object[]{
                actual.multa.getId(),
                actual.multa.getPlaca(),
                actual.multa.getFecha(),
                actual.multa.getDescripcion(),
                actual.multa.getMonto(),
                actual.multa.getDepartamento()
            });
        }
        actual = actual.siguiente;
    }

    long endTime = System.currentTimeMillis(); // Tiempo de fin en milisegundos
    double durationInSeconds = (endTime - startTime) / 1000.0; // Calcular la duración en segundos

    // Mostrar el tiempo en el JTextArea
    textM.setText("Tiempo de búsqueda: " + durationInSeconds + " segundos");

    return resultados.toArray(new Object[0][0]);
}


    public boolean eliminarPorId(int id, JTextArea textM) {
    long startTime = System.currentTimeMillis(); // Tiempo de inicio en milisegundos

    if (cabeza == null) {
        return false; // La lista está vacía
    }

    Nodo actual = cabeza;
    while (actual != null) {
        if (actual.multa.getId() == id) {
            // Si es el primer nodo
            if (actual == cabeza) {
                cabeza = actual.siguiente;
                if (cabeza != null) {
                    cabeza.anterior = null;
                } else {
                    cola = null; // La lista queda vacía
                }
            } else if (actual == cola) { // Si es el último nodo
                cola = actual.anterior;
                cola.siguiente = null;
            } else { // Si es un nodo intermedio
                actual.anterior.siguiente = actual.siguiente;
                actual.siguiente.anterior = actual.anterior;
            }

            long endTime = System.currentTimeMillis(); // Tiempo de fin en milisegundos
            double durationInSeconds = (endTime - startTime) / 1000.0; // Calcular la duración en segundos

            // Mostrar el tiempo en el JTextArea
            textM.setText("Tiempo de eliminación: " + durationInSeconds + " segundos");
            return true; // Se eliminó con éxito
        }
        actual = actual.siguiente;
    }

    // Si no se encontró el ID, también se puede mostrar el tiempo
    long endTime = System.currentTimeMillis(); // Tiempo de fin en milisegundos
    double durationInSeconds = (endTime - startTime) / 1000.0; // Calcular la duración en segundos
    textM.setText("Tiempo de eliminación: " + durationInSeconds + " segundos (ID no encontrado)");

    return false; // No se encontró el ID
}


    public Multa buscarMultaPorId(int id) {
        Nodo actual = cabeza; // Cambiado a Nodo
        while (actual != null) {
            if (actual.multa.getId() == id) { // Asegúrate de que Multa tenga un método getId()
                return actual.multa;
            }
            actual = actual.siguiente;
        }
        return null; // No se encontró la multa
    }
}
