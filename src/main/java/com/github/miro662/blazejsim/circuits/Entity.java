package com.github.miro662.blazejsim.circuits;

import java.util.Map;

/**
 * Describes single entity on circuit (e.g. logic gate, flip-flop)
 */
public abstract class Entity {
    Map<String, Input> inputs;
    Map<String, Output> outputs;
}
