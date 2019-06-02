package com.wawey.parser.automata;

import com.wawey.lexer.Token;
import com.wawey.lexer.TokenType;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

import java.util.Stack;

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
                        public StateChange nextState(Token token, Stack<ASTNode> stack) {
                            Stack<ASTNode> copy = (Stack<ASTNode>) stack.clone();
                            ASTNode left = copy.pop();
                            copy.push(new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, left));
                            return new StateChangeImpl(
                                    new InnerAutomataState(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new, (s) -> {
                                        ASTNode r = s.pop();
                                        ASTNode l = s.pop();
                                        s.push(new NonTerminalNode(Rule.SUM_EXPRESSION, l, r));
                                    }),
                                    copy
                            );
                        }
                    },
                    new Transition() {
                        @Override
                        public boolean consumes(Token token) {
                            return token.getType() == TokenType.MINUS;
                        }

                        @Override
                        public StateChange nextState(Token token, Stack<ASTNode> stack) {
                            Stack<ASTNode> copy = (Stack<ASTNode>) stack.clone();
                            ASTNode left = copy.pop();
                            copy.push(new NonTerminalNode(Rule.ADDITIVE_EXPRESSION, left));
                            return new StateChangeImpl(
                                    new InnerAutomataState(new MultiplicativeExpressionAutomata(), AddOrSubtractState::new, (s) -> {
                                        ASTNode r = s.pop();
                                        ASTNode l = s.pop();
                                        s.push(new NonTerminalNode(Rule.SUBTRACT_EXPRESSION, l, r));
                                    }),
                                    copy
                            );
                        }
                    }
            );
        }
    }
}
