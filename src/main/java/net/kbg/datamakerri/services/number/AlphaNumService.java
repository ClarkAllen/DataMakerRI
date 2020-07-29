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

import lombok.extern.slf4j.Slf4j;
import net.kb.datamaker.numbers.AlphaNumFactory;
import net.kbg.datamakerri.input.PatternTemplate;
import net.kbg.datamakerri.model.TextResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AlphaNumService {

    public Optional<TextResult> makeAlphaNumPatternText(PatternTemplate template) {
        if (template.isNotValid()) {
            return Optional.empty();
        }

        String rslt = null;

        try {
            rslt = AlphaNumFactory.patternedAlphaNumString(
                    template.getPattern(),
                    template.getCharSymbol().charAt(0),
                    template.getNumSymbol().charAt(0));
        } catch (Exception e) {
            log.error(e.getMessage() + " :  Unable to make patterned sequence", e);
        }

        TextResult textResult = new TextResult(rslt);

        return Optional.ofNullable(textResult);
    }
}
