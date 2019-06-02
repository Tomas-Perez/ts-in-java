package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

public class LiteralAutomata extends ParserAutomataImpl {

    public LiteralAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.LITERAL, stack.peek());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new Transition() {
                        private ParserAutomata inner = new NumberLiteralAutomata();

                        @Override
                        public boolean consumes(Token token) {
                            return inner.accepts(token);
                        }

                        @Override
                        public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                            ParserAutomataState next = new InnerAutomataState(inner, AcceptedState::new);
                            return next.transition(token, stack);
                        }
                    },
                    new Transition() {
                        private ParserAutomata inner = new StringLiteralAutomata();

                        @Override
                        public boolean consumes(Token token) {
                            return inner.accepts(token);
                        }

                        @Override
                        public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                            ParserAutomataState next = new InnerAutomataState(inner, AcceptedState::new);
                            return next.transition(token, stack);
                        }
                    }
            );
        }
    }
}
