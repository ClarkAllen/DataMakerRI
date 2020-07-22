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
        assertTrue(sql.size() > 0);

    }

}
