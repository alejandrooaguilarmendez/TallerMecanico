package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.List;

public class Vista {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar() {
        do {
            Consola.mostrarMenu();
            ejecutar(Consola.elegirOpcion());
        } while (Consola.elegirOpcion() != Opcion.SALIR);
    }

    public void terminar() {
        System.out.println("\n¡Hasta luego!");
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE                -> insertarCliente();
            case BUSCAR_CLIENTE                  -> buscarCliente();
            case MODIFICAR_CLIENTE               -> modificarCliente();
            case BORRAR_CLIENTE                  -> borrarCliente();
            case LISTAR_CLIENTES                 -> listarClientes();
            case INSERTAR_VEHICULO               -> insertarVehiculo();
            case BUSCAR_VEHICULO                 -> buscarVehiculo();
            case BORRAR_VEHICULO                 -> borrarVehiculo();
            case LISTAR_VEHICULOS                -> listarVehiculos();
            case INSERTAR_REVISION               -> insertarRevision();
            case BUSCAR_REVISION                 -> buscarRevision();
            case ANADIR_HORAS_REVISION           -> anadirHoras();
            case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
            case CERRAR_REVISION                 -> cerrarRevision();
            case BORRAR_REVISION                 -> borrarRevision();
            case LISTAR_REVISIONES               -> listarRevisiones();
            case LISTAR_REVISIONES_CLIENTE       -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO      -> listarRevisionesVehiculo();
            case SALIR                           -> terminar();
        }
    }

    private void insertarCliente() {
        Consola.mostrarCabecera("Insertar cliente");
        try {
            controlador.insertar(Consola.leerCliente());
            System.out.println("Cliente insertado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarCliente() {
        Consola.mostrarCabecera("Buscar cliente");
        try {
            Cliente resultado = controlador.buscar(Consola.leerClienteDni());
            System.out.println(resultado != null ? resultado : "No se encontró el cliente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void modificarCliente() {
        Consola.mostrarCabecera("Modificar cliente");
        try {
            Cliente cliente = Consola.leerClienteDni();
            String nombre = Consola.leerNuevoNombre();
            String tlf = Consola.leerNuevoTelefono();
            controlador.modificar(cliente, nombre.isBlank() ? null : nombre, tlf.isBlank() ? null : tlf);
            System.out.println("Cliente modificado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void borrarCliente() {
        Consola.mostrarCabecera("Borrar cliente");
        try {
            controlador.borrar(Consola.leerClienteDni());
            System.out.println("Cliente borrado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarClientes() {
        Consola.mostrarCabecera("Listado de clientes");
        List<Cliente> clientes = controlador.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("Insertar vehículo");
        try {
            controlador.insertar(Consola.leerVehiculo());
            System.out.println("Vehículo insertado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("Buscar vehículo");
        try {
            Vehiculo resultado = controlador.buscar(Consola.leerVehiculoMatricula());
            System.out.println(resultado != null ? resultado : "No se encontró el vehículo.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("Borrar vehículo");
        try {
            controlador.borrar(Consola.leerVehiculoMatricula());
            System.out.println("Vehículo borrado correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("Listado de vehículos");
        List<Vehiculo> vehiculos = controlador.getVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            vehiculos.forEach(System.out::println);
        }
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("Insertar revisión");
        try {
            Cliente cliente = Consola.leerClienteDni();
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            controlador.insertar(new Revision(cliente, vehiculo, fechaCierre));
            System.out.println("Revisión insertada correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("Buscar revisión");
        try {
            Cliente cliente = Consola.leerClienteDni();
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            Revision resultado = controlador.buscar(new Revision(cliente, vehiculo, fechaCierre));
            System.out.println(resultado != null ? resultado : "No se encontró la revisión.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("Añadir horas a revisión");
        try {
            Cliente cliente = Consola.leerClienteDni();
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            controlador.anadirHoras(new Revision(cliente, vehiculo, fechaCierre), Consola.leerHoras());
            System.out.println("Horas añadidas correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("Añadir precio de material a revisión");
        try {
            Cliente cliente = Consola.leerClienteDni();
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            controlador.anadirPrecioMaterial(new Revision(cliente, vehiculo, fechaCierre), Consola.leerPrecioMaterial());
            System.out.println("Precio de material añadido correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("Cerrar revisión");
        try {
            Cliente cliente = Consola.leerClienteDni();
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            controlador.cerrar(new Revision(cliente, vehiculo, fechaCierre), Consola.leerFechaCierre());
            System.out.println("Revisión cerrada correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("Borrar revisión");
        try {
            Cliente cliente = Consola.leerClienteDni();
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            controlador.borrar(new Revision(cliente, vehiculo, fechaCierre));
            System.out.println("Revisión borrada correctamente.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("Listado de revisiones");
        List<Revision> revisiones = controlador.getRevisiones();
        if (revisiones.isEmpty()) {
            System.out.println("No hay revisiones registradas.");
        } else {
            revisiones.forEach(System.out::println);
        }
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Revisiones de un cliente");
        try {
            List<Revision> revisiones = controlador.getRevisiones(Consola.leerClienteDni());
            if (revisiones.isEmpty()) {
                System.out.println("No hay revisiones para ese cliente.");
            } else {
                revisiones.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Revisiones de un vehículo");
        try {
            List<Revision> revisiones = controlador.getRevisiones(Consola.leerVehiculoMatricula());
            if (revisiones.isEmpty()) {
                System.out.println("No hay revisiones para ese vehículo.");
            } else {
                revisiones.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
