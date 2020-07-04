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

package net.kbg.datamakerri.input;

public class PatternTemplate {
    private String pattern;
    private String charSymbol;
    private String numSymbol;

    public PatternTemplate() {
    }

    public PatternTemplate(String pattern, String charSymbol, String numSymbol) {
        this.pattern = pattern;
        this.charSymbol = charSymbol;
        this.numSymbol = numSymbol;
    }

    public boolean isValid() {
        return charSymbol.length() == 1 &&
                Character.isAlphabetic(charSymbol.charAt(0)) &&
                numSymbol.length() == 1 &&
                Character.isAlphabetic(numSymbol.charAt(0)) &&
                (pattern.contains(charSymbol) ||
                        pattern.contains(numSymbol));
    }

    public boolean isNotValid() {
        return ! isValid();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCharSymbol() {
        return charSymbol;
    }

    public void setCharSymbol(String charSymbol) {
        this.charSymbol = charSymbol;
    }

    public String getNumSymbol() {
        return numSymbol;
    }

    public void setNumSymbol(String numSymbol) {
        this.numSymbol = numSymbol;
    }

    @Override
    public String toString() {
        return "PatternTemplate{" +
                "pattern='" + pattern + '\'' +
                ", charSymbol='" + charSymbol + '\'' +
                ", numSymbol='" + numSymbol + '\'' +
                '}';
    }
}
