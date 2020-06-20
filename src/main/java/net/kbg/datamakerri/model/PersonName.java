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

import net.kb.datamaker.alpha.Gender;
import net.kb.datamaker.alpha.NameFactory;

public class PersonName {

    private String gender;
    private String nameFmt;
    private String name;
    private String first;
    private String middle;
    private String last;

    public PersonName() {
    }

    public PersonName(Gender gender, String namefmt, Integer nameFmtInt) {
        this.gender = gender.getGenderSymbol();
        this.nameFmt = namefmt.toUpperCase();
        String[] fields = null;
        switch (nameFmtInt) {
            case 0:
                name = NameFactory.firstMiLast(gender);             // 0
                fields = name.split(" ");
                first = fields[0];
                middle = fields[1];
                last = fields[2];
                break;
            case 1:
                name = NameFactory.firstMiddleLast(gender);     // 1
                fields = name.split(" ");
                first = fields[0];
                middle = fields[1];
                last = fields[2];
                break;
            case 2:
                name = NameFactory.firstLast(gender);                  // 2
                fields = name.split(" ");
                first = fields[0];
                middle = "";
                last = fields[1];
                break;
            case 3:
                name = NameFactory.fiLast();                        // 3
                fields = name.split(" ");
                first = fields[0];
                middle = "";
                last = fields[1];
                break;
            case 4:
                name = NameFactory.fiMiLast();                   // 4
                fields = name.split(" ");
                first = fields[0];
                middle = fields[1];
                last = fields[2];
                break;
            case 5:
                name = NameFactory.lastCommaFirst(gender);       // 5
                fields = name.split(", ");
                first = fields[1];
                middle = "";
                last = fields[0];
                break;
            case 6:
                name = NameFactory.lastCommaFi();            // 6
                fields = name.split(", ");
                first = fields[1];
                middle = "";
                last = fields[0];
                break;
            case 7:
                name = NameFactory.lastCommaFiMi();        // 7
                fields = name.split(" ");
                first = fields[1];
                middle = fields[2];
                last = fields[0].replaceAll(",", "");
                break;
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNameFmt() {
        return nameFmt;
    }

    public void setNameFmt(String nameFmt) {
        this.nameFmt = nameFmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "PersonName{" +
                "gender='" + gender + '\'' +
                ", nameFmt='" + nameFmt + '\'' +
                ", name='" + name + '\'' +
                ", first='" + first + '\'' +
                ", middle='" + middle + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}
