package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Revision {

    private static final float PRECIO_HORA = 30f;
    private static final float PRECIO_DIA = 10f;
    private static float PRECIO_MATERIAL = 1.5f;
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        this.fechaFin = null;
        this.horas = 0;
        precioMaterial = 0;
    }

    public Revision(Revision revision) {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        cliente = new Cliente(revision.cliente);
        vehiculo = revision.vehiculo;
        fechaInicio = revision.fechaInicio;
        fechaFin = revision.fechaFin;
        horas = revision.horas;
        precioMaterial = revision.precioMaterial;
    }
    public Cliente getCliente() { return cliente; }

    private void setCliente (Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }
    public Vehiculo getVehiculo() { return vehiculo; }

    private void setVehiculo (Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }
    public LocalDate getFechaInicio() { return fechaInicio; }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() { return fechaFin; }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (fechaFin.isBefore(fechaInicio)){
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        this.fechaFin = fechaFin;
    }
    public int getHoras() { return horas; }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que la revisión está cerrada.");
        }
        this.horas += horas;
    }
    public float getPrecioMaterial() { return precioMaterial; }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion{
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        if (estaCerrada()){
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }
        this.precioMaterial += precioMaterial;
    }
    public boolean estaCerrada() {
        return fechaFin != null;
    }
    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion{
        if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("La revisión ya está cerrada.");
        }
        setFechaFin(fechaFin);
    }

    public double getPrecio() {
        float precioFijo = PRECIO_DIA * getDias() + PRECIO_HORA * getHoras();
        float precioEspecifico = PRECIO_MATERIAL * precioMaterial;
        return precioFijo + precioEspecifico;
    }

    private float getDias(){
        return (estaCerrada()) ? (int) ChronoUnit.DAYS.between(fechaInicio,fechaFin): 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Revision revision = (Revision) obj;
        return cliente.equals(revision.cliente) &&
                vehiculo.equals(revision.vehiculo) &&
                fechaInicio.equals(revision.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, vehiculo, fechaInicio);
    }

    @Override
    public String toString() {
        String cadena;
        if (!estaCerrada()){
            cadena = String.format("%s - %s: (%s - ), 0 horas, 0,00 € en material", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), horas, precioMaterial);
        } else {
            cadena = String.format("%s - %s: (%s - %s), 0 horas, 0,00 € en material, 10,00 € total", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), fechaFin.format(FORMATO_FECHA), horas, precioMaterial, getPrecio());
        }
        return cadena;
    }
}
