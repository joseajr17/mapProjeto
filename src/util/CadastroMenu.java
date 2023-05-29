package util;

import java.util.Scanner;

import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.LojaDao;
import model.entities.Comprador;
import model.entities.Loja;

public class CadastroMenu {

	private CompradorDao compradorDao;
	private LojaDao lojaDao;
	private Scanner sc;

	public CadastroMenu() {
		compradorDao = DaoFactory.criarCompradorDao();
		lojaDao = DaoFactory.criarLojaDao();
		sc = new Scanner(System.in);
	}

	public void exibirMenuCadastro() {
		int opcaoCadastro;

		do {
			System.out.println("----- MENU DE CADASTRO -----");
			System.out.println("1. Comprador");
			System.out.println("2. Loja");
			System.out.println("0. Voltar");
			System.out.print("Escolha uma opção: ");
			opcaoCadastro = sc.nextInt();
			sc.nextLine();

			switch (opcaoCadastro) {
			case 1:
				cadastrarComprador();
				break;
			case 2:
				cadastrarLoja();
				break;
			case 0:
				System.out.println("Voltando ao menu principal...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (opcaoCadastro != 0);
	}
	
	private void cadastrarComprador() {
        System.out.println("----- CADASTRO DE COMPRADOR -----");
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        String email;

        do{
            System.out.print("Email: ");
            email = sc.nextLine();
            if(!Validar.validarEmail(email)){
                System.out.println("Email inválido.");
            }
        } while(!Validar.validarEmail(email));

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String cpf;
        do{
            System.out.print("CPF: ");
            cpf = sc.nextLine();
            if(!Validar.validarCpf(cpf)){
                System.out.println("CPF inválido.");
            }
        } while(!Validar.validarCpf(cpf));
        

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        // Verificar se o CPF já existe no arquivo JSON
        if (compradorDao.verificarCpfExiste(cpf)) {
            System.out.println("Já existe um usuário com o CPF informado.");
            return;
        }

        Comprador comprador = new Comprador(nome, email, senha, cpf, endereco);

        compradorDao.cadastrar(comprador);
    }

    private void cadastrarLoja() {
        System.out.println("----- CADASTRO DE LOJA -----");
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        String email;

        do{
            System.out.print("Email: ");
            email = sc.nextLine();
            if(!Validar.validarEmail(email)){
                System.out.println("Email inválido.");
            }
        } while(!Validar.validarEmail(email));

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String cpfOUcnpj;

        do{
            System.out.print("CPF ou CNPJ: ");
            cpfOUcnpj = sc.nextLine();
            if(!Validar.validarCpfOuCnpj(cpfOUcnpj)){
                System.out.println("CPF ou CNPJ inválido.");
            }
        } while(!Validar.validarCpfOuCnpj(cpfOUcnpj));

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        // Verificar se o CPF ou CNPJ já existe no arquivo JSON
        if (lojaDao.verificarCpfOuCnpjExiste(cpfOUcnpj)) {
            System.out.println("Já existe uma loja com o CPF ou CNPJ informado.");
            return;
        }

        Loja loja = new Loja(nome, email, senha, cpfOUcnpj, endereco, null);

        lojaDao.cadastrar(loja);
    }
}
