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
package com.intellectualsites.flags.types;

import com.intellectualsites.flags.AbstractFlag;
import com.intellectualsites.flags.FlagParseException;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public abstract class EnumFlag<E extends Enum<E>> extends AbstractFlag<E, EnumFlag<E>> {

    private final Set<E> values;

    /**
     * Construct a new flag instance.
     *
     * @param clazz Enum class
     * @param value Flag value
     */
    protected EnumFlag(@NotNull final Class<E> clazz, @NotNull E value) {
        super(value);
        // All possible enum values
        values = EnumSet.allOf(clazz);
    }

    @Override public EnumFlag<E> parse(@NotNull final String input) throws FlagParseException {
        for (final E value : this.values) {
            if (input.equalsIgnoreCase(value.name())) {
                return flagOf(value);
            }
        }
        final StringBuilder values = new StringBuilder();
        final Iterator<E> iterator = this.values.iterator();
        while (iterator.hasNext()) {
            values.append(iterator.next());
            if (iterator.hasNext()) {
                values.append(", ");
            }
        }
        throw new FlagParseException(this, input, "Value has to be one of: " + values);
    }

    @Override public EnumFlag<E> merge(@NotNull final E newValue) {
        return flagOf(newValue);
    }

    @Override public String toString() {
        return this.getValue().name();
    }

    @Override public String getExample() {
        if (this.values.isEmpty()) {
            return "";
        }
        return values.toArray()[0].toString();
    }

}
