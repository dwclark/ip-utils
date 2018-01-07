package io.github.dwclark.iputils;

import groovy.transform.*;
import java.util.concurrent.atomic.AtomicReference;
import com.opencsv.*;

@CompileStatic
class Lookup {

    private int parseInt(final String s) {
        return s ? Integer.parseInt(s) : GeoInfo.MISSING_INT;
    }

    private float parseFloat(final String s) {
        return s ? Float.parseFloat(s) : GeoInfo.MISSING_FLOAT;
    }
    
    private final AtomicReference<NavigableMap<Ip4Range, GeoInfo>> ip4Geo = new AtomicReference<>();

    public void replaceIp4Geo(final NavigableMap<Ip4Range,GeoInfo> val) {
        ip4Geo.set(val);
    }
    
    public void replaceIp4Geo(final Reader reader) {
        NavigableMap<Ip4Range,GeoInfo> map = new TreeMap<>();
        new CSVReaderBuilder(reader).withSkipLines(1).build().each { String[] row ->
            Ip4Range r = Ip4Range.immutable(row[0]);
            map[r] = new GeoInfo(_geoNameId: parseInt(row[1]),
                                 _registeredCountryGeoNameId: parseInt(row[2]),
                                 _representedCountryGeoNameId: parseInt(row[3]),
                                 _postalCode: PostalCode.encode(row[6]),
                                 _latitude: parseFloat(row[7]),
                                 _longitude: parseFloat(row[8]),
                                 _radius: parseFloat(row[9])); };
        replaceIp4Geo(map);
    }

    public GeoInfo geoForIp4(final String ipAddress) {
        Ip4Range search = Ip4Range.forSearch(ipAddress);
        Map.Entry<Ip4Range,GeoInfo> entry = (Map.Entry) ip4Geo.get().floorEntry(search);
        if(entry != null && entry.key.inRange(search.lower)) {
            return entry.getValue();
        }
        else {
            return null;
        }
    }
}
