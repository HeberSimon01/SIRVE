/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Multa {
    private static int nextId = 1; // Para generar IDs automáticamente
    private int id;
    private String placa;
    private String fecha;
    private String descripcion;
    private double monto;
    private String departamento;

    public Multa(String placa, String fecha, String descripcion, double monto, String departamento) {
        this.id = nextId++;
        this.placa = placa;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.monto = monto;
        this.departamento = departamento;
    }

    // Getters para todos los atributos
    public int getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public static void setNextId(int nextId) {
        Multa.nextId = nextId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    // Opcional: Setter para resetear nextId si es necesario en algún punto (por ejemplo, al reiniciar la aplicación)
    public static void resetNextId() {
        nextId = 1;
    }


}