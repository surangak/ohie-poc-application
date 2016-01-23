/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */
package org.regenstrief.hl7.transform;

import org.regenstrief.hl7.SegmentReader;
import org.regenstrief.hl7.segment.HL7Segment;

import java.io.IOException;

/**
 * SegmentFilter
 */
public abstract class SegmentFilter extends SegmentReader {
    
    protected SegmentReader parent = null;
    
    @Override
    public abstract HL7Segment readSegment() throws IOException;
    
    public void setParent(final SegmentReader parent) {
        this.parent = parent;
    }
}
