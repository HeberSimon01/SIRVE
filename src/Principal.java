
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
import javax.swing.JComboBox;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */

public class Principal extends javax.swing.JFrame {

      public Principal() {
        initComponents();
    inicializarTabla(); 
    // Configurar JComboBox
    Depto.addItem("Todos"); // Opción para ver todos los departamentos
    Depto.setSelectedItem("Todos"); // Seleccionar por defecto
    
    }

private ABB arbolVehiculos = new ABB(); // Cambiado a ABB
private DefaultTableModel tablaModelo;
private Set<String> departamentosCargados = new HashSet<>(); // Para el JComboBox

    
private void inicializarTabla() {
    // Columnas de la tabla (SIN Departamento)
    String[] columnas = {"ID", "Placa", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos"};
    tablaModelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer que las celdas no sean editables
        }
    };
    Tabla.setModel(tablaModelo); // <--- CAMBIO AQUÍ: Usar 'Tabla'
}
// Dentro de MiAplicacionGUI
private void cargarDatosDesdeCarpeta(File carpetaRaiz) {
    File[] subCarpetas = carpetaRaiz.listFiles(File::isDirectory);

    if (subCarpetas != null) {
        for (File subCarpeta : subCarpetas) {
            String nombreDepartamento = subCarpeta.getName();
            departamentosCargados.add(nombreDepartamento); // Almacenar el nombre del departamento
            
            File archivoVehiculos = new File(subCarpeta, nombreDepartamento + "_vehiculos.txt");
            File archivoMultas = new File(subCarpeta, nombreDepartamento + "_multas.txt");
            File archivoTraspasos = new File(subCarpeta, nombreDepartamento + "_traspasos.txt");

            if (archivoVehiculos.exists() && archivoMultas.exists() && archivoTraspasos.exists()) {
                cargarVehiculos(archivoVehiculos, nombreDepartamento);
            } else {
                jTextAreaMensajes.append("Advertencia: No se encontraron todos los archivos para el departamento " + nombreDepartamento + "\n");
            }
        }
    }
}
private void cargarVehiculos(File archivoVehiculos, String departamento) {
    try (BufferedReader br = new BufferedReader(new FileReader(archivoVehiculos))) {
        String linea;
        br.readLine(); // Saltar la primera línea (encabezados)
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 8) { // Asegúrate de que tenga 8 columnas en el TXT original
                try {
                    String placa = partes[0];
                    String dpi = partes[1];
                    String nombre = partes[2];
                    String marca = partes[3];
                    String modelo = partes[4];
                    int año = Integer.parseInt(partes[5]);
                    int multas = Integer.parseInt(partes[6]);
                    int traspasos = Integer.parseInt(partes[7]);

                    Vehiculo vehiculo = new Vehiculo(placa, dpi, nombre, marca, modelo, año, multas, traspasos);
                    vehiculo.setDepartamento(departamento); // Asignar el departamento
                    arbolVehiculos.insertar(vehiculo); // Insertar en el ABB
                } catch (NumberFormatException e) {
                    jTextAreaMensajes.append("Error de formato numérico en línea: " + linea + " de " + archivoVehiculos.getName() + "\n");
                }
            } else {
                jTextAreaMensajes.append("Error: Línea mal formateada en " + archivoVehiculos.getName() + ": " + linea + "\n");
            }
        }
    } catch (IOException e) {
        jTextAreaMensajes.append("Error al leer el archivo " + archivoVehiculos.getName() + ": " + e.getMessage() + "\n");
    }
}
private void mostrarDatosEnTabla(List<Vehiculo> listaVehiculos) {
    tablaModelo.setRowCount(0); // Limpiar filas existentes

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
            vehiculo.getTraspasos()
            // Se elimina el departamento de la fila visible en la tabla
        };
        tablaModelo.addRow(fila);
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
        Buscar = new javax.swing.JTextField();
        Buscador = new javax.swing.JButton();

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
                "ID", "PLACA", "DPI", "Nombre", "Marca", "Modelo", "Año", "Multas", "Traspasos"
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

        Buscar.setText("Placa a buscar");

        Buscador.setText("b");

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
                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Buscador))))
                .addGap(545, 545, 545))
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
            arbolVehiculos.reset();
            departamentosCargados.clear();
            Depto.removeAllItems(); // Cambiado de jComboBoxDepartamentos a Depto
            Depto.addItem("Todos"); // Cambiado de jComboBoxDepartamentos a Depto
            
            cargarDatosDesdeCarpeta(carpetaRaiz);
            
            // Llenar JComboBox con los departamentos únicos cargados
            for (String dep : departamentosCargados) {
                Depto.addItem(dep); // Cambiado de jComboBoxDepartamentos a Depto
            }
            jTextAreaMensajes.setText("Archivos cargados. Total de vehículos: " + arbolVehiculos.obtenerVehiculosOrdenados().size());
            
            mostrarDatosEnTabla(arbolVehiculos.obtenerVehiculosOrdenados());

        } else {
            jTextAreaMensajes.setText("Por favor, selecciona una carpeta válida.");
        }
    } else {
        jTextAreaMensajes.setText("Carga de archivos cancelada.");
    }

    }//GEN-LAST:event_CargaActionPerformed

    private void ABBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABBActionPerformed
    if (arbolVehiculos.obtenerVehiculosOrdenados().isEmpty()) {
        jTextAreaMensajes.setText("No hay datos cargados para mostrar en el ABB.");
        return;
    }
    List<Vehiculo> vehiculosOrdenados = arbolVehiculos.obtenerVehiculosOrdenados();
    mostrarDatosEnTabla(vehiculosOrdenados);
    jTextAreaMensajes.setText("Datos cargados del ABB y mostrados en la tabla (ordenados por placa).");
    Depto.setSelectedItem("Todos"); // Cambiado de jComboBoxDepartamentos a Depto
    }//GEN-LAST:event_ABBActionPerformed
private void jComboBoxDepartamentosItemStateChanged(java.awt.event.ItemEvent evt) {
    if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        String departamentoSeleccionado = (String) Depto.getSelectedItem();
        
        if (departamentoSeleccionado != null && !departamentoSeleccionado.isEmpty()) {
            if (departamentoSeleccionado.equals("Todos")) {
                mostrarDatosEnTabla(arbolVehiculos.obtenerVehiculosOrdenados());
            } else {
                List<Vehiculo> vehiculosFiltrados = arbolVehiculos.obtenerVehiculosPorDepartamento(departamentoSeleccionado);
                mostrarDatosEnTabla(vehiculosFiltrados);
            }
            jTextAreaMensajes.setText("Filtrando por departamento: " + departamentoSeleccionado);
        }
    }
}
    private void DeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeptoActionPerformed

    }//GEN-LAST:event_DeptoActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ABB;
    private javax.swing.JButton Buscador;
    private javax.swing.JTextField Buscar;
    private javax.swing.JButton Carga;
    private javax.swing.JComboBox<String> Depto;
    private javax.swing.JPanel Prin;
    private javax.swing.JTable Tabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaMensajes;
    // End of variables declaration//GEN-END:variables
}
