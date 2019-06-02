package com.wawey.parser.ast;

import java.util.stream.Collectors;

public class ASTPrettyPrinter {
    public static void prettyPrint(ASTNode ast) {
        System.out.println(prettyPrint(ast, 1));
    }

    private static String prettyPrint(ASTNode ast, int depth) {
        StringBuilder builder = new StringBuilder();
        builder.append(ast.getRule());
        builder.append("(");
        if (ast instanceof TerminalNode) {
            builder.append(((TerminalNode) ast).getValue());
        } else {
            builder.append("\n");
            StringBuilder tabs = new StringBuilder();
            for (int i = 0; i < depth - 1; i++) {
                tabs.append("\t");
            }
            builder.append(((NonTerminalNode) ast).getChildren().stream().map(n -> tabs.toString() + "\t" + prettyPrint(n, depth + 1)).collect(Collectors.joining(",\n")));
            builder.append("\n");
            builder.append(tabs.toString());
        }
        builder.append(")");
        return builder.toString();
    }
}
