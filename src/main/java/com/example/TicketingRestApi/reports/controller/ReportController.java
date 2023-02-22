package com.example.TicketingRestApi.reports.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.TicketingRestApi.reports.service.ReportService;
import com.example.TicketingRestApi.ticket.repository.TicketRepository;
import com.example.TicketingRestApi.ticket.service.TicketService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    TicketService ticketService;
    
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ReportService reportService;

    @GetMapping("/reports")
    public String reportOne()
    {
        return "report/report_one";
    }
    
   
    @GetMapping("/export")
    public void exportToExcel(@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate, 
                              @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate,
                              HttpServletResponse response) throws Exception {
        List<Object[]> data = ticketRepository.findTotalAdultChildrenAndTotalPriceByTicketNameAndDateRange(startDate, endDate);
        exportExcel(response, data);
    }
    
    private void exportExcel(HttpServletResponse response, List<Object[]> data) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Ticket Report");
    
            String[] columns = {"Ticket Name", "Total Adults", "Total Children", "Total Price"};
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(columns[i]);
            }
    
            for (Object[] rowData : data) {
                row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Long) {
                        cell.setCellValue((Long) field);
                    } else if (field instanceof Double) {
                        cell.setCellValue((Double) field);
                    }
                }
            }
    
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"tickets.xlsx\"");
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        }
    }
    
}
