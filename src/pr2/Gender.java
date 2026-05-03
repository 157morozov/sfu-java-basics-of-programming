package pr2;

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
   *
   * @return читаемое обозначение пола.
   */
  public String getLabel() {
    return label;
  }
}