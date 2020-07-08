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

package net.kbg.datamakerri.services.bool;

import net.kb.datamaker.bool.BooleanFactory;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.BooleanValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooleanService {

    private static final Logger log = LoggerFactory.getLogger(BooleanService.class);

    @Autowired
    AlphArgHelper argHelper;

    public BooleanValue makeRandomBoolean() {
        boolean b = BooleanFactory.makeRandomBoolean();
        BooleanValue val = new BooleanValue(b?1:0, b, b?"True":"False");
        return val;
    }

    public Optional<BooleanValue> makeIntBoolean(int trueValue, int falseValue) {
        if (trueValue == falseValue) {
            return Optional.empty();
        }
        int b = BooleanFactory.makeRandomBooleanIntFromArgs(trueValue, falseValue);
        boolean bool = b == trueValue ? true : false;
        BooleanValue val = new BooleanValue(b, bool, bool?"True":"False");
        return Optional.of(val);
    }

    public Optional<BooleanValue> makeStringBoolean(String trueValue, String falseValue) {
        if (argHelper.noContent(trueValue) ||
                argHelper.noContent(falseValue) ||
                trueValue.equals(falseValue)) {

            return Optional.empty();
        }

        String b = BooleanFactory.makeRandomBooleanStringFromArgs(trueValue, falseValue);
        boolean bool = b.equals(trueValue);
        int ival = bool ? 1 : 0;
        BooleanValue booleanValue = new BooleanValue(ival, bool, b);
        return Optional.of(booleanValue);
    }
}
