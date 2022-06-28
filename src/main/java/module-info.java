module de.thdeg.grademanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires itextpdf;
    //requires charm.glisten;


    opens de.thdeg.grademanager.model to org.hibernate.orm.core, javafx.fxml;


    opens de.thdeg.grademanager.gui to javafx.fxml;

    exports de.thdeg.grademanager.gui;
    exports de.thdeg.grademanager.model;

}