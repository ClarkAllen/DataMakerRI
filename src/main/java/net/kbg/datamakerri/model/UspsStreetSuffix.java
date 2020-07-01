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

public class UspsStreetSuffix {

    private String commonSuffix;
    private String uspsStandardSuffix;
    private String mixedCaseName;

    public UspsStreetSuffix() {
    }

    public UspsStreetSuffix(String commonSuffix, String uspsStandardSuffix, String mixedCaseName) {
        this.commonSuffix = commonSuffix;
        this.uspsStandardSuffix = uspsStandardSuffix;
        this.mixedCaseName = mixedCaseName;
    }

    public String getCommonSuffix() {
        return commonSuffix;
    }

    public void setCommonSuffix(String commonSuffix) {
        this.commonSuffix = commonSuffix;
    }

    public String getUspsStandardSuffix() {
        return uspsStandardSuffix;
    }

    public void setUspsStandardSuffix(String uspsStandardSuffix) {
        this.uspsStandardSuffix = uspsStandardSuffix;
    }

    public String getMixedCaseName() {
        return mixedCaseName;
    }

    public void setMixedCaseName(String mixedCaseName) {
        this.mixedCaseName = mixedCaseName;
    }

    @Override
    public String toString() {
        return "UspsStreetSuffix{" +
                "commonSuffix='" + commonSuffix + '\'' +
                ", uspsStandardSuffix='" + uspsStandardSuffix + '\'' +
                ", mixedCaseName='" + mixedCaseName + '\'' +
                '}';
    }
}
