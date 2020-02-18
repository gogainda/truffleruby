/*
 * Copyright (c) 2013, 2019 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.core.array;

import org.truffleruby.Layouts;
import org.truffleruby.core.array.library.ArrayStoreLibrary;
import org.truffleruby.language.RubyContextSourceNode;
import org.truffleruby.language.RubyNode;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.profiles.ConditionProfile;

@NodeChild(value = "array", type = RubyNode.class)
@ImportStatic(ArrayGuards.class)
public abstract class ArrayDropTailNode extends RubyContextSourceNode {

    final int index;

    public ArrayDropTailNode(int index) {
        this.index = index;
    }

    @Specialization(limit = "STORAGE_STRATEGIES")
    protected DynamicObject dropTail(DynamicObject array,
            @CachedLibrary("getStore(array)") ArrayStoreLibrary arrays,
            @Cached ArrayExtractRangeNode extractRangeNode,
            @Cached("createBinaryProfile()") ConditionProfile indexLargerThanSize) {
        final int size = Layouts.ARRAY.getSize(array);
        if (indexLargerThanSize.profile(index >= size)) {
            return createArray(ArrayStoreLibrary.INITIAL_STORE, 0);
        } else {
            final int newSize = size - index;
            final Object withoutTail = extractRangeNode.execute(array, 0, newSize);
            return createArray(withoutTail, newSize);
        }
    }

}
