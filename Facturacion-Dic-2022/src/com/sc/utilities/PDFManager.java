package com.sc.utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sc.models.billing.Bill;

import java.io.File;
import java.io.FileOutputStream;

public class PDFManager {

    private final Font bold;
    private final Font plain;

    public PDFManager() {
        bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        plain = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
    }

    public void generateTicket(Bill bill) {
        String path = System.getProperty("user.home");
        File folder = new File(path + "/Documents/tickets");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        document.setPageSize(new Rectangle(270, 600));
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/tickets/Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(new Paragraph("Gracias por su compra", plain));
            document.add(new Paragraph(String.valueOf(bill.getClient().getId()), plain));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Número de contacto", plain));
            document.add(new Paragraph("Dirección", plain));
            document.add(new Paragraph("Ciudad Departamento", plain));
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
            info.setWidths(new float[]{40f, 35f});
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Subtotal " + bill.getSubTotal(), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("IVA " + (Bill.TAX * 100) + "%", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Descuento xxxx", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Total " + bill.getTotal(), plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Pagado xxxx", plain));
            info.addCell(addCell(" ", plain));
            info.addCell(addCell("Cambio xxxx", plain));
            info.addCell(addCell("Método de pago ", plain));
            document.add(info);
            document.add(new Paragraph(String.valueOf(bill.getCode()), bold));
            document.add(new Paragraph("Hora    Fecha ", bold));
            document.newPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        document.close();
        System.out.println("Se creó el pdf");
    }

    public void generateDetailedTicket(Bill bill) {
        String path = System.getProperty("user.home");
        File folder = new File(path + "/Documents/Details_Tickets");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "/Documents/Details_Tickets/Detailed_Ticket_" + bill.getCode() + ".pdf"));
            document.open();
            document.add(addParagraph("Nombre de la empresa", plain, "right"));
            document.add(addParagraph("Dirección", plain, "right"));
            document.add(addParagraph("Ciudad, Departamento", plain, "right"));
            document.add(addParagraph("Número de contacto", plain, "right"));
            document.add(addParagraph("Correo", plain, "right"));
            document.add(addParagraph(" ", plain, "right"));
            document.add(addParagraph("Nombre del cliente", plain, "left"));
            document.add(addParagraph("Dirección", plain, "left"));
            document.add(addParagraph("Ciudad, Departamento", plain, "left"));
            document.add(addParagraph("Número de contacto", plain, "left"));
            document.add(addParagraph("Correo", plain, "left"));
            document.add(addParagraph(" ", plain, "left"));
            PdfPTable generalInfo = new PdfPTable(5);
            generalInfo.setWidthPercentage(100);
            generalInfo.setWidths(new float[]{70f, 45f, 50f, 45f, 50f});
            generalInfo.addCell(addCell("Fecha de Facturación", bold));
            generalInfo.addCell(addCell("Número de Factura", bold));
            generalInfo.addCell(addCell("Valor Total", bold));
            generalInfo.addCell(addCell("Fecha de pago", bold));
            generalInfo.addCell(addCell("Forma de pago", bold));
            generalInfo.addCell(addCell("DD/MM/AAAA", plain));
            generalInfo.addCell(addCell(String.valueOf(bill.getCode()), plain));
            generalInfo.addCell(addCell(String.valueOf(bill.getTotal()), plain));
            generalInfo.addCell(addCell("DD/MM/AAAA", plain));
            generalInfo.addCell(addCell("XXXX", plain));
            document.add(generalInfo);
            document.add(addParagraph(" ", plain, "left"));
            PdfPTable productsTable = new PdfPTable(4);
            productsTable.setWidthPercentage(100);
            productsTable.setWidths(new float[]{80f, 40f, 35f, 35f});
            productsTable.addCell(addCell("Producto", bold));
            productsTable.addCell(addCell("Cantidad", bold));
            productsTable.addCell(addCell("Precio Unidad", bold));
            productsTable.addCell(addCell("Precio Total", bold));
            for (int i = 0; i < bill.getProducts().size(); i++) {
                productsTable.addCell(addCell(bill.getProducts().get(i).getName() + "\n" + "Description", plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getQuantity()), plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice()) + "\n" + "IVA", plain));
                productsTable.addCell(addCell(String.valueOf(bill.getProducts().get(i).getPrice() * bill.getProducts().get(i).getQuantity()) + "\n" + "IVA", plain));
            }
            document.add(productsTable);
            document.add(addParagraph(" ", plain, "right"));
            document.add(addParagraph("Subtotal " + bill.getSubTotal(), plain, "right"));
            document.add(addParagraph("IVA " + (Bill.TAX * 100) + "%", plain, "right"));
            document.add(addParagraph("Descuento xxxx", plain, "right"));
            document.add(addParagraph("Total " + bill.getTotal(), plain, "right"));
            document.add(addParagraph(" ", plain, "right"));
            document.add(addParagraph("Firma de Recibo", plain, "left"));
            document.add(addParagraph("Fecha", plain, "left"));
            document.add(addParagraph("Notas", plain, "left"));
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
            case "left":
                paragraph.setAlignment(Element.ALIGN_LEFT);
                break;
            case "right":
                paragraph.setAlignment(Element.ALIGN_RIGHT);
        }
        return paragraph;
    }

}
