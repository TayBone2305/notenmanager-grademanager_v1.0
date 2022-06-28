package de.thdeg.grademanager.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * this class is used for displaying data in a javafx tableview
 * <p>
 * author: Kevin Thaller
 */
public class EnrollmentProperty {


    /**
     * the variable which stores the course name.
     */
    private SimpleStringProperty courseProp;

    /**
     * the variable which stores the grade value.
     */
    private SimpleStringProperty gradeProp;


    /**
     * assign the attributes of an enrollment-object to the private properties variables.
     * <p>
     * author: Kevin Thaller
     *
     * @param enrollment the object to assign to the properties variables.
     */
    public EnrollmentProperty(Enrollment enrollment) {
        courseProp = new SimpleStringProperty(enrollment.getCourse().getName());
        if (enrollment.getGrade() == null) {
            gradeProp = new SimpleStringProperty("-");
        } else {
            gradeProp = new SimpleStringProperty(enrollment.getGrade().toString());
        }
    }

    /**
     * this method is used for filling the tableview.
     * <p>
     * author: Kevin Thaller
     *
     * @return the value of courseProp.
     */
    public String getCourseProp() {
        return courseProp.get();
    }

    /**
     * this method is used for filling the tableview.
     * <p>
     * author: Kevin Thaller
     *
     * @return the value of gradeProp.
     */
    public String getGradeProp() {
        return gradeProp.get();
    }


}
