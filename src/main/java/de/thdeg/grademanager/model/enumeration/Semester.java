package de.thdeg.grademanager.model.enumeration;

public enum Semester
{
    FIRST(1),
    SECOND(2),
    THIRD(3),       // Regelstudienzeit Master
    FOURTH(4),
    FIFTH(5),       // Erstmaliges Ablegen der Masterarbeit; nur ein weiterer Versuch erlaubt
    SIXTH(6),       // max. Anzahl an Master-Semester; bis hier: ohne Corona
    SEVENTH(7),     // Regelstudienzeit Bachelor // M: WS 2021/22
    EIGHTH(8),      // M: SS 2021
    NINTH(9),       // Erstmaliges Ablegen der Bachelorarbeit; nur ein weiterer Versuch erlaubt // M: WS 2020/21
    TENTH(10),      // max. Anzahl an Bachelor-Semester; bis hier: ohne Corona // M: vor und zum SS 2020
    ELEVENTH(11),   // B: WS 2021/22
    TWELFTH(12),    // B: SS 2021
    THIRTEENTH(13), // B: WS 2020/21
    FOURTEENTH(14); // B: vor und zum SS 2020

    private final int semester;

    private Semester(final int semester)
    {
        this.semester = semester;
    }

    public int getSemester()
    {
        return semester;
    }
}
