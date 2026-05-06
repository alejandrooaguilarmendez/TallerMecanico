package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModeloCascada implements Modelo {
    private IClientes clientes;
    private ITrabajos trabajos;
    private IVehiculos vehiculos;
    private final FabricaFuenteDatos fabricaFuenteDatos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos, "La fábrica de fuente de datos no puede ser nula.");
        this.fabricaFuenteDatos = fabricaFuenteDatos;
    }

    @Override
    public void comenzar() {
        clientes = fabricaFuenteDatos.crear().crearClientes();
        vehiculos = fabricaFuenteDatos.crear().crearVehiculos();
        trabajos = fabricaFuenteDatos.crear().crearTrabajos();
    }

    @Override
    public void terminar() {
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        clientes.buscar(trabajo.getCliente());
        vehiculos.buscar(trabajo.getVehiculo());
        trabajos.insertar(Trabajo.copiar(trabajo));
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Cliente clienteEncontrado = clientes.buscar(cliente);
        if (clienteEncontrado == null)
            return null;
        return new Cliente(clienteEncontrado);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        Trabajo trabajoEncontrado = trabajos.buscar(trabajo);
        if (trabajoEncontrado == null)
            return null;
        return Trabajo.copiar(trabajoEncontrado);
    }

    @Override
    public void modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        for (Trabajo trabajoEnCurso : trabajos.get(cliente)) {
            trabajos.borrar(trabajoEnCurso);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        for (Trabajo trabajoEnCurso : trabajos.get(vehiculo)) {
            trabajos.borrar(trabajoEnCurso);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente clienteEnCurso : clientes.get()) {
            copiaClientes.add(new Cliente(clienteEnCurso));
        }
        return copiaClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copiaTrabajos = new ArrayList<>();
        for (Trabajo trabajoEnCurso : trabajos.get()) {
            copiaTrabajos.add(Trabajo.copiar(trabajoEnCurso));
        }
        return copiaTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> copiaTrabajos = new ArrayList<>();
        for (Trabajo trabajoEnCurso : trabajos.get(cliente)) {
            copiaTrabajos.add(Trabajo.copiar(trabajoEnCurso));
        }
        return copiaTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        List<Trabajo> copiaTrabajos = new ArrayList<>();
        for (Trabajo trabajoEnCurso : trabajos.get(vehiculo)) {
            copiaTrabajos.add(Trabajo.copiar(trabajoEnCurso));
        }
        return copiaTrabajos;
    }
}
