package util;

import java.util.List;
import java.util.Scanner;

import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.LojaDao;
import model.entities.Comprador;
import model.entities.Loja;

public class LoginMenu {

    private Scanner sc;
    private static LojaDao lojaDao;
    private static CompradorDao compradorDao;

    public LoginMenu() {
        sc = new Scanner(System.in);
        lojaDao = DaoFactory.criarLojaDao();
        compradorDao = DaoFactory.criarCompradorDao();
    }

    public void exibirMenuLogin() {
        int opcaoLogin;

        do {
            System.out.println("----- MENU DE LOGIN -----");
            System.out.println("1. Comprador");
            System.out.println("2. Loja");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcaoLogin = sc.nextInt();
            sc.nextLine();

            switch (opcaoLogin) {
                case 1:
                    loginComprador();
                    break;
                case 2:
                    loginLoja();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoLogin != 0);
    }

    private void loginComprador() {
        System.out.println("----- LOGIN DE COMPRADOR -----");
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        // Verificar se as informações de login são válidas
        if (verificarLoginComprador(email, senha)) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + email + "!");
            CompradorMenu compradorMenu = new CompradorMenu();
            Comprador comprador = compradorDao.buscar(email); // Buscar o comprador no banco de dados
            compradorMenu.exibirMenuComprador(comprador);
        } else {
            System.out.println("Email ou senha inválidos. Tente novamente.");
        }
    }

	private void loginLoja() {
        System.out.println("----- LOGIN DE LOJA -----");
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        // Verificar se as informações de login são válidas
        if (verificarLoginLoja(email, senha)) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + email + "!");
            LojaMenu lojaMenu = new LojaMenu();
            Loja loja = lojaDao.buscar(email);
            lojaMenu.exibirMenuLoja(loja);
            // Chamada de método ou exibição de menu para o perfil da loja
        } else {
            System.out.println("Email ou senha inválidos. Tente novamente.");
        }
    }

    private void exibirPaginaInicialLoja() {
		// TODO Auto-generated method stub
		
	}

	private boolean verificarLoginComprador(String email, String senha) {
		List<Comprador> compradores = compradorDao.listarCompradores();

		for (Comprador comprador : compradores) {
			if (comprador.getEmail().equals(email) && comprador.getSenha().equals(senha)) {
				return true;
			}
		}

		return false;
    }

    private boolean verificarLoginLoja(String email, String senha) {
    	List<Loja> lojas= lojaDao.listarLojas();

		for (Loja loja: lojas) {
			if (loja.getEmail().equals(email) && loja.getSenha().equals(senha)) {
				return true;
			}
		}

		return false;
	}
}
