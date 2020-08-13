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
 *
 *
 */

package net.kbg.datamakerri.helpers;

import net.kb.datamaker.alpha.MoneySymbols;
import net.kbg.datamakerri.model.MoneySymbol;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Definitions {

    private static final Map<String, Integer> nameFormatMap = new HashMap<>();
    private static final Map<String, MoneySymbol> moneySymbolsMap = new HashMap<>();
    private static final List<String> dmSourceTypes = new ArrayList<>();

    public static final String ID = "id";
    public static final String UUID = "uuid";
    public static final String RTEXT = "rtext";
    public static final String TEXT_FROM_LIST = "textfromlist";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";
    public static final String STREET = "street";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String BOOL = "bool";
    public static final String DATE_MON_YR = "datemonyr";
    public static final String DATE_IN_YR = "dateinyr";
    public static final String DATE_BETW_YRS = "datebetwyrs";
    public static final String PATTERN = "pattern";
    public static final String DOUBLE = "double";
    public static final String LONG = "long";
    public static final String LONG_FROM_LIST = "longfromlist";

    {
        nameFormatMap.put("FIRST_MI_LAST", 0);
        nameFormatMap.put("FIRST_MIDDLE_LAST", 1);
        nameFormatMap.put("FIRST_LAST", 2);
        nameFormatMap.put("FI_LAST", 3);
        nameFormatMap.put("FI_MI_LAST", 4);
        nameFormatMap.put("LAST_FIRST", 5);
        nameFormatMap.put("LAST_FI", 6);
        nameFormatMap.put("LAST_FI_MI", 7);
        nameFormatMap.put("RANDOM_NAME_FORMAT", 8);

        for (MoneySymbols item : MoneySymbols.values()) {
            MoneySymbol ms = new MoneySymbol(item.getSymbol(), item.getAbbreviation(), item.getDescription());
            moneySymbolsMap.put(item.name(), ms);
        }

        dmSourceTypes.add(ID);
        dmSourceTypes.add(UUID);
        dmSourceTypes.add(RTEXT);
        dmSourceTypes.add(TEXT_FROM_LIST);
        dmSourceTypes.add(FNAME);
        dmSourceTypes.add(LNAME);
        dmSourceTypes.add(STREET);
        dmSourceTypes.add(CITY);
        dmSourceTypes.add(STATE);
        dmSourceTypes.add(BOOL);
        dmSourceTypes.add(DATE_MON_YR);
        dmSourceTypes.add(DATE_IN_YR);
        dmSourceTypes.add(DATE_BETW_YRS);
        dmSourceTypes.add(PATTERN);
        dmSourceTypes.add(DOUBLE);
        dmSourceTypes.add(LONG);
        dmSourceTypes.add(LONG_FROM_LIST);
    }

    public Definitions() {
    }


    public Map<String, Integer> getNameFormatMap() {
        return Collections.unmodifiableMap(nameFormatMap);
    }

    public Map<String, MoneySymbol> getMoneySymbolsMap() {
        return Collections.unmodifiableMap(moneySymbolsMap);
    }

    public List<String> getDmSourceTypes() {
        return Collections.unmodifiableList(dmSourceTypes);
    }

}
