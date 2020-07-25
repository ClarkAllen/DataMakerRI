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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class SqlEmitterServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SqlEmitterService sqlService;

    @Test
    public void testEmitUuid() {
        Map<String, List<String>> fromLists = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        Field id = new Field("ID", "id", "",
                36, 1, Long.MAX_VALUE, "", "");
        Field uuid = new Field("UUID", "uuid", "",
                1, 1, 1,"", "");
        fields.add(id);
        fields.add(uuid);
        Table table = new Table("UNIQUE_IDS", 5, fields);
        List<String> sql = sqlService.emit(1, table, fromLists);
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
        Map<String, List<String>> fromLists = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        Field id = new Field("ID", "id", "",
                36, 1, Long.MAX_VALUE, "", "");
        Field rtext = new Field("COMMENT", "rtext", "",
                35, 1, 1,"", "");
        fields.add(id);
        fields.add(rtext);
        Table table = new Table("COMMENTS", 5, fields);
        List<String> sql = sqlService.emit(20, table, fromLists);
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
        Map<String, List<String>> fromLists = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        String tableName = "MEMBERS";
        String field1Name = "FIRST_NAME";
        String field2Name = "LAST_NAME";
        Field id = new Field("ID", "id", "",
                36, 1, Long.MAX_VALUE, "", "");
        Field fname = new Field(field1Name, "fname", "",
                1, 1, 1,"R", "");
        Field lname = new Field(field2Name, "lname", "",
                1, 1, 1,"R", "");
        fields.add(id);
        fields.add(fname);
        fields.add(lname);
        Table table = new Table(tableName, 5, fields);
        List<String> sql = sqlService.emit(20, table, fromLists);
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
        Map<String, List<String>> fromLists = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        String tableName = "ADDRESSES";
        String field1Name = "STREET";
        String field2Name = "CITY";
        String field3Name = "STATE";
        Field id = new Field("ID", "id", "",
                36, 1, Long.MAX_VALUE, "", "");
        Field street = new Field(field1Name, "street", "",
                1, 1, 1,"", "");
        Field city = new Field(field2Name, "city", "",
                1, 1, 1,"", "");
        Field state = new Field(field3Name, "state", "",
                1, 1, 1,"", "");
        fields.add(id);
        fields.add(street);
        fields.add(city);
        fields.add(state);
        Table table = new Table(tableName, 5, fields);
        List<String> sql = sqlService.emit(20, table, fromLists);
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
        Map<String, List<String>> fromLists = new HashMap<>();
        List<Field> fields = new LinkedList<>();
        String tableName = "TRUTHINESS";
        String field1Name = "TRUE_INT";
        String field2Name = "TRUE_BOOL";
        String field3Name = "TRUE_STRING";
        Field id = new Field("ID", "id", "",
                36, 1, Long.MAX_VALUE, "", "");
        Field boolint = new Field(field1Name, "bool", "int",
                1, 1, 1,"", "");
        Field boolTF = new Field(field2Name, "bool", "boolean",
                1, 1, 1,"", "");
        Field boolstr = new Field(field3Name, "bool", "string",
                1, 1, 1,"", "");
        fields.add(id);
        fields.add(boolint);
        fields.add(boolTF);
        fields.add(boolstr);
        Table table = new Table(tableName, 5, fields);
        List<String> sql = sqlService.emit(20, table, fromLists);
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

}
