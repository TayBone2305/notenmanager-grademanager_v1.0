package de.thdeg.grademanager.model.enumeration;

public enum Gender
{
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private String gender;

    private Gender(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }
}
