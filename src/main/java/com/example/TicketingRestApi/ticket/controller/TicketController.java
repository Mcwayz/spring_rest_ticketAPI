package com.example.TicketingRestApi.ticket.controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.TicketingRestApi.ticket.model.Ticket;
import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.service.TicketService;
import com.example.TicketingRestApi.ticket.service.TicketTypeService;
import com.example.TicketingRestApi.ticket.shared.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;



@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketTypeService ticketTypeService;

    //get list of tickets
    @GetMapping("/tickets")
    public String getTickets(Model model)
    {
        List<Ticket> ticketList = ticketService.getTickets();
        model.addAttribute("tickets", ticketList);
        return "ticket/ticket_list";
    }

    //get ticket details by id
    @GetMapping("/tickets/ticketDetails/{id}")
    public String ticketDetails(@PathVariable int id, Model model)
    {
        Ticket ticket = ticketService.getById(id);
        
        String barcode = ticket.getBarcode();

         // Create the barcode writer
         
         byte[] image = new byte[0];
         try {
 
             // Generate and Return Qr Code in Byte Array
             image = QRCodeGenerator.getQRCodeImage(barcode,250,250);
 
             // Generate and Save Qr Code Image in static/image folder
            //  QRCodeGenerator.generateQRCodeImage(github,250,250,QR_CODE_IMAGE_PATH);
 
         } catch (WriterException | IOException e) {
             e.printStackTrace();
         }

         // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("ticket",ticket);
        model.addAttribute("qrcode",qrcode);

        return "/ticket/ticket_details";
    }

    //return buy ticket menu page
    @GetMapping("/tickets/buyTicket")
    public String buyTicket()
    {
        return "/ticket/ticket_buy_menu";
    }

    //return buy ticket cash page
    @GetMapping("/tickets/buyTicket/buyTicketCash")
    public String buyTicketCash(Model model)
    {
        List<TicketType> ticketTypeList = ticketTypeService.getTicketTypes();

        model.addAttribute("ticketTypes", ticketTypeList);
        return "/ticket/ticket_buy_cash";
    }

    //return buy ticket cash page
    @GetMapping("/tickets/buyTicket/buyTicketMNO")
    public String buyTicketMNO(Model model)
    {
        List<TicketType> ticketTypeList = ticketTypeService.getTicketTypes();

        model.addAttribute("ticketTypes", ticketTypeList);
        return "/ticket/ticket_buy_mno";
    }

    //save new ticket MMO
    @PostMapping("/ticketMNO")
    public String saveTicketMNO(Ticket ticket)
    {
        
            ticketService.save(ticket);
        
        return "redirect:/ticket/tickets";
    }

    @GetMapping("/tickets/editTicket/{id}")
    public String editTicket(@PathVariable int id, Model model)
    {
        Ticket ticket = ticketService.getById(id);
        model.addAttribute("ticket", ticket);
        return "/ticket/ticket_edit";
    }

     //delete ticket
     //working
     @RequestMapping(value="/tickets/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
     public String delete(@PathVariable Integer id) {
         ticketService.delete(id);
         return "redirect:/ticket/tickets";
     }


     //print pdf 
     @GetMapping(value = "/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
     public ResponseEntity<InputStreamResource> pdf(@PathVariable int id) throws IOException{
 
         Ticket ticket = ticketService.getById(id);
         
         String barcode = ticket.getBarcode();

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

           
            File ticketFile = new File("src/main/resources/static/img/zitf.png");
            Image imgTicket = new Image(ImageDataFactory.create(ticketFile.getAbsolutePath()));
            imgTicket.scaleAbsolute(100, 36);
            imgTicket.setMarginTop(30);
            imgTicket.setHorizontalAlignment(HorizontalAlignment.CENTER);

    
            // File logoFile = new File("src/main/resources/static/img/logo.jpg");
            // Image logo = new Image(ImageDataFactory.create(logoFile.getAbsolutePath()));
            // logo.scaleAbsolute(36,36);
            // logo.setHorizontalAlignment(HorizontalAlignment.RIGHT);
          
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
                    .add(new Paragraph(ticket.getPhoneNumber())
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
                    .add(new Paragraph(ticket.getTicketName())
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
                    .add(new Paragraph(String.valueOf(ticket.getId()))
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
                    .add(new Paragraph(String.valueOf(ticket.getAdult()))
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
                    .add(new Paragraph(String.valueOf(ticket.getChildren()))
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
                    .add(new Paragraph(String.valueOf(ticket.getTotalPrice()))
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
        
         // Return the PDF as a response
         HttpHeaders headers = new HttpHeaders();
         headers.add("Content-Disposition", "inline; filename=ticket.pdf");
 
         return ResponseEntity
                 .ok()
                 .headers(headers)
                 .contentType(MediaType.APPLICATION_PDF)
                 .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
     
 }
 

   
}

