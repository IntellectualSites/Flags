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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class GlobalFlagContainer extends FlagContainer {

    private static final GlobalFlagContainer instance = new GlobalFlagContainer();

    /**
     * Get the singleton instance of {@link GlobalFlagContainer}
     *
     * @return Instance
     */
    public static GlobalFlagContainer getInstance() {
        return instance;
    }

    private static Map<String, Class<?>> stringClassMap;

    private GlobalFlagContainer() {
        super(null, (flag, type) -> {
            if (type == FlagUpdateType.FLAG_ADDED) {
                stringClassMap.put(flag.getName().toLowerCase(Locale.ENGLISH), flag.getClass());
            }
        });
        stringClassMap = new HashMap<>();
    }

    @Override public AbstractFlag<?, ?> getFlagErased(Class<?> flagClass) {
        final AbstractFlag<?, ?> flag = super.getFlagErased(flagClass);
        if (flag != null) {
            return flag;
        } else {
            throw new IllegalStateException(String.format("Unrecognized flag '%s'. All flag types"
                + " must be present in the global flag container.", flagClass.getSimpleName()));
        }
    }

    @NotNull @Override
    public <V, T extends AbstractFlag<V, ?>> T getFlag(Class<? extends T> flagClass) {
        final AbstractFlag<?, ?> flag = super.getFlag(flagClass);
        if (flag != null) {
            return castUnsafe(flag);
        } else {
            throw new IllegalStateException(String.format("Unrecognized flag '%s'. All flag types"
                + " must be present in the global flag container.", flagClass.getSimpleName()));
        }
    }

    public Class<?> getFlagClassFromString(final String name) {
        return stringClassMap.get(name.toLowerCase(Locale.ENGLISH));
    }

    public AbstractFlag<?, ?> getFlagFromString(final String name) {
        final Class<?> flagClass = this.getFlagClassFromString(name);
        if (flagClass == null) {
            return null;
        }
        return getFlagErased(flagClass);
    }

}
