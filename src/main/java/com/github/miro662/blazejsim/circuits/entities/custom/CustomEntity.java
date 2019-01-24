package com.github.miro662.blazejsim.circuits.entities.custom;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.*;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.parser.ParseException;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.parser.Parser;
import com.github.miro662.blazejsim.gui.circuit.entity_views.CustomEntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Entity that always return one
 */
@RegisterEntity(name = "Expression")
public class CustomEntity extends Entity implements Serializable {
    private Expression expression;
    private Output output;
    private HashMap<String, Input> inputs;
    private String expressionString;

    @NotNull
    @Override
    protected synchronized SimulationState simulate(SimulationState oldState) {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        if (expression != null) {
            ssb.addFor(output, expression.evaluate(name -> {
                Input input = getInput(name);
                if (input != null) {
                    return oldState.getFor(input);
                } else {
                    return LogicState.UNDEFINED;
                }
            }));
        } else {
            ssb.addFor(output, LogicState.UNDEFINED);
        }
        return ssb.build();
    }

    @Override
    public EntityView getEntityView() {
        return new CustomEntityView(this);
    }

    public CustomEntity () throws CannotModifyInputsOfConnectedEntity {
        output = new Output(this, 0);
        expressionString = "";
        inputs = new HashMap<>();
        setExpression(new ConstantExpression());
    }

    public synchronized void setExpression(Expression expression) throws CannotModifyInputsOfConnectedEntity {
        this.expression = expression;
        int offset = -12;

        // if params changed, redefine inputs
        List<String> parametersList = expression.getParameters().sorted().collect(Collectors.toList());
        List<String> inputsList = getInputNames().sorted().collect(Collectors.toList());

        if (!parametersList.equals(inputsList)) {
            if (!this.isConnectedToAnything()) {
                this.inputs = new HashMap<>();
                for (String name : parametersList) {
                    inputs.put(name, new Input(this, offset));
                    offset += 8;
                }
            } else {
                throw new CannotModifyInputsOfConnectedEntity();
            }
        }
    }

    public void setExpressionString(String str) throws ParseException, CannotModifyInputsOfConnectedEntity {
        Parser parser = new Parser();
        setExpression(parser.parse(str));
        this.expressionString = str;
    }

    public String getExpressionString() {
        return expressionString;
    }

    @Nullable
    @Override
    public synchronized Input getInput(String name) {
        return inputs.get(name);
    }

    @NotNull
    @Override
    public synchronized Stream<Input> getInputs() {
        return inputs.values().stream();
    }

    @NotNull
    @Override
    public synchronized Stream<String> getInputNames() {
        return inputs.keySet().stream();
    }

    @Nullable
    @Override
    public Output getOutput(String name) {
        return output;
    }

    @NotNull
    @Override
    public Stream<Output> getOutputs() {
        return Stream.of(output);
    }

    @NotNull
    @Override
    public Stream<String> getOuptutNames() {
        return Stream.of("y");
    }

    public class CannotModifyInputsOfConnectedEntity extends Exception {

    }
}