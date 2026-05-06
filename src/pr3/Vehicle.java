package pr3;

import java.util.Objects;

/**
 * Базовый класс, представляющий транспортное средство.
 * Содержит общие характеристики для всех видов транспорта:
 * название, максимальную скорость и год выпуска.
 */
public class Vehicle {

  /** Минимально допустимая максимальная скорость (км/ч). */
  protected static final int MIN_SPEED = 1;

  /** Максимально допустимая максимальная скорость (км/ч). */
  protected static final int MAX_SPEED = 50_000;

  /** Минимальный год выпуска транспортного средства. */
  protected static final int MIN_YEAR = 1800;

  /** Максимальный год выпуска транспортного средства. */
  protected static final int MAX_YEAR = 2100;

  /** Название транспортного средства. */
  private String name;

  /** Максимальная скорость в км/ч. */
  private int maxSpeed;

  /** Год выпуска. */
  private int year;

  /**
   * Конструктор по умолчанию.
   * Инициализирует поля значениями по умолчанию.
   */
  public Vehicle() {
    this.name = "Не указано";
    this.maxSpeed = MIN_SPEED;
    this.year = MIN_YEAR;
  }

  /**
   * Конструктор с параметрами.
   *
   * @param name название транспортного средства
   * @param maxSpeed максимальная скорость (км/ч), диапазон [{@value #MIN_SPEED}, {@value #MAX_SPEED}]
   * @param year год выпуска, диапазон [{@value #MIN_YEAR}, {@value #MAX_YEAR}]
   */
  public Vehicle(String name, int maxSpeed, int year) {
    this.name = name;
    this.maxSpeed = maxSpeed;
    this.year = year;
  }

  /**
   * Возвращает название транспортного средства.
   *
   * @return название
   */
  public String getName() {
    return name;
  }

  /**
   * Устанавливает название транспортного средства.
   *
   * @param name название
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Возвращает максимальную скорость.
   *
   * @return максимальная скорость в км/ч
   */
  public int getMaxSpeed() {
    return maxSpeed;
  }

  /**
   * Устанавливает максимальную скорость.
   *
   * @param maxSpeed скорость в км/ч
   */
  public void setMaxSpeed(int maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  /**
   * Возвращает год выпуска.
   *
   * @return год выпуска
   */
  public int getYear() {
    return year;
  }

  /**
   * Устанавливает год выпуска.
   *
   * @param year год выпуска
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Возвращает строковое представление объекта.
   *
   * @return строка с полями объекта
   */
  @Override
  public String toString() {
    return "Vehicle{"
        + "name='" + name + '\''
        + ", maxSpeed=" + maxSpeed
        + ", year=" + year
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
    Vehicle vehicle = (Vehicle) o;
    return maxSpeed == vehicle.maxSpeed
        && year == vehicle.year
        && Objects.equals(name, vehicle.name);
  }

  /**
   * Возвращает хэш-код объекта.
   *
   * @return хэш-код
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, maxSpeed, year);
  }
}