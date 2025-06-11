
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */

public class Principal extends javax.swing.JFrame {
    private Map<String, String> archivosVehiculosPorDepto;
    private List<Vector<String>> allTableData; // <<-- DECLARAR ESTA AQUÍ
        private ABB arbolABB; // Instancia de tu árbol ABB
 private Set<String> departamentosCargados;

        
    private JTextField txtPlacaBuscar; // Para que el usuario ingrese la placa
    private JButton btnBuscar; // Botón para iniciar la búsqueda
    private JTextArea areaResultadoBusqueda; 
    
      public Principal() {
        initComponents();

       departamentosCargados = new HashSet<>();
         arbolABB = new ABB();
tablaModelo = (DefaultTableModel) Tabla.getModel();
  tablaModelo = (DefaultTableModel) Tabla.getModel();
        // Definir las columnas explícitamente, AHORA INCLUYENDO "Departamento"
        tablaModelo.setColumnIdentifiers(new Object[]{"ID", "PLACA", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos", "Departamento"});
        
        departamentosCargados = new HashSet<>();
    txtPlacaBuscar = new JTextField(15);
    btnBuscar = new JButton("Buscar Vehículo por Placa");
    areaResultadoBusqueda = new JTextArea(5, 30); // O un JLabel, o actualizar la JTable
    areaResultadoBusqueda.setEditable(false);
      Depto.removeAllItems(); 
        Depto.addItem("Todos");
        Depto.setSelectedItem("Todos");
       
        
    // Configurar JComboBox
    Depto.addItem("Todos"); // Opción para ver todos los departamentos
    Depto.setSelectedItem("Todos"); // Seleccionar por defecto
    
    }
private DefaultTableModel tablaModelo;
public Set<String> getDepartamentosCargados() {
    return new HashSet<>(departamentosCargados); // Retorna una copia para evitar modificaciones externas
}



private static final String[] ARCHIVOS_DEPARTAMENTOS = {
    "Antigua_Guatemala_vehiculos.txt", "Chimaltenango_vehiculos.txt", "Escuintla_vehiculos.txt",
    "Guatemala_vehiculos.txt", "Huehuetenango_vehiculos.txt", "Peten_vehiculos.txt",
    "Quetzaltenango_vehiculos.txt", "San_Marcos_vehiculos.txt", "Suchitepequez_vehiculos.txt",
    "Chiquimula_vehiculos.txt"
};
    // Este método es LLAMADO DESDE LA CLASE AGREGAR (por eso debe ser public)
    public void agregarVehiculoDesdeVentana(Vehiculo vehiculo, String departamento) {
        if (vehiculo != null) {
            vehiculo.setPlaca(vehiculo.getPlaca().trim().toUpperCase());
            vehiculo.setDepartamento(departamento);

            arbolABB.insertar(vehiculo);

            actualizarTablaConTodosLosVehiculos(); // Refresca la tabla completa

            // Persistir el vehículo en el archivo de texto del departamento correspondiente
            String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos(); 
            String rutaArchivoDepartamento = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";
            guardarVehiculoEnArchivo(vehiculo, rutaArchivoDepartamento);

            // Si el departamento es nuevo y no está en el ComboBox, añadirlo
            if (!departamentosCargados.contains(departamento)) {
                departamentosCargados.add(departamento);
                ((DefaultComboBoxModel<String>) Depto.getModel()).addElement(departamento);
            }
            jTextAreaMensajes.append("DEBUG - Vehículo agregado correctamente al ABB y tabla: " + vehiculo.getPlaca() + "\n");

        } else {
            jTextAreaMensajes.append("DEBUG - Intento de agregar vehículo nulo.\n");
        }
    }
    
    // Método auxiliar para obtener la ruta base de las carpetas de departamentos
    // ¡¡¡IMPORTANTE!!! AJUSTA ESTA RUTA A LA UBICACIÓN REAL DE TUS ARCHIVOS
    private String obtenerRutaBaseDepartamentos() {
        // Por ejemplo:
        // return "C:" + File.separator + "MisDatosVehiculos"; 
        // return System.getProperty("user.dir") + File.separator + "data"; // Si tus datos están en una carpeta 'data' en el directorio del proyecto
        return "recursos"; // Ruta relativa, asumiendo que tus carpetas de departamentos están dentro de una carpeta 'recursos' en tu proyecto.
    }


    // Método para guardar un solo vehículo en su archivo correspondiente
public void guardarVehiculoEnArchivo(Vehiculo vehiculo, String rutaArchivo) {
    try {
        // 1. Get the parent directory path from the file path
        File archivo = new File(rutaArchivo);
        File directorioPadre = archivo.getParentFile(); // Gets "recursos\Antigua_Guatemala" for example

        // 2. Check if the parent directory exists, if not, create it
        if (!directorioPadre.exists()) {
            if (directorioPadre.mkdirs()) { // mkdirs() creates all necessary but nonexistent parent directories
                System.out.println("DEBUG: Directorio creado: " + directorioPadre.getAbsolutePath());
            } else {
                System.err.println("ERROR: No se pudo crear el directorio: " + directorioPadre.getAbsolutePath());
                // You might want to throw an exception or show an error message here
                return; // Exit if directory creation fails
            }
        }

        // 3. Now that the directory is guaranteed to exist, proceed with writing the file
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
    
    // Método para reescribir un archivo completo de un departamento
    // Se usa después de eliminar un vehículo para reflejar el cambio en el archivo.
    private void reescribirArchivoVehiculos(String departamento) {
        String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos(); 
        String rutaArchivoCompleta = rutaCarpetaDepartamentos + File.separator + departamento + File.separator + departamento + "_vehiculos.txt";

        List<Vehiculo> vehiculosDelDepartamento = arbolABB.obtenerVehiculosPorDepartamento(departamento);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivoCompleta))) {
            bw.write("Placa,DPI,Nombre,Marca,Modelo,Año,Multas,Traspasos"); // Encabezado
            bw.newLine();
            for (Vehiculo v : vehiculosDelDepartamento) {
                bw.write(v.toCsvLine()); 
                bw.newLine();
            }
            jTextAreaMensajes.append("DEBUG - Archivo " + rutaArchivoCompleta + " reescrito.\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al reescribir el archivo: " + rutaArchivoCompleta + "\n" + ex.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // Este método refresca la tabla con TODOS los vehículos actualmente en el ABB
    private void actualizarTablaConTodosLosVehiculos() {
        tablaModelo.setRowCount(0); // Limpiar la tabla
        List<Vehiculo> todosLosVehiculos = arbolABB.obtenerVehiculosOrdenados(); // Obtener todos del ABB
        for (Vehiculo v : todosLosVehiculos) {
            tablaModelo.addRow(new Object[]{
                v.getId(), v.getPlaca(), v.getDpi(), v.getNombre(), v.getMarca(),
                v.getModelo(), v.getAño(), v.getMultas(), v.getTraspasos(), v.getDepartamento() // AHORA INCLUYE DEPARTAMENTO
            });
        }
    }

    // Este método muestra en la tabla la lista de vehículos que se le pase
    // Se usa para filtrado (por departamento) o para mostrar el resultado de una búsqueda.
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
    
    // Método para cargar vehículos desde un archivo específico
    private void cargarVehiculosDesdeArchivo(String rutaArchivo, String departamento) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            br.readLine(); // Salta la línea de encabezado
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) { // Placa,DPI,Nombre,Marca,Modelo,Año,Multas,Traspasos (8 campos en el CSV)
                    try {
                        String placa = parts[0].trim().toUpperCase(); 
                        String dpi = parts[1].trim();
                        String nombre = parts[2].trim();
                        String marca = parts[3].trim();
                        String modelo = parts[4].trim();
                        int año = Integer.parseInt(parts[5].trim());
                        int multas = Integer.parseInt(parts[6].trim());
                        int traspasos = Integer.parseInt(parts[7].trim());

                        Vehiculo vehiculo = new Vehiculo(placa, dpi, nombre, marca, modelo, año, multas, traspasos);
                        vehiculo.setDepartamento(departamento); 
                        arbolABB.insertar(vehiculo); 

                    } catch (NumberFormatException ex) {
                        jTextAreaMensajes.append("Error de formato numérico en: '" + line + "' en archivo " + rutaArchivo + ": " + ex.getMessage() + "\n");
                    }
                } else {
                    jTextAreaMensajes.append("Línea mal formateada en " + rutaArchivo + ": '" + line + "' (Esperado 8 partes, encontrado " + parts.length + ")\n");
                }
            }
        } catch (IOException ex) {
            jTextAreaMensajes.append("Error al leer archivo " + rutaArchivo + ": " + ex.getMessage() + "\n");
        }
    }

    // Método para cargar todos los datos desde la estructura de carpetas de departamentos
    private void cargarDatosDesdeCarpeta(File carpetaRaiz) {
        File[] subCarpetas = carpetaRaiz.listFiles(File::isDirectory);

        if (subCarpetas != null) {
            arbolABB.reset(); 
            departamentosCargados.clear(); 
            
            if (Depto != null) { 
                Depto.removeAllItems();
                Depto.addItem("Todos"); 
            }

            for (File subCarpeta : subCarpetas) {
                String nombreDepartamento = subCarpeta.getName();
                
                File archivoVehiculos = new File(subCarpeta, nombreDepartamento + "_vehiculos.txt");
                
                if (archivoVehiculos.exists()) {
                    cargarVehiculosDesdeArchivo(archivoVehiculos.getAbsolutePath(), nombreDepartamento);
                    
                    if (Depto != null && !departamentosCargados.contains(nombreDepartamento)) {
                        Depto.addItem(nombreDepartamento);
                        departamentosCargados.add(nombreDepartamento); 
                    }
                } else {
                    jTextAreaMensajes.append("Advertencia: No se encontró el archivo de vehículos para el departamento " + nombreDepartamento + " en " + subCarpeta.getAbsolutePath() + "\n");
                }
            }
            actualizarTablaConTodosLosVehiculos();
            jTextAreaMensajes.append("Carga de datos desde carpeta completada. Total de vehículos en ABB: " + arbolABB.obtenerVehiculosOrdenados().size() + "\n");

        } else {
            jTextAreaMensajes.setText("Error: La carpeta seleccionada no contiene subcarpetas de departamentos o no se pudo listar su contenido.\n");
        }
    }

    // Lógica para el botón de búsqueda por placa (BBuscar)
    private void buscarVehiculoPorPlaca() {
        String placaBuscada = BBuscar.getText().trim().toUpperCase(); 
        jTextAreaMensajes.setText(""); 

        if (placaBuscada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una placa para buscar.", "Error de Búsqueda", JOptionPane.WARNING_MESSAGE);
            actualizarTablaConTodosLosVehiculos(); // Muestra todos si el campo está vacío
            return;
        }

        Vehiculo vehiculoEncontrado = arbolABB.buscarPorPlaca(placaBuscada);

        if (vehiculoEncontrado != null) {
            jTextAreaMensajes.setText("Vehículo Encontrado:\n" + vehiculoEncontrado.toString());
            // Muestra SOLO el vehículo encontrado en la tabla
            mostrarDatosEnTabla(Arrays.asList(vehiculoEncontrado)); 
        } else {
            jTextAreaMensajes.setText("Vehículo con placa '" + placaBuscada + "' no encontrado.");
            tablaModelo.setRowCount(0); // Limpiar la tabla si no se encuentra
        }
    }
    
    // Lógica para el botón de eliminar (btnEliminar o EliminarActionPerformed)
    private void eliminarVehiculoPorId() {
        String idTexto = TextEliminar.getText().trim(); 
        jTextAreaMensajes.setText(""); 

        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del vehículo a eliminar en el campo de ID.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

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
    private void modificarVehiculoAction() {
        String placaAModificar = txtPlacaModificar.getText().trim().toUpperCase();

        if (placaAModificar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa la placa del vehículo a modificar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Vehiculo vehiculoEncontrado = arbolABB.buscarPorPlaca(placaAModificar);

        if (vehiculoEncontrado != null) {
            // Abre la ventana de edición y le pasa el vehículo encontrado
            abrirVentanaEditarVehiculo(vehiculoEncontrado);
        } else {
            JOptionPane.showMessageDialog(this, "Vehículo con placa '" + placaAModificar + "' no encontrado.", "No Encontrado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // --- Nuevo método en Principal para abrir la ventana de edición ---
    private void abrirVentanaEditarVehiculo(Vehiculo vehiculo) {
        // Asumiendo que ahora tienes una clase EditarVehiculo o que has adaptado Agregar
        EditarVehiculo editarFrame = new EditarVehiculo(this, vehiculo); // Le pasas 'this' y el Vehiculo
        editarFrame.setVisible(true);
    }

    // Este método es CRUCIAL. Es el que la ventana EditarVehiculo llamará.
    // Necesitas eliminar el vehículo viejo y reinsertar el nuevo en el ABB,
    // y luego actualizar los archivos.
    public void actualizarVehiculoModificado(Vehiculo vehiculoOriginal, Vehiculo vehiculoModificado) {
        // Paso 1: Eliminar el vehículo original del ABB
        // (Esto es necesario si la placa cambió, o si la posición en el ABB podría cambiar)
        // Podrías pasar solo la placa original y el nuevo objeto, o ambos objetos Vehiculo.
        arbolABB.eliminar(vehiculoOriginal.getPlaca()); // Elimina por la placa original

        // Paso 2: Insertar el vehículo modificado en el ABB
        // El ID del vehículo modificado ya debería ser el mismo que el original.
        arbolABB.insertar(vehiculoModificado);

        // Paso 3: Reestructurar los archivos
        // Esto es un poco más complejo, ya que el departamento podría haber cambiado.
        // Opción A: Reescritura completa del departamento original y el nuevo (si cambió)
        // y luego actualizar toda la tabla.
        if (!vehiculoOriginal.getDepartamento().equals(vehiculoModificado.getDepartamento())) {
            // Si el departamento cambió, reescribe el archivo del departamento original
            reescribirArchivoVehiculos(vehiculoOriginal.getDepartamento());
            // Y guarda el vehículo modificado en el nuevo departamento
            String rutaCarpetaDepartamentos = obtenerRutaBaseDepartamentos();
            String rutaNuevoArchivoDepartamento = rutaCarpetaDepartamentos + File.separator + vehiculoModificado.getDepartamento() + File.separator + vehiculoModificado.getDepartamento() + "_vehiculos.txt";
            guardarVehiculoEnArchivo(vehiculoModificado, rutaNuevoArchivoDepartamento);

            // Asegúrate de que el nuevo departamento esté en el ComboBox si no lo estaba
            if (!departamentosCargados.contains(vehiculoModificado.getDepartamento())) {
                departamentosCargados.add(vehiculoModificado.getDepartamento());
                ((DefaultComboBoxModel<String>) Depto.getModel()).addElement(vehiculoModificado.getDepartamento());
            }

        } else {
            // Si el departamento no cambió, solo reescribe el archivo de ese departamento
            reescribirArchivoVehiculos(vehiculoModificado.getDepartamento());
        }

        // Finalmente, actualiza la tabla en Principal para reflejar los cambios
        actualizarTablaConTodosLosVehiculos();
        JOptionPane.showMessageDialog(this, "Vehículo modificado con éxito.", "Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
 private void visualizarABB() {
        int limiteVisualizacion = 500; // Define el límite de nodos a visualizar
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
                VisorArbolFrame visor = new VisorArbolFrame(imageFilePath);
                visor.setVisible(true);

            } else {
                // ... (manejo de errores de Graphviz) ...
                String errorOutput = new java.util.Scanner(process.getErrorStream()).useDelimiter("\\A").next();
                JOptionPane.showMessageDialog(this,
                    "Error al ejecutar Graphviz (código: " + exitCode + "). Asegúrate de que 'dot.exe' esté en tu PATH.\n" +
                    "Detalle de Graphviz:\n" + errorOutput,
                    "Error de Graphviz", JOptionPane.ERROR_MESSAGE);
                System.err.println("DEBUG: Graphviz Error Output:\n" + errorOutput);
            }

        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar visualizar el ABB: " + e.getMessage(), "Error de Visualización", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Opcional: puedes eliminar los archivos temporales .dot y .png aquí
            // (esto los elimina inmediatamente después de que se abre la ventana, si eso es lo que quieres)
            // new File(dotFilePath).delete();
            // new File(imageFilePath).delete();
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
        ABB = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        Depto = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMensajes = new javax.swing.JTextArea();
        BBuscar = new javax.swing.JTextField();
        Buscador = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        TextEliminar = new javax.swing.JTextField();
        Agregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        txtPlacaModificar = new javax.swing.JTextField();
        btnVisualizarABB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Prin.setBackground(new java.awt.Color(0, 0, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/check.png"))); // NOI18N
        jLabel1.setText("Sistema Inteligente de Registro de Vehículos y Evaluación (SIRVE)");

        Carga.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Carga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cargando.png"))); // NOI18N
        Carga.setText("Data Sheets");
        Carga.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12))); // NOI18N
        Carga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Admin");

        ABB.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        ABB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/arbol-de-decision.png"))); // NOI18N
        ABB.setText("ABB");
        ABB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABBActionPerformed(evt);
            }
        });

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

        Buscador.setText("b");
        Buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscadorActionPerformed(evt);
            }
        });

        Eliminar.setText("X");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        txtPlacaModificar.setText("MOD");

        btnVisualizarABB.setText("VER ABB");
        btnVisualizarABB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarABBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PrinLayout = new javax.swing.GroupLayout(Prin);
        Prin.setLayout(PrinLayout);
        PrinLayout.setHorizontalGroup(
            PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addComponent(Carga)
                        .addGap(18, 18, 18)
                        .addComponent(Depto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
            .addGroup(PrinLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ABB)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPlacaModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Eliminar)
                            .addComponent(Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificar)))
                    .addComponent(Agregar)
                    .addComponent(btnVisualizarABB))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        PrinLayout.setVerticalGroup(
            PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Carga)
                            .addComponent(Depto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addComponent(ABB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Buscador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Eliminar)
                            .addComponent(TextEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Agregar)
                        .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PrinLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(btnModificar))
                            .addGroup(PrinLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtPlacaModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVisualizarABB)))
                .addGap(528, 528, 528))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Prin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Prin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void ABBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABBActionPerformed
        if (arbolABB.obtenerVehiculosOrdenados().isEmpty()) {
            jTextAreaMensajes.setText("No hay datos cargados para mostrar en el ABB.");
            return;
        }
        List<Vehiculo> vehiculosOrdenados = arbolABB.obtenerVehiculosOrdenados();
        mostrarDatosEnTabla(vehiculosOrdenados);
        jTextAreaMensajes.setText("Datos cargados del ABB y mostrados en la tabla (ordenados por placa).");
        Depto.setSelectedItem("Todos");
    }//GEN-LAST:event_ABBActionPerformed
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

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
eliminarVehiculoPorId();
    }//GEN-LAST:event_EliminarActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
Agregar Agregar = new Agregar(this);
Agregar.setVisible(true);
    }//GEN-LAST:event_AgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
    // 1. Get the plate number from the text field where the user enters it
    String placaAModificar = txtPlacaModificar.getText().trim().toUpperCase(); // Assuming 'txtPlacaModificar' is your JTextField

    // 2. Validate if the plate field is empty
    if (placaAModificar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingresa la placa del vehículo a modificar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        return; // Exit the method if the field is empty
    }

    // 3. Search for the vehicle in your data structure (e.g., your ABB)
    // Make sure 'arbolABB' is an instance of your ABB and has a 'buscarPorPlaca' method
    Vehiculo vehiculoEncontrado = arbolABB.buscarPorPlaca(placaAModificar); 

    // 4. Check if the vehicle was found
    if (vehiculoEncontrado != null) {
        // 5. If found, create an instance of EditarVehiculo,
        //    passing 'this' (the Principal instance) and the found 'Vehiculo' object.
        EditarVehiculo editarFrame = new EditarVehiculo(this, vehiculoEncontrado); 
        
        // 6. Make the EditarVehiculo window visible
        editarFrame.setVisible(true);
        
        // Optional: Center the new window relative to Principal
        editarFrame.setLocationRelativeTo(this);

        // Optional: Clear the search text field in Principal after opening the edit window
        txtPlacaModificar.setText("");

    } else {
        // 7. If the vehicle was not found, show a message to the user
        JOptionPane.showMessageDialog(this, "Vehículo con placa '" + placaAModificar + "' no encontrado.", "No Encontrado", JOptionPane.INFORMATION_MESSAGE);
    }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnVisualizarABBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarABBActionPerformed
        visualizarABB();
    }//GEN-LAST:event_btnVisualizarABBActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ABB;
    private javax.swing.JButton Agregar;
    private javax.swing.JTextField BBuscar;
    private javax.swing.JButton Buscador;
    private javax.swing.JButton Carga;
    private javax.swing.JComboBox<String> Depto;
    private javax.swing.JButton Eliminar;
    private javax.swing.JPanel Prin;
    private javax.swing.JTable Tabla;
    private javax.swing.JTextField TextEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVisualizarABB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaMensajes;
    private javax.swing.JTextField txtPlacaModificar;
    // End of variables declaration//GEN-END:variables
}
