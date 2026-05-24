package pr5;

/**
 * Проверяемое исключение, сигнализирующее о недопустимом возрасте студента.
 * Выделено в отдельный класс, так как возраст является ключевым атрибутом
 * для определения статуса обучающегося, и обработка этой ошибки может
 * требовать специфической реакции по сравнению с общей валидацией полей.
 */
public class StudentAgeException extends Exception {

  /** Недопустимое значение возраста, которое спровоцировало исключение. */
  private final int invalidAge;

  /**
   * Создаёт исключение с указанием недопустимого значения возраста.
   *
   * @param invalidAge значение возраста, которое было признано недопустимым.
   * @param message читаемое описание ошибки.
   */
  public StudentAgeException(int invalidAge, String message) {
    super(message);
    this.invalidAge = invalidAge;
  }

  /**
   * Создаёт исключение с указанием недопустимого значения возраста и причины.
   *
   * @param invalidAge значение возраста, которое было признано недопустимым.
   * @param message читаемое описание ошибки.
   * @param cause исходное исключение-причина (для цепочки исключений).
   */
  public StudentAgeException(int invalidAge, String message, Throwable cause) {
    super(message, cause);
    this.invalidAge = invalidAge;
  }

  /**
   * Возвращает значение возраста, признанного недопустимым.
   *
   * @return недопустимый возраст.
   */
  public int getInvalidAge() {
    return invalidAge;
  }

  @Override
  public String toString() {
    return "StudentAgeException[age=" + invalidAge + "]: " + getMessage();
  }
}
