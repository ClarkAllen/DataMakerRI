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
import net.kbg.datamakerri.services.alpha.AddressService;
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
    private AddressService service;

    @GetMapping("/address")
    public ResponseEntity makeAddress(@RequestParam String gender, @RequestParam String namefmt) {
        log.debug("address to person : gender=" + gender + ", namefmt=" + namefmt);
        Optional<Address> opRslt = service.makeAddress(gender, namefmt);
        if (opRslt.isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg(
                    "400", "Bad gender argument or " +
                    "name format argument.  Please check your arguments.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(opRslt.get());
    }
}
