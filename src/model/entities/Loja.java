package model.entities;

import java.io.Serializable;
import java.util.List;

import model.dao.AvaliacaoDao;
import model.dao.impl.AvaliacaoDaoImpl;

public class Loja implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
	private String senha;
	private String cpfOUcnpj;
	private String endereco;
	private List<Produto> produtos;
	
	public Loja() {
		this("", "", "", "", "", null);
	}

	public Loja(String nome, String email, String senha, String cpfOUcnpj, String endereco, List<Produto> produtos) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpfOUcnpj = cpfOUcnpj;
		this.endereco = endereco;
		this.produtos = produtos;
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

	public String getCpfOUcnpj() {
		return cpfOUcnpj;
	}

	public void setCpfOUcnpj(String cpfOUcnpj) {
		this.cpfOUcnpj = cpfOUcnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpfOUcnpj == null) ? 0 : cpfOUcnpj.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((produtos == null) ? 0 : produtos.hashCode());
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
		Loja other = (Loja) obj;
		if (cpfOUcnpj == null) {
			if (other.cpfOUcnpj != null)
				return false;
		} else if (!cpfOUcnpj.equals(other.cpfOUcnpj))
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

	private double getMediaNota(){
		AvaliacaoDao avaliacaoDao = new AvaliacaoDaoImpl();
		List<Avaliacao> avaliacoes = avaliacaoDao.listar(this);
		double nota = 0.0;
		int n = 0;
		for(Avaliacao avaliacao: avaliacoes){
			nota += avaliacao.getNota();
			n++;
		}
		return nota/n;
	}

	public Conceito getConceito(){
		double mediaNota = getMediaNota();

		if(mediaNota <= 2){
			return Conceito.RUIM;
		} else if(mediaNota <= 3){
			return Conceito.MEDIO;
		} else if (mediaNota <= 4){
			return Conceito.BOM;
		} else{
			return Conceito.EXCELENTE;
		}
	}

	@Override
	public String toString() {
		return "Loja [nome=" + nome + ", email=" + email + "conceito= " + getConceito() + ", senha=" + senha + ", cpfOUcnpj=" + cpfOUcnpj
				+ ", endereco=" + endereco + ", produtos=" + produtos + "]";
	}
}