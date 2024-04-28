import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final ConversorMoeda conversorMoeda;

    public Menu(Scanner scanner, ConversorMoeda conversorMoeda) {
        this.scanner = scanner;
        this.conversorMoeda = conversorMoeda;
    }

    public void exibirMenu() {
        System.out.println("\n\n### Conversor de Moedas ###");
        System.out.println("1. Converter Moeda");
        System.out.println("2. Sair");
        System.out.print("Selecione sua opção: ");
    }

    public int lerOpcao() {
        return scanner.nextInt();
    }

    public void converterMoeda() {
        System.out.println("Informe a moeda de entrada EX: USD, AUD, BGN, CAD, CHF, CNY, EGP, BRL, EUR.");
        String moedaEntrada = scanner.next().toUpperCase();

        System.out.println("Informe a moeda de saída: EX: USD, AUD, BGN, CAD, CHF, CNY, EGP, BRL, EUR.");
        String moedaSaida = scanner.next().toUpperCase();

        System.out.print("Informe o valor a ser convertido: ");
        double valorEntrada = scanner.nextDouble();

        try {
            double valorConvertido = conversorMoeda.converter(valorEntrada, moedaEntrada, moedaSaida);
            System.out.printf("%.2f %s equivale a %.2f %s", valorEntrada, moedaEntrada, valorConvertido, moedaSaida);
        } catch (IOException | InterruptedException e) {
            System.err.println("Desculpe, não foi possível converter a moeda. Por favor, tente novamente mais tarde.");
        }
    }

    public void sair() {
        System.out.println("Saindo do Conversor de Moedas...");
    }

    public void executar() {
        while (true) {
            exibirMenu();
            int opcao = lerOpcao();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    converterMoeda();
                    break;
                case 2:
                    sair();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

}
