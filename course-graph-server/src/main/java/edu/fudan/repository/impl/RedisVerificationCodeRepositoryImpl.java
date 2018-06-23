package edu.fudan.repository.impl;

import edu.fudan.config.Constants;
import edu.fudan.repository.VerificationCodeRepository;
import edu.fudan.exception.InvalidVerificationCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisVerificationCodeRepositoryImpl implements VerificationCodeRepository {
    private RedisTemplate<String, String> redis;

    @Autowired
    public RedisVerificationCodeRepositoryImpl(RedisTemplate<String, String> redis) {
        this.redis = redis;
    }

    @Override
    public String createVerificationCode(String email) {
        // 使用uuid作为验证码
        String code = UUID.randomUUID().toString();
        // 存储到redis并设置过期时间
        redis.boundValueOps(email).set(code, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return code;
    }

    @Override
    public boolean checkVerificationCode(String email, String verificationCode) {
        if (verificationCode == null || verificationCode.length() == 0)
            throw new InvalidVerificationCodeException();
        String code = redis.boundValueOps(email).get();
        if (!code.equals(verificationCode))
            return false;
        deleteToken(email);
        return true;
    }

    @Override
    public String getVerificationCode(String email) {
        if (email == null || email.length() == 0)
            throw new InvalidVerificationCodeException();

        return redis.boundValueOps(email).get();
    }

    @Override
    public void deleteToken(String email) {
        redis.delete(email);
    }
}
