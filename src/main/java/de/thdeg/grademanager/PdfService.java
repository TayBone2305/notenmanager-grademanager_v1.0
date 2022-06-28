package de.thdeg.grademanager;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import de.thdeg.grademanager.model.Enrollment;
import de.thdeg.grademanager.model.Student;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

/**
 * this class is used to generate pdf-files.
 * <p>
 * author: Kevin Thaller
 */
public class PdfService {

    /**
     * write the attributes of a student to a pdf-file.
     * <p>
     * author: Kevin Thaller
     *
     * @param path    the selected path where the pdf should be stored.
     * @param student the student whose attributes should be written to the pdf.
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public static void writePDF(String path, Student student) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        String fileName = "/Notenblatt_" + student.getFirstName() + "_" + student.getLastName() + "_" + LocalDate.now() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path + fileName));
        document.open();
        document.addTitle("Notenblatt " + student.getFirstName() + " " + student.getLastName());

        Paragraph headLine = new Paragraph("Notenblatt", FontFactory.getFont(FontFactory.COURIER, 30, BaseColor.BLACK));
        addImageToHeadLine(headLine);
        document.add(headLine);

        Paragraph dateParagraph = new Paragraph();

        Chunk date = new Chunk("Stand: "+LocalDate.now());
        dateParagraph.add(date);
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        Paragraph studentParagraph = new Paragraph();
        studentParagraph.setFont(FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK));

        addStudentDetails(studentParagraph, student);

        PdfPTable table = new PdfPTable(3);
        initPdfTable(table);
        fillEnrollmentTable(table, student);


        studentParagraph.setSpacingBefore(20);
        studentParagraph.setSpacingAfter(50);
        table.setSpacingBefore(50);
        table.setSpacingAfter(20);

        document.add(studentParagraph);
        document.add(table);
        document.close();
    }

    /**
     * load and add an image to the given paragraph.
     * <p>
     * author: Kevin Thaller
     *
     * @param headLine the paragraph where the image should be stored.
     */
    private static void addImageToHeadLine(Paragraph headLine) {
        try {
            Image image = Image.getInstance("src/main/resources/THD-Logo.png");
            image.setAlignment(Element.ALIGN_RIGHT);
            image.scaleAbsolute(200, 35);
            headLine.add(image);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * add different student attributes to a pdf-paragraph.
     * <p>
     * author: Kevin Thaller
     *
     * @param studentParagraph the paragraph that should store the student information.
     * @param student          the student whose attributes should be stored in the paragraph.
     */
    private static void addStudentDetails(Paragraph studentParagraph, Student student) {
        studentParagraph.add(new Paragraph("Name: " + student.getFirstName() + " " + student.getLastName()));
        studentParagraph.add(new Paragraph("Wohnort: " + student.getPlaceOfResidence()));
        studentParagraph.add(new Paragraph("Geburtsort: " + student.getBirthPlace()));
        studentParagraph.add(new Paragraph("Studiengang: " + student.getCoursesOfStudy().getName()));
        studentParagraph.add(new Paragraph("Semester: " + student.getSemester()));
        studentParagraph.add(new Paragraph("Matrikel-Nr.: " + student.getId()));
        studentParagraph.add(new Paragraph("Email: " + student.getOfficialEmail()));
    }

    /**
     * initialize the pdfptable with 3 different columns.
     * <p>
     * author: Kevin Thaller
     *
     * @param table the table which should be initialized.
     */
    private static void initPdfTable(PdfPTable table) {
        PdfPCell cell = new PdfPCell(new Phrase("Kurs"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Note"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Datum"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        table.setHeaderRows(1);
    }

    /**
     * fill the given PdfPTable table with the enrollments of the student.
     * <p>
     * author: Kevin Thaller
     *
     * @param table   the table which should store the enrollments
     * @param student the student whose enrollments should be stored in the table.
     */
    private static void fillEnrollmentTable(PdfPTable table, Student student) {
        for (Enrollment enrollment : student.getEnrollments()) {
            PdfPCell courseCell;
            PdfPCell gradeCell;
            PdfPCell dateCell;

            courseCell = new PdfPCell(new Phrase(enrollment.getCourse().getName()));

            if (enrollment.getGrade() != null) {
                gradeCell = new PdfPCell(new Phrase(enrollment.getGrade().toString()));
            } else {
                gradeCell = new PdfPCell(new Phrase("-"));
            }

            if (enrollment.getEnrollmentDate() != null) {
                dateCell = new PdfPCell(new Phrase(enrollment.getEnrollmentDate().toString()));
            } else {
                dateCell = new PdfPCell(new Phrase("-"));
            }
            courseCell.setFixedHeight(20);
            gradeCell.setFixedHeight(20);
            dateCell.setFixedHeight(20);

            courseCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            gradeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(courseCell);
            table.addCell(gradeCell);
            table.addCell(dateCell);
        }

    }

}
