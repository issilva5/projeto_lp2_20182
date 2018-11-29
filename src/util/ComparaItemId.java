package util;

import java.util.Comparator;
import model.Item;

public class ComparaItemId implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {
		long aux1 = Long.parseLong(o1.getItemID());
		long aux2 = Long.parseLong(o2.getItemID());
		
		int resp = 0;
		if (aux1 > aux2) resp = 1;
		else resp = -1;
		
		return resp;
	}

}
