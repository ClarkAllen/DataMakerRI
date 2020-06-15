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
 *
 *
 */

package net.kbg.datamakerri.controllers;

import net.kb.datamaker.alpha.Gender;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.GenderMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alph")
public class GenderController {

    @Autowired
    AlphArgHelper argHelper;

    @GetMapping("/gender")
    public ResponseEntity makeGender(@RequestParam String type) {
        if (argHelper.genderArg(type).isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg("400", "Bad type argument");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        Gender g = argHelper.genderArg(type).get();
        GenderMod gender = new GenderMod(g.getGenderSymbol(), g.getGenderString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gender);
    }
}
