import java.util.Arrays;
import java.util.Random;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
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
     */
    static void testarAlgoritmo(IOrdenador<Integer> ordenador, Integer[] vetorOriginal, String nome) {
        Integer[] vetorCopia = vetorOriginal.clone();
        Integer[] vetorOrdenado = ordenador.ordenar(vetorCopia);
        
        System.out.println("\n--- " + nome + " ---");
        System.out.println("Comparações: " + ordenador.getComparacoes());
        System.out.println("Movimentações: " + ordenador.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + String.format("%.3f", ordenador.getTempoOrdenacao()));
        
        // Verifica se o vetor foi ordenado corretamente (apenas para vetores pequenos)
        if (vetorOriginal.length <= 48) {
            System.out.println("Vetor ordenado: " + Arrays.toString(vetorOrdenado));
        }
    }

    /**
     * Método para testar todos os algoritmos com diferentes tamanhos de vetor
     * @param tamanhos Array com os tamanhos a serem testados
     * @param nomeGrupo Nome do grupo de teste (Pequeno, Médio, Grande)
     */
    static void testarComTamanhos(int[] tamanhos, String nomeGrupo) {
        System.out.println("\n\n" + "=".repeat(80));
        System.out.println("TESTES COM VETORES " + nomeGrupo.toUpperCase());
        System.out.println("=".repeat(80));
        
        for (int tam : tamanhos) {
            System.out.println("\n" + "-".repeat(80));
            System.out.println(">> Tamanho do vetor: " + tam);
            System.out.println("-".repeat(80));
            
            // Gera um vetor aleatório para este tamanho
            Integer[] vetor = gerarVetorObjetos(tam);
            
            // Exibe o vetor original apenas para tamanhos pequenos
            if (tam <= 48) {
                System.out.println("\nVetor original: " + Arrays.toString(vetor));
            }
            
            // Testa os três algoritmos
            BubbleSort<Integer> bubbleSort = new BubbleSort<>();
            InsertionSort<Integer> insertionSort = new InsertionSort<>();
            SelectionSort<Integer> selectionSort = new SelectionSort<>();
            
            testarAlgoritmo(bubbleSort, vetor, "BubbleSort");
            testarAlgoritmo(insertionSort, vetor, "InsertionSort");
            testarAlgoritmo(selectionSort, vetor, "SelectionSort");
            
            // Tabela comparativa para cada tamanho
            System.out.println("\n--- RESUMO COMPARATIVO (Tamanho: " + tam + ") ---");
            System.out.println("Algoritmo     | Comparações | Movimentações | Tempo (ms)");
            System.out.println("--------------|-------------|---------------|------------");
            System.out.printf("BubbleSort    | %11d | %13d | %10.3f\n", 
                              bubbleSort.getComparacoes(), bubbleSort.getMovimentacoes(), bubbleSort.getTempoOrdenacao());
            System.out.printf("InsertionSort | %11d | %13d | %10.3f\n", 
                              insertionSort.getComparacoes(), insertionSort.getMovimentacoes(), insertionSort.getTempoOrdenacao());
            System.out.printf("SelectionSort | %11d | %13d | %10.3f\n", 
                              selectionSort.getComparacoes(), selectionSort.getMovimentacoes(), selectionSort.getTempoOrdenacao());
        }
    }


    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE TESTE DE ALGORITMOS DE ORDENAÇÃO ===");
        System.out.println("Algoritmos disponíveis: BubbleSort, InsertionSort, SelectionSort");
        
        // ========== TESTE INICIAL COM VETOR PEQUENO (Tamanho 20) ==========
        System.out.println("\n\n" + "=".repeat(80));
        System.out.println("TESTE INICIAL COM VETOR DE TAMANHO 20");
        System.out.println("=".repeat(80));
        
        int tam = 20;
        Integer[] vetor = gerarVetorObjetos(tam);
        
        System.out.println("\nVetor original: " + Arrays.toString(vetor));
        
        BubbleSort<Integer> bolha = new BubbleSort<>();
        InsertionSort<Integer> insercao = new InsertionSort<>();
        SelectionSort<Integer> selecao = new SelectionSort<>();
        
        testarAlgoritmo(bolha, vetor, "BubbleSort");
        testarAlgoritmo(insercao, vetor, "InsertionSort");
        testarAlgoritmo(selecao, vetor, "SelectionSort");
        
        // Tabela comparativa do teste inicial
        System.out.println("\n--- RESUMO COMPARATIVO (Tamanho: " + tam + ") ---");
        System.out.println("Algoritmo     | Comparações | Movimentações | Tempo (ms)");
        System.out.println("--------------|-------------|---------------|------------");
        System.out.printf("BubbleSort    | %11d | %13d | %10.3f\n", 
                          bolha.getComparacoes(), bolha.getMovimentacoes(), bolha.getTempoOrdenacao());
        System.out.printf("InsertionSort | %11d | %13d | %10.3f\n", 
                          insercao.getComparacoes(), insercao.getMovimentacoes(), insercao.getTempoOrdenacao());
        System.out.printf("SelectionSort | %11d | %13d | %10.3f\n", 
                          selecao.getComparacoes(), selecao.getMovimentacoes(), selecao.getTempoOrdenacao());
        
        // ========== TESTES COM DIFERENTES TAMANHOS ==========
        
        // Teste com vetores pequenos (3, 6, 12, 24, 48)
        testarComTamanhos(tamanhosTestePequeno, "Pequenos");
        
        // Teste com vetores médios (12.500, 25.000, 50.000, 100.000, 200.000)
        // ATENÇÃO: Para vetores médios, o BubbleSort pode ser muito lento!
        // Descomente a linha abaixo se quiser testar (pode levar vários minutos)
        // testarComTamanhos(tamanhosTesteMedio, "Médios");
        
        // Teste com vetores grandes (31.250.000, 62.500.000, 125.000.000, 250.000.000, 500.000.000)
        // ATENÇÃO: Para vetores grandes, apenas algoritmos eficientes devem ser usados!
        // BubbleSort e InsertionSort são O(n²) - podem levar horas ou dias!
        // Descomente a linha abaixo apenas se tiver tempo e memória suficiente
        // testarComTamanhos(tamanhosTesteGrande, "Grandes");
        
        System.out.println("\n\n" + "=".repeat(80));
        System.out.println("FIM DOS TESTES");
        System.out.println("=".repeat(80));
        
        // ========== ANÁLISE TEÓRICA ESPERADA ==========
        System.out.println("\n=== ANÁLISE TEÓRICA DOS ALGORITMOS ===");
        System.out.println("BubbleSort:");
        System.out.println("  - Melhor caso: O(n)    | Pior caso: O(n²)");
        System.out.println("  - Comparações: ~n²/2   | Movimentações: ~n²/2");
        System.out.println();
        System.out.println("InsertionSort:");
        System.out.println("  - Melhor caso: O(n)    | Pior caso: O(n²)");
        System.out.println("  - Comparações: ~n²/4   | Movimentações: ~n²/4");
        System.out.println();
        System.out.println("SelectionSort:");
        System.out.println("  - Melhor caso: O(n²)   | Pior caso: O(n²)");
        System.out.println("  - Comparações: ~n²/2   | Movimentações: ~n");
        System.out.println();
        System.out.println("OBSERVAÇÕES:");
        System.out.println("  - SelectionSort tem MENOS movimentações que os outros");
        System.out.println("  - InsertionSort é melhor para dados parcialmente ordenados");
        System.out.println("  - BubbleSort geralmente é o pior dos três");
    }
}