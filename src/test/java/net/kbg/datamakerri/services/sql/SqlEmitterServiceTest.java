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

package net.kbg.datamakerri.services.sql;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.model.Field;
import net.kbg.datamakerri.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class SqlEmitterServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SqlEmitterService sqlService;

    @Test
    public void testEmitUuid() {
        List<Field> fields = new LinkedList<>();
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field uuid = Field.builder()
                .name("UUID")
                .dmSourceType("uuid")
                .build();
        fields.add(id);
        fields.add(uuid);
        Table table = Table.builder()
                .name("UNIQUE_IDS")
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains("INSERT INTO UNIQUE_IDS (ID,UUID)"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

    @Test
    public void testEmitRandomText() {
        List<Field> fields = new LinkedList<>();
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field rtext = Field.builder()
                .name("COMMENT")
                .dmSourceType("rtext")
                .length(35)
                .build();
        fields.add(id);
        fields.add(rtext);
        Table table = Table.builder()
                .name("COMMENTS")
                .rows(5)
                .startRowNum(20)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains("INSERT INTO COMMENTS (ID,COMMENT)"));
        assertTrue(sql.get(1).contains("VALUES (20,"));
        assertTrue(sql.get(2).contains("VALUES (21,"));
    }

    @Test
    public void testEmitFNameLName() {
        List<Field> fields = new LinkedList<>();
        String tableName = "MEMBERS";
        String field1Name = "FIRST_NAME";
        String field2Name = "LAST_NAME";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field fname = Field.builder()
                .name(field1Name)
                .dmSourceType("fname")
                .gender("R")
                .build();
        Field lname = Field.builder()
                .name(field2Name)
                .dmSourceType("lname")
                .gender("R")
                .build();
        fields.add(id);
        fields.add(fname);
        fields.add(lname);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(20)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ","
                        + field2Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (20,"));
        assertTrue(sql.get(2).contains("VALUES (21,"));
    }

    @Test
    public void testEmitStreetCityState() {
        List<Field> fields = new LinkedList<>();
        String tableName = "ADDRESSES";
        String field1Name = "STREET";
        String field2Name = "CITY";
        String field3Name = "STATE";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field street = Field.builder()
                .name(field1Name)
                .dmSourceType("street")
                .build();
        Field city = Field.builder()
                .name(field2Name)
                .dmSourceType("city")
                .build();
        Field state = Field.builder()
                .name(field3Name)
                .dmSourceType("state")
                .build();
        fields.add(id);
        fields.add(street);
        fields.add(city);
        fields.add(state);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(20)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ","
                        + field2Name + ","
                        + field3Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (20,"));
        assertTrue(sql.get(2).contains("VALUES (21,"));
    }

    @Test
    public void testEmitBooleanValues() {
        List<Field> fields = new LinkedList<>();
        String tableName = "TRUTHINESS";
        String field1Name = "TRUE_INT";
        String field2Name = "TRUE_BOOL";
        String field3Name = "TRUE_STRING";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field boolint = Field.builder()
                .name(field1Name)
                .dmSourceType("bool")
                .databaseType("int")
                .build();
        Field boolTF = Field.builder()
                .name(field2Name)
                .dmSourceType("bool")
                .databaseType("boolean")
                .build();
        Field boolstr = Field.builder()
                .name(field3Name)
                .dmSourceType("bool")
                .databaseType("string")
                .build();
        fields.add(id);
        fields.add(boolint);
        fields.add(boolTF);
        fields.add(boolstr);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(20)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ","
                        + field2Name + ","
                        + field3Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (20,"));
        assertTrue(sql.get(2).contains("VALUES (21,"));
    }

    @Test
    public void testEmitDates() {
        List<Field> fields = new LinkedList<>();
        String tableName = "TIMELINESS";
        String field1Name = "MONTH_YEAR";
        String field2Name = "IN_YEAR";
        String field3Name = "IN_YEAR_RANGE";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field moyr = Field.builder()
                .name(field1Name)
                .dmSourceType("datemonyr")
                .month(6)
                .year(2011)
                .build();
        Field inyr = Field.builder()
                .name(field2Name)
                .dmSourceType("dateinyr")
                .year(2012)
                .build();
        Field yrrange = Field.builder()
                .name(field3Name)
                .dmSourceType("datebetwyrs")
                .yearLowEnd(2013)
                .yearHighEnd(2019)
                .build();
        fields.add(id);
        fields.add(moyr);
        fields.add(inyr);
        fields.add(yrrange);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ","
                        + field2Name + ","
                        + field3Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

    @Test
    public void testEmitPattern() {
        List<Field> fields = new LinkedList<>();
        String tableName = "PATTERNS";
        String field1Name = "LICENCE_TAG";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field tag = Field.builder()
                .name(field1Name)
                .dmSourceType("pattern")
                .pattern("ccc nnnn")
                .charSymbol("c")
                .numSymbol("n")
                .build();
        fields.add(id);
        fields.add(tag);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

    @Test
    public void testEmitDouble() {
        List<Field> fields = new LinkedList<>();
        String tableName = "DOUBLES";
        String field1Name = "PRICE";
        String field2Name = "INDEX";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field price = Field.builder()
                .name(field1Name)
                .dmSourceType("double")
                .rangeLowEnd(5)
                .rangeHighEnd(15)
                .precision(2)
                .build();
        Field index = Field.builder()
                .name(field2Name)
                .dmSourceType("double")
                .rangeLowEnd(1)
                .rangeHighEnd(3)
                .precision(3)
                .build();
        fields.add(id);
        fields.add(price);
        fields.add(index);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ","
                        + field2Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

    @Test
    public void testEmitLongFromRange() {
        List<Field> fields = new LinkedList<>();
        String tableName = "LONG_VALUES";
        String field1Name = "SIZE_MM";
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field size = Field.builder()
                .name(field1Name)
                .dmSourceType("long")
                .rangeLowEnd(55)
                .rangeHighEnd(170)
                .build();
        fields.add(id);
        fields.add(size);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

    @Test
    public void testEmitLongFromListHappyPath() {
        List<String> strlst = Arrays.asList(new String[]{"2", "3", "5", "7", "11", "13"});
        Map<String, List<String>> fromList = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        String tableName = "LONG_VALUES";
        String field1Name = "SIZE_OPT_1";
        fromList.put(field1Name, strlst);
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field size = Field.builder()
                .name(field1Name)
                .dmSourceType("longfromlist")
                .fromLists(fromList)
                .build();
        fields.add(id);
        fields.add(size);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

    @Test
    public void testEmitTextFromListHappyPath() {
        List<String> strList = Arrays.asList(new String[]{"ab", "cd", "ef", "gh", "ij"});
        Map<String, List<String>> fromList = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        String tableName = "TEXT_LIST_VALUES";
        String field1Name = "SIZE_DOPS_1";
        fromList.put(field1Name, strList);
        Field id = Field.builder().name("ID").dmSourceType("id").build();
        Field dops = Field.builder()
                .name(field1Name)
                .dmSourceType("textfromlist")
                .fromLists(fromList)
                .build();
        fields.add(id);
        fields.add(dops);
        Table table = Table.builder()
                .name(tableName)
                .rows(5)
                .startRowNum(1)
                .fields(fields)
                .build();
        List<String> sql = sqlService.emit(table);
        for (String s : sql) {
            System.out.println(s);
        }
        assertNotNull(sql);
        assertTrue(sql.size() == 6);
        assertTrue(sql.get(0).contains(
                "INSERT INTO "
                        + tableName +
                        " (ID,"
                        + field1Name + ")"));
        assertTrue(sql.get(1).contains("VALUES (1,"));
        assertTrue(sql.get(2).contains("VALUES (2,"));
    }

}
