import classes.*;
import enumeration.*;

import java.time.Duration;

/**
 * Defines the entry point of the Java application.
 */
public class Application
{
    private static JpaService jpaService = JpaService.getInstance();

    public static void main(String[] args)
    {
        try
        {
            jpaService.runInTransaction(entityManager -> {
                // name, semester, courseType, sws, ects, examType, isCredited
                //entityManager.persist(new Course("Numerische Methoden", Semester.SIXTH.getSemester(), CourseType.REQUIRED.getCourseType(), 4, 5, ExamType.WRITTEN.getExamType(), null));
                //entityManager.persist(new Course("Echtzeitsysteme", Semester.SIXTH.getSemester(), CourseType.REQUIRED.getCourseType(), 4, 5, ExamType.WRITTEN.getExamType(), null));
                //entityManager.persist(new Course("Digitale Signalverarbeitung", Semester.SIXTH.getSemester(), CourseType.REQUIRED.getCourseType(), 4, 5, ExamType.WRITTEN.getExamType(), null));
                //int id, String name, String degree, int duration, int fees, String fieldOfStudy
                CoursesOfStudy coursesOfStudy = new CoursesOfStudy("Angewandte Informatik", Degree.BACHELOR_OF_SCIENCE.getDegree(), 7, 62, FieldOfStudy.B_ACS_EMBEDDED_SYSTEMS.getFieldOfStudy());

                coursesOfStudy.addCourse(new Course("Systemprogrammierung", Semester.SIXTH.getSemester(), CourseType.REQUIRED.getCourseType(), 4, 5, ExamType.WRITTEN.getExamType(), null));

                entityManager.persist(coursesOfStudy);

                return null;
            });
        }
        finally
        {
            jpaService.closeResource();
        }
    }
}