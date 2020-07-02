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


import net.kb.datamaker.dates.DateFactory;
import net.kbg.datamakerri.model.DateValue;
import net.kbg.datamakerri.model.ErrorMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/v1/date")
public class DateController {

    @GetMapping("/year")
    public ResponseEntity makeDateInYear(@RequestParam int year) {
        if (year < 1 || year > 4_000) {
            ErrorMsg errorMsg = new ErrorMsg("400", "The year parameter must be between 1 and 4000.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        Date dt = DateFactory.makeDateInYear(year);
        String iso8601 = dt.toInstant().toString();
        DateValue dateValue = new DateValue(iso8601, dt.getTime());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dateValue);
    }

    @GetMapping("/monthyear")
    public ResponseEntity makeDateInMonthYear(@RequestParam int month, @RequestParam int year) {
        if (year < 1 || year > 4_000 || month < 1 || month > 12) {
            ErrorMsg errorMsg = new ErrorMsg("400", "Bad parameter values");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        Date dt = DateFactory.dateInMonthYear(month - 1, year);
        String iso8601 = dt.toInstant().toString();
        DateValue dateValue = new DateValue(iso8601, dt.getTime());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dateValue);
    }

    @GetMapping("/range")
    public ResponseEntity makeDateInYearRange(@RequestParam int lowyear, @RequestParam int highyear) {
        if (lowyear >= highyear  || lowyear < 1 || highyear > 4_000) {
            ErrorMsg errorMsg = new ErrorMsg(
                    "400",
                    "Bad parameter values.  lowyear must be less then highyear and both values must be between 1 and 4,000");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        Date dt = DateFactory.makeDateInYearRange(lowyear, highyear);
        String iso8601 = dt.toInstant().toString();
        DateValue dateValue = new DateValue(iso8601, dt.getTime());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dateValue);
    }


}
