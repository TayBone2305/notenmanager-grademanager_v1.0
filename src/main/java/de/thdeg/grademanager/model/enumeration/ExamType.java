package de.thdeg.grademanager.model.enumeration;

public enum ExamType
{
    BACHELOR_THESIS("BA"),
    INTERNSHIP("Pr"),
    MASTER_THESIS("MA"),
    ORAL("mdlP"),
    PERFORMANCE_RECORD("LN"),
    PROOF_OF_PARTICIPATION("TN"),
    RESEARCH_PROJECT("PStA"),
    WRITTEN("schrP");

    private String examType;

    private ExamType(String examType)
    {
        this.examType = examType;
    }

    public String getExamType()
    {
        return examType;
    }
}
