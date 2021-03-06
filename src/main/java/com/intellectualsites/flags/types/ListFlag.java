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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class ListFlag<V, F extends AbstractFlag<List<V>, F>> extends
    AbstractFlag<List<V>, F> {

    public ListFlag(final List<V> valueList) {
        super(Collections.unmodifiableList(valueList));
    }

    @Override public F merge(@NotNull List<V> newValue) {
        final List<V> mergedList = new ArrayList<>();
        mergedList.addAll(getValue());
        mergedList.addAll(newValue);
        return this.flagOf(mergedList);
    }

    @Override public String toString() {
        final StringBuilder builder = new StringBuilder();
        final Iterator<V> iterator = this.getValue().iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(',');
            }
        }
        return builder.toString();
    }
}
