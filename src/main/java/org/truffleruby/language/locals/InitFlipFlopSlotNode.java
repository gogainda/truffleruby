/*
 * Copyright (c) 2013, 2017 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.truffleruby.language.locals;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import org.truffleruby.language.RubyNode;

public class InitFlipFlopSlotNode extends RubyNode {

    private final FrameSlot frameSlot;

    public InitFlipFlopSlotNode(FrameSlot frameSlot) {
        this.frameSlot = frameSlot;
    }

    @Override
    public void doExecuteVoid(VirtualFrame frame) {
        frame.setBoolean(frameSlot, false);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        doExecuteVoid(frame);
        return null;
    }

}
