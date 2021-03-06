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

import net.kb.datamaker.alpha.UspsStreetSuffixes;
import net.kbg.datamakerri.model.UspsStreetSuffix;
import org.springframework.stereotype.Service;

@Service
public class UspsStreetSuffixService {

    public UspsStreetSuffix makeUspsSuffix() {
        UspsStreetSuffixes sfxs = UspsStreetSuffixes.streetSuffix();
        UspsStreetSuffix suffix = new UspsStreetSuffix(
                sfxs.getCommonSuffix(),
                sfxs.getUspsStandardSuffix(),
                sfxs.getMixedCaseName());

        return suffix;
    }
}
