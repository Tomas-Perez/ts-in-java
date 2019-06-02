package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

public class MultiplicativeExpressionAutomata extends ParserAutomataImpl {

    public MultiplicativeExpressionAutomata() {
        super(new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, stack.peek());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new TransitionToAutomata(new PrimaryExpressionAutomata(), DivideOrMultiplyState::new)
            );
        }
    }

    private static class DivideOrMultiplyState extends TransitionState {

        public DivideOrMultiplyState() {
            super(
                    true,
                    new Transition() {
                        @Override
                        public boolean consumes(Token token) {
                            return token.getType() == TokenType.ASTERISK;
                        }

                        @Override
                        public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                            ASTNode left = stack.pop();
                            stack.push(new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, left));
                            return new InnerAutomataState(new PrimaryExpressionAutomata(), DivideOrMultiplyState::new, (s) -> {
                                ASTNode r = s.pop();
                                ASTNode l = s.pop();
                                stack.push(new NonTerminalNode(Rule.MULTIPLY_EXPRESSION, l, r));
                            });
                        }
                    },
                    new Transition() {
                        @Override
                        public boolean consumes(Token token) {
                            return token.getType() == TokenType.FORWARD_SLASH;
                        }

                        @Override
                        public ParserAutomataState nextState(Token token, Stack<ASTNode> stack) {
                            ASTNode left = stack.pop();
                            stack.push(new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, left));
                            return new InnerAutomataState(new PrimaryExpressionAutomata(), DivideOrMultiplyState::new, (s) -> {
                                ASTNode r = s.pop();
                                ASTNode l = s.pop();
                                stack.push(new NonTerminalNode(Rule.DIVIDE_EXPRESSION, l, r));
                            });
                        }
                    }
            );
        }
    }
}
