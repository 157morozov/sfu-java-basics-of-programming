package pr2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


/**
 * Учебная группа, к которой принадлежит студент.
 */
class StudyGroup {

  private String name;
  private int course;

  /**
   * Создаёт учебную группу с указанным названием и номером курса.
   */
  public StudyGroup(String name, int course) {
    this.name = name;
    this.course = course;
  }

  @Override
  public String toString() {
    return getName() + " (" + course + " курс)";
  }

  /**
   * Возвращает название группы.
   */
  public String getName() {
    return name != null ? name : "";
  }

  /**
   * Возвращает номер курса.
   */
  public int getCourse() {
    return course;
  }
}

/**
 * Пол студента.
 */
enum Gender {
  MALE("Мужской"),
  FEMALE("Женский");

  private final String label;

  Gender(String label) {
    this.label = label;
  }

  /**
   * Возвращает читаемое обозначение пола.
   */
  public String getLabel() {
    return label;
  }
}

/**
 * Студент университета с личными и академическими атрибутами.
 */
class Student {

  // Текстовые поля
  private String name; // Имя
  private String surname; // Фамилия
  private String patronymic; // Отчество

  // Числовые поля
  private int age; // Возраст (целое)
  private double gpa; // Средний балл (вещественное)
  private int groupNumber; // Номер группы (целое)
  private double scholarship; // Стипендия в рублях (вещественное)

  // Составные поля
  private StudyGroup group; // Учебная группа
  private Gender gender; // Пол


  /**
   * Создаёт студента со значениями по умолчанию.
   */
  public Student() {
    this.name = "Имя";
    this.surname = "Фамилия";
    this.patronymic = "Отчество";
    this.age = 18;
    this.gpa = 3.0;
    this.groupNumber = 1;
    this.scholarship = 0.0;
    this.group = new StudyGroup("Группа по умолчанию", 1);
    this.gender = Gender.MALE;
  }

  /**
   * Создаёт студента с явно указанными значениями всех полей.
   */
  public Student(
      String name,
      String surname,
      String patronymic,
      int age,
      double gpa,
      int groupNumber,
      double scholarship,
      StudyGroup group,
      Gender gender) {
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.age = age;
    this.gpa = gpa;
    this.groupNumber = groupNumber;
    this.scholarship = scholarship;
    this.group = group;
    this.gender = gender;
  }

  /**
   * Возвращает полное имя студента.
   */
  public String getFullName() {
    return getSurname() + " " + getName() + " " + getPatronymic();
  }

  /**
   * Возвращает имя.
   */
  public String getName() {
    return name != null ? name : "";
  }

  /**
   * Возвращает фамилию.
   */
  public String getSurname() {
    return surname != null ? surname : "";
  }

  /**
   * Возвращает отчество.
   */
  public String getPatronymic() {
    return patronymic != null ? patronymic : "";
  }

  /**
   * Возвращает возраст студента.
   */
  public int getAge() {
    return age;
  }

  /**
   * Возвращает средний балл.
   */
  public double getGpa() {
    return gpa;
  }

  /**
   * Возвращает номер учебной группы.
   */
  public int getGroupNumber() {
    return groupNumber;
  }

  /**
   * Возвращает размер ежемесячной стипендии.
   */
  public double getScholarship() {
    return scholarship;
  }

  /**
   * Возвращает строковое представление учебной группы.
   */
  public String getStudyGroup() {
    return group.toString();
  }

  /**
   * Возвращает обозначение пола.
   */
  public String getGender() {
    return gender.getLabel();
  }

  /**
   * Возвращает академический статус студента на основе среднего балла.
   */
  public String getAcademicStatus() {
    if (gpa >= 4.5) {
      return "Отличник";
    } else if (gpa >= 3.5) {
      return "Хорошист";
    } else if (gpa >= 2.5) {
      return "Троечник";
    } else {
      return "Должник";
    }
  }

  /**
   * Устанавливает имя, если оно является непустой строкой длиной от 2 до 50 символов.
   *
   * @return {@code true}, если значение принято; {@code false} в противном случае.
   */
  public boolean setName(String name) {
    if (!isStringValid(name)) {
      return false;
    }
    this.name = name;
    return true;
  }

  /**
   * Устанавливает фамилию, если она прошла валидацию.
   *
   * @return {@code true}, если значение принято.
   */
  public boolean setSurname(String surname) {
    if (!isStringValid(surname)) {
      return false;
    }
    this.surname = surname;
    return true;
  }

  /**
   * Устанавливает отчество, если оно прошло валидацию.
   *
   * @return {@code true}, если значение принято.
   */
  public boolean setPatronymic(String patronymic) {
    if (!isStringValid(patronymic)) {
      return false;
    }
    this.patronymic = patronymic;
    return true;
  }

  /**
   * Устанавливает возраст, если он находится в диапазоне [16, 100].
   *
   * @return {@code true}, если значение принято.
   */
  public boolean setAge(int age) {
    if (age < 16 || age > 100) {
      System.out.println("Ошибка: возраст должен быть в диапазоне от 16 до 100");
      return false;
    }
    this.age = age;
    return true;
  }

  /**
   * Устанавливает средний балл, если он находится в диапазоне [0.0, 5.0].
   *
   * @return {@code true}, если значение принято.
   */
  public boolean setGpa(double gpa) {
    if (gpa < 0.0 || gpa > 5.0) {
      System.out.println("Ошибка: средний балл должен быть в диапазоне от 0.0 до 5.0");
      return false;
    }
    this.gpa = gpa;
    return true;
  }

  /**
   * Устанавливает номер группы, если он является положительным числом.
   *
   * @return {@code true}, если значение принято.
   */
  public boolean setGroupNumber(int groupNumber) {
    if (groupNumber <= 0) {
      System.out.println("Ошибка: номер группы должен быть положительным числом");
      return false;
    }
    this.groupNumber = groupNumber;
    return true;
  }

  /**
   * Устанавливает стипендию, если она не является отрицательным числом.
   *
   * @return {@code true}, если значение принято.
   */
  public boolean setScholarship(double scholarship) {
    if (scholarship < 0.0) {
      System.out.println("Ошибка: стипендия не может быть отрицательной");
      return false;
    }
    this.scholarship = scholarship;
    return true;
  }

  /**
   * Устанавливает учебную группу.
   */
  public void setGroup(StudyGroup group) {
    if (group == null) {
      System.out.println("Ошибка: группа не может быть null");
      return;
    }
    this.group = group;
  }

  /**
   * Устанавливает пол студента.
   */
  public void setGender(Gender gender) {
    if (gender == null) {
      System.out.println("Ошибка: пол не может быть null");
      return;
    }
    this.gender = gender;
  }

  private boolean isStringValid(String value) {
    if (value == null || value.isBlank()) {
      System.out.println("Ошибка: поле не может быть пустым");
      return false;
    }
    if (value.length() < 2 || value.length() > 50) {
      System.out.println("Ошибка: поле должно содержать от 2 до 50 символов");
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return String.format(
        "%s (Возраст: %d, Пол: %s, Группа: %s, Номер гр.: %d, Средний балл: %.2f, Стипендия: "
            + "%.2f руб., Статус: %s)",
        getFullName(),
        age,
        getGender(),
        getStudyGroup(),
        groupNumber,
        gpa,
        scholarship,
        getAcademicStatus());
  }
}

/**
 * Объявление главного класса файла.
 */
public class Main {

  private static final Scanner SCANNER = new Scanner(System.in);

  private static final String[] MENU_ITEMS = {
    "Добавить пустой объект (конструктор по умолчанию)",
    "Добавить объект с данными пользователя",
    "Редактировать поле объекта по индексу",
    "Вывести информацию обо всех объектах",
    "Сортировать список по выбранному полю",
    "Завершить работу программы",
  };

  /**
   * Запуск приложения
   */
  public static void main(String[] args) {
    List<Student> students = new ArrayList<>();
    boolean running = true;

    while (running) {
      printMenu();
      int choice = readIntInRange(1, MENU_ITEMS.length);

      switch (choice) {
        case 1:
          addDefaultStudent(students);
          break;
        case 2:
          addStudentFromInput(students);
          break;
        case 3:
          editStudent(students);
          break;
        case 4:
          printAllStudents(students);
          break;
        case 5:
          sortStudents(students);
          break;
        case 6:
          running = false;
          System.out.println("Программа завершена");
          break;
        default:
          System.out.println("Ошибка: неизвестный пункт меню");
      }
    }
  }

  private static void addDefaultStudent(List<Student> students) {
    students.add(new Student());
    System.out.println(
        "Добавлен объект со значениями по умолчанию (индекс " + (students.size() - 1) + ")");
  }

  private static void addStudentFromInput(List<Student> students) {
    System.out.println("Добавление нового студента: ");

    String name = readString("Имя");
    String surname = readString("Фамилия");
    String patronymic = readString("Отчество");
    int age = readIntWithPrompt("Возраст (16–100)", 16, 100);
    double gpa = readDoubleWithPrompt("Средний балл (0.0–5.0)", 0.0, 5.0);
    int groupNumber = readIntWithPrompt("Номер группы (> 0)", 1, Integer.MAX_VALUE);
    double scholarship = readDoubleWithPrompt("Стипендия (руб., >= 0)", 0.0, Double.MAX_VALUE);

    String groupName = readString("Название учебной группы");
    int course = readIntWithPrompt("Курс (1–6)", 1, 6);
    StudyGroup group = new StudyGroup(groupName, course);

    Gender gender = readGender();

    Student student = new Student(
        name, surname, patronymic, age, gpa, groupNumber, scholarship, group, gender);
    students.add(student);
    System.out.println("Студент добавлен (индекс " + (students.size() - 1) + ")");
  }

  private static void editStudent(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("Список студентов пуст");
      return;
    }

    int index = readIndexInList(students);
    Student student = students.get(index);

    System.out.println("Выберите поле для редактирования:");
    String[] fields = {
        "Имя", "Фамилия", "Отчество", "Возраст", "Средний балл",
        "Номер группы", "Стипендия", "Учебная группа (название + курс)", "Пол"
    };
    for (int i = 0; i < fields.length; i++) {
      System.out.printf("%d. %s%n", i + 1, fields[i]);
    }

    int fieldChoice = readIntInRange(1, fields.length);

    switch (fieldChoice) {
      case 1:
        student.setName(readString("Новое имя"));
        break;
      case 2:
        student.setSurname(readString("Новая фамилия"));
        break;
      case 3:
        student.setPatronymic(readString("Новое отчество"));
        break;
      case 4:
        student.setAge(readIntWithPrompt("Новый возраст (16–100)", 16, 100));
        break;
      case 5:
        student.setGpa(
            readDoubleWithPrompt("Новый средний балл (0.0–5.0)", 0.0, 5.0));
        break;
      case 6:
        student.setGroupNumber(
            readIntWithPrompt("Новый номер группы (> 0)", 1, Integer.MAX_VALUE));
        break;
      case 7:
        student.setScholarship(
            readDoubleWithPrompt("Новая стипендия (>= 0)", 0.0, Double.MAX_VALUE));
        break;
      case 8:
        String groupName = readString("Новое название группы");
        int course = readIntWithPrompt("Новый курс (1–6)", 1, 6);
        student.setGroup(new StudyGroup(groupName, course));
        break;
      case 9:
        student.setGender(readGender());
        break;
      default:
        System.out.println("Ошибка: неизвестное поле");
    }

    System.out.println("Данные обновлены");
  }

  private static void printAllStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("Список студентов пуст");
      return;
    }
    System.out.println("Список студентов:");
    for (int i = 0; i < students.size(); i++) {
      System.out.printf("%d. %s%n", i, students.get(i));
    }
  }

  private static void sortStudents(List<Student> students) {
    if (students.isEmpty()) {
      System.out.println("Список студентов пуст");
      return;
    }

    System.out.println("Выберите поле для сортировки:");
    String[] sortFields = {
        "Фамилия", "Имя", "Возраст", "Средний балл", "Номер группы", "Стипендия"
    };
    for (int i = 0; i < sortFields.length; i++) {
      System.out.printf("%d. %s%n", i + 1, sortFields[i]);
    }

    int sortChoice = readIntInRange(1, sortFields.length);
    Comparator<Student> comparator;

    switch (sortChoice) {
      case 1:
        comparator = Comparator.comparing(Student::getSurname);
        break;
      case 2:
        comparator = Comparator.comparing(Student::getName);
        break;
      case 3:
        comparator = Comparator.comparingInt(Student::getAge);
        break;
      case 4:
        comparator = Comparator.comparingDouble(Student::getGpa);
        break;
      case 5:
        comparator = Comparator.comparingInt(Student::getGroupNumber);
        break;
      case 6:
        comparator = Comparator.comparingDouble(Student::getScholarship);
        break;
      default:
        System.out.println("Ошибка: неизвестное поле сортировки");
        return;
    }

    students.sort(comparator);
    System.out.println("Список отсортирован по полю \"" + sortFields[sortChoice - 1] + "\"");
    printAllStudents(students);
  }

  /**
   * Выводит главное меню в консоль.
   */
  private static void printMenu() {
    System.out.println("Выберите действие:");
    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.printf("%d. %s%n", i + 1, MENU_ITEMS[i]);
    }
    System.out.print("Ваш выбор: ");
  }

  /**
   * Считывает целое число из stdin в диапазоне [{@code min}, {@code max}]. Повторяет запрос, пока
   * не будет введено корректное значение.
   */
  private static int readIntInRange(int min, int max) {
    while (true) {
      String input = SCANNER.nextLine().trim();
      if (!input.matches("-?\\d+")) {
        System.out.printf("Ошибка: введите целое число от %d до %d: ", min, max);
        continue;
      }
      int value = Integer.parseInt(input);
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %d до %d: ", min, max);
        continue;
      }
      return value;
    }
  }

  /**
   * Выводит подсказку и считывает целое число в диапазоне [{@code min}, {@code max}].
   */
  private static int readIntWithPrompt(String prompt, int min, int max) {
    System.out.printf("%s: ", prompt);
    return readIntInRange(min, max);
  }

  /**
   * Считывает вещественное число из stdin в диапазоне [{@code min}, {@code max}]. Повторяет запрос,
   * пока не будет введено корректное значение.
   */
  private static double readDoubleInRange(double min, double max) {
    while (true) {
      String input = SCANNER.nextLine().trim().replace(',', '.');
      double value;
      try {
        value = Double.parseDouble(input);
      } catch (NumberFormatException e) {
        System.out.printf("Ошибка: введите число (напр. 3.75): ");
        continue;
      }
      if (value < min || value > max) {
        System.out.printf("Ошибка: число должно быть от %.2f до %.2f: ", min, max);
        continue;
      }
      return value;
    }
  }

  /**
   * Выводит подсказку и считывает вещественное число в диапазоне [{@code min}, {@code max}].
   */
  private static double readDoubleWithPrompt(String prompt, double min, double max) {
    System.out.printf("%s: ", prompt);
    return readDoubleInRange(min, max);
  }

  /**
   * Считывает непустую строку из stdin длиной от 2 до 50 символов. Повторяет запрос, пока не будет
   * введено корректное значение.
   */
  private static String readString(String prompt) {
    while (true) {
      System.out.printf("%s: ", prompt);
      String value = SCANNER.nextLine().trim();
      if (value.isBlank() || value.length() < 2 || value.length() > 50) {
        System.out.println("Ошибка: введите строку от 2 до 50 символов");
        continue;
      }
      return value;
    }
  }

  /**
   * Считывает корректный индекс объекта в списке (нумерация с 0). Повторяет запрос, пока индекс не
   * окажется в допустимых границах.
   */
  private static int readIndexInList(List<Student> students) {
    System.out.printf("Введите индекс объекта (0–%d): ", students.size() - 1);
    return readIntInRange(0, students.size() - 1);
  }

  /**
   * Считывает выбор пола ({@link Gender}) из stdin. Повторяет запрос, пока не будет введено 1
   * (мужской) или 2 (женский).
   */
  private static Gender readGender() {
    System.out.println("Пол: 1. Мужской, 2. Женский");
    System.out.print("Ваш выбор: ");
    int choice = readIntInRange(1, 2);
    return choice == 1 ? Gender.MALE : Gender.FEMALE;
  }
}
