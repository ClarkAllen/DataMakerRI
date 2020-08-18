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

package net.kbg.datamakerri.controllers.bool;

import lombok.extern.slf4j.Slf4j;
import net.kbg.datamakerri.model.BooleanValue;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.services.bool.BooleanService;
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
@RequestMapping("/v1/bool")
public class BooleanController {

    @Autowired
    BooleanService booleanService;

    @GetMapping("/boolean")
    public ResponseEntity makeRandomBoolean() {
        BooleanValue bool = booleanService.makeRandomBoolean();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bool);
    }

    @GetMapping("/intbool")
    public ResponseEntity makeIntBoolean(@RequestParam int trueValue, @RequestParam int falseValue) {
        Optional<BooleanValue> rslt = booleanService.makeIntBoolean(trueValue, falseValue);
        if (rslt.isEmpty()) {
            log.error("Returning 400 : Probably bad arguments.");
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "Please check your arguments.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rslt.get());
    }

    @GetMapping("/strbool")
    public ResponseEntity makeStringBoolean(@RequestParam String trueValue, @RequestParam String falseValue) {
        Optional<BooleanValue> rslt = booleanService.makeStringBoolean(trueValue, falseValue);
        if (rslt.isEmpty()) {
            log.error("Returning 400 : Probably bad arguments.");
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "Please check your arguments.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rslt.get());
    }

}
