package smrjay001.csc2003s.hideandseek.util;

import com.badlogic.gdx.math.Vector2;

import java.util.Comparator;

public class ClockwiseComparator implements Comparator {

	private Vector2 origin;

	public ClockwiseComparator(Vector2 origin){
		this.origin = origin;
	}

	public int compare(Vector2 vec1, Vector2 vec2, Vector2 origin) {
		if (vec1.equals(vec2))
			return 0;

		Vector2 firstOffset = new Vector2(vec1.x - origin.x, vec1.y - origin.y);
		Vector2 secondOffset = new Vector2(vec2.x - origin.x, vec2.y - origin.y);

		float angle1 = (float) Math.atan2(firstOffset.y, firstOffset.x);
		float angle2 = (float) Math.atan2(secondOffset.y, secondOffset.x);

		if (angle1 < angle2)
			return -1;
		if (angle1 > angle2)
			return 1;

		return (firstOffset.len() < secondOffset.len()) ? 1 : -1;
	}

	@Override
	public int compare(Object o1, Object o2) {
		assert (o1.getClass() == Vector2.class);
		assert (o1.getClass() == Vector2.class);
		return compare((Vector2) o1, (Vector2)o2, origin);

	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}
}
