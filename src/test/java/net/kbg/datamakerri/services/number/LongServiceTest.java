/*
 *     Copyright 2020 Clark Allen
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package net.kbg.datamakerri.services.number;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.model.NumberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

@SpringBootTest(classes = DatamakerriApplication.class)
public class LongServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    LongService longService;

    /*
        Happy Path Tests
     */

    @Test
    public void testLongInRange() {
        long lowEnd = -20;
        long highEnd = 20;

        Optional<NumberValue> optNum = longService.longFromRange(lowEnd, highEnd);
        assertTrue(optNum.isPresent());
        NumberValue num = optNum.get();
        assertTrue(num.getNumber().longValue() > -21);
        assertTrue(num.getNumber().longValue() < 21);
        assertTrue(num.getStrNum().length() > 0);
    }

    @Test
    public void testLongFromList() {
        List<Long> longList = Arrays.asList(new Long[]{4L, 8L, 12L, 16L, 20L, 24L, 28L});
        Optional<NumberValue> optNum = longService.longFromList(longList);
        assertTrue(optNum.isPresent());
        NumberValue num = optNum.get();
        assertTrue(longList.contains(num.getNumber().longValue()));
        assertTrue(num.getStrNum().length() > 0);
        assertEquals(num.getStrNum(), "" + num.getNumber().longValue());
    }



    /*
        Expected Failure Tests
     */

    @Test
    public void testLongFromRangeInverted() {
        long lowEnd = -20;
        long highEnd = 20;

        Optional<NumberValue> optNum = longService.longFromRange(highEnd, lowEnd);
        assertFalse(optNum.isPresent());
    }

    @Test
    public void testLongFromRangeEmptyArg() {
        List<Long> longList = new ArrayList<>();
        Optional<NumberValue> optNum = longService.longFromList(longList);
        assertFalse(optNum.isPresent());
    }

}
