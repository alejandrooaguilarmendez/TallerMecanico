package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {

    private List<Revision> coleccionRevisiones;

    public Revisiones() {
        this.coleccionRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return coleccionRevisiones;
    }

    public List<Revision> get(Cliente cliente) {
        List<Revision> resultado = new ArrayList<>();
        for (Revision revisionEnCurso : coleccionRevisiones) {
            if (revisionEnCurso.getCliente().equals(cliente)) {
                resultado.add(revisionEnCurso);
            }
        }
        return resultado;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> resultado = new ArrayList<>();
        for (Revision revisionEnCurso : coleccionRevisiones) {
            if (revisionEnCurso.getVehiculo().equals(vehiculo)) {
                resultado.add(revisionEnCurso);
            }
        }
        return resultado;
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");

        int indice = coleccionRevisiones.indexOf(revision);
        if (indice != -1) {
            return coleccionRevisiones.get(indice);
        }
        return null;
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");

        Cliente cliente = revision.getCliente();
        Vehiculo vehiculo = revision.getVehiculo();
        LocalDate fechaInicio = revision.getFechaInicio();

        for (Revision revisionEnCurso : coleccionRevisiones) {
            if (!revisionEnCurso.estaCerrado()) {
                if (revisionEnCurso.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                }
                if (revisionEnCurso.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                }
            }
        }

        for (Revision revisionEnCurso : coleccionRevisiones) {
            if (revisionEnCurso.estaCerrado()) {
                if (revisionEnCurso.getCliente().equals(cliente) && !revisionEnCurso.getFechaFin().isBefore(fechaInicio)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                }
                if (revisionEnCurso.getVehiculo().equals(vehiculo) && !revisionEnCurso.getFechaFin().isBefore(fechaInicio)) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
                }
            }
        }

        coleccionRevisiones.add(revision);
    }

    public void anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");

        int indice = coleccionRevisiones.indexOf(revision);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        coleccionRevisiones.get(indice).anadirHoras(horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");

        int indice = coleccionRevisiones.indexOf(revision);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        coleccionRevisiones.get(indice).anadirPrecioMaterial(precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");

        int indice = coleccionRevisiones.indexOf(revision);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        coleccionRevisiones.get(indice).cerrar(fechaFin);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");

        boolean borrado = coleccionRevisiones.remove(revision);
        if (!borrado) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
    }
}
