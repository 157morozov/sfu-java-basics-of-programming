package pr3;

import java.util.Objects;

/**
 * Класс, представляющий автомобиль.
 * Наследует общие характеристики транспортного средства и добавляет:
 * тип кузова, мощность двигателя и количество дверей.
 */
public class Car extends Vehicle {

  /** Минимальная мощность двигателя (л.с.). */
  public static final int MIN_POWER = 1;

  /** Максимальная мощность двигателя (л.с.). */
  public static final int MAX_POWER = 10_000;

  /** Минимальное количество дверей. */
  public static final int MIN_DOORS = 2;

  /** Максимальное количество дверей. */
  public static final int MAX_DOORS = 10;

  /** Тип кузова (например, «седан», «универсал»). */
  private String bodyType;

  /** Мощность двигателя в лошадиных силах. */
  private int enginePower;

  /** Количество дверей. */
  private int doorsCount;

  /**
   * Конструктор по умолчанию.
   * Инициализирует поля значениями по умолчанию.
   */
  public Car() {
    super();
    this.bodyType = "Не указан";
    this.enginePower = MIN_POWER;
    this.doorsCount = MIN_DOORS;
  }

  /**
   * Конструктор с параметрами.
   *
   * @param name название автомобиля
   * @param maxSpeed максимальная скорость (км/ч)
   * @param year год выпуска
   * @param bodyType тип кузова
   * @param enginePower мощность двигателя (л.с.), диапазон [{@value #MIN_POWER}, {@value #MAX_POWER}]
   * @param doorsCount количество дверей, диапазон [{@value #MIN_DOORS}, {@value #MAX_DOORS}]
   */
  public Car(String name, int maxSpeed, int year,
      String bodyType, int enginePower, int doorsCount) {
    super(name, maxSpeed, year);
    this.bodyType = bodyType;
    this.enginePower = enginePower;
    this.doorsCount = doorsCount;
  }

  /**
   * Возвращает тип кузова.
   *
   * @return тип кузова
   */
  public String getBodyType() {
    return bodyType;
  }

  /**
   * Устанавливает тип кузова.
   *
   * @param bodyType тип кузова
   */
  public void setBodyType(String bodyType) {
    this.bodyType = bodyType;
  }

  /**
   * Возвращает мощность двигателя.
   *
   * @return мощность в л.с.
   */
  public int getEnginePower() {
    return enginePower;
  }

  /**
   * Устанавливает мощность двигателя.
   *
   * @param enginePower мощность в л.с.
   */
  public void setEnginePower(int enginePower) {
    this.enginePower = enginePower;
  }

  /**
   * Возвращает количество дверей.
   *
   * @return количество дверей
   */
  public int getDoorsCount() {
    return doorsCount;
  }

  /**
   * Устанавливает количество дверей.
   *
   * @param doorsCount количество дверей
   */
  public void setDoorsCount(int doorsCount) {
    this.doorsCount = doorsCount;
  }

  /**
   * Возвращает строковое представление объекта.
   *
   * @return строка с полями объекта
   */
  @Override
  public String toString() {
    return "Car{"
        + "name='" + getName() + '\''
        + ", maxSpeed=" + getMaxSpeed()
        + ", year=" + getYear()
        + ", bodyType='" + bodyType + '\''
        + ", enginePower=" + enginePower
        + ", doorsCount=" + doorsCount
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
    Car car = (Car) o;
    return enginePower == car.enginePower
        && doorsCount == car.doorsCount
        && Objects.equals(bodyType, car.bodyType);
  }

  /**
   * Возвращает хэш-код объекта.
   *
   * @return хэш-код
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), bodyType, enginePower, doorsCount);
  }
}