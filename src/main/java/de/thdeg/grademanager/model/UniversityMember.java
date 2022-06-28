package de.thdeg.grademanager.model;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
@Table(name = "university_member")
public abstract class UniversityMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;


    @Column(name = "gender")
    private String gender;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "place_of_residence")
    private String placeOfResidence;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "official_email")
    private String officialEmail;

    @Column(name = "private_email")
    private String privateEmail;

    /* TODO: Not 100% sure if needed
    @Column(name = "Angemeldet")
    private Boolean isSignedUpForCourse;

     */

    // private List<Course> personalCourses = new ArrayList<>();
    protected UniversityMember()
    {

    }

    protected UniversityMember(String gender, String firstName, String lastName, String placeOfResidence, String birthPlace,
                            String officialEmail, String privateEmail)
    {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placeOfResidence = placeOfResidence;
        this.birthPlace = birthPlace;
        this.officialEmail = officialEmail;
        this.privateEmail = privateEmail;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityMember that = (UniversityMember) o;
        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPlaceOfResidence()
    {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence)
    {
        this.placeOfResidence = placeOfResidence;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    public String getOfficialEmail()
    {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail)
    {
        this.officialEmail = officialEmail;
    }

    public String getPrivateEmail()
    {
        return privateEmail;
    }

    public void setPrivateEmail(String privateEmail)
    {
        this.privateEmail = privateEmail;
    }

    /*
    public List<Course> getPersonalCourses() {
        return personalCourses;
    }

    public boolean signUpForCourse(Course course) {
        if (isSignedUpForCourse(course)) {
            return false;
        } else {
            return personalCourses.add(course);
        }

    }

    public boolean leaveCourse(Course course) {
        if (isSignedUpForCourse(course)) {
            return personalCourses.remove(course);
        } else {
            return false;
        }

    }

    public boolean isSignedUpForCourse(Course course){
        return personalCourses.contains(course);
    }

     */
}
