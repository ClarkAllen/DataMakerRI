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

import net.kb.datamaker.alpha.States;
import net.kbg.datamakerri.model.State;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/alph")
public class StateController {

    @GetMapping("/state")
    public ResponseEntity makeState() {
        States sts = States.randomState();
        State state = new State(
                sts.getName(), sts.getAbbreviation(), sts.getFipsCode(),
                sts.getRegion(), sts.getRegionNumber(), sts.getDivision(),
                sts.getDivisionNumber());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(state);
    }
}
