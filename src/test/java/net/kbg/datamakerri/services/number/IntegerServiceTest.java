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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class IntegerServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private IntegerService integerService;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeDigit() {
        Optional<NumberValue> optNum = integerService.makeDigit();
        assertTrue(optNum.isPresent());
        NumberValue numVal = optNum.get();
        assertTrue(numVal.getNumber().intValue() > -1 && numVal.getNumber().intValue() < 10);
        assertTrue(numVal.getStrNum().length() == 1);
    }

    @Test
    public void testIntegerFromList() {
        List<Integer> intList = Arrays.asList(new Integer[]{3, 6, 9, 12, 15, 18});
        Optional<NumberValue> optNum = integerService.fromList(intList);
        assertTrue(optNum.isPresent());
        NumberValue numVal = optNum.get();
        assertTrue(numVal.getNumber().intValue() > 2);
        assertTrue(numVal.getNumber().intValue() < 19);
        assertTrue(numVal.getNumber().intValue() % 3 == 0);
        assertTrue(numVal.getStrNum().length() > 0);
    }

    @Test
    public void testIntegerInRange(){
        int lowEnd = -20;
        int highEnd = 20;
        Optional<NumberValue> optNum = integerService.integerInRange(lowEnd, highEnd);
        assertTrue(optNum.isPresent());
        NumberValue numVal = optNum.get();
        assertTrue(numVal.getNumber().intValue() > -21);
        assertTrue(numVal.getNumber().intValue() < 21);
        assertTrue(numVal.getStrNum().length() > 0);
    }



    /*
        Expected Failure Tests
     */

    @Test
    public void testIntegerFromListEmpty() {
        List<Integer> intList = new ArrayList<>();
        Optional<NumberValue> optNum = integerService.fromList(intList);
        assertFalse(optNum.isPresent());
    }

    @Test
    public void testIntegerInRangeInverted(){
        int lowEnd = 20;
        int highEnd = -20;
        Optional<NumberValue> optNum = integerService.integerInRange(lowEnd, highEnd);
        assertFalse(optNum.isPresent());
    }

}
