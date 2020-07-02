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


public class DateValue {
    private String iso8601;
    private long milliseconds;

    public DateValue() {
    }

    public DateValue(String iso8601, long milliseconds) {
        this.iso8601 = iso8601;
        this.milliseconds = milliseconds;
    }

    public String getIso8601() {
        return iso8601;
    }

    public void setIso8601(String iso8601) {
        this.iso8601 = iso8601;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public String toString() {
        return "DateValue{" +
                "iso8601='" + iso8601 + '\'' +
                ", milliseconds=" + milliseconds +
                '}';
    }
}
