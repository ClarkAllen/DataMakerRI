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

import net.kb.datamaker.alpha.LetterFactory;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.Letter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LetterService {

    @Autowired
    private AlphArgHelper argHelper;

    private static final Logger log = LoggerFactory.getLogger(LetterService.class);

    public Letter makeLetter() {
        Letter letr = new Letter(LetterFactory.randomLetter());
        return letr;
    }

    public Optional<Letter> selectFromList(List<String> list) {
        Optional<Character> opChar = argHelper.charFromStringList(list);
        if (opChar.isPresent()) {
            Letter ltr = new Letter(opChar.get().toString());
            return Optional.of(ltr);
        }
        log.error("Bad argument list");
        return Optional.empty();
    }
}
