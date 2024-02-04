package com.bulade.donor.framework;

import com.bulade.donor.framework.ip.core.Area;
import com.bulade.donor.framework.ip.core.utils.IPUtils;
import org.junit.jupiter.api.Test;
import org.lionsoul.ip2region.xdb.Searcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link IPUtils} 的单元测试
 */
public class IPUtilsTest {

    @Test
    public void testGetAreaIdString() {
        Integer areaId = IPUtils.getAreaId("120.202.4.50");
        assertEquals(420600, areaId);
    }

    @Test
    public void testGetAreaIdLong() throws Exception {
        long ip = Searcher.checkIP("120.203.123.250");
        Integer areaId = IPUtils.getAreaId(ip);
        assertEquals(360900, areaId);
    }

    @Test
    public void testGetAreaString() {
        Area area = IPUtils.getArea("120.202.4.50");
        assertEquals("襄阳市", area.getName());
    }

    @Test
    public void testGetAreaLong() throws Exception {
        long ip = Searcher.checkIP("120.203.123.252");
        Area area = IPUtils.getArea(ip);
        assertEquals("宜春市", area.getName());
    }

}
