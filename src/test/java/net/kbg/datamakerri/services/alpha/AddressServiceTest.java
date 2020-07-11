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
import net.kbg.datamakerri.model.Address;
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
public class AddressServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AddressService service;

    @Autowired
    private AlphArgHelper argHelper;

    /*
        Happy Path Tests
     */

    @Test
    public void testMakeAddressVarryingGenders() {
        List<String> genders = Arrays.asList(new String[]{"F","M","R"});
        for (String gender : genders) {
            Optional<Address> opAddr = service.makeAddress(
                    gender, "FIRST_MIDDLE_LAST");
            assertTrue(opAddr.isPresent());
            Address addr = opAddr.get();
            assertTrue(argHelper.hasContent(addr.getName()));
            assertTrue(argHelper.hasContent(addr.getLine1()));
            assertTrue(argHelper.hasContent(addr.getCity()));
            assertTrue(argHelper.hasContent(addr.getState()));
            assertTrue(argHelper.hasContent(addr.getZip()));
        }
    }

    @Test
    public void testMakeAddressVarryingFormats() {
        List<String> formats = Arrays.asList(
                new String[]{
                        "FIRST_MI_LAST",
                        "FIRST_MIDDLE_LAST",
                        "FIRST_LAST",
                        "FI_LAST",
                        "FI_MI_LAST",
                        "LAST_FIRST",
                        "LAST_FI",
                        "LAST_FI_MI",
                        "RANDOM_NAME_FORMAT"
                });

        for (String format : formats) {
            Optional<Address> opAddr = service.makeAddress("R", format);
            assertTrue(opAddr.isPresent());
            Address addr = opAddr.get();
            assertTrue(argHelper.hasContent(addr.getName()));
            assertTrue(argHelper.hasContent(addr.getLine1()));
            assertTrue(argHelper.hasContent(addr.getCity()));
            assertTrue(argHelper.hasContent(addr.getState()));
            assertTrue(argHelper.hasContent(addr.getZip()));
        }
    }


    /*
        Expected Failure Tests
     */

    @Test
    public void testMakeAddressBadGenderArg() {
        Optional<Address> opAddr = service.makeAddress(
                "X", "FIRST_LAST");
        assertFalse(opAddr.isPresent());
    }

    @Test
    public void testMakeAddressBadFormatArg() {
        Optional<Address> opAddr = service.makeAddress(
                "R", "BAD_NAME_FORMAT_ARG");
        assertFalse(opAddr.isPresent());
    }

}
