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
import net.kbg.datamakerri.model.Letter;
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
public class LetterServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private LetterService service;

    @Autowired
    private AlphArgHelper argHelper;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeLetter() {
        Letter letr = service.makeLetter();
        assertTrue(argHelper.hasContent(letr.getSymbol()));
        assertTrue(letr.getSymbol().length() == 1);
    }

    @Test
    public void testSelectFromList() {
        List<String> input = Arrays.asList(new String[]{"a", "e", "i", "o", "u"});
        Optional<Letter> opLetr = service.selectFromList(input);
        assertTrue(opLetr.isPresent());
        assertTrue(opLetr.get().getSymbol().matches("[aeiou]{1,1}"));
    }



    /*
        Expected Failure Tests
     */

    @Test
    public void testSelectFromListBadArg() {
        List<String> input = Arrays.asList(
                new String[]{"aa", "ee", "i", "oo", "uu"});
        Optional<Letter> opLetr = service.selectFromList(input);
        assertFalse(opLetr.isPresent());
    }

    @Test
    public void testSelectFromListEmptyArg() {
        List<String> input = new ArrayList<>();
        Optional<Letter> opLetr = service.selectFromList(input);
        assertFalse(opLetr.isPresent());
    }

}
