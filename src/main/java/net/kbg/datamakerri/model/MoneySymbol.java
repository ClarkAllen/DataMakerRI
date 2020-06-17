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

public class MoneySymbol {
    private String symbol;
    private String abbr;
    private String descrip;

    public MoneySymbol() {
    }

    public MoneySymbol(String symbol, String abbr, String descrip) {
        this.symbol = symbol;
        this.abbr = abbr;
        this.descrip = descrip;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public String toString() {
        return "MoneySymbol{" +
                "symbol='" + symbol + '\'' +
                ", abbr='" + abbr + '\'' +
                ", descrip='" + descrip + '\'' +
                '}';
    }
    
}
