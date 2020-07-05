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

import net.kb.datamaker.numbers.DoubleFactory;
import net.kbg.datamakerri.model.NumberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoubleService {

    public static final Logger log = LoggerFactory.getLogger(DoubleService.class);

    public Optional<NumberValue> doubleInRange(long lowEnd, long highEnd) {
        if (lowEnd >= highEnd) {
            log.error("lowEnd >= highEnd");
            return Optional.empty();
        }

        Double rslt = DoubleFactory.doubleInRange(lowEnd, highEnd);
        NumberValue numberValue = new NumberValue(rslt.doubleValue(), "" + rslt.toString());
        return Optional.ofNullable(numberValue);
    }
}
