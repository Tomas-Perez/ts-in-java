package com.wawey.helper;

import fj.data.List;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Tomas Perez Molina
 */
public class LinkedImmutableStack<T> implements ImmutableStack<T> {
    private final List<T> list;

    private LinkedImmutableStack(List<T> list) {
        this.list = list;
    }

    @Override
    public T peek() {
        if (list.isEmpty()) throw new EmptyStackException();
        return list.head();
    }

    @Override
    public ImmutableStack<T> push(T t) {
        return new LinkedImmutableStack<>(List.cons(t, list));
    }

    @Override
    public PopResult<T> pop() {
        if (list.isEmpty()) throw new EmptyStackException();
        return new PopResult<>(
                new LinkedImmutableStack<>(list.tail()),
                list.head()
        );
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.reverse().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedImmutableStack)) return false;
        LinkedImmutableStack<?> that = (LinkedImmutableStack<?>) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    public static <T> ImmutableStack<T> empty() {
        return new LinkedImmutableStack<>(List.nil());
    }

    public static <T> ImmutableStack<T> of(T... ts) {
        return new LinkedImmutableStack<>(List.list(ts));
    }
}
