package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import java.time.LocalDate;
import java.util.Locale;

public class Revision extends Trabajo {

    private static final float FACTOR_DIA = 10f;
    private static final float FACTOR_HORA = 35f;

    private int horas;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
        this.horas = 0;
    }

    public Revision(Revision revision) {
        super(revision);
        this.horas = revision.horas;
    }

    @Override
    public int getHoras() {
        return horas;
    }

    @Override
    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        super.anadirHoras(horas);
        this.horas += horas;
    }

    @Override
    public float getPrecio() {
        return getDias() * FACTOR_DIA + horas * FACTOR_HORA;
    }

    @Override
    public String toString() {
        String fechaFinStr = getFechaFin() != null ? getFechaFin().format(FORMATO_FECHA) : "";
        String base = String.format("Revisión -> %s - %s (%s - %s): %d horas",
                getCliente(), getVehiculo(),
                getFechaInicio().format(FORMATO_FECHA),
                fechaFinStr, horas);
        if (estaCerrado())
            return base + String.format(new Locale("es", "ES"), ", %.2f € total", getPrecio());
        return base;
    }
}

