import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static Scanner scanner = new Scanner(System.in);
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }

    /**
     * Método para testar um algoritmo de ordenação com um vetor específico
     * @param ordenador O algoritmo de ordenação a ser testado
     * @param vetorOriginal O vetor a ser ordenado
     * @param nome Nome do algoritmo para exibição
     * @param exibirVetor Se true, exibe o vetor ordenado
     */
    static void testarAlgoritmo(IOrdenador<Integer> ordenador, Integer[] vetorOriginal, String nome, boolean exibirVetor) {
        Integer[] vetorCopia = vetorOriginal.clone();
        
        System.out.print("Ordenando com " + nome + "... ");
        
        Integer[] vetorOrdenado = ordenador.ordenar(vetorCopia);
        
        System.out.println("CONCLUÍDO!");
        System.out.println("  Comparações: " + ordenador.getComparacoes());
        System.out.println("  Movimentações: " + ordenador.getMovimentacoes());
        System.out.println("  Tempo de ordenação: " + String.format("%.3f", ordenador.getTempoOrdenacao()) + " ms");
        
        // Verifica se o vetor foi ordenado corretamente
        boolean estaOrdenado = true;
        for (int i = 0; i < vetorOrdenado.length - 1; i++) {
            if (vetorOrdenado[i].compareTo(vetorOrdenado[i+1]) > 0) {
                estaOrdenado = false;
                break;
            }
        }
        System.out.println("  Vetor ordenado corretamente: " + (estaOrdenado ? "SIM" : "NÃO"));
        
        // Exibe o vetor ordenado se solicitado e se for pequeno
        if (exibirVetor && vetorOriginal.length <= 48) {
            System.out.println("  Vetor ordenado: " + Arrays.toString(vetorOrdenado));
        }
    }

    /**
     * Exibe o menu principal e retorna a opção escolhida pelo usuário
     */
    static int exibirMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("         SISTEMA DE ORDENAÇÃO - AEDs II");
        System.out.println("=".repeat(60));
        System.out.println("Escolha o algoritmo de ordenação:");
        System.out.println("  1 - BubbleSort");
        System.out.println("  2 - InsertionSort");
        System.out.println("  3 - SelectionSort");
        System.out.println("  4 - MergeSort");
        System.out.println("  0 - Sair");
        System.out.println("-".repeat(60));
        System.out.print("Digite sua opção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        return opcao;
    }
    
    /**
     * Solicita ao usuário o tamanho do vetor
     */
    static int solicitarTamanho() {
        System.out.print("\nDigite o tamanho do vetor (ex: 10, 100, 1000): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();
        
        // Validação básica
        while (tamanho <= 0) {
            System.out.print("Tamanho inválido! Digite um valor positivo: ");
            tamanho = scanner.nextInt();
            scanner.nextLine();
        }
        
        // Aviso para vetores muito grandes
        if (tamanho > 100_000) {
            System.out.println("\n⚠️  ATENÇÃO: Vetor grande detectado!");
            System.out.println("   - BubbleSort, InsertionSort e SelectionSort (O(n²)) podem ser EXTREMAMENTE lentos.");
            System.out.println("   - MergeSort (O(n log n)) é recomendado para este tamanho.");
            System.out.print("   Deseja continuar mesmo assim? (S/N): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("S")) {
                System.out.println("Operação cancelada.");
                return -1;
            }
        }
        
        return tamanho;
    }
    
    /**
     * Pergunta se o usuário deseja exibir o vetor ordenado
     */
    static boolean solicitarExibicaoVetor() {
        System.out.print("\nDeseja exibir o vetor ordenado? (S/N): ");
        String resposta = scanner.nextLine();
        return resposta.equalsIgnoreCase("S");
    }
    
    /**
     * Pergunta se o usuário deseja realizar outro teste
     */
    static boolean solicitarNovoTeste() {
        System.out.print("\nDeseja realizar outra ordenação? (S/N): ");
        String resposta = scanner.nextLine();
        return resposta.equalsIgnoreCase("S");
    }

    public static void main(String[] args) {
        System.out.println("=== BEM-VINDO AO SISTEMA DE ORDENAÇÃO ===");
        System.out.println("Algoritmos disponíveis: BubbleSort, InsertionSort, SelectionSort, MergeSort");
        
        boolean continuar = true;
        
        while (continuar) {
            int opcao = exibirMenu();
            
            if (opcao == 0) {
                System.out.println("\nEncerrando o programa. Até mais!");
                continuar = false;
                break;
            }
            
            if (opcao < 1 || opcao > 4) {
                System.out.println("\n❌ Opção inválida! Tente novamente.");
                continue;
            }
            
            // Solicita o tamanho do vetor
            int tamanho = solicitarTamanho();
            if (tamanho == -1) {
                continue; // Usuário cancelou
            }
            
            // Pergunta se quer exibir o vetor
            boolean exibirVetor = solicitarExibicaoVetor();
            
            // Gera o vetor aleatório
            System.out.println("\nGerando vetor aleatório de tamanho " + tamanho + "...");
            Integer[] vetor = gerarVetorObjetos(tamanho);
            
            // Exibe o vetor original se for pequeno e o usuário quiser
            if (exibirVetor && tamanho <= 48) {
                System.out.println("Vetor original: " + Arrays.toString(vetor));
            } else if (tamanho > 48 && exibirVetor) {
                System.out.println("(Vetor muito grande para exibição - tamanho: " + tamanho + ")");
            }
            
            System.out.println();
            
            // Executa o algoritmo escolhido
            IOrdenador<Integer> ordenador;
            String nomeAlgoritmo;
            
            switch (opcao) {
                case 1:
                    ordenador = new BubbleSort<>();
                    nomeAlgoritmo = "BubbleSort";
                    break;
                case 2:
                    ordenador = new InsertionSort<>();
                    nomeAlgoritmo = "InsertionSort";
                    break;
                case 3:
                    ordenador = new SelectionSort<>();
                    nomeAlgoritmo = "SelectionSort";
                    break;
                case 4:
                    ordenador = new MergeSort<>();
                    nomeAlgoritmo = "MergeSort";
                    break;
                default:
                    continue;
            }
            
            testarAlgoritmo(ordenador, vetor, nomeAlgoritmo, exibirVetor);
            
            continuar = solicitarNovoTeste();
        }
        
        scanner.close();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ANÁLISE DE COMPLEXIDADE DOS ALGORITMOS:");
        System.out.println("=".repeat(60));
        System.out.println("BubbleSort    - O(n²) - Muitas comparações e movimentações");
        System.out.println("InsertionSort - O(n²) - Bom para dados parcialmente ordenados");
        System.out.println("SelectionSort - O(n²) - Poucas movimentações, muitas comparações");
        System.out.println("MergeSort     - O(n log n) - Eficiente para grandes volumes de dados");
        System.out.println("=".repeat(60));
        System.out.println("💡 DICA: Para vetores grandes (> 100.000), prefira o MergeSort!");
    }
}