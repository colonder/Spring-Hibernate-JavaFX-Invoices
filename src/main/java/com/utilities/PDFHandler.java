package com.utilities;

import com.entity.Customer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class PDFHandler
{
    private PDDocument doc;
    private VBox root = new VBox(5);

    public PDFHandler(Customer customer)
    {
        // TODO: create custom pdf

        try {
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(customer.getLastNameProp());
            contents.endText();
            contents.close();
        }

        catch(NullPointerException e)
        {
            showAlert("Nie wybrany jest obecnie żaden kontrahent", "Należy najpierw wybrać kontrahenta");
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void printPDF()
    {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null)
            return;
        boolean proceed = job.showPrintDialog(new Stage());
        if (proceed)
        {
            boolean printed = job.printPage(root);
            if (printed)
                job.endJob();
        }
    }

    public void savePDF()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz fakturę");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                doc.save(file.getPath());
            } catch (IOException e) {
                showAlert("Błąd zapisu pliku", "Nie można było wykonać zapisu do pliku");
            }
        }

        try {
            doc.close();
        } catch (IOException e) {
            showAlert("Błąd programu", "Nie można było poprawnie zamknąć pliku PDF");
        }
    }

    private void showAlert(String headerText, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        alert.show();
    }
}
