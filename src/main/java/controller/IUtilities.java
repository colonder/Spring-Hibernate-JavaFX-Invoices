package controller;

import java.util.List;

public interface IUtilities<T>
{
    List<T> convertIterableToCollection(Iterable<T> iterable);
}
