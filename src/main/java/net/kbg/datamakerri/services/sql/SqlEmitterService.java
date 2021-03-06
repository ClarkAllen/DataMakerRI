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

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.kbg.datamakerri.helpers.AlphArgHelper;
import net.kbg.datamakerri.helpers.Definitions;
import net.kbg.datamakerri.input.PatternTemplate;
import net.kbg.datamakerri.model.*;
import net.kbg.datamakerri.services.alpha.*;
import net.kbg.datamakerri.services.bool.BooleanService;
import net.kbg.datamakerri.services.date.DateService;
import net.kbg.datamakerri.services.number.AlphaNumService;
import net.kbg.datamakerri.services.number.DoubleService;
import net.kbg.datamakerri.services.number.LongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
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

    @Autowired
    private DateService dateService;

    @Autowired
    private AlphaNumService alphaNumService;

    @Autowired
    private DoubleService doubleService;

    @Autowired
    private LongService longService;

    public Optional<List<String>> emit(Table table) {
        List<String> sql = new LinkedList<>();
        try {
            if (table.getRows() < 1) {
                return Optional.empty();
            }
            sql.add(makeInsertStatement(table));
            for (int b = 0; b < table.getRows(); ++b) {
                if (b == table.getRows() - 1) {
                    sql.add(generateRow(table.getStartRowNum() + b, table) + ";");
                } else {
                    sql.add(generateRow(table.getStartRowNum() + b, table) + ", ");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(sql);
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

    public String generateRow(long rowId, Table table) {
        StringBuffer sb = new StringBuffer();
        sb.append("  VALUES (");
        for (Field field : table.getFields()) {
            sb.append(",")
                    .append(generateFieldValue(rowId, field));
        }
        sb.append(")");
        return sb.toString().replaceFirst(",", "");
    }

    public String generateFieldValue(long rowId, Field field) {

        StringBuffer value = new StringBuffer();

        switch (field.getDmSourceType().toLowerCase()) {
            case Definitions.ID :
                value.append("" + rowId);
                break;
            case Definitions.UUID :
                value.append(quote(textService.makeUuid().getValue()));
                break;
            case Definitions.RTEXT :
                Optional<TextResult> optTxt = textService.makeText(field.getLength());
                if (optTxt.isPresent()) {
                    value.append(quote(optTxt.get().getValue()));
                } else {
                    throw new RuntimeException("Error making random text");
                }
                break;
            case Definitions.TEXT_FROM_LIST :
                List<String> strLst = field.getItemList();
                Optional<TextResult> optTxl = textService.selectFromList(strLst);
                if (optTxl.isPresent()) {
                    value.append(quote(optTxl.get().getValue()));
                } else {
                    throw new RuntimeException("Error creating text value from list");
                }
                break;
            case Definitions.FNAME :
                Optional<PersonName> opName = nameService.makeNameOfPerson(field.getGender(),
                        "FIRST_MIDDLE_LAST");
                if (opName.isPresent()) {
                    value.append(quote(opName.get().getFirst()));
                } else {
                    throw new RuntimeException("Error making person name");
                }
                break;
            case Definitions.LNAME :
                Optional<PersonName> optName = nameService.makeNameOfPerson("R",
                        "FIRST_MIDDLE_LAST");
                if (optName.isPresent()) {
                    value.append(quote(optName.get().getLast()));
                } else {
                    throw new RuntimeException("Error making person name");
                }
                break;
            case Definitions.STREET :
                Street street = streetService.makeStreet();
                value.append(quote(street.getStreet()));
                break;
            case Definitions.CITY :
                City city = cityService.makeCity();
                value.append(quote(city.getName()));
                break;
            case Definitions.STATE :
                State state = stateService.makeState();
                value.append(quote(state.getName()));
                break;
            case Definitions.BOOL :
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
            case Definitions.DATE_MON_YR :
                Optional<DateValue> optDt = dateService.makeDateInMonthYear(
                        field.getMonth(), field.getYear());
                if (optDt.isPresent()) {
                    value.append(quote(optDt.get().getIso8601()));
                } else {
                    throw new RuntimeException("Error creating date in month/year");
                }
                break;
            case Definitions.DATE_IN_YR :
                Optional<DateValue> optDtyr = dateService.makeDateInYear(
                        field.getYear());
                if (optDtyr.isPresent()) {
                    value.append(quote(optDtyr.get().getIso8601()));
                } else {
                    throw new RuntimeException("Error creating date in year");
                }
                break;
            case Definitions.DATE_BETW_YRS :
                Optional<DateValue> optDtrg = dateService.makeDateInYearRange(
                        field.getYearLowEnd(), field.getYearHighEnd());
                if (optDtrg.isPresent()) {
                    value.append(quote(optDtrg.get().getIso8601()));
                } else {
                    throw new RuntimeException("Error creating date in year range");
                }
                break;
            case Definitions.PATTERN :
                PatternTemplate template = new PatternTemplate(field.getPattern(), field.getCharSymbol(), field.getNumSymbol());
                Optional<TextResult> optRslt = alphaNumService.makeAlphaNumPatternText(template);
                if (optRslt.isPresent()) {
                    value.append(quote(optRslt.get().getValue()));
                } else {
                    throw new RuntimeException("Error creating pattern");
                }
                break;
            case Definitions.DOUBLE :
                Optional<NumberValue> optDub = doubleService.doubleInRange(
                        field.getRangeLowEnd(), field.getRangeHighEnd());
                if (optDub.isPresent()) {
                    double d = optDub.get().getNumber().doubleValue();
                    String fmt = argHelper.makePrecisionPattern(field.getPrecision());
                    DecimalFormat df = new DecimalFormat(fmt);
                    value.append(df.format(d));
                } else {
                    throw new RuntimeException("Error creating decimal value");
                }
                break;
            case Definitions.LONG :
                Optional<NumberValue> optVal = longService.longFromRange(
                        field.getRangeLowEnd(), field.getRangeHighEnd());
                if (optVal.isPresent()) {
                    value.append(optVal.get().getStrNum());
                } else {
                    throw new RuntimeException("Error creating number value");
                }
                break;
            case Definitions.LONG_FROM_LIST :
                List<String> strlst = field.getItemList();
                Optional<List<Long>> optlngs = argHelper.makeLongListFromStringList(strlst);
                if (optlngs.isPresent()) {
                    Optional<NumberValue> optlv = longService.longFromList(optlngs.get());
                    if (optlv.isPresent()) {
                        value.append(optlv.get().getStrNum());
                    } else {
                        throw new RuntimeException("Error creating number value");
                    }
                } else {
                    throw new RuntimeException("Error creating number value");
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
