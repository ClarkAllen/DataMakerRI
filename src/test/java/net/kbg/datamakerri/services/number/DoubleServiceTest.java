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
import java.util.Optional;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class DoubleServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DoubleService doubleService;

    /*
        Happy Path Tests
     */

    @Test
    public void testDoubleInRange() {
        long lowEnd = -10;
        long highEnd = 10;
        Optional<NumberValue> optRslt = doubleService.doubleInRange(lowEnd, highEnd);
        assertTrue(optRslt.isPresent());
        NumberValue numberValue = optRslt.get();
        assertTrue(lowEnd * 1.0 <= numberValue.getNumber().doubleValue());
        assertTrue(highEnd * 1.0 >= numberValue.getNumber().doubleValue());
        System.out.println("Number Value = " + numberValue.toString());
    }

    @Test
    public void testDoubleInRangeExtreme() {
        long lowEnd = Long.MIN_VALUE;
        long highEnd = Long.MAX_VALUE;
        Optional<NumberValue> optRslt = doubleService.doubleInRange(lowEnd, highEnd);
        assertTrue(optRslt.isPresent());
        NumberValue numberValue = optRslt.get();
        assertTrue(lowEnd * 1.0 <= numberValue.getNumber().doubleValue());
        assertTrue(highEnd * 1.0 >= numberValue.getNumber().doubleValue());
        System.out.println("Number Value = " + numberValue.toString());
    }



    /*
        Expected Failure Tests
     */

    @Test
    public void testDoubleInRangeLowOverHigh() {
        long lowEnd = -10;
        long highEnd = 10;
        Optional<NumberValue> optRslt = doubleService.doubleInRange(highEnd, lowEnd);
        assertFalse(optRslt.isPresent());
    }
}
