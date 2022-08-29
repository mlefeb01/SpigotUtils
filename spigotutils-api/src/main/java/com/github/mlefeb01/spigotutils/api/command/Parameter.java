package com.github.mlefeb01.spigotutils.api.command;

/**
 * Model class for a command parameter
 *
 * @param <T> type
 * @author Matt Lefebvre
 */
public class Parameter<T> {
    private final AbstractParameterType<T> type;
    private final String name;
    private final T defaultValue;

    /**
     * Constructor
     *
     * @param type type
     * @param name name
     */
    public Parameter(AbstractParameterType<T> type, String name) {
        this(type, name, null);
    }

    /**
     * Constructor
     *
     * @param type         type
     * @param name         name
     * @param defaultValue defaultValue
     */
    public Parameter(AbstractParameterType<T> type, String name, T defaultValue) {
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    /**
     * The type of this parameter
     *
     * @return type
     */
    public AbstractParameterType<T> getType() {
        return type;
    }

    /**
     * The name of this parameter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * The default value of this parameter
     *
     * @return defaultValue
     */
    public T getDefaultValue() {
        return defaultValue;
    }

}
