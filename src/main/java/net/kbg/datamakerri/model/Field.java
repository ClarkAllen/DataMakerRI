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
    private int yearLowEnd;
    private int yearHighEnd;
    private String gender;
    private String nameFormat;
    private int precision;

    public Field() {
        name = "";
        dmSourceType = "";
        databaseType = "";
        length = 0;
        rangeLowEnd = 0;
        rangeHighEnd = 0;
        month = 0;
        yearLowEnd = 0;
        yearHighEnd = 0;
        gender = "R";
        nameFormat = "";
        precision = 2;
    }
}
