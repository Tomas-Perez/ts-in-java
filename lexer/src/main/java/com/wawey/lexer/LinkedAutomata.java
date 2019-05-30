package com.wawey.lexer;

import java.util.LinkedList;
import java.util.List;

public class LinkedAutomata implements Automata {
    private List<AutomataLink> automatas;
    private int current = 0;

    private LinkedAutomata(List<AutomataLink> automatas) {
        this.automatas = automatas;
    }

    @Override
    public boolean acceptable() {
        return automatas.stream().allMatch(l -> l.skippable || l.automata.acceptable());
    }

    @Override
    public void consume(char c) {
        AutomataLink currentLink = automatas.get(current);
        try {
            currentLink.automata.consume(c);
        } catch (NoTransitionException exc) {
            if (
                    (currentLink.automata.acceptable() || currentLink.skippable)
                            && current < automatas.size() - 1) {
                current++;
                consume(c);
            } else throw exc;
        }
    }

    @Override
    public void reset() {
        automatas.forEach(l -> l.automata.reset());
        current = 0;
    }

    public static class Builder {
        private List<AutomataLink> links = new LinkedList<>();

        public Builder andThen(Automata automata) {
            links.add(new AutomataLink(automata, false));
            return this;
        }

        public Builder maybeThen(Automata automata) {
            links.add(new AutomataLink(automata, true));
            return this;
        }

        public LinkedAutomata build() {
            return new LinkedAutomata(links);
        }
    }

    private static class AutomataLink {
        final Automata automata;
        final boolean skippable;

        public AutomataLink(Automata automata, boolean skippable) {
            this.automata = automata;
            this.skippable = skippable;
        }
    }
}
