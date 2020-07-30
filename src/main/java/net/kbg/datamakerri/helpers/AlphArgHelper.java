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

import lombok.extern.slf4j.Slf4j;
import net.kb.datamaker.alpha.Gender;
import net.kb.datamaker.alpha.TextFactory;
import net.kbg.datamakerri.model.MoneySymbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AlphArgHelper {

    @Autowired
    private Definitions definitions;

    public Optional<Gender> genderArg(String arg) {
        switch (arg.toLowerCase()) {
            case "f" : return Optional.of(Gender.FEMALE);
            case "m" : return Optional.of(Gender.MALE);
            case "r" : return Optional.of(Gender.findRandomGender());
        }
        return Optional.empty();
    }

    public Optional<Integer> nameFormatArg(String arg) {
        Map<String, Integer> nameFormats = definitions.getNameFormatMap();
        return Optional.ofNullable(nameFormats.get(arg.toUpperCase()));
    }

    public Optional<String> stringFromFormatArg(int arg) {
        Map<String, Integer> map = definitions.getNameFormatMap();
        Map<Integer, String> mapInversed =
                map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return Optional.ofNullable(mapInversed.get(arg));
    }

    public Optional<Character> charFromStringList(List<String> list) {
        char ch = ' ';
        try {
            List<String> chList = list.stream()
                    .filter(c -> c.length() == 1)
                    .collect(Collectors.toList());

            if (list.size() < 2) {
                return Optional.empty();
            }

            String s = TextFactory.fromList(chList);
            ch = s.charAt(0);
        }
        catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(ch);
    }

    public Optional<MoneySymbol> getMoneySymbolByName(String name) {
        Map<String, MoneySymbol> msm = definitions.getMoneySymbolsMap();
        return Optional.ofNullable(msm.getOrDefault(name.toUpperCase(), null));
    }

    public Set<String> getMoneySymbolNames() {
        return definitions.getMoneySymbolsMap().keySet();
    }

    public boolean hasContent(String arg) {
        return arg != null && arg.trim().length() > 0;
    }

    public boolean noContent(String arg) {
        return ! hasContent(arg);
    }

    public String makePrecisionPattern(int precision) {
        if (precision < 1) {
            throw new IllegalArgumentException("Precision argument must be greater than zero");
        }
        StringBuilder sb = new StringBuilder("#.");
        for (int k = 0; k < precision; ++k) {
            sb.append("#");
        }
        return sb.toString();
    }

    public Optional<List<Long>> makeLongListFromStringList(List<String> nums) {
        List<Long> lngs = new LinkedList<>();
        try {
            lngs = nums.stream()
                    .map(n -> Long.parseLong(n))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        if (lngs.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(lngs);
    }

}
