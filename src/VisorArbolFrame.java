/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

// Archivo: VisorArbolFrame.java
// Colócalo en el mismo paquete que tu clase Principal.java o en un paquete de "vistas"
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class VisorArbolFrame extends JFrame {

    private JLabel lblImagenArbol;
    private JScrollPane scrollPaneArbol;

public VisorArbolFrame(String imagePath, String title) {
    super(title); // Ahora el título es dinámico

        // Configuración básica de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al presionar la X
        setLayout(new BorderLayout()); // Usa un BorderLayout para ocupar todo el espacio

        // Inicializar el JLabel y el JScrollPane
        lblImagenArbol = new JLabel();
        lblImagenArbol.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen en el JLabel
        scrollPaneArbol = new JScrollPane(lblImagenArbol); // Envuelve el JLabel en un JScrollPane

        // Añadir el JScrollPane al centro de la ventana
        add(scrollPaneArbol, BorderLayout.CENTER);

        // Cargar y mostrar la imagen
        cargarImagen(imagePath);

        // Maximizar la ventana para que ocupe toda la pantalla
        // Esto funciona en la mayoría de los sistemas operativos y gestores de ventanas
        // setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza tanto ancho como alto
        
        // Opcional: Intentar usar el tamaño de la pantalla principal si MAXIMIZED_BOTH no es suficiente
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setSize(screenWidth, screenHeight); // Establece el tamaño al de la pantalla
        setLocationRelativeTo(null); // Centra la ventana en la pantalla (aunque ya esté maximizada)
    }

    private void cargarImagen(String imagePath) {
        File imageFile = new File(imagePath);
        if (imageFile.exists() && imageFile.isFile()) {
            ImageIcon icon = new ImageIcon(imagePath);
            if (icon.getIconWidth() == -1) {
                System.err.println("Error: No se pudo cargar la imagen del árbol correctamente. Ruta: " + imagePath);
                lblImagenArbol.setText("Error al cargar la imagen o imagen corrupta.");
            } else {
                lblImagenArbol.setIcon(icon);
                // No escalamos aquí, dejamos que el JScrollPane maneje la imagen grande
                lblImagenArbol.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
                scrollPaneArbol.revalidate(); // Revalidar el JScrollPane para que sepa el tamaño del JLabel
                scrollPaneArbol.repaint();
            }
        } else {
            lblImagenArbol.setText("Error: Archivo de imagen no encontrado en: " + imagePath);
            System.err.println("Error: Archivo de imagen del árbol no encontrado en: " + imagePath);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1025, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
