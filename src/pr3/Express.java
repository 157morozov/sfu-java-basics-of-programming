package pr3;

import java.util.Objects;

/**
 * Класс, представляющий экспресс-поезд.
 * Наследует характеристики поезда и добавляет:
 * класс обслуживания, цену билета и признак высокоскоростного режима.
 */
public class Express extends Train {

  /** Минимальная цена билета (руб.). */
  public static final double MIN_TICKET_PRICE = 0.01;

  /** Максимальная цена билета (руб.). */
  public static final double MAX_TICKET_PRICE = 1_000_000.0;

  /** Класс обслуживания (например, «бизнес», «эконом»). */
  private String serviceClass;

  /** Цена билета в рублях. */
  private double ticketPrice;

  /** Признак высокоскоростного режима. */
  private boolean highSpeed;

  /**
   * Конструктор по умолчанию.
   * Инициализирует поля значениями по умолчанию.
   */
  public Express() {
    super();
    this.serviceClass = "Не указан";
    this.ticketPrice = MIN_TICKET_PRICE;
    this.highSpeed = false;
  }

  /**
   * Конструктор с параметрами.
   *
   * @param name название экспресса
   * @param maxSpeed максимальная скорость (км/ч)
   * @param year год выпуска
   * @param route маршрут
   * @param wagonsCount количество вагонов
   * @param tractionType тип тяги
   * @param serviceClass класс обслуживания
   * @param ticketPrice цена билета (руб.), диапазон
   * [{@value #MIN_TICKET_PRICE}, {@value #MAX_TICKET_PRICE}]
   * @param highSpeed    высокоскоростной режим
   */
  public Express(String name, int maxSpeed, int year,
      String route, int wagonsCount, String tractionType,
      String serviceClass, double ticketPrice, boolean highSpeed) {
    super(name, maxSpeed, year, route, wagonsCount, tractionType);
    this.serviceClass = serviceClass;
    this.ticketPrice = ticketPrice;
    this.highSpeed = highSpeed;
  }

  /**
   * Возвращает класс обслуживания.
   *
   * @return класс обслуживания
   */
  public String getServiceClass() {
    return serviceClass;
  }

  /**
   * Устанавливает класс обслуживания.
   *
   * @param serviceClass класс обслуживания
   */
  public void setServiceClass(String serviceClass) {
    this.serviceClass = serviceClass;
  }

  /**
   * Возвращает цену билета.
   *
   * @return цена в рублях
   */
  public double getTicketPrice() {
    return ticketPrice;
  }

  /**
   * Устанавливает цену билета.
   *
   * @param ticketPrice цена в рублях
   */
  public void setTicketPrice(double ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

  /**
   * Возвращает признак высокоскоростного режима.
   *
   * @return {@code true}, если высокоскоростной
   */
  public boolean isHighSpeed() {
    return highSpeed;
  }

  /**
   * Устанавливает признак высокоскоростного режима.
   *
   * @param highSpeed высокоскоростной режим
   */
  public void setHighSpeed(boolean highSpeed) {
    this.highSpeed = highSpeed;
  }

  /**
   * Возвращает строковое представление объекта.
   *
   * @return строка с полями объекта
   */
  @Override
  public String toString() {
    return "Express{"
        + "name='" + getName() + '\''
        + ", maxSpeed=" + getMaxSpeed()
        + ", year=" + getYear()
        + ", route='" + getRoute() + '\''
        + ", wagonsCount=" + getWagonsCount()
        + ", tractionType='" + getTractionType() + '\''
        + ", serviceClass='" + serviceClass + '\''
        + ", ticketPrice=" + ticketPrice
        + ", highSpeed=" + highSpeed
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
    Express express = (Express) o;
    return Double.compare(express.ticketPrice, ticketPrice) == 0
        && highSpeed == express.highSpeed
        && Objects.equals(serviceClass, express.serviceClass);
  }

  /**
   * Возвращает хэш-код объекта.
   *
   * @return хэш-код
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), serviceClass, ticketPrice, highSpeed);
  }
}