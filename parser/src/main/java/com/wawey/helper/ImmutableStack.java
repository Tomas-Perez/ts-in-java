package com.wawey.helper;

/**
 * @author Tomas Perez Molina
 */
public interface ImmutableStack<T> extends Iterable<T>{
    ImmutableStack<T> push(T t);
    T peek();
    PopResult<T> pop();
    boolean isEmpty();

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
