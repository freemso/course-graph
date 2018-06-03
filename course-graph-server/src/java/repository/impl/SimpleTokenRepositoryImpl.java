package java.repository.impl;

import java.domain.TokenEntry;
import java.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SimpleTokenRepositoryImpl implements TokenRepository {

    private Map<Long, String> id2token;
    private Map<String, Long> token2id;

    @Autowired
    public SimpleTokenRepositoryImpl() {
        this.id2token = new HashMap<>();
        this.token2id = new HashMap<>();
    }

    @Override
    public TokenEntry createToken(long id) {
        return null;
    }

    @Override
    public boolean checkToken(TokenEntry tokenEntry) {
        return false;
    }

    @Override
    public TokenEntry getToken(String authentication) {
        return null;
    }

    @Override
    public String getAuthentication(TokenEntry tokenEntry) {
        return null;
    }

    @Override
    public void deleteToken(long id) {

    }
}
