package com.arreglalo.fixerapp;

public class solicitud_list {
    private String tipo;
    private String detalles;
    private String status;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public solicitud_list(String tipo, String detalles, String status) {
        this.tipo = tipo;
        this.detalles = detalles;
        this.status = status;
    }
}
