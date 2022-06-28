package de.thdeg.grademanager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "lecturer")
public class Lecturer extends UniversityMember
{
    @Column(name = "faculty")
    String faculty;

    public Lecturer()
    {

    }

    public Lecturer(String gender, String firstName, String lastName, String placeOfResidence, String birthPlace,
                    String officialEmail, String privateEmail, String faculty)
    {
        super(gender, firstName, lastName, placeOfResidence, birthPlace,
                officialEmail, privateEmail);
        this.faculty = faculty;
    }

    public String getFaculty()
    {
        return faculty;
    }

    public void setFaculty(String faculty)
    {
        this.faculty = faculty;
    }
}
