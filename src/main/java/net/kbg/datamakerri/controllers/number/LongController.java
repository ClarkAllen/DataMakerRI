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
import net.kbg.datamakerri.services.number.LongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/num/long")
public class LongController {

    private static final Logger log = LoggerFactory.getLogger(LongController.class);

    @Autowired
    LongService longService;

    @GetMapping("/range")
    public ResponseEntity makeLongFromRange(@RequestParam long lowEnd, @RequestParam long highEnd) {
        Optional<NumberValue> optNum = longService.longFromRange(lowEnd, highEnd);
        if (optNum.isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "Check your parameters.  The lowEnd must be less than the highEnd.");
            log.error("Bad parameters.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        NumberValue num = optNum.get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(num);
    }

    @PutMapping("/fromlist")
    public ResponseEntity selectLongFromList(@RequestBody List<Long> longList) {
        Optional<NumberValue> optNum = longService.longFromList(longList);
        if (optNum.isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "Check your parameters.  The list may be empty or too small.");
            log.error("Bad parameters.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        NumberValue num = optNum.get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(num);
    }

}