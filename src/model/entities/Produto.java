package model.entities;

import java.io.Serializable;

public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Double valor;
	private ProdutoTipo tipo;
	private Integer quantidade;
	private String marca;
	private String descricao;
	private String emailLoja;
	
	public Produto() {
		this("", null, null, null,"", "", "");
	}

	public Produto(String nome, Double valor, ProdutoTipo tipo, Integer quantidade, String marca, String descricao,
			String emailLoja) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.tipo = tipo;
		this.quantidade = quantidade;
		this.marca = marca;
		this.descricao = descricao;
		this.emailLoja = emailLoja;
	}



	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public ProdutoTipo getTipo() {
		return tipo;
	}
	public void setTipo(ProdutoTipo tipo) {
		this.tipo = tipo;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmailLoja() {
		return emailLoja;
	}

	public void setEmailLoja(String emailLoja) {
		this.emailLoja = emailLoja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((emailLoja == null) ? 0 : emailLoja.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (emailLoja == null) {
			if (other.emailLoja != null)
				return false;
		} else if (!emailLoja.equals(other.emailLoja))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
				
		}
		if (tipo != other.tipo)
			return false; 
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto -> Nome: " + nome + ", Valor: R$" + valor + ", Quant.: " + quantidade + ", Tipo: " + tipo
				+ ", Marca:" + marca + "\nDescricao: " + descricao ;
	}
}