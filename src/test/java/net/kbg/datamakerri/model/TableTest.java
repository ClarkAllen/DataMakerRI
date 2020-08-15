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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class TableTest {

    @Test
    public void testValidate() {
        Table table = new Table();
        table.setName("VARIOUS_THINGS");
        table.setRows(5);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 0);
    }

    @Test
    public void testNoTableName() {
        Table table = new Table();
        table.setName("");
        table.setRows(5);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void testNoRows() {
        Table table = new Table();
        table.setName("NO_ROWS");
        table.setRows(0);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void testNegativeRows() {
        Table table = new Table();
        table.setName("NEGATIVE_ROWS");
        table.setRows(-4);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void testZeroStartRowNumber() {
        Table table = new Table();
        table.setName("ZERO_START");
        table.setRows(5);
        table.setStartRowNum(0);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void testNegativeStartRowNumber() {
        Table table = new Table();
        table.setName("NEGATIVE_START");
        table.setRows(5);
        table.setStartRowNum(-10);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }


    @Test
    public void testTooManyRows() {
        Table table = new Table();
        table.setName("TOO_MANY_ROWS");
        table.setRows(2_001);
        table.setStartRowNum(100);
        table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void testNoFields() {
        Table table = new Table();
        table.setName("NO_FIELDS");
        table.setRows(5);
        table.setStartRowNum(100);
        //table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 1);
    }

    @Test
    public void testEverythingWrong() {
        Table table = new Table();
        table.setName("  ");
        table.setRows(0);
        table.setStartRowNum(0);
        //table.setFields(makeFields());

        List errors = table.validate();
        assertNotNull(errors);
        System.out.println("Table errors : " + errors);
        assertTrue(errors.size() == 4);
    }

    private List<Field> makeFields() {
        List<Field> fields = new ArrayList<>();
        Field idField = Field.builder()
                .name("ID")
                .dmSourceType(Definitions.ID)
                .build();
        Field lName = Field.builder()
                .name("LAST_NAME")
                .dmSourceType(Definitions.LNAME)
                .build();
        Field age = Field.builder()
                .name("AGE")
                .dmSourceType(Definitions.LONG)
                .rangeLowEnd(18)
                .rangeHighEnd(120)
                .build();
        fields.add(idField);
        fields.add(lName);
        fields.add(age);

        return fields;
    }

}
