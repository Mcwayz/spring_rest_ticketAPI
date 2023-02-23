package com.example.TicketingRestApi.ticket.shared.util;
import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.TicketingRestApi.ticket.restapi.dto.TicketDto;
import com.example.TicketingRestApi.ticket.shared.QRCodeGenerator;
import com.example.TicketingRestApi.ticket.shared.mail.service.MailService;
import com.google.zxing.WriterException;
import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

@Component
public class TicketPdf {


    @Autowired
    private MailService mailService;

    public void createPdf(TicketDto ticketDto) throws IOException, java.io.IOException
    {
      
    
        String barcode = ticketDto.getBarcode();

            // Create the barcode writer
            
            byte[] image = new byte[0];
            try {

                // Generate and Return Qr Code in Byte Array
                image = QRCodeGenerator.getQRCodeImage(barcode,250,250);
             
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }

            // Convert Byte Array into Base64 Encode String
            ImageData qrImage = ImageDataFactory.create(image);
        
            // Create a new document and a PDF writer
            PdfWriter writer = new PdfWriter("Ticket.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A6);
            
           
            
            Document document = new Document(pdf);
            // Open the document and add some content
            document.setMargins(0, 0,0, 0);

            // File backgroundFile = new File("src/main/resources/static/img/background.png");
            // Image imgBackground = new Image(ImageDataFactory.create(backgroundFile.getAbsolutePath()));
            // imgBackground.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            // imgBackground.setFixedPosition(0, 0);

            File ticketFile = new File("src/main/resources/static/img/zitf.png");
            Image imgTicket = new Image(ImageDataFactory.create(ticketFile.getAbsolutePath()));
            imgTicket.scaleAbsolute(100, 36);
            imgTicket.setMarginTop(30);
            imgTicket.setHorizontalAlignment(HorizontalAlignment.CENTER);
   
            final float COL = 70;

            final float MGL = 30;

            final float[] COLUMN_WIDTHS = {80, COL, COL, COL, COL};
            
          // Create a table with column widths
            Table table = new Table(COLUMN_WIDTHS);
            table.setMarginLeft(MGL);
            table.setMarginTop(30);

            // Add a cell for the phone number
            Cell phoneNumberCell = new Cell()
                    .add(new Paragraph("Phone Number")
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table.addCell(phoneNumberCell);

            // Add a cell for the phone number value
            Cell phoneNumberValueCell = new Cell()
                    .add(new Paragraph(ticketDto.getPhoneNumber())
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table.addCell(phoneNumberValueCell);

            // Create a table for the ticket type
            Table table2 = new Table(COLUMN_WIDTHS);
            table2.setMarginLeft(MGL);

            // Add a cell for the ticket type
            Cell ticketTypeCell = new Cell()
                    .add(new Paragraph("Ticket Type")
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table2.addCell(ticketTypeCell);

            // Add a cell for the ticket type value
            Cell ticketTypeValueCell = new Cell()
                    .add(new Paragraph(ticketDto.getTicketName())
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table2.addCell(ticketTypeValueCell);

            // Create a table for the ticket ID
            Table table3 = new Table(COLUMN_WIDTHS);
            table3.setMarginLeft(MGL);

            // Add a cell for the ticket ID
            Cell ticketIdCell = new Cell()
                    .add(new Paragraph("Ticket ID")
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table3.addCell(ticketIdCell);

            // Add a cell for the ticket ID value
            Cell ticketIdValueCell = new Cell()
                    .add(new Paragraph(String.valueOf(ticketDto.getId()))
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table3.addCell(ticketIdValueCell);

            // Create a table for the number of adult tickets
            Table table4 = new Table(COLUMN_WIDTHS);
            table4.setMarginLeft(MGL);

            // Add a cell for the number of adult tickets
            Cell adultTicketsCell = new Cell()
                    .add(new Paragraph("Adult")
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table4.addCell(adultTicketsCell);

            // Add a cell for the number of adult tickets value
            Cell adultTicketsValueCell = new Cell()
                    .add(new Paragraph(String.valueOf(ticketDto.getAdult()))
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table4.addCell(adultTicketsValueCell);

            // Create a table for the number of children tickets
            Table table5 = new Table(COLUMN_WIDTHS);
            table5.setMarginLeft(MGL);

            // Add a cell for the number of children tickets
            Cell childrenTicketsCell = new Cell()
                    .add(new Paragraph("Children")
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table5.addCell(childrenTicketsCell);

            // Add a cell for the number of children tickets value
            Cell childrenTicketsValueCell = new Cell()
                    .add(new Paragraph(String.valueOf(ticketDto.getChildren()))
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table5.addCell(childrenTicketsValueCell);

             // Create a table for the price
             Table table6 = new Table(COLUMN_WIDTHS);
             table6.setMarginLeft(MGL);

            // Add a cell for the price
            Cell priceCell = new Cell()
                    .add(new Paragraph("Total Price")
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table6.addCell(priceCell);

            // Add a cell for price
            Cell priceValueCell = new Cell()
                    .add(new Paragraph(String.valueOf(ticketDto.getTotalPrice()))
                            .setFontSize(6)
                            .setFontColor(ColorConstants.BLACK))
                    .setBorder(Border.NO_BORDER);
            table6.addCell(priceValueCell);
            
            
            
            Image imgQRImage = new Image(qrImage);
            float x = (PageSize.A6.getWidth() + 50) / 2;
            float y = (PageSize.A6.getHeight() - 75 - 100);//tincker with this next time 
            imgQRImage.setFixedPosition(x, y);
            imgQRImage.scaleAbsolute(80, 80);
           
            document.add(imgTicket);
            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(table4);
            document.add(table5);
            document.add(table6);
            document.add(imgQRImage);
            document.close();
            pdf.close();
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream("Ticket.pdf");
            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            fis.close();
               // Send the PDF as an email attachment
        mailService.sendMail(ticketDto.getEmail(), "Ticket purchase successful...", "Thank you for purchasing a ticket !Please find your ticket attached to this email as a PDF document.", out.toByteArray(), "ticket.pdf", "application/pdf");

    }

    
}
