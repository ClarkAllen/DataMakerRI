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

package net.kbg.datamakerri.services.number;

import net.kb.datamaker.numbers.LongFactory;
import net.kbg.datamakerri.model.NumberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LongService {

    private static final Logger log = LoggerFactory.getLogger(LongService.class);

    public Optional<NumberValue> longFromRange(long lowEnd, long highEnd) {
        long val = 0;
        try {
            val = LongFactory.randomLongFromRange(lowEnd, highEnd);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        NumberValue numberValue = new NumberValue(val, "" + val);
        return Optional.of(numberValue);
    }

    public Optional<NumberValue> longFromList(List<Long> longList) {
        long val = 0;
        if (longList.size() < 2) {
            log.error("List is too small.  Returning 400.");
            return Optional.empty();
        }
        try {
            val = LongFactory.randomLongFromValueList(longList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        NumberValue numberValue = new NumberValue(val, "" + val);
        return Optional.of(numberValue);
    }
}
