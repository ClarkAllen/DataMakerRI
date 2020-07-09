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
import net.kbg.datamakerri.model.TextResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class TextServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TextService service;

    @Autowired
    AlphArgHelper argHelper;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeUuid() {
        TextResult tr = service.makeUuid();
        assertNotNull(tr);
        //TextResult{value='22a2bcbc-fada-4177-a39d-dea3313b9899'}
        assertTrue(tr.getValue().length() == 36);
    }

    @Test
    public void testSelectFromList() {
        List<String> inputList = Arrays.asList(
                new String[]{"2", "3", "5", "7", "11", "13", "17"});
        Optional<TextResult> optTxt = service.selectFromList(inputList);
        assertTrue(optTxt.isPresent());
        TextResult rslt = optTxt.get();
        assertTrue(argHelper.hasContent(rslt.getValue()));
    }

    @Test
    public void testMakeText() {
        int length = 256;
        Optional<TextResult> optTxt = service.makeText(length);
        assertTrue(optTxt.isPresent());
        TextResult rslt = optTxt.get();
        assertTrue(argHelper.hasContent(rslt.getValue()));
    }


    /*
        Expected Failure Tests
     */

    @Test
    public void testSelectFromListEmpty() {
        List<String> inputList = new ArrayList<>();
        Optional<TextResult> optTxt = service.selectFromList(inputList);
        assertTrue(optTxt.isEmpty());
    }

    @Test
    public void testSelectFromListTooSmall() {
        List<String> inputList = Arrays.asList(new String[]{"5"});
        Optional<TextResult> optTxt = service.selectFromList(inputList);
        assertTrue(optTxt.isEmpty());
    }

    @Test
    public void testMakeTextZero() {
        Optional<TextResult> optTxt = service.makeText(0);
        assertTrue(optTxt.isEmpty());
    }

    @Test
    public void testMakeTextTooBig() {
        Optional<TextResult> optTxt = service.makeText(2049);
        assertTrue(optTxt.isEmpty());
    }

}
