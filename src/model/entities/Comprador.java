package model.entities;

import java.io.Serializable;
import java.util.List;

public class Comprador implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String endereco;
	private List<Produto> carrinhoDeCompras;
	private List<Produto> historicoDeCompras;
	
	public Comprador() {
		this("", "", "", "", "", null, null);
	}

	public Comprador(String nome, String email, String senha, String cpf, String endereco, List<Produto> carrinhoDeCompras, List<Produto> historicoDeCompras) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.endereco = endereco;
		this.carrinhoDeCompras = carrinhoDeCompras;
		this.historicoDeCompras = historicoDeCompras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Produto> getCarrinhoDeCompras() {
		return carrinhoDeCompras;
	}

	public void setCarrinhoDeCompras(List<Produto> carrinhoDeCompras) {
		this.carrinhoDeCompras = carrinhoDeCompras;
	}

	public List<Produto> getHistoricoDeCompras() {
		return historicoDeCompras;
	}

	public void setHistoricoDeCompras(List<Produto> historicoDeCompras) {
		this.historicoDeCompras = historicoDeCompras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrinhoDeCompras == null) ? 0 : carrinhoDeCompras.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((historicoDeCompras == null) ? 0 : historicoDeCompras.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Comprador other = (Comprador) obj;
		if (carrinhoDeCompras == null) {
			if (other.carrinhoDeCompras != null)
				return false;
		} else if (!carrinhoDeCompras.equals(other.carrinhoDeCompras))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (historicoDeCompras == null) {
			if (other.historicoDeCompras != null)
				return false;
		} else if (!historicoDeCompras.equals(other.historicoDeCompras))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comprador [nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf + ", endereco="
				+ endereco + ", carrinhoDeCompras=" + carrinhoDeCompras + ", historicoDeCompras=" + historicoDeCompras
				+ "]";
	}
}