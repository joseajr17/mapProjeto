package model.entities;

public class Comentario {
    private String comentario;
    private Produto produto;
    private int nota;

    
    public Comentario(String comentario, Produto produto, int nota) {
        this.comentario = comentario;
        this.produto = produto;
        this.nota = nota;
    }
    public int getNota() {
        return nota;
    }
    public void setNota(int nota) {
        this.nota = nota;
    }
    public Comentario(String comentario, Produto produto) {
        this.comentario = comentario;
        this.produto = produto;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public String getKeyLoja() {
        return produto.getEmailLoja();
    }
    
    @Override
    public String toString() {
        return "Comentario [comentario=" + comentario + ", produto=" + produto + ", nota=" + nota + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
        result = prime * result + ((produto == null) ? 0 : produto.hashCode());
        result = prime * result + nota;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comentario other = (Comentario) obj;
        if (comentario == null) {
            if (other.comentario != null)
                return false;
        } else if (!comentario.equals(other.comentario))
            return false;
        if (produto == null) {
            if (other.produto != null)
                return false;
        } else if (!produto.equals(other.produto))
            return false;
        if (nota != other.nota)
            return false;
        return true;
    }
    
 

    
}
