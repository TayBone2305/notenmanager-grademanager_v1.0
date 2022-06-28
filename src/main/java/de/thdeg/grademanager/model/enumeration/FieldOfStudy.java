package de.thdeg.grademanager.model.enumeration;

public enum FieldOfStudy
{
    B_ACS_EMBEDDED_SYSTEMS("Embedded Systems"),
    B_ACS_MOBILE_AND_SPATIAL_SYSTEMS("Mobile and Spatial Systems");

    private String fieldOfStudy;

    private FieldOfStudy(String fieldOfStudy)
    {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getFieldOfStudy()
    {
        return fieldOfStudy;
    }
}
