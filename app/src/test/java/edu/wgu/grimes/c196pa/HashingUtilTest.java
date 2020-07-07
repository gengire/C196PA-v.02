package edu.wgu.grimes.c196pa;

import org.junit.Test;

import edu.wgu.grimes.c196pa.utilities.HashingUtil;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HashingUtilTest {

    @Test
    public void hashingTest() throws Exception {
        String pass = "testing";
        String badPass = "Testing";
        String hashedPass = HashingUtil.generateStrongPassword(pass);
        assertThat(HashingUtil.validatePassword(pass, hashedPass), is(true));
        assertThat(HashingUtil.validatePassword(badPass, hashedPass), is(false));
        System.out.println(hashedPass);
    }
}
