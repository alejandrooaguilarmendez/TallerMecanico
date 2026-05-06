package org.iesalandalus.programacion.tallermecanico.modelo.dominio;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.util.Locale;

public class Mecanico extends Revision {

    private static final float FACTOR_HORA = 30f;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5f;

    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
        this.precioMaterial = 0f;
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        this.precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        if (estaCerrado())
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        if (precioMaterial <= 0)
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        this.precioMaterial += precioMaterial;
    }

    @Override
    public float getPrecio() {
        return getDias() * 10f + getHoras() * FACTOR_HORA + precioMaterial * FACTOR_PRECIO_MATERIAL;
    }

    @Override
    public String toString() {
        String fechaFinStr = getFechaFin() != null ? getFechaFin().format(FORMATO_FECHA) : "";
        String base = String.format(new Locale("es", "ES"),
                "Mecánico -> %s - %s (%s - %s): %d horas, %.2f € en material",
                getCliente(), getVehiculo(),
                getFechaInicio().format(FORMATO_FECHA),
                fechaFinStr, getHoras(), precioMaterial);
        if (estaCerrado())
            return base + String.format(new Locale("es", "ES"), ", %.2f € total", getPrecio());
        return base;
    }
}
