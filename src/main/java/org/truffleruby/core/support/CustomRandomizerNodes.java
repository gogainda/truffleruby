/*
 * Copyright (c) 2021, 2021 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 2.0, or
 * GNU General Public License version 2, or
 * GNU Lesser General Public License version 2.1.
 */
package org.truffleruby.core.support;

import org.truffleruby.builtins.CoreMethod;
import org.truffleruby.builtins.CoreModule;
import org.truffleruby.builtins.UnaryCoreMethodNode;
import org.truffleruby.core.klass.RubyClass;
import org.truffleruby.language.Visibility;

import com.oracle.truffle.api.dsl.Specialization;

@CoreModule(value = "Truffle::CustomRandomizer", isClass = true)
public abstract class CustomRandomizerNodes {

    @CoreMethod(names = { "__allocate__", "__layout_allocate__" }, constructor = true, visibility = Visibility.PRIVATE)
    public abstract static class AllocateNode extends UnaryCoreMethodNode {

        @Specialization
        protected RubyCustomRandomizer randomizerAllocate(RubyClass customRandomizerClass) {
            return new RubyCustomRandomizer(
                    coreLibrary().customRandomizerClass,
                    getLanguage().customRandomizerShape);
        }

    }

}
