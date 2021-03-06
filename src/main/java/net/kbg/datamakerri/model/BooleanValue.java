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

public class BooleanValue {
    private int intValue;
    private boolean truth;
    private String stringValue;

    public BooleanValue() {
    }

    public BooleanValue(int intValue, boolean booleanValue, String stringValue) {
        this.intValue = intValue;
        this.truth = booleanValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public boolean isTruth() {
        return truth;
    }

    public void setTruth(boolean truth) {
        this.truth = truth;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "intValue=" + intValue +
                ", booleanValue=" + truth +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }
}
