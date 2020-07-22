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

public class Field {

    private String name;
    private String dmSourceType;
    private String databaseType;
    private int length;
    private long rangeLowEnd;
    private long rangeHighEnd;
    private String gender;
    private String nameFormat;

    public Field() {
    }

    public Field(String name, String dmSourceType, String databaseType,
                 int length, long rangeLowEnd, long rangeHighEnd,
                 String gender, String nameFormat) {
        this.name = name;
        this.dmSourceType = dmSourceType;
        this.databaseType = databaseType;
        this.length = length;
        this.rangeLowEnd = rangeLowEnd;
        this.rangeHighEnd = rangeHighEnd;
        this.gender = gender;
        this.nameFormat = nameFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDmSourceType() {
        return dmSourceType;
    }

    public void setDmSourceType(String dmSourceType) {
        this.dmSourceType = dmSourceType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getRangeLowEnd() {
        return rangeLowEnd;
    }

    public void setRangeLowEnd(long rangeLowEnd) {
        this.rangeLowEnd = rangeLowEnd;
    }

    public long getRangeHighEnd() {
        return rangeHighEnd;
    }

    public void setRangeHighEnd(long rangeHighEnd) {
        this.rangeHighEnd = rangeHighEnd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNameFormat() {
        return nameFormat;
    }

    public void setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", dmSourceType='" + dmSourceType + '\'' +
                ", databaseType='" + databaseType + '\'' +
                ", length=" + length +
                ", rangeLowEnd=" + rangeLowEnd +
                ", rangeHighEnd=" + rangeHighEnd +
                ", gender='" + gender + '\'' +
                ", nameFormat='" + nameFormat + '\'' +
                '}';
    }
}
