package com.github.miro662.blazejsim;

import com.github.miro662.blazejsim.circuits.entities.logic_gates.AndGate;

import java.lang.reflect.InvocationTargetException;

public class BlazejSim {
    public void aaa() {

    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        System.out.println(AndGate.class.getConstructor().newInstance());
    }
}
