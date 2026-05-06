package org.iesalandalus.programacion.tallermecanico.vista;

public enum FabricaVista {
    TEXTO {
        @Override
        public Vista crear() {
            return new org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto();
        }
    };

    public abstract Vista crear();
}
