package com.inn.appointment.serviceImpl;

import com.inn.appointment.JWT.JwtFilter;
import com.inn.appointment.POJO.Bill;
import com.inn.appointment.constents.AppointmentConstant;
import com.inn.appointment.dao.BillDao;
import com.inn.appointment.service.BillService;
import com.inn.appointment.utils.AppointmentUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@Service
public class BillServiceImpl implements BillService {

    /**
     * @param requestMap
     * @return
     */

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside Generate Reports");
        try {
            String fileName;
            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = AppointmentUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }

                String data = "Name: " + requestMap.get("name") + "\n" + "Phone: " + requestMap.get("phone") + "\n" + "Email: " + requestMap.get("email")
                        + "\n" + requestMap.get("address") + "\n" + "Payment Method :" + requestMap.get("paymentmethod");

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(AppointmentConstant.STORE_LOCATION + "/" + fileName + ".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph chunk = new Paragraph("Bill", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);


                JSONArray jsonArray = AppointmentUtils.getJsonArrayFromString((String) requestMap.get("description"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, AppointmentUtils.getMapFromJsonString(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total Amount: " + requestMap.get("amount") + "\n" + "Thank you for the appointment", getFont("Data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"message\":\"Report Generated Successfully\"}", HttpStatus.OK);
            }
            return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppointmentUtils.getResponseEntity(AppointmentConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("description"));
        table.addCell((String) data.get("phone"));
        table.addCell((String) data.get("paymentmethod"));
        table.addCell(Double.toString((Double) data.get("amount")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("inside addTableHeader");
        Stream.of("Name", "description", "phone", "paymentmethod", "amount")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(header);
                });
    }

    private Font getFont(String header) {
        log.info("inside getFont");
        String type = null;
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {

        log.info("inside setRectangleInPdf");
        Rectangle react = new Rectangle(577, 825, 18, 15);
        react.enableBorderSide(1);
        react.enableBorderSide(2);
        react.enableBorderSide(4);
        react.enableBorderSide(8);
        react.setBackgroundColor(BaseColor.BLACK);
        react.setBorderWidth(1);
        document.add(react);

    }

    private void insertBill(Map<String, Object> requestMap) {

        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setPhone((String) requestMap.get("phone"));
            bill.setAddress((String) requestMap.get("address"));
            bill.setPaymentmethod((String) requestMap.get("paymentmethod"));
            bill.setAmount(Integer.parseInt((String) requestMap.get("amount")));
            bill.setDescription((String) requestMap.get("description"));
            bill.setBilledBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("phone") &&
                requestMap.containsKey("address") &&
                requestMap.containsKey("paymentmethod") &&
                requestMap.containsKey("amount") &&
                requestMap.containsKey("Description") &&
                requestMap.containsKey("BilledBy");
    }


    /**
     * @return
     */
    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
        if (jwtFilter.isAdmin()) {
            list = billDao.getAllBIlls();
        } else {
            list = billDao.getBillsByUserName(jwtFilter.getCurrentUser());
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @param requestMap
     * @return
     */
    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf");
        try {
            byte[] byteArray = new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            String filePath = AppointmentConstant.STORE_LOCATION + "/" + requestMap.get("uuid") + ".pdf";
            if (AppointmentUtils.isFileExist(filePath)) {
                byteArray = getBytesArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerate", false);
                generateReport(requestMap);
                byteArray = getBytesArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            Optional optional = billDao.findById(id);
            if(!optional.isEmpty()){
                billDao.deleteById(id);
                return AppointmentUtils.getResponseEntity("Bill Deleted Successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("{\"message\":\"Bill Id does not exists\"}", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytesArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

}
