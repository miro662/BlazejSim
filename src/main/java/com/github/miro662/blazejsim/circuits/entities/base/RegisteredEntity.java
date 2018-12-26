package com.github.miro662.blazejsim.circuits.entities.base;

import com.github.miro662.blazejsim.circuits.Circuit;
import com.github.miro662.blazejsim.circuits.entities.Entity;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * Class describing registered entity
 * Factory of such entities and container for their data
 */
public class RegisteredEntity {
    /**
     * Return entity class used in this registered entity
     * @return class of entity used in this RegisteredEntity
     */
    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

    private Class<? extends Entity> entityClass;

    /**
     * Create new RegisteredEntity using annotated Entity
     * @param entityClass entity class to be used to create RegisteredEntity instance
     * @return RegisteredEntity instance for given Entity class
     */
    @NotNull
    public static RegisteredEntity fromAnnotatedEntity(@NotNull Class<? extends Entity> entityClass)
        throws UnannotatedEntityException {
        RegisterEntity annotation = entityClass.getAnnotation(RegisterEntity.class);
        if (annotation == null) {
            throw new UnannotatedEntityException();
        }
        RegisteredEntity registeredEntity = new RegisteredEntity();
        registeredEntity.entityClass = entityClass;

        //TODO: set other params from annotation to RegisteredEntity

        return registeredEntity;
    }

    /**
     * Creates entity of registered entity
     * @return created entity
     */
    @NotNull
    public Entity create() {
        try {
            return getEntityClass().getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Entity that was being tried to be registered was unannotated
     */
    public static class UnannotatedEntityException extends Exception {
    }
}
