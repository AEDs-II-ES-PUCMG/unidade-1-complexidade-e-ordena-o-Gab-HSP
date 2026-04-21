import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class App {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static Pedido pedido;
    static Produto[] produtosOrdenadosPorId;      // Tarefa 2 - cópia ordenada por ID
    static Produto[] produtosOrdenadosPorDescricao; // Tarefa 2 - cópia ordenada por descrição
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static IOrdenador<Produto> ordenador;

    // #region utilidades
    static Scanner teclado;

    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha (BubbleSort)");
        System.out.println("2 - Inserção (InsertionSort)");
        System.out.println("3 - Seleção (SelectionSort)");
        System.out.println("4 - Mergesort");
        System.out.println("0 - Cancelar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Ordenar por descrição (padrão)");
        System.out.println("2 - Ordenar por código (ID)");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;
    }

    /**
     * Tarefa 2: Inicializa as cópias ordenadas dos produtos
     * - produtosOrdenadosPorId: ordenado por código (hashCode/ID)
     * - produtosOrdenadosPorDescricao: ordenado por descrição (compareTo padrão)
     */
    static void inicializarCopiasOrdenadas() {
        if (produtos == null) return;
        
        // Cria cópias dos produtos originais
        produtosOrdenadosPorId = Arrays.copyOf(produtos, quantProdutos);
        produtosOrdenadosPorDescricao = Arrays.copyOf(produtos, quantProdutos);
        
        // Ordena por ID (hashCode)
        Arrays.sort(produtosOrdenadosPorId, (p1, p2) -> Integer.compare(p1.hashCode(), p2.hashCode()));
        
        // Ordena por descrição (usando o compareTo padrão de Produto)
        Arrays.sort(produtosOrdenadosPorDescricao);
        
        System.out.println("Copias ordenadas inicializadas com sucesso!");
        System.out.println("  - " + quantProdutos + " produtos ordenados por ID");
        System.out.println("  - " + quantProdutos + " produtos ordenados por descrição");
    }

    /**
     * Tarefa 2: Localizar produto usando busca binária por ID
     * @param id Identificador do produto
     * @return Produto encontrado ou null se não existir
     */
    static Produto localizarProdutoPorIdBinario(int id) {
        if (produtosOrdenadosPorId == null) return null;
        
        int esquerda = 0;
        int direita = quantProdutos - 1;
        
        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;
            int idMeio = produtosOrdenadosPorId[meio].hashCode();
            
            if (idMeio == id) {
                return produtosOrdenadosPorId[meio];
            } else if (idMeio < id) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }
        return null;
    }

    /**
     * Tarefa 2: Localizar produto usando busca binária por descrição
     * @param descricao Descrição do produto
     * @return Produto encontrado ou null se não existir
     */
    static Produto localizarProdutoPorDescricaoBinario(String descricao) {
        if (produtosOrdenadosPorDescricao == null) return null;
        
        int esquerda = 0;
        int direita = quantProdutos - 1;
        
        while (esquerda <= direita) {
            int meio = (esquerda + direita) / 2;
            int comparacao = produtosOrdenadosPorDescricao[meio].getDescricao().compareToIgnoreCase(descricao);
            
            if (comparacao == 0) {
                return produtosOrdenadosPorDescricao[meio];
            } else if (comparacao < 0) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }
        return null;
    }

    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Localizando um produto");
        System.out.println("Como deseja buscar?");
        System.out.println("1 - Por ID (busca binária - mais rápida)");
        System.out.println("2 - Por descrição (busca binária)");
        System.out.println("3 - Busca sequencial tradicional");
        
        int tipoBusca = lerNumero("Digite sua opção", Integer.class);
        
        if (tipoBusca == 1) {
            int numero = lerNumero("Digite o identificador do produto", Integer.class);
            Produto localizado = localizarProdutoPorIdBinario(numero);
            if (localizado == null) {
                System.out.println("Produto não encontrado!");
            }
            return localizado;
        } else if (tipoBusca == 2) {
            System.out.print("Digite a descrição do produto: ");
            String descricao = teclado.nextLine();
            Produto localizado = localizarProdutoPorDescricaoBinario(descricao);
            if (localizado == null) {
                System.out.println("Produto não encontrado!");
            }
            return localizado;
        } else {
            // Busca sequencial original (fallback)
            int numero = lerNumero("Digite o identificador do produto", Integer.class);
            Produto localizado = null;
            for (int i = 0; i < quantProdutos && localizado == null; i++) {
                if (produtos[i].hashCode() == numero)
                    localizado = produtos[i];
            }
            return localizado;
        }
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    /**
     * Tarefa 1: Método completo para ordenar produtos
     * Permite ao usuário escolher o algoritmo e o critério de ordenação
     */
    static void ordenarProdutos(){
        cabecalho();
        
        // Escolhe o algoritmo de ordenação
        int opcaoAlgoritmo = exibirMenuOrdenadores();
        if (opcaoAlgoritmo == 0) {
            System.out.println("Operação cancelada.");
            return;
        }
        
        // Escolhe o critério de comparação
        int opcaoComparador = exibirMenuComparadores();
        
        // Instancia o algoritmo escolhido
        switch (opcaoAlgoritmo) {
            case 1:
                ordenador = new BubbleSort<>();
                System.out.println("Algoritmo escolhido: BubbleSort (Bolha)");
                break;
            case 2:
                ordenador = new InsertSort<>();
                System.out.println("Algoritmo escolhido: InsertionSort (Inserção)");
                break;
            case 3:
                ordenador = new SelectionSort<>();
                System.out.println("Algoritmo escolhido: SelectionSort (Seleção)");
                break;
            case 4:
                ordenador = new Mergesort<>();
                System.out.println("Algoritmo escolhido: MergeSort");
                break;
            default:
                System.out.println("Opção inválida!");
                return;
        }
        
        // Define o comparador baseado na escolha do usuário
        Produto[] produtosOrdenados;
        long comparacoes, movimentacoes;
        double tempo;
        
        if (opcaoComparador == 1) {
            // Ordenar por descrição (padrão - usando compareTo de Produto)
            System.out.println("Critério: Ordenar por DESCRIÇÃO");
            produtosOrdenados = ordenador.ordenar(produtos);
        } else if (opcaoComparador == 2) {
            // Ordenar por ID (código do produto)
            System.out.println("Critério: Ordenar por CÓDIGO (ID)");
            produtosOrdenados = ordenador.ordenar(produtos, (p1, p2) -> Integer.compare(p1.hashCode(), p2.hashCode()));
        } else {
            System.out.println("Opção inválida!");
            return;
        }
        
        // Obtém as estatísticas
        comparacoes = ordenador.getComparacoes();
        movimentacoes = ordenador.getMovimentacoes();
        tempo = ordenador.getTempoOrdenacao();
        
        // Exibe os resultados
        System.out.println("\n--- ESTATÍSTICAS DA ORDENAÇÃO ---");
        System.out.println("Comparações: " + comparacoes);
        System.out.println("Movimentações: " + movimentacoes);
        System.out.printf("Tempo de ordenação: %.3f ms\n", tempo);
        
        // Pergunta se deseja substituir o array original
        verificarSubstituicao(produtos, produtosOrdenados);
        
        // Atualiza as cópias ordenadas após possível substituição
        inicializarCopiasOrdenadas();
    }

    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
        System.out.println("Produtos embaralhados!");
        // Após embaralhar, as cópias ordenadas ficam desatualizadas
        System.out.println("ATENÇÃO: As cópias ordenadas foram desatualizadas. Reordenando...");
        inicializarCopiasOrdenadas();
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S")) {
            for (int i = 0; i < quantProdutos; i++) {
                dadosOriginais[i] = copiaDados[i];
            }
            System.out.println("Dados originais substituídos!");
        }
    }

    static void listarProdutos(){
        cabecalho();
        System.out.println("LISTA DE PRODUTOS:");
        System.out.println("----------------------------------------");
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
        System.out.println("----------------------------------------");
        System.out.println("Total de produtos: " + quantProdutos);
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        
        if (produtos == null || quantProdutos == 0) {
            System.out.println("Erro ao carregar produtos. Encerrando...");
            return;
        }
        
        // Tarefa 2: Inicializa as cópias ordenadas ao carregar os dados
        inicializarCopiasOrdenadas();
        
        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }

    public static void ordenarPedidos() {
        cabecalho();
        
        // Escolhe o algoritmo de ordenação
        int opcaoAlgoritmo = exibirMenuOrdenadores();
        if (opcaoAlgoritmo == 0) {
            System.out.println("Operação cancelada.");
            return;
        }
        
        // Escolhe o critério de comparação
        int opcaoComparador = exibirMenuComparadores();
        
        // Instancia o algoritmo escolhido
        switch (opcaoAlgoritmo) {
            case 1:
                ordenador = new BubbleSort<>();
                System.out.println("Algoritmo escolhido: BubbleSort (Bolha)");
                break;
            case 2:
                ordenador = new InsertSort<>();
                System.out.println("Algoritmo escolhido: InsertionSort (Inserção)");
                break;
            case 3:
                ordenador = new SelectionSort<>();
                System.out.println("Algoritmo escolhido: SelectionSort (Seleção)");
                break;
            case 4:
                ordenador = new Mergesort<>();
                System.out.println("Algoritmo escolhido: MergeSort");
                break;
            case 5:
                ordenador = new Heapsort<>();
                System.out.println("Algoritmo escolhido: HeapSort");
                break;
            default:
                System.out.println("Opção inválida!");
                return;
        }
        
        // Define o comparador baseado na escolha do usuário
        if (opcaoComparador == 1) {
            // Ordenar pelo valor final do pedido
            System.out.println("Critério: Ordenar por pelo valor final do pedido");
            Pedido pedidoOrdenado = ComparadorCriterioA.compare(pedido);
        } else if (opcaoComparador == 2) {
            // Ordenar pela forma de pagamento
            System.out.println("Critério: Ordenar pela forma de pagamento");
            
        } else {
            System.out.println("Opção inválida!");
            return;
        }
    }
}