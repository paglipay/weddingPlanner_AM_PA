package weddingPlanner_AM_PA.util.collections;

public interface Collection<T> {
	boolean add(T element);

	boolean contains(T element);

	boolean isEmpty();

	boolean remove(T element);

	int size();
}
