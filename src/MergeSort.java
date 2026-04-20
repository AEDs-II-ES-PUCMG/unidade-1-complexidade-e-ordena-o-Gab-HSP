import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements IOrdenador<T> {

    private int comparacoes;
    private int movimentacoes;
    private double tempoOrdenacao;
    private double inicio;
    private T[] vetorAuxiliar; // Vetor auxiliar para evitar recriação a cada intercalação

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

    /**
     * Algoritmo de ordenação Mergesort.
     * @param array Array a ser ordenado
     * @param esq Início do array a ser ordenado
     * @param dir Fim do array a ser ordenado
     */
    private void mergesort(T[] array, int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir);
        }
    }

    /**
     * Algoritmo que intercala os elementos localizados entre as posições esq e dir
     * Versão adaptada para tipos genéricos usando vetor auxiliar global
     * @param array Array a ser intercalado
     * @param esq Início do array a ser ordenado
     * @param meio Posição do meio do array a ser ordenado
     * @param dir Fim do array a ser ordenado
     */
    private void intercalar(T[] array, int esq, int meio, int dir) {
        int i = esq;
        int j = meio + 1;
        int k = esq;
        
        // Copia os elementos para o vetor auxiliar
        for (int pos = esq; pos <= dir; pos++) {
            vetorAuxiliar[pos] = array[pos];
            movimentacoes++; // Conta cada cópia para o vetor auxiliar
        }
        
        // Intercalação propriamente dita
        while (i <= meio && j <= dir) {
            comparacoes++; // Conta a comparação
            if (vetorAuxiliar[i].compareTo(vetorAuxiliar[j]) <= 0) {
                array[k] = vetorAuxiliar[i];
                i++;
                movimentacoes++; // Conta a movimentação
            } else {
                array[k] = vetorAuxiliar[j];
                j++;
                movimentacoes++; // Conta a movimentação
            }
            k++;
        }
        
        // Copia os elementos restantes da primeira metade (se houver)
        while (i <= meio) {
            array[k] = vetorAuxiliar[i];
            i++;
            k++;
            movimentacoes++; // Conta a movimentação
        }
        
        // Copia os elementos restantes da segunda metade (se houver)
        while (j <= dir) {
            array[k] = vetorAuxiliar[j];
            j++;
            k++;
            movimentacoes++; // Conta a movimentação
        }
    }

    @Override
    public T[] ordenar(T[] dados) {
        // Cria uma cópia do vetor original para não modificá-lo
        T[] dadosOrdenados = Arrays.copyOf(dados, dados.length);
        int tamanho = dadosOrdenados.length;
        
        // Inicializa o vetor auxiliar com o mesmo tamanho
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[tamanho];
        this.vetorAuxiliar = aux;
        
        // Inicia a contagem e o cronômetro
        iniciar();
        
        // Chama o mergesort recursivo
        mergesort(dadosOrdenados, 0, tamanho - 1);
        
        // Finaliza o cronômetro
        terminar();
        
        return dadosOrdenados;
    }
}