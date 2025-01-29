package org.example;

import java.nio.charset.Charset;
import java.util.Scanner;

public class test {
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding

    private static int bytetoindex(byte b) {
        int value = Byte.toUnsignedInt(b); // Convert the byte to an unsigned integer
        if(value==9) return 1;
        else if(value==10) return 2;
        else if(value==13) return 3;
        else return value-28;// Example transformation
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, ENCODING);

        System.out.println("Enter text (including tabs):");
        String input = scanner.nextLine(); // Read input from the console

        // Encode the string into bytes using Windows-1251 encoding
        byte[] bytes = input.getBytes(ENCODING);

        // Process each byte
        for (byte aByte : bytes) {
            int tt = bytetoindex(aByte);
            System.out.println(aByte + " --- " + tt);
        }

        scanner.close(); // Close the scanner
    }
}
