package edu.fudan.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository {

    /**
     * Create a verification code using email
     *
     * @param email user
     * @return created verification code
     */
    String createVerificationCode(String email);

    /**
     * Check if verification code is still valid
     *
     * @param verificationCode, verification code to be checked
     * @return true for valid  verification code
     */
    boolean checkVerificationCode(String email, String verificationCode);


    /**
     * Get verification code
     *
     * @param email
     * @return
     */
    String getVerificationCode(String email);


    /**
     * Delete token related to user
     *
     * @param email, user email as id
     */
    void deleteToken(String email);

}
