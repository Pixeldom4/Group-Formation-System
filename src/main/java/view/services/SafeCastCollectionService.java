package view.services;

import java.util.Collection;

/**
 * A service for safely casting objects to collections.
 */
public class SafeCastCollectionService {

    /**
     * Requires: obj is an instance of collection type provided by the supplier and contains elements of type clazz.
     * @param obj the object to cast
     * @param clazz the type of the elements in the collection
     * @param collectionSupplier the supplier for the collection
     * @return the cast object
     * @throws IllegalArgumentException if obj is not a collection
     */
    public static <T, C extends Collection<T>> C convertToCollection(Object obj, Class<T> clazz, CollectionSupplier<T, C> collectionSupplier) {
        if (!(obj instanceof Collection<?> originalCollection)) {
            throw new IllegalArgumentException("Object is not a Collection");
        }
        C resultCollection = collectionSupplier.get();
        for (Object element : originalCollection) {
            if (clazz.isInstance(element)) {
                resultCollection.add(clazz.cast(element));
            }
        }
        return resultCollection;
    }

    /**
     * A functional interface for creating collections.
     * @param <T> the type of the elements in the collection
     * @param <C> the type of the collection
     */
    @FunctionalInterface
    public interface CollectionSupplier<T, C extends Collection<T>> {
        C get();
    }

}
