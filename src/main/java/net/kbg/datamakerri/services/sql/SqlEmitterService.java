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

package net.kbg.datamakerri.services.sql;

import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.model.*;
import net.kbg.datamakerri.services.alpha.*;
import net.kbg.datamakerri.services.bool.BooleanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SqlEmitterService {

    @Autowired
    private AlphArgHelper argHelper;

    @Autowired
    private NameService nameService;

    @Autowired
    private StreetService streetService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private TextService textService;

    @Autowired
    private BooleanService booleanService;

    public List<String> emit(int startId, Table table, Map<String, List<String>> fromLists) {
        List<String> sql = new LinkedList<>();
        if (table.getRows() < 1) {
            return sql;
        }
        sql.add(makeInsertStatement(table));
        for (int b = 0; b < table.getRows(); ++b) {
            if (b == table.getRows() - 1) {
                sql.add(generateRow(startId + b, table, fromLists) + ";");
            } else {
                sql.add(generateRow(startId + b, table, fromLists) + ", ");
            }
        }
        return sql;
    }

    public String makeInsertStatement(Table table) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO " + table.getName() + " (");
        for (Field field : table.getFields()) {
            sb.append(",").append(field.getName());
        }
        sb.append(")  ");
        return sb.toString().replaceFirst(",", "");
    }

    public String generateRow(long rowId, Table table, Map<String, List<String>> fromLists) {
        StringBuffer sb = new StringBuffer();
        sb.append("  VALUES (");
        for (Field field : table.getFields()) {
            sb.append(",")
                    .append(generateFieldValue(rowId, field, fromLists));
        }
        sb.append(")");
        return sb.toString().replaceFirst(",", "");
    }

    public String generateFieldValue(long rowId, Field field,
              Map<String, List<String>> fromLists) {

        StringBuffer value = new StringBuffer();

        switch (field.getDmSourceType().toLowerCase()) {
            case "id" :
                value.append("" + rowId);
                break;
            case "uuid" :
                value.append(quote(textService.makeUuid().getValue()));
                break;
            case "rtext" :
                Optional<TextResult> optTxt = textService.makeText(field.getLength());
                if (optTxt.isPresent()) {
                    value.append(quote(optTxt.get().getValue()));
                } else {
                    throw new RuntimeException("Error making random text");
                }
                break;
            case "fname" :
                Optional<PersonName> opName = nameService.makeNameOfPerson(field.getGender(),
                        "FIRST_MIDDLE_LAST");
                if (opName.isPresent()) {
                    value.append(quote(opName.get().getFirst()));
                } else {
                    throw new RuntimeException("Error making person name");
                }
                break;
            case "lname" :
                Optional<PersonName> optName = nameService.makeNameOfPerson(field.getGender(),
                        "FIRST_MIDDLE_LAST");
                if (optName.isPresent()) {
                    value.append(quote(optName.get().getLast()));
                } else {
                    throw new RuntimeException("Error making person name");
                }
                break;
            case "street" :
                Street street = streetService.makeStreet();
                value.append(quote(street.getStreet()));
                break;
            case "city" :
                City city = cityService.makeCity();
                value.append(quote(city.getName()));
                break;
            case "state" :
                State state = stateService.makeState();
                value.append(quote(state.getName()));
                break;
            case "bool" :
                BooleanValue val = booleanService.makeRandomBoolean();
                switch(field.getDatabaseType()) {
                    case "int" :
                        value.append(val.getIntValue());
                        break;
                    case "boolean" :
                        value.append(val.isTruth());
                        break;
                    case "string" :
                        value.append(quote(val.getStringValue()));
                        break;
                    default :
                        throw new RuntimeException("Bad state for boolean creation");
                }
                break;
        }
        return value.toString();
    }

    public String quote(String arg) {
        String qt1 = "'";
        String qt2 = "''";

        if (argHelper.noContent(arg)) {
            return qt2;
        }
        return qt1 + arg.replaceAll(qt1, qt2) + qt1;
    }
}
