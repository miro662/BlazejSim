package com.github.miro662.blazejsim.circuits.entities.base;

import com.github.miro662.blazejsim.circuits.entities.Entity;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class AnnotatedEntitiesBase implements EntityBase {
    Map<String, RegisteredEntity> registeredEntities;

    @NotNull
    @Override
    public Stream<RegisteredEntity> getAll() {
        return registeredEntities.values().stream();
    }

    @Override
    public RegisteredEntity get(String name) {
        return registeredEntities.get(name);
    }

    public AnnotatedEntitiesBase() {
        registeredEntities = new HashMap<>();

        Reflections reflections = new Reflections("com.github.miro662.blazejsim");
        Set<Class<? extends Entity>> classes = reflections.getSubTypesOf(Entity.class);
        for (Class<? extends Entity> cls : classes) {
            try {
                RegisteredEntity re = RegisteredEntity.fromAnnotatedEntity(cls);
                registeredEntities.put(re.getName(), re);
            } catch (RegisteredEntity.UnannotatedEntityException ignored) {

            }
        }

    }
}
