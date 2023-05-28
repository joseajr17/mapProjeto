package util;

import java.util.Scanner;

import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.entities.Comprador;

public class CompradorMenu {
    private static CompradorDao compradorDao = DaoFactory.criarCompradorDao();
    private static Scanner sc = new Scanner(System.in);

    public void exibirMenuComprador(Comprador comprador) {
        int opcao;

        do {
            System.out.println("----- MENU DO COMPRADOR -----");
            System.out.println("1. Buscar loja");
            System.out.println("2. Listar todas as lojas");
            System.out.println("3. Buscar produto");
            System.out.println("4. Listar todos os produtos");
            System.out.println("5. Listar todos os produtos de uma loja específica");
            System.out.println("6. Ir para as opções de perfil");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    buscarLoja();
                    break;
                case 2:
                    listarTodasAsLojas();
                    break;
                case 3:
                    buscarProduto();
                    break;
                case 4:
                    listarTodosOsProdutos();
                    break;
                case 5:
                    listarProdutosDeLojaEspecifica();
                    break;
                case 6:
                    exibirOpcoesDePerfilComprador(comprador);
                    break;
                case 0:
                    System.out.println("Saindo do menu do comprador...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void buscarLoja() {
        // Implementação da busca de lojas
    }

    private void listarTodasAsLojas() {
        // Implementação da listagem de todas as lojas
    }

    private void buscarProduto() {
        // Implementação da busca de produtos
    }

    private void listarTodosOsProdutos() {
        // Implementação da listagem de todos os produtos
    }

    private void listarProdutosDeLojaEspecifica() {
        // Implementação da listagem de produtos de uma loja específica
    }

    private void exibirOpcoesDePerfilComprador(Comprador comprador) {
        int opcao;

        do {
            System.out.println("----- PERFIL DO COMPRADOR -----");
            System.out.println("1. Editar informações pessoais");
            System.out.println("2. Ver histórico de compras");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    editarInformacoesPessoais(comprador);
                    break;
                case 2:
                    verHistoricoDeCompras(comprador);
                    break;
                case 0:
                    System.out.println("Voltando para o menu do comprador...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void editarInformacoesPessoais(Comprador comprador) {
        // Implementação da edição das informações pessoais do comprador
    }

    private void verHistoricoDeCompras(Comprador comprador) {
        // Implementação da visualização do histórico de compras do comprador
    }
}
