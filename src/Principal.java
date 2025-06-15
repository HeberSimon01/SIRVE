
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
  


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */

public class Principal extends javax.swing.JFrame {
    private Map<String, String> archivosVehiculosPorDepto; // Si lo usas
    private List<Vector<String>> allTableData; // Si lo usas
    private ABB arbolABB; // Instancia de tu árbol ABB
    private AVL arbolAVL; // Instancia de tu árbol AVL
    private int nextGlobalId = 1; // Para IDs únicos y generados automáticamente
    private Set<String> departamentosCargados; // Para gestionar los departamentos en el ComboBox



    private DefaultTableModel tablaModelo;    // Modelo para Tabla
    private DefaultTableModel tablaModelo1;   // Modelo para Tabla1
    private javax.swing.JTextField txtPlacaBuscar; // Para buscar en ABB
    private javax.swing.JButton btnBuscar;       // Botón de búsqueda (general o ABB)
    private javax.swing.JTextArea areaResultadoBusqueda; // Área de resultados (general o ABB)

    private static final String[] ARCHIVOS_DEPARTAMENTOS = {
        "Antigua_Guatemala_vehiculos.txt", "Chimaltenango_vehiculos.txt", "Escuintla_vehiculos.txt",
        "Guatemala_vehiculos.txt", "Huehuetenango_vehiculos.txt", "Peten_vehiculos.txt",
        "Quetzaltenango_vehiculos.txt", "San_Marcos_vehiculos.txt", "Suchitepequez_vehiculos.txt",
        "Chiquimula_vehiculos.txt"
    };
    
      public Principal() {
        initComponents();

       departamentosCargados = new HashSet<>();arbolABB = new ABB();
        arbolAVL = new AVL();
        btnBuscarAVL.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnBuscarAVLActionPerformed(evt); // Este método llamará a buscarVehiculoAVL()
    }
});
        BtnEliminarAVL.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        BtnEliminarAVLActionPerformed(evt); // Este método llamará a eliminarVehiculoAVL()
    }
});
        // Dentro de initComponents(), busca o añade esta línea para el botón de modificar del AVL
btnModificarAVL.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnModificarAVLActionPerformed(evt); // Este método llamará a modificarVehiculoAVL()
    }
});
btnAgregarAVL.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAgregarAVLActionPerformed(evt); // Este método llamará a agregarVehiculoAVL()
    }
});
        // Inicializar el conjunto de departamentos cargados
        departamentosCargados = new HashSet<>();

        // Inicializar los modelos de tabla DESPUÉS de initComponents()
        tablaModelo = (DefaultTableModel) Tabla.getModel();
        tablaModelo1 = (DefaultTableModel) Tabla1.getModel();

        // Definir las columnas para ambos modelos de tabla
        Object[] columnNames = {"ID", "PLACA", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos", "Departamento"};
        tablaModelo.setColumnIdentifiers(columnNames);
        tablaModelo1.setColumnIdentifiers(columnNames);

        // Configurar JTables para que no sean editables directamente por el usuario
        Tabla.setDefaultEditor(Object.class, null);
        Tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Tabla1.setDefaultEditor(Object.class, null);
        Tabla1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // Inicialización de componentes UI que quizás no maneja initComponents() o que quieres controlar

        if (txtPlacaBuscar == null) txtPlacaBuscar = new JTextField(15);
        if (btnBuscar == null) btnBuscar = new JButton("Buscar Vehículo por Placa");
        if (areaResultadoBusqueda == null) areaResultadoBusqueda = new JTextArea(5, 30);
        if (jTextAreaMensajes == null) jTextAreaMensajes = new JTextArea();
        areaResultadoBusqueda.setEditable(false);
        jTextAreaMensajes.setEditable(false); // Generalmente, jTextAreaMensajes no es editable

        // Configuración inicial del ComboBox de Departamentos (general)
        if (Depto != null) {
            Depto.removeAllItems();
            Depto.addItem("Todos");
            Depto.setSelectedItem("Todos");
        }
        // Configuración inicial del ComboBox de Departamentos para AVL (si es diferente y existe)
        if (cmbDepartamentoAVL != null) {
            cmbDepartamentoAVL.removeAllItems();
            // Puedes añadir items por defecto o esperar a que se carguen de archivos
            // cmbDepartamentoAVL.addItem("Guatemala");
        }
    }

public Set<String> getDepartamentosCargados() {
    return new HashSet<>(departamentosCargados); // Retorna una copia para evitar modificaciones externas
}


private void configurarTabla(javax.swing.JTable tabla) {
    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    model.setColumnIdentifiers(new Object[]{"ID", "PLACA", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos", "Departamento"});
    // Disable editing
    tabla.setDefaultEditor(Object.class, null); // Makes cells uneditable
    tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
}

// Este método es LLAMADO DESDE TU JDialog 'Agregar' (por eso es public)
public void agregarVehiculoDesdeVentana(Vehiculo vehiculo, String departamento) {
    long startTime = System.nanoTime(); // Inicia el temporizador al principio del método

    if (vehiculo != null) {
        vehiculo.setPlaca(vehiculo.getPlaca().trim().toUpperCase());
        vehiculo.setDepartamento(departamento);

        // ¡IMPORTANTE! Asignar el ID global único
        Vehiculo vehiculoFinal = new Vehiculo(
            nextGlobalId++, // Asigna el ID actual y luego lo incrementa para el siguiente
            vehiculo.getPlaca(),
            vehiculo.getDpi(),
            vehiculo.getNombre(),
            vehiculo.getMarca(),
            vehiculo.getModelo(),
            vehiculo.getAño(),
            vehiculo.getMultas(),
            vehiculo.getTraspasos(),
            vehiculo.getDepartamento()
        );

        arbolAVL.insertar(vehiculoFinal); // Inserta en el AVL
        arbolABB.insertar(vehiculoFinal); // Inserta también en ABB para mantener sincronía

        actualizarTablaConTodosLosVehiculos(); // Refresca ambas tablas

        // Persistir el vehículo en el archivo de texto del departamento correspondiente
        String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos();
        String rutaArchivoDepartamento = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";
        guardarVehiculoEnArchivo(vehiculoFinal, rutaArchivoDepartamento);

        // Si el departamento es nuevo, añadirlo al ComboBox de Principal
        if (!departamentosCargados.contains(departamento)) {
            departamentosCargados.add(departamento);
            if (Depto != null) {
                ((DefaultComboBoxModel<String>) Depto.getModel()).addElement(departamento);
            }
            if (cmbDepartamentoAVL != null) {
                ((DefaultComboBoxModel<String>) cmbDepartamentoAVL.getModel()).addElement(departamento);
            }
        }

        long endTime = System.nanoTime(); // Detiene el temporizador después de todas las operaciones
        double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0; // Calcula la duración

        if (jTextAreaMensajes != null) {
            jTextAreaMensajes.append("Vehículo agregado correctamente al ABB/AVL y tablas: " + vehiculoFinal.getPlaca() + "\n");
            jTextAreaMensajes.append("Operación AGREGAR completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n"); // Muestra el tiempo
        }
    } else {
        if (jTextAreaMensajes != null) {
            jTextAreaMensajes.append("Error: Intento de agregar vehículo nulo.\n");
        }
    }
}
    
    // Método auxiliar para obtener la ruta base de las carpetas de departamentos
        private String obtenerRutaBaseDepartamentos() {
            return "recursos"; // Ruta relativa, asumiendo que tus carpetas de departamentos están dentro de una carpeta 'recursos' en tu proyecto.
        }


        // Método para guardar un solo vehículo en su archivo correspondiente
    public void guardarVehiculoEnArchivo(Vehiculo vehiculo, String rutaArchivo) {
        try {

        File archivo = new File(rutaArchivo);
        File directorioPadre = archivo.getParentFile(); // Gets "recursos\Antigua_Guatemala" for example

        if (!directorioPadre.exists()) {
            if (directorioPadre.mkdirs()) { // mkdirs() creates all necessary but nonexistent parent directories
                System.out.println("DEBUG: Directorio creado: " + directorioPadre.getAbsolutePath());
            } else {
                System.err.println("ERROR: No se pudo crear el directorio: " + directorioPadre.getAbsolutePath());
                // You might want to throw an exception or show an error message here
                return; // Exit if directory creation fails
            }
        }

        try (FileWriter writer = new FileWriter(rutaArchivo, true)) { // 'true' for append mode
            // Format your vehicle data as a single line string
            String lineaVehiculo = vehiculo.getId() + "," +
                                   vehiculo.getPlaca() + "," +
                                   vehiculo.getDpi() + "," +
                                   vehiculo.getNombre() + "," +
                                   vehiculo.getMarca() + "," +
                                   vehiculo.getModelo() + "," +
                                   vehiculo.getAño() + "," +
                                   vehiculo.getMultas() + "," +
                                   vehiculo.getTraspasos() + "," +
                                   vehiculo.getDepartamento(); // Include department if it's part of the line

            writer.write(lineaVehiculo + System.lineSeparator()); // Write the line and a new line character
            System.out.println("DEBUG: Vehículo guardado en archivo: " + rutaArchivo);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al guardar el vehículo en el archivo: " + e.getMessage(), "Error de E/S", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Print the stack trace for debugging
    }
}

    // Obtiene el departamento actualmente seleccionado en el JComboBox (Depto)
    public String getDepartamentoSeleccionado() {
        if (Depto.getSelectedItem() != null) {
            return (String) Depto.getSelectedItem();
        }
        return "Todos"; // Valor por defecto si no hay nada seleccionado
    }

    // Se usa después de eliminar un vehículo para reflejar el cambio en el archivo.
private void reescribirArchivoVehiculos(String departamento) {
    String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos();
    String rutaArchivoCompleta = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";

    List<Vehiculo> vehiculosDelDepartamento = arbolABB.obtenerVehiculosPorDepartamento(departamento);

    long startTimeEscritura = System.nanoTime(); // Inicia el temporizador para la operación de escritura

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivoCompleta))) {
        bw.write("Placa,DPI,Nombre,Marca,Modelo,Año,Multas,Traspasos"); // Encabezado
        bw.newLine();
        for (Vehiculo v : vehiculosDelDepartamento) {
            bw.write(v.toCsvLine());
            bw.newLine();
        }
        jTextAreaMensajes.append("DEBUG - Archivo " + rutaArchivoCompleta + " reescrito.\n");

        long endTimeEscritura = System.nanoTime(); // Detiene el temporizador después de la escritura exitosa
        double durationInSeconds = (double) (endTimeEscritura - startTimeEscritura) / 1_000_000_000.0;
        jTextAreaMensajes.append("Operación REESCRITURA de archivo para departamento '" + departamento + "' completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");

    } catch (IOException ex) {
        // En caso de error, también podrías registrar el tiempo hasta el error si lo consideras útil,
        // pero lo más común es reportar el error en sí.
        long endTimeError = System.nanoTime();
        double durationInSeconds = (double) (endTimeError - startTimeEscritura) / 1_000_000_000.0;

        JOptionPane.showMessageDialog(this, "Error al reescribir el archivo: " + rutaArchivoCompleta + "\n" + ex.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        jTextAreaMensajes.append("Error al reescribir archivo para departamento '" + departamento + "'. Duración hasta el error: " + String.format("%.6f", durationInSeconds) + " segundos.\n");
        ex.printStackTrace();
    }
}
    
private void actualizarTablaConTodosLosVehiculos() {
    // Obtener todos los vehículos del ABB y actualizar Tabla
    List<Vehiculo> allVehiculosABB = arbolABB.obtenerVehiculosOrdenados(); // <-- CHANGE HERE!
    actualizarTabla(allVehiculosABB, Tabla);

    // Obtener todos los vehículos del AVL y actualizar Tabla1
    List<Vehiculo> allVehiculosAVL = arbolAVL.obtenerTodosLosVehiculos(); // This one is correct for AVL
    actualizarTabla(allVehiculosAVL, Tabla1);
}
// Este método es genérico para actualizar cualquier JTable con una lista de vehículos
private void actualizarTabla(List<Vehiculo> vehiculos, javax.swing.JTable tabla) {
    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    model.setRowCount(0); // Limpia las filas existentes de la tabla

    for (Vehiculo v : vehiculos) {
        model.addRow(new Object[]{
            v.getId(),
            v.getPlaca(),
            v.getDpi(),
            v.getNombre(),
            v.getMarca(),
            v.getModelo(),
            v.getAño(),
            v.getMultas(),
            v.getTraspasos(),
            v.getDepartamento()
        });
    }
}

    // Este método muestra en la tabla la lista de vehículos que se le pase.
    private void mostrarDatosEnTabla(List<Vehiculo> listaVehiculos) {
        tablaModelo.setRowCount(0); // Limpiar todas las filas existentes en la tabla

        for (Vehiculo vehiculo : listaVehiculos) {
            Object[] fila = {
                vehiculo.getId(),
                vehiculo.getPlaca(),
                vehiculo.getDpi(),
                vehiculo.getNombre(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAño(),
                vehiculo.getMultas(),
                vehiculo.getTraspasos(),
                vehiculo.getDepartamento() // AHORA INCLUYE DEPARTAMENTO
            };
            tablaModelo.addRow(fila);
        }
    }

    private void cargarVehiculosDesdeArchivo(String rutaArchivo, String departamento) {
    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        String line;
        br.readLine(); // Saltar la línea de encabezado (e.g., "Placa,DPI,Nombre,...")
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            if (parts.length == 8) {
                try {
                    // Cargar los campos String sin encriptar
                    String placa = parts[0].trim().toUpperCase();
                    String dpi = parts[1].trim();
                    String nombre = parts[2].trim();
                    String marca = parts[3].trim();
                    String modelo = parts[4].trim();
                    int año = Integer.parseInt(parts[5].trim());
                    int multas = Integer.parseInt(parts[6].trim());
                    int traspasos = Integer.parseInt(parts[7].trim());

                    // Crear el objeto Vehiculo sin encriptar
                    Vehiculo vehiculo = new Vehiculo(nextGlobalId++, placa, dpi, nombre, marca, modelo, año, multas, traspasos, departamento);

                    // Medir tiempo de inserción en ABB
                    long startTimeABB = System.nanoTime();
                    arbolABB.insertar(vehiculo);
                    long endTimeABB = System.nanoTime();
                    double durationABB = (endTimeABB - startTimeABB) / 1_000_000_000.0;

                    // Medir tiempo de inserción en AVL
                    long startTimeAVL = System.nanoTime();
                    arbolAVL.insertar(vehiculo);
                    long endTimeAVL = System.nanoTime();
                    double durationAVL = (endTimeAVL - startTimeAVL) / 1_000_000_000.0;

                    // Mostrar los tiempos en jTextAreaMensajes
                    jTextAreaMensajes.append(String.format(
                        "Vehículo con placa '%s' insertado en ABB en %.6f segundos.%n",
                        placa, durationABB));
                    jTextAreaMensajes.append(String.format(
                        "Vehículo con placa '%s' insertado en AVL en %.6f segundos.%n",
                        placa, durationAVL));

                } catch (NumberFormatException ex) {
                    jTextAreaMensajes.append("Error de formato numérico en línea: '" + line + "' en archivo " + rutaArchivo + ": " + ex.getMessage() + "\n");
                }
            } else {
                jTextAreaMensajes.append("Línea mal formateada en " + rutaArchivo + ": '" + line + "' (Esperado 8 partes de datos, encontrado " + parts.length + ")\n");
            }
        }
        jTextAreaMensajes.append("DEBUG: Archivo " + rutaArchivo + " cargado exitosamente.\n");
    } catch (IOException ex) {
        jTextAreaMensajes.append("Error al leer archivo " + rutaArchivo + ": " + ex.getMessage() + "\n");
    }
}


private void escribirVehiculosAArchivo(List<Vehiculo> vehiculos, String rutaArchivo) throws IOException {
    File file = new File(rutaArchivo);
    File parentDir = file.getParentFile();
    if (parentDir != null && !parentDir.exists()) {
        parentDir.mkdirs(); // Create directories if they don't exist
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, false))) { // false = overwrite
        for (Vehiculo vehiculo : vehiculos) {
            String line = String.format("%d;%s;%s;%s;%s;%s;%d;%d;%d;%s",
                    vehiculo.getId(), vehiculo.getPlaca(), vehiculo.getDpi(),
                    vehiculo.getNombre(), vehiculo.getMarca(), vehiculo.getModelo(),
                    vehiculo.getAño(), vehiculo.getMultas(), vehiculo.getTraspasos(),
                    vehiculo.getDepartamento());
            writer.write(line);
            writer.newLine();
        }
    } // The try-with-resources automatically closes the writer
}

// Este método carga todos los datos desde la estructura de carpetas de departamentos
private void cargarDatosDesdeCarpeta(File carpetaRaiz) {
    File[] subCarpetas = carpetaRaiz.listFiles(File::isDirectory);

    if (subCarpetas != null) {
        // Reinicia ambos árboles y el contador de IDs al cargar nueva data
        arbolABB.reset();
        arbolAVL.reset(); // ¡Reinicia el AVL!
        departamentosCargados.clear();
        nextGlobalId = 1; // Reinicia el ID global

        if (Depto != null) {
            Depto.removeAllItems();
            Depto.addItem("Todos");
        }
        if (cmbDepartamentoAVL != null) { // Reinicia el combo para AVL si es diferente
            cmbDepartamentoAVL.removeAllItems();
        }

        for (File subCarpeta : subCarpetas) {
            String nombreDepartamento = subCarpeta.getName();
            File archivoVehiculos = new File(subCarpeta, nombreDepartamento + "_vehiculos.txt");

            if (archivoVehiculos.exists()) {
                cargarVehiculosDesdeArchivo(archivoVehiculos.getAbsolutePath(), nombreDepartamento);

                // Agrega el departamento al ComboBox general si no está ya
                if (Depto != null && !departamentosCargados.contains(nombreDepartamento)) {
                    Depto.addItem(nombreDepartamento);
                    departamentosCargados.add(nombreDepartamento);
                }
                // Agrega el departamento al ComboBox específico de AVL si es diferente
                if (cmbDepartamentoAVL != null && ((DefaultComboBoxModel<String>)cmbDepartamentoAVL.getModel()).getIndexOf(nombreDepartamento) == -1) {
                    ((DefaultComboBoxModel<String>) cmbDepartamentoAVL.getModel()).addElement(nombreDepartamento);
                }

            } else {
                jTextAreaMensajes.append("Advertencia: No se encontró el archivo de vehículos para el departamento " + nombreDepartamento + " en " + subCarpeta.getAbsolutePath() + "\n");
            }
        }
        // Después de cargar todos los datos, actualiza ambas tablas
        actualizarTabla(arbolABB.obtenerVehiculosOrdenados(), Tabla); // <-- CHANGE THIS LINE
        actualizarTabla(arbolAVL.obtenerTodosLosVehiculos(), Tabla1);

        jTextAreaMensajes.append("Carga de datos desde carpeta completada. Total de vehículos en ABB: " + arbolABB.obtenerVehiculosOrdenados().size() + "\n");
        jTextAreaMensajes.append("Total de vehículos en AVL: " + arbolAVL.obtenerTodosLosVehiculos().size() + "\n");
    } else {
        jTextAreaMensajes.setText("Error: La carpeta seleccionada no contiene subcarpetas de departamentos o no se pudo listar su contenido.\n");
    }
}

    // Lógica para el botón de búsqueda por placa (BBuscar)
   private void buscarVehiculoPorPlaca() {
    long startTime = System.nanoTime(); // Inicia el temporizador

    String placaBuscada = BBuscar.getText().trim().toUpperCase();
    jTextAreaMensajes.setText(""); // Limpia el JTextArea antes de mostrar el mensaje de búsqueda

    if (placaBuscada.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese una placa para buscar.", "Error de Búsqueda", JOptionPane.WARNING_MESSAGE);
        actualizarTablaConTodosLosVehiculos(); // Muestra todos si el campo está vacío
        // No es necesario medir el tiempo si la búsqueda no se realizó
        return;
    }

    Vehiculo vehiculoEncontrado = arbolABB.buscarPorPlaca(placaBuscada); // Realiza la búsqueda aquí

    long endTime = System.nanoTime(); // Detiene el temporizador DESPUÉS DE LA BÚSQUEDA
    double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0; // Calcula la duración

    if (vehiculoEncontrado != null) {
        jTextAreaMensajes.append("Vehículo Encontrado:\n" + vehiculoEncontrado.toString() + "\n"); // Usa append

        mostrarDatosEnTabla(Arrays.asList(vehiculoEncontrado));

    } else {
        jTextAreaMensajes.append("Vehículo con placa '" + placaBuscada + "' no encontrado.\n"); // Usa append
        tablaModelo.setRowCount(0); // Limpiar la tabla si no se encuentra
    }

    // El mensaje de duración se muestra SIEMPRE al final de la lógica de búsqueda
    jTextAreaMensajes.append("Operación BUSCAR para placa '" + placaBuscada + "' completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");

    // Limpia el campo de búsqueda si lo deseas
    // BBuscar.setText("");
}
    
    // Lógica para el botón de eliminar (btnEliminar o EliminarActionPerformed)
    private void eliminarVehiculoPorId() {
        String idTexto = TextEliminar.getText().trim(); 
        jTextAreaMensajes.setText(""); 

        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del vehículo a eliminar en el campo de ID.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
long startTime = System.nanoTime();
        try {
            int idAEliminar = Integer.parseInt(idTexto);

            Vehiculo vehiculoAEliminar = arbolABB.buscarPorId(idAEliminar);

            if (vehiculoAEliminar != null) {
                String placaDelVehiculoAEliminar = vehiculoAEliminar.getPlaca();
                String departamentoDelVehiculo = vehiculoAEliminar.getDepartamento();

                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar el vehículo con ID: " + idAEliminar +
                    " y Placa: " + placaDelVehiculoAEliminar + " del departamento de " + departamentoDelVehiculo + "?\n" +
                    "Esta acción es irreversible y modificará el archivo.",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean eliminadoDelABB = arbolABB.eliminar(placaDelVehiculoAEliminar);

                    if (eliminadoDelABB) {
                        reescribirArchivoVehiculos(departamentoDelVehiculo);

                        JOptionPane.showMessageDialog(this, "Vehículo con ID " + idAEliminar + " eliminado exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                        TextEliminar.setText(""); 
    long endTime = System.nanoTime(); // Detiene el temporizador
    double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;

    jTextAreaMensajes.append("Operación ELIMINAR para placa '" + "' completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");
    TextEliminar.setText(""); // Limpiar el campo de eliminación
                        actualizarTablaConTodosLosVehiculos(); 
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo eliminar el vehículo del ABB. (Error interno o vehículo no encontrado)", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún vehículo con ID: " + idAEliminar + ".", "Registro No Encontrado", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método auxiliar para extraer el departamento del nombre del archivo (si es necesario)
    private String obtenerDepartamentoDesdeNombreArchivo(String nombreArchivo) {
        int index = nombreArchivo.indexOf("_vehiculos.txt");
        if (index != -1) {
            return nombreArchivo.substring(0, index);
        }
        return ""; 
    }
    // --- Nuevo método en Principal para manejar la modificación ---



public void actualizarVehiculoModificado(Vehiculo vehiculoOriginal, Vehiculo vehiculoModificado) {
    long startTime = System.nanoTime(); // Inicia el temporizador

    // Lógica para eliminar el original e insertar el modificado en ABB y AVL
    if (vehiculoOriginal != null && vehiculoModificado != null) {
        // Eliminar del ABB
        if (arbolABB.buscarPorPlaca(vehiculoOriginal.getPlaca()) != null) {
            arbolABB.eliminar(vehiculoOriginal.getPlaca());
        }
        // Eliminar del AVL
        if (arbolAVL.buscarPorPlaca(vehiculoOriginal.getPlaca()) != null) {
            arbolAVL.eliminar(vehiculoOriginal.getPlaca());
        }

        // Insertar el vehículo modificado en ambos árboles
        arbolABB.insertar(vehiculoModificado);
        arbolAVL.insertar(vehiculoModificado);

        actualizarTablaConTodosLosVehiculos(); // Refrescar ambas tablas

        removerVehiculoDeArchivo(vehiculoOriginal);
        // Guardar el vehículo modificado en su archivo correspondiente (podría ser el mismo o uno nuevo)
        guardarVehiculoEnArchivo(vehiculoModificado, obtenerRutaBaseDepartamentos() + File.separator + vehiculoModificado.getDepartamento() + File.separator + vehiculoModificado.getDepartamento() + "_vehiculos.txt");

        long endTime = System.nanoTime(); // Detiene el temporizador después de todas las operaciones
        double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0; // Calcula la duración

        JOptionPane.showMessageDialog(this, "Vehículo modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Muestra el tiempo en el jTextAreaMensajes
        jTextAreaMensajes.append("Operación MODIFICAR para vehículo " + vehiculoModificado.getPlaca() + " completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");

    } else {
        JOptionPane.showMessageDialog(this, "Error al actualizar vehículo.", "Error", JOptionPane.ERROR_MESSAGE);
        // Podrías registrar el tiempo de una operación fallida si lo necesitas
        long endTime = System.nanoTime();
        double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;
        jTextAreaMensajes.append("Operación MODIFICAR fallida en: " + String.format("%.6f", durationInSeconds) + " segundos (datos nulos).\n");
    }
}
private void removerVehiculoDeArchivo(Vehiculo vehiculoToRemove) {
    String departamento = vehiculoToRemove.getDepartamento();
    String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos();
    String rutaArchivoDepartamento = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";

    List<Vehiculo> vehiculosRestantes = new ArrayList<>();
    File file = new File(rutaArchivoDepartamento);

    try {
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                // Si tus archivos tienen encabezado, lo saltamos aquí:
                // br.readLine(); // Descomenta si hay encabezado

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    // Ahora estamos seguros de que los archivos se escribirán con 10 campos
                    // por el toCsvString(), así que esperamos 10 partes al leer.
                    if (parts.length == 10) { // <--- Asegúrate de que esta condición sea 10
                        try {
                            String currentPlaca = parts[1].trim();
                            if (!currentPlaca.equalsIgnoreCase(vehiculoToRemove.getPlaca())) {
                                // Aquí usas el constructor de 10 argumentos porque estás leyendo 10 campos
                                vehiculosRestantes.add(new Vehiculo(
                                    Integer.parseInt(parts[0].trim()), // ID
                                    parts[1].trim(), // PLACA
                                    parts[2].trim(), // DPI
                                    parts[3].trim(), // Nombre
                                    parts[4].trim(), // Marca
                                    parts[5].trim(), // Modelo
                                    Integer.parseInt(parts[6].trim()), // Año
                                    Integer.parseInt(parts[7].trim()), // Multas
                                    Integer.parseInt(parts[8].trim()), // Traspasos
                                    parts[9].trim() // Departamento
                                ));
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error al parsear número en el archivo durante la remoción: " + line + " - " + e.getMessage());
                        }
                    } else {
                        // Esto te avisará si una línea no tiene el número esperado de partes
                        System.err.println("DEBUG: Línea mal formateada en archivo de vehículos: " + line + " - Partes encontradas: " + parts.length);
                        // Opcional: Podrías loggear la línea y la ruta del archivo para depuración
                    }
                }
            }
        }

        // Después de leer y filtrar, reescribe el archivo con los vehículos restantes.
        // Aquí usamos el nuevo toCsvString() que genera 10 campos.
        try (FileWriter writer = new FileWriter(rutaArchivoDepartamento, false)) { // 'false' para sobrescribir
            // Si tus archivos tienen un encabezado, escríbelo aquí también
            // writer.write("ID,PLACA,DPI,Nombre,Marca,Modelo,Año,Multas,Traspasos,Departamento" + System.lineSeparator());

            for (Vehiculo v : vehiculosRestantes) {
                writer.write(v.toCsvString() + System.lineSeparator()); // <--- Usa toCsvString() AQUÍ
            }
            System.out.println("DEBUG: Vehículo removido del archivo de departamento: " + rutaArchivoDepartamento);
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error de I/O al remover vehículo del archivo: " + e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

// Método para obtener todos los departamentos cargados (útil para el ComboBox en las ventanas)
public List<String> obtenerTodosLosDepartamentos() {
    return new ArrayList<>(departamentosCargados); // Retorna una copia de la lista de departamentos
}
private void visualizarABB() {
    // Verificar si el árbol ABB está vacío antes de intentar visualizar
    // (Asumiendo que tienes un método para verificar si el ABB está vacío,
    // o que obtenerVehiculosOrdenados devolvería una lista vacía)
    if (arbolABB.obtenerVehiculosOrdenados().isEmpty()) { // O arbolABB.estaVacio(); si tienes ese método
        JOptionPane.showMessageDialog(this, "El árbol ABB está vacío. No hay nada que visualizar.", "Árbol Vacío", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    int limiteVisualizacion = 3000; // Define el límite de nodos a visualizar
    String dotCode = arbolABB.generarDotLimitado(limiteVisualizacion);
    String dotFilePath = "arbol_abb_limitado.dot"; // Nombre del archivo DOT temporal
    String imageFilePath = "arbol_abb_limitado.png"; // Nombre del archivo de imagen de salida

    try {
        // 1. Guardar el código DOT en un archivo .dot
        try (FileWriter writer = new FileWriter(dotFilePath)) {
            writer.write(dotCode);
        }
        System.out.println("DEBUG: Archivo DOT limitado generado en: " + dotFilePath);

        // 2. Ejecutar Graphviz (comando 'dot') para generar la imagen
        String graphvizPath = "dot"; // Asegúrate de que 'dot.exe' esté en el PATH
        // String graphvizPath = "C:\\Program Files\\Graphviz\\bin\\dot.exe"; // O la ruta completa

        ProcessBuilder pb = new ProcessBuilder(graphvizPath, "-Tpng", dotFilePath, "-o", imageFilePath);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("DEBUG: Imagen ABB generada en: " + imageFilePath);

            // 3. Crear y mostrar la nueva ventana con la imagen
            // ¡CAMBIO AQUÍ! Añade el título como segundo argumento al constructor.
            VisorArbolFrame visor = new VisorArbolFrame(imageFilePath, "Visualización de Árbol ABB");
            visor.setVisible(true);

        } else {
            // Captura la salida de error de Graphviz para depuración
            String errorOutput = new java.util.Scanner(process.getErrorStream()).useDelimiter("\\A").next();
            JOptionPane.showMessageDialog(this,
                "Error al ejecutar Graphviz (código: " + exitCode + "). Asegúrate de que 'dot.exe' esté en tu PATH.\n" +
                "Detalle de Graphviz:\n" + errorOutput,
                "Error de Graphviz para ABB", JOptionPane.ERROR_MESSAGE); // Título más específico
            System.err.println("DEBUG: Graphviz Error Output (ABB):\n" + errorOutput);
        }

    } catch (IOException | InterruptedException e) {
        JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar visualizar el ABB: " + e.getMessage(), "Error de Visualización", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {

    }
}


// Método para agregar un vehículo desde la interfaz de usuario al AVL
public void agregarVehiculoAVL() {
    try {
        // Asigna el siguiente ID global único al nuevo vehículo
        int id = nextGlobalId++;

        // Obtén los valores de los campos de texto del panel AVL
        String placa = txtPlacaAVL.getText().trim().toUpperCase(); // Convertir a mayúsculas
        String dpi = txtDPIAVL.getText().trim();
        String nombre = txtNombreAVL.getText().trim();
        String marca = txtMarcaAVL.getText().trim();
        String modelo = txtModeloAVL.getText().trim();
        int anio = Integer.parseInt(txtAnioAVL.getText());
        int multas = Integer.parseInt(txtMultasAVL.getText());
        int traspasos = Integer.parseInt(txtTraspasosAVL.getText());
        String departamento = (String) cmbDepartamentoAVL.getSelectedItem(); // Obtén el departamento seleccionado

        // Validaciones básicas
        if (placa.isEmpty() || dpi.isEmpty() || nombre.isEmpty() || departamento == null || departamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos para agregar un vehículo al AVL.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crea una nueva instancia de Vehiculo
        Vehiculo nuevoVehiculo = new Vehiculo(id, placa, dpi, nombre, marca, modelo, anio, multas, traspasos, departamento);

        // Inserta el nuevo vehículo en el árbol AVL
        arbolAVL.insertar(nuevoVehiculo);

        // ¡Actualiza Tabla1 para reflejar los cambios!
        actualizarTabla(arbolAVL.obtenerTodosLosVehiculos(), Tabla1);
        JOptionPane.showMessageDialog(this, "Vehículo agregado al AVL con ID: " + id, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCamposAVL(); // Limpia los campos después de agregar

        String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos(); // Debes tener este método
        String rutaArchivoDepartamento = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";
        guardarVehiculoEnArchivo(nuevoVehiculo, rutaArchivoDepartamento); // Debes tener este método

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Error de formato numérico en Año, Multas o Traspasos. Asegúrate de ingresar números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al agregar vehículo al AVL: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
// En Principal.java
private void eliminarVehiculoAVL() {
    long startTime = System.nanoTime(); // Inicia el temporizador

    String placaAEliminar = TextEliminar1.getText().trim().toUpperCase();
    if (placaAEliminar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese la placa del vehículo a eliminar del AVL.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        // No medimos el tiempo si la operación no procede por falta de entrada.
        return;
    }

    Vehiculo vehiculoAEliminar = arbolAVL.buscarPorPlaca(placaAEliminar);

    if (vehiculoAEliminar != null) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el vehículo con placa: " + placaAEliminar + " del AVL?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Realiza la eliminación del AVL
                arbolAVL.eliminar(placaAEliminar);
                // Ahora removerVehiculoDeArchivo NO lanza IOException, la maneja internamente.
                removerVehiculoDeArchivo(vehiculoAEliminar);

                // Actualiza la tabla del AVL
                actualizarTabla(arbolAVL.obtenerTodosLosVehiculos(), Tabla1);

                long endTime = System.nanoTime(); // Detiene el temporizador aquí, después de todas las operaciones exitosas
                double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;

                JOptionPane.showMessageDialog(this, "Vehículo con placa " + placaAEliminar + " eliminado exitosamente del AVL.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                TextEliminar1.setText("");

                // Muestra el tiempo en jTextAreaMensajes
                if (jTextAreaMensajes != null) {
                    jTextAreaMensajes.append("Operación ELIMINAR (AVL) para placa '" + placaAEliminar + "' completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");
                }

            } catch (Exception e) {
                // Si ocurre un error, también podemos registrar el tiempo hasta el error
                long endTimeError = System.nanoTime();
                double durationInSeconds = (double) (endTimeError - startTime) / 1_000_000_000.0;

                JOptionPane.showMessageDialog(this, "Error al eliminar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                if (jTextAreaMensajes != null) {
                    jTextAreaMensajes.append("Operación ELIMINAR (AVL) para placa '" + placaAEliminar + "' FAILED en: " + String.format("%.6f", durationInSeconds) + " segundos. Error: " + e.getMessage() + "\n");
                }
                e.printStackTrace();
            }
        }
    } else {

        long endTime = System.nanoTime(); // Detiene el temporizador después de la búsqueda fallida
        double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0;

        JOptionPane.showMessageDialog(this, "Vehículo con placa '" + placaAEliminar + "' no encontrado en el AVL para eliminar.", "No Encontrado", JOptionPane.INFORMATION_MESSAGE);
        TextEliminar1.setText("");

        if (jTextAreaMensajes != null) {
            jTextAreaMensajes.append("Operación ELIMINAR (AVL) para placa '" + placaAEliminar + "' (vehículo no encontrado) completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");
        }
    }
}
// Método para modificar un vehículo en el AVL
private void actualizarVehiculoEnArchivo(Vehiculo vehiculoModificado) throws IOException { // <--- Asegúrate de tener "throws IOException" aquí
    String departamento = vehiculoModificado.getDepartamento();
    String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos();
    String rutaArchivoDepartamento = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";

    List<Vehiculo> vehiculosTemporales = new ArrayList<>();
    File archivoOriginal = new File(rutaArchivoDepartamento);

    // Si el archivo no existe, no hay nada que actualizar.
    if (!archivoOriginal.exists()) {
        System.out.println("DEBUG: El archivo para el departamento " + departamento + " no existe. No se puede actualizar el vehículo.");
        // Opcional: Lanza una excepción o retorna.
        return;
    }

    // Leer todos los vehículos excepto el que se va a modificar (por placa)
    try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal))) {
        String line;


        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            // Asegúrate de que la lectura sea consistente con 10 campos
            if (parts.length == 10) {
                try {
                    String currentPlaca = parts[1].trim();
                    // Si no es el vehículo que estamos modificando, añadirlo a la lista temporal
                    if (!currentPlaca.equalsIgnoreCase(vehiculoModificado.getPlaca())) {
                        vehiculosTemporales.add(new Vehiculo(
                            Integer.parseInt(parts[0].trim()), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                            parts[4].trim(), parts[5].trim(), Integer.parseInt(parts[6].trim()),
                            Integer.parseInt(parts[7].trim()), Integer.parseInt(parts[8].trim()), parts[9].trim()
                        ));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear número en el archivo durante la actualización: " + line + " - " + e.getMessage());
                }
            } else {
                System.err.println("DEBUG: Línea mal formateada en archivo de vehículos durante la actualización: " + line + " - Partes encontradas: " + parts.length);
            }
        }
    } // El try-with-resources cierra el BufferedReader automáticamente

    // Añadir el vehículo modificado a la lista
    vehiculosTemporales.add(vehiculoModificado);

    // Reescribir todo el archivo con la lista actualizada
    try (FileWriter writer = new FileWriter(rutaArchivoDepartamento, false)) { // 'false' para sobrescribir

        for (Vehiculo v : vehiculosTemporales) {
            writer.write(v.toCsvString() + System.lineSeparator()); // <--- Usamos toCsvString() para 10 campos
        }
    } // El try-with-resources cierra el FileWriter automáticamente
    System.out.println("DEBUG: Vehículo actualizado en archivo de departamento: " + rutaArchivoDepartamento);
}
// Método para visualizar el árbol AVL usando Graphviz
private void visualizarAVL() {
    // Verifica si el árbol AVL está vacío
    if (arbolAVL.obtenerTodosLosVehiculos().isEmpty()) { // Asumo que este método te indica si hay vehículos
        JOptionPane.showMessageDialog(this, "El árbol AVL está vacío. No hay nada que visualizar.", "Árbol Vacío", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    int limiteVisualizacion = 3000; // Define el límite de nodos a visualizar
    // Asegúrate de que tu clase AVL tenga un método 'generarDotLimitado'
    String dotCode = arbolAVL.generarDotLimitado(limiteVisualizacion);
    String dotFilePath = "arbol_avl_limitado.dot"; // Nombre del archivo DOT temporal para AVL
    String imageFilePath = "arbol_avl_limitado.png"; // Nombre del archivo de imagen de salida para AVL

    try {
        // 1. Guarda el código DOT en un archivo .dot
        try (FileWriter writer = new FileWriter(dotFilePath)) {
            writer.write(dotCode);
        }
        System.out.println("DEBUG: Archivo DOT limitado para AVL generado en: " + dotFilePath);

        // 2. Ejecuta Graphviz (comando 'dot') para generar la imagen
        String graphvizPath = "dot"; // Asegúrate de que 'dot.exe' esté en tu PATH


        ProcessBuilder pb = new ProcessBuilder(graphvizPath, "-Tpng", dotFilePath, "-o", imageFilePath);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("DEBUG: Imagen AVL generada en: " + imageFilePath);

            // 3. Crea y muestra la nueva ventana con la imagen
            VisorArbolFrame visor = new VisorArbolFrame(imageFilePath, "Visualización de Árbol AVL");
            visor.setVisible(true);

        } else {
            // Captura la salida de error de Graphviz para depuración
            String errorOutput = new java.util.Scanner(process.getErrorStream()).useDelimiter("\\A").next();
            JOptionPane.showMessageDialog(this,
                    "Error al ejecutar Graphviz (código: " + exitCode + "). Asegúrate de que 'dot.exe' esté en tu PATH.\n" +
                    "Detalle de Graphviz:\n" + errorOutput,
                    "Error de Graphviz para AVL", JOptionPane.ERROR_MESSAGE);
            System.err.println("DEBUG: Graphviz Error Output (AVL):\n" + errorOutput);
        }

    } catch (IOException | InterruptedException e) {
        JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar visualizar el AVL: " + e.getMessage(), "Error de Visualización", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
    }
}
private void buscarVehiculoAVL() {
long startTime = System.nanoTime(); // Inicia el temporizador
    String placa = BuscarAVL.getText().trim().toUpperCase(); // Asegúrate de que este JTextField se llama 'BuscarAVL'
    jTextAreaMensajes.setText(""); // Limpia el JTextArea antes de mostrar el mensaje de búsqueda
    if (placa.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese una placa para buscar.", "Error de Búsqueda", JOptionPane.WARNING_MESSAGE);
        mostrarTodosLosVehiculos(); // Muestra todos los vehículos si el campo está vacío
        return; // No es necesario medir el tiempo si la búsqueda no se realizó
    }
    // Realiza la búsqueda en el AVL
    Vehiculo encontrado = arbolAVL.buscarPorPlaca(placa);
    long endTime = System.nanoTime(); // Detiene el temporizador después de la búsqueda
    double durationInSeconds = (double) (endTime - startTime) / 1_000_000_000.0; // Calcula la duración
    if (encontrado != null) {
        jTextAreaMensajes.append("Vehículo encontrado:\n" + encontrado.toString() + "\n"); // Usa append
        actualizarTabla(Arrays.asList(encontrado), Tabla1); // Muestra SOLO el vehículo encontrado
    } else {
        jTextAreaMensajes.append("Vehículo con placa '" + placa + "' no encontrado.\n"); // Usa append
        mostrarTodosLosVehiculos(); // Muestra todos los vehículos si no se encuentra
    }
    // Muestra el tiempo en el jTextAreaMensajes, independientemente de si se encontró o no
    jTextAreaMensajes.append("Operación BUSCAR (AVL) para placa '" + placa + "' completada en: " + String.format("%.6f", durationInSeconds) + " segundos.\n");
    // Limpia el campo de búsqueda si lo deseas
    // BuscarAVL.setText("");
    }


private void limpiarCamposAVL() {
    txtPlacaAVL.setText("");
    txtDPIAVL.setText("");
    txtNombreAVL.setText("");
    txtMarcaAVL.setText("");
    txtModeloAVL.setText("");
    txtAnioAVL.setText("");
    txtMultasAVL.setText("");
    txtTraspasosAVL.setText("");
    cmbDepartamentoAVL.setSelectedIndex(0); // Seleccionar el primer elemento o un valor por defecto
    // Si tienes txtIDAVL
    // txtIDAVL.setText("");
}
private javax.swing.JTextField txtPlacaAVL;
private javax.swing.JTextField txtDPIAVL;
private javax.swing.JTextField txtNombreAVL;
private javax.swing.JTextField txtMarcaAVL;
private javax.swing.JTextField txtModeloAVL;
private javax.swing.JTextField txtAnioAVL;
private javax.swing.JTextField txtMultasAVL;
private javax.swing.JTextField txtTraspasosAVL;
private javax.swing.JTextField txtIDAVL;
// Dentro de Principal.java
private void cargarDepartamentosIniciales() {
    String rutaBaseDepartamentos = obtenerRutaBaseDepartamentos(); // Asegúrate de que este método exista y devuelva la ruta correcta
    File carpetaBase = new File(rutaBaseDepartamentos);

    if (carpetaBase.exists() && carpetaBase.isDirectory()) {
        File[] subCarpetas = carpetaBase.listFiles(File::isDirectory); // Filtra solo directorios
        if (subCarpetas != null) {
            for (File subCarpeta : subCarpetas) {
                departamentosCargados.add(subCarpeta.getName());
            }
        }
    } else {
        jTextAreaMensajes.append("Advertencia: No se encontró la carpeta base de departamentos en: " + rutaBaseDepartamentos + "\n");
    }
}
private void mostrarTodosLosVehiculos() {
    List<Vehiculo> todosLosVehiculos = arbolAVL.obtenerTodosLosVehiculos(); // Obtiene todos los vehículos del AVL
    actualizarTabla(todosLosVehiculos, Tabla1); // Actualiza la Tabla1 con todos los vehículos
}
private String encriptar(String texto, int desplazamiento) {
    StringBuilder resultado = new StringBuilder();

    for (char c : texto.toCharArray()) {
        if (Character.isLetter(c)) {
            char base = Character.isLowerCase(c) ? 'a' : 'A';
            c = (char) ((c + desplazamiento - base) % 26 + base);
        } else if (Character.isDigit(c)) {
            c = (char) ((c - '0' + desplazamiento) % 10 + '0'); // Cifrar números
        }
        resultado.append(c);
    }
    return resultado.toString();
}

   private String desencriptar(String texto, int desplazamiento) {
       return encriptar(texto, -desplazamiento); // Desplazamiento inverso
   }

private void mostrarDatosEnTabla1(List<Vehiculo> listaVehiculos) {
    // Limpiar todas las filas existentes en Tabla1
    DefaultTableModel tablaModelo1 = (DefaultTableModel) Tabla1.getModel();
    tablaModelo1.setRowCount(0); 
    for (Vehiculo vehiculo : listaVehiculos) {
        Object[] fila = {
            vehiculo.getId(),
            vehiculo.getPlaca(),
            vehiculo.getDpi(),
            vehiculo.getNombre(),
            vehiculo.getMarca(),
            vehiculo.getModelo(),
            vehiculo.getAño(),
            vehiculo.getMultas(),
            vehiculo.getTraspasos(),
            vehiculo.getDepartamento()
        };
        tablaModelo1.addRow(fila); // Agregar fila a Tabla1
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Prin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Carga = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        Depto = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMensajes = new javax.swing.JTextArea();
        BBuscar = new javax.swing.JTextField();
        Buscador = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        TextEliminar = new javax.swing.JTextField();
        Agregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        txtPlacaModificar = new javax.swing.JTextField();
        btnVisualizarABB = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        BuscarAVL = new javax.swing.JTextField();
        btnBuscarAVL = new javax.swing.JButton();
        TextEliminar1 = new javax.swing.JTextField();
        BtnEliminarAVL = new javax.swing.JButton();
        btnAgregarAVL = new javax.swing.JButton();
        txtPlacaModificar1 = new javax.swing.JTextField();
        btnModificarAVL = new javax.swing.JButton();
        btnVisualizarABB1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cmbDepartamentoAVL = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        Encriptar = new javax.swing.JButton();
        Desencriptar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Prin.setBackground(new java.awt.Color(0, 0, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/check.png"))); // NOI18N
        jLabel1.setText("Sistema Inteligente de Registro de Vehículos y Evaluación (SIRVE)");

        Carga.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Carga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/carpeta.png"))); // NOI18N
        Carga.setText("Data Sheets");
        Carga.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N
        Carga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/call.png"))); // NOI18N
        jLabel2.setText("Admin");

        Tabla.setBackground(new java.awt.Color(102, 255, 255));
        Tabla.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PLACA", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos", "Departamento"
            }
        ));
        jScrollPane1.setViewportView(Tabla);

        Depto.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Depto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Antigua_Guatemala", "Chimaltenango", "Chiquimula", "Escuintla", "Guatemala", "Huehuetenango", "Peten", "Quetzaltenango", "San_Marcos", "Suchitepequez" }));
        Depto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeptoActionPerformed(evt);
            }
        });

        jTextAreaMensajes.setColumns(20);
        jTextAreaMensajes.setRows(5);
        jScrollPane2.setViewportView(jTextAreaMensajes);

        BBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBuscarActionPerformed(evt);
            }
        });

        Buscador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/placa.png"))); // NOI18N
        Buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscadorActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        TextEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextEliminarActionPerformed(evt);
            }
        });

        Agregar.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/editing.png"))); // NOI18N
        btnModificar.setText("MOD");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        txtPlacaModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlacaModificarActionPerformed(evt);
            }
        });

        btnVisualizarABB.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnVisualizarABB.setText("VER ABB");
        btnVisualizarABB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarABBActionPerformed(evt);
            }
        });

        Tabla1.setBackground(new java.awt.Color(102, 255, 255));
        Tabla1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PLACA", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos", "Departamento"
            }
        ));
        jScrollPane3.setViewportView(Tabla1);

        BuscarAVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarAVLActionPerformed(evt);
            }
        });

        btnBuscarAVL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/placa.png"))); // NOI18N
        btnBuscarAVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAVLActionPerformed(evt);
            }
        });

        BtnEliminarAVL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel.png"))); // NOI18N
        BtnEliminarAVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarAVLActionPerformed(evt);
            }
        });

        btnAgregarAVL.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnAgregarAVL.setText("Agregar");
        btnAgregarAVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAVLActionPerformed(evt);
            }
        });

        btnModificarAVL.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnModificarAVL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/editing.png"))); // NOI18N
        btnModificarAVL.setText("MOD");
        btnModificarAVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarAVLActionPerformed(evt);
            }
        });

        btnVisualizarABB1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnVisualizarABB1.setText("VER AVL");
        btnVisualizarABB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarABB1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/expediente.png"))); // NOI18N
        jButton1.setText("TRASPASOS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/expediente.png"))); // NOI18N
        jButton2.setText("MULTAS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cmbDepartamentoAVL.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        cmbDepartamentoAVL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Antigua_Guatemala", "Chimaltenango", "Chiquimula", "Escuintla", "Guatemala", "Huehuetenango", "Peten", "Quetzaltenango", "San_Marcos", "Suchitepequez" }));
        cmbDepartamentoAVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentoAVLActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton3.setText("Resumen");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Encriptar.setText("Encriptar");
        Encriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EncriptarActionPerformed(evt);
            }
        });

        Desencriptar.setText("Desencriptar");
        Desencriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesencriptarActionPerformed(evt);
            }
        });

        jButton4.setText("InOrden");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("PreOrden");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("PostOrden");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("InOrden");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("PreOrden");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("PostOrden");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PrinLayout = new javax.swing.GroupLayout(Prin);
        Prin.setLayout(PrinLayout);
        PrinLayout.setHorizontalGroup(
            PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrinLayout.createSequentialGroup()
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Carga)
                        .addGap(18, 18, 18)
                        .addComponent(Depto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PrinLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PrinLayout.createSequentialGroup()
                                        .addComponent(BBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(141, 141, 141)
                                        .addComponent(TextEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnEliminar)
                                        .addGap(90, 90, 90)
                                        .addComponent(Agregar)
                                        .addGap(103, 103, 103)
                                        .addComponent(txtPlacaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnModificar))
                                    .addGroup(PrinLayout.createSequentialGroup()
                                        .addComponent(cmbDepartamentoAVL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(178, 178, 178)
                                        .addComponent(jButton7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton8)
                                        .addGap(27, 27, 27)
                                        .addComponent(jButton9))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(PrinLayout.createSequentialGroup()
                                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PrinLayout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(104, 104, 104)
                                        .addComponent(jButton4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5)
                                        .addGap(31, 31, 31)
                                        .addComponent(jButton6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Encriptar)
                                        .addGap(18, 18, 18)
                                        .addComponent(Desencriptar))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrinLayout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnVisualizarABB1)
                                .addComponent(btnVisualizarABB))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrinLayout.createSequentialGroup()
                                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(3, 3, 3)))
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(BuscarAVL, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarAVL, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136)
                        .addComponent(TextEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEliminarAVL)
                        .addGap(100, 100, 100)
                        .addComponent(btnAgregarAVL)
                        .addGap(91, 91, 91)
                        .addComponent(txtPlacaModificar1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificarAVL)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PrinLayout.setVerticalGroup(
            PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Carga)
                            .addComponent(Depto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVisualizarABB)
                            .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Encriptar)
                                .addComponent(Desencriptar))))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jButton6))))
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnVisualizarABB1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(27, 27, 27)
                        .addComponent(jButton2)
                        .addGap(26, 26, 26)
                        .addComponent(jButton1))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Buscador)
                    .addComponent(BBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar)
                    .addComponent(Agregar)
                    .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPlacaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnModificar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDepartamentoAVL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarAVL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarAVL)
                                .addComponent(txtPlacaModificar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnModificarAVL)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscarAVL)
                            .addComponent(BtnEliminarAVL))
                        .addContainerGap(37, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Prin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Prin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargaActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Selecciona la carpeta raíz de los departamentos");

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpetaRaiz = fileChooser.getSelectedFile();
            if (carpetaRaiz.isDirectory()) {
                cargarDatosDesdeCarpeta(carpetaRaiz);
                jTextAreaMensajes.setText("Archivos cargados desde: " + carpetaRaiz.getAbsolutePath() + ". Total de vehículos: " + arbolABB.obtenerVehiculosOrdenados().size());
            } else {
                jTextAreaMensajes.setText("Por favor, selecciona una carpeta válida.");
            }
        } else {
            jTextAreaMensajes.setText("Carga de archivos cancelada.");
        }
    }//GEN-LAST:event_CargaActionPerformed
private void jComboBoxDepartamentosItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String departamentoSeleccionado = (String) Depto.getSelectedItem();
            
            if (departamentoSeleccionado != null && !departamentoSeleccionado.isEmpty()) {
                if (departamentoSeleccionado.equals("Todos")) {
                    mostrarDatosEnTabla(arbolABB.obtenerVehiculosOrdenados());
                } else {
                    List<Vehiculo> vehiculosFiltrados = arbolABB.obtenerVehiculosPorDepartamento(departamentoSeleccionado);
                    mostrarDatosEnTabla(vehiculosFiltrados);
                }
                jTextAreaMensajes.setText("Filtrando por departamento: " + departamentoSeleccionado);
            }
        }
        }
    private void DeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeptoActionPerformed

    }//GEN-LAST:event_DeptoActionPerformed

    private void BuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscadorActionPerformed
buscarVehiculoPorPlaca();
    }//GEN-LAST:event_BuscadorActionPerformed

    private void BBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBuscarActionPerformed
    
    }//GEN-LAST:event_BBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
eliminarVehiculoPorId();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
Agregar Agregar = new Agregar(this);
Agregar.setVisible(true);
    }//GEN-LAST:event_AgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
    // 1. Get the plate number from the text field where the user enters it
    String placaAModificar = txtPlacaModificar.getText().trim().toUpperCase(); // Assuming 'txtPlacaModificar' is your JTextField

    if (placaAModificar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa la placa del vehículo a modificar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        return; // Exit the method if the field is empty
    }

    Vehiculo vehiculoEncontrado = arbolABB.buscarPorPlaca(placaAModificar); 

    // 4. Check if the vehicle was found
    if (vehiculoEncontrado != null) {

        EditarVehiculo editarFrame = new EditarVehiculo(this, vehiculoEncontrado); 
        editarFrame.setVisible(true);
        editarFrame.setLocationRelativeTo(this);
        txtPlacaModificar.setText("");

    } else {
        JOptionPane.showMessageDialog(this, "Vehículo con placa '" + placaAModificar + "' no encontrado.", "No Encontrado", JOptionPane.INFORMATION_MESSAGE);
    }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnVisualizarABBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarABBActionPerformed
visualizarABB();
    }//GEN-LAST:event_btnVisualizarABBActionPerformed

    private void BuscarAVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarAVLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarAVLActionPerformed

    private void btnBuscarAVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAVLActionPerformed
buscarVehiculoAVL();
    }//GEN-LAST:event_btnBuscarAVLActionPerformed

    private void BtnEliminarAVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarAVLActionPerformed
eliminarVehiculoAVL();
    }//GEN-LAST:event_BtnEliminarAVLActionPerformed

    private void btnAgregarAVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAVLActionPerformed
Agregar Agregar = new Agregar(this);
Agregar.setVisible(true);
    }//GEN-LAST:event_btnAgregarAVLActionPerformed

    private void btnModificarAVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarAVLActionPerformed

    String placaAModificar = txtPlacaModificar1.getText().trim().toUpperCase(); // Assuming 'txtPlacaModificar' is your JTextField

    if (placaAModificar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa la placa del vehículo a modificar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        return; // Exit the method if the field is empty
    }

    Vehiculo vehiculoEncontrado = arbolAVL.buscarPorPlaca(placaAModificar); 

    if (vehiculoEncontrado != null) {

        EditarVehiculo editarFrame = new EditarVehiculo(this, vehiculoEncontrado); 

        editarFrame.setVisible(true);

        editarFrame.setLocationRelativeTo(this);

        txtPlacaModificar.setText("");

    } else {    
        JOptionPane.showMessageDialog(this, "Vehículo con placa '" + placaAModificar + "' no encontrado.", "No Encontrado", JOptionPane.INFORMATION_MESSAGE);
    }

    }//GEN-LAST:event_btnModificarAVLActionPerformed

    private void btnVisualizarABB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarABB1ActionPerformed
visualizarAVL();
    }//GEN-LAST:event_btnVisualizarABB1ActionPerformed

    private void TextEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextEliminarActionPerformed

    private void txtPlacaModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlacaModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlacaModificarActionPerformed

    private void cmbDepartamentoAVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentoAVLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDepartamentoAVLActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
Multas Multas = new Multas();
Multas.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
Traspasos Traspasos = new Traspasos();
Traspasos.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
   Resumen resumenFrame = new Resumen(); // Reemplaza JFrameResumen con el nombre real de tu clase JFrame de Resumen

    // 2. Hacerlo visible
    resumenFrame.setVisible(true);

    // Opcional: Centrar la ventana
    resumenFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void EncriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EncriptarActionPerformed
                                        
        long startTime = System.nanoTime();
        for (int i = 0; i < tablaModelo.getRowCount(); i++) {
            for (int j = 1; j < tablaModelo.getColumnCount(); j++) { // Empezar desde 1 para evitar ID
                Object value = tablaModelo.getValueAt(i, j);
                if (value instanceof String) {
                    String original = (String) value;
                    String encriptado = encriptar(original, 3);
                    tablaModelo.setValueAt(encriptado, i, j);
                }
            }
        }
        for (int i = 0; i < tablaModelo1.getRowCount(); i++) {
            for (int j = 1; j < tablaModelo1.getColumnCount(); j++) { // Empezar desde 1 para evitar ID
                Object value = tablaModelo1.getValueAt(i, j);
                if (value instanceof String) {
                    String original = (String) value;
                    String encriptado = encriptar(original, 3);
                    tablaModelo1.setValueAt(encriptado, i, j);
                }
            }
        }
        long endTime = System.nanoTime();
        double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
        jTextAreaMensajes.append(String.format("Encriptación completada en %.6f segundos.%n", durationSeconds));
    
    }//GEN-LAST:event_EncriptarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    long start = System.nanoTime();
    List<Vehiculo> listaInOrden = arbolABB.obtenerVehiculosOrdenados(); // InOrden ya definido en ABB
    mostrarDatosEnTabla(listaInOrden);
    long end = System.nanoTime();
    double seconds = (end - start) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Recorrido InOrden mostrado en %.6f segundos.%n", seconds));

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    long start = System.nanoTime();
    List<Vehiculo> listaPreOrden = arbolABB.obtenerVehiculosPreOrden(); // Método que debes implementar en ABB
    mostrarDatosEnTabla(listaPreOrden);
    long end = System.nanoTime();
    double seconds = (end - start) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Recorrido PreOrden mostrado en %.6f segundos.%n", seconds));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    long start = System.nanoTime();
    List<Vehiculo> listaPosOrden = arbolABB.obtenerVehiculosPosOrden(); // Método que debes implementar en ABB
    mostrarDatosEnTabla(listaPosOrden);
    long end = System.nanoTime();
    double seconds = (end - start) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Recorrido PosOrden mostrado en %.6f segundos.%n", seconds));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    long start = System.nanoTime();
    List<Vehiculo> listaInOrden = arbolAVL.obtenerTodosLosVehiculos(); // Recorrido InOrden ya implementado
    mostrarDatosEnTabla1(listaInOrden); // Actualiza Tabla1
    long end = System.nanoTime();
    double seconds = (end - start) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Recorrido InOrden de AVL mostrado en %.6f segundos.%n", seconds));
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    long start = System.nanoTime();
    List<Vehiculo> listaPreOrden = arbolAVL.obtenerVehiculosPreOrden(); // Debes tener este método en AVL
    mostrarDatosEnTabla1(listaPreOrden); // Actualiza Tabla1
    long end = System.nanoTime();
    double seconds = (end - start) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Recorrido PreOrden de AVL mostrado en %.6f segundos.%n", seconds));
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    long start = System.nanoTime();
    List<Vehiculo> listaPosOrden = arbolAVL.obtenerVehiculosPosOrden(); // Debes tener este método en AVL
    mostrarDatosEnTabla1(listaPosOrden); // Actualiza Tabla1
    long end = System.nanoTime();
    double seconds = (end - start) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Recorrido PosOrden de AVL mostrado en %.6f segundos.%n", seconds));
    }//GEN-LAST:event_jButton9ActionPerformed

    private void DesencriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesencriptarActionPerformed
                                            
    long startTime = System.nanoTime();
    
    // Desencriptar en Tabla
    for (int i = 0; i < tablaModelo.getRowCount(); i++) {
        for (int j = 1; j < tablaModelo.getColumnCount(); j++) { // Empieza en 1 para evitar columna ID
            Object value = tablaModelo.getValueAt(i, j);
            if (value instanceof String) {
                String encriptado = (String) value;
                String original = desencriptar(encriptado, 3); // Llama a desencriptar con el desplazamiento
                tablaModelo.setValueAt(original, i, j);
            }
        }
    }

    // Desencriptar en Tabla1
    for (int i = 0; i < tablaModelo1.getRowCount(); i++) {
        for (int j = 1; j < tablaModelo1.getColumnCount(); j++) { // Empieza en 1 para evitar columna ID
            Object value = tablaModelo1.getValueAt(i, j);
            if (value instanceof String) {
                String encriptado = (String) value;
                String original = desencriptar(encriptado, 3); // Llama a desencriptar con el desplazamiento
                tablaModelo1.setValueAt(original, i, j);
            }
        }
    }

    long endTime = System.nanoTime();
    double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
    jTextAreaMensajes.append(String.format("Desencriptación completada en %.6f segundos.%n", durationSeconds));

    }//GEN-LAST:event_DesencriptarActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JTextField BBuscar;
    private javax.swing.JButton BtnEliminarAVL;
    private javax.swing.JButton Buscador;
    private javax.swing.JTextField BuscarAVL;
    private javax.swing.JButton Carga;
    private javax.swing.JComboBox<String> Depto;
    private javax.swing.JButton Desencriptar;
    private javax.swing.JButton Encriptar;
    private javax.swing.JPanel Prin;
    private javax.swing.JTable Tabla;
    private javax.swing.JTable Tabla1;
    private javax.swing.JTextField TextEliminar;
    private javax.swing.JTextField TextEliminar1;
    private javax.swing.JButton btnAgregarAVL;
    private javax.swing.JButton btnBuscarAVL;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificarAVL;
    private javax.swing.JButton btnVisualizarABB;
    private javax.swing.JButton btnVisualizarABB1;
    private javax.swing.JComboBox<String> cmbDepartamentoAVL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaMensajes;
    private javax.swing.JTextField txtPlacaModificar;
    private javax.swing.JTextField txtPlacaModificar1;
    // End of variables declaration//GEN-END:variables

    private Resumen Resumen(Principal aThis) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
