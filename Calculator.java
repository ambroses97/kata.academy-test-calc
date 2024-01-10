import java.util.*;

public class Calculator {

    private static final Map<Integer, String> ARABIC_TO_ROMAN = new HashMap<Integer, String>() {
        {
            put(1, "I");
            put(2, "II");
            put(3, "III");
            put(4, "IV");
            put(5, "V");
            put(6, "VI");
            put(7, "VII");
            put(8, "VIII");
            put(9, "IX");
            put(10, "X");
            put(11, "XI");
            put(12, "XII");
            put(13, "XIII");
            put(14, "XIV");
            put(15, "XV");
            put(16, "XVI");
            put(17, "XVII");
            put(18, "XVIII");
            put(19, "XIX");
            put(20, "XX");
            put(21, "XXI");
            put(24, "XXIV");
            put(25, "XXV");
            put(27, "XXVII");
            put(28, "XXVIII");
            put(30, "XXX");
            put(32, "XXXII");
            put(35, "XXXV");
            put(36, "XXXVI");
            put(40, "XL");
            put(42, "XLII");
            put(45, "XLV");
            put(48, "XLVIII");
            put(49, "XLIX");
            put(50, "L");
            put(54, "LIV");
            put(56, "LVI");
            put(60, "LX");
            put(63, "LXIII");
            put(64, "LXIV");
            put(70, "LXX");
            put(72, "LXXII");
            put(80, "LXXX");
            put(81, "LXXXI");
            put(90, "XC");
            put(100, "C");
        }
    };

    private static final Map<String, Integer> ROMAN_TO_ARABIC = new HashMap<String, Integer>() {
        {
            put("I", 1);
            put("II", 2);
            put("III", 3);
            put("IV", 4);
            put("V", 5);
            put("VI", 6);
            put("VII", 7);
            put("VIII", 8);
            put("IX", 9);
            put("X", 10);
            put("XI", 11);
            put("XII", 12);
            put("XIII", 13);
            put("XIV", 14);
            put("XV", 15);
            put("XVI", 16);
            put("XVII", 17);
            put("XVIII", 18);
            put("XIX", 19);
            put("XX", 20);
            put("XXI", 21);
            put("XXIV", 24);
            put("XXV", 25);
            put("XXVII", 27);
            put("XXVIII", 28);
            put("XXX", 30);
            put("XXXII", 32);
            put("XXXV", 35);
            put("XXXVI", 36);
            put("XL", 40);
            put("XLII", 42);
            put("XLV", 45);
            put("XLVIII", 48);
            put("XLIX", 49);
            put("L", 50);
            put("LIV", 54);
            put("LVI", 56);
            put("LX", 60);
            put("LXIII", 63);
            put("LXIV", 64);
            put("LXX", 70);
            put("LXXII", 72);
            put("LXXX", 80);
            put("LXXXI", 81);
            put("XC", 90);
            put("C", 100);
        }
    };

    public static String calc(String input) {

        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException("превышено колличество передаваемых значений");
        }

        boolean isFirstRoman = isRoman(parts[0]);
        boolean isSecondRoman = isRoman(parts[2]);

        if (isFirstRoman != isSecondRoman) {
            throw new IllegalArgumentException("используются одновременно разные системы счисления");
        }

        boolean isRoman = isRoman(parts[0]);

        int a = parseNumber(parts[0]);
        int b = parseNumber(parts[2]);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new IllegalArgumentException("числа должны быть от 1 до 10");
        }

        int result;
        
        switch (parts[1]) {
            case "+":
                result = a + b;
                break;
            case "-":
                if (a - b < 1 && isRoman) {
                    throw new IllegalArgumentException("в римской системе счисления нет отрицательных чисе");
                }
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
            if (a / b < 1 && isRoman) {
                    throw new IllegalArgumentException("в римской системе счисления нет чисел меньше 1");
                }
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("неправильный оператор");
        }

        if (isRoman(parts[0]) && isRoman(parts[2])) {
            return toRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static int parseNumber(String s) {
        try {
            int num = Integer.parseInt(s);
            if (num < 1 || num > 10) {
                throw new IllegalArgumentException("числа должны быть от 1 до 10");
            }
            return num;
        } catch (Exception e) {
            if (!isRoman(s)) {
                throw new IllegalArgumentException("неверный формат чисел");
            }
            return romanToArabic(s);
        }
    }

    private static boolean isRoman(String s) {
        return s.matches("[IVXLC]+");
    }

    private static String toRoman(int n) {
        return ARABIC_TO_ROMAN.get(n);
    }

    private static int romanToArabic(String s) {
        if (ROMAN_TO_ARABIC.get(s) == null) {
            throw new IllegalArgumentException("числа должны быть от 1 до 10");
        }
        return ROMAN_TO_ARABIC.get(s);
    }

    public static void main(String[] args) {
        Scanner scanner = null;
        boolean isOn = true;
        try {
            scanner = new Scanner(System.in);
            while (isOn) {
                System.out.print("Введите арифметическое выражение или 'q' для выхода:\n");
                String input = scanner.nextLine();
                if ("q".equalsIgnoreCase(input)) {
                    break;
                }
                if (input.isEmpty()) {
                    System.err.println("Ошибка: вы ничего не ввели");
                    continue;
                }
                try {
                    String result = calc(input);
                    System.out.println(result);
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                    isOn = false;
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}