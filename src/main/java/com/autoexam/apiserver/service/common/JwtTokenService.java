package com.autoexam.apiserver.service.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.autoexam.apiserver.exception.AuthenticationException;
import com.autoexam.apiserver.model.AuthenticationInfo;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

@Service
public class JwtTokenService {
  @Value("${server.ssl.key-store-name}")
  private String keyName;
  @Value("${server.ssl.key-alias}")
  private String keyAlias;
  @Value("${server.ssl.key-store-password}")
  private String keyPassword;

  public String generateToken(AuthenticationInfo auth) {
    try {
      String payloadStr = JSONUtil.toJsonStr(auth);
      RSAKey rsaKey = getSystemRSAKey();
      //创建JWS头，设置签名算法和类型
      JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
        .type(JOSEObjectType.JWT)
        .build();
      //将负载信息封装到Payload中
      Payload payload = new Payload(payloadStr);
      //创建JWS对象
      JWSObject jwsObject = new JWSObject(jwsHeader, payload);
      //创建RSA签名器
      JWSSigner jwsSigner = new RSASSASigner(rsaKey, true);
      //签名
      jwsObject.sign(jwsSigner);
      return jwsObject.serialize();
    } catch (Exception e) {
      throw new RuntimeException("Internal error", e);
    }
  }

  public AuthenticationInfo verifyToken(String token, int tolerateSecond) {
    try {
      RSAKey rsaKey = getSystemRSAKey();
      //从token中解析JWS对象
      JWSObject jwsObject = JWSObject.parse(token);
      JWSVerifier jwsVerifier = new RSASSAVerifier(rsaKey);
      if (!jwsObject.verify(jwsVerifier)) {
        throw new AuthenticationException("token签名不合法！");
      }
      String payload = jwsObject.getPayload().toString();
      AuthenticationInfo payloadDto = JSONUtil.toBean(payload, AuthenticationInfo.class);
      if (payloadDto.getExp() < DateUtil.offsetSecond(new Date(), tolerateSecond).getTime()) {
        throw new AuthenticationException("token已过期！");
      }
      return payloadDto;
    } catch (ParseException | JOSEException e) {
      throw new RuntimeException("Internal error", e);
    }
  }

  private RSAKey getSystemRSAKey() {
    //从classpath下获取RSA秘钥对
    char[] password = keyPassword.toCharArray();
    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyName), password);
    KeyPair keyPair = keyStoreKeyFactory.getKeyPair(keyAlias, password);
    //获取RSA公钥
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    //获取RSA私钥
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
  }

}
