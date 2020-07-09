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

package net.kbg.datamakerri.services.bool;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.model.BooleanValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

@SpringBootTest(classes = DatamakerriApplication.class)
public class BooleanServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BooleanService booleanService;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeRandomBoolean() {
        BooleanValue val = booleanService.makeRandomBoolean();
        assertNotNull(val);
        if (val.isTruth()) {
            assertTrue(val.getIntValue() == 1);
            assertTrue(val.getStringValue().equals("True"));
        } else {
            assertTrue(val.getIntValue() == 0);
            assertTrue(val.getStringValue().equals("False"));
        }
    }

    @Test
    public void testMakeIntBoolean() {
        int itrue = 10;
        int ifalse = -10;
        BooleanValue val = booleanService.makeIntBoolean(itrue, ifalse).get();
        if (val.getIntValue() == itrue) {
            assertTrue(val.isTruth());
            assertTrue(val.getStringValue().equals("True"));
        } else if (val.getIntValue() == ifalse) {
            assertFalse(val.isTruth());
            assertTrue(val.getStringValue().equals("False"));
        } else {
            fail();
        }
    }

    @Test
    public void testMakeStringBoolean() {
        String strT = "yes";
        String strF = "no";
        BooleanValue val = booleanService.makeStringBoolean(strT, strF).get();
        if (val.getStringValue().equals(strT)) {
            assertTrue(val.isTruth());
            assertTrue(val.getIntValue() == 1);
        } else if (val.getStringValue().equals(strF)) {
            assertFalse(val.isTruth());
            assertTrue(val.getIntValue() == 0);
        } else {
            fail();
        }
    }


    /*
        Expected Failure Tests
     */

    public void testMakeIntBooleanBadArgs() {
        // if true and false are the same this is an error.
        int itrue = 10;
        int ifalse = 10;
        Optional<BooleanValue> optBool = booleanService.makeIntBoolean(itrue, ifalse);
        assertTrue(optBool.isEmpty());
    }

    public void testMakeStringBooleanBadArgs() {
        // if true and false are the same this is an error.
        String strT = "T";
        String strF = "T";
        Optional<BooleanValue> optBool = booleanService.makeStringBoolean(strT, strF);
        assertTrue(optBool.isEmpty());
    }

    public void testMakeStringBooleanEmptyArgs() {
        String empty = "";
        String strF = "F";
        Optional<BooleanValue> optBool = booleanService.makeStringBoolean(empty, strF);
        assertTrue(optBool.isEmpty());

        optBool = booleanService.makeStringBoolean(strF, empty);
        assertTrue(optBool.isEmpty());
    }

    public void testMakeStringBooleanNullArgs() {
        String nothing = null;
        String strF = "F";
        Optional<BooleanValue> optBool = booleanService.makeStringBoolean(nothing, strF);
        assertTrue(optBool.isEmpty());

        optBool = booleanService.makeStringBoolean(strF, nothing);
        assertTrue(optBool.isEmpty());
    }

}
