package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

public class MultiplicativeExpressionAutomata extends ParserAutomataImpl {

    public MultiplicativeExpressionAutomata() {
        super(Rule.MULTIPLICATIVE_EXPRESSION, new InitialState());
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
                        public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
                            ImmutableStack.PopResult<ASTNode> popResult = stack.pop();
                            ASTNode left = popResult.getElement();
                            ImmutableStack<ASTNode> newStack =
                                    popResult.getStack()
                                            .push(new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, left));
                            return new StateChangeImpl(
                                    new InnerAutomataState(new PrimaryExpressionAutomata(), DivideOrMultiplyState::new, (s) -> {
                                        ImmutableStack.PopResult<ASTNode> popResult1 = s.pop();
                                        ImmutableStack.PopResult<ASTNode> popResult2 = popResult1.getStack().pop();
                                        ASTNode r = popResult1.getElement();
                                        ASTNode l = popResult2.getElement();
                                        return popResult2.getStack().push(new NonTerminalNode(Rule.MULTIPLY_EXPRESSION, l, r));
                                    }),
                                    newStack
                            );
                        }
                    },
                    new Transition() {
                        @Override
                        public boolean consumes(Token token) {
                            return token.getType() == TokenType.FORWARD_SLASH;
                        }

                        @Override
                        public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
                            ImmutableStack.PopResult<ASTNode> popResult = stack.pop();
                            ASTNode left = popResult.getElement();
                            ImmutableStack<ASTNode> newStack =
                                    popResult.getStack()
                                            .push(new NonTerminalNode(Rule.MULTIPLICATIVE_EXPRESSION, left));
                            return new StateChangeImpl(
                                    new InnerAutomataState(new PrimaryExpressionAutomata(), DivideOrMultiplyState::new, (s) -> {
                                        ImmutableStack.PopResult<ASTNode> popResult1 = s.pop();
                                        ImmutableStack.PopResult<ASTNode> popResult2 = popResult1.getStack().pop();
                                        ASTNode r = popResult1.getElement();
                                        ASTNode l = popResult2.getElement();
                                        return popResult2.getStack().push(new NonTerminalNode(Rule.DIVIDE_EXPRESSION, l, r));
                                    }),
                                    newStack
                            );
                        }
                    }
            );
        }
    }
}
