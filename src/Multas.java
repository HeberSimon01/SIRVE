
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Multas extends javax.swing.JFrame{

    private DefaultTableModel tableModel;
    public ListaDoblementeEnlazadaMultas listaMultas;
   public JTextArea getTextM() {
       return TextM;
   }
   
    public Multas() {
        initComponents(); // Inicializa los componentes primero
        String[] columnNames = {"ID", "Placa", "Fecha", "Descripción", "Monto", "Departamento"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };
        jTable2.setModel(tableModel); // Establece el modelo de la tabla
        listaMultas = new ListaDoblementeEnlazadaMultas();
    }
private void cargarMultasDesdeCarpeta() {
    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setDialogTitle("Seleccione la carpeta general de departamentos");
    int userSelection = fileChooser.showOpenDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File selectedDirectory = fileChooser.getSelectedFile();
        listaMultas.limpiar();
        tableModel.setRowCount(0); 
        File[] departmentFolders = selectedDirectory.listFiles(File::isDirectory);
        if (departmentFolders != null) {
            for (File folder : departmentFolders) {
                String departmentName = folder.getName();
                File multasFile = new File(folder, departmentName + "_multas.txt");
                if (multasFile.exists() && multasFile.isFile()) {
                    System.out.println("Cargando multas de: " + multasFile.getAbsolutePath());
                    // Asegúrate de pasar el JTextArea al método cargarMultasDesdeArchivo
                    cargarMultasDesdeArchivo(multasFile, departmentName, TextM); // Pasar el JTextArea
                } else {
                    System.out.println("Archivo de multas no encontrado en: " + folder.getAbsolutePath());
                }
            }
            actualizarTabla();
            if (listaMultas.obtenerDatosParaTabla().length > 0) {
                JOptionPane.showMessageDialog(this, "Multas cargadas exitosamente.", "Carga Completa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron archivos de multas válidos.", "Carga Fallida", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron subcarpetas de departamentos en el directorio seleccionado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}
private void cargarMultasDesdeArchivo(File archivo, String departamento, JTextArea textM) {
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String line;
        br.readLine(); // Saltar la primera línea (encabezados)
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String placa = parts[0].trim();
                String fecha = parts[1].trim();
                String descripcion = parts[2].trim();
                double monto = Double.parseDouble(parts[3].trim());
                Multa multa = new Multa(placa, fecha, descripcion, monto, departamento);
                
                // Pasar el JTextArea al método agregarMulta
                listaMultas.agregarMulta(multa, textM);
            } else {
                System.err.println("Línea mal formateada en " + archivo.getName() + ": " + line);
            }
        }
    } catch (IOException | NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error al leer el archivo " + archivo.getName() + ": " + e.getMessage(), "Error de Lectura", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

         public void actualizarTabla() {
        tableModel.setRowCount(0);
        Object[][] datos = listaMultas.obtenerDatosParaTabla();
        for (Object[] row : datos) {
            tableModel.addRow(row);
        }
    }
public void actualizarTabla(Object[][] datos) {
    tableModel.setRowCount(0); // Limpia la tabla
    for (Object[] fila : datos) {
        tableModel.addRow(fila);
    }
}
public Multa buscarMultaPorId(int id) {
return listaMultas.buscarMultaPorId(id);
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
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Buscar = new javax.swing.JTextField();
        Buscador = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextM = new javax.swing.JTextArea();
        Eliminar = new javax.swing.JTextField();
        Eliminador = new javax.swing.JButton();
        Crear = new javax.swing.JButton();
        Modificar = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        CargaM = new javax.swing.JButton();
        Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 51, 255));

        jTable2.setBackground(new java.awt.Color(255, 255, 0));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PLACA", "FECHA", "DESCRIPCION", "MONTO", "DEPARTAMENTO"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/check.png"))); // NOI18N
        jLabel1.setText("Gestor de Multas SIRVE");

        Buscador.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Buscador.setText("Buscar");
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

        Crear.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Crear.setText("Ingresar");
        Crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        CargaM.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        CargaM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/carpeta.png"))); // NOI18N
        CargaM.setText("Carga");
        CargaM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargaMActionPerformed(evt);
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
                .addGap(16, 16, 16)
                .addComponent(CargaM)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Buscador)
                        .addGap(29, 29, 29)
                        .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Eliminador)
                        .addGap(34, 34, 34)
                        .addComponent(Crear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Salir)
                        .addGap(16, 16, 16))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(Salir)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(CargaM)))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Eliminador)
                        .addComponent(Crear)
                        .addComponent(Buscador)
                        .addComponent(Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnModificar)))
                .addContainerGap(153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CargaMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargaMActionPerformed
cargarMultasDesdeCarpeta();
    }//GEN-LAST:event_CargaMActionPerformed

    private void BuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscadorActionPerformed
    String placaBuscada = Buscar.getText().trim();
    if (placaBuscada.isEmpty()) {
        // Mostrar todas las multas
        Object[][] todosLosDatos = listaMultas.obtenerDatosParaTabla();
        actualizarTabla(todosLosDatos);
        return;
    }
    
    Object[][] resultados = listaMultas.buscarPorPlaca(placaBuscada, TextM);
    
    if (resultados.length == 0) {
        JOptionPane.showMessageDialog(this, "No se encontraron multas para la placa: " + placaBuscada, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Actualizar la tabla con los resultados encontrados
    actualizarTabla(resultados);
    }//GEN-LAST:event_BuscadorActionPerformed

    private void EliminadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminadorActionPerformed
    String idText = Eliminar.getText().trim();
    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para eliminar.", "ID Vacío", JOptionPane.WARNING_MESSAGE);
        return;
    }
    try {
        int id = Integer.parseInt(idText);
        // Llamar al método de eliminación y pasar el JTextArea
        boolean eliminado = listaMultas.eliminarPorId(id, TextM);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Multa eliminada con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla(); // Actualiza la tabla después de eliminar
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró una multa con el ID: " + id, "ID No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_EliminadorActionPerformed

    private void CrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearActionPerformed
Ingresar ingresarFrame = new Ingresar(Multas.this); // Pasar referencia al JFrame principal
        ingresarFrame.setVisible(true);
    }//GEN-LAST:event_CrearActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
    String idText = Modificar.getText().trim();
               if (idText.isEmpty()) {
                   JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para modificar.", "ID Vacío", JOptionPane.WARNING_MESSAGE);
                   return;
               }
               try {
                   int id = Integer.parseInt(idText);
                   Multa multa = listaMultas.buscarMultaPorId(id); // Llama al método de la lista
                   if (multa == null) {
                       JOptionPane.showMessageDialog(this, "No se encontró una multa con el ID: " + id, "ID No Encontrado", JOptionPane.WARNING_MESSAGE);
                       return;
                   }
                   // Abrir JFrame Ingresar en modo edición
                   Ingresar ingresarFrame = new Ingresar(this, multa);
                   ingresarFrame.setVisible(true);
               } catch (NumberFormatException e) {
                   JOptionPane.showMessageDialog(this, "Ingrese un ID válido.", "ID Inválido", JOptionPane.ERROR_MESSAGE);
               }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        dispose();
    }//GEN-LAST:event_SalirActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Buscador;
    private javax.swing.JTextField Buscar;
    private javax.swing.JButton CargaM;
    private javax.swing.JButton Crear;
    private javax.swing.JButton Eliminador;
    private javax.swing.JTextField Eliminar;
    private javax.swing.JTextField Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JTextArea TextM;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
