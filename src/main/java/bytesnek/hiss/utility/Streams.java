package bytesnek.hiss.utility;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public class Streams
{
    /**
     * Creates a new stream from a collection
     *
     * @param collection The collection to stream
     * @param function   The collector function
     * @return A new stream of {@link T}
     **/
    public static <T> Stream<T> newCollectionStream(Collection<T> collection, IntFunction<T[]> function)
    {
        return Arrays.stream(collection.toArray(function));
    }

    /**
     * Creates a new stream from a collection
     *
     * @param collection The collection to stream
     * @param mapper     The collection mapper
     * @param function   The collector function
     * @return A new stream of {@link T}
     **/
    public static <T> Stream<T> newCollectionStream(Collection<T> collection, Function<T, ?> mapper, IntFunction<T[]> function)
    {
        return Arrays.stream(collection.stream().map(mapper).toArray(function));
    }

    /**
     * Creates a new concurrent friendly stream from a collection
     *
     * @param collection The collection to stream
     * @param function   The collector function
     * @return A new stream of {@link T}
     **/
    public static <T> Stream<T> newConcurrentCollectionStream(Collection<T> collection, IntFunction<T[]> function)
    {
        return Arrays.stream(new CopyOnWriteArrayList<>(collection).toArray(function));
    }

    /**
     * Creates a new concurrent friendly stream from a collection
     *
     * @param collection The collection to stream
     * @param mapper     The collection mapper
     * @param function   The collector function
     * @return A new stream of {@link T}
     **/
    public static <T> Stream<T> newConcurrentCollectionStream(Collection<T> collection, Function<T, ?> mapper, IntFunction<T[]> function)
    {
        return Arrays.stream(new CopyOnWriteArrayList<>(collection).stream().map(mapper).toArray(function));
    }
}
