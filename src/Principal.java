
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class Principal extends javax.swing.JFrame {
private File archivoSeleccionado;
private ABB arbolMultas = new ABB();

private void mostrarEnTabla(List<Multa> multas) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("PLACA");
    modelo.addColumn("FECHA");
    modelo.addColumn("DESCRIPCION");
    modelo.addColumn("MONTO");

    for (Multa m : multas) {
        modelo.addRow(new Object[]{
            m.getId(), m.getPlaca(), m.getFecha(), m.getDescripcion(), m.getMonto()
        });
    }

    Tabla.setModel(modelo);
}
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
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
        ABB = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        Placa = new javax.swing.JTextField();
        BPlaca = new javax.swing.JButton();

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

        ABB.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        ABB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/arbol-de-decision.png"))); // NOI18N
        ABB.setText("ABB");
        ABB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ABBActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Admin");

        Tabla.setBackground(new java.awt.Color(255, 255, 102));
        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PLACA", "FECHA", "DESCRIPCION", "MONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla);

        BPlaca.setText("Busca * Placa");
        BPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BPlacaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PrinLayout = new javax.swing.GroupLayout(Prin);
        Prin.setLayout(PrinLayout);
        PrinLayout.setHorizontalGroup(
            PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrinLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrinLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(111, 111, 111))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrinLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap())))
            .addGroup(PrinLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addComponent(Placa, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BPlaca))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 854, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PrinLayout.createSequentialGroup()
                        .addComponent(Carga)
                        .addGap(44, 44, 44)
                        .addComponent(ABB)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        PrinLayout.setVerticalGroup(
            PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Carga)
                    .addComponent(ABB))
                .addGap(18, 18, 18)
                .addGroup(PrinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BPlaca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
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
    fileChooser.setDialogTitle("Selecciona el archivo de vehículos");

    int resultado = fileChooser.showOpenDialog(this);
    if (resultado == JFileChooser.APPROVE_OPTION) {
        archivoSeleccionado = fileChooser.getSelectedFile();
        
        // Aquí puedes procesar el archivo (leerlo, cargar vehículos, etc.)
        System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
        

    }
    }//GEN-LAST:event_CargaActionPerformed

    private void ABBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ABBActionPerformed
    if (archivoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Primero selecciona un archivo.");
        return;
    }

    arbolMultas = new ABB(); // Reiniciar árbol

    try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
        String linea;

        // Leer y descartar la primera línea (encabezado)
        br.readLine();

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 4) {
                String placa = partes[0];
                String fecha = partes[1];
                String descripcion = partes[2];
                double monto = Double.parseDouble(partes[3]);
                Multa multa = new Multa(placa, fecha, descripcion, monto);
                arbolMultas.insertar(multa);
            }
        }

        mostrarEnTabla(arbolMultas.inorden());

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al leer el archivo.");
    }
    }//GEN-LAST:event_ABBActionPerformed

    private void BPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BPlacaActionPerformed
    String placaBuscada = Placa.getText().trim();
    if (placaBuscada.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese una placa para buscar.");
        return;
    }

    List<Multa> multasEncontradas = arbolMultas.buscarPorPlaca(placaBuscada);

    if (multasEncontradas.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron multas para la placa: " + placaBuscada);
    } else {
        mostrarEnTabla(multasEncontradas);
    }
    }//GEN-LAST:event_BPlacaActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ABB;
    private javax.swing.JButton BPlaca;
    private javax.swing.JButton Carga;
    private javax.swing.JTextField Placa;
    private javax.swing.JPanel Prin;
    private javax.swing.JTable Tabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
