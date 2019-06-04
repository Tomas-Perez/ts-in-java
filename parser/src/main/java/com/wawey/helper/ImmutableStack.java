package com.wawey.helper;

import java.util.List;

/**
 * @author Tomas Perez Molina
 */
public interface ImmutableStack<T> extends Iterable<T>{
    ImmutableStack<T> push(T t);
    T peek();
    PopResult<T> pop();
    boolean isEmpty();
    List<T> toList();

    class PopResult<T> {
        private ImmutableStack<T> stack;
        private T element;

        PopResult(ImmutableStack<T> stack, T element) {
            this.stack = stack;
            this.element = element;
        }

        public ImmutableStack<T> getStack() {
            return stack;
        }

        public T getElement() {
            return element;
        }
    }
}
