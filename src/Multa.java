
import java.io.File;
import javax.swing.JFileChooser;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Multa {
    private static int contador = 1;
    private int id;
    private String placa;
    private String fecha;
    private String descripcion;
    private double monto;

    public Multa(String placa, String fecha, String descripcion, double monto) {
        this.id = contador++;
        this.placa = placa;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public int getId() { return id; }
    public String getPlaca() { return placa; }
    public String getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public double getMonto() { return monto; }
    
    
}

