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

package net.kbg.datamakerri.services.date;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.model.DateValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

@SpringBootTest(classes = DatamakerriApplication.class)
public class DateServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DateService dateService;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeDateInYear() {
        int year = 1981;
        Optional<DateValue> optVal = dateService.makeDateInYear(year);
        assertTrue(optVal.isPresent());
        DateValue date = optVal.get();
        assertNotNull(date.getIso8601());
        assertTrue(date.getIso8601().contains("" + year));
        assertTrue(date.getIso8601().length() > 0);
        assertTrue(date.getMilliseconds() > 0);
    }

    @Test
    public void testMakeDateInMonthYear() {
        List<Integer> months = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        int year = 2000;

        for (int k = 0; k < months.size(); ++k) {
            Optional<DateValue> optVal = dateService.makeDateInMonthYear(months.get(k), year);
            assertTrue(optVal.isPresent());
            DateValue date = optVal.get();
            assertNotNull(date.getIso8601());
            assertTrue(date.getIso8601().length() > 0);
            assertTrue(date.getIso8601().contains("" + year));
            // DateValue{iso8601='2001-07-23T04:26:32.106Z', milliseconds=995862392106}
            assertTrue(date.getIso8601().substring(5, 7).contains("" + months.get(k)));
            assertTrue(date.getMilliseconds() > 0);
        }
    }

    @Test
    public void testMakeDateInYearRange() {
        int lowYear = 1998;
        int highYear = 2004;
        Optional<DateValue> optVal = dateService.makeDateInYearRange(lowYear, highYear);
        assertTrue(optVal.isPresent());
        DateValue date = optVal.get();
        assertNotNull(date.getIso8601());
        assertTrue(date.getIso8601().length() > 0);
        assertTrue(date.getMilliseconds() > 0);
        // DateValue{iso8601='2001-07-23T04:26:32.106Z', milliseconds=995862392106}
        int yr = Integer.parseInt(date.getIso8601().substring(0, 4));
        assertTrue(yr >= lowYear);
        assertTrue(yr <= highYear);
    }


    /*
        Expected Failure Tests
     */

    @Test
    public void testMakeDateInYearTooEarly() {
        int year = 0;
        Optional<DateValue> optVal = dateService.makeDateInYear(year);
        assertFalse(optVal.isPresent());
    }

    @Test
    public void testMakeDateInYearTooLate() {
        int year = 4001;
        Optional<DateValue> optVal = dateService.makeDateInYear(year);
        assertFalse(optVal.isPresent());
    }

    @Test
    public void testMakeDateInMonthYearTooEarly() {
        int month = 0;
        int year = 1999;
        Optional<DateValue> optVal = dateService.makeDateInMonthYear(month, year);
        assertFalse(optVal.isPresent());

        month = 6;
        year = 0;
        optVal = dateService.makeDateInMonthYear(month, year);
        assertFalse(optVal.isPresent());
    }

    @Test
    public void testMakeDateInMonthYearTooLate() {
        int month = 13;
        int year = 1999;
        Optional<DateValue> optVal = dateService.makeDateInMonthYear(month, year);
        assertFalse(optVal.isPresent());

        month = 6;
        year = 4001;
        optVal = dateService.makeDateInMonthYear(month, year);
        assertFalse(optVal.isPresent());
    }

    @Test
    public void testMakeDateInYearRangeTooEarly() {
        int lowYear = 0;
        int highYear = 2004;
        Optional<DateValue> optVal = dateService.makeDateInYearRange(lowYear, highYear);
        assertFalse(optVal.isPresent());
    }

    @Test
    public void testMakeDateInYearRangeTooLate() {
        int lowYear = 1999;
        int highYear = 4001;
        Optional<DateValue> optVal = dateService.makeDateInYearRange(lowYear, highYear);
        assertFalse(optVal.isPresent());
    }

}