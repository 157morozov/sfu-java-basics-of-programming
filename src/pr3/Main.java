package pr3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Точка входа в приложение «Транспортные средства».
 * Реализует консольное меню для управления коллекцией объектов
 * иерархии {@link Vehicle}: добавление, удаление, вывод и сравнение.
 */
public class Main {

  /** Пункты главного меню. */
  private static final String[] MENU_ITEMS = {
      "Добавить транспортное средство",
      "Удалить транспортное средство по индексу",
      "Вывести все транспортные средства",
      "Сравнить два транспортных средства",
      "Завершить работу",
  };

  /** Пункты подменю выбора типа транспортного средства. */
  private static final String[] TYPE_ITEMS = {
      "Транспортное средство (Vehicle)",
      "Автомобиль (Car)",
      "Поезд (Train)",
      "Экспресс (Express)",
  };

  /**
   * Главный метод программы.
   *
   * @param args аргументы командной строки (не используются)
   */
  public static void main(String[] args) {
    List<Vehicle> vehicles = new ArrayList<>();
    InputReader reader = new InputReader(new Scanner(System.in));
    boolean running = true;

    while (running) {
      printMenu();
      int choice = reader.readMenuChoice(1, MENU_ITEMS.length);

      switch (choice) {
        case 1:
          addVehicle(vehicles, reader);
          break;

        case 2:
          removeVehicle(vehicles, reader);
          break;

        case 3:
          printAllVehicles(vehicles);
          break;

        case 4:
          compareVehicles(vehicles, reader);
          break;

        case 5:
          running = false;
          System.out.println("Программа завершена.");
          break;

        default:
          System.out.println("Ошибка: неизвестный пункт меню.");
      }
    }
  }

  /**
   * Добавляет транспортное средство в коллекцию.
   * Пользователь выбирает тип объекта из подменю.
   *
   * @param vehicles коллекция транспортных средств
   * @param reader объект для чтения ввода
   */
  private static void addVehicle(List<Vehicle> vehicles, InputReader reader) {
    System.out.println("Выберите тип транспортного средства:");
    for (int i = 0; i < TYPE_ITEMS.length; i++) {
      System.out.printf("%d. %s%n", i + 1, TYPE_ITEMS[i]);
    }
    System.out.print("Ваш выбор: ");
    int typeChoice = reader.readMenuChoice(1, TYPE_ITEMS.length);

    Vehicle vehicle;
    switch (typeChoice) {
      case 1:
        vehicle = readVehicle(reader);
        break;
      case 2:
        vehicle = readCar(reader);
        break;
      case 3:
        vehicle = readTrain(reader);
        break;
      case 4:
        vehicle = readExpress(reader);
        break;
      default:
        System.out.println("Ошибка: неизвестный тип.");
        return;
    }

    vehicles.add(vehicle);
    System.out.printf("Добавлено (индекс %d): %s%n%n", vehicles.size() - 1, vehicle);
  }

  /**
   * Удаляет транспортное средство из коллекции по индексу.
   *
   * @param vehicles коллекция транспортных средств
   * @param reader объект для чтения ввода
   */
  private static void removeVehicle(List<Vehicle> vehicles, InputReader reader) {
    if (vehicles.isEmpty()) {
      System.out.println("Коллекция пуста.");
      return;
    }
    int index = readIndex(vehicles, reader);
    Vehicle removed = vehicles.remove(index);
    System.out.printf("Удалён элемент %d: %s%n%n", index, removed);
  }

  /**
   * Выводит все транспортные средства из коллекции.
   *
   * @param vehicles коллекция транспортных средств
   */
  private static void printAllVehicles(List<Vehicle> vehicles) {
    if (vehicles.isEmpty()) {
      System.out.println("Коллекция пуста.");
      return;
    }
    System.out.println("Список транспортных средств");
    for (int i = 0; i < vehicles.size(); i++) {
      System.out.printf("%d. %s%n", i, vehicles.get(i));
    }
    System.out.println();
  }

  /**
   * Сравнивает два элемента коллекции по индексам с помощью {@code equals()}.
   * Теперь позволяет сравнивать элемент с самим собой, если в коллекции хотя бы 1 объект.
   *
   * @param vehicles коллекция транспортных средств
   * @param reader объект для чтения ввода
   */
  private static void compareVehicles(List<Vehicle> vehicles, InputReader reader) {
    if (vehicles.isEmpty()) {
      System.out.println("Коллекция пуста. Нечего сравнивать.");
      return;
    }

    System.out.println("Введите индекс первого элемента:");
    int firstIndex = readIndex(vehicles, reader);
    System.out.println("Введите индекс второго элемента:");
    int secondIndex = readIndex(vehicles, reader);

    Vehicle first = vehicles.get(firstIndex);
    Vehicle second = vehicles.get(secondIndex);

    System.out.printf("%nЭлемент %d: %s%n", firstIndex, first);
    System.out.printf("Элемент %d: %s%n", secondIndex, second);

    if (first.equals(second)) {
      System.out.println("Результат: объекты РАВНЫ (equals = true).");
    } else {
      System.out.println("Результат: объекты НЕ РАВНЫ (equals = false).");
    }
  }

  /**
   * Считывает данные для создания объекта {@link Vehicle}.
   *
   * @param reader объект для чтения ввода
   * @return новый объект {@link Vehicle}
   */
  private static Vehicle readVehicle(InputReader reader) {
    System.out.println("Транспортное средство");
    String name = reader.readString("Название", 2, 100);
    int maxSpeed = reader.readInt("Макс. скорость (км/ч)",
        Vehicle.MIN_SPEED, Vehicle.MAX_SPEED);
    int year = reader.readInt("Год выпуска", Vehicle.MIN_YEAR, Vehicle.MAX_YEAR);
    return new Vehicle(name, maxSpeed, year);
  }

  /**
   * Считывает данные для создания объекта {@link Car}.
   *
   * @param reader объект для чтения ввода
   * @return новый объект {@link Car}
   */
  private static Car readCar(InputReader reader) {
    System.out.println("Автомобиль");
    String name = reader.readString("Название/модель", 2, 100);
    int maxSpeed = reader.readInt("Макс. скорость (км/ч)",
        Vehicle.MIN_SPEED, Vehicle.MAX_SPEED);
    int year = reader.readInt("Год выпуска", Vehicle.MIN_YEAR, Vehicle.MAX_YEAR);
    String bodyType = reader.readString("Тип кузова", 2, 50);
    int enginePower = reader.readInt("Мощность двигателя (л.с.)",
        Car.MIN_POWER, Car.MAX_POWER);
    int doorsCount = reader.readInt("Количество дверей",
        Car.MIN_DOORS, Car.MAX_DOORS);
    return new Car(name, maxSpeed, year, bodyType, enginePower, doorsCount);
  }

  /**
   * Считывает данные для создания объекта {@link Train}.
   *
   * @param reader объект для чтения ввода
   * @return новый объект {@link Train}
   */
  private static Train readTrain(InputReader reader) {
    System.out.println("Поезд");
    String name = reader.readString("Название", 2, 100);
    int maxSpeed = reader.readInt("Макс. скорость (км/ч)",
        Vehicle.MIN_SPEED, Vehicle.MAX_SPEED);
    int year = reader.readInt("Год выпуска", Vehicle.MIN_YEAR, Vehicle.MAX_YEAR);
    String route = reader.readString("Маршрут", 2, 100);
    int wagonsCount = reader.readInt("Количество вагонов",
        Train.MIN_WAGONS, Train.MAX_WAGONS);
    String tractionType = reader.readString("Тип тяги", 2, 50);
    return new Train(name, maxSpeed, year, route, wagonsCount, tractionType);
  }

  /**
   * Считывает данные для создания объекта {@link Express}.
   *
   * @param reader объект для чтения ввода
   * @return новый объект {@link Express}
   */
  private static Express readExpress(InputReader reader) {
    System.out.println("Экспресс");
    String name = reader.readString("Название", 2, 100);
    int maxSpeed = reader.readInt("Макс. скорость (км/ч)",
        Vehicle.MIN_SPEED, Vehicle.MAX_SPEED);
    int year = reader.readInt("Год выпуска", Vehicle.MIN_YEAR, Vehicle.MAX_YEAR);
    String route = reader.readString("Маршрут", 2, 100);
    int wagonsCount = reader.readInt("Количество вагонов",
        Train.MIN_WAGONS, Train.MAX_WAGONS);
    String tractionType = reader.readString("Тип тяги", 2, 50);
    String serviceClass = reader.readString("Класс обслуживания", 2, 50);
    double ticketPrice = reader.readDouble("Цена билета (руб.)",
        Express.MIN_TICKET_PRICE, Express.MAX_TICKET_PRICE);
    boolean highSpeed = reader.readBoolean("Высокоскоростной режим");
    return new Express(name, maxSpeed, year, route, wagonsCount, tractionType,
        serviceClass, ticketPrice, highSpeed);
  }

  /**
   * Выводит главное меню в консоль.
   */
  private static void printMenu() {
    System.out.println("Выберите действие:");
    for (int i = 0; i < MENU_ITEMS.length; i++) {
      System.out.printf("%d. %s%n", i + 1, MENU_ITEMS[i]);
    }
    System.out.print("Ваш выбор: ");
  }

  /**
   * Запрашивает у пользователя корректный индекс элемента в коллекции.
   *
   * @param vehicles коллекция транспортных средств
   * @param reader объект для чтения ввода
   * @return корректный индекс
   */
  private static int readIndex(List<Vehicle> vehicles, InputReader reader) {
    return reader.readInt(
        String.format("Индекс (0–%d)", vehicles.size() - 1),
        0,
        vehicles.size() - 1);
  }
}