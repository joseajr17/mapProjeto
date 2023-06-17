package model.entities;

public class Pedido {
    private Produto produto;
    private int quantidade;
    private double valor;

    public Pedido() {
    }

   public Pedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = quantidade * produto.getValor();
    }

public Produto getProduto() {
    return produto;
}

public void setProduto(Produto produto) {
    this.produto = produto;
}

public int getQuantidade() {
    return quantidade;
}

public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
    this.valor = quantidade * this.produto.getValor();
}

public double getValor() {
    return valor;
}

public void setValor(double valor) {
    this.valor = valor;
}

@Override
public String toString() {
    return "Produto: " + produto.getNome() + ", Quantidade: " + quantidade + ", Valor: " + valor + "\n";
}


}
