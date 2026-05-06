package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador implements IControlador{

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo.");
        Objects.requireNonNull(vista, "La vista no puede ser nula.");
        this.modelo = modelo;
        this.vista = vista;
        vista.getGestorEventos().suscribir(this::actualizar, Evento.values());
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> insertarCliente();
                case BUSCAR_CLIENTE -> buscarCliente();
                case BORRAR_CLIENTE -> borrarCliente();
                case LISTAR_CLIENTES -> listarClientes();
                case MODIFICAR_CLIENTE -> modificarCliente();
                case INSERTAR_VEHICULO -> insertarVehiculo();
                case BUSCAR_VEHICULO -> buscarVehiculo();
                case BORRAR_VEHICULO -> borrarVehiculo();
                case LISTAR_VEHICULOS -> listarVehiculos();
                case INSERTAR_REVISION -> insertarRevision();
                case INSERTAR_MECANICO -> insertarMecanico();
                case BUSCAR_TRABAJO -> buscarTrabajo();
                case BORRAR_TRABAJO -> borrarTrabajo();
                case LISTAR_TRABAJOS -> listarTrabajos();
                case LISTAR_TRABAJOS_CLIENTE -> listarTrabajosCliente();
                case LISTAR_TRABAJOS_VEHICULO -> listarTrabajosVehiculo();
                case ANADIR_HORAS_TRABAJO -> anadirHorasTrabajo();
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> anadirPrecioMaterialTrabajo();
                case CERRAR_TRABAJO -> cerrarTrabajo();
                case SALIR -> terminar();
            }
        } catch (TallerMecanicoExcepcion | IllegalArgumentException | NullPointerException e) {
            vista.notificarResultado(evento, e.getMessage(), false);
        }
    }

    private void insertarCliente() throws TallerMecanicoExcepcion {
        Cliente cliente = vista.leerCliente();
        modelo.insertar(cliente);
        vista.notificarResultado(Evento.INSERTAR_CLIENTE, "Cliente insertado correctamente.", true);
    }

    private void buscarCliente() {
        Cliente cliente = vista.leerClienteDni();
        Cliente clienteEncontrado = modelo.buscar(cliente);
        if (clienteEncontrado != null)
            vista.mostrarCliente(clienteEncontrado);
        else
            vista.notificarResultado(Evento.BUSCAR_CLIENTE, "No se ha encontrado el cliente.", false);
    }

    private void borrarCliente() throws TallerMecanicoExcepcion {
        Cliente cliente = vista.leerClienteDni();
        modelo.borrar(cliente);
        vista.notificarResultado(Evento.BORRAR_CLIENTE, "Cliente borrado correctamente.", true);
    }

    private void listarClientes() {
        List<Cliente> clientes = modelo.getClientes();
        if (clientes.isEmpty())
            vista.notificarResultado(Evento.LISTAR_CLIENTES, "No hay clientes.", false);
        else
            vista.mostrarClientes(clientes);
    }

    private void modificarCliente() throws TallerMecanicoExcepcion {
        Cliente cliente = vista.leerClienteDni();
        String nuevoNombre = vista.leerNuevoNombre();
        String nuevoTelefono = vista.leerNuevoTelefono();
        modelo.modificar(cliente, nuevoNombre.isBlank() ? null : nuevoNombre,
                nuevoTelefono.isBlank() ? null : nuevoTelefono);
        vista.notificarResultado(Evento.MODIFICAR_CLIENTE, "Cliente modificado correctamente.", true);
    }

    private void insertarVehiculo() throws TallerMecanicoExcepcion {
        Vehiculo vehiculo = vista.leerVehiculo();
        modelo.insertar(vehiculo);
        vista.notificarResultado(Evento.INSERTAR_VEHICULO, "Vehículo insertado correctamente.", true);
    }

    private void buscarVehiculo() {
        Vehiculo vehiculo = vista.leerVehiculoMatricula();
        Vehiculo vehiculoEncontrado = modelo.buscar(vehiculo);
        if (vehiculoEncontrado != null)
            vista.mostrarVehiculo(vehiculoEncontrado);
        else
            vista.notificarResultado(Evento.BUSCAR_VEHICULO, "No se ha encontrado el vehículo.", false);
    }

    private void borrarVehiculo() throws TallerMecanicoExcepcion {
        Vehiculo vehiculo = vista.leerVehiculoMatricula();
        modelo.borrar(vehiculo);
        vista.notificarResultado(Evento.BORRAR_VEHICULO, "Vehículo borrado correctamente.", true);
    }

    private void listarVehiculos() {
        List<Vehiculo> vehiculos = modelo.getVehiculos();
        if (vehiculos.isEmpty())
            vista.notificarResultado(Evento.LISTAR_VEHICULOS, "No hay vehículos.", false);
        else
            vista.mostrarVehiculos(vehiculos);
    }

    private void insertarRevision() throws TallerMecanicoExcepcion {
        Trabajo revision = vista.leerRevision();
        modelo.insertar(revision);
        vista.notificarResultado(Evento.INSERTAR_REVISION, "Revisión insertada correctamente.", true);
    }

    private void insertarMecanico() throws TallerMecanicoExcepcion {
        Trabajo mecanico = vista.leerMecanico();
        modelo.insertar(mecanico);
        vista.notificarResultado(Evento.INSERTAR_MECANICO, "Mecánico insertado correctamente.", true);
    }

    private void buscarTrabajo() {
        Trabajo trabajo = vista.leerTrabajoVehiculo();
        Trabajo trabajoEncontrado = modelo.buscar(trabajo);
        if (trabajoEncontrado != null)
            vista.mostrarTrabajo(trabajoEncontrado);
        else
            vista.notificarResultado(Evento.BUSCAR_TRABAJO, "No se ha encontrado el trabajo.", false);
    }

    private void borrarTrabajo() throws TallerMecanicoExcepcion {
        Trabajo trabajo = vista.leerTrabajoVehiculo();
        modelo.borrar(trabajo);
        vista.notificarResultado(Evento.BORRAR_TRABAJO, "Trabajo borrado correctamente.", true);
    }

    private void listarTrabajos() {
        List<Trabajo> trabajos = modelo.getTrabajos();
        if (trabajos.isEmpty())
            vista.notificarResultado(Evento.LISTAR_TRABAJOS, "No hay trabajos.", false);
        else
            vista.mostrarTrabajos(trabajos);
    }

    private void listarTrabajosCliente() {
        Cliente cliente = vista.leerClienteDni();
        List<Trabajo> trabajos = modelo.getTrabajos(cliente);
        if (trabajos.isEmpty())
            vista.notificarResultado(Evento.LISTAR_TRABAJOS_CLIENTE, "No hay trabajos para ese cliente.", false);
        else
            vista.mostrarTrabajos(trabajos);
    }

    private void listarTrabajosVehiculo() {
        Vehiculo vehiculo = vista.leerVehiculoMatricula();
        List<Trabajo> trabajos = modelo.getTrabajos(vehiculo);
        if (trabajos.isEmpty())
            vista.notificarResultado(Evento.LISTAR_TRABAJOS_VEHICULO, "No hay trabajos para ese vehículo.", false);
        else
            vista.mostrarTrabajos(trabajos);
    }

    private void anadirHorasTrabajo() throws TallerMecanicoExcepcion {
        Trabajo trabajo = vista.leerTrabajoVehiculo();
        int horas = vista.leerHoras();
        modelo.anadirHoras(trabajo, horas);
        vista.notificarResultado(Evento.ANADIR_HORAS_TRABAJO, "Horas añadidas correctamente.", true);
    }

    private void anadirPrecioMaterialTrabajo() throws TallerMecanicoExcepcion {
        Trabajo trabajo = vista.leerTrabajoVehiculo();
        float precioMaterial = vista.leerPrecioMaterial();
        modelo.anadirPrecioMaterial(trabajo, precioMaterial);
        vista.notificarResultado(Evento.ANADIR_PRECIO_MATERIAL_TRABAJO, "Precio material añadido correctamente.", true);
    }

    private void cerrarTrabajo() throws TallerMecanicoExcepcion {
        Trabajo trabajo = vista.leerTrabajoVehiculo();
        LocalDate fechaCierre = vista.leerFechaCliente();
        modelo.cerrar(trabajo, fechaCierre);
        vista.notificarResultado(Evento.CERRAR_TRABAJO, "Trabajo cerrado correctamente.", true);
    }
}
