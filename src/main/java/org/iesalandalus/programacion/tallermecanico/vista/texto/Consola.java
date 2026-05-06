package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Consola {
    static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola() {
    }

    public static void mostrarCabecera(String mensaje) {
        System.out.printf("%n%s%n", mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    public static void mostrarMenu() {
        mostrarCabecera("Taller Mecánico");
        for (Evento evento : Evento.values()) {
            System.out.println(evento);
        }
    }

    public static Evento elegirOpcion() {
        int opcion;
        do {
            System.out.print("Elige una opción: ");
            opcion = org.iesalandalus.programacion.utilidades.Entrada.entero();
            if (!Evento.esValido(opcion))
                System.out.println("El valor introducido no es válido.");
        } while (!Evento.esValido(opcion));
        return Evento.get(opcion);
    }

    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return org.iesalandalus.programacion.utilidades.Entrada.cadena();
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return org.iesalandalus.programacion.utilidades.Entrada.entero();
    }

    public static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return org.iesalandalus.programacion.utilidades.Entrada.real();
    }

    public static LocalDate leerFecha(String mensaje) {
        LocalDate fecha = null;
        do {
            try {
                String cadenaFecha = leerCadena(mensaje);
                fecha = LocalDate.parse(cadenaFecha, DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA));
            } catch (DateTimeParseException e) {
                System.out.println("El formato de la fecha no es válido.");
            }
        } while (fecha == null);
        return fecha;
    }

    public static void mostrarCliente(Cliente cliente) {
        System.out.println(cliente);
    }

    public static void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println(vehiculo);
    }

    public static void mostrarTrabajo(Trabajo trabajo) {
        System.out.println(trabajo);
    }

    public static void mostrarClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public static void mostrarVehiculos(List<Vehiculo> vehiculos) {
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    public static void mostrarTrabajos(List<Trabajo> trabajos) {
        for (Trabajo trabajo : trabajos) {
            System.out.println(trabajo);
        }
    }
}
