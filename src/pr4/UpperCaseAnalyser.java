package pr4;

/**
 * Класс для подсчета количества заглавных латинских символов в строке.
 */
public class UpperCaseAnalyser implements StringAnalyser {

  /** Лямбда-выражение для реализации логики подсчета. */
  private final StringAnalyser internalAnalyser = (str) -> {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c >= 'A' && c <= 'Z') {
        count++;
      }
    }
    return count;
  };

  @Override
  public int analyse(String str) {
    return internalAnalyser.analyse(str);
  }
}