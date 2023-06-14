import java.util.Scanner;

public class RabinKarpAlgorithm {
    private static final int PRIME = 101; // Prime number used for hashing

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the text: ");
        String text = scanner.nextLine();

        System.out.print("Enter the pattern to search: ");
        String pattern = scanner.nextLine();

        boolean isHashingPossible = checkHashingPossibility(text, pattern);
        System.out.println("Is hashing possible? " + isHashingPossible);

        if (isHashingPossible) {
            int index = search(text, pattern);
            if (index == -1)
                System.out.println("Pattern not found in the text.");
            else
                System.out.println("Pattern found at index " + index);
        }

        scanner.close();
    }

    private static boolean checkHashingPossibility(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        if (patternLength > textLength)
            return false;

        long patternHash = calculateHash(pattern, patternLength);
        long textHash = calculateHash(text, patternLength);

        return patternHash == textHash;
    }

    private static int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        long patternHash = calculateHash(pattern, patternLength);
        long textHash = calculateHash(text, patternLength);

        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == textHash && checkEqual(text, i, i + patternLength - 1, pattern))
                return i;

            if (i < textLength - patternLength)
                textHash = recalculateHash(textHash, patternLength, text, i);
        }

        return -1;
    }

    private static long calculateHash(String str, int length) {
        long hash = 0;
        for (int i = 0; i < length; i++) {
            hash += str.charAt(i) * Math.pow(PRIME, i);
        }
        return hash;
    }

    private static long recalculateHash(long oldHash, int length, String str, int index) {
        long newHash = oldHash - str.charAt(index);
        newHash /= PRIME;
        newHash += str.charAt(index + length) * Math.pow(PRIME, length - 1);
        return newHash;
    }

    private static boolean checkEqual(String text, int start1, int end1, String pattern) {
        int start2 = 0;
        while (start1 <= end1) {
            if (text.charAt(start1) != pattern.charAt(start2))
                return false;
            start1++;
            start2++;
        }
        return true;
    }
}
