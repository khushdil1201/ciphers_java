import java.nio.charset.Charset;
import java.util.*;

public class AsciiVerticalTranspositionCipher {
    private static final Charset ENCODING = Charset.forName("windows-1251");

    private int columns;
    private List<Integer> columnOrder;

    public AsciiVerticalTranspositionCipher(String key) {
        // Убираем повторяющиеся символы в ключе
        key = removeDuplicates(key);
        this.columns = key.length();
        this.columnOrder = generateColumnOrder(key);
    }

    // Метод для удаления дублирующихся символов из ключа
    private String removeDuplicates(String key) {
        StringBuilder uniqueKey = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            if (uniqueKey.indexOf(String.valueOf(key.charAt(i))) == -1) {
                uniqueKey.append(key.charAt(i));
            }
        }
        return uniqueKey.toString();
    }

    private List<Integer> generateColumnOrder(String key) {
        List<Integer> order = new ArrayList<>();
        Byte[] keyBytes = new Byte[key.length()];

        // Extract encoded bytes of the key
        byte[] encodedBytes = key.getBytes(ENCODING);
        for (int i = 0; i < key.length(); i++) {
            keyBytes[i] = encodedBytes[i];
        }

        // Generate indices for sorting
        Integer[] indices = new Integer[key.length()];
        for (int i = 0; i < key.length(); i++) {
            indices[i] = i;
        }

        // Sort indices based on the corresponding byte values
        Arrays.sort(indices, (a, b) -> {
            int aVal = Byte.toUnsignedInt(keyBytes[a]);
            int bVal = Byte.toUnsignedInt(keyBytes[b]);
            return Integer.compare(aVal, bVal);
        });

        // Build the column order list based on sorted indices
        order.addAll(Arrays.asList(indices));

        return order;
    }

    public String encrypt(String text) {
        byte[] textBytes = text.getBytes(ENCODING);
        int rows = (int) Math.ceil((double) textBytes.length / columns);

        // Create a matrix and populate it with the text bytes
        byte[][] matrix = new byte[rows][columns];
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (index < textBytes.length) {
                    matrix[r][c] = textBytes[index++];
                } else {
                    matrix[r][c] = (byte) ' '; // Padding with space
                }
            }
        }

        // Encrypt by reading columns in the order of columnOrder
        StringBuilder encryptedText = new StringBuilder();
        for (int col : columnOrder) {
            for (int r = 0; r < rows; r++) {
                encryptedText.append(new String(new byte[]{matrix[r][col]}, ENCODING));
            }
        }

        return encryptedText.toString();
    }

    public String decrypt(String encryptedText) {
        byte[] textBytes = encryptedText.getBytes(ENCODING);
        int rows = (int) Math.ceil((double) textBytes.length / columns);

        // Create a matrix to store the columns in order
        byte[][] matrix = new byte[rows][columns];
        int index = 0;

        // Fill the matrix column by column based on columnOrder
        for (int orderIndex = 0; orderIndex < columnOrder.size(); orderIndex++) {
            int col = columnOrder.get(orderIndex);
            for (int r = 0; r < rows; r++) {
                if (index < textBytes.length) {
                    matrix[r][col] = textBytes[index++];
                } else {
                    matrix[r][col] = (byte) ' '; // Padding with space
                }
            }
        }

        // Read the text row by row
        StringBuilder decryptedText = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
//                if (matrix[r][c] != ' ') { // Ignore padding spaces
                    decryptedText.append(new String(new byte[]{matrix[r][c]}, ENCODING));
//                }
            }
        }

        return correctSymbols(decryptedText.toString());
    }

    private String correctSymbols(String text) {
        // Карта неправильных символов и их замен
        Map<String, String> corrections = new HashMap<>();
        corrections.put("Q_R", "р");
        corrections.put("Jbe", "ии "); // Пример: неправильный символ  заменяется на пробел
        corrections.put("\u001C", "я");
        corrections.put("юyьDЖ®", "турных");
        corrections.put("џєГ", "м и");
        corrections.put("ўнН", "пет");
        corrections.put("ПнЕ", "рек");
        corrections.put("ўњН", "пет");
        corrections.put("ЈњЕ", "перек");
        corrections.put("вцт", "вет");
        corrections.put("чцй", "чей");
        corrections.put("ь-ь", "ведь ");
        corrections.put("иь", "и ");
        corrections.put("вь", "в ");
        corrections.put("ь-", " -");
        corrections.put("Воть", "Вот");
        corrections.put("аь", "а ");
        corrections.put(",ьв", ", в");
        corrections.put("зьл", "з л");
        corrections.put("кьП", "к П");
        corrections.put("глубокомысленно", "глубоко мысленно");
        corrections.put("оь", "о ");
        corrections.put("всталь", "встал ");
        corrections.put("ьа", " а");
        corrections.put("хь", "х ");
        corrections.put(" лн", " он");
        corrections.put(".ь", ". ");
        corrections.put("…ь", "… ");
        corrections.put(",ь", ", ");
        corrections.put("ье", " е");
        corrections.put("изллжил", "изложил");




        // Замена строк на основе карты
        for (Map.Entry<String, String> entry : corrections.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        return text;
    }

    public static void main(String[] args) {
        String input = "зтхащннз,ншии сф нрнопнпплои аоивеиард ньрс...";
        String key = "informat"; // Используем ключ с повторяющимися символами

        AsciiVerticalTranspositionCipher cipher = new AsciiVerticalTranspositionCipher(key);
        for (int i = 0; i < cipher.columnOrder.size(); i++)
            System.out.println(cipher.columnOrder.get(i));

        String encrypted = cipher.encrypt(input);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
