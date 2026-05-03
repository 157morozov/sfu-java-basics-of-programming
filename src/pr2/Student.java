package pr2;

/**
 * Студент университета с личными и академическими атрибутами.
 */
class Student {

  // Текстовые поля
  private String name;
  private String surname;
  private String patronymic;

  // Числовые поля
  private int age;
  private double gpa;
  private int groupNumber;
  private double scholarship;

  // Составные поля
  private StudyGroup group;
  private Gender gender;

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
   *
   * @param name        имя.
   * @param surname     фамилия.
   * @param patronymic  отчество.
   * @param age         возраст.
   * @param gpa         средний балл.
   * @param groupNumber номер группы.
   * @param scholarship стипендия.
   * @param group       учебная группа.
   * @param gender      пол.
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
   *
   * @return полное имя студента.
   */
  public String getFullName() {
    return getSurname() + " " + getName() + " " + getPatronymic();
  }

  /**
   * Возвращает имя.
   *
   * @return имя.
   */
  public String getName() {
    return name != null ? name : "";
  }

  /**
   * Возвращает фамилию.
   *
   * @return фамилию.
   */
  public String getSurname() {
    return surname != null ? surname : "";
  }

  /**
   * Возвращает отчество.
   *
   * @return отчество.
   */
  public String getPatronymic() {
    return patronymic != null ? patronymic : "";
  }

  /**
   * Возвращает возраст студента.
   *
   * @return возраст студента.
   */
  public int getAge() {
    return age;
  }

  /**
   * Возвращает средний балл.
   *
   * @return средний балл.
   */
  public double getGpa() {
    return gpa;
  }

  /**
   * Возвращает номер учебной группы.
   *
   * @return номер группы.
   */
  public int getGroupNumber() {
    return groupNumber;
  }

  /**
   * Возвращает размер ежемесячной стипендии.
   *
   * @return размер стипендии.
   */
  public double getScholarship() {
    return scholarship;
  }

  /**
   * Возвращает строковое представление учебной группы.
   *
   * @return строковое представление группы.
   */
  public String getStudyGroup() {
    return group.toString();
  }

  /**
   * Возвращает обозначение пола.
   *
   * @return обозначение пола.
   */
  public String getGender() {
    return gender.getLabel();
  }

  /**
   * Возвращает академический статус студента на основе среднего балла.
   *
   * @return статус студента.
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
   * @param name имя.
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
   * @param surname фамилия.
   * @return {@code true}, если значение принято; {@code false} в противном случае.
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
   * @param patronymic отчество.
   * @return {@code true}, если значение принято; {@code false} в противном случае.
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
   * @param age возраст.
   * @return {@code true}, если значение принято; {@code false} в противном случае.
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
   * @param gpa средний балл.
   * @return {@code true}, если значение принято; {@code false} в противном случае.
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
   * @param groupNumber номер группы.
   * @return {@code true}, если значение принято; {@code false} в противном случае.
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
   * @param scholarship стипендия.
   * @return {@code true}, если значение принято; {@code false} в противном случае.
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
   *
   * @param group учебная группа.
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
   *
   * @param gender пол.
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