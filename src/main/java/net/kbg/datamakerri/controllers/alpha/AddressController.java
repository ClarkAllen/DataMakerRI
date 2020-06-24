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

package net.kbg.datamakerri.controllers.alpha;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/alph")
public class AddressController {

    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AlphArgHelper argHelper;

    @Autowired
    NameFormatProcessor nameFormatProcessor;

    @GetMapping("/address")
    public ResponseEntity makeAddress(@RequestParam String gender, @RequestParam String namefmt) {
        log.debug("address to person : gender=" + gender + ", namefmt=" + namefmt);
        Optional<Gender> opGen = argHelper.genderArg(gender);
        Optional<Integer> opInt = argHelper.nameFormatArg(namefmt);
        if (opGen.isEmpty() || opInt.isEmpty()) {
            log.error("400 Bad arguments");
            ErrorMsg errorMsg = new ErrorMsg("400", "Bad gender or name format argument");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        Gender gen = argHelper.genderArg(gender).get();
        int fmt = opInt.get();

        if (namefmt.toUpperCase().contains("FI_") || namefmt.toUpperCase().equals("LAST_FI")) {
            Optional<Address> optAddr = nameFormatProcessor.processNameFormat(namefmt);
            if (optAddr.isEmpty()) {
                log.error("unable to get Address for spec " + namefmt);
                throw new RuntimeException("Address processing error");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(optAddr.get());
        }

        String addr = AddressFactory.addressToPerson(gen, fmt);
        Address rslt = new Address(addr);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rslt);
    }
}
