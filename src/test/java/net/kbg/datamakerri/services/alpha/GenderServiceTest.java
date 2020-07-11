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
import net.kbg.datamakerri.model.GenderMod;
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
public class GenderServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GenderService service;

    @Autowired
    private AlphArgHelper argHelper;

    @Test
    public void testMakeGender() {
        List<String> args = Arrays.asList(new String[]{"F","f","M","m","R","r"});
        for (String arg : args) {
            Optional<GenderMod> gmod = service.makeGender(arg);
            assertTrue(gmod.isPresent());
            assertTrue(argHelper.hasContent(gmod.get().getLongText()));
            assertTrue(argHelper.hasContent(gmod.get().getShortText()));
        }
    }


    @Test
    public void testMakeGenderBadArg() {
        List<String> args = Arrays.asList(new String[]{"X"," "});
        for (String arg : args) {
            Optional<GenderMod> gmod = service.makeGender(arg);
            assertFalse(gmod.isPresent());
        }
    }

}
