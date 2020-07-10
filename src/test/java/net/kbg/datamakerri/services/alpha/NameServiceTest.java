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

package net.kbg.datamakerri.services.alpha;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.PersonName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class NameServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    NameService service;

    @Autowired
    AlphArgHelper argHelper;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeNameOfPersonGenders() {
        List<String> glist = Arrays.asList(new String[]{"M", "F", "R"});
        for (String gender : glist) {
            Optional<PersonName> optName = service.makeNameOfPerson(
                    gender, "FIRST_MIDDLE_LAST");
            assertTrue(optName.isPresent());
            PersonName name = optName.get();
            assertTrue(argHelper.hasContent(name.getName()));
            assertTrue(argHelper.hasContent(name.getNameFmt()));
            assertTrue(argHelper.hasContent(name.getFirst()));
            assertTrue(argHelper.hasContent(name.getMiddle()));
            assertTrue(argHelper.hasContent(name.getLast()));
            assertTrue(argHelper.hasContent(name.getGender()));
        }
    }

    @Test
    public void testNameOfPersonRandomFormats() {
        for (int g = 0; g < 50; ++g) {
            Optional<PersonName> optName = service.makeNameOfPerson(
                    "R", "RANDOM_NAME_FORMAT");
            assertTrue(optName.isPresent());
            PersonName name = optName.get();
            assertTrue(argHelper.hasContent(name.getName()));
            assertTrue(argHelper.hasContent(name.getNameFmt()));
            assertTrue(argHelper.hasContent(name.getFirst()));
            assertTrue(argHelper.hasContent(name.getLast()));
            assertTrue(argHelper.hasContent(name.getGender()));
        }
    }



    /*
        Expected Failure Tests
     */

    @Test
    public void testMakeNameOfPersonBadGender() {
        List<String> glist = Arrays.asList(new String[]{"M", "F", "R"});
        Optional<PersonName> optName = service.makeNameOfPerson(
                "X", "FIRST_MIDDLE_LAST");
        assertFalse(optName.isPresent());
    }

    @Test
    public void testMakeNameOfPersonBadFormat() {
        List<String> glist = Arrays.asList(new String[]{"M", "F", "R"});
        Optional<PersonName> optName = service.makeNameOfPerson(
                "R", "MESSED_UP_FORMAT");
        assertFalse(optName.isPresent());
    }

}
