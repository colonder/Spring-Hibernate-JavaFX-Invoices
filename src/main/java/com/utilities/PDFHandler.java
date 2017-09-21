package com.utilities;

public class PDFHandler
{
    // TODO: implement later

    /*private GridPane root;

    public PDFHandler(Customer customer, LocalDate date)
    {
        root = (GridPane) new InvoiceTemplateView().getView();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void printPDF()
    {
        // FIXME: not showing printer dialog
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null)
            return;
        boolean proceed = job.showPrintDialog(null);
        if (proceed)
        {
            // resizing root node if needed
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double scaleX = pageLayout.getPrintableWidth() / root.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / root.getBoundsInParent().getHeight();
            double scale = (scaleX <= scaleY) ? scaleX : scaleY; // select number that's smallest
            root.getTransforms().add(new Scale(scale, scale));
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
        PDDocument doc = new PDDocument();

        if (file != null)
        {
            try
            {
                PDPage page = new PDPage();
                doc.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page,
                        PDPageContentStream.AppendMode.APPEND, true, true);
                PDImageXObject imageXObject = nodeToPDFImage(root, doc);
                contentStream.drawImage(imageXObject, 20, 20);
                contentStream.close();
                doc.save(file.getPath());
            }

            catch (IOException e) {
                showAlert("Błąd zapisu pliku", "Nie można było wykonać zapisu do pliku");
            }
        }

        try
        {
            doc.close();
        }

        catch
        (IOException e) {
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

    private PDImageXObject nodeToPDFImage(Node node, PDDocument doc)
    {
        WritableImage snapshot = node.snapshot(new SnapshotParameters(), null);
        BufferedImage buffImg = SwingFXUtils.fromFXImage(snapshot, null);
        PDImageXObject obj = null;
        try
        {
            obj = LosslessFactory.createFromImage(doc, buffImg);
        }

        catch (IOException e) {
            showAlert("Błąd konwersji faktury do PDF", "Nie można było poprawnie wykonać konwersji");
        }

        return obj;
    }*/
}
