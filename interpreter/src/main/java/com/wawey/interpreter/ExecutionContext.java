package com.wawey.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Perez Molina
 */
public class ExecutionContext implements VariableDeclarator, VariablePool {
    private final Map<String, VariableTypeValue> variables = new HashMap<>();

    @Override
    public void declareVariable(String identifier, VariableType type) {
        if (variables.containsKey(identifier)) throw new RedeclarationError(identifier);
        else variables.put(identifier, new VariableTypeValue(type, UndefinedValue.getInstance()));
    }

    @Override
    public void setVariableValue(String identifier, Value value) {
        if (!variables.containsKey(identifier)) throw new CannotFindNameException(identifier);
        else {
            VariableTypeValue variableTypeValue = variables.get(identifier);
            if (variableTypeValue.type != VariableType.ANY && variableTypeValue.type != value.getType()) {
                throw new UnassignableException(value.getType(), variableTypeValue.type);
            }
            variables.put(identifier, new VariableTypeValue(value.getType(), value));
        }
    }

    @Override
    public Value getVariable(String identifier) {
        VariableTypeValue variableTypeValue = variables.get(identifier);
        if (variableTypeValue == null) throw new ReferenceError(identifier);
        return variableTypeValue.value;
    }

    private static class VariableTypeValue {
        final VariableType type;
        final Value value;

        public VariableTypeValue(VariableType type, Value value) {
            this.type = type;
            this.value = value;
        }
    }
}
