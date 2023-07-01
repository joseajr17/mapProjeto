package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Compra {
    private String date;
    private String time;
    private Pedido pedido;
    private boolean avaliado;
    
    public Compra() {
    }

    
    public Compra(Pedido pedido) {
        this.pedido = pedido;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().toString();
        this.avaliado = false;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


    @Override
    public String toString() {
        return  "Data: " + date + " Hora: " + time + "\nCompra -> " + pedido;
    }


    public boolean isAvaliado() {
        return avaliado;
    }


    public void setAvaliado(boolean avaliado) {
        this.avaliado = avaliado;
    }
   
}
