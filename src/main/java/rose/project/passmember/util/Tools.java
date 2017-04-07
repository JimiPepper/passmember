package rose.project.passmember.util;

import rose.project.passmember.util.entry.PasswordEntry;

import java.util.List;
import java.util.Random;

/**
 * Created by Lord Rose on 30/03/2017.
 */
public class Tools {
    public static String randomPassword(TypePassword type, boolean capitalize, int passwordLength) {
        char authorizedChar[] = getAuthorizedCharsPassword(type);
        int arrayLength = authorizedChar.length;
        Random rand = new Random();
        char currentChar;

        /* LOOP TO GENERATE RANDOM CHARACTER */
        StringBuilder buffer = new StringBuilder(passwordLength);
        for (int i = 0 ; i < passwordLength ; i++) {
            currentChar = authorizedChar[rand.nextInt(arrayLength - 1)];

            /* TEST CAPITALIZATION */
            if(capitalize && Character.isLetter(currentChar) && rand.nextBoolean()) {
                buffer.append(Character.toUpperCase(currentChar));
            }
            else {
                buffer.append(currentChar);
            }
        }

        return buffer.toString();
    }

    private static char[] getAuthorizedCharsPassword(TypePassword type) {
        String authorizedChars;

        switch (type) {
            case ALPHA:
                authorizedChars = "abcdefghijklmnopqrstuvwxyz";
                break;
            case NUMERIC:
                authorizedChars = "0123456789";
                break;
            case SPECIAL:
                authorizedChars = " !\"#$%&'()*+,-./:;<?@[\\]^_`{|}~>=°";
                break;
            case ALPHANUM:
                authorizedChars = new String(getAuthorizedCharsPassword(TypePassword.ALPHA)) + new String(getAuthorizedCharsPassword(TypePassword.NUMERIC));
                break;
            case ALPHASPEC:
                authorizedChars = new String(getAuthorizedCharsPassword(TypePassword.ALPHA)) + new String(getAuthorizedCharsPassword(TypePassword.SPECIAL));
                break;
            case NUMSPEC:
                authorizedChars = new String(getAuthorizedCharsPassword(TypePassword.NUMERIC)) + new String(getAuthorizedCharsPassword(TypePassword.SPECIAL));
                break;
            default:
                authorizedChars = new String(getAuthorizedCharsPassword(TypePassword.ALPHA)) + new String(getAuthorizedCharsPassword(TypePassword.NUMERIC)) + new String(getAuthorizedCharsPassword(TypePassword.SPECIAL));
        }

        return authorizedChars.toCharArray();
    }

    private static void debug(List<PasswordEntry> listSavedPasswords) {
        System.out.println("*** DEBUG ***");

        for(PasswordEntry password : listSavedPasswords) {
            System.out.println("Title : "+ password.title);
            System.out.println("Login : "+ password.login);
            System.out.println("Password : "+ password.password);
            System.out.println();
        }
    }
}
