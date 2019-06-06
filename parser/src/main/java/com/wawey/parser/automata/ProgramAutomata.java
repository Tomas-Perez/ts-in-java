package com.wawey.parser.automata;

import com.wawey.helper.ImmutableStack;
import com.wawey.lexer.Token;
import com.wawey.parser.Rule;
import com.wawey.parser.ast.ASTNode;
import com.wawey.parser.ast.NonTerminalNode;

/**
 * @author Tomas Perez Molina
 */
public class ProgramAutomata extends ParserAutomataImpl {
    public ProgramAutomata() {
        super(Rule.PROGRAM, new InitialState());
    }

    @Override
    public ASTNode getResult() {
        return stack.peek();
    }

    private static class InitialState extends TransitionState {
        public InitialState() {
            super(
                    new Transition() {
                        ParserAutomata inner = new LineAutomata();

                        @Override
                        public boolean consumes(Token token) {
                            return inner.accepts(token);
                        }

                        @Override
                        public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
                            ParserAutomataState next = new InnerAutomataState(inner, GotLineState::new, (s) -> {
                                ImmutableStack.PopResult<ASTNode> popResult = s.pop();
                                ASTNode line = popResult.getElement();
                                return popResult.getStack().push(new NonTerminalNode(Rule.PROGRAM, line));
                            });
                            return next.transition(token, stack);
                        }
                    }
            );
        }
    }

    private static class GotLineState extends TransitionState {
        public GotLineState() {
            super(
                    true,
                    new Transition() {
                        ParserAutomata inner = new LineAutomata();

                        @Override
                        public boolean consumes(Token token) {
                            return inner.accepts(token);
                        }

                        @Override
                        public StateChange nextState(Token token, ImmutableStack<ASTNode> stack) {
                            ParserAutomataState next = new InnerAutomataState(inner, GotLineState::new, (s) -> {
                                ImmutableStack.PopResult<ASTNode> popResult1 = s.pop();
                                ImmutableStack.PopResult<ASTNode> popResult2 = popResult1.getStack().pop();
                                ASTNode line = popResult1.getElement();
                                ASTNode program = popResult2.getElement();
                                return popResult2.getStack().push(new NonTerminalNode(Rule.PROGRAM, program, line));
                            });
                            return next.transition(token, stack);
                        }
                    }
            );
        }
    }
}
