package com.api.domain.users.service;

import com.api.domain.users.entity.Email;
import com.api.domain.users.repository.UserRedisRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRedisRepository userRedisRepository;

    private final String SENDER_EMAIL = "ssjcd0913@gmail.com";

    public void sendEmail(String email) {
        int number = createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            String body = "";
            body += "<h3> 가입 인증 번호입니다. </h3>";
            body += "<h1> " + number + " </h1>";

            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(SENDER_EMAIL); // 보내는 사람
            helper.setTo(email); // 받는 사람
            helper.setSubject("이메일 인증"); // 이메일 제목
            helper.setText(body, true);
        } catch (MessagingException e){
            System.out.println(e);
        }

        // 메일 전송
        javaMailSender.send(message);

        Email sendEmail = new Email(email, number, 180);
        userRedisRepository.save(sendEmail);
    }

    public int createNumber(){
        return (int)((Math.random() * 90000)) + 100000;
    }
}