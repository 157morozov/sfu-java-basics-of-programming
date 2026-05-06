package pr3;

import java.util.Objects;

/**
 * Класс, представляющий поезд.
 * Наследует общие характеристики транспортного средства и добавляет:
 * маршрут, количество вагонов и тип тяги.
 */
public class Train extends Vehicle {

  /** Минимальное количество вагонов. */
  public static final int MIN_WAGONS = 1;

  /** Максимальное количество вагонов. */
  public static final int MAX_WAGONS = 500;

  /** Маршрут поезда (например, «Москва — Санкт-Петербург»). */
  private String route;

  /** Количество вагонов. */
  private int wagonsCount;

  /** Тип тяги (например, «электрическая», «тепловозная»). */
  private String tractionType;

  /**
   * Конструктор по умолчанию.
   * Инициализирует поля значениями по умолчанию.
   */
  public Train() {
    super();
    this.route = "Не указан";
    this.wagonsCount = MIN_WAGONS;
    this.tractionType = "Не указан";
  }

  /**
   * Конструктор с параметрами.
   *
   * @param name название поезда
   * @param maxSpeed максимальная скорость (км/ч)
   * @param year год выпуска
   * @param route маршрут
   * @param wagonsCount количество вагонов, диапазон [{@value #MIN_WAGONS}, {@value #MAX_WAGONS}]
   * @param tractionType тип тяги
   */
  public Train(String name, int maxSpeed, int year,
      String route, int wagonsCount, String tractionType) {
    super(name, maxSpeed, year);
    this.route = route;
    this.wagonsCount = wagonsCount;
    this.tractionType = tractionType;
  }

  /**
   * Возвращает маршрут поезда.
   *
   * @return маршрут
   */
  public String getRoute() {
    return route;
  }

  /**
   * Устанавливает маршрут поезда.
   *
   * @param route маршрут
   */
  public void setRoute(String route) {
    this.route = route;
  }

  /**
   * Возвращает количество вагонов.
   *
   * @return количество вагонов
   */
  public int getWagonsCount() {
    return wagonsCount;
  }

  /**
   * Устанавливает количество вагонов.
   *
   * @param wagonsCount количество вагонов
   */
  public void setWagonsCount(int wagonsCount) {
    this.wagonsCount = wagonsCount;
  }

  /**
   * Возвращает тип тяги.
   *
   * @return тип тяги
   */
  public String getTractionType() {
    return tractionType;
  }

  /**
   * Устанавливает тип тяги.
   *
   * @param tractionType тип тяги
   */
  public void setTractionType(String tractionType) {
    this.tractionType = tractionType;
  }

  /**
   * Возвращает строковое представление объекта.
   *
   * @return строка с полями объекта
   */
  @Override
  public String toString() {
    return "Train{"
        + "name='" + getName() + '\''
        + ", maxSpeed=" + getMaxSpeed()
        + ", year=" + getYear()
        + ", route='" + route + '\''
        + ", wagonsCount=" + wagonsCount
        + ", tractionType='" + tractionType + '\''
        + '}';
  }

  /**
   * Сравнивает данный объект с другим на равенство.
   *
   * @param o объект для сравнения
   * @return {@code true}, если объекты равны
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Train train = (Train) o;
    return wagonsCount == train.wagonsCount
        && Objects.equals(route, train.route)
        && Objects.equals(tractionType, train.tractionType);
  }

  /**
   * Возвращает хэш-код объекта.
   *
   * @return хэш-код
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), route, wagonsCount, tractionType);
  }
}