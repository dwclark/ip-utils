package io.github.dwclark.iputils;

import groovy.transform.*;
import java.util.concurrent.atomic.AtomicReference;

@CompileStatic
class Lookup {
    
    private final AtomicReference<NavigableMap<Ip4Range, GeoInfo>> ip4Geo = new AtomicReference<>();

    public void replace(final NavigableMap<Ip4Range,GeoInfo> val) {
        ip4Geo.set(val);
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
