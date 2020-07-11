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

import net.kb.datamaker.alpha.LetterFactory;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.Letter;
import net.kbg.datamakerri.services.alpha.LetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/alph")
public class LetterController {

    private static final Logger log = LoggerFactory.getLogger(LetterController.class);

    @Autowired
    private LetterService letterService;

    @Autowired
    private AlphArgHelper argHelper;

    @GetMapping("/char")
    public ResponseEntity makeCharacter() {
        Letter letr = letterService.makeLetter();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(letr);
    }

    @PutMapping("/char")
    public ResponseEntity selectFromList(@RequestBody List<String> list) {
        Optional<Letter> opLetr = letterService.selectFromList(list);
        if (opLetr.isEmpty()) {
            log.error("400 : Could not extract a random character from the list passed in.");
            ErrorMsg msg = new ErrorMsg("400", "Could not extract a random character from the list passed in.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(msg);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(opLetr.get());
    }
}
