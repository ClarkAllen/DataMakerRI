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

package net.kbg.datamakerri.controllers.alpha;

import net.kb.datamaker.alpha.UspsStreetSuffixes;
import net.kbg.datamakerri.model.UspsStreetSuffix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alph")
public class UspsStreetSuffixController {

    @GetMapping("/uspssuffix")
    public ResponseEntity makeUspsSuffix() {
        UspsStreetSuffixes sfxs = UspsStreetSuffixes.streetSuffix();
        UspsStreetSuffix suffix = new UspsStreetSuffix(
                sfxs.getCommonSuffix(),
                sfxs.getUspsStandardSuffix(),
                sfxs.getMixedCaseName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(suffix);
    }

}
