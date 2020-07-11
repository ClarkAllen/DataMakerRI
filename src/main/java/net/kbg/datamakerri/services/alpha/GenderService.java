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

import net.kb.datamaker.alpha.Gender;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.GenderMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenderService {

    @Autowired
    private AlphArgHelper argHelper;

    private static final Logger log = LoggerFactory.getLogger(GenderService.class);

    public Optional<GenderMod> makeGender(String type) {
        Optional<Gender> gen = argHelper.genderArg(type);
        if (gen.isEmpty()) {
            log.error("Bad type argument : " + type);
            return Optional.empty();
        }

        GenderMod genderMod = new GenderMod(
                gen.get().getGenderSymbol(), gen.get().getGenderString());

        return Optional.of(genderMod);
    }
}
