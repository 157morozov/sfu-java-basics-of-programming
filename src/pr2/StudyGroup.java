package pr2;

/**
 * Учебная группа, к которой принадлежит студент.
 */
class StudyGroup {

  private String name;
  private int course;

  /**
   * Создаёт учебную группу с указанным названием и номером курса.
   *
   * @param name   название группы.
   * @param course номер курса.
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