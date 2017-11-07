package smrjay001.csc2003s.hideandseek.util;

import java.util.Comparator;

public class CostComparator implements Comparator {

	CostComparator() {
	}

	private int compare(float cost1, float cost2) {
		if (cost1 == cost2) {
			return 0;
		}
		return (cost1 > cost2) ? 1 : -1;
	}

	@Override
	public int compare(Object o1, Object o2) {
		assert (o1.getClass() == Node.class);
		assert (o1.getClass() == Node.class);
		return compare(((Node) o1).getCost(), ((Node)o2).getCost());

	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}
}
