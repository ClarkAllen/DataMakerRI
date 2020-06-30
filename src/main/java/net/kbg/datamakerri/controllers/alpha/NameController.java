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

import net.kb.datamaker.alpha.Gender;
import net.kb.datamaker.numbers.IntegerFactory;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.PersonName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/alph")
public class NameController {

    private static final Logger log = LoggerFactory.getLogger(NameController.class);

    @Autowired
    private AlphArgHelper argHelper;

    @RequestMapping("/person")
    public ResponseEntity makeNameOfPerson(@RequestParam String gender, @RequestParam String namefmt) {
        Optional<Gender> optGen = argHelper.genderArg(gender);
        Optional<Integer> optInt = Optional.empty();
        String nameFormat = namefmt;

        if (namefmt.equals("RANDOM_NAME_FORMAT")) {
            int arg = IntegerFactory.randomNumberInRange(0, 7);
            Optional<String> optStr = argHelper.stringFromFormatArg(arg);
            if (optStr.isPresent()) {
                optInt = Optional.of(Integer.valueOf(arg));
                nameFormat = argHelper.stringFromFormatArg(optInt.get()).get();
            }  // else return 400 below.
        } else {
            optInt = argHelper.nameFormatArg(namefmt);
        }

        if (optGen.isEmpty() || optInt.isEmpty()) {
            log.error("400 : Bad gender or namefmt argument : gender=" + gender + "  namefmt=" + namefmt);
            ErrorMsg errorMsg = new ErrorMsg("400", "Bad gender or namefmt argument");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        PersonName name = new PersonName(optGen.get(), nameFormat, optInt.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(name);
    }

}
