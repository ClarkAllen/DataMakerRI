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

import lombok.extern.slf4j.Slf4j;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.PersonName;
import net.kbg.datamakerri.services.alpha.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/alph")
public class NameController {

    @Autowired
    private NameService nameService;

    @GetMapping("/person")
    public ResponseEntity makeNameOfPerson(@RequestParam String gender, @RequestParam String namefmt) {

        Optional<PersonName> optName = nameService.makeNameOfPerson(gender, namefmt);

        if (optName.isEmpty()) {
            log.error("400 : Bad gender or namefmt argument : gender=" + gender + "  namefmt=" + namefmt);
            ErrorMsg errorMsg = new ErrorMsg("400", "Bad gender or namefmt argument");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        PersonName name = optName.get();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(name);
    }

}
