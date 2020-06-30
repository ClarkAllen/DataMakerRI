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

import net.kb.datamaker.alpha.TextFactory;
import net.kbg.datamakerri.model.ErrorMsg;
import net.kbg.datamakerri.model.TextResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/alph")
public class TextController {

    @GetMapping("/uuid")
    public ResponseEntity makeUuid() {
        TextResult textResult = new TextResult(TextFactory.makeRandomUuid());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(textResult);
    }

    @PutMapping("/fromlist")
    public ResponseEntity selectFromList(@RequestBody List<String> inputList) {
        if (inputList.size() < 2) {
            ErrorMsg errorMsg = new ErrorMsg("400", "Input list is too small");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        TextResult textResult = new TextResult(TextFactory.fromList(inputList));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(textResult);
    }

    @GetMapping("/textoflength")
    public ResponseEntity makeText(@RequestParam int length) {
        if (length < 1 || length > 1_024_000) {
            ErrorMsg errorMsg = new ErrorMsg("400",
                    "Length must be more than zero and less than 1,024,000");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMsg);
        }

        TextResult textResult = new TextResult(TextFactory.textOfLength(length));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(textResult);
    }

}
