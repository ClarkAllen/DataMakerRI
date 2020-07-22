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
import net.kbg.datamakerri.model.Address;
import net.kbg.datamakerri.model.Field;
import net.kbg.datamakerri.model.Table;
import net.kbg.datamakerri.services.alpha.AddressService;
import net.kbg.datamakerri.services.alpha.TextService;
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
    private AddressService addrService;

    @Autowired
    private TextService textService;

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
            case "id" : value.append("" + rowId);
                        break;
            case "uuid" : value.append(quote(textService.makeUuid().getValue()));
                        break;
            case "rtext" : value.append(textService.makeText(field.getLength()));
                        break;
            case "address" :
                        Optional<Address> opAddr = addrService.makeAddress(field.getGender(), field.getNameFormat());
                        value.append(opAddr.get());
        }
        return value.toString();
    }

    public String quote(String arg) {
        if (argHelper.noContent(arg)) {
            return "''";
        }
        return "'" + arg + "'";
    }
}
