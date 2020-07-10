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
import net.kbg.datamakerri.model.UspsSecondaryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = DatamakerriApplication.class)
public class UspsUnitServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UspsUnitService service;

    @Test
    public void testMakeUspsUnit() {
        UspsSecondaryUnit unit = service.makeUspsUnit();
        assertNotNull(unit);
        assertTrue(unit.getShortText().length() > 0);
        assertTrue(unit.getLongText().length() > 0);
    }
}
