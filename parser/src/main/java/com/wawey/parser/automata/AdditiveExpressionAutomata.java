package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

public class AdditiveExpressionAutomata extends ParserAutomataImpl {

    public AdditiveExpressionAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, stack.peek());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
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
        }
    }

    private static class AddOrSubtractState extends TransitionState {
        public AddOrSubtractState() {
            super(
                    true,
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
        }
    }
}
