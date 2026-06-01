package pr6;

/**
 * Проверяемое исключение, сигнализирующее о невозможности выполнить операцию над списком.
 * Обычно выбрасывается при попытке получить, удалить, переместить или обменять элемент,
 * когда кольцевой список пуст.
 */
public class InvalidListOperationException extends Exception {

  /** Название операции, при выполнении которой возникла ошибка. */
  private final String operationName;

  /**
   * Создаёт исключение с указанием операции и причины ошибки.
   *
   * @param operationName название операции.
   * @param message читаемое описание причины ошибки.
   */
  public InvalidListOperationException(String operationName, String message) {
    super(message);
    this.operationName = operationName;
  }

  /**
   * Возвращает название операции, при выполнении которой возникла ошибка.
   *
   * @return название операции.
   */
  public String getOperationName() {
    return operationName;
  }

  @Override
  public String toString() {
    return "InvalidListOperationException[operation=" + operationName + "]: "
        + getMessage();
  }
}
