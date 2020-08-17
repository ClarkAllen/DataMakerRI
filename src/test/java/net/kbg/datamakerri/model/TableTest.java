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

import static org.testng.Assert.*;

@SpringBootTest(classes = DatamakerriApplication.class)
public class TableTest {

    @Test
    public void testValidate() {
        Table table = new Table();
        table.setName("VARIOUS_THINGS");
        table.setRows(5);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 0);
    }

    @Test
    public void testValidateBigTable() {
        Table table = new Table();
        table.setName("ALL_FIELD_TYPES");
        table.setRows(25);
        table.setStartRowNum(1);
        table.setFields(makeBigFieldList());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 0);
        System.out.println(table.toString());
    }


    /*
        Expected Failure tests
     */

    @Test
    public void testNoTableName() {
        Table table = new Table();
        table.setName("");
        table.setRows(5);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNoRows() {
        Table table = new Table();
        table.setName("NO_ROWS");
        table.setRows(0);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNegativeRows() {
        Table table = new Table();
        table.setName("NEGATIVE_ROWS");
        table.setRows(-4);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testZeroStartRowNumber() {
        Table table = new Table();
        table.setName("ZERO_START");
        table.setRows(5);
        table.setStartRowNum(0);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNegativeStartRowNumber() {
        Table table = new Table();
        table.setName("NEGATIVE_START");
        table.setRows(5);
        table.setStartRowNum(-10);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }


    @Test
    public void testTooManyRows() {
        Table table = new Table();
        table.setName("TOO_MANY_ROWS");
        table.setRows(2_001);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testNoFields() {
        Table table = new Table();
        table.setName("NO_FIELDS");
        table.setRows(5);
        table.setStartRowNum(100);
        //table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 1);
    }

    @Test
    public void testEverythingWrong() {
        Table table = new Table();
        table.setName("  ");
        table.setRows(0);
        table.setStartRowNum(0);
        //table.setFields(makeFields());

        List<String> errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertEquals(errors.size(), 4);
    }

    private List<Field> makeFields() {
        List<Field> fields = new ArrayList<>();
        Field age = Field.builder()
                .name("AGE")
                .dmSourceType(Definitions.LONG)
                .rangeLowEnd(18)
                .rangeHighEnd(120)
                .build();
        fields.add(makeIdField());
        fields.add(makeLNameField());
        fields.add(age);

        return fields;
    }

    private List<Field> makeBigFieldList() {
        List<Field> fields = new LinkedList<>();
        fields.add(makeIdField());
        fields.add(makeUuidField());
        fields.add(makeRTextField());
        fields.add(makeTextFromListField());
        fields.add(makeFNameField());
        fields.add(makeLNameField());
        fields.add(makeStreetField());
        fields.add(makeCityField());
        fields.add(makeStateField());
        fields.add(makeBoolField());
        fields.add(makeDateMonYrField());
        fields.add(makeDateInYrField());
        fields.add(makeDateInYearRangeField());
        fields.add(makePatternField());
        fields.add(makeDoubleField());
        fields.add(makeLongField());
        fields.add(makeLongFromListField());
        return fields;
    }

    private Field makeIdField() {
        return Field.builder()
                .name("ID")
                .dmSourceType(Definitions.ID)
                .build();
    }

    private Field makeUuidField() {
        return Field.builder()
                .name("UUID")
                .dmSourceType(Definitions.UUID)
                .build();
    }

    private Field makeRTextField() {
        return Field.builder()
                .name("RTEXT")
                .dmSourceType(Definitions.RTEXT)
                .length(20)
                .build();
    }

    private Field makeTextFromListField() {
        List<String> list = Arrays.asList(new String[]{"aa","bb","cc","dd"});
        Map<String, List<String>> fromLists = new HashMap<>();
        fromLists.put("TEXT_FROM_LIST", list);
        return Field.builder()
                .name("TEXT_FROM_LIST")
                .dmSourceType(Definitions.TEXT_FROM_LIST)
                .fromLists(fromLists)
                .build();
    }

    private Field makeFNameField() {
        return Field.builder()
                .name("FNAME")
                .dmSourceType(Definitions.FNAME)
                .gender("R")
                .build();
    }

    private Field makeLNameField() {
        return Field.builder()
                .name("LNAME")
                .dmSourceType(Definitions.LNAME)
                .build();
    }

    private Field makeStreetField() {
        return Field.builder()
                .name("STREET")
                .dmSourceType(Definitions.STREET)
                .build();
    }

    private Field makeCityField() {
        return Field.builder()
                .name("CITY")
                .dmSourceType(Definitions.CITY)
                .build();
    }

    private Field makeStateField() {
        return Field.builder()
                .name("STATE")
                .dmSourceType(Definitions.STATE)
                .build();
    }

    private Field makeBoolField() {
        return Field.builder()
                .name("BOOL")
                .dmSourceType(Definitions.BOOL)
                .databaseType("boolean")
                .build();
    }

    private Field makeDateMonYrField() {
        return Field.builder()
                .name("DATE_MON_YR")
                .dmSourceType(Definitions.DATE_MON_YR)
                .month(7)
                .year(2001)
                .build();
    }

    private Field makeDateInYrField() {
        return Field.builder()
                .name("DATE_IN_YR")
                .dmSourceType(Definitions.DATE_IN_YR)
                .year(2_003)
                .build();
    }

    private Field makeDateInYearRangeField() {
        return Field.builder()
                .name("DATE_BETW_YRS")
                .dmSourceType(Definitions.DATE_BETW_YRS)
                .yearLowEnd(1_995)
                .yearHighEnd(2_005)
                .build();
    }

    private Field makePatternField() {
        return Field.builder()
                .name("PATTERN")
                .dmSourceType(Definitions.PATTERN)
                .pattern("sn: CCNN-CCCNNN-CNNNN CC")
                .charSymbol("C")
                .numSymbol("N")
                .build();
    }

    private Field makeDoubleField() {
        return Field.builder()
                .name("DOUBLE")
                .dmSourceType(Definitions.DOUBLE)
                .rangeLowEnd(-7)
                .rangeHighEnd(7)
                .precision(3)
                .build();
    }

    private Field makeLongField() {
        return Field.builder()
                .name("DOUBLE")
                .dmSourceType(Definitions.LONG)
                .rangeLowEnd(-20)
                .rangeHighEnd(20)
                .build();
    }

    private Field makeLongFromListField() {
        List<String> list = Arrays.asList(new String[]{"911","538","196","212"});
        Map<String, List<String>> fromLists = new HashMap<>();
        fromLists.put("LONG_FROM_LIST", list);
        return Field.builder()
                .name("LONG_FROM_LIST")
                .dmSourceType(Definitions.LONG_FROM_LIST)
                .fromLists(fromLists)
                .build();
    }

}
