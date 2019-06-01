package com.wawey.parser.ast;

import com.wawey.parser.Rule;

import java.util.Arrays;
import java.util.Collections;

public class FileNode extends NonTerminalNode {
    public FileNode(FileNode file, LineNode line) {
        super(Rule.FILE, Arrays.asList(file, line));
    }

    public FileNode(LineNode line) {
        super(Rule.FILE, Collections.singletonList(line));
    }
}
