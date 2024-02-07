import java.util.HashMap; // Хэштаблица вместо массива для удобства
import java.util.Map;
import java.util.Scanner; // Для вывода в консоль, с возможностью ввода

public class Calculator {
    // Словарь для хранения соответствия римских чисел и их арабских значений
    private static final Map<String, Integer> romanNumerals = new HashMap<>();
    static {
        // Инициализация словаря
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Запрос ввода пользователем выражения
            System.out.print("Введите выражение: ");
            String input = scanner.nextLine().trim();

            // Переменные для хранения операндов и оператора
            String operand1 = "";
            String operator = "";
            String operand2 = "";

            // Цикл разбора введенного выражения на операнды и оператор
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    operator += ch;
                } else {
                    if (operator.isEmpty()) {
                        operand1 += ch;
                    } else {
                        operand2 += ch;
                    }
                }
            }

            // Проверка на корректность введенных данных
            if (!isValidInput(operand1, operand2, operator))
                throw new IllegalArgumentException("Invalid input");

            // Преобразование операндов в числа
            int num1 = getNumber(operand1);
            int num2 = getNumber(operand2);

            // Выполнение операции
            int result = calculate(num1, num2, operator);

            // Вывод результата
            if (isRoman(operand1, operand2))
                System.out.println(toRoman(result));
            else
                System.out.println(result);
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
    }

    // Метод для проверки корректности введенных данных
    private static boolean isValidInput(String operand1, String operand2, String operator) {
        return isValidOperand(operand1) && isValidOperand(operand2) && isValidOperator(operator)
                && isSameType(operand1, operand2);
    }

    // Метод для проверки корректности операнда
    private static boolean isValidOperand(String operand) {
        return romanNumerals.containsKey(operand) || operand.matches("\\d+") && Integer.parseInt(operand) >= 1 && Integer.parseInt(operand) <= 10;
    }

    // Метод для проверки корректности оператора
    private static boolean isValidOperator(String operator) {
        return operator.matches("[+\\-*/]") && operator.length() == 1;
    }

    // Метод для проверки совместимости типов операндов
    private static boolean isSameType(String operand1, String operand2) {
        return (romanNumerals.containsKey(operand1) && romanNumerals.containsKey(operand2))
                || (operand1.matches("\\d+") && operand2.matches("\\d+"));
    }

    // Метод для определения типа операндов (римские или арабские числа)
    private static boolean isRoman(String operand1, String operand2) {
        return romanNumerals.containsKey(operand1) && romanNumerals.containsKey(operand2);
    }

    // Метод для получения численного значения операнда
    private static int getNumber(String operand) {
        if (romanNumerals.containsKey(operand))
            return romanNumerals.get(operand);
        else
            return Integer.parseInt(operand);
    }

    // Метод для выполнения операции
    private static int calculate(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    // Метод для преобразования числа в римскую систему счисления
    private static String toRoman(int number) {
        if (number < 1)
            throw new IllegalArgumentException("Roman numbers cannot represent non-positive numbers");

        // Переменная для хранения результирующей строки
        StringBuilder sb = new StringBuilder();

        // Перебор римских чисел в обратном порядке
        for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
            // Пока число больше или равно значению римского числа
            while (number >= entry.getValue()) {
                // Добавляем римское число к результирующей строке
                sb.append(entry.getKey());
                // Вычитаем значение римского числа из числа
                number -= entry.getValue();
            }
        }

        // Оптимизация римской строки (упрощение)
        return optimizeRoman(sb.toString());
    }

    // Метод для оптимизации римской строки
    private static String optimizeRoman(String roman) {
        // Правила оптимизации римских чисел
        // "IIIII" -> "V"
        roman = roman.replace("IIIII", "V");
        // "VV" -> "X"
        roman = roman.replace("VV", "X");
        // "IIII" -> "IV"
        roman = roman.replace("IIII", "IV");
        // "VIV" -> "IX"
        roman = roman.replace("VIV", "IX");


        // Возвращаем оптимизированную римскую строку
        return roman;
    }
}
