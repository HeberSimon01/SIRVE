/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// Vehiculo.java
// Vehiculo.java
public class Vehiculo implements Comparable<Vehiculo> {
    private int id; // Se generará automáticamente
    private String placa;
    private String dpi;
    private String nombre;
    private String marca;
    private String modelo;
    private int año;
    private int multas;
    private int traspasos;
    private String departamento; // Se añadirá al cargar el archivo

    // Constructor para cuando se lee del archivo (sin ID ni Departamento inicialmente)
    public Vehiculo(String placa, String dpi, String nombre, String marca, String modelo, int año, int multas, int traspasos) {
        this.placa = placa;
        this.dpi = dpi;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.multas = multas;
        this.traspasos = traspasos;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPlaca() { return placa; }
    public String getDpi() { return dpi; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAño() { return año; }
    public int getMultas() { return multas; }
    public int getTraspasos() { return traspasos; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    // Implementación de Comparable para el ordenamiento en el ABB (por Placa)
    @Override
    public int compareTo(Vehiculo otroVehiculo) {
        return this.placa.compareTo(otroVehiculo.placa);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Placa: " + placa + ", Departamento: " + departamento + ", Marca: " + marca + ", Modelo: " + modelo;
    }
}