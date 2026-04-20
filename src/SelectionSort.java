import java.util.Arrays;

public class SelectionSort<T extends Comparable<T>> implements IOrdenador<T> {

    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;

    private double nanoToMilli = 1.0/1_000_000;

    @Override
    public int getComparacoes() {
        return comparacoes;
    }

    @Override
    public int getMovimentacoes() {
        return movimentacoes;
    }

    @Override
    public double getTempoOrdenacao() {
        return tempoOrdenacao;
    }

    private void iniciar(){
        this.comparacoes = 0;
        this.movimentacoes = 0;
        this.inicio = System.nanoTime();
    }

    private void terminar(){
        this.tempoOrdenacao = (System.nanoTime() - this.inicio) * nanoToMilli;
    }

    private void swap(int x, int y, T[] vetor) {
        T temp = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = temp;
        movimentacoes += 3; // Cada swap conta como 3 movimentações
    }

    @Override
    public T[] ordenar(T[] dados) {
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;
        iniciar();
        
        // Algoritmo SelectionSort
        for (int i = 0; i < tamanho - 1; i++) {
            // Encontra o índice do menor elemento no restante do vetor
            int indiceMenor = i;
            for (int j = i + 1; j < tamanho; j++) {
                comparacoes++; // Conta cada comparação
                if (dadosOrdenados[j].compareTo(dadosOrdenados[indiceMenor]) < 0) {
                    indiceMenor = j;
                }
            }
            // Troca o elemento atual com o menor elemento encontrado
            if (indiceMenor != i) {
                swap(i, indiceMenor, dadosOrdenados);
            }
        }
        
        terminar();
        return dadosOrdenados;
    }
}