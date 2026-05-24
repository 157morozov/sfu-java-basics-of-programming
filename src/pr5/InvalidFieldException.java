package pr5;

/**
 * Проверяемое исключение, сигнализирующее о недопустимом значении поля объекта.
 * Выбрасывается сеттерами и конструкторами классов предметной области,
 * когда переданный аргумент не соответствует бизнес-правилам (пустая строка,
 * выход за допустимый диапазон, {@code null} и т.п.).
 */
public class InvalidFieldException extends Exception {

  /** Название поля, значение которого нарушило ограничение. */
  private final String fieldName;

  /**
   * Создаёт исключение с указанием поля и текстом описания ошибки.
   *
   * @param fieldName название поля, содержащего недопустимое значение.
   * @param message читаемое описание причины ошибки.
   */
  public InvalidFieldException(String fieldName, String message) {
    super(message);
    this.fieldName = fieldName;
  }

  /**
   * Создаёт исключение с указанием поля, описания и причины (для цепочки исключений).
   *
   * @param fieldName название поля, содержащего недопустимое значение.
   * @param message читаемое описание причины ошибки.
   * @param cause исходное исключение-причина.
   */
  public InvalidFieldException(String fieldName, String message, Throwable cause) {
    super(message, cause);
    this.fieldName = fieldName;
  }

  /**
   * Возвращает название поля, значение которого было отклонено.
   *
   * @return название поля.
   */
  public String getFieldName() {
    return fieldName;
  }

  @Override
  public String toString() {
    return "InvalidFieldException[field=" + fieldName + "]: " + getMessage();
  }
}
