package com.greenatom.utils.generator.request;

public class TextUtils {

    private TextUtils() {}

    private static final String[] UNITS = {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять"};
    private static final String[] TENS = {"", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
    private static final String[] TEENS = {"десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
    private static final String[] THOUSANDS = {"", "тысяча", "тысячи", "тысяч"};
    private static final String[] MILLIONS = {"", "миллион", "миллиона", "миллионов"};

    public static String intToText(int cost) {
        if (cost == 0) {
            return "ноль";
        }

        StringBuilder result = new StringBuilder();

        int million = cost / 1000000;
        if (million > 0) {
            result.append(numToText(million, 1)).append(" ").append(MILLIONS[getForm(million)]);
            cost %= 1000000;
            if (cost > 0) {
                result.append(" ");
            }
        }

        int thousand = cost / 1000;
        if (thousand > 0) {
            result.append(numToText(thousand, 2)).append(" ").append(THOUSANDS[getForm(thousand)]);
            cost %= 1000;
            if (cost > 0) {
                result.append(" ");
            }
        }

        if (cost > 0) {
            result.append(numToText(cost, 3));
        }

        return result.toString();
    }

    private static String numToText(int num, int form) {
        StringBuilder result = new StringBuilder();

        int hundred = num / 100;
        if (hundred > 0) {
            result.append(UNITS[hundred]).append("сот");
            num %= 100;
            if (num > 0) {
                result.append(" ");
            }
        }

        int ten = num / 10;
        if (ten >= 2) {
            result.append(TENS[ten]);
            num %= 10;
            if (num > 0) {
                result.append(" ");
            }
        } else if (ten == 1) {
            result.append(TEENS[num % 10]);
            return result.toString();
        }

        if (num > 0) {
            if (form == 2 && num == 1) {
                result.append("одна");
            } else if (form == 2 && num == 2) {
                result.append("две");
            } else {
                result.append(UNITS[num]);
            }
        }

        return result.toString();
    }

    private static int getForm(int num) {
        if (num % 100 >= 11 && num % 100 <= 19) {
            return 3;
        } else {
            return switch (num % 10) {
                case 1 -> 1;
                case 2, 3, 4 -> 2;
                default -> 3;
            };
        }
    }

}