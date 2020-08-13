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

package net.kbg.datamakerri.model;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Table {

    private String name;
    private int rows;
    private int startRowNum;
    private List<Field> fields;

    public Table() {
        name = "";
        rows = 0;
        startRowNum = 1;
        fields = new LinkedList<>();
    }

    public List<String> validate() {
        List<String> errors = new LinkedList<>();
        if (name.isBlank()) {
            errors.add("Table name is required");
        }
        else if (rows < 1) {
            errors.add("Table rows must be greater than zero");
        }
        else if (fields.size() < 1) {
            errors.add("The table has no fields");
        }
        else if (startRowNum < 1) {
            errors.add("Table startRowNum must be greater than zero");
        }
        for (int k = 0; k < fields.size(); ++k) {
            List<String> errs = fields.get(k).validate(k);
            if (errs.size() > 0) {
                errors.addAll(errs);
            }
        }
        return errors;
    }

}
