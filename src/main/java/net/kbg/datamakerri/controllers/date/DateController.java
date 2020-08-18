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

package net.kbg.datamakerri.controllers.date;


import lombok.extern.slf4j.Slf4j;
import net.kbg.datamakerri.model.DateValue;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.services.date.DateService;
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
@RequestMapping("/v1/date")
public class DateController {

    @Autowired
    DateService dateService;

    @GetMapping("/year")
    public ResponseEntity makeDateInYear(@RequestParam int year) {
        Optional<DateValue> optVal = dateService.makeDateInYear(year);
        if (optVal.isEmpty()) {
            log.error("Returning 400 : Bad arguments.");
            ErrorMsg errorMsg = new ErrorMsg("400", "The year must be between 1 and 4,000.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optVal.get());
    }

    @GetMapping("/monthyear")
    public ResponseEntity makeDateInMonthYear(@RequestParam int month, @RequestParam int year) {
        Optional<DateValue> optVal = dateService.makeDateInMonthYear(month, year);
        if (optVal.isEmpty()) {
            log.error("Returning 400 : Probably bad arguments.");
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "The month must be between 1 and 12 and the " +
                            "year must be between 1 and 4,000.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optVal.get());
    }

    @GetMapping("/range")
    public ResponseEntity makeDateInYearRange(@RequestParam int lowyear, @RequestParam int highyear) {
        Optional<DateValue> optVal = dateService.makeDateInYearRange(lowyear, highyear);
        if (optVal.isEmpty()) {
            log.error("Returning 400 : Probably bad arguments.");
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "The year must be between 1 and 4,000.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optVal.get());
    }

}
