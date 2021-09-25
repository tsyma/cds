package net.lafox.cds.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IpAddressMatcherTest {

    void matches_localhost() {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher("localhost/8");
        assertThat(ipAddressMatcher.matches("127.0.0.1")).isTrue();
        assertThat(ipAddressMatcher.matches("127.0.0.255")).isTrue();
        assertThat(ipAddressMatcher.matches("127.0.1.255")).isTrue();
        assertThat(ipAddressMatcher.matches("127.1.1.255")).isTrue();
        assertThat(ipAddressMatcher.matches("126.0.0.1")).isFalse();
        assertThat(ipAddressMatcher.matches("128.0.0.1")).isFalse();
    }

    @Test
    void matches_host() {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher("192.168.0.1");
        assertThat(ipAddressMatcher.matches("192.168.0.1")).isTrue();
        assertThat(ipAddressMatcher.matches("192.168.0.0")).isFalse();
        assertThat(ipAddressMatcher.matches("192.168.0.2")).isFalse();
    }

    @Test
    void testToString() {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher("localhost/8");
        assertThat(ipAddressMatcher.toString()).isEqualTo("127.0.0.1/8");
    }

}