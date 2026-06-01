package pr6;

/**
 * Режим работы демонстрационного приложения.
 */
enum DataMode {
  STRING("Строковые данные", "строку"),
  INTEGER("Целочисленные данные", "целое число");

  private final String label;
  private final String inputLabel;

  DataMode(String label, String inputLabel) {
    this.label = label;
    this.inputLabel = inputLabel;
  }

  /**
   * Возвращает читаемое название режима.
   *
   * @return читаемое название режима.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Возвращает подсказку для ввода значения выбранного типа.
   *
   * @return подсказка для ввода значения.
   */
  public String getInputLabel() {
    return inputLabel;
  }
}
