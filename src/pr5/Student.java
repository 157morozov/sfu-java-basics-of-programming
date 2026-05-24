package pr5;

import java.util.logging.Level;
import java.util.logging.Logger;


class Student {

  /**
   * Логгер класса. Используется для записи предупреждений при невалидных данных и информационных
   * сообщений об успешных операциях.
   */
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
   * Демонстрирует подавление исключения: если создание {@link StudyGroup}
   * по умолчанию по каким-либо причинам завершится ошибкой, исключение подавляется через
   * {@link Throwable#addSuppressed} и добавляется к общему {@link InvalidFieldException}. Это
   * позволяет не потерять информацию об ошибке, продолжая обработку основного пути.
   *
   * @throws InvalidFieldException если инициализация значений по умолчанию оказалась невозможной.
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

    InvalidFieldException primary = null;
    try {
      this.group = new StudyGroup("Группа по умолчанию", 1);
      LOGGER.info("Студент создан со значениями по умолчанию");
    } catch (InvalidFieldException groupEx) {
      primary = new InvalidFieldException("group",
          "Не удалось создать группу по умолчанию");
      primary.addSuppressed(groupEx);
      LOGGER.log(Level.WARNING, "Ошибка при создании группы по умолчанию", groupEx);
    }
    if (primary != null) {
      throw primary;
    }
  }

  /**
   * Создаёт студента с явно указанными значениями всех полей.
   * Демонстрирует повторное генерирование и цепочку исключений (rethrow/chaining):
   * если конструктор {@link StudyGroup} выбросил {@link InvalidFieldException}, она перехватывается
   * и пробрасывается повторно как новое {@link InvalidFieldException} с более подробным контекстом,
   * сохраняя исходную причину (cause).
   *
   * @param name имя.
   * @param surname фамилия.
   * @param patronymic отчество.
   * @param age возраст.
   * @param gpa средний балл.
   * @param groupNumber номер группы.
   * @param scholarship стипендия.
   * @param group готовый объект учебной группы.
   * @param gender пол.
   * @throws InvalidFieldException если любое из переданных значений не прошло валидацию (в том
   *                               числе если возраст был обёрнут в цепочку исключений).
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

    try {
      setAge(age);
    } catch (StudentAgeException ex) {
      LOGGER.log(Level.WARNING, "Недопустимый возраст при создании студента: " + age, ex);
      throw new InvalidFieldException("age",
          "Недопустимый возраст при создании студента: " + age, ex);
    }

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
    return group != null ? group.toString() : "";
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
   * Устанавливает имя студента.
   *
   * @param name имя (от 2 до 50 непустых символов).
   * @throws InvalidFieldException если строка не прошла валидацию.
   */
  public void setName(String name) throws InvalidFieldException {
    validateString("имя", name);
    this.name = name;
  }

  /**
   * Устанавливает фамилию студента.
   *
   * @param surname фамилия (от 2 до 50 непустых символов).
   * @throws InvalidFieldException если строка не прошла валидацию.
   */
  public void setSurname(String surname) throws InvalidFieldException {
    validateString("фамилия", surname);
    this.surname = surname;
  }

  /**
   * Устанавливает отчество студента.
   *
   * @param patronymic отчество (от 2 до 50 непустых символов).
   * @throws InvalidFieldException если строка не прошла валидацию.
   */
  public void setPatronymic(String patronymic) throws InvalidFieldException {
    validateString("отчество", patronymic);
    this.patronymic = patronymic;
  }

  /**
   * Устанавливает возраст студента.
   * Выбрасывает {@link StudentAgeException} — отдельный тип исключения,
   * предназначенный именно для ошибок возраста.
   *
   * @param age возраст (от 16 до 100 включительно).
   * @throws StudentAgeException если возраст выходит за допустимые границы.
   */
  public void setAge(int age) throws StudentAgeException {
    if (age < 16 || age > 100) {
      throw new StudentAgeException(age,
          "Возраст должен быть в диапазоне от 16 до 100, получено: " + age);
    }
    this.age = age;
  }

  /**
   * Устанавливает средний балл студента.
   *
   * @param gpa средний балл (от 0.0 до 5.0 включительно).
   * @throws InvalidFieldException если значение выходит за допустимый диапазон.
   */
  public void setGpa(double gpa) throws InvalidFieldException {
    if (gpa < 0.0 || gpa > 5.0) {
      throw new InvalidFieldException("средний балл",
          "Средний балл должен быть в диапазоне от 0.0 до 5.0, получено: " + gpa);
    }
    this.gpa = gpa;
  }

  /**
   * Устанавливает номер учебной группы.
   *
   * @param groupNumber номер группы (положительное целое число).
   * @throws InvalidFieldException если значение не является положительным.
   */
  public void setGroupNumber(int groupNumber) throws InvalidFieldException {
    if (groupNumber <= 0) {
      throw new InvalidFieldException("номер группы",
          "Номер группы должен быть положительным числом, получено: " + groupNumber);
    }
    this.groupNumber = groupNumber;
  }

  /**
   * Устанавливает размер стипендии.
   *
   * @param scholarship стипендия (неотрицательное число).
   * @throws InvalidFieldException если значение отрицательное.
   */
  public void setScholarship(double scholarship) throws InvalidFieldException {
    if (scholarship < 0.0) {
      throw new InvalidFieldException("стипендия",
          "Стипендия не может быть отрицательной, получено: " + scholarship);
    }
    this.scholarship = scholarship;
  }

  /**
   * Устанавливает учебную группу студента.
   *
   * @param group учебная группа.
   * @throws InvalidFieldException если передан {@code null}.
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
   * @throws InvalidFieldException если передан {@code null}.
   */
  public void setGender(Gender gender) throws InvalidFieldException {
    if (gender == null) {
      throw new InvalidFieldException("пол", "Пол студента не может быть null");
    }
    this.gender = gender;
  }

  /**
   * Проверяет, что строка непустая и имеет длину от 2 до 50 символов.
   *
   * @param fieldName название поля (для сообщения об ошибке).
   * @param value проверяемое значение.
   * @throws InvalidFieldException если строка не прошла валидацию.
   */
  private void validateString(String fieldName, String value) throws InvalidFieldException {
    if (value == null || value.isBlank()) {
      throw new InvalidFieldException(fieldName,
          "Поле «" + fieldName + "» не может быть пустым");
    }
    if (value.length() < 2 || value.length() > 50) {
      throw new InvalidFieldException(fieldName,
          "Поле «" + fieldName + "» должно содержать от 2 до 50 символов, "
              + "получено: " + value.length());
    }
  }

  @Override
  public String toString() {
    return String.format(
        "%s (Возраст: %d, Пол: %s, Группа: %s, Номер гр.: %d, "
            + "Средний балл: %.2f, Стипендия: %.2f руб., Статус: %s)",
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
