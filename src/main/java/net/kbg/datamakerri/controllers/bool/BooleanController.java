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

import net.kb.datamaker.bool.BooleanFactory;
import net.kbg.datamakerri.model.BooleanValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bool")
public class BooleanController {

    @GetMapping("/boolean")
    public ResponseEntity makeRandomBoolean() {
        boolean rslt = BooleanFactory.makeRandomBoolean();
        String strbool = rslt ? "True" : "False";
        BooleanValue booleanValue = new BooleanValue(rslt ? 1 : 0, rslt, strbool);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(booleanValue);
    }

    @GetMapping("/intbool")
    public ResponseEntity makeIntBoolean(@RequestParam int trueValue, @RequestParam int falseValue) {
        int rslt = BooleanFactory.makeRandomBooleanIntFromArgs(trueValue, falseValue);
        boolean bool = rslt == trueValue;
        String strbool = rslt == trueValue ? "True" : "False";
        BooleanValue booleanValue = new BooleanValue(rslt, bool, strbool);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(booleanValue);
    }

    @GetMapping("/strbool")
    public ResponseEntity makeStringBoolean(@RequestParam String trueValue, @RequestParam String falseValue) {
        String rslt = BooleanFactory.makeRandomBooleanStringFromArgs(trueValue, falseValue);
        boolean bool = rslt.equals(trueValue);
        int intbool = bool ? 1 : 0;
        BooleanValue booleanValue = new BooleanValue(intbool, bool, rslt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(booleanValue);
    }

}
