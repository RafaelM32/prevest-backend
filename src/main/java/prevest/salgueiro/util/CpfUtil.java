package prevest.salgueiro.util;

public class CpfUtil {




    public static String limparCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll(".", "");
        cpfLimpo = cpfLimpo.replaceAll("-", "");
        return cpfLimpo;
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(cpf.charAt(i));
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            int checkDigit1 = (sum1 * 10) % 11;
            checkDigit1 = (checkDigit1 == 10) ? 0 : checkDigit1;

            int checkDigit2 = ((sum2 + checkDigit1 * 2) * 10) % 11;
            checkDigit2 = (checkDigit2 == 10) ? 0 : checkDigit2;

            return checkDigit1 == Character.getNumericValue(cpf.charAt(9)) &&
                   checkDigit2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
