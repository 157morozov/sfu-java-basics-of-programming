package pr5;

/**
 * Учебная группа, к которой принадлежит студент.
 * Класс демонстрирует применение утверждений ({@code assert}) для проверки
 * инвариантов после выполнения конструктора.
 */
class StudyGroup {

  /** Минимально допустимый номер курса. */
  static final int MIN_COURSE = 1;

  /** Максимально допустимый номер курса. */
  static final int MAX_COURSE = 6;

  private String name;
  private int course;

  /**
   * Создаёт учебную группу с указанным названием и номером курса.
   * Для проверки постусловия конструктора используется утверждение: после
   * инициализации поле {@code course} обязано находиться в допустимом диапазоне
   * [{@value #MIN_COURSE}; {@value #MAX_COURSE}]. Утверждение активируется
   * запуском JVM с флагом {@code -ea}.
   *
   * @param name название группы.
   * @param course номер курса (от {@value #MIN_COURSE} до {@value #MAX_COURSE}).
   * @throws InvalidFieldException если название группы пустое или {@code null},
   *                               либо номер курса выходит за допустимый диапазон.
   */
  public StudyGroup(String name, int course) throws InvalidFieldException {
    if (name == null || name.isBlank()) {
      throw new InvalidFieldException("название группы",
          "Название группы не может быть пустым");
    }
    if (course < MIN_COURSE || course > MAX_COURSE) {
      throw new InvalidFieldException("курс",
          "Курс должен быть в диапазоне от " + MIN_COURSE + " до " + MAX_COURSE
              + ", получено: " + course);
    }
    this.name = name;
    this.course = course;

    assert this.course >= MIN_COURSE && this.course <= MAX_COURSE
        : "Инвариант нарушен: course=" + this.course;
  }

  @Override
  public String toString() {
    return getName() + " (" + course + " курс)";
  }

  /**
   * Возвращает название группы.
   *
   * @return название группы.
   */
  public String getName() {
    return name != null ? name : "";
  }

  /**
   * Возвращает номер курса.
   *
   * @return номер курса.
   */
  public int getCourse() {
    return course;
  }
}
