import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.LocalDate; // Necesario para la fecha de Multa y Traspaso
import java.time.format.DateTimeParseException; // Para manejar errores de formato de fecha

public class Resumen extends javax.swing.JFrame {

    // Listas para almacenar todos los datos de los diferentes archivos
    private List<Vehiculo> todosLosVehiculos;
    private List<Multa> todasLasMultas;
    private List<Traspaso> todosLosTraspasos;



    public Resumen() {
        initComponents(); // ¡IMPORTANTE! Inicializar componentes primero

        // Inicializar todas las listas
        todosLosVehiculos = new ArrayList<>();
        todasLasMultas = new ArrayList<>();
        todosLosTraspasos = new ArrayList<>();

        TextM.setText("Por favor, seleccione una carpeta con archivos de vehículos, multas y traspasos.");
    }

    // --- Métodos de Carga Específicos ---

    private void cargarVehiculosDesdeArchivo(File file) {
        int loadedCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Saltar la línea de encabezado
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",");
                if (parts.length >= 8) { // Placa,DPI,Nombre,Marca,Modelo,Año,Multas,Traspasos
                    try {
                        String placa = parts[0].trim();
                        String dpi = parts[1].trim();
                        String nombre = parts[2].trim();
                        String marca = parts[3].trim();
                        String modelo = parts[4].trim();
                        int año = Integer.parseInt(parts[5].trim());
                        int multas = Integer.parseInt(parts[6].trim());
                        int traspasos = Integer.parseInt(parts[7].trim());

                        // Inferir departamento del nombre del archivo
                        String departamento = file.getName().replace("_vehiculos.txt", "");
                        departamento = departamento.replace("_", " ");

                        Vehiculo nuevoVehiculo = new Vehiculo(placa, dpi, nombre, marca, modelo, año, multas, traspasos);
                        nuevoVehiculo.setDepartamento(departamento);
                        todosLosVehiculos.add(nuevoVehiculo); // Añadir a la lista global
                        loadedCount++;
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato de número en archivo " + file.getName() + " en línea " + lineNumber + ": " + e.getMessage() + " (Línea: " + line + ")");
                    }
                } else {
                    System.err.println("Error de formato en archivo " + file.getName() + " en línea " + lineNumber + ": Columnas insuficientes. (Línea: " + line + ")");
                }
            }
            TextM.append("Cargados " + loadedCount + " vehículos desde '" + file.getName() + "'.\n");
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de vehículos " + file.getName() + ": " + e.getMessage());
        }
    }

   private void cargarMultasDesdeArchivo(File file) {
        int loadedCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Saltar la línea de encabezado
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",");
                // Asegúrate de que haya suficientes partes para todos los campos
                if (parts.length >= 4) { // Placa,Fecha,Descripcion,Monto
                    try {
                        String placa = parts[0].trim();
                        String fechaStr = parts[1].trim(); // Capturamos la fecha como String
                        String descripcion = parts[2].trim();
                        double monto = Double.parseDouble(parts[3].trim());

                        // Inferir departamento del nombre del archivo (similar a cómo lo haces en Vehiculo)
                        // Si el archivo se llama "Antigua_Guatemala_multas.txt", el departamento sería "Antigua Guatemala"
                        String departamento = file.getName().replace("_multas.txt", "");
                        departamento = departamento.replace("_", " ");

                        // Llamar al constructor de Multa con los 5 argumentos
                        Multa nuevaMulta = new Multa(placa, fechaStr, descripcion, monto, departamento);
                        todasLasMultas.add(nuevaMulta);
                        loadedCount++;
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato de número en archivo de multas " + file.getName() + " en línea " + lineNumber + ": " + e.getMessage() + " (Línea: " + line + ")");
                    }
                } else {
                    System.err.println("Error de formato en archivo de multas " + file.getName() + " en línea " + lineNumber + ": Columnas insuficientes. (Línea: " + line + ")");
                }
            }
            TextM.append("Cargadas " + loadedCount + " multas desde '" + file.getName() + "'.\n");
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de multas " + file.getName() + ": " + e.getMessage());
        }
    }

    private void cargarTraspasosDesdeArchivo(File file) {
        int loadedCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Saltar la línea de encabezado
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",");
                // Ahora necesitamos 6 campos del archivo para los 6 argumentos del constructor (placa, dpiAnterior, nombreAnterior, fecha, dpiNuevo, nombreNuevo)
                if (parts.length >= 6) { // Placa,DPI_Anterior,Nombre_Anterior,Fecha,DPI_Nuevo,Nombre_Nuevo
                    try {
                        String placa = parts[0].trim();
                        String dpiAnterior = parts[1].trim();
                        String nombreAnterior = parts[2].trim();
                        String fechaStr = parts[3].trim(); // Captura la fecha como String (tu clase Traspaso la espera como String)
                        String dpiNuevo = parts[4].trim();
                        String nombreNuevo = parts[5].trim();

                        // Inferir departamento del nombre del archivo
                        String departamento = file.getName().replace("_traspasos.txt", "");
                        departamento = departamento.replace("_", " ");

                        // Llamar al constructor de Traspaso con los 7 argumentos
                        Traspaso nuevoTraspaso = new Traspaso(placa, dpiAnterior, nombreAnterior, fechaStr, dpiNuevo, nombreNuevo, departamento);
                        todosLosTraspasos.add(nuevoTraspaso);
                        loadedCount++;
                    } catch (Exception e) { // Usar Exception para capturar cualquier error de parseo o ArrayIndexOutOfBounds
                        System.err.println("Error procesando línea en archivo de traspasos " + file.getName() + " en línea " + lineNumber + ": " + e.getMessage() + " (Línea: " + line + ")");
                    }
                } else {
                    System.err.println("Error de formato en archivo de traspasos " + file.getName() + " en línea " + lineNumber + ": Columnas insuficientes. (Línea: " + line + ")");
                }
            }
            TextM.append("Cargados " + loadedCount + " traspasos desde '" + file.getName() + "'.\n");
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de traspasos " + file.getName() + ": " + e.getMessage());
        }
    }


    // --- Método para procesar un directorio y sus subdirectorios ---
    private void procesarDirectorio(File directorio) {
        if (directorio.isDirectory()) {
            File[] files = directorio.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Recursivamente llamar para subdirectorios
                        procesarDirectorio(file);
                    } else if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                        // Identificar tipo de archivo y cargar
                        if (file.getName().toLowerCase().contains("_vehiculos.txt")) {
                            cargarVehiculosDesdeArchivo(file);
                        } else if (file.getName().toLowerCase().contains("_multas.txt")) {
                            cargarMultasDesdeArchivo(file);
                        } else if (file.getName().toLowerCase().contains("_traspasos.txt")) {
                            cargarTraspasosDesdeArchivo(file);
                        }
                    }
                }
            }
        }
    }

    // --- Método para mostrar estadísticas (actualizado) ---
    private void displaySummaryStatistics() {
        if (todosLosVehiculos.isEmpty() && todasLasMultas.isEmpty() && todosLosTraspasos.isEmpty()) {
            TextM.append("\nNo se encontraron datos en los archivos seleccionados.");
            return;
        }

        StringBuilder summaryText = new StringBuilder();
        summaryText.append("\n--- RESUMEN GENERAL DE DATOS CARGADOS ---\n");
        summaryText.append("Total de vehículos: ").append(todosLosVehiculos.size()).append("\n");
        summaryText.append("Total de multas: ").append(todasLasMultas.size()).append("\n");
        summaryText.append("Total de traspasos: ").append(todosLosTraspasos.size()).append("\n\n");

        // 1. Vehículos por Año (usando todosLosVehiculos)
        summaryText.append("--- Vehículos por Año ---\n");
        Map<Integer, Long> vehiculosPorAño = todosLosVehiculos.stream()
                .collect(Collectors.groupingBy(Vehiculo::getAño, Collectors.counting()));
        vehiculosPorAño.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> summaryText.append("Año ")
                        .append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" vehículos\n"));
        summaryText.append("\n");

        // 2. Vehículos por Marca (usando todosLosVehiculos)
        summaryText.append("--- Vehículos por Marca ---\n");
        Map<String, Long> vehiculosPorMarca = todosLosVehiculos.stream()
                .collect(Collectors.groupingBy(Vehiculo::getMarca, Collectors.counting()));
        vehiculosPorMarca.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> summaryText.append("Marca ")
                        .append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" vehículos\n"));
        summaryText.append("\n");

        // 3. Vehículos por Departamento (usando todosLosVehiculos)
        summaryText.append("--- Vehículos por Departamento ---\n");
        Map<String, Long> vehiculosPorDepartamento = todosLosVehiculos.stream()
                .collect(Collectors.groupingBy(Vehiculo::getDepartamento, Collectors.counting()));
        vehiculosPorDepartamento.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> summaryText.append("Departamento ")
                        .append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" vehículos\n"));
        summaryText.append("\n");

        // 4. Multas por Descripción
        summaryText.append("--- Multas por Descripción ---\n");
        Map<String, Long> multasPorDescripcion = todasLasMultas.stream()
                .collect(Collectors.groupingBy(Multa::getDescripcion, Collectors.counting()));
        multasPorDescripcion.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> summaryText.append("Descripción '")
                        .append(entry.getKey()).append("': ")
                        .append(entry.getValue()).append(" multas\n"));
        summaryText.append("\n");
        
        // 5. Total de multas por vehículo (Top 5)
        summaryText.append("--- Top 5 Vehículos con Más Multas ---\n");
        todasLasMultas.stream()
                .collect(Collectors.groupingBy(Multa::getPlaca, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> summaryText.append("Placa ")
                        .append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" multas\n"));
        summaryText.append("\n");

        // 6. Total de traspasos por vehículo (Top 5)
        summaryText.append("--- Top 5 Vehículos con Más Traspasos ---\n");
        todosLosTraspasos.stream()
                .collect(Collectors.groupingBy(Traspaso::getPlaca, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> summaryText.append("Placa ")
                        .append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" traspasos\n"));
        summaryText.append("\n");

        TextM.setText(summaryText.toString());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SeleccionarCarpeta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextM = new javax.swing.JTextArea();
        Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 0, 255));

        SeleccionarCarpeta.setText("Carga");
        SeleccionarCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeleccionarCarpetaActionPerformed(evt);
            }
        });

        TextM.setColumns(20);
        TextM.setRows(5);
        jScrollPane1.setViewportView(TextM);

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(SeleccionarCarpeta)
                .addGap(76, 76, 76)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salir)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Salir)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SeleccionarCarpeta))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SeleccionarCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionarCarpetaActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(".")); 
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            
            todosLosVehiculos.clear();
            todasLasMultas.clear();
            todosLosTraspasos.clear();

            // Reiniciar los IDs estáticos
            Multa.resetNextId(); // Asegúrate de que esto se llame para Multa
            Traspaso.resetearSiguienteId(); // ¡Nuevo! Llama al método de tu clase Traspaso
            
            TextM.setText("Cargando y procesando archivos desde la carpeta: " + selectedDirectory.getName() + "...\n");
            
            procesarDirectorio(selectedDirectory);
            
            displaySummaryStatistics();
            JOptionPane.showMessageDialog(this, "Carga de datos completada.", "Proceso Finalizado", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_SeleccionarCarpetaActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        dispose();
    }//GEN-LAST:event_SalirActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Salir;
    private javax.swing.JButton SeleccionarCarpeta;
    private javax.swing.JTextArea TextM;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
