package net.mediger.global.message;

import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailMessageSender implements MessageSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject("Mediger 인증 메일입니다.");
        mailMessage.setText("인증번호 : " + message);

        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAILED_SEND_MAIL);
        }

        mailMessage.getText();
    }
}
