package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola(){}

    public static void mostrarCabecera(String mensaje) {
        System.out.println(mensaje);
        for (int i = 0; i < mensaje.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void mostrarMenu() {
        mostrarCabecera("Menú de opciones:");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        Opcion opcion = null;
        boolean valida = false;
        do {
            try {
                int numeroOpcion = leerEntero("Introduce la opción deseada: ");
                opcion = Opcion.get(numeroOpcion);
                valida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (!valida);
        return opcion;
    }

    private static int leerEntero(String mensaje) {
        int entero;
        System.out.print(mensaje);
        entero = Entrada.entero();
        return entero;
    }

    private static float leerReal(String mensaje){
        float real;
        System.out.print(mensaje);
        real = Entrada.real();
        return real;
    }

    private static String leerCadena(String mensaje) {
        String cadena;
        System.out.print(mensaje);
        cadena = Entrada.cadena();
        return cadena;
    }

    private static LocalDate leerFecha(String mensaje){
        LocalDate fecha = null;
        boolean fechaValida = false;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        do {
            try {
                String cadenaIntroducida = leerCadena(mensaje);
                fecha = LocalDate.parse(cadenaIntroducida, formato);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: El formato de la fecha es incorrecto. Por favor, usa el formato: " + CADENA_FORMATO_FECHA);
            }
        } while (!fechaValida);
        return fecha;
    }

    public static Cliente leerCliente() {
        String dni = leerCadena("Introduce el DNI del cliente: ");
        String nombre = leerCadena("Introduce el nombre del cliente: ");
        String telefono = leerCadena("Introduce el teléfono del cliente: ");
        return new Cliente(nombre, dni, telefono);
    }

    public static Cliente leerClienteDni() {
        String dni = leerCadena("Introduce el DNI del cliente a buscar/borrar: ");
        return Cliente.get(dni);
    }

    public static String leerNuevoNombre() {
        return leerCadena("Introduce el nuevo nombre: ");
    }

    public static String leerNuevoTelefono() {
        return leerCadena("Introduce el nuevo teléfono: ");
    }

    public static Vehiculo leerVehiculo() {
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        String marca = leerCadena("Introduce la marca del vehículo: ");
        String modelo = leerCadena("Introduce el modelo del vehículo: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    public static Vehiculo leerVehiculoMatricula() {
        String matricula = leerCadena("Introduce la matrícula del vehículo a buscar/borrar: ");
        return Vehiculo.get(matricula);
    }

    public static Revision leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio de la revisión (" + CADENA_FORMATO_FECHA + "): ");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    public static int leerHoras() {
        return leerEntero("Introduce el número de horas: ");
    }

    public static float leerPrecioMaterial() {
        return leerReal("Introduce el precio del material: ");
    }

    public static LocalDate leerFechaCierre() {
        return leerFecha("Introduce la fecha de cierre de la revisión (" + CADENA_FORMATO_FECHA + "): ");
    }

}
