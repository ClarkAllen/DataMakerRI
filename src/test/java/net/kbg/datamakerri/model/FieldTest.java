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

package net.kbg.datamakerri.model;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.helpers.Definitions;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = DatamakerriApplication.class)
public class FieldTest {

    /*
        Expected failure tests
     */

    @Test
    public void testEmptyRequiredFields() {
        Field f = Field.builder()
                .name("")
                .dmSourceType("")
                .build();
        List<String> errors = f.validate(1);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 2);
    }

    @Test
    public void testNullRequiredFields() {
        Field f = Field.builder()
                .name(null)
                .dmSourceType(null)
                .build();
        List<String> errors = f.validate(2);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 2);
    }

    @Test
    public void testBadSourceType() {
        Field f = Field.builder()
                .name("BAD_SOURCE_TYPE")
                .dmSourceType("blivit")
                .build();
        List<String> errors = f.validate(3);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testRandomTextZeroLength() {
        Field f = Field.builder()
                .name("BAD_RTEXT_LENGTH")
                .dmSourceType(Definitions.RTEXT)
                .length(0)
                .build();
        List<String> errors = f.validate(4);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLongFromListWithNoList() {
        Field f = Field.builder()
                .name("NO_LIST_OF_LONGS")
                .dmSourceType(Definitions.LONG_FROM_LIST)
                .build();
        List<String> errors = f.validate(4);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLongListFieldNameIsBad() {
        List<String> longs = Arrays.asList(new String[]{"1","2","3"});
        Map<String, List<String>> fromLists = new HashMap<>();
        fromLists.put("does not match field name", longs);
        Field f = Field.builder()
                .name("BAD_LONG_LIST_NAME")
                .dmSourceType(Definitions.LONG_FROM_LIST)
                .fromLists(fromLists)
                .build();
        List<String> errors = f.validate(5);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLongListTooSmall() {
        List<String> longs = Arrays.asList(new String[]{"1"});
        Map<String, List<String>> fromLists = new HashMap<>();
        fromLists.put("LONG_LIST_TOO_SMALL", longs);
        Field f = Field.builder()
                .name("LONG_LIST_TOO_SMALL")
                .dmSourceType(Definitions.LONG_FROM_LIST)
                .fromLists(fromLists)
                .build();
        List<String> errors = f.validate(6);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testTextFromListWithNoList() {
        Field f = Field.builder()
                .name("NO_LIST_OF_TEXT")
                .dmSourceType(Definitions.TEXT_FROM_LIST)
                .build();
        List<String> errors = f.validate(7);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testTextListFieldNameIsBad() {
        List<String> longs = Arrays.asList(new String[]{"1","2","3"});
        Map<String, List<String>> fromLists = new HashMap<>();
        fromLists.put("does not match field name", longs);
        Field f = Field.builder()
                .name("BAD_TEXT_LIST_NAME")
                .dmSourceType(Definitions.TEXT_FROM_LIST)
                .fromLists(fromLists)
                .build();
        List<String> errors = f.validate(8);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testTextListTooSmall() {
        List<String> longs = Arrays.asList(new String[]{"1"});
        Map<String, List<String>> fromLists = new HashMap<>();
        fromLists.put("TEXT_LIST_TOO_SMALL", longs);
        Field f = Field.builder()
                .name("TEXT_LIST_TOO_SMALL")
                .dmSourceType(Definitions.TEXT_FROM_LIST)
                .fromLists(fromLists)
                .build();
        List<String> errors = f.validate(9);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testFNameGenderIsEmpty() {
        Field f = Field.builder()
                .name("EMPTY_GENDER_ARG")
                .dmSourceType(Definitions.FNAME)
                .gender("")
                .build();
        List<String> errors = f.validate(10);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testFNameGenderIsNull() {
        Field f = Field.builder()
                .name("NULL_GENDER_ARG")
                .dmSourceType(Definitions.FNAME)
                .gender(null)
                .build();
        List<String> errors = f.validate(11);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testFNameGenderIsBad() {
        Field f = Field.builder()
                .name("BAD_GENDER_ARG")
                .dmSourceType(Definitions.FNAME)
                .gender("X")
                .build();
        List<String> errors = f.validate(12);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testBoolDatabaseTypeIsBlank() {
        Field f = Field.builder()
                .name("EMPTY_DB_TYPE_ARG")
                .dmSourceType(Definitions.BOOL)
                .databaseType("")
                .build();
        List<String> errors = f.validate(13);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testBoolDatabaseTypeIsNull() {
        Field f = Field.builder()
                .name("NULL_DB_TYPE_ARG")
                .dmSourceType(Definitions.BOOL)
                .databaseType(null)
                .build();
        List<String> errors = f.validate(14);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testBoolDatabaseTypeIsBad() {
        Field f = Field.builder()
                .name("BAD_DB_TYPE_ARG")
                .dmSourceType(Definitions.BOOL)
                .databaseType("something-bad")
                .build();
        List<String> errors = f.validate(15);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLowMonthInMonthYear() {
        Field f = Field.builder()
                .name("LOW_MONTH_ARG")
                .dmSourceType(Definitions.DATE_MON_YR)
                .month(0)
                .year(2001)
                .build();
        List<String> errors = f.validate(16);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testBigMonthInMonthYear() {
        Field f = Field.builder()
                .name("HIGH_MONTH_ARG")
                .dmSourceType(Definitions.DATE_MON_YR)
                .month(13)
                .year(2001)
                .build();
        List<String> errors = f.validate(17);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLowYearInMonthYear() {
        Field f = Field.builder()
                .name("LOW_YEAR_ARG")
                .dmSourceType(Definitions.DATE_MON_YR)
                .month(1)
                .year(0)
                .build();
        List<String> errors = f.validate(18);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testBigYearInMonthYear() {
        Field f = Field.builder()
                .name("HIGH_YEAR_ARG")
                .dmSourceType(Definitions.DATE_MON_YR)
                .month(12)
                .year(4001)
                .build();
        List<String> errors = f.validate(19);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLowYearInYearDate() {
        Field f = Field.builder()
                .name("LOW_YEAR_ARG")
                .dmSourceType(Definitions.DATE_IN_YR)
                .year(0)
                .build();
        List<String> errors = f.validate(20);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testHighYearInYearDate() {
        Field f = Field.builder()
                .name("HIGH_YEAR_ARG")
                .dmSourceType(Definitions.DATE_IN_YR)
                .year(4_001)
                .build();
        List<String> errors = f.validate(21);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLowYearInDateRange() {
        Field f = Field.builder()
                .name("LOW_YEAR_IN_RANGE")
                .dmSourceType(Definitions.DATE_BETW_YRS)
                .yearLowEnd(0)
                .yearHighEnd(2001)
                .build();
        List<String> errors = f.validate(22);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testHighYearInDateRange() {
        Field f = Field.builder()
                .name("HIGH_YEAR_IN_RANGE")
                .dmSourceType(Definitions.DATE_BETW_YRS)
                .yearLowEnd(2_001)
                .yearHighEnd(4_001)
                .build();
        List<String> errors = f.validate(23);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testInvertedYearsInDateRange() {
        Field f = Field.builder()
                .name("HIGH_YEAR_IN_RANGE")
                .dmSourceType(Definitions.DATE_BETW_YRS)
                .yearLowEnd(2_001)
                .yearHighEnd(1_999)
                .build();
        List<String> errors = f.validate(24);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testEmptyPattern() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("")
                .charSymbol("C")
                .numSymbol("N")
                .build();
        List<String> errors = f.validate(25);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNullPattern() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern(null)
                .charSymbol("C")
                .numSymbol("N")
                .build();
        List<String> errors = f.validate(25);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testEmptyCharSymbol() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("Hah_CCC_NNN")
                .charSymbol("")
                .numSymbol("N")
                .build();
        List<String> errors = f.validate(26);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNullCharSymbol() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("Hah_CCC_NNN")
                .charSymbol(null)
                .numSymbol("N")
                .build();
        List<String> errors = f.validate(26);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLongCharSymbol() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("Hah_CCC_NNN")
                .charSymbol("CC")
                .numSymbol("N")
                .build();
        List<String> errors = f.validate(26);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testEmptyNumSymbol() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("Glom_CCC:NNN")
                .charSymbol("C")
                .numSymbol("")
                .build();
        List<String> errors = f.validate(27);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNullNumSymbol() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("Glom_CCC:NNN")
                .charSymbol("C")
                .numSymbol(null)
                .build();
        List<String> errors = f.validate(27);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLongNumSymbol() {
        Field f = Field.builder()
                .name("EMPTY_PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("Glom_CCC:NNN")
                .charSymbol("C")
                .numSymbol("NN")
                .build();
        List<String> errors = f.validate(27);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testDoubleInvalidRangeArgs() {
        Field f = Field.builder()
                .name("SAME_LOW_AND_HIGH_END_DOUBLE")
                .dmSourceType(Definitions.DOUBLE)
                .rangeLowEnd(7)
                .rangeHighEnd(7)
                .precision(3)
                .build();
        List<String> errors = f.validate(28);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testDoubleMissingPrecisionArg() {
        Field f = Field.builder()
                .name("SAME_LOW_AND_HIGH_END_DOUBLE")
                .dmSourceType(Definitions.DOUBLE)
                .rangeLowEnd(2)
                .rangeHighEnd(7)
                .build();
        List<String> errors = f.validate(28);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testLongInvalidArgs() {
        Field f = Field.builder()
                .name("SAME_LOW_AND_HIGH_END_DOUBLE")
                .dmSourceType(Definitions.LONG)
                .rangeLowEnd(7)
                .rangeHighEnd(7)
                .build();
        List<String> errors = f.validate(29);
        assertNotNull(errors);
        System.out.println("Field Errors : " + errors);
        assertEquals(errors.size(), 1);
    }

}
