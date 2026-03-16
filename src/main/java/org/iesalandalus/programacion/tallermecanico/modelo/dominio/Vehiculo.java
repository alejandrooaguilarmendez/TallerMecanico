package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public record Vehiculo(String marca, String modelo, String matricula) {
    private static final String ER_MARCA = "^([A-Z][a-z]+|[A-Z][a-z]+ [A-Z][a-z]+|[A-Z]+|[A-Z][a-z]+-[A-Z][a-z]+|[A-Z][a-z]+[A-Z][a-z]+)$";
    private static final String ER_MATRICULA = "^\\d{4}[B-DF-HJ-NPR-TV-Z]{3}$";

    public Vehiculo{
        validarMarca(marca);
        validarModelo(modelo);
        validarMatricula(matricula);
    }

    private void validarMarca(String marca){
        Objects.requireNonNull(marca, "La marca no puede ser nula.");
        if (!marca.matches(ER_MARCA)){
            throw new IllegalArgumentException("La marca no tiene un formato válido.");
        }
    }
    private void validarModelo(String modelo){
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo.");
        if (modelo.isBlank()){
            throw new IllegalArgumentException("El modelo no puede estar en blanco.");
        }
    }
    private void validarMatricula(String matricula){
        Objects.requireNonNull(matricula, "La matrícula no puede ser nula.");
        if (!matricula.matches(ER_MATRICULA)){
            throw new IllegalArgumentException("La matrícula no tiene un formato válido.");
        }
    }

    public static Vehiculo get(String matricula){
        Objects.requireNonNull(matricula, "La matrícula no puede ser nula.");
        return new Vehiculo("Seat", "Ibiza", matricula);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Vehiculo && java.util.Objects.equals(this.matricula, ((Vehiculo) o).matricula));
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", marca, modelo, matricula);
    }
}
