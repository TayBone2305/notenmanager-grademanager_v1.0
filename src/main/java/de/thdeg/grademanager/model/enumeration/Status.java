package de.thdeg.grademanager.model.enumeration;

public enum Status
{
    ENROLLED("Enrolled"),
    WITHDRAWAL("Withdrawing"),
    DISMISSAL("Dismissed");

    private String status;

    private Status(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
}
