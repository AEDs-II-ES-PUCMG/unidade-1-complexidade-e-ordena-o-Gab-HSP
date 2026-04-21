import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */
public class ComparadorCriterioA implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        double valor1 = o1.valorFinal();
        double valor2 = o2.valorFinal();
        
        if (Math.abs(valor1 - valor2) > 0.0001) {
            return Double.compare(valor1, valor2);
        }
        
        int totalItens1 = o1.getTotalItens();
        int totalItens2 = o2.getTotalItens();
        
        if (totalItens1 != totalItens2) {
            return Integer.compare(totalItens1, totalItens2);
        }
        
        return Integer.compare(o1.getIdPrimeiroProduto(), o2.getIdPrimeiroProduto());
    }
}