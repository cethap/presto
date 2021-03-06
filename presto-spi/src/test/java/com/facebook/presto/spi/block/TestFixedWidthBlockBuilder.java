/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.spi.block;

import org.testng.annotations.Test;

import static com.facebook.presto.spi.type.BooleanType.BOOLEAN;
import static io.airlift.slice.SizeOf.SIZE_OF_BYTE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestFixedWidthBlockBuilder
{
    private static final int BOOLEAN_ENTRY_SIZE = BOOLEAN.getFixedSize() + SIZE_OF_BYTE;
    private static final int EXPECTED_ENTRY_COUNT = 3;

    @Test
    public void testFixedBlockIsFull()
            throws Exception
    {
        testIsFull(new FixedWidthBlockBuilder(BOOLEAN, EXPECTED_ENTRY_COUNT));
        testIsFull(new FixedWidthBlockBuilder(BOOLEAN, new BlockBuilderStatus(BOOLEAN_ENTRY_SIZE * EXPECTED_ENTRY_COUNT, 1024)));
        testIsFull(new FixedWidthBlockBuilder(BOOLEAN, new BlockBuilderStatus(1024, BOOLEAN_ENTRY_SIZE * EXPECTED_ENTRY_COUNT)));
    }

    private void testIsFull(FixedWidthBlockBuilder blockBuilder)
    {
        assertTrue(blockBuilder.isEmpty());
        while (!blockBuilder.isFull()) {
            blockBuilder.appendBoolean(true);
        }
        assertEquals(blockBuilder.getPositionCount(), EXPECTED_ENTRY_COUNT);
        assertEquals(blockBuilder.isFull(), true);
    }
}
