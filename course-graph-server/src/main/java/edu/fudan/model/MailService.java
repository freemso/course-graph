package edu.fudan.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${mail.smtp.starttls.required}")
    private boolean startlls_required;

    private static final String TITLE_SIGN_UP = "Validation Code for CourseGraph Website Registration";

    // private static final String CONTENT = "[邮件内容]";

    public void sendMessage(String mail, String message) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailProperties.put("mail.smtp.starttls.required", startlls_required);

        mailSender.setJavaMailProperties(mailProperties);
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
