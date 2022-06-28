package de.thdeg.grademanager.model.enumeration;

public enum CourseType
{
    COMPULSORY("FWP"),
    ELECTIVE("AWP"),
    PLV("PLV"),
    REQUIRED("Required"),
    VOLUNTARY("Voluntary");

    private String courseType;

    private CourseType(String courseType)
    {
        this.courseType = courseType;
    }

    public String getCourseType()
    {
        return courseType;
    }

    public static CourseType getValue(String value)
    {
        for (CourseType m : CourseType.values())
        {
            if (m.courseType == value)
            {
                return m;
            }
        }

        return null; // no value found
    }
}
