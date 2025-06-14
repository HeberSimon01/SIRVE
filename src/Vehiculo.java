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
    this.placa = placa; // <-- Asegúrate de que esta 'placa' ya viene en mayúsculas
    this.dpi = dpi;
    this.nombre = nombre;
    this.marca = marca;
    this.modelo = modelo;
    this.año = año;
    this.multas = multas;
    this.traspasos = traspasos;
    this.id = 0; // Inicializar ID a 0 (se asignará en ABB.insertar)
}
    // Nuevo Constructor completo (10 argumentos) - Usado para UI y cuando el ID y Departamento ya están disponibles
    public Vehiculo(int id, String placa, String dpi, String nombre, String marca, String modelo, int año, int multas, int traspasos, String departamento) {
        this.id = id;
        this.placa = placa.toUpperCase(); // Asegúrate de que la placa siempre esté en mayúsculas
        this.dpi = dpi;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.multas = multas;
        this.traspasos = traspasos;
        this.departamento = departamento;
    }
    public void setPlaca(String placa) {
        this.placa = placa.toUpperCase(); // Asegurar mayúsculas también al setear
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setMultas(int multas) {
        this.multas = multas;
    }

    public void setTraspasos(int traspasos) {
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
    return this.placa.toUpperCase().compareTo(otroVehiculo.getPlaca().toUpperCase());
}

    @Override
    public String toString() {
        return "ID: " + id + ", Placa: " + placa + ", Departamento: " + departamento + ", Marca: " + marca + ", Modelo: " + modelo;
    }
    public String toCsvLine() {
    // Asegúrate de que el orden y los campos coincidan con el formato de tu archivo .txt
    return String.join(",",
            placa,
            dpi,
            nombre,
            marca,
            modelo,
            String.valueOf(año),       // Convertir int a String
            String.valueOf(multas),    // Convertir int a String
            String.valueOf(traspasos)  // Convertir int a String
    );
}
        // NUEVO MÉTODO: toCsvString() con 10 campos para la persistencia en archivos
    public String toCsvString() {
        return String.join(",",
                String.valueOf(id),      // Campo 1: ID
                placa,                   // Campo 2: Placa
                dpi,                     // Campo 3: DPI
                nombre,                  // Campo 4: Nombre
                marca,                   // Campo 5: Marca
                modelo,                  // Campo 6: Modelo
                String.valueOf(año),     // Campo 7: Año
                String.valueOf(multas),  // Campo 8: Multas
                String.valueOf(traspasos),// Campo 9: Traspasos
                departamento             // Campo 10: Departamento
        );
    }
}