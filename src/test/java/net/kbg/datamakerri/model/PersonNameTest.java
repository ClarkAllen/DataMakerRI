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


import net.kb.datamaker.alpha.Gender;
import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


@SpringBootTest(classes = DatamakerriApplication.class)
public class PersonNameTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext appContext;

    @Autowired
    AlphArgHelper argHelper;


    @Test
    public void testConstructorForFirstMiLast() {
        String fmt = "FIRST_MI_LAST";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, true);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getMiddle().matches("[A-Z]{1,1}\\."));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]{1,1}[a-z]{1,} [A-Z]{1,1}\\. [A-Z]{1,1}[a-z]{1,}"));
        }
    }

    private void verifyHasContent(PersonName name, boolean testMiddle) {
        assertTrue(argHelper.hasContent(name.getName()));
        assertTrue(argHelper.hasContent(name.getGender()));
        assertTrue(argHelper.hasContent(name.getNameFmt()));
        assertTrue(argHelper.hasContent(name.getFirst()));
        if (testMiddle) assertTrue(argHelper.hasContent(name.getMiddle()));
        assertTrue(argHelper.hasContent(name.getLast()));
    }

    @Test
    public void testConstructorForFirstMiddleLast() {
        String fmt = "FIRST_MIDDLE_LAST";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, true);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getMiddle().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]{1,1}[a-z]{1,} [A-Z]{1,1}[a-z]{1,} [A-Z]{1,1}[a-z]{1,}"));
        }
    }

    @Test
    public void testConstructorForFirstLast() {
        String fmt = "FIRST_LAST";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, false);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getMiddle().equals(""));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]{1,1}[a-z]{1,} [A-Z]{1,1}[a-z]{1,}"));
        }
    }

    @Test
    public void testConstructorForFiLast() {
        String fmt = "FI_LAST";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, false);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]\\."));
            assertTrue(name.getMiddle().equals(""));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]\\. [A-Z]{1,1}[a-z]{1,}"));
        }
    }

    @Test
    public void testConstructorForFiMiLast() {
        String fmt = "FI_MI_LAST";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, true);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]\\."));
            assertTrue(name.getMiddle().matches("[A-Z]\\."));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]\\. [A-Z]\\. [A-Z]{1,1}[a-z]{1,}"));
        }
    }

    @Test
    public void testConstructorForLastFirst() {
        String fmt = "LAST_FIRST";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, false);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getMiddle().equals(""));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]{1,1}[a-z]{1,}, [A-Z]{1,1}[a-z]{1,}"));
        }
    }

    @Test
    public void testConstructorForLastFi() {
        String fmt = "LAST_FI";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, false);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]\\."));
            assertTrue(name.getMiddle().equals(""));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]{1,1}[a-z]{1,}, [A-Z]\\."));
        }
    }

    @Test
    public void testConstructorForLastFiMi() {
        String fmt = "LAST_FI_MI";
        for (int k = 0; k < 500; ++k) {
            PersonName name = new PersonName(
                    Gender.findRandomGender(), fmt, argHelper.nameFormatArg(fmt).get());
            System.out.println(name);
            // PersonName{gender='M', nameFmt='FIRST_MI_LAST', name='Morgan B. Alvarez', first='Morgan', middle='B.', last='Alvarez'}
            verifyHasContent(name, true);

            assertTrue(name.getGender().matches("[FM]{1,1}"));
            assertTrue(name.getNameFmt().equals(fmt));
            assertTrue(name.getFirst().matches("[A-Z]\\."));
            assertTrue(name.getMiddle().matches("[A-Z]\\."));
            assertTrue(name.getLast().matches("[A-Z]{1,1}[a-z]{1,}"));
            assertTrue(name.getName().matches("[A-Z]{1,1}[a-z]{1,}, [A-Z]\\. [A-Z]\\."));
        }
    }

}
