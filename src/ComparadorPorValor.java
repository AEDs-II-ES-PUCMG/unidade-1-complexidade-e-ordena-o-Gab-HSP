import java.util.Comparator;

public class ComparadorPorValor implements Comparator<Pedido>{

	@Override
	public int compare(Pedido o1, Pedido o2) {
		if (o1.valorFinal().equals(o2.valorFinal())) {
    		return o1;
    	} else if (o1.valorFinal().isBefore(o2.valorFinal())) {
        	return -1;
        } else {
        	return 1;
        }
	}
}
