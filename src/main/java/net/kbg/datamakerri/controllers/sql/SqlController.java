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

package net.kbg.datamakerri.controllers.sql;

import lombok.extern.slf4j.Slf4j;
import net.kbg.datamakerri.model.Table;
import net.kbg.datamakerri.services.sql.SqlEmitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/sql")
public class SqlController {

    @Autowired
    private SqlEmitterService sqlService;

    @PutMapping("/table")
    public ResponseEntity insertToTable(@RequestBody Table table) {
        List<String> errors = table.validate();
        if (errors.size() > 0) {
            log.error("Validation errors : " + errors.size());
            return ResponseEntity
                    .status(400)
                    .body(errors);
        }
        Optional<List<String>> optSql = sqlService.emit(table);
        if (optSql.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Bad Request: Check your table definition.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optSql.get());
    }
}
