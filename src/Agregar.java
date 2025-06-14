/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane; // Asegúrate de tener esto importado

public class Agregar extends javax.swing.JFrame {

    private Principal ventanaPrincipal; // Referencia a la ventana principal

    public Agregar(Principal principal) { // Constructor que recibe la instancia de Principal
        initComponents();
        this.ventanaPrincipal = principal;

        // Asegúrate de que el botón OK tenga este ActionListener
        OK.addActionListener(new ActionListener() { // 'OK' es el nombre de la variable de tu botón
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarVehiculoDesdeCampos();
            }
        });
        
        // Opcional: Centrar la ventana al abrirse
        this.setLocationRelativeTo(null); 
    }

    // --- AÑADE ESTOS MÉTODOS COMPLETOS ---

    public void agregarVehiculoDesdeCampos() {
        try {
            // 1. Obtener los datos de los JTextField (asegúrate que los nombres de las variables sean EXACTOS)
            String placa = Placa.getText().trim();
            String dpi = DPI.getText().trim();
            String nombre = Nombre.getText().trim();
            String marca = Marca.getText().trim();
            String modelo = Modelo.getText().trim();
            
            // Convertir a int. Usa Integer.parseInt.
            int año = Integer.parseInt(Año.getText().trim());
            int multas = Integer.parseInt(Multas.getText().trim());
            int traspasos = Integer.parseInt(Traspasos.getText().trim());

            // Validaciones básicas (puedes añadir más según tus necesidades)
            if (placa.isEmpty() || dpi.isEmpty() || nombre.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos (Placa, DPI, Nombre, Marca, Modelo) son obligatorios.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (placa.length() != 8) { // Ejemplo de validación de formato de placa
                JOptionPane.showMessageDialog(this, "La placa debe tener 8 caracteres (ej. AAA-1234).", "Error de formato", JOptionPane.WARNING_MESSAGE);
                // Puedes ser más estricto con expresiones regulares si lo deseas
            }
            // Puedes agregar validaciones para el año, multas, traspasos (ej. no negativos)
            if (año < 1900 || año > 2026) { // Ejemplo de rango de año razonable
                JOptionPane.showMessageDialog(this, "El año debe ser un valor entre 1900 y 2026.", "Año inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (multas < 0 || traspasos < 0) {
                JOptionPane.showMessageDialog(this, "Multas y traspasos no pueden ser valores negativos.", "Valores inválidos", JOptionPane.WARNING_MESSAGE);
                return;
            }


            // 2. Crear un objeto Vehiculo
            // Asegúrate que tu constructor de Vehiculo acepte estos 8 parámetros en este orden
            Vehiculo nuevoVehiculo = new Vehiculo(placa, dpi, nombre, marca, modelo, año, multas, traspasos);
            // El ID y Departamento se asignarán en Principal.agregarVehiculoDesdeVentana

             // 3. Llamar al método en la ventana principal para que agregue el vehículo al ABB
            // y actualice la tabla y guarde en archivo.
            String departamentoActual = ventanaPrincipal.getDepartamentoSeleccionado(); // Obtener el departamento de Principal
            
            ventanaPrincipal.agregarVehiculoDesdeVentana(nuevoVehiculo, departamentoActual); // Llamada al método en Principal

            JOptionPane.showMessageDialog(this, "Vehículo agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Opcional: Limpiar los campos después de agregar
            limpiarCampos();
            
            // Opcional: Cerrar esta ventana después de agregar
            // this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para Año, Multas y Traspasos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Un error inesperado
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al agregar el vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime la traza de la pila para depuración
        }
    }
    
    public void limpiarCampos() {
        Placa.setText("");
        DPI.setText("");
        Nombre.setText("");
        Marca.setText("");
        Modelo.setText("");
        Año.setText("");
        Multas.setText("");
        Traspasos.setText("");
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panelito = new javax.swing.JPanel();
        P = new javax.swing.JLabel();
        D = new javax.swing.JLabel();
        N = new javax.swing.JLabel();
        MA = new javax.swing.JLabel();
        MO = new javax.swing.JLabel();
        A = new javax.swing.JLabel();
        M = new javax.swing.JLabel();
        TRA = new javax.swing.JLabel();
        OK = new javax.swing.JButton();
        DPI = new javax.swing.JTextField();
        Placa = new javax.swing.JTextField();
        Año = new javax.swing.JTextField();
        Nombre = new javax.swing.JTextField();
        Marca = new javax.swing.JTextField();
        Modelo = new javax.swing.JTextField();
        Multas = new javax.swing.JTextField();
        Traspasos = new javax.swing.JTextField();
        Salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panelito.setBackground(new java.awt.Color(0, 51, 204));

        P.setText("Placa");

        D.setText("DPI");

        N.setText("Nombre");

        MA.setText("Marca");

        MO.setText("modelo");

        A.setText("año");

        M.setText("Multas");

        TRA.setText("Traspasos");

        OK.setText("Agregar");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });

        Placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlacaActionPerformed(evt);
            }
        });

        Año.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AñoActionPerformed(evt);
            }
        });

        Nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreActionPerformed(evt);
            }
        });

        Traspasos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TraspasosActionPerformed(evt);
            }
        });

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelitoLayout = new javax.swing.GroupLayout(Panelito);
        Panelito.setLayout(PanelitoLayout);
        PanelitoLayout.setHorizontalGroup(
            PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelitoLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(N)
                    .addComponent(P)
                    .addComponent(D)
                    .addComponent(MA)
                    .addGroup(PanelitoLayout.createSequentialGroup()
                        .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(A)
                            .addComponent(M)
                            .addComponent(MO))
                        .addGap(24, 24, 24)
                        .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Modelo, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(Nombre)
                            .addComponent(Placa)
                            .addComponent(DPI)
                            .addComponent(Marca)
                            .addComponent(Año)
                            .addComponent(Multas)))
                    .addGroup(PanelitoLayout.createSequentialGroup()
                        .addComponent(TRA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Traspasos)))
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelitoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelitoLayout.createSequentialGroup()
                        .addComponent(Salir)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelitoLayout.createSequentialGroup()
                        .addComponent(OK)
                        .addGap(167, 167, 167))))
        );
        PanelitoLayout.setVerticalGroup(
            PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelitoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Salir)
                .addGap(24, 24, 24)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(P)
                    .addComponent(Placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(D)
                    .addComponent(DPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(N)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MA)
                    .addComponent(Marca, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MO))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Año, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(A))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(M)
                    .addComponent(Multas, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TRA)
                    .addComponent(Traspasos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(OK)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panelito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKActionPerformed
agregarVehiculoDesdeCampos();
    }//GEN-LAST:event_OKActionPerformed

    private void PlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlacaActionPerformed

    }//GEN-LAST:event_PlacaActionPerformed

    private void AñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AñoActionPerformed

    private void TraspasosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TraspasosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TraspasosActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
dispose();
    }//GEN-LAST:event_SalirActionPerformed

    private void NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel A;
    private javax.swing.JTextField Año;
    private javax.swing.JLabel D;
    private javax.swing.JTextField DPI;
    private javax.swing.JLabel M;
    private javax.swing.JLabel MA;
    private javax.swing.JLabel MO;
    private javax.swing.JTextField Marca;
    private javax.swing.JTextField Modelo;
    private javax.swing.JTextField Multas;
    private javax.swing.JLabel N;
    private javax.swing.JTextField Nombre;
    private javax.swing.JButton OK;
    private javax.swing.JLabel P;
    private javax.swing.JPanel Panelito;
    private javax.swing.JTextField Placa;
    private javax.swing.JButton Salir;
    private javax.swing.JLabel TRA;
    private javax.swing.JTextField Traspasos;
    // End of variables declaration//GEN-END:variables
}
