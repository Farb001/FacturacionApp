package com.sc.utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sc.models.Company;
import com.sc.models.billing.Bill;
import com.sc.models.billing.BillProduct;

import javax.print.Doc;
import java.io.File;
import java.io.FileOutputStream;

public class PDFManager {

    private final static String LEFT = "left";
    private final static String RIGHT = "right";
    private final Font bold;
    private final Font plain;

    public PDFManager() {
        bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        plain = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
    }

    public void generateTicket(Bill bill, double pay) {
        String path = System.getProperty("user.home");
        File folder = new File(path + "/Documents/tickets");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        document.setPageSize(new Rectangle(270, 600));
        if (bill.getClient() == null) {
            if (bill.getPaymentType().toString().equalsIgnoreCase("Tarjeta de credito")) {
                ticketWithoutClientAndCash(document, bill, path);
            } else {
                ticketWithoutClient(document, bill, path, pay);
            }
        } else {
            if (bill.getPaymentType().toString().equalsIgnoreCase("Tarjeta de credito")) {
                ticketWithClientAndWithoutCash(document, bill, path);
            } else {
                ticketWithClient(document, bill, path, pay);
            }
        }
    }

    public void generateDetailedTicket(Bill bill, Company company, String notes) {
        String path = System.getProperty("user.home");
        File folder = new File(path + "/Documents/Details_Tickets");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        if (bill.getClient() != null) {
            detailedTicket(document, path, bill, company, notes);
        } else {
            detailedTicketWithoutClient(document, path, bill, company, notes);
        }
    }

    private void ticketWithClientAndWithoutCash(Document document, Bill bill, String path) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/tickets/Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(new Paragraph("Gracias por su compra", plain));
            document.add(new Paragraph(String.valueOf(bill.getClient().getId()), plain));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(bill.getClient().getPhoneNumber(), plain));
            document.add(new Paragraph(bill.getClient().getAddress(), plain));
            document.add(new Paragraph(bill.getClient().getCity() + ", " + bill.getClient().getDepartment(), plain));
            document.add(new Paragraph(" "));
            PdfPTable productsTable = new PdfPTable(3);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{50f, 45f, 40f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()), plain));
            }
            document.add(productsTable);
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(75);
            info.setWidths(new float[]{30f, 45f});
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Subtotal: " + ((int)bill.getSubTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("IVA: " + ((int)(BillProduct.TAX * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Descuento: " + ((int)(bill.getDiscount() * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Total: " + ((int)bill.getTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Pagado: " + ((int)bill.getTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Método de pago: " + "\n" + bill.getPaymentType().toString(), plain));
            document.add(info);
            document.add(new Paragraph("Ticket #" + String.valueOf(bill.getCode()), bold));
            document.add(new Paragraph(bill.getBillHour() + " - " + bill.getBillDate(), bold));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf");
    }

    private void ticketWithoutClientAndCash(Document document, Bill bill, String path) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/tickets/Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(new Paragraph("Gracias por su compra", plain));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            PdfPTable productsTable = new PdfPTable(3);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{50f, 45f, 40f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()), plain));
            }
            document.add(productsTable);
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(75);
            info.setWidths(new float[]{30f, 45f});
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Subtotal: " + ((int)bill.getSubTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("IVA: " + ((int)(BillProduct.TAX * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Descuento: " + ((int)(bill.getDiscount() * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Total: " + ((int)bill.getTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Pagado: " + ((int)bill.getTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Método de pago: " + "\n" + bill.getPaymentType().toString(), plain));
            document.add(info);
            document.add(new Paragraph("Ticket #" + String.valueOf(bill.getCode()), bold));
            document.add(new Paragraph(bill.getBillHour() + " - " + bill.getBillDate(), bold));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf");
    }

    private void ticketWithoutClient(Document document, Bill bill, String path, double pay) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/tickets/Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(new Paragraph("Gracias por su compra", plain));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            PdfPTable productsTable = new PdfPTable(3);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{50f, 45f, 40f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()), plain));
            }
            document.add(productsTable);
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(75);
            info.setWidths(new float[]{30f, 45f});
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Subtotal: " + ((int)bill.getSubTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("IVA: " + ((int)(BillProduct.TAX * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Descuento: " + ((int)(bill.getDiscount() * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Total: " + ((int)bill.getTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Pagado: " + ((int)pay), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Cambio: " + ((int)calculateChange(pay, bill.getTotal())), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Método de pago: " + "\n" + bill.getPaymentType().toString(), plain));
            document.add(info);
            document.add(new Paragraph("Ticket #" + String.valueOf(bill.getCode()), bold));
            document.add(new Paragraph(bill.getBillHour() + " - " + bill.getBillDate(), bold));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf");
    }

    private void ticketWithClient(Document document, Bill bill, String path, double pay) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/tickets/Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(new Paragraph("Gracias por su compra", plain));
            document.add(new Paragraph(String.valueOf(bill.getClient().getId()), plain));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(bill.getClient().getPhoneNumber(), plain));
            document.add(new Paragraph(bill.getClient().getAddress(), plain));
            document.add(new Paragraph(bill.getClient().getCity() + ", " + bill.getClient().getDepartment(), plain));
            document.add(new Paragraph(" "));
            PdfPTable productsTable = new PdfPTable(3);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{50f, 45f, 40f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()), plain));
            }
            document.add(productsTable);
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(75);
            info.setWidths(new float[]{30f, 45f});
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Subtotal: " + ((int)bill.getSubTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("IVA: " + ((int)(BillProduct.TAX * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Descuento: " + ((int)(bill.getDiscount() * 100)) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Total: " + ((int)bill.getTotal()), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Pagado: " + ((int)pay), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Cambio: " + ((int)calculateChange(pay, bill.getTotal())), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Método de pago: " + "\n" + bill.getPaymentType().toString(), plain));
            document.add(info);
            document.add(new Paragraph("Ticket #" + String.valueOf(bill.getCode()), bold));
            document.add(new Paragraph(bill.getBillHour() + " - " + bill.getBillDate(), bold));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf");
    }


    public void detailedTicket(Document document, String path, Bill bill, Company company, String notes) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/Details_Tickets/Detailed_Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(addParagraph(company.getName(), plain, RIGHT));
            document.add(addParagraph(company.getAddress(), plain, RIGHT));
            document.add(addParagraph(company.getCity() + ", " + company.getDepartment().toString(), plain, RIGHT));
            document.add(addParagraph(company.getPhoneNumber(), plain, RIGHT));
            document.add(addParagraph(company.getEmail(), plain, RIGHT));
            document.add(addParagraph(" ", plain, RIGHT));
            document.add(addParagraph(bill.getClient().getName() + " " + bill.getClient().getLastName(), plain, LEFT));
            document.add(addParagraph(bill.getClient().getAddress(), plain, LEFT));
            document.add(addParagraph(bill.getClient().getCity() + ", " + bill.getClient().getDepartment().toString(), plain, LEFT));
            document.add(addParagraph(bill.getClient().getPhoneNumber(), plain, LEFT));
            document.add(addParagraph(bill.getClient().getEmail(), plain, LEFT));
            document.add(addParagraph(" ", plain, LEFT));
            PdfPTable generalInfo = new PdfPTable(5);
            generalInfo.setWidthPercentage(100);
            generalInfo.setWidths(new float[]{70f, 45f, 50f, 45f, 50f});
            generalInfo.addCell(addCell("Fecha de Facturación", bold));
            generalInfo.addCell(addCell("Número de Factura", bold));
            generalInfo.addCell(addCell("Valor Total", bold));
            generalInfo.addCell(addCell("Fecha de pago", bold));
            generalInfo.addCell(addCell("Forma de pago", bold));
            generalInfo.addCell(addCell(bill.getBillDate(), plain));
            generalInfo.addCell(addCell(String.valueOf(bill.getCode()), plain));
            generalInfo.addCell(addCell(String.valueOf((int) bill.getTotal()), plain));
            generalInfo.addCell(addCell(bill.getBillDate(), plain));
            generalInfo.addCell(addCell(bill.getPaymentType().toString(), plain));
            document.add(generalInfo);
            document.add(addParagraph(" ", plain, LEFT));
            PdfPTable productsTable = new PdfPTable(4);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{80f, 40f, 35f, 35f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio Unidad", bold));
            productsTable.addCell(addCell("Precio Total", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName() + "\n" + bill.getProducts().get(i).getDescription(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()) + "\n" + "IVA: " + bill.getProducts().get(i).getTaxProduct(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice() * bill.getProducts().get(i).getQuantity()) + "\n" + "IVA: " + bill.getProducts().get(i).getTaxTotalProducts(), plain));
            }
            document.add(productsTable);
            document.add(addParagraph(" ", plain, RIGHT));
            document.add(addParagraph("Subtotal: " + ((int)bill.getSubTotal()), plain, RIGHT));
            document.add(addParagraph("IVA: " + ((int)(BillProduct.TAX * 100)) + "%", plain, RIGHT));
            document.add(addParagraph("Descuento: " + ((int)bill.getDiscount() * 100) + "%", plain, RIGHT));
            document.add(addParagraph("Total: " + ((int)bill.getTotal()), plain, RIGHT));
            document.add(addParagraph(" ", plain, RIGHT));
            document.add(addParagraph("Firma de Recibo", plain, LEFT));
            document.add(addParagraph(bill.getBillDate() + " - " + bill.getBillHour(), plain, LEFT));
            document.add(addParagraph(notes, plain, LEFT));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf Detailed");
    }

    public void detailedTicketWithoutClient(Document document, String path, Bill bill, Company company, String notes) {
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/Details_Tickets/Detailed_Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(addParagraph(company.getName(), plain, RIGHT));
            document.add(addParagraph(company.getAddress(), plain, RIGHT));
            document.add(addParagraph(company.getCity() + ", " + company.getDepartment().toString(), plain, RIGHT));
            document.add(addParagraph(company.getPhoneNumber(), plain, RIGHT));
            document.add(addParagraph(company.getEmail(), plain, RIGHT));
            document.add(addParagraph(" ", plain, LEFT));
            PdfPTable generalInfo = new PdfPTable(5);
            generalInfo.setWidthPercentage(100);
            generalInfo.setWidths(new float[]{70f, 45f, 50f, 45f, 50f});
            generalInfo.addCell(addCell("Fecha de Facturación", bold));
            generalInfo.addCell(addCell("Número de Factura", bold));
            generalInfo.addCell(addCell("Valor Total", bold));
            generalInfo.addCell(addCell("Fecha de pago", bold));
            generalInfo.addCell(addCell("Forma de pago", bold));
            generalInfo.addCell(addCell(bill.getBillDate(), plain));
            generalInfo.addCell(addCell(String.valueOf(bill.getCode()), plain));
            generalInfo.addCell(addCell(String.valueOf((int) bill.getTotal()), plain));
            generalInfo.addCell(addCell(bill.getBillDate(), plain));
            generalInfo.addCell(addCell(bill.getPaymentType().toString(), plain));
            document.add(generalInfo);
            document.add(addParagraph(" ", plain, LEFT));
            PdfPTable productsTable = new PdfPTable(4);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{80f, 40f, 35f, 35f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio Unidad", bold));
            productsTable.addCell(addCell("Precio Total", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName() + "\n" + bill.getProducts().get(i).getDescription(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()) + "\n" + "IVA: " + bill.getProducts().get(i).getTaxProduct(), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice() * bill.getProducts().get(i).getQuantity()) + "\n" + "IVA: " + bill.getProducts().get(i).getTaxTotalProducts(), plain));
            }
            document.add(productsTable);
            document.add(addParagraph(" ", plain, RIGHT));
            document.add(addParagraph("Subtotal: " + bill.getSubTotal(), plain, RIGHT));
            document.add(addParagraph("IVA: " + (BillProduct.TAX * 100) + "%", plain, RIGHT));
            document.add(addParagraph("Descuento: " + (bill.getDiscount() * 100) + "%", plain, RIGHT));
            document.add(addParagraph("Total: " + bill.getTotal(), plain, RIGHT));
            document.add(addParagraph(" ", plain, RIGHT));
            document.add(addParagraph("Firma de Recibo", plain, LEFT));
            document.add(addParagraph(bill.getBillDate() + " - " + bill.getBillHour(), plain, LEFT));
            document.add(addParagraph(notes, plain, LEFT));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf Detailed");
    }

    private PdfPCell addCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(0);
        return cell;
    }

    private Paragraph addParagraph(String text, Font font, String align) {
        Paragraph paragraph = new Paragraph(text, font);
        switch (align) {
            case LEFT:
                paragraph.setAlignment(Element.ALIGN_LEFT);
                break;
            case RIGHT:
                paragraph.setAlignment(Element.ALIGN_RIGHT);
        }
        return paragraph;
    }

    private double calculateChange(double pay, double count) {
        int change = (int) (pay - count);
        String str = String.valueOf(change);
        String aprox = str.substring((str.length() - 3), str.length());
        if (0 < (Integer.parseInt(aprox.substring(1, 3))) && (Integer.parseInt(aprox.substring(1, 3))) < 50) {
            aprox = String.valueOf(aprox.charAt(0)) + "50";
        } else if (50 < (Integer.parseInt(aprox.substring(1, 3))) && (Integer.parseInt(aprox.substring(1, 3))) < 100) {
            aprox = (Integer.parseInt(String.valueOf(aprox.charAt(0))) + 1) + "00";
        }
        return Double.parseDouble(str.substring(0, str.length() - 3) + aprox);
    }
}
