import java.util.Comparator;

/**
 * Critério C - Ticket Médio por Variedade de Produtos (decrescente).
 * O ticket médio é a razão entre o Valor Final do Pedido e a quantidade de produtos distintos.
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */
public class ComparadorCriterioC implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        double ticketMedio1 = o1.valorFinal() / o1.getQuantosProdutos();
        double ticketMedio2 = o2.valorFinal() / o2.getQuantosProdutos();
        
        if (Math.abs(ticketMedio1 - ticketMedio2) > 0.0001) {
            return Double.compare(ticketMedio2, ticketMedio1);
        }
        
        double valor1 = o1.valorFinal();
        double valor2 = o2.valorFinal();
        
        if (Math.abs(valor1 - valor2) > 0.0001) {
            return Double.compare(valor1, valor2);
        }
        
        return Integer.compare(o1.getIdPedido(), o2.getIdPedido());
    }
}