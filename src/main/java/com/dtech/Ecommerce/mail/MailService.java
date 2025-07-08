package com.dtech.Ecommerce.mail;

import com.dtech.Ecommerce.exeption.MailExeption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MailService implements MailServiceImpl{


    private final MailRepo mailRepo;
    @Override
    public void sendMail(MailDTO mail) {
        try{
            Mail mail1 = new Mail();
            mail1.setEmail(mail.getEmail());
            mail1.setMessage(mail.getMessage());
            mail1.setName(mail.getName());
            mailRepo.save(mail1);
        }catch (MailExeption e){
            throw new MailExeption(e.getMessage());
        }

    }
}
