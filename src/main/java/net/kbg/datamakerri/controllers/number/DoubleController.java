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

package net.kbg.datamakerri.controllers.number;

import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.NumberValue;
import net.kbg.datamakerri.services.number.DoubleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/double")
public class DoubleController {

    @Autowired
    DoubleService doubleService;

    @GetMapping("range")
    public ResponseEntity makeDoubleInRange(@RequestParam long lowEnd, @RequestParam long highEnd) {
        Optional<NumberValue> optRslt = doubleService.doubleInRange(lowEnd, highEnd);

        if (optRslt.isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg(
                    "400",
                    "Check your parameters.  The low end must be less than the high end.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optRslt.get());
    }
}
