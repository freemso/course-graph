package edu.fudan.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TITLE_SIGN_UP = "Validation Code for CourseGraph Website Registeraion";

    // private static final String CONTENT = "[邮件内容]";

    public void sendMessage(String mail, String message) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mailMessage, true, "GBK");
            helper.setFrom(from);
            helper.setTo(from);
            helper.setCc(mail);
            helper.setSubject(TITLE_SIGN_UP);
            helper.setText(message, true);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
