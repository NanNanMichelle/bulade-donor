package utils;

import com.bulade.donor.common.utils.crypto.CryptoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class CryptoUtilsTest {

    @Test
    public void testDES() {
        var s1 = CryptoUtils.encryptByDesEcb("shinowkey", "shinow");
        assertThat(s1).isEqualTo("E+VrOdeDHZg=");

        var s2 = CryptoUtils.decryptByDesEcb("shinowkey", s1);
        assertThat(s2).isEqualTo("shinow");
    }

    @Test
    public void testAES() {
        var key = "a36aa6b79-OptServiceAPI-0c284700";
        var hexIV = "d5b1b6f900edaf02d751700eb1431912";

        var s1 = CryptoUtils.encryptByAesCbc(key, hexIV, "shinow");
        assertThat(s1).isEqualTo("24HGDXRMPItyb97sCqd2TQ==");

        var s2 = CryptoUtils.decryptByAesCbc(key, hexIV, s1);
        assertThat(s2).isEqualTo("shinow");
    }

    @Test
    public void testHMAC() {
        var s1 = CryptoUtils.hmacSha256("a36aa6b79-OptServiceAPI-0c284700", "shinow");
        assertThat(s1).isEqualTo("2ddfe5f664cdf297eeafd419bfe2d3b8dc1c4e871ffec9a1deb0063810e9b61f");
    }

}
