package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {
    private final List<Trabajo> coleccionTrabajos;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajoEnCurso : coleccionTrabajos) {
            if (trabajoEnCurso.getCliente().equals(cliente))
                trabajosCliente.add(trabajoEnCurso);
        }
        return trabajosCliente;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajoEnCurso : coleccionTrabajos) {
            if (trabajoEnCurso.getVehiculo().equals(vehiculo))
                trabajosVehiculo.add(trabajoEnCurso);
        }
        return trabajosVehiculo;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo trabajoEnCurso : coleccionTrabajos) {
            if (!trabajoEnCurso.estaCerrado()) {
                if (trabajoEnCurso.getCliente().equals(cliente))
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                if (trabajoEnCurso.getVehiculo().equals(vehiculo))
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
            } else {
                if (trabajoEnCurso.getCliente().equals(cliente) && !fechaInicio.isAfter(trabajoEnCurso.getFechaFin()))
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                if (trabajoEnCurso.getVehiculo().equals(vehiculo) && !fechaInicio.isAfter(trabajoEnCurso.getFechaFin()))
                    throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
            }
        }
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        for (Trabajo trabajoEnCurso : coleccionTrabajos) {
            if (trabajoEnCurso.getVehiculo().equals(vehiculo) && !trabajoEnCurso.estaCerrado())
                return trabajoEnCurso;
        }
        throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        getTrabajoAbierto(trabajo.getVehiculo()).anadirHoras(horas);
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (!(trabajoAbierto instanceof Mecanico trabajoMecanico))
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        trabajoMecanico.anadirPrecioMaterial(precioMaterial);
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        getTrabajoAbierto(trabajo.getVehiculo()).cerrar(fechaFin);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        if (indice == -1)
            return null;
        return coleccionTrabajos.get(indice);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.remove(trabajo))
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
    }
}
