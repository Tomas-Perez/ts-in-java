package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

/**
 * @author Tomas Perez Molina
 */
public class StatementAutomata extends ParserAutomataImpl {
    public StatementAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.STATEMENT, stack.peek());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new Transition() {
                        ParserAutomata inner = new VariableDeclarationAutomata();

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
                        ParserAutomata inner = new PrintStatementAutomata();

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
                        ParserAutomata inner = new CoverInitializedNameAutomata();

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
