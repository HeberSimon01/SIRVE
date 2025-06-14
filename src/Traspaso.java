/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
    public class Traspaso {
        private static int nextId = 1; // For automatic ID generation

        private int id;
        private String placa;
        private String dpiAnterior;
        private String nombreAnterior;
        private String fecha;
        private String dpiNuevo;
        private String nombreNuevo;
        private String departamento;

        public Traspaso(String placa, String dpiAnterior, String nombreAnterior,
                        String fecha, String dpiNuevo, String nombreNuevo, String departamento) {
            this.id = nextId++;
            this.placa = placa;
            this.dpiAnterior = dpiAnterior;
            this.nombreAnterior = nombreAnterior;
            this.fecha = fecha;
            this.dpiNuevo = dpiNuevo;
            this.nombreNuevo = nombreNuevo;
            this.departamento = departamento;
        }

      public static void resetearSiguienteId() {
           nextId = 1; // Reiniciar el contador de IDs
       }

        // Getters for all fields (needed for JTable)
        public int getId() {
            return id;
        }

        public String getPlaca() {
            return placa;
        }

        public String getDpiAnterior() {
            return dpiAnterior;
        }

        public String getNombreAnterior() {
            return nombreAnterior;
        }

        public String getFecha() {
            return fecha;
        }

        public String getDpiNuevo() {
            return dpiNuevo;
        }

        public String getNombreNuevo() {
            return nombreNuevo;
        }

        public String getDepartamento() {
            return departamento;
        }

        public static void setNextId(int nextId) {
            Traspaso.nextId = nextId;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public void setDpiAnterior(String dpiAnterior) {
            this.dpiAnterior = dpiAnterior;
        }

        public void setNombreAnterior(String nombreAnterior) {
            this.nombreAnterior = nombreAnterior;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public void setDpiNuevo(String dpiNuevo) {
            this.dpiNuevo = dpiNuevo;
        }

        public void setNombreNuevo(String nombreNuevo) {
            this.nombreNuevo = nombreNuevo;
        }

        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }
    }