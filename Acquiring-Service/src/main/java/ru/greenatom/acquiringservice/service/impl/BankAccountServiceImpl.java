package ru.greenatom.acquiringservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.greenatom.acquiringservice.domain.dto.PaymentResponseDto;
import ru.greenatom.acquiringservice.domain.entity.BankAccount;
import ru.greenatom.acquiringservice.domain.enums.PaymentStatus;
import ru.greenatom.acquiringservice.exception.AcquiringException;
import ru.greenatom.acquiringservice.repository.BankAccountRepository;
import ru.greenatom.acquiringservice.service.BankAccountService;
import ru.greenatom.acquiringservice.utils.request.CheckGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final KafkaTemplate<Long, PaymentResponseDto> kafkaTemplate;

    private final JavaMailSender mailSender;

    private final Environment env;

    private final CheckGenerator checkGenerator = new CheckGenerator();

    @Override
    @Transactional
    @KafkaListener(topics = "payment", groupId = "consumerServer")
    public void consume(PaymentResponseDto dto) {
        log.info("consuming: {}", dto);
        validatePayment(dto);
        dto.setStatus(PaymentStatus.PAYMENT_COMPLETED);
        sendPaymentResult(dto);
    }

    private void sendPaymentResult(PaymentResponseDto dto) {
        log.info("sending: {}", dto);
        kafkaTemplate.send("payment-result-topic", dto);
    }

    private void validatePayment(PaymentResponseDto dto) {
        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByCardNumber(dto.getCardNumber())
                .orElseThrow(AcquiringException.CODE.BANK_ACCOUNT_NOT_FOUND::get);
        long currBalance = bankAccount.getBalance();
        if (currBalance < dto.getSumOfPay()) {
            throw AcquiringException.CODE.NOT_ENOUGH_BALANCE.get();
        }
        PaymentResponseDto responseDto = new PaymentResponseDto(1L,"m.byckoff2015" +
                "@yandex.ru","Chel", "00011",
                PaymentStatus.PAYMENT_COMPLETED, 1000L);
        BankAccount bankAccount1 = new BankAccount(1L, "0011", 12345L, "Сбер");
        bankAccount.setBalance(currBalance - dto.getSumOfPay());
        byte[] array = checkGenerator.generateCheck(responseDto,bankAccount1);
        sendToEmail(array,dto);
        bankAccountRepository.save(bankAccount);
    }
    private void sendToEmail(byte[] myByteArray, PaymentResponseDto responseDto) {
        File file = new File(responseDto.getId() + ".docx");
        String toAddress = responseDto.getEmail();
        String senderName = "Green Atom";
        String subject = "Ваш заказ";
        String content = "Дорогой [[name]],<br>"
                + "Ваш заказ готов, чек в приложенном файле<br>"
                + "Спасибо за покупку,<br>"
                + "Ваш Green Atom.";
        content = content.replace("[[name]]", responseDto.getFullName());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try (FileOutputStream fos = new FileOutputStream(responseDto.getId() + ".docx")) {
            fos.write(myByteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String fromAddress = env.getProperty("mail_address");
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(fromAddress), senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.addAttachment("Заказ.docx", file);
            helper.setText(content, true);
        } catch (MessagingException | IOException e) {
            throw new MailSendException("Couldn't send email to address: " + toAddress, e);
        }
        mailSender.send(message);
    }
}
