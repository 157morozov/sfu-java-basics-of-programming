package pr6;

/**
 * Обобщённый кольцевой однонаправленный список.
 * Внутри класса не используются готовые коллекции Java, хранение данных построено
 * только на связанных узлах. У списка есть указатель, относительно которого
 * выполняются основные операции варианта.
 *
 * @param <T> тип значений, хранящихся в списке.
 */
class CircularSinglyLinkedList<T> {

  /** Узел кольцевого однонаправленного списка. */
  private static class Node<T> {

    /** Значение узла. */
    private T value;

    /** Ссылка на следующий узел. */
    private Node<T> next;

    /**
     * Создаёт узел с указанным значением.
     *
     * @param value значение узла.
     */
    private Node(T value) {
      this.value = value;
    }
  }

  /** Первый элемент списка. */
  private Node<T> head;

  /** Последний элемент списка. */
  private Node<T> tail;

  /** Рабочий указатель списка. */
  private Node<T> pointer;

  /** Количество элементов списка. */
  private int size;

  /**
   * Проверяет, пуст ли список.
   *
   * @return {@code true}, если список пуст, иначе {@code false}.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Возвращает количество элементов списка.
   *
   * @return количество элементов списка.
   */
  public int size() {
    return size;
  }

  /**
   * Устанавливает указатель в начало списка.
   *
   * @throws InvalidListOperationException если список пуст.
   */
  public void setPointerToStart() throws InvalidListOperationException {
    checkNotEmpty("установка указателя в начало");
    pointer = head;
  }

  /**
   * Добавляет элемент за указателем. Если список пуст, новый элемент становится началом,
   * концом и текущим указателем списка.
   *
   * @param value добавляемое значение.
   */
  public void addAfterPointer(T value) {
    Node<T> newNode = new Node<>(value);

    if (isEmpty()) {
      head = newNode;
      tail = newNode;
      pointer = newNode;
      newNode.next = newNode;
      size = 1;
      return;
    }

    newNode.next = pointer.next;
    pointer.next = newNode;

    if (pointer == tail) {
      tail = newNode;
    }
    size++;
  }

  /**
   * Удаляет элемент за указателем.
   *
   * @return значение удалённого элемента.
   * @throws InvalidListOperationException если список пуст.
   */
  public T removeAfterPointer() throws InvalidListOperationException {
    checkNotEmpty("удаление элемента за указателем");

    Node<T> removedNode = pointer.next;
    T removedValue = removedNode.value;

    if (size == 1) {
      head = null;
      tail = null;
      pointer = null;
      size = 0;
      return removedValue;
    }

    pointer.next = removedNode.next;

    if (removedNode == head) {
      head = removedNode.next;
    }
    if (removedNode == tail) {
      tail = pointer;
    }

    size--;
    return removedValue;
  }

  /**
   * Просматривает элемент за указателем без удаления.
   *
   * @return значение элемента за указателем.
   * @throws InvalidListOperationException если список пуст.
   */
  public T getAfterPointer() throws InvalidListOperationException {
    checkNotEmpty("просмотр элемента за указателем");
    return pointer.next.value;
  }

  /**
   * Перемещает указатель вправо, то есть на следующий элемент списка.
   *
   * @throws InvalidListOperationException если список пуст.
   */
  public void movePointerRight() throws InvalidListOperationException {
    checkNotEmpty("перемещение указателя вправо");
    pointer = pointer.next;
  }

  /**
   * Обменивает значения конца списка и элемента за указателем.
   *
   * @throws InvalidListOperationException если список пуст.
   */
  public void swapTailAndAfterPointer() throws InvalidListOperationException {
    checkNotEmpty("обмен конца списка и элемента за указателем");
    swapValues(tail, pointer.next);
  }

  /**
   * Обменивает значения начала списка и элемента за указателем.
   *
   * @throws InvalidListOperationException если список пуст.
   */
  public void swapHeadAndAfterPointer() throws InvalidListOperationException {
    checkNotEmpty("обмен начала списка и элемента за указателем");
    swapValues(head, pointer.next);
  }

  /**
   * Возвращает строковое представление списка от начала до конца.
   * Элемент, на котором установлен указатель, дополнительно помечается.
   *
   * @return строковое представление списка.
   */
  public String toDisplayString() {
    if (isEmpty()) {
      return "Список пуст";
    }

    StringBuilder builder = new StringBuilder();
    Node<T> current = head;

    for (int i = 0; i < size; i++) {
      if (current == pointer) {
        builder.append("[").append(current.value).append(" <- указатель]");
      } else {
        builder.append(current.value);
      }

      if (i < size - 1) {
        builder.append(" -> ");
      }
      current = current.next;
    }

    builder.append(" -> начало");
    return builder.toString();
  }

  /**
   * Проверяет, что список не пуст.
   *
   * @param operationName название выполняемой операции.
   * @throws InvalidListOperationException если список пуст.
   */
  private void checkNotEmpty(String operationName) throws InvalidListOperationException {
    if (isEmpty()) {
      throw new InvalidListOperationException(operationName,
          "Невозможно выполнить операцию: список пуст");
    }
  }

  /**
   * Обменивает значения двух узлов.
   *
   * @param first первый узел.
   * @param second второй узел.
   */
  private void swapValues(Node<T> first, Node<T> second) {
    T temp = first.value;
    first.value = second.value;
    second.value = temp;
  }
}
