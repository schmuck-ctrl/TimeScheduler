/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import handlers.DatabaseHandler;
import handlers.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author nilss
 */
public class PDF {

    private JFileChooser saveDialog = null;
    private Operator author = null;

    public PDF(Operator author) {
        this.saveDialog = new JFileChooser();

        setAuthor(author);
    }

    public void setAuthor(Operator user) {
        if (user != null) {
            this.author = user;
        }
    }

    public void showDialogAndSavePDF(PDDocument pdf) {
        FileFilter filter = new FileNameExtensionFilter("PDF", "pdf");

        saveDialog.setDialogType(JFileChooser.SAVE_DIALOG);
        saveDialog.setDialogTitle("Save weekly report as PDF");
        saveDialog.removeChoosableFileFilter(saveDialog.getAcceptAllFileFilter());
        saveDialog.setFileFilter(filter);

        int retVal = saveDialog.showSaveDialog(forms.FrmMain.getInstance());

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String path = this.saveDialog.getSelectedFile().toString();

            if (filter.accept(new File(path.trim()))) {
                try {
                    pdf.save(path);
                } catch (IOException ex) {
                    Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.saveDialog.setVisible(false);
        }
    }

    public PDDocument createWeeklySchedule() {

        try {
            //Creating PDF document object
            PDDocument document = new PDDocument();

            //Creating the PDDocumentInformation object
            PDDocumentInformation pdd = document.getDocumentInformation();

            //Setting the author of the document
            pdd.setAuthor("Tutorialspoint");

            // Setting the title of the document
            pdd.setTitle("Sample document");

            //Setting the creator of the document
            pdd.setCreator("PDF Examples");

            //Setting the subject of the document
            pdd.setSubject("Example document");

            //Setting the created date of the document
            pdd.setCreationDate(java.util.Calendar.getInstance());
                      
            //Creating a blank page
            PDPage page = new PDPage();

            //Adding the blank page to the document
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 750);

            EventHandler eHandler = new EventHandler();
            ArrayList<Event> eventsOfWeek = eHandler.getEventsOfWeek(author.getUserId());
            //Adding text in the form of string 
            contentStream.showText("text");

            //Ending the content stream
            contentStream.endText();

            //Closing the content stream
            contentStream.close();

            return document;

        } catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
