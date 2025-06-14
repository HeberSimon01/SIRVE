
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class Traspasos extends javax.swing.JFrame {
    private JButton botonCargaT;
    private JTable tablaTraspasos; // Renombrado de Tabla3
    private DefaultTableModel modeloTabla;
    public ListaTraspasos<Traspaso> listaTraspasos;
     private JTextArea textM;
private CryptoUtil cryptoUtil;

    public JTextArea getTextM() {
        return TextM;
    }
 
      
   public Traspasos() {
        initComponents(); // Inicializa los componentes
        listaTraspasos = new ListaTraspasos<>();
        modeloTabla = new DefaultTableModel(); // Inicializa el modelo de la tabla
        modeloTabla.setColumnIdentifiers(new String[] {
            "ID", "PLACA", "DPI ant", "NOMBRE ant", "FECHA", "DPI", "NOMBRE", "DEPARTAMENTO", "ENCRIPTADO" // Nueva columna
        });
        Tabla3.setModel(modeloTabla); // Asigna el modelo a la tabla

        cryptoUtil = new CryptoUtil(); // Inicializa la utilidad de cifrado

    }
        public void agregarNuevoTraspaso(Traspaso nuevo) {
        listaTraspasos.agregar(nuevo); // Agregar el nuevo traspaso a la lista
        poblarTabla(); // Actualizar la tabla
    }
    private void cargarTraspasosDesdeCarpeta() {
        JFileChooser selectorArchivos = new JFileChooser();
        selectorArchivos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectorArchivos.setDialogTitle("Selecciona la carpeta raíz para buscar archivos de traspasos");

        int seleccionUsuario = selectorArchivos.showOpenDialog(this);

        if (seleccionUsuario == JFileChooser.APPROVE_OPTION) {
            File directorioRaiz = selectorArchivos.getSelectedFile();

            // Limpiar datos existentes en la tabla y la lista antes de una nueva carga
            modeloTabla.setRowCount(0);
            Traspaso.resetearSiguienteId(); // Reiniciar el contador de ID
            listaTraspasos = new ListaTraspasos<>();

            // Iniciar la búsqueda recursiva de archivos de traspasos
            buscarYLeerArchivosTraspasos(directorioRaiz);

            if (listaTraspasos.obtenerTamano() > 0) {
                poblarTabla();
                JOptionPane.showMessageDialog(this, "Archivos de traspasos cargados correctamente.", "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron archivos '_traspasos.txt' en la carpeta seleccionada ni en sus subcarpetas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
        // Nuevo método recursivo para buscar archivos
    private void buscarYLeerArchivosTraspasos(File directorio) {
        File[] archivosYDirectorios = directorio.listFiles();

        if (archivosYDirectorios != null) {
            for (File item : archivosYDirectorios) {
                if (item.isDirectory()) {
                    // Si es un directorio, llamar recursivamente
                    buscarYLeerArchivosTraspasos(item);
                } else if (item.isFile() && item.getName().toLowerCase().endsWith("_traspasos.txt")) {
                    // Si es un archivo y es de traspasos, leerlo
                    leerArchivoTraspaso(item);
                }
            }
        }
    }

    private void leerArchivoTraspaso(File archivo) {
        String departamento = obtenerDepartamentoDesdeArchivo(archivo.getName());
        if (departamento == null) {


            departamento = archivo.getParentFile().getName().replace("_", " ");
            if (departamento.isEmpty() || departamento.contains(".txt")) { // Si el nombre de la carpeta no es válido
                 System.err.println("No se pudo determinar un departamento válido para el archivo: " + archivo.getName());
                 return; // Omitir el archivo si el departamento no se puede obtener
            }
        }


        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length == 6) {
                    String placa = partes[0];
                    String dpiAnterior = partes[1];
                    String nombreAnterior = partes[2];
                    String fecha = partes[3];
                    String dpiNuevo = partes[4];
                    String nombreNuevo = partes[5];

                    Traspaso traspaso = new Traspaso(placa, dpiAnterior, nombreAnterior, fecha, dpiNuevo, nombreNuevo, departamento);
                    listaTraspasos.agregar(traspaso);
                } else {
                    System.err.println("Línea mal formateada en " + archivo.getName() + ": " + linea);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + archivo.getName() + "\n" + ex.getMessage(), "Error de Lectura", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private String obtenerDepartamentoDesdeArchivo(String nombreArchivo) {
        // Asume el formato "NombreDepartamento_traspasos.txt"
        int indiceFin = nombreArchivo.toLowerCase().indexOf("_traspasos.txt");
        if (indiceFin != -1) {
            String parteDepartamento = nombreArchivo.substring(0, indiceFin);
            return parteDepartamento.replace("_", " ");
        }
        return null; // Retorna null si no puede extraer el departamento del nombre del archivo
    }

public void poblarTabla() {
    modeloTabla.setRowCount(0); // Limpia todas las filas actuales
    for (Traspaso traspaso : listaTraspasos) {
        Object[] fila = {
            traspaso.getId(),
            traspaso.getPlaca(),
            traspaso.getDpiAnterior(),
            traspaso.getNombreAnterior(),
            traspaso.getFecha(),
            traspaso.getDpiNuevo(),
            traspaso.getNombreNuevo(),
            traspaso.getDepartamento()
        };
        modeloTabla.addRow(fila);
    }
}

private void buscarPorPlaca() {
    String placaBusqueda = Bus.getText().trim();
    if (placaBusqueda.isEmpty()) {
        // Si el campo está vacío, mostrar todas las entradas
        poblarTabla();
        TextM.setText("Se mostraron todos los traspasos."); // Mensaje informativo
        return;
    }

    long startTime = System.currentTimeMillis(); // Inicio medición

    modeloTabla.setRowCount(0); // Limpiar tabla

    // Buscar y mostrar coincidencias
    for (Traspaso traspaso : listaTraspasos) {
        if (traspaso.getPlaca().equalsIgnoreCase(placaBusqueda)) {
            Object[] datosFila = {
                traspaso.getId(),
                traspaso.getPlaca(),
                traspaso.getDpiAnterior(),
                traspaso.getNombreAnterior(),
                traspaso.getFecha(),
                traspaso.getDpiNuevo(),
                traspaso.getNombreNuevo(),
                traspaso.getDepartamento()
            };
            modeloTabla.addRow(datosFila);
        }
    }

    long endTime = System.currentTimeMillis(); // Fin medición
    double duracionSegundos = (endTime - startTime) / 1000.0;

    if (modeloTabla.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No se encontraron traspasos para la placa: " + placaBusqueda,
            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        TextM.setText("Búsqueda terminada en " + duracionSegundos + " segundos. No se hallaron resultados.");
    } else {
        TextM.setText("Búsqueda terminada en " + duracionSegundos + " segundos. Se encontraron " + modeloTabla.getRowCount() + " resultados.");
    }
}
private void eliminarPorId() {
    String idTexto = Eliminar.getText().trim();
    if (idTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    try {
        int id = Integer.parseInt(idTexto);
        // Llama al método eliminarPorId con el JTextArea TextM
        boolean eliminado = listaTraspasos.eliminarPorId(id, TextM); // Pasa TextM como argumento
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            poblarTabla(); // Actualiza la tabla después de eliminar
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un registro con el ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
public void mostrarMensaje(String mensaje) {
    TextM.setText(mensaje);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Bus = new javax.swing.JTextField();
        Buscador = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextM = new javax.swing.JTextArea();
        Eliminar = new javax.swing.JTextField();
        Eliminador = new javax.swing.JButton();
        Ingreso = new javax.swing.JButton();
        Mod = new javax.swing.JTextField();
        Modificar = new javax.swing.JButton();
        CargaT = new javax.swing.JButton();
        Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 51, 255));

        Tabla3.setBackground(new java.awt.Color(255, 255, 0));
        Tabla3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PLACA", "DPI ant", "NOMBRE ant", "FECHA", "DPI", "NOMBRE", "DEPARTAMENTO"
            }
        ));
        jScrollPane1.setViewportView(Tabla3);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/check.png"))); // NOI18N
        jLabel1.setText("Gestor de Traspasos SIRVE");

        Buscador.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Buscador.setText("Busacar");
        Buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscadorActionPerformed(evt);
            }
        });

        TextM.setColumns(20);
        TextM.setRows(5);
        jScrollPane2.setViewportView(TextM);

        Eliminador.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Eliminador.setText("Elimiar");
        Eliminador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminadorActionPerformed(evt);
            }
        });

        Ingreso.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Ingreso.setText("Ingresar");
        Ingreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresoActionPerformed(evt);
            }
        });

        Modificar.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        CargaT.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        CargaT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/carpeta.png"))); // NOI18N
        CargaT.setText("Carga");
        CargaT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargaTActionPerformed(evt);
            }
        });

        Salir.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
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
                .addGap(45, 45, 45)
                .addComponent(Bus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Buscador)
                .addGap(51, 51, 51)
                .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Eliminador)
                .addGap(58, 58, 58)
                .addComponent(Ingreso)
                .addGap(50, 50, 50)
                .addComponent(Mod, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Modificar)
                .addContainerGap(310, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CargaT)
                .addGap(141, 141, 141)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Salir)
                .addGap(29, 29, 29))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(CargaT)
                                .addGap(7, 7, 7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Salir)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Bus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscador)
                    .addComponent(Modificar)
                    .addComponent(Mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ingreso)
                    .addComponent(Eliminador)
                    .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
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

    private void CargaTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargaTActionPerformed

    long startTime = System.currentTimeMillis();
    // Llamar a la función que carga traspasos desde carpeta
    cargarTraspasosDesdeCarpeta();
    long endTime = System.currentTimeMillis();
    double durationInSeconds = (endTime - startTime) / 1000.0;
    // Mostrar duración en el JTextArea TextM
    TextM.setText("Tiempo de carga: " + durationInSeconds + " segundos");
    }//GEN-LAST:event_CargaTActionPerformed

    private void BuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscadorActionPerformed
buscarPorPlaca();
    }//GEN-LAST:event_BuscadorActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        dispose();
    }//GEN-LAST:event_SalirActionPerformed

    private void EliminadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminadorActionPerformed
eliminarPorId();
    }//GEN-LAST:event_EliminadorActionPerformed

    private void IngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresoActionPerformed
    IngresoT ingresarFrame = new IngresoT(this); // Pasar referencia al JFrame principal
    ingresarFrame.setVisible(true);
    }//GEN-LAST:event_IngresoActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        String idText = Mod.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para modificar.", "ID Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(idText);
            Traspaso traspaso = listaTraspasos.buscarPorId(id);
            if (traspaso == null) {
                JOptionPane.showMessageDialog(this, "No se encontró un traspaso con el ID: " + id, "ID No Encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            IngresoT ingresarFrame = new IngresoT(this, traspaso);
            ingresarFrame.setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID válido.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ModificarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Bus;
    private javax.swing.JButton Buscador;
    private javax.swing.JButton CargaT;
    private javax.swing.JButton Eliminador;
    private javax.swing.JTextField Eliminar;
    private javax.swing.JButton Ingreso;
    private javax.swing.JTextField Mod;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JTable Tabla3;
    private javax.swing.JTextArea TextM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
