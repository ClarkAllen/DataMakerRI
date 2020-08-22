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

import java.util.*;

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
    private List<String> itemList;

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
        itemList = new ArrayList<>();
    }

    public List<String> validate(int idx) {
        Definitions defs = new Definitions();
        List<String> sourceTypes = defs.getDmSourceTypes();
        List<String> errors = new LinkedList<>();
        if (name == null || name.isBlank()) {
            errors.add("Error: Field name is required : " + idx);
        }
        if (dmSourceType == null || dmSourceType.isBlank()) {
            errors.add("Error: dmSourceType is required : " + idx);
        }
        else if ( ! sourceTypes.contains(dmSourceType) ) {
            errors.add("Error: The dmSourceType given is not recognized : " + idx);
        }
        if ( dmSourceType != null && ! dmSourceType.isBlank() ) {
            switch (dmSourceType) {
                case Definitions.RTEXT :
                    if (length < 1) {
                        errors.add("Error: The field length must be greater than one for a random text field : " + idx);
                    }
                    break;
                case Definitions.LONG_FROM_LIST:
                case Definitions.TEXT_FROM_LIST :
                    if ( itemList == null ) {
                        errors.add("Error: The itemList array for " + name + " needs to be populated " + idx);
                    }
                    else if (itemList.size() < 2) {
                        errors.add("Error: The itemList array for " + name + " is too small : " + idx);
                    }
                    break;
                case Definitions.FNAME :
                    if ( ! validGender() ) {
                        errors.add("Error: The gender given is not valid; expecting R, M, or F : " + idx);
                    }
                    break;
                case Definitions.BOOL:
                    if ( ! validBoolDatabaseType()) {
                        errors.add("Error: The database type given is not valid; expecting int, boolean, or string : " + idx);
                    }
                    break;
                case Definitions.DATE_MON_YR:
                    if ( ! validMonth() || ! validYear() ) {
                        errors.add("Error: Month or year argument was not valid for date creation : " + idx);
                    }
                    break;
                case Definitions.DATE_IN_YR:
                    if ( ! validYear() ) {
                        errors.add("Error: The year argument was not valid for date creation : " + idx);
                    }
                    break;
                case Definitions.DATE_BETW_YRS:
                    if ( ! validYearRange() ) {
                        errors.add("Error: The year range parameters were not valid : " + idx);
                    }
                    break;
                case Definitions.PATTERN:
                    if ( ! patternValid() ) {
                        errors.add("Error: Patterned string requires a valid pattern, charSymbol, and numSymbol : " + idx);
                    }
                    break;
                case Definitions.DOUBLE:
                    if (precision < 1) {
                        errors.add("Error: doubles require a precision argument : " + idx);
                    }
                    // No break here.  Test the ranges the same as Longs.
                case Definitions.LONG:
                    if (rangeLowEnd >= rangeHighEnd) {
                        errors.add("Error: rangeLowEnd and rangeHighEnd arguments are not valid; expecting rangeLowEnd < rangeHighEnd : " + idx);
                    }
                    break;
                default : // do nothing
            }
        }
        return errors;
    }

    private boolean validGender() {
        return  gender != null &&
                ! gender.isBlank() &&
                (gender.toUpperCase().equalsIgnoreCase("R") ||
                gender.toUpperCase().equalsIgnoreCase("M") ||
                gender.toUpperCase().equalsIgnoreCase("F"));
    }

    private boolean validBoolDatabaseType() {
        return  databaseType != null &&
                ! databaseType.isBlank() &&
                (databaseType.equals("int") ||
                databaseType.equals("boolean") ||
                databaseType.equals("string"));
    }

    private boolean validMonth() {
        return month > 0 && month < 13;
    }

    private boolean validYear() {
        return year > 0 && year < 4_001;
    }

    private boolean validYearRange() {
        return  yearLowEnd > 0 &&
                yearLowEnd < 4_001 &&
                yearHighEnd > 0 &&
                yearHighEnd < 4_001 &&
                yearLowEnd < yearHighEnd;
    }

    private boolean patternValid() {
        return  pattern != null &&
                ! pattern.isBlank() &&
                charSymbol != null &&
                ! charSymbol.isBlank() &&
                numSymbol != null &&
                ! numSymbol.isBlank() &&
                pattern.trim().length() > 0 &&
                charSymbol.trim().length() == 1 &&
                numSymbol.trim().length() == 1;
    }

}
