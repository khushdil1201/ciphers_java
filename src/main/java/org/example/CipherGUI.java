import org.example.AlgebraOfMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CipherGUI {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Ciphers");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel inputLabel = new JLabel("Input Text:");
        JTextArea inputArea = new JTextArea();
        JLabel shiftOrKeyLabel = new JLabel("Key:");
        shiftOrKeyLabel.setBackground(Color.gray);
        shiftOrKeyLabel.setOpaque(true); // Делает фон видимым

        JTextField shiftOrKeyField = new JTextField();
        JComboBox<String> cipherType = new JComboBox<>(new String[]{"Caesar", "Vigenere", "PlayFair","Vertical Transposition","Hill","Algebra of Matrix"});
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        JComboBox<String> codingType=new JComboBox<>(new String[]{"ASCII","UNICODE"});
//        JLabel rowcount=new JLabel("rows:");
//        JLabel colcount=new JLabel("columns:");
//        JTextField rows=new JTextField();
//        JTextField cols=new JTextField();




        // Layout setup
        JPanel panel = new JPanel();
        Color backgroundColor = new Color(34, 40, 49);
        panel.setBackground(backgroundColor);
        panel.setLayout(null);

//        inputLabel.setBounds(50, 30, 100, 25);
        inputScroll.setBounds(10, 10, 1165, 250);
        outputScroll.setBounds(10, 300, 1165, 250);
        shiftOrKeyLabel.setBounds(100, 580, 100, 25);
        shiftOrKeyField.setBounds(200, 580, 400, 25);
        cipherType.setBounds(100, 630, 150, 30);
        codingType.setBounds(100, 670, 150, 30);
        encryptButton.setBounds(450, 630, 150, 30);
        decryptButton.setBounds(450, 670, 150, 30);

//        rowcount.setBounds(765,390,60,25);
//        rows.setBounds(800, 390, 50, 25);
//        colcount.setBounds(900,390,60,25);
//        cols.setBounds(960,390,50,25);


//        panel.add(inputLabel);
        panel.add(inputScroll);
        panel.add(shiftOrKeyLabel);
        panel.add(shiftOrKeyField);
        panel.add(cipherType);
        panel.add(codingType);
        panel.add(encryptButton);
        panel.add(decryptButton);
//        panel.add(rowcount);
//        panel.add(rows);
//        panel.add(colcount);
//        panel.add(cols);
        panel.add(outputScroll);


        frame.add(panel);

        // Button actions
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = inputArea.getText();
                    String keyOrShift = shiftOrKeyField.getText();
                    String selectedCipher = (String) cipherType.getSelectedItem();
                    String selectedCoding = (String) codingType.getSelectedItem();
                    int row=1;
                    int col=1;


                    if (selectedCoding.equals("ASCII")) {
                        if (selectedCipher.equals("Caesar")) {
                            int shift = Integer.parseInt(keyOrShift);
                            String encrypted = AsciiCaesarCipher.encrypt(input, shift);
                            outputArea.setText(encrypted);
                        } else if (selectedCipher.equals("Vigenere")) {
                            String encrypted = AsciiVigenereCipher.encrypt(input, keyOrShift);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
                            AsciiPlayfairCipher cipher = new AsciiPlayfairCipher(keyOrShift);
                            String ciphertext = cipher.encrypt(input);
                            outputArea.setText(ciphertext);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
//                            String input = "Привет, мир! Это тестовое сообщение.";
//                            String key = "ключ"; // Encryption key

                            AsciiVerticalTranspositionCipher cipher = new AsciiVerticalTranspositionCipher(keyOrShift);
                            String encrypted = cipher.encrypt(input);
                            outputArea.setText(encrypted);

//                            String decrypted = cipher.decrypt(encrypted);
//                            System.out.println("Decrypted: " + decrypted);
                        }
                        else if(selectedCipher.equals("Algebra of Matrix"))
                        {
                            AlgebraOfMatrix aom=new AlgebraOfMatrix(keyOrShift);
                            String encrypted=aom.encrypt(input);
                            outputArea.setText(encrypted);

                            String decrypted = aom.decrypt(encrypted);
//                          System.out.println("Decrypted: " + decrypted);
                        }
                        else if(selectedCipher.equals("Hill"))
                        {

//                            String key = "КЛЮЧ123"; // Пример ключа
                            HillCipher hillCipher = new HillCipher(keyOrShift);

//                            String message = input;
                            String encrypted = hillCipher.encrypt(input);
                            //String decrypted = hillCipher.decrypt(encrypted);

//                            System.out.println("Исходное сообщение: " + message);
                            outputArea.setText(encrypted);
//                            System.out.println("Расшифрованное сообщение: " + decrypted);
                        }
                    }
//                    else if(selectedCipher.equals("Algebra Matrix"))
//                    {
//
////                            String key = "КЛЮЧ123"; // Пример ключа
//                        AlgebraMatrix hillCipher2 = new AlgebraMatrix(keyOrShift);
//
//
////                            String message = input;
//                        String encrypted = hillCipher2.encrypt(input);
//                        //String decrypted = hillCipher.decrypt(encrypted);
//
////                            System.out.println("Исходное сообщение: " + message);
//                        outputArea.setText(encrypted);
////                            System.out.println("Расшифрованное сообщение: " + decrypted);
//
//                    }
                    else if(selectedCoding.equals("UNICODE")){
                        if(selectedCipher.equals("Caesar"))
                        {
                            int shift=Integer.parseInt(keyOrShift);
                            String encrypted= UnicodeCaesarCipher.encrypt(input,shift);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("Vigenere"))
                        {
                            String encrypted = UnicodeVigenereCipher.encrypt(input, keyOrShift);
                            outputArea.setText(encrypted);
                        }

                        else if(selectedCipher.equals("PlayFair"))
                        {
                            if(row*col==65504){
                            UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift,row,col);
                            String encrypted = cipher.encrypt(input,row,col);
                            outputArea.setText(encrypted);
                            }
                            else JOptionPane.showMessageDialog(frame, "Rows * Columns is not 65504", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
                            UnicodeVerticalTranspositionCipher cipher = new UnicodeVerticalTranspositionCipher(keyOrShift);
                            String encrypted = cipher.encrypt(input);
                            outputArea.setText(encrypted);
                        }
                        }
                    }

                    catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Shift value must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = inputArea.getText();
                    String keyOrShift = shiftOrKeyField.getText();
                    String selectedCipher = (String) cipherType.getSelectedItem();
                    String selectedCoding = (String) codingType.getSelectedItem();
                    int row=1;
                    int col=1;
                    if (selectedCoding.equals("ASCII")) {
                        if (selectedCipher.equals("Caesar")) {
                            int shift = Integer.parseInt(keyOrShift);
                            String decrypted = AsciiCaesarCipher.decrypt(input, shift);
                            outputArea.setText(decrypted);
                        } else if (selectedCipher.equals("Vigenere")) {
                            String decrypted = AsciiVigenereCipher.decrypt(input, keyOrShift);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
                            AsciiPlayfairCipher cipher = new AsciiPlayfairCipher(keyOrShift);
                            String decryptedText = cipher.decrypt(input);
                            outputArea.setText(decryptedText);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
                            AsciiVerticalTranspositionCipher cipher = new AsciiVerticalTranspositionCipher(keyOrShift);
//                            String encrypted = cipher.encrypt(input);
//                            System.out.println(encrypted);

                            String decrypted = cipher.decrypt(input);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Algebra of Matrix"))
                        {
                            AlgebraOfMatrix aom=new AlgebraOfMatrix(keyOrShift);
                            String decrypted=aom.decrypt(input);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Hill"))
                        {
//                            String key = "КЛЮЧ123"; // Пример ключа
                            HillCipher hillCipher = new HillCipher(keyOrShift);

//                            String message = input;
//                            String encrypted = hillCipher.encrypt(input);
                            String decrypted = hillCipher.decrypt(input);

//                            System.out.println("Исходное сообщение: " + message);
                            outputArea.setText(decrypted);
//                            System.out.println("Расшифрованное сообщение: " + decrypted);
                        }
                    }
                    else if(selectedCoding.equals("UNICODE"))
                    {
                        if(selectedCipher.equals("Caesar"))
                        {
                            int shift=Integer.parseInt(keyOrShift);
                            String decrypted= UnicodeCaesarCipher.decrypt(input,shift);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Vigenere"))
                        {
                            String decrypted = UnicodeVigenereCipher.decrypt(input, keyOrShift);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
                                if(row*col==65504){
                                UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift,row,col);
    //                            UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift);
                                String decrypted = cipher.decrypt(input,row,col);
                                outputArea.setText(decrypted);
                            } else JOptionPane.showMessageDialog(frame, "Rows * Columns is not 65504", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
                            UnicodeVerticalTranspositionCipher cipher = new UnicodeVerticalTranspositionCipher(keyOrShift);
                            String decrypted = cipher.decrypt(input);
                            outputArea.setText(decrypted);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Shift value must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show frame
        frame.setVisible(true);
    }
}
