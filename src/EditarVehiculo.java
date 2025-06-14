// EditarVehiculo.java (Este será tu nuevo archivo, basado en Agregar.java)

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
// Asegúrate de importar Vehiculo si está en un paquete diferente
// import tu.paquete.Vehiculo;

public class EditarVehiculo extends javax.swing.JFrame {

    private Principal ventanaPrincipal;
    private Vehiculo vehiculoAEditar; // Variable para guardar el vehículo original



    // Constructor que recibe la instancia de Principal y el Vehiculo a editar
    public EditarVehiculo(Principal principal, Vehiculo vehiculo) {
        this.ventanaPrincipal = principal;
        this.vehiculoAEditar = vehiculo; // Guarda el vehículo original
        initComponents();

        // Cargar los datos del vehículo en los campos de texto
        cargarDatosVehiculo();

        // Configurar el JComboBox de departamentos
        configurarComboBoxDepartamentos();

        // Listener para el botón OK (ahora para guardar las modificaciones)
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarModificaciones();
            }
        });

        // Listener para el botón Salida (cerrar ventana sin guardar)
        Salida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.setLocationRelativeTo(null);
    }

    private void cargarDatosVehiculo() {
        if (vehiculoAEditar != null) {
            Placa.setText(vehiculoAEditar.getPlaca());
            DPI.setText(vehiculoAEditar.getDpi());
            Nombre.setText(vehiculoAEditar.getNombre());
            Marca.setText(vehiculoAEditar.getMarca());
            Modelo.setText(vehiculoAEditar.getModelo());
            Año.setText(String.valueOf(vehiculoAEditar.getAño()));
            Multas.setText(String.valueOf(vehiculoAEditar.getMultas()));
            Traspasos.setText(String.valueOf(vehiculoAEditar.getTraspasos()));
            // No establecemos el departamento aquí todavía, lo haremos después de llenar el ComboBox
        }
    }

    private void configurarComboBoxDepartamentos() {
        // Obtener la lista de departamentos cargados desde Principal
        // Necesitas un getter en Principal para departamentosCargados
        Set<String> departamentosDisponibles = ventanaPrincipal.getDepartamentosCargados();

        cmbDepartamento.removeAllItems();
        for (String depto : departamentosDisponibles) {
            cmbDepartamento.addItem(depto);
        }
        // Si el departamento del vehículo no está en la lista (ej., es un depto nuevo), agrégalo
        if (vehiculoAEditar != null && !departamentosDisponibles.contains(vehiculoAEditar.getDepartamento())) {
             cmbDepartamento.addItem(vehiculoAEditar.getDepartamento());
        }

        // Seleccionar el departamento actual del vehículo
        if (vehiculoAEditar != null && vehiculoAEditar.getDepartamento() != null) {
            cmbDepartamento.setSelectedItem(vehiculoAEditar.getDepartamento());
        } else {
            // Si no hay depto, selecciona el primero o una opción por defecto
            if (cmbDepartamento.getItemCount() > 0) {
                cmbDepartamento.setSelectedIndex(0);
            }
        }
    }


    private void guardarModificaciones() {
        try {
            // 1. Obtener los datos modificados de los JTextField
            String placa = Placa.getText().trim();
            String dpi = DPI.getText().trim();
            String nombre = Nombre.getText().trim();
            String marca = Marca.getText().trim();
            String modelo = Modelo.getText().trim();
            int año = Integer.parseInt(Año.getText().trim());
            int multas = Integer.parseInt(Multas.getText().trim());
            int traspasos = Integer.parseInt(Traspasos.getText().trim());
            String departamento = (String) cmbDepartamento.getSelectedItem();

            // Validaciones (similar a las de agregar, pero considera si la placa es la misma)
            if (placa.isEmpty() || dpi.isEmpty() || nombre.isEmpty() || marca.isEmpty() || modelo.isEmpty() || departamento == null || departamento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben ser llenados.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Agrega más validaciones si es necesario (ej. formato de placa, rangos de año/multas/traspasos)


            // 2. Crear un nuevo objeto Vehiculo con los datos modificados
            // Es vital mantener el ID del vehículo original
            Vehiculo vehiculoModificado = new Vehiculo(placa, dpi, nombre, marca, modelo, año, multas, traspasos);
            vehiculoModificado.setId(vehiculoAEditar.getId()); // ¡Mantener el ID original!
            vehiculoModificado.setDepartamento(departamento);

            // 3. Llamar al método en la ventana Principal para actualizar el vehículo
            ventanaPrincipal.actualizarVehiculoModificado(vehiculoAEditar, vehiculoModificado);

            JOptionPane.showMessageDialog(this, "Vehículo modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar la ventana de edición
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para Año, Multas y Traspasos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al guardar las modificaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


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
        Salida = new javax.swing.JButton();
        cmbDepartamento = new javax.swing.JComboBox<>();

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

        DPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DPIActionPerformed(evt);
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

        Modelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModeloActionPerformed(evt);
            }
        });

        Traspasos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TraspasosActionPerformed(evt);
            }
        });

        Salida.setText("Salir");
        Salida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalidaActionPerformed(evt);
            }
        });

        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Antigua_Guatemala", "Chimaltenango", "Chiquimula", "Escuintla", "Guatemala", "Huehuetenango", "Peten", "Quetzaltenango", "San_Marcos", "Suchitepequez" }));
        cmbDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelitoLayout = new javax.swing.GroupLayout(Panelito);
        Panelito.setLayout(PanelitoLayout);
        PanelitoLayout.setHorizontalGroup(
            PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelitoLayout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addComponent(OK)
                .addGap(157, 157, 157))
            .addGroup(PanelitoLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(N)
                    .addComponent(MO)
                    .addComponent(A)
                    .addGroup(PanelitoLayout.createSequentialGroup()
                        .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(P)
                            .addComponent(D)
                            .addComponent(MA)
                            .addComponent(M)
                            .addComponent(TRA))
                        .addGap(52, 52, 52)
                        .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Año, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(Modelo)
                            .addComponent(Nombre)
                            .addComponent(Placa)
                            .addComponent(DPI)
                            .addComponent(Marca)
                            .addComponent(Multas)
                            .addComponent(Traspasos))))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelitoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Salida)
                .addContainerGap())
        );
        PanelitoLayout.setVerticalGroup(
            PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelitoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelitoLayout.createSequentialGroup()
                        .addComponent(Salida)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelitoLayout.createSequentialGroup()
                        .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelitoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(P)))
                .addGap(18, 18, 18)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(D)
                    .addComponent(DPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(N)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(MA)
                    .addComponent(Marca, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MO)
                    .addComponent(Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(A)
                    .addComponent(Año, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Multas, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(M))
                .addGap(6, 6, 6)
                .addGroup(PanelitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Traspasos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TRA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(OK)
                .addGap(25, 25, 25))
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

    }//GEN-LAST:event_OKActionPerformed

    private void PlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlacaActionPerformed

    }//GEN-LAST:event_PlacaActionPerformed

    private void AñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AñoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AñoActionPerformed

    private void TraspasosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TraspasosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TraspasosActionPerformed

    private void SalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalidaActionPerformed
        dispose();
    }//GEN-LAST:event_SalidaActionPerformed

    private void cmbDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentoActionPerformed

    }//GEN-LAST:event_cmbDepartamentoActionPerformed

    private void NombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreActionPerformed

    private void ModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ModeloActionPerformed

    private void DPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DPIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DPIActionPerformed



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
    private javax.swing.JButton Salida;
    private javax.swing.JLabel TRA;
    private javax.swing.JTextField Traspasos;
    private javax.swing.JComboBox<String> cmbDepartamento;
    // End of variables declaration//GEN-END:variables
}