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

import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.TextResult;
import net.kbg.datamakerri.services.alpha.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/alph")
public class TextController {

    @Autowired
    private TextService textService;

    @GetMapping("/uuid")
    public ResponseEntity makeUuid() {
        TextResult textResult = textService.makeUuid();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(textResult);
    }

    @PutMapping("/fromlist")
    public ResponseEntity selectFromList(@RequestBody List<String> inputList) {
        Optional<TextResult> optTxt = textService.selectFromList(inputList);
        if (optTxt.isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "The list must have at least two elements.  " +
                            "Please check your arguments");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optTxt.get());
    }

    @GetMapping("/textoflength")
    public ResponseEntity makeText(@RequestParam int length) {
        Optional<TextResult> optTxt = textService.makeText(length);

        if (optTxt.isEmpty()) {
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "The length argument must be between 1 and 2048.  " +
                            "Please check your arguments.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optTxt.get());
    }

}
