import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;


public class EncryptTextClass extends JFrame {
    public EncryptTextClass() {
        setTitle("Encrypt Text");

        JLabel welcome = new JLabel("Encrypt your text message", SwingConstants.CENTER);
        welcome.setFont(new Font("Serif", Font.PLAIN, 34));
        welcome.setForeground(Color.WHITE);
        welcome.setBackground(Color.BLACK);
        welcome.setOpaque(true);
        welcome.setBounds(0, 20, 700, 50);
        add(welcome);

        JButton Back = new JButton("BACK");
        Back.setBounds(10,30,90,30);
        Back.setFont(new Font("Serif", Font.BOLD, 17));
        Back.setBackground(Color.WHITE);
        Back.setForeground(Color.BLACK);
        Back.setFocusPainted(false);
        add(Back);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main m = new Main();
                setVisible(false);
            }
        });

        JLabel heading1 = new JLabel("Normal Text", SwingConstants.CENTER);
        heading1.setFont(new Font("Serif", Font.PLAIN, 17));
        heading1.setForeground(Color.WHITE);
        heading1.setBackground(Color.BLACK);
        heading1.setOpaque(true);
        heading1.setBounds(15,100,320,26);
        add(heading1);

        JTextArea NormalTextBox = new JTextArea();
        JScrollPane NTScrollPane = new JScrollPane(NormalTextBox);
        NTScrollPane.setBounds(15,126,320,240);
        NormalTextBox.setLineWrap(true);
        NormalTextBox.setWrapStyleWord(true);
        add(NTScrollPane);

        JLabel heading2 = new JLabel("Encrypted Text", SwingConstants.CENTER);
        heading2.setFont(new Font("Serif", Font.PLAIN, 17));
        heading2.setForeground(Color.WHITE);
        heading2.setBackground(Color.BLACK);
        heading2.setOpaque(true);
        heading2.setBounds(350,100,320,26);
        add(heading2);

        JTextArea EncryptedTextBox = new JTextArea();
        JScrollPane ETScrollPane = new JScrollPane(EncryptedTextBox);
        ETScrollPane.setBounds(350,126,320,240);
        EncryptedTextBox.setEditable(false);
        EncryptedTextBox.setLineWrap(true);
        EncryptedTextBox.setWrapStyleWord(true);
        add(ETScrollPane);

        JLabel Lpass = new JLabel(" Enter Password ");
        Lpass.setBounds(120,400,150,40);
        Lpass.setFont(new Font("Serif", Font.BOLD, 19));
        Lpass.setForeground(Color.WHITE);
        Lpass.setBackground(Color.BLACK);
        Lpass.setOpaque(true);
        add(Lpass);

        JPasswordField TextPassword = new JPasswordField();
        TextPassword.setBounds(320,400,260,40);
        TextPassword.setFont(new Font("Serif", Font.BOLD, 19));
        add(TextPassword);

        JButton Encrypt = new JButton("ENCRYPT");
        Encrypt.setBounds(50,470,160,40);
        Encrypt.setFont(new Font("Serif", Font.BOLD, 21));
        Encrypt.setBackground(Color.decode("#0000FF"));
        Encrypt.setForeground(Color.WHITE);
        Encrypt.setFocusPainted(false);
        add(Encrypt);
        Encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = NormalTextBox.getText();
                String p = new String(TextPassword.getPassword());
                if(Message.equals("") && p.length()==0){
                    JOptionPane.showMessageDialog(null,"Message and Password box is empty.");
                }else if(Message.equals("")){
                    JOptionPane.showMessageDialog(null,"Message box is empty.");
                }else if(p.length()==0){
                    JOptionPane.showMessageDialog(null,"Password box is empty");
                }
                try {
                    String encryptedText = encrypt(Message, p);
                    EncryptedTextBox.setText(encryptedText);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EncryptTextClass.this, "Encryption failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });


        JButton Reset = new JButton("RESET");
        Reset.setBounds(260,470,160,40);
        Reset.setFont(new Font("Serif", Font.BOLD, 21));
        Reset.setBackground(Color.decode("#0000FF"));
        Reset.setForeground(Color.WHITE);
        Reset.setFocusPainted(false);
        add(Reset);
        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NormalTextBox.setText("");
                EncryptedTextBox.setText("");
            }
        });

        JButton Copy = new JButton("COPY");
        Copy.setBounds(471,470,160,40);
        Copy.setFont(new Font("Serif", Font.BOLD, 21));
        Copy.setBackground(Color.decode("#0000FF"));
        Copy.setForeground(Color.WHITE);
        Copy.setFocusPainted(false);
        add(Copy);
        Copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = EncryptedTextBox.getText();
                StringSelection selection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(null,"Message Copied");
            }
        });


        setSize(700, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#fde61e"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private static String encrypt(String text, String password) throws Exception {
        byte[] salt = new byte[16];

        int iterationCount = 10000;
        int keyLength = 128;

        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

        SecretKeySpec keySpecAES = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpecAES);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

}
