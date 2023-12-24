package ru.greenatom.acquiringservice.utils.request;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;
import ru.greenatom.acquiringservice.domain.dto.PaymentResponseDto;
import ru.greenatom.acquiringservice.domain.entity.BankAccount;
import ru.greenatom.acquiringservice.domain.enums.PaymentStatus;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;

@Slf4j
@Component
public class CheckGenerator {
    private static final int FONT_SIZE = 16;

    private final XWPFDocument document;

    public CheckGenerator() {

        this.document = new XWPFDocument();
    }

    public static void main(String[] args) {
        CheckGenerator check = new CheckGenerator();
        PaymentResponseDto responseDto = new PaymentResponseDto(1L,"m.byckoff2015" +
                "@yandex.ru","Chel", "00011",
                PaymentStatus.PAYMENT_COMPLETED, 1000L);
        BankAccount bankAccount = new BankAccount(1L, "0011", 12345L, "Сбер");
        byte[] myByteArray = check.generateCheck(responseDto, bankAccount);

        try (FileOutputStream fos = new FileOutputStream("pathname.docx")) {
            fos.write(myByteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateCheck(PaymentResponseDto dto, BankAccount bankAccount) {
        log.debug("Process check generation");
        createMessage(Constants.DOC_NAME_LABEL+dto.getId());
        createInfo(Constants.TIME_LABEL, Instant.now().toString().replace("T", " ")
                .replace("Z", " "));
        createInfo(Constants.CARD_NUMBER_LABEL, dto.getCardNumber());
        createInfo(Constants.SUM_LABEL, dto.getSumOfPay().toString()+" рублей");
        createInfo(Constants.BANK_LABEL, bankAccount.getNameOfBank());
        createMessage("Спасибо за покупку!");
        return getByteArray();
    }

    private void createMessage(String text) {
        log.debug("Creating title");
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(FONT_SIZE);
        titleRun.setText(text);
        document.createParagraph();
    }

    private void createInfo(String label, String info) {
        log.debug("Creating info for {}", label);
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun runStart = paragraph.createRun();
        int marginCount = 50 - label.length();
        StringBuilder spaces = new StringBuilder();
        spaces.append(label);
        spaces.append(" ".repeat(marginCount*2));
        spaces.append(info);
        runStart.setText(spaces.toString());
        runStart.setFontSize(12);
        runStart.setBold(true);
        runStart.setItalic(true);
        paragraph.setAlignment(ParagraphAlignment.BOTH);
    }

    private byte[] getByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("IO");
        }
        return out.toByteArray();
    }
}
