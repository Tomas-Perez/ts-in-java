package com.wawey.helper;

import org.junit.Test;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Tomas Perez Molina
 */
public class LinkedImmutableStackTest {
    @Test
    public void emptyConstShouldStartEmpty() {
        ImmutableStack stack = LinkedImmutableStack.empty();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void constWithValuesShouldNotStartEmpty() {
        ImmutableStack stack = LinkedImmutableStack.of(1, 2, 3);
        assertFalse(stack.isEmpty());
    }

    @Test(expected = EmptyStackException.class)
    public void shouldThrowEmptyExcWhenPoppingEmptyStack() {
        ImmutableStack stack = LinkedImmutableStack.empty();
        stack.pop();
    }

    @Test
    public void shouldPopAValueAfterPushingIt() {
        ImmutableStack<Integer> stack = LinkedImmutableStack.empty();
        ImmutableStack<Integer> afterPush = stack.push(5);
        assertTrue(stack.isEmpty());
        assertFalse(afterPush.isEmpty());
        assertEquals(new Integer(5), afterPush.pop().getElement());
    }

    @Test
    public void shouldPeekAValueAfterPushingIt() {
        ImmutableStack<Integer> stack = LinkedImmutableStack.empty();
        ImmutableStack<Integer> afterPush = stack.push(5);
        assertTrue(stack.isEmpty());
        assertFalse(afterPush.isEmpty());
        assertEquals(new Integer(5), afterPush.peek());
    }

    @Test
    public void shouldBeEmptyAfterPoppingOnlyValue() {
        ImmutableStack<Integer> stack = LinkedImmutableStack.empty();
        ImmutableStack<Integer> afterPush = stack.push(5);
        assertFalse(afterPush.isEmpty());
        ImmutableStack<Integer> afterPop = afterPush.pop().getStack();
        assertTrue(afterPop.isEmpty());
    }

    @Test
    public void shouldIterateOverItemsInTheirPushedOrder() {
        List<Integer> expected = Arrays.asList(1, 2, 3);
        List<Integer> result = new LinkedList<>();
        ImmutableStack<Integer> stack1 = LinkedImmutableStack.empty();
        ImmutableStack<Integer> stack2 = stack1.push(1);
        ImmutableStack<Integer> stack3 = stack2.push(2);
        ImmutableStack<Integer> stack4 = stack3.push(3);
        for (Integer a : stack4) {
            result.add(a);
        }
        assertEquals(expected, result);
    }
}
