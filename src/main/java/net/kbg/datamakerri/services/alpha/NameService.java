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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NameService {

    private static final Logger log = LoggerFactory.getLogger(NameService.class);

    @Autowired
    AlphArgHelper argHelper;

    public Optional<PersonName> makeNameOfPerson(String gender, String nameFmt) {
        Optional<Gender> optGen = argHelper.genderArg(gender);
        Optional<Integer> optInt = Optional.empty();
        String nameFormat = nameFmt;

        if (nameFmt.equals("RANDOM_NAME_FORMAT")) {
            int arg = IntegerFactory.randomNumberInRange(0, 7);
            Optional<String> optStr = argHelper.stringFromFormatArg(arg);
            if (optStr.isPresent()) {
                optInt = Optional.of(Integer.valueOf(arg));
                nameFormat = argHelper.stringFromFormatArg(optInt.get()).get();
            }  // else return 400 below.
        } else {
            optInt = argHelper.nameFormatArg(nameFmt);
        }

        if (optGen.isEmpty() || optInt.isEmpty()) {
            log.error("Bad gender or namefmt argument : gender=" + gender +
                    "  namefmt=" + nameFmt);
            return Optional.empty();
        }

        PersonName name = new PersonName(optGen.get(), nameFormat, optInt.get());

        return Optional.of(name);
    }
}
