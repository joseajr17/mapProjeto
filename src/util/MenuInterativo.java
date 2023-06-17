package util;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MenuInterativo {

	private static Scanner sc;
	private static CadastroMenu cadastroMenu;
	private static LoginMenu loginMenu;

	public MenuInterativo() {
		sc = new Scanner(System.in);
		cadastroMenu = new CadastroMenu();
		loginMenu = new LoginMenu();

	}

	public void exibirMenu() {
	    int opcao;

	    do {
	        System.out.println("----- MENU -----");
	        System.out.println("1. Cadastro");
	        System.out.println("2. Login");
	        System.out.println("0. Sair");
	        System.out.print("Escolha uma opção: ");

	        try {
	            opcao = sc.nextInt();
	            sc.nextLine();

	            switch (opcao) {
	                case 1:
	                    cadastroMenu.exibirMenuCadastro();
	                    break;
	                case 2:
	                    loginMenu.exibirMenuLogin();
	                    break;
	                case 0:
	                    System.out.println("Saindo do programa...");
	                    break;
	                default:
	                    System.out.println("Opção inválida. Tente novamente.");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada inválida. Digite um número inteiro.");
	            sc.nextLine(); // Limpar o buffer do scanner
	            opcao = -1; // Definir um valor inválido para continuar no loop
	        }
	    } while (opcao != 0);
	}
}
