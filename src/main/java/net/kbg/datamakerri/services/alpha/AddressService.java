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

import net.kb.datamaker.alpha.AddressFactory;
import net.kb.datamaker.alpha.Gender;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.helpers.NameFormatProcessor;
import net.kbg.datamakerri.model.Address;
import net.kbg.datamakerri.model.ErrorMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AlphArgHelper argHelper;

    @Autowired
    private NameFormatProcessor nameFormatProcessor;

    public Optional<Address> makeAddress(String gender, String namefmt) {
        log.debug("address to person : gender=" + gender + ", namefmt=" + namefmt);
        Optional<Gender> opGen = argHelper.genderArg(gender);
        Optional<Integer> opInt = argHelper.nameFormatArg(namefmt);
        if (opGen.isEmpty() || opInt.isEmpty()) {
            log.error("Bad argument/s for gender and name");
            return Optional.empty();
        }
        Gender gen = argHelper.genderArg(gender).get();
        int fmt = opInt.get();

        if (namefmt.toUpperCase().contains("FI_") || namefmt.toUpperCase().equals("LAST_FI")) {
            Optional<Address> optAddr = nameFormatProcessor.processNameFormat(namefmt);
            if (optAddr.isEmpty()) {
                log.error("unable to get Address for spec " + namefmt);
                return Optional.empty();
            }
            return optAddr;
        }

        String addr = AddressFactory.addressToPerson(gen, fmt);
        Address rslt = new Address(addr);
        return Optional.of(rslt);
    }
}
