import java.util.Comparator;

/**
 * Critério B - Forma de Pagamento (crescente: à vista primeiro, depois parcelado).
 * Desempate 1: Valor Final do Pedido (crescente).
 * Desempate 2: Código Identificador do pedido (crescente).
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        // Compara pela Forma de Pagamento (1 = à vista, 2 = parcelado)
        // À vista (1) vem antes de parcelado (2)
        int forma1 = o1.getFormaDePagamento();
        int forma2 = o2.getFormaDePagamento();
        
        if (forma1 != forma2) {
            return Integer.compare(forma1, forma2);
        }
        
        // Desempate 1: Valor Final do Pedido (crescente)
        double valor1 = o1.valorFinal();
        double valor2 = o2.valorFinal();
        
        if (Math.abs(valor1 - valor2) > 0.0001) {
            return Double.compare(valor1, valor2);
        }
        
        // Desempate 2: Código Identificador do pedido (crescente)
        return Integer.compare(o1.getIdPedido(), o2.getIdPedido());
    }
}