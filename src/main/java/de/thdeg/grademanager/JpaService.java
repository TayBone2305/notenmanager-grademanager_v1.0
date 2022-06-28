package de.thdeg.grademanager;

import de.thdeg.grademanager.model.Course;
import de.thdeg.grademanager.model.CoursesOfStudy;
import de.thdeg.grademanager.model.Student;
import jakarta.persistence.*;

import java.util.List;
import java.util.function.Function;

/**
 * A singleton implementation that encapsulates the JPA logic.
 * You can configure the database connection in the
 * {@link /src/main/resources/META-INF/persistence.xml} file.
 */
public class JpaService {
    private static JpaService instance;
    private EntityManagerFactory entityManagerFactory;

    private JpaService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-notenmanager");
    }

    public static synchronized JpaService getInstance() {
        return instance == null ? instance = new JpaService() : instance;
    }

    public void closeResource() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * Runs the specified function inside a transaction boundary. The function has
     * access to a ready to use {@link EntityManager} and can return any type of
     * value ({@code T}).
     *
     * @param function The function to run.
     * @param <T>      The function's return type.
     * @return the value returned by the specified function.
     */
    public <T> T runInTransaction(Function<EntityManager, T> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        boolean isPersisted = false;

        entityTransaction.begin();

        try {
            T returnValue = function.apply(entityManager);
            isPersisted = true;

            return returnValue;
        } finally {
            if (isPersisted) {
                entityTransaction.commit();
            } else {
                entityTransaction.rollback();
            }
        }
    }

    /**
     * get all students from the database.
     * <p>
     * author: Kevin Thaller
     *
     * @return a list of found students or an empty List if there are no students.
     */
    public List<Student> getStudentsFromDb() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT q FROM Student q", Student.class);
        return query.getResultList();
    }

    /**
     * get all courses of study from the database.
     * <p>
     * author: Kevin Thaller
     *
     * @return a list of found courses of study or an empty list if there are no courses of study.
     */
    public List<CoursesOfStudy> getCoursesOfStudyFromDb() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT q FROM CoursesOfStudy q", CoursesOfStudy.class);
        return query.getResultList();
    }

    /**
     * get the courses linked to a courses of study from the database.
     * <p>
     * author: Kevin Thaller
     *
     * @param coursesOfStudy the object whose courses should be found.
     * @return a list of courses linked to a courses of study object or an empty list if there are no courses.
     */
    public List<Course> getCoursesForCoursesOfStudyFromDb(CoursesOfStudy coursesOfStudy) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT q FROM Course q WHERE q.coursesOfStudy=:coursesOfStudy");
        query.setParameter("coursesOfStudy", coursesOfStudy);
        return query.getResultList();
    }

    /**
     * get the student for a given id from the database.
     * <p>
     * author: Kevin Thaller
     *
     * @param id the variable which identifies the student.
     * @return a student for the given id  or null if the id isn't linked to an object.
     */
    public Student getStudentFromDb(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT p FROM Student p WHERE p.id=:id");
        query.setParameter("id", id);
        return (Student) query.getSingleResult();
    }

    /**
     * get the course for a given id from the database.
     * <p>
     * author: Kevin Thaller
     *
     * @param id the variable which identifies the course.
     * @return a course for the given id or null if the id isn't linked to an object.
     */
    public Course getCourseFromDb(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT p FROM Course p WHERE p.id=:id");
        query.setParameter("id", id);
        return (Course) query.getSingleResult();
    }

    /**
     * get the courses of study for a given id from the database.
     * <p>
     * author: Kevin Thaller
     *
     * @param id the variable which identifies the courses of study.
     * @return a courses-of-study for the given id or null if the id isn't linked to an object.
     */
    public CoursesOfStudy getCoursesOfStudyFromDb(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT p FROM CoursesOfStudy p WHERE p.id=:id");
        query.setParameter("id", id);
        return (CoursesOfStudy) query.getSingleResult();
    }
}
