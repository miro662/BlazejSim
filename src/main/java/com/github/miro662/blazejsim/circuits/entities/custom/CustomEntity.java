package com.github.miro662.blazejsim.circuits.entities.custom;

import com.github.miro662.blazejsim.circuits.Input;
import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.*;
import com.github.miro662.blazejsim.gui.circuit.entity_views.CustomEntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Entity that always return one
 */
@RegisterEntity(name = "Expression")
public class CustomEntity extends Entity implements Serializable {
    private Expression expression;
    private Output output;
    private HashMap<String, Input> inputs;

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

    public CustomEntity () {
        output = new Output(this, 0);
        setExpression(new NotExpression(new AndExpression(
                new OrExpression(
                        new ParameterExpression("a"),
                        new ParameterExpression("b")
                ),
                new OrExpression(
                        new ParameterExpression("A"),
                        new ParameterExpression("B")
                ))
        ));
    }

    public synchronized void setExpression(Expression expression) {
        this.expression = expression;
        int offset = -12;
        this.inputs = new HashMap<>();
        for (Iterator<String> it = expression.getParameters().iterator(); it.hasNext(); ) {
            String name = it.next();
            inputs.put(name, new Input(this, offset));
            offset += 8;
        }
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
}