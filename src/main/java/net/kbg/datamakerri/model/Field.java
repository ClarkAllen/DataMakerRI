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
import net.kbg.datamakerri.helpers.Definitions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Field {

    private String name;
    private String dmSourceType;
    private String databaseType;
    private int length;
    private long rangeLowEnd;
    private long rangeHighEnd;
    private int month;
    private int year;
    private int yearLowEnd;
    private int yearHighEnd;
    private String gender;
    private String nameFormat;
    private int precision;
    private String pattern;
    private String charSymbol;
    private String numSymbol;
    private Map<String, List<String>> fromLists;

    public Field() {
        name = "";
        dmSourceType = "";
        databaseType = "";
        length = 0;
        rangeLowEnd = 0;
        rangeHighEnd = 0;
        month = 0;
        year = 0;
        yearLowEnd = 0;
        yearHighEnd = 0;
        gender = "R";
        nameFormat = "";
        precision = 2;
        pattern = "";
        charSymbol = "";
        numSymbol = "";
        fromLists = new HashMap<>();
    }

    public List<String> validate(int idx) {
        Definitions defs = new Definitions();
        List<String> sourceTypes = defs.getDmSourceTypes();
        List<String> errors = new LinkedList<>();
        if (name.isBlank()) {
            errors.add("Field name is required : " + idx);
        }
        else if (dmSourceType.isBlank()) {
            errors.add("dmSourceType is required : " + idx);
        }
        else if ( ! sourceTypes.contains(dmSourceType) ) {
            errors.add("The dmSourceType given is not recognized : " + idx);
        }
        if ( ! dmSourceType.isBlank() ) {
            switch (dmSourceType) {
                case Definitions.RTEXT :
                    if (length < 1) {
                        errors.add("The field length must be greater than one for a random text field : " + idx);
                    }
                    break;
                case Definitions.TEXT_FROM_LIST :
                    if ( ! fromLists.containsKey(name) ) {
                        errors.add("The fromLists array needs a key/value pair of string/string[] " +
                                "and the key must be the same  as the field name : " + idx);
                    }
                    else if (fromLists.get(name).size() < 2) {
                        errors.add("The string list for " + name + " is too small : " + idx);
                    }
                    break;
                default : // do nothing
            }
        }
        return errors;
    }
}
