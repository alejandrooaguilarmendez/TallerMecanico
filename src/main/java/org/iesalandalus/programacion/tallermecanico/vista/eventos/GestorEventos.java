package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorEventos {
    private final Map<Evento, List<ReceptorEventos>> receptores;

    public GestorEventos(Evento... eventos) {
        receptores = new HashMap<>();
        for (Evento evento : eventos) {
            receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos) {
        for (Evento evento : eventos) {
            List<ReceptorEventos> listaReceptores = receptores.get(evento);
            if (listaReceptores != null)
                listaReceptores.add(receptor);
        }
    }

    public void desuscribir(ReceptorEventos receptor, Evento... eventos) {
        for (Evento evento : eventos) {
            List<ReceptorEventos> listaReceptores = receptores.get(evento);
            if (listaReceptores != null)
                listaReceptores.remove(receptor);
        }
    }

    public void notificar(Evento evento) {
        List<ReceptorEventos> listaReceptores = receptores.get(evento);
        if (listaReceptores != null) {
            for (ReceptorEventos receptor : listaReceptores) {
                receptor.actualizar(evento);
            }
        }
    }
}
