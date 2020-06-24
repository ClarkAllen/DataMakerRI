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

package net.kbg.datamakerri.helpers;

import net.kb.datamaker.alpha.*;
import net.kbg.datamakerri.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.spi.StateFactory;
import java.util.Optional;

@Component
public class NameFormatProcessor {

    @Autowired
    Definitions definitions;

    public Optional<Address> processNameFormat(String namefmt) {
        Address address = null;
        switch (namefmt.toUpperCase()) {
            case "FI_LAST" :
                address = new Address(
                        AddressFactory.addressToPerson(Gender.findRandomGender(),
                                definitions.getNameFormatMap().get("FIRST_LAST")));
                address.setName(NameFactory.fiLast());
                break;
            case "FI_MI_LAST" :
                address = new Address(
                        AddressFactory.addressToPerson(Gender.findRandomGender(),
                                definitions.getNameFormatMap().get("FIRST_LAST")));
                address.setName(NameFactory.fiMiLast());
                break;
            case "LAST_FI" :
                address = new Address(
                        AddressFactory.addressToPerson(Gender.findRandomGender(),
                                definitions.getNameFormatMap().get("FIRST_LAST")));
                address.setName(NameFactory.lastCommaFi());
                break;
            case "LAST_FI_MI" :
                address = new Address(
                        AddressFactory.addressToPerson(Gender.findRandomGender(),
                                definitions.getNameFormatMap().get("FIRST_LAST")));
                address.setName(NameFactory.lastCommaFiMi());
                break;
        }
        return Optional.ofNullable(address);
    }
}
