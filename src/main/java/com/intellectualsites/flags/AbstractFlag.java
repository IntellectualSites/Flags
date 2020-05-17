/*
 *       _____  _       _    _____                                _
 *      |  __ \| |     | |  / ____|                              | |
 *      | |__) | | ___ | |_| (___   __ _ _   _  __ _ _ __ ___  __| |
 *      |  ___/| |/ _ \| __|\___ \ / _` | | | |/ _` | '__/ _ \/ _` |
 *      | |    | | (_) | |_ ____) | (_| | |_| | (_| | | |  __/ (_| |
 *      |_|    |_|\___/ \__|_____/ \__, |\__,_|\__,_|_|  \___|\__,_|
 *                                    | |
 *                                    |_|
 *            PlotSquared plot management system for Minecraft
 *                  Copyright (C) 2020 IntellectualSites
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.intellectualsites.flags;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * A flag is any property that can be assigned
 * to an object, that will alter its functionality in some way.
 *
 * @param <T> Value contained in the flag.
 */
public abstract class AbstractFlag<T, F extends AbstractFlag<T, F>> {

    private final T value;
    private final String flagName;

    /**
     * Construct a new flag instance.
     *
     * @param value           Flag value
     */
    protected AbstractFlag(@NotNull final T value) {
        // Parse flag name
        this.value = Objects.requireNonNull(value, "Value may not be null");
        final StringBuilder flagName = new StringBuilder();
        final char[] chars = this.getClass().getSimpleName().replace("Flag", "").toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                flagName.append(Character.toLowerCase(chars[i]));
            } else if (Character.isUpperCase(chars[i])) {
                flagName.append('-').append(Character.toLowerCase(chars[i]));
            } else {
                flagName.append(chars[i]);
            }
        }
        this.flagName = flagName.toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractFlag<?, ?> that = (AbstractFlag<?, ?>) o;
        return Objects.equals(value, that.value);
    }

    @Override public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Get the flag value
     *
     * @return Non-nullable flag value
     */
    @NotNull public final T getValue() {
        return this.value;
    }

    /**
     * Parse a string into a flag, and throw an exception in the case that the
     * string does not represent a valid flag value. This instance won't change its
     * state, but instead an instance holding the parsed flag value will be returned.
     *
     * @param input String to parse.
     * @return Parsed value, if valid.
     * @throws FlagParseException If the value could not be parsed.
     */
    public abstract F parse(@NotNull final String input) throws FlagParseException;

    /**
     * Merge this flag's value with another value and return an instance
     * holding the merged value.
     *
     * @param newValue New flag value.
     * @return Flag containing parsed flag value.
     */
    public abstract F merge(@NotNull final T newValue);

    /**
     * Returns a string representation of the flag instance, that when
     * passed through {@link #parse(String)} will result in an equivalent
     * instance of the flag.
     *
     * @return String representation of the flag
     */
    public abstract String toString();

    /**
     * Get the flag name.
     *
     * @return Flag name
     */
    public final String getName() {
        return this.flagName;
    }

    /**
     * An example of a string that would parse into a valid
     * flag value.
     *
     * @return An example flag value.
     */
    public abstract String getExample();

    protected abstract F flagOf(@NotNull T value);

    /**
     * Create a new instance of the flag using a provided
     * (non-null) value.
     *
     * @param value The flag value
     * @return The created flag instance
     */
    public final F createFlagInstance(@NotNull final T value) {
        return flagOf(Objects.requireNonNull(value));
    }

    /**
     * Get a list of value suggestions for this flag
     *
     * @return Collection containing suggested values
     */
    public Collection<String> getValueSuggestions() {
        return Collections.emptyList();
    }

}
