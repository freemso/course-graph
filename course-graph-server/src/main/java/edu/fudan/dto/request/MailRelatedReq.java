package edu.fudan.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MailRelatedReq {
    @Email(message = "invalid email.")
    @NotBlank
    private String email;

    public MailRelatedReq() {
    }

    public String getEmail() {
        return email;

    }
}
