package de.thdeg.grademanager.model.enumeration;

public enum Degree
{
    BACHELOR_OF_ARTS("B.A."),
    BACHELOR_OF_ENGINEERING("B.Eng."),
    BACHELOR_OF_SCIENCE("B.Sc."),
    MASTER_OF_ARTS("M.A."),
    MASTER_OF_ENGINEERING("M.Eng."),
    MASTER_OF_SCIENCE("M.Sc.");

    private String degree;

    private Degree(String degree)
    {
        this.degree = degree;
    }

    public String getDegree()
    {
        return degree;
    }
}
