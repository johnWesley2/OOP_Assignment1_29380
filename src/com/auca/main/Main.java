package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    private static final Scanner input = new Scanner(System.in);

    // Reads one field and repeats only that field when its value is invalid.
    private static <T> void readField(String label, Function<String, T> converter, Consumer<T> setter) {
        while (true) {
            System.out.print(label + ": ");
            String raw = input.nextLine().trim();
            try {
                setter.accept(converter.apply(raw));
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid " + label + ": " + e.getMessage() + ". Try again.");
            }
        }
    }

    private static String string(String value) { return value; }
    private static Integer integer(String value) {
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Enter a whole number"); }
    }
    private static Double decimal(String value) {
        try {
            double n = Double.parseDouble(value);
            if (!Double.isFinite(n)) throw new NumberFormatException();
            return n;
        } catch (NumberFormatException e) { throw new IllegalArgumentException("Enter a valid number"); }
    }
    private static BigDecimal money(String value) {
        try { return new BigDecimal(value); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Enter a valid amount"); }
    }
    private static LocalDate date(String value) {
        try { return LocalDate.parse(value); }
        catch (DateTimeParseException e) { throw new IllegalArgumentException("Use YYYY-MM-DD"); }
    }
    private static Boolean bool(String value) {
        if (value.equalsIgnoreCase("true")) return true;
        if (value.equalsIgnoreCase("false")) return false;
        throw new IllegalArgumentException("Enter true or false");
    }

    private static void heading(String title) {
        System.out.println("\n============================================================");
        System.out.println("                 " + title);
        System.out.println("============================================================");
    }

    private static <T> T choose(String label, List<T> list, Function<T, String> description) {
        if (list.isEmpty()) {
            System.out.println("No " + label + " registered. Register one first.");
            return null;
        }
        System.out.println("\nChoose " + label + ":");
        for (int i = 0; i < list.size(); i++)
            System.out.println((i + 1) + ". " + description.apply(list.get(i)));
        final int[] selected = {-1};
        readField("Enter number (0 to cancel)", Main::integer, n -> {
            if (n == 0) selected[0] = 0;
            else if (n < 1 || n > list.size()) throw new IllegalArgumentException("Choose 0 to " + list.size());
            else selected[0] = n;
        });
        return selected[0] == 0 ? null : list.get(selected[0] - 1);
    }

    private static boolean idExists(List<Registrable> records, String id) {
        for (Registrable r : records)
            if (r instanceof Identifiable && ((Identifiable) r).getId().equalsIgnoreCase(id)) return true;
        return false;
    }

    private static void display(List<Registrable> records) {
        heading("ALL REGISTERED RECORDS");
        if (records.isEmpty()) { System.out.println("No records registered yet."); return; }
        for (Registrable r : records) {
            try {
                r.validateRegistration();
                System.out.println("\n------------------------------------------------------------");
                System.out.println(r);
            } catch (ValidationException e) {
                System.out.println("Incomplete record: " + r.getRegistrationName() + " - " + e.getMessage());
            }
        }
    }

    private static void registerDepartment(List<Department> departments, List<Registrable> records) {
        heading("REGISTER DEPARTMENT");
        Department d = new Department();
        readField("Department ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); d.setDepartmentId(v); });
        readField("Department name", Main::string, d::setDepartmentName);
        readField("Department code", Main::string, d::setDepartmentCode);
        readField("Office location", Main::string, d::setOfficeLocation);
        readField("Contact email", Main::string, d::setContactEmail);
        departments.add(d); records.add(d);
        System.out.println("Department registered successfully.");
    }

    private static void registerStudent(List<Department> departments, List<Student> students, List<Registrable> records) {
        heading("REGISTER STUDENT");
        Department d = choose("department", departments, Department::getDepartmentName);
        if (d == null) return;
        Student s = new Student();
        readField("Person ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); s.setPersonId(v); });
        readField("First name", Main::string, s::setFirstName);
        readField("Last name", Main::string, s::setLastName);
        readField("Personal email", Main::string, s::setEmail);
        readField("Phone number", Main::string, s::setPhoneNumber);
        readField("Date of birth (YYYY-MM-DD)", Main::date, s::setDateOfBirth);
        readField("Member number", Main::string, s::setMemberNumber);
        s.setDepartment(d);
        readField("Join date (YYYY-MM-DD)", Main::date, s::setJoinDate);
        readField("Academic status", Main::string, s::setAcademicStatus);
        readField("Institutional email", Main::string, s::setInstitutionalEmail);
        readField("Advisor name", Main::string, s::setAdvisorName);
        readField("Student number", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); s.setStudentNumber(v); });
        readField("Program name", Main::string, s::setProgramName);
        readField("Admission date (YYYY-MM-DD)", Main::date, s::setAdmissionDate);
        readField("Study year (1-6)", Main::integer, s::setStudyYear);
        readField("Guardian phone", Main::string, s::setGuardianPhone);
        readField("Scholarship status (true/false)", Main::bool, s::setScholarshipStatus);
        students.add(s); records.add(s);
        System.out.println("Student registered successfully.");
    }

    private static void registerTeacher(List<Department> departments, List<Teacher> teachers, List<Registrable> records) {
        heading("REGISTER TEACHER");
        Department d = choose("department", departments, Department::getDepartmentName);
        if (d == null) return;
        Teacher t = new Teacher();
        readField("Person ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); t.setPersonId(v); });
        readField("First name", Main::string, t::setFirstName);
        readField("Last name", Main::string, t::setLastName);
        readField("Personal email", Main::string, t::setEmail);
        readField("Phone number", Main::string, t::setPhoneNumber);
        readField("Date of birth (YYYY-MM-DD)", Main::date, t::setDateOfBirth);
        readField("Member number", Main::string, t::setMemberNumber);
        t.setDepartment(d);
        readField("Join date (YYYY-MM-DD)", Main::date, t::setJoinDate);
        readField("Academic status", Main::string, t::setAcademicStatus);
        readField("Institutional email", Main::string, t::setInstitutionalEmail);
        readField("Advisor name", Main::string, t::setAdvisorName);
        readField("Employee number", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); t.setEmployeeNumber(v); });
        readField("Specialization", Main::string, t::setSpecialization);
        readField("Hire date (YYYY-MM-DD)", Main::date, t::setHireDate);
        readField("Salary (RWF)", Main::money, t::setSalary);
        readField("Office number", Main::string, t::setOfficeNumber);
        readField("Employment type", Main::string, t::setEmploymentType);
        if (d.getHeadTeacher() == null) d.setHeadTeacher(t);
        teachers.add(t); records.add(t);
        System.out.println("Teacher registered successfully.");
    }

    private static void registerAdministrator(List<Registrable> records) {
        heading("REGISTER ADMINISTRATOR");
        Administrator a = new Administrator();
        readField("Person ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); a.setPersonId(v); });
        readField("First name", Main::string, a::setFirstName);
        readField("Last name", Main::string, a::setLastName);
        readField("Personal email", Main::string, a::setEmail);
        readField("Phone number", Main::string, a::setPhoneNumber);
        readField("Date of birth (YYYY-MM-DD)", Main::date, a::setDateOfBirth);
        readField("Staff number", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); a.setStaffNumber(v); });
        readField("Job title", Main::string, a::setJobTitle);
        readField("Assigned office", Main::string, a::setAssignedOffice);
        readField("Employment date (YYYY-MM-DD)", Main::date, a::setEmploymentDate);
        readField("Access level (1-5)", Main::integer, a::setAccessLevel);
        readField("Active (true/false)", Main::bool, a::setActive);
        records.add(a);
        System.out.println("Administrator registered successfully.");
    }

    private static void registerCourse(List<Department> departments, List<Teacher> teachers, List<Course> courses, List<Registrable> records) {
        heading("REGISTER COURSE");
        Department d = choose("department", departments, Department::getDepartmentName);
        if (d == null) return;
        List<Teacher> sameDepartment = new ArrayList<>();
        for (Teacher t : teachers) if (t.getDepartment() == d) sameDepartment.add(t);
        Teacher t = choose("teacher in this department", sameDepartment,
                x -> x.getFirstName() + " " + x.getLastName() + " - " + x.getEmployeeNumber());
        if (t == null) return;
        Course c = new Course();
        readField("Course ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); c.setCourseId(v); });
        readField("Course code", Main::string, c::setCourseCode);
        readField("Course title", Main::string, c::setCourseTitle);
        readField("Credit hours", Main::integer, c::setCreditHours);
        c.setDepartment(d); c.setAssignedTeacher(t);
        courses.add(c); records.add(c);
        System.out.println("Course registered successfully.");
    }

    private static void registerClassroom(List<Registrable> records) {
        heading("REGISTER CLASSROOM");
        Classroom c = new Classroom();
        readField("Classroom ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); c.setClassroomId(v); });
        readField("Building name", Main::string, c::setBuildingName);
        readField("Room number", Main::string, c::setRoomNumber);
        readField("Capacity", Main::integer, c::setCapacity);
        readField("Floor number", Main::integer, c::setFloorNumber);
        readField("Available (true/false)", Main::bool, c::setAvailable);
        records.add(c); System.out.println("Classroom registered successfully.");
    }

    private static void registerEnrollment(List<Student> students, List<Course> courses, List<Enrollment> enrollments, List<Registrable> records) {
        heading("ENROLL STUDENT");
        Student s = choose("student", students, x -> x.getFirstName() + " " + x.getLastName() + " (" + x.getStudentNumber() + ")");
        if (s == null) return;
        Course c = choose("course", courses, Course::getCourseTitle);
        if (c == null) return;
        Enrollment e = new Enrollment();
        readField("Enrollment ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); e.setEnrollmentId(v); });
        e.setStudent(s); e.setCourse(c);
        readField("Enrollment date (YYYY-MM-DD)", Main::date, e::setEnrollmentDate);
        readField("Semester", Main::string, e::setSemester);
        readField("Enrollment status", Main::string, e::setEnrollmentStatus);
        enrollments.add(e); // Important: save it for the Assessment menu.
        records.add(e);
        System.out.println("Student enrolled successfully.");
    }

    private static void registerAssessment(List<Enrollment> enrollments, List<Registrable> records) {
        heading("RECORD ASSESSMENT");
        Enrollment e = choose("enrollment", enrollments, Enrollment::getEnrollmentId);
        if (e == null) return;
        Assessment a = new Assessment();
        readField("Assessment ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); a.setAssessmentId(v); });
        a.setEnrollment(e);
        readField("Assessment type", Main::string, a::setAssessmentType);
        readField("Maximum score", Main::decimal, v -> { if (v <= 0) throw new IllegalArgumentException("Must be greater than zero"); a.setMaximumScore(v); });
        readField("Obtained score", Main::decimal, v -> { if (v < 0 || v > a.getMaximumScore()) throw new IllegalArgumentException("Must be between 0 and " + a.getMaximumScore()); a.setObtainedScore(v); });
        readField("Assessment date (YYYY-MM-DD)", Main::date, a::setAssessmentDate);
        records.add(a); System.out.println("Assessment recorded successfully.");
    }

    private static void registerPayment(List<Student> students, List<Registrable> records) {
        heading("RECORD FEE PAYMENT");
        Student s = choose("student", students, x -> x.getFirstName() + " " + x.getLastName() + " (" + x.getStudentNumber() + ")");
        if (s == null) return;
        FeePayment p = new FeePayment();
        readField("Payment ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); p.setPaymentId(v); });
        p.setStudent(s);
        readField("Amount paid (RWF)", Main::money, v -> { if (v.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive"); p.setAmount(v); });
        readField("Payment date (YYYY-MM-DD)", Main::date, p::setPaymentDate);
        readField("Payment method", Main::string, p::setPaymentMethod);
        readField("Receipt number", Main::string, v -> {
            for (Registrable r : records) if (r instanceof FeePayment && ((FeePayment) r).getReceiptNumber().equalsIgnoreCase(v)) throw new IllegalArgumentException("Receipt number already exists");
            p.setReceiptNumber(v);
        });
        records.add(p); System.out.println("Fee payment recorded successfully.");
    }

    private static void registerBook(List<LibraryBook> books, List<Registrable> records) {
        heading("REGISTER LIBRARY BOOK");
        LibraryBook b = new LibraryBook();
        readField("Book ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); b.setBookId(v); });
        readField("ISBN", Main::string, b::setIsbn);
        readField("Book title", Main::string, b::setTitle);
        readField("Author", Main::string, b::setAuthor);
        readField("Total copies", Main::integer, v -> { if (v <= 0) throw new IllegalArgumentException("Must be greater than zero"); b.setTotalCopies(v); });
        readField("Available copies", Main::integer, v -> { if (v < 0 || v > b.getTotalCopies()) throw new IllegalArgumentException("Must be 0 to " + b.getTotalCopies()); b.setAvailableCopies(v); });
        books.add(b); records.add(b); System.out.println("Library book registered successfully.");
    }

    private static void borrowBook(List<Student> students, List<LibraryBook> books, List<BookLoan> loans, List<Registrable> records) {
        heading("BORROW LIBRARY BOOK");
        Student s = choose("student", students, x -> x.getFirstName() + " " + x.getLastName() + " (" + x.getStudentNumber() + ")");
        if (s == null) return;
        LibraryBook b = choose("library book", books, x -> x.getTitle() + " - available: " + x.getAvailableCopies());
        if (b == null) return;
        if (b.getAvailableCopies() < 1) { System.out.println("No copy is currently available."); return; }
        BookLoan loan = new BookLoan();
        readField("Loan ID", Main::string, v -> { if (idExists(records, v)) throw new IllegalArgumentException("ID already exists"); loan.setLoanId(v); });
        loan.setStudent(s); loan.setBook(b);
        readField("Borrowed date (YYYY-MM-DD)", Main::date, loan::setBorrowedDate);
        readField("Due date (YYYY-MM-DD)", Main::date, v -> { if (v.isBefore(loan.getBorrowedDate())) throw new IllegalArgumentException("Due date cannot be before borrowed date"); loan.setDueDate(v); });
        b.setAvailableCopies(b.getAvailableCopies() - 1);
        loans.add(loan); records.add(loan);
        System.out.println("Book borrowed successfully. Available copies: " + b.getAvailableCopies());
    }

    private static void returnBook(List<BookLoan> loans) {
        heading("RETURN LIBRARY BOOK");
        List<BookLoan> unreturned = new ArrayList<>();
        for (BookLoan loan : loans) if (loan.getReturnedDate() == null) unreturned.add(loan);
        BookLoan loan = choose("active book loan", unreturned,
                x -> x.getLoanId() + " | " + x.getBook().getTitle() + " | " + x.getStudent().getFirstName() + " " + x.getStudent().getLastName());
        if (loan == null) return;
        LocalDate returned = readDate("Return date (YYYY-MM-DD)");
        while (returned.isBefore(loan.getBorrowedDate())) {
            System.out.println("Return date cannot be before the borrowed date.");
            returned = readDate("Return date (YYYY-MM-DD)");
        }
        loan.setReturnedDate(returned);
        loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);
        System.out.println("Book returned successfully. Available copies: " + loan.getBook().getAvailableCopies());
    }

    private static LocalDate readDate(String label) {
        final LocalDate[] result = new LocalDate[1];
        readField(label, Main::date, v -> result[0] = v);
        return result[0];
    }

    private static void searchById(List<Registrable> records) {
        heading("SEARCH RECORD BY ID");
        System.out.print("Enter record ID: ");
        String id = input.nextLine().trim();
        for (Registrable r : records) {
            if (r instanceof Identifiable && ((Identifiable) r).getId().equalsIgnoreCase(id)) {
                System.out.println("\nRecord found:\n" + r); return;
            }
        }
        System.out.println("No record found with ID: " + id);
    }

    private static void updateRecord(List<Registrable> records) {
        heading("UPDATE EXISTING RECORD");
        System.out.print("Enter the record ID to update: ");
        String id = input.nextLine().trim();
        for (Registrable r : records) {
            if (!(r instanceof Identifiable) || !((Identifiable) r).getId().equalsIgnoreCase(id)) continue;
            System.out.println("Selected record:\n" + r);
            System.out.println("1. Update email (Student/Teacher/Administrator)\n2. Update phone (Student/Teacher/Administrator)\n3. Cancel");
            int option = readMenuNumber("Select: ", 1, 3);
            if (option == 1 && r instanceof Person) readField("New email", Main::string, ((Person) r)::setEmail);
            else if (option == 2 && r instanceof Person) readField("New phone", Main::string, ((Person) r)::setPhoneNumber);
            else if (option == 3) { System.out.println("Update cancelled."); return; }
            else { System.out.println("This update option is not applicable to the selected record."); return; }
            System.out.println("Record updated successfully:\n" + r); return;
        }
        System.out.println("No record found with that ID.");
    }

    private static int readMenuNumber(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = Integer.parseInt(input.nextLine().trim());
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a number from " + min + " to " + max + ".");
        }
    }

    private static void academicSummary(List<Student> students, List<Registrable> records) {
        heading("STUDENT ACADEMIC SUMMARY");
        Student s = choose("student", students, x -> x.getFirstName() + " " + x.getLastName() + " (" + x.getStudentNumber() + ")");
        if (s == null) return;
        System.out.println("Student: " + s.getFirstName() + " " + s.getLastName());
        System.out.println("Student number: " + s.getStudentNumber());
        System.out.println("Program: " + s.getProgramName());
        System.out.println("Department: " + s.getDepartment().getDepartmentName());
        int count = 0; double total = 0;
        for (Registrable r : records) if (r instanceof Assessment) {
            Assessment a = (Assessment) r;
            if (a.getEnrollment().getStudent() == s) {
                System.out.println("Assessment: " + a.getAssessmentType() + " | Score: " + a.getObtainedScore() + "/" + a.getMaximumScore());
                total += a.getObtainedScore(); count++;
            }
        }
        System.out.println("Assessments recorded: " + count);
        if (count > 0) System.out.printf("Average obtained score: %.2f%n", total / count);
        else System.out.println("No assessments recorded for this student.");
    }

    private static void paymentHistory(List<Student> students, List<Registrable> records) {
        heading("STUDENT PAYMENT HISTORY");
        Student s = choose("student", students, x -> x.getFirstName() + " " + x.getLastName() + " (" + x.getStudentNumber() + ")");
        if (s == null) return;
        BigDecimal total = BigDecimal.ZERO; int count = 0;
        for (Registrable r : records) if (r instanceof FeePayment) {
            FeePayment p = (FeePayment) r;
            if (p.getStudent() == s) {
                System.out.println(p);
                total = total.add(p.getAmount()); count++;
            }
        }
        System.out.println("Payments made: " + count);
        System.out.println("Total paid: RWF " + total);
    }

    private static void availableBooks(List<LibraryBook> books) {
        heading("AVAILABLE LIBRARY BOOKS");
        boolean found = false;
        for (LibraryBook b : books) if (b.getAvailableCopies() > 0) {
            System.out.println(b.getTitle() + " | Author: " + b.getAuthor() + " | Copies available: " + b.getAvailableCopies());
            found = true;
        }
        if (!found) System.out.println("No books are currently available.");
    }

    private static void demonstratePolymorphism(List<Student> students, List<Teacher> teachers,
                                                  List<Registrable> records) {
        heading("POLYMORPHISM DEMONSTRATION");
        List<Person> people = new ArrayList<>();
        people.addAll(students); 
        people.addAll(teachers);
        for (Registrable r : records) if (r instanceof Administrator) people.add((Administrator) r);
        System.out.println("Person list: the same Person reference can hold different child objects.");
        if (people.isEmpty()) System.out.println("Register a Student, Teacher or Administrator first.");
        for (Person p : people)
            System.out.println("\nObject type: " + p.getClass().getSimpleName() + "\n" + p);

        System.out.println("\nIdentifiable list: unrelated record classes share the interface.");
        List<Identifiable> identifiableRecords = new ArrayList<>();
        for (Registrable r : records) if (r instanceof Identifiable) identifiableRecords.add((Identifiable) r);
        for (Identifiable item : identifiableRecords)
            System.out.println(item.getClass().getSimpleName() + " | ID: " + item.getId() + " | Status: " + item.getStatus());
    }

    private static void menu() {
        List<Department> departments = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        List<Enrollment> enrollments = new ArrayList<>();
        List<LibraryBook> books = new ArrayList<>();
        List<BookLoan> loans = new ArrayList<>();
        List<Registrable> records = new ArrayList<>();

        boolean running = true;
        while (running) {
            System.out.println("\n============== AUCA_ID SCHOOL MANAGEMENT ==============");
            System.out.println("1. Register Department       11. Borrow Library Book");
            System.out.println("2. Register Student          12. Return Library Book");
            System.out.println("3. Register Teacher          13. View All Records");
            System.out.println("4. Register Administrator    14. Search Record by ID");
            System.out.println("5. Register Course           15. Update Existing Record");
            System.out.println("6. Register Classroom        16. Student Academic Summary");
            System.out.println("7. Enroll Student            17. Student Payment History");
            System.out.println("8. Record Assessment         18. View Available Books");
            System.out.println("9. Record Fee Payment        19. Demonstrate Polymorphism");
            System.out.println("10. Register Library Book     0. Exit");

            int choice;
            try {
                System.out.print("Select an option: ");
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number from 0 to 19.");
                continue;
            }

            try {
                switch (choice) {
                    case 1: registerDepartment(departments, records); break;
                    case 2: registerStudent(departments, students, records); break;
                    case 3: registerTeacher(departments, teachers, records); break;
                    case 4: registerAdministrator(records); break;
                    case 5: registerCourse(departments, teachers, courses, records); break;
                    case 6: registerClassroom(records); break;
                    case 7: registerEnrollment(students, courses, enrollments, records); break;
                    case 8: registerAssessment(enrollments, records); break;
                    case 9: registerPayment(students, records); break;
                    case 10: registerBook(books, records); break;
                    case 11: borrowBook(students, books, loans, records); break;
                    case 12: returnBook(loans); break;
                    case 13: display(records); break;
                    case 14: searchById(records); break;
                    case 15: updateRecord(records); break;
                    case 16: academicSummary(students, records); break;
                    case 17: paymentHistory(students, records); break;
                    case 18: availableBooks(books); break;
                    case 19: demonstratePolymorphism(students, teachers, records); break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using AUCA_ID School Management System.");
                        break;
                    default: System.out.println("Invalid option. Choose a number from 0 to 19.");
                }
            } catch (Exception e) {
                System.out.println("Operation could not be completed: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        menu();
        input.close();
    }
}
