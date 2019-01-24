package com.github.miro662.blazejsim.circuits.entities.custom;

import com.github.miro662.blazejsim.circuits.Output;
import com.github.miro662.blazejsim.circuits.entities.ClassEntity;
import com.github.miro662.blazejsim.circuits.entities.EntityOutput;
import com.github.miro662.blazejsim.circuits.entities.base.RegisterEntity;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.ConstantExpression;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.Expression;
import com.github.miro662.blazejsim.circuits.entities.custom.expression.ParameterProvider;
import com.github.miro662.blazejsim.gui.circuit.entity_views.CustomEntityView;
import com.github.miro662.blazejsim.gui.circuit.entity_views.EntityView;
import com.github.miro662.blazejsim.simulation.LogicState;
import com.github.miro662.blazejsim.simulation.SimulationState;
import com.github.miro662.blazejsim.simulation.SimulationStateBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Entity that always return one
 */
@RegisterEntity(name = "Expression")
public class CustomEntity extends ClassEntity implements Serializable {
    private Expression expression;

    @EntityOutput(offset = 0)
    public Output y;

    @NotNull
    @Override
    protected SimulationState simulate(SimulationState oldState) {
        SimulationStateBuilder ssb = new SimulationStateBuilder();
        if (expression != null) {
            ssb.addFor(y, expression.evaluate(new ParameterProvider() {
                @Override
                public LogicState getParameter(String name) {
                    return this.getParameter(name);
                }
            }));
        } else {
            ssb.addFor(y, LogicState.UNDEFINED);
        }
        return ssb.build();
    }

    @Override
    public EntityView getEntityView() {
        return new CustomEntityView(this);
    }

    public CustomEntity () {
        expression = new ConstantExpression(LogicState.HIGH);
    }
}