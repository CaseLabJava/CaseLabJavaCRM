package com.greenatom.utils.generator.request;

import com.greenatom.domain.entity.Client;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
public class OrderGenerator {
    private static final int FONT_SIZE = 16;
    private static final int DEFAULT_WIDTH = 10000;
    private static final int TOP_MARGIN = 0;
    private static final int BOT_MARGIN = 0;
    private static final int LEFT_MARGIN = 100;
    private static final int RIGHT_MARGIN = 100;
    private static final int NUMBER_COL_POS = 0;
    private static final int NAME_COL_POS = 1;
    private static final int AMOUNT_COL_POS = 2;
    private static final int COST_COL_POS = 3;
    private static final int TOTAL_COL_POS = 4;

    private final XWPFDocument document;

    public OrderGenerator() {
        this.document = new XWPFDocument();
    }

    public byte[] processGeneration(List<OrderItem> products, Client client, Employee employee, String path) {
        log.debug("Process request generation");
        createTitle();
        createInfo(Constants.SELLER_LABEL, employee.getFullName(),  Constants.ADDRESS);
        createInfo(Constants.BUYER_LABEL, client.getFullName(),  client.getAddress());
        createTable(document, products);
        createTotalCostText(products.stream().mapToLong(OrderItem::getTotalCost).sum());
        createSignatureFields(document, employee);
        return getByteArray();
    }

    private void createTitle() {
        log.debug("Creating title");
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(FONT_SIZE);
        titleRun.setText(Constants.DOC_NAME_LABEL);
        document.createParagraph();
    }

    private void createInfo(String label, String info, String address) {
        log.debug("Creating info for {}", label);
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setText(label);
        run.setText(info);
        run.addBreak();
        run.setText(address);
    }

    private void createTable(XWPFDocument document, List<OrderItem> products) {
        log.debug("Creating product table");
        int rowNum = 1;
        int colNum = 5;
        XWPFTable table = document.createTable(rowNum, colNum);
        table.setTableAlignment(TableRowAlign.LEFT);
        table.setWidth(DEFAULT_WIDTH);
        table.setCellMargins(TOP_MARGIN, LEFT_MARGIN, BOT_MARGIN, RIGHT_MARGIN);
        String[] columnHeaders = Constants.REQUEST_COL_HEADERS;
        int startPos = 0;
        XWPFTableRow headerRow = table.getRow(startPos);
        for (int i = 0; i < columnHeaders.length; i++) {
            XWPFTableCell cell = headerRow.getCell(i);
            XWPFParagraph headerParagraph = cell.addParagraph();
            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun headerRun = headerParagraph.createRun();
            headerRun.setBold(true);
            headerRun.setText(columnHeaders[i]);
        }
        fillTableWithData(table, products);
    }

    private void fillTableWithData(XWPFTable table, List<OrderItem> products) {
        log.debug("Filling product table with data");
        XWPFTableRow dataRow;
        for (int i = 0; i < products.size(); i++) {
            OrderItem product = products.get(i);
            dataRow = table.createRow();
            dataRow.getCell(NUMBER_COL_POS).setText(String.valueOf(i + 1));
            dataRow.getCell(NAME_COL_POS).setText(product.getName());
            dataRow.getCell(AMOUNT_COL_POS).setText(String.valueOf(product.getOrderAmount()));
            dataRow.getCell(COST_COL_POS).setText(String.valueOf(product.getCost()));
            dataRow.getCell(TOTAL_COL_POS).setText(String.valueOf(product.getTotalCost()));
        }
        dataRow = table.createRow();
        dataRow.getCell(NUMBER_COL_POS)
                .setText(Constants.TOTAL_COST_COL_LABEL);
        dataRow.getCell(AMOUNT_COL_POS)
                .setText(String.valueOf(products.stream().mapToLong(OrderItem::getOrderAmount).sum()));
        dataRow.getCell(TOTAL_COL_POS)
                .setText(String.valueOf(products.stream().mapToLong(OrderItem::getTotalCost).sum()));
    }

    private void createTotalCostText(long cost) {
        log.debug("Creating total cost text");
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().setText(String.format(Constants.TOTAL_COST_TEXT_LABEL, TextUtils.intToText((int) cost)));
    }

    private void createSignatureFields(XWPFDocument document, Employee employee) {
        log.debug("Creating seller/buyer signature fields");
        XWPFParagraph signatureFields = document.createParagraph();
        signatureFields.setAlignment(ParagraphAlignment.LEFT);
        assignByManager(signatureFields, employee);
        signatureFields.createRun().addBreak();
        signatureFields.createRun().setText(Constants.BUYER_SIGNATURE_PLACE);
    }

    private void assignByManager(XWPFParagraph paragraph, Employee employee) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        paragraph.createRun().setText(String.format(
                Constants.SELLER_SIGNATURE,
                employee.getFullName(),
                LocalDateTime.now().format(formatter)));
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
