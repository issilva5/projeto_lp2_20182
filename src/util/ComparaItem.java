package util;

import java.util.Comparator;
import model.Item;

/**
 * Implementar um comparator para a classe Item.
 * Compara a quantidade, e em caso de empate a ordem alfabética da descrição.
 */
public class ComparaItem implements Comparator<Item> {

	@Override
	public int compare(Item i, Item j) {
		if(i.getQuantidade() == j.getQuantidade()) {
			return i.getDescritor().compareTo(j.getDescritor());
		}
		return j.getQuantidade() - i.getQuantidade();
	}

}
