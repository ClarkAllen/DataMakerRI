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

package net.kbg.datamakerri.services.date;

import net.kb.datamaker.dates.DateFactory;
import net.kbg.datamakerri.model.DateValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class DateService {

    private static final Logger log = LoggerFactory.getLogger(DateService.class);
    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");


    public Optional<DateValue> makeDateInYear(int year) {
        if (year < 1 || year > 4_000) {
            log.error("makeDateInYear : Date was out of range");
            return Optional.empty();
        }

        Date dt = DateFactory.makeDateInYear(year);
        String iso8601 = sdf.format(dt);
        DateValue dateValue = new DateValue(iso8601, dt.getTime());
        return Optional.of(dateValue);
    }

    public Optional<DateValue> makeDateInMonthYear(int month, int year) {
        if (year < 1 || year > 4_000 || month < 1 || month > 12) {
            log.error("makeDateInMonthYear : Date was out of range");
            return Optional.empty();
        }

        Date dt = DateFactory.dateInMonthYear(month - 1, year);
        String iso8601 = sdf.format(dt);
        DateValue dateValue = new DateValue(iso8601, dt.getTime());
        return Optional.of(dateValue);
    }

    public Optional<DateValue> makeDateInYearRange(@RequestParam int lowyear, @RequestParam int highyear) {
        if (lowyear >= highyear  || lowyear < 1 || highyear > 4_000) {
            log.error("Bad parameter values.  lowyear must be less then highyear " +
                    "and both values must be between 1 and 4,000");
            return Optional.empty();
        }

        Date dt = DateFactory.makeDateInYearRange(lowyear, highyear);
        String iso8601 = sdf.format(dt);
        DateValue dateValue = new DateValue(iso8601, dt.getTime());
        return Optional.of(dateValue);
    }
}
