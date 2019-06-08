package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

public class AdditiveExpressionAutomata extends ParserAutomataImpl {

    public AdditiveExpressionAutomata() {
        super(Rule.ADDITIVE_EXPRESSION, new InitialState());
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(new TransitionToAutomata(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new));
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
                        public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
                            ImmutableStack.PopResult<ASTNode> popResult = stack.pop();
                            ASTNode left = popResult.getElement();
                            ImmutableStack<ASTNode> newStack =
                                    popResult.getStack()
                                            .push(new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, left));
                            return new StateChangeImpl(
                                    new InnerAutomataState(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new, (s) -> {
                                        ImmutableStack.PopResult<ASTNode> popResult1 = s.pop();
                                        ImmutableStack.PopResult<ASTNode> popResult2 = popResult1.getStack().pop();
                                        ASTNode r = popResult1.getElement();
                                        ASTNode l = popResult2.getElement();
                                        return popResult2.getStack().push(new NonTerminalNode(Rule.SUM_EXPRESSION, l, r));
                                    }),
                                    newStack
                            );
                        }
                    },
                    new Transition() {
                        @Override
                        public boolean consumes(Token token) {
                            return token.getType() == TokenType.MINUS;
                        }

                        @Override
                        public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
                            ImmutableStack.PopResult<ASTNode> popResult = stack.pop();
                            ASTNode left = popResult.getElement();
                            ImmutableStack<ASTNode> newStack =
                                    popResult.getStack()
                                            .push(new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, left));
                            return new StateChangeImpl(
                                    new InnerAutomataState(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new, (s) -> {
                                        ImmutableStack.PopResult<ASTNode> popResult1 = s.pop();
                                        ImmutableStack.PopResult<ASTNode> popResult2 = popResult1.getStack().pop();
                                        ASTNode r = popResult1.getElement();
                                        ASTNode l = popResult2.getElement();
                                        return popResult2.getStack().push(new NonTerminalNode(Rule.SUBTRACT_EXPRESSION, l, r));
                                    }),
                                    newStack
                            );
                        }
                    }
            );
        }
    }
}
