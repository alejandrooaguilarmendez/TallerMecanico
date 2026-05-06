package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto implements Vista {
    private final GestorEventos gestorEventos;

    public VistaTexto() {
        gestorEventos = new GestorEventos(Evento.values());
    }

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        Evento opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            gestorEventos.notificar(opcion);
        } while (opcion != Evento.SALIR);
    }

    @Override
    public void terminar() {
        System.out.println("Hasta pronto.");
    }

    @Override
    public Cliente leerCliente() {
        String nombre = Consola.leerCadena("Nombre: ");
        String dni = Consola.leerCadena("DNI: ");
        String telefono = Consola.leerCadena("Teléfono: ");
        return new Cliente(nombre, dni, telefono);
    }

    @Override
    public Cliente leerClienteDni() {
        String dni = Consola.leerCadena("DNI: ");
        return Cliente.get(dni);
    }

    @Override
    public String leerNuevoNombre() {
        return Consola.leerCadena("Nuevo nombre (intro para no cambiar): ");
    }

    @Override
    public String leerNuevoTelefono() {
        return Consola.leerCadena("Nuevo teléfono (intro para no cambiar): ");
    }

    @Override
    public Vehiculo leerVehiculo() {
        String marca = Consola.leerCadena("Marca: ");
        String modelo = Consola.leerCadena("Modelo: ");
        String matricula = Consola.leerCadena("Matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        String matricula = Consola.leerCadena("Matrícula: ");
        return Vehiculo.get(matricula);
    }

    @Override
    public Trabajo leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = Consola.leerFecha("Fecha de inicio (dd/MM/yyyy): ");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    @Override
    public Trabajo leerMecanico() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = Consola.leerFecha("Fecha de inicio (dd/MM/yyyy): ");
        return new Mecanico(cliente, vehiculo, fechaInicio);
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        Vehiculo vehiculo = leerVehiculoMatricula();
        return Trabajo.get(vehiculo);
    }

    @Override
    public int leerHoras() {
        return Consola.leerEntero("Horas: ");
    }

    @Override
    public float leerPrecioMaterial() {
        return Consola.leerReal("Precio material: ");
    }

    @Override
    public LocalDate leerFechaCliente() {
        return Consola.leerFecha("Fecha (dd/MM/yyyy): ");
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito)
            System.out.printf("OK - %s: %s%n", evento, texto);
        else
            System.out.printf("ERROR - %s: %s%n", evento, texto);
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        Consola.mostrarCliente(cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        Consola.mostrarVehiculo(vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        Consola.mostrarTrabajo(trabajo);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        Consola.mostrarClientes(clientes);
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        Consola.mostrarVehiculos(vehiculos);
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        Consola.mostrarTrabajos(trabajos);
    }

}
