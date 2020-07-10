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

package net.kbg.datamakerri.services.alpha;

import net.kb.datamaker.alpha.TextFactory;
import net.kbg.datamakerri.model.TextResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TextService {

    private static final Logger log = LoggerFactory.getLogger(TextService.class);

    public TextResult makeUuid() {
        TextResult textResult = new TextResult(TextFactory.makeRandomUuid());
        return textResult;
    }

    public Optional<TextResult> selectFromList(List<String> inputList) {
        if (inputList.size() < 2) {
            log.error("Input list size is too small.");
            return Optional.empty();
        }
        TextResult textResult = new TextResult(TextFactory.fromList(inputList));
        return Optional.of(textResult);
    }

    public Optional<TextResult> makeText(int length) {
        if (length < 1 || length > 2048) {
            log.error("makeText : Bad length argument.");
            return Optional.empty();
        }
        TextResult textResult = new TextResult(TextFactory.textOfLength(length));
        return Optional.of(textResult);
    }
}
