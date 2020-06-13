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

package net.kbg.datamakerri.model;

public class Address {
    private String name;
    private String line1;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

    public Address(String arg) {
        // Argument comes from AddressFactory and needs to be parsed.
        /*
            Kade A. Owen : 7698 Vinal EXPY : Sweet Home MS : 46019-3420
            Myles Z. Townsend : 3456 Boylston RTE E : Chewelah GA : 94614-7535
            Tucker Y. Mclaughlin : 315 Kenney AVE BSMT : Eagle Point OK : 14688-0304
         */
        String fields[] = arg.split(":");
        String cityState[] = fields[2].split(" ");
        StringBuilder cty = new StringBuilder();
        for (int k = 0; k < cityState.length - 1; ++k) {
            cty.append(cityState[k]).append(" ");
        }
        name = fields[0].trim();
        line1 = fields[1].trim();
        state = cityState[cityState.length - 1].trim();
        if (state.equals("DC")) {
            city = "Washington";
        }
        else {
            city = cty.toString().trim();
        }
        zip = fields[3].trim();
    }

    public Address(String name, String line1, String city, String state, String zip) {
        this.name = name;
        this.line1 = line1;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", line1='" + line1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

}
