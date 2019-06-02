package com.wawey.parser.automata;

import com.wawey.lexer.NoTransitionException;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class AdditiveExpressionAutomata extends ParserAutomataImpl {

    public AdditiveExpressionAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, stack.peek());
    }

    private static class InitialState implements ParserAutomataState {
        private List<Transition> transitions = Collections.singletonList(
                new Transition() {
                    private ParserAutomata inner = new MultiplicativeExpressionAutomata();

                    @Override
                    public boolean consumes(Token token) {
                        return inner.accepts(token);
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ParserAutomataState next = new InnerAutomataState(inner, AddOrSubtractState::new);
                        return next.transition(token, stack);
                    }
                }
        );

        @Override
        public boolean accepts(Token token) {
            return transitions.stream().anyMatch(t -> t.consumes(token));
        }

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            return transitions.stream()
                    .filter(t -> t.consumes(token))
                    .findFirst()
                    .map(t -> t.nextState(token, stack))
                    .orElseThrow(NoTransitionException::new);
        }

        @Override
        public boolean isAcceptable() {
            return false;
        }
    }

    private static class AddOrSubtractState implements ParserAutomataState {
        private List<Transition> transitions = Arrays.asList(
                new Transition() {
                    @Override
                    public boolean consumes(Token token) {
                        return token.getType() == TokenType.PLUS;
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ASTNode left = stack.pop();
                        stack.push(new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, left));
                        return new InnerAutomataState(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new, (s) -> {
                            ASTNode r = s.pop();
                            ASTNode l = s.pop();
                            stack.push(new NonTerminalNode(Rule.SUM_EXPRESSION, l, r));
                        });
                    }
                },
                new Transition() {
                    @Override
                    public boolean consumes(Token token) {
                        return token.getType() == TokenType.MINUS;
                    }

                    @Override
                    public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                        ASTNode left = stack.pop();
                        stack.push(new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, left));
                        return new InnerAutomataState(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new, (s) -> {
                            ASTNode r = s.pop();
                            ASTNode l = s.pop();
                            stack.push(new NonTerminalNode(Rule.SUBTRACT_EXPRESSION, l, r));
                        });
                    }
                }
        );

        @Override
        public ParserAutomataState transition(Token token, Stack<ASTNode> stack) {
            return transitions.stream()
                    .filter(t -> t.consumes(token))
                    .findFirst()
                    .map(t -> t.nextState(token, stack))
                    .orElseThrow(NoTransitionException::new);
        }

        @Override
        public boolean isAcceptable() {
            return true;
        }

        @Override
        public boolean accepts(Token token) {
            return transitions.stream().anyMatch(t -> t.consumes(token));
        }
    }
}
