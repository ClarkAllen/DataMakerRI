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

public class State {

    private String name;
    private String abbrev;
    private String fipsCode;
    private String region;
    private String regionNumber;
    private String division;
    private String divisionNumber;

    public State() {
    }

    public State(String name, String abbrev, String fipsCode,
                 String region, String regionNumber, String division,
                 String divisionNumber) {
        this.name = name;
        this.abbrev = abbrev;
        this.fipsCode = fipsCode;
        this.region = region;
        this.regionNumber = regionNumber;
        this.division = division;
        this.divisionNumber = divisionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionNumber() {
        return regionNumber;
    }

    public void setRegionNumber(String regionNumber) {
        this.regionNumber = regionNumber;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivisionNumber() {
        return divisionNumber;
    }

    public void setDivisionNumber(String divisionNumber) {
        this.divisionNumber = divisionNumber;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", abbrev='" + abbrev + '\'' +
                ", fipsCode='" + fipsCode + '\'' +
                ", region='" + region + '\'' +
                ", regionNumber='" + regionNumber + '\'' +
                ", division='" + division + '\'' +
                ", divisionNumber='" + divisionNumber + '\'' +
                '}';
    }

}
