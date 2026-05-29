package pr5;

import java.util.logging.Logger;

/**
 * Студент с набором учебных характеристик.
 */
class Student {

  /** Логгер класса Student. */
  private static final Logger LOGGER = Logger.getLogger(Student.class.getName());

  private String name;
  private String surname;
  private String patronymic;
  private int age;
  private double gpa;
  private int groupNumber;
  private double scholarship;
  private StudyGroup group;
  private Gender gender;

  /**
   * Создаёт студента со значениями по умолчанию.
   * Здесь демонстрируется подавление исключения в понимании задания: блок {@code catch}
   * полностью пустой. Исключение от намеренно некорректной пробной группы игнорируется,
   * после чего объект создаётся с корректной группой по умолчанию.
   *
   * @throws InvalidFieldException если корректная группа по умолчанию не была создана.
   */
  public Student() throws InvalidFieldException {
    this.name = "Имя";
    this.surname = "Фамилия";
    this.patronymic = "Отчество";
    this.age = 18;
    this.gpa = 3.0;
    this.groupNumber = 1;
    this.scholarship = 0.0;
    this.gender = Gender.MALE;

    try {
      new StudyGroup("", 0);
    } catch (InvalidFieldException ex) {
    }

    this.group = new StudyGroup("Группа по умолчанию", 1);
    LOGGER.info("Студент создан со значениями по умолчанию");
  }

  /**
   * Создаёт студента с явно указанными значениями всех полей.
   * Повторное генерирование и связывание в цепочку демонстрируется для возраста: сначала
   * моделируется специальное исключение {@link StudentAgeException}, затем оно становится
   * причиной общего {@link InvalidFieldException}.
   *
   * @param name имя.
   * @param surname фамилия.
   * @param patronymic отчество.
   * @param age возраст.
   * @param gpa средний балл.
   * @param groupNumber номер группы.
   * @param scholarship стипендия.
   * @param group учебная группа.
   * @param gender пол.
   * @throws InvalidFieldException если одно из полей не прошло проверку.
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
      Gender gender) throws InvalidFieldException {

    setName(name);
    setSurname(surname);
    setPatronymic(patronymic);

    StudentAgeException ageProblem = null;
    if (age < 16 || age > 100) {
      ageProblem = new StudentAgeException(age,
          "Возраст должен быть в диапазоне от 16 до 100, получено: " + age);
      try {
        throw ageProblem;
      } catch (StudentAgeException ex) {
      }
    }
    if (ageProblem != null) {
      LOGGER.warning("Недопустимый возраст при создании студента: " + age);
      throw new InvalidFieldException(
          "age", "Недопустимый возраст при создании студента", ageProblem);
    }

    this.age = age;
    setGpa(gpa);
    setGroupNumber(groupNumber);
    setScholarship(scholarship);
    setGroup(group);
    setGender(gender);

    LOGGER.info("Создан студент: " + getFullName());
  }

  /**
   * Возвращает полное имя студента.
   *
   * @return полное имя.
   */
  public String getFullName() {
    return getSurname() + " " + getName() + " " + getPatronymic();
  }

  /** @return имя студента. */
  public String getName() {
    return name != null ? name : "";
  }

  /** @return фамилия студента. */
  public String getSurname() {
    return surname != null ? surname : "";
  }

  /** @return отчество студента. */
  public String getPatronymic() {
    return patronymic != null ? patronymic : "";
  }

  /** @return возраст студента. */
  public int getAge() {
    return age;
  }

  /** @return средний балл. */
  public double getGpa() {
    return gpa;
  }

  /** @return номер группы. */
  public int getGroupNumber() {
    return groupNumber;
  }

  /** @return размер стипендии. */
  public double getScholarship() {
    return scholarship;
  }

  /** @return строковое представление учебной группы. */
  public String getStudyGroup() {
    return group != null ? group.toString() : "";
  }

  /** @return читаемое обозначение пола. */
  public String getGender() {
    return gender.getLabel();
  }

  /**
   * Возвращает академический статус студента.
   *
   * @return академический статус.
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
   * Устанавливает имя студента.
   *
   * @param name имя.
   * @throws InvalidFieldException если имя некорректно.
   */
  public void setName(String name) throws InvalidFieldException {
    validateString("имя", name);
    this.name = name;
  }

  /**
   * Устанавливает фамилию студента.
   *
   * @param surname фамилия.
   * @throws InvalidFieldException если фамилия некорректна.
   */
  public void setSurname(String surname) throws InvalidFieldException {
    validateString("фамилия", surname);
    this.surname = surname;
  }

  /**
   * Устанавливает отчество студента.
   *
   * @param patronymic отчество.
   * @throws InvalidFieldException если отчество некорректно.
   */
  public void setPatronymic(String patronymic) throws InvalidFieldException {
    validateString("отчество", patronymic);
    this.patronymic = patronymic;
  }

  /**
   * Устанавливает возраст студента.
   *
   * @param age возраст.
   * @throws StudentAgeException если возраст вне диапазона 16-100.
   */
  public void setAge(int age) throws StudentAgeException {
    if (age < 16 || age > 100) {
      throw new StudentAgeException(age,
          "Возраст должен быть в диапазоне от 16 до 100, получено: " + age);
    }
    this.age = age;
  }

  /**
   * Устанавливает средний балл.
   *
   * @param gpa средний балл.
   * @throws InvalidFieldException если балл вне диапазона 0.0-5.0.
   */
  public void setGpa(double gpa) throws InvalidFieldException {
    if (gpa < 0.0 || gpa > 5.0) {
      throw new InvalidFieldException("средний балл",
          "Средний балл должен быть в диапазоне от 0.0 до 5.0, получено: " + gpa);
    }
    this.gpa = gpa;
  }

  /**
   * Устанавливает номер группы.
   *
   * @param groupNumber номер группы.
   * @throws InvalidFieldException если номер группы неположительный.
   */
  public void setGroupNumber(int groupNumber) throws InvalidFieldException {
    if (groupNumber <= 0) {
      throw new InvalidFieldException("номер группы",
          "Номер группы должен быть положительным числом, получено: " + groupNumber);
    }
    this.groupNumber = groupNumber;
  }

  /**
   * Устанавливает стипендию.
   *
   * @param scholarship стипендия.
   * @throws InvalidFieldException если стипендия отрицательна.
   */
  public void setScholarship(double scholarship) throws InvalidFieldException {
    if (scholarship < 0.0) {
      throw new InvalidFieldException("стипендия",
          "Стипендия не может быть отрицательной, получено: " + scholarship);
    }
    this.scholarship = scholarship;
  }

  /**
   * Устанавливает учебную группу.
   *
   * @param group учебная группа.
   * @throws InvalidFieldException если группа равна null.
   */
  public void setGroup(StudyGroup group) throws InvalidFieldException {
    if (group == null) {
      throw new InvalidFieldException("группа", "Учебная группа не может быть null");
    }
    this.group = group;
  }

  /**
   * Устанавливает пол студента.
   *
   * @param gender пол.
   * @throws InvalidFieldException если пол равен null.
   */
  public void setGender(Gender gender) throws InvalidFieldException {
    if (gender == null) {
      throw new InvalidFieldException("пол", "Пол студента не может быть null");
    }
    this.gender = gender;
  }

  /**
   * Проверяет строковое поле.
   *
   * @param fieldName название поля.
   * @param value значение поля.
   * @throws InvalidFieldException если строка пустая или имеет недопустимую длину.
   */
  private void validateString(String fieldName, String value) throws InvalidFieldException {
    if (value == null || value.isBlank()) {
      throw new InvalidFieldException(fieldName,
          "Поле «" + fieldName + "» не может быть пустым");
    }
    if (value.length() < 2 || value.length() > 50) {
      throw new InvalidFieldException(fieldName,
          "Поле «" + fieldName + "» должно содержать от 2 до 50 символов, получено: "
              + value.length());
    }
  }

  @Override
  public String toString() {
    return String.format(
        "%s (Возраст: %d, Пол: %s, Группа: %s, Номер гр.: %d, "
            + "Средний балл: %.2f, Стипендия: %.2f руб., Статус: %s)",
        getFullName(), age, getGender(), getStudyGroup(), groupNumber, gpa,
        scholarship, getAcademicStatus());
  }
}
