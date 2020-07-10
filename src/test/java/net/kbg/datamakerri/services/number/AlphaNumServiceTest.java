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

package net.kbg.datamakerri.services.number;

import net.kbg.datamakerri.DatamakerriApplication;
import net.kbg.datamakerri.input.PatternTemplate;
import net.kbg.datamakerri.model.TextResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class AlphaNumServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AlphaNumService ans;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeAlphaNumPatternText() {
        PatternTemplate pt = new PatternTemplate(
                "ccnn-nnnn:cc cc", "c","n");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isPresent());
        String pattern = optTR.get().getValue();
        assertEquals(pattern.length(), 15);
        System.out.println("pattern = " + pattern);
        assertTrue(pattern.matches("[a-zA-Z]{2,2}[0-9]{2,2}-[0-9]{4,4}:[a-zA-Z]{2,2} [a-zA-Z]{2,2}"));
    }

    @Test
    public void testMakeAlphaNumPatternOnlySymbol1() {
        PatternTemplate pt = new PatternTemplate(
                "cccc-cccc", "c","n");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isPresent());
        String pattern = optTR.get().getValue();
        assertEquals(pattern.length(), 9);
        System.out.println("pattern = " + pattern);
        assertTrue(pattern.matches("[a-zA-Z]{4,4}-[a-zA-Z]{4,4}"));
    }

    @Test
    public void testMakeAlphaNumPatternOnlySymbol2() {
        PatternTemplate pt = new PatternTemplate(
                "nnnn-nnnn", "c","n");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isPresent());
        String pattern = optTR.get().getValue();
        assertEquals(pattern.length(), 9);
        System.out.println("pattern = " + pattern);
        assertTrue(pattern.matches("[0-9]{4,4}-[0-9]{4,4}"));
    }



    /*
        Expected Failure Tests
     */

    @Test
    public void testAlphaNumPatternHasNoSymbols() {
        PatternTemplate pt = new PatternTemplate(
                "has no symbols in pattern", "Z","K");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isEmpty());
    }

    @Test
    public void testAlphaNumPatternBackSlashInSymbol1() {
        PatternTemplate pt = new PatternTemplate(
                "KKKKKKK", "\\","K");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isEmpty());
    }

    @Test
    public void testAlphaNumPatternBackSlashInSymbol2() {
        PatternTemplate pt = new PatternTemplate(
                "ZZZZZZZZZZZZ", "Z","\\");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isEmpty());
    }

    @Test
    public void testAlphaNumPatternLongSymbol1() {
        PatternTemplate pt = new PatternTemplate(
                "Long symbol ccc n", "ccc","n");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isEmpty());
    }

    @Test
    public void testAlphaNumPatternLongSymbol2() {
        PatternTemplate pt = new PatternTemplate(
                "Long symbol c nnn", "c","nnn");

        Optional<TextResult> optTR = ans.makeAlphaNumPatternText(pt);

        assertTrue(optTR.isEmpty());
    }

}
