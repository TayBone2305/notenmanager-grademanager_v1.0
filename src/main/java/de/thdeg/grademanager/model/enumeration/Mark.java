package de.thdeg.grademanager.model.enumeration;

public enum Mark {
    EINS_NULL(1.0),
    EINS_DREI(1.3),
    EINS_SIEBEN(1.7),
    ZWEI_NULL(2.0),
    ZWEI_DREI(2.3),
    ZWEI_SIEBEN(2.7),
    DREI_NULL(3.0),
    DREI_DREI(3.3),
    DREI_SIEBEN(3.7),
    VIER_NULL(4.0),
    FUENF_NULL(5.0),
    SECHS_NULL(6.0),
    SIEBEN_NULL(7.0);

    private double mark;

    public static Mark getValue(double value)
    {
        for (Mark m : Mark.values())
        {
            if (m.mark == value)
            {
                return m;
            }
        }
        return null; // not found
    }

    Mark(double mark) {
        this.mark = mark;
    }

    public double getMark() {
        return mark;
    }



}
