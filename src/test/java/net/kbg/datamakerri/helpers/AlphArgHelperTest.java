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
 *
 *
 */

package net.kbg.datamakerri.helpers;

import net.kb.datamaker.alpha.Gender;
import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.model.MoneySymbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

@SpringBootTest(classes = DatamakerriApplication.class)
public class AlphArgHelperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext appContext;

    @Autowired
    private AlphArgHelper argHelper;

    private MockMvc mockMvc;

    @BeforeClass
    public void init() {
        //mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
    }

    @Test
    public void testHappyPathGenderArg() {
        assertTrue(Gender.MALE.equals(argHelper.genderArg("M").get()));
        assertTrue(Gender.FEMALE.equals(argHelper.genderArg("F").get()));
        Gender g = argHelper.genderArg("R").get();
        assertTrue(Gender.FEMALE.equals(g) ||
                Gender.MALE.equals(g));
    }

    @Test
    public void testBadGenderArg() {
        assertTrue(argHelper.genderArg("K").isEmpty());
    }

    @Test
    public void testHappyPathFormatArg() {
        assertTrue(0 == argHelper.nameFormatArg("FIRST_MI_LAST").get());
        assertTrue(1 == argHelper.nameFormatArg("FIRST_MIDDLE_LAST").get());
        assertTrue(2 == argHelper.nameFormatArg("FIRST_LAST").get());
        assertTrue(3 == argHelper.nameFormatArg("FI_LAST").get());
        assertTrue(4 == argHelper.nameFormatArg("FI_MI_LAST").get());
        assertTrue(5 == argHelper.nameFormatArg("LAST_FIRST").get());
        assertTrue(6 == argHelper.nameFormatArg("LAST_FI").get());
        assertTrue(7 == argHelper.nameFormatArg("LAST_FI_MI").get());
        for (int b = 0; b < 50; ++b) {
            assertTrue(-1 < argHelper.nameFormatArg("RANDOM_NAME_FORMAT").get() &&
                    9 > argHelper.nameFormatArg("RANDOM_NAME_FORMAT").get()
            );
        }
    }

    @Test
    public void testHappyPathFormatArgLowCase() {
        assertTrue(0 == argHelper.nameFormatArg("first_mi_last").get());
        assertTrue(1 == argHelper.nameFormatArg("first_middle_last").get());
        assertTrue(2 == argHelper.nameFormatArg("first_last").get());
        assertTrue(3 == argHelper.nameFormatArg("fi_last").get());
        assertTrue(4 == argHelper.nameFormatArg("fi_mi_last").get());
        assertTrue(5 == argHelper.nameFormatArg("last_first").get());
        assertTrue(6 == argHelper.nameFormatArg("last_fi").get());
        assertTrue(7 == argHelper.nameFormatArg("last_fi_mi").get());
        for (int b = 0; b < 50; ++b) {
            assertTrue(-1 < argHelper.nameFormatArg("random_name_format").get() &&
                    9 > argHelper.nameFormatArg("random_name_format").get()
            );
        }
    }

    @Test
    public void testBadFormatArg() {
        assertTrue(argHelper.nameFormatArg("NOT_AN_OPTION").isEmpty());
    }

    @Test
    public void getMoneySymbolByNameHappyPathTest() {
        Optional<MoneySymbol> opMs = argHelper.getMoneySymbolByName("EURO");
        assertTrue(opMs.isPresent());
        System.out.println(opMs.get());

        opMs = argHelper.getMoneySymbolByName("euro");
        assertTrue(opMs.isPresent());
    }

    @Test
    public void getMoneySymbolByNameBadArg() {
        Optional<MoneySymbol> opMs = argHelper.getMoneySymbolByName("BLIVIT");
        assertTrue(opMs.isEmpty());
    }

    @Test
    public void getCharFromStringListHappyPath() {
        List<String> list = Arrays.asList(new String[]{"a", "b",  "c",  "d",  "e", });
        Optional<Character> optChar = argHelper.charFromStringList(list);
        assertTrue(optChar.isPresent());
        assertFalse(optChar.isEmpty());
        System.out.println("char from list = " + optChar.get());
    }

    @Test
    public void getCharFromStringListSomeBadEntries() {
        List<String> list = Arrays.asList(new String[]{"a", "bb",  "c",  "dd",  "ee", });
        Optional<Character> optChar = argHelper.charFromStringList(list);
        assertTrue(optChar.isPresent());
        assertFalse(optChar.isEmpty());
        System.out.println("char from list = " + optChar.get());
    }

    @Test
    public void getCharFromStringListOneGoodEntry() {
        // two is the minimum in the input list.  Selecting froma list of 1 is pointless.
        List<String> list = Arrays.asList(new String[]{"aa", "bb",  "c",  "dd",  "ee"});
        Optional<Character> optChar = argHelper.charFromStringList(list);
        assertFalse(optChar.isPresent());
        assertTrue(optChar.isEmpty());
    }

    @Test
    public void getCharFromStringEmptyList() {
        // two is the minimum in the input list.  Selecting froma list of 1 is pointless.
        List<String> list = Arrays.asList(new String[]{});
        Optional<Character> optChar = argHelper.charFromStringList(list);
        assertFalse(optChar.isPresent());
        assertTrue(optChar.isEmpty());
    }

    @Test
    public void testMakePrecisionPattern() {
        for (int b = 1; b < 6; ++b) {
            String rslt = argHelper.makePrecisionPattern(b);
            System.out.println("precision format = " + rslt);
            assertEquals(rslt.length(), b + 2);
            assertTrue(rslt.matches("#\\.#+"));
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMakePrecisionPatternBadArg() {
        String rslt = argHelper.makePrecisionPattern(0);
    }
}
