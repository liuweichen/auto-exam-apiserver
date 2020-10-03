package com.autoexam.apiserver;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServerApplicationTests {
	@Autowired
	StringEncryptor encryptor;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testEncrypt() {
		String encrypt = encryptor.encrypt("test");
		String origin = encryptor.decrypt(encrypt);
		Assert.assertEquals("test", origin);
	}

}
