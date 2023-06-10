package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Compra {
    private LocalDate date;
    private LocalTime time;
    private Pedido pedido;
    
    public Compra() {
    }

    
    public Compra(Pedido pedido) {
        this.pedido = pedido;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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
        return "Compra: " + pedido + "Data: " + date + "Hora: " + time;
    }
   
}
