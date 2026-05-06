package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.Map;
import java.util.TreeMap;

public enum Evento {
    INSERTAR_CLIENTE(1, "Insertar cliente"),
    BUSCAR_CLIENTE(2, "Buscar cliente"),
    BORRAR_CLIENTE(3, "Borrar cliente"),
    LISTAR_CLIENTES(4, "Listar clientes"),
    MODIFICAR_CLIENTE(5, "Modificar cliente"),
    INSERTAR_VEHICULO(6, "Insertar vehículo"),
    BUSCAR_VEHICULO(7, "Buscar vehículo"),
    BORRAR_VEHICULO(8, "Borrar vehículo"),
    LISTAR_VEHICULOS(9, "Listar vehículos"),
    INSERTAR_REVISION(10, "Insertar revisión"),
    INSERTAR_MECANICO(11, "Insertar mecánico"),
    BUSCAR_TRABAJO(12, "Buscar trabajo"),
    BORRAR_TRABAJO(13, "Borrar trabajo"),
    LISTAR_TRABAJOS(14, "Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(15, "Listar trabajos de cliente"),
    LISTAR_TRABAJOS_VEHICULO(16, "Listar trabajos de vehículo"),
    ANADIR_HORAS_TRABAJO(17, "Añadir horas a trabajo"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(18, "Añadir precio material a trabajo"),
    CERRAR_TRABAJO(19, "Cerrar trabajo"),
    SALIR(20, "Salir");

    private static final Map<Integer, Evento> eventos = new TreeMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    private final int codigo;
    private final String texto;

    Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return codigo + ". " + texto;
    }
}
