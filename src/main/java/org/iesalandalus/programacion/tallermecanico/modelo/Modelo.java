package org.iesalandalus.programacion.tallermecanico.modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Modelo {

    private Clientes clientes;
    private Revisiones revisiones;
    private Vehiculos vehiculos;

    public Modelo() {
        this.clientes   = new Clientes();
        this.revisiones = new Revisiones();
        this.vehiculos  = new Vehiculos();
    }

    public void comenzar() {}

    public void terminar() {}


    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    public Cliente buscar(Cliente cliente) {
        return new Cliente(clientes.buscar(cliente));
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return clientes.modificar(cliente, nombre, telefono);
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        for (Revision revision : revisiones.get(cliente)) {
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }

    public List<Cliente> getClientes() {
        List<Cliente> copia = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copia.add(new Cliente(cliente));
        }
        return copia;
    }


    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        for (Revision revision : revisiones.get(vehiculo)) {
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }


    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Cliente clienteReal   = clientes.buscar(revision.getCliente());
        Vehiculo vehiculoReal = vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(clienteReal, vehiculoReal, revision.getFechaInicio()));
    }

    public Revision buscar(Revision revision) {
        return new Revision(revisiones.buscar(revision));
    }

    public void anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        revisiones.anadirHoras(revision, horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        revisiones.anadirPrecioMaterial(revision, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        revisiones.cerrar(revision, fechaFin);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        revisiones.borrar(revision);
    }

    public List<Revision> getRevisiones() {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            copia.add(new Revision(revision));
        }
        return copia;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            copia.add(new Revision(revision));
        }
        return copia;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            copia.add(new Revision(revision));
        }
        return copia;
    }
}
