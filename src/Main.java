import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public Main() {
        JFrame f = new JFrame("Steganography");

        JLabel welcome = new JLabel("Welcome to Steganography Project",SwingConstants.CENTER);
        welcome.setFont(new Font("Serif", Font.PLAIN, 34));
        welcome.setForeground(Color.WHITE);
        welcome.setBackground(Color.BLACK);
        welcome.setOpaque(true);
        welcome.setBounds(0,20,700,50);
        f.add(welcome);

        JButton EncryptText = new JButton("Encrypt Text");
        EncryptText.setBounds(90,150,200,50);
        EncryptText.setFont(new Font("Serif", Font.PLAIN, 18));
        EncryptText.setBackground(Color.decode("#0000FF"));
        EncryptText.setForeground(Color.WHITE);
        EncryptText.setFocusPainted(false);
        EncryptText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncryptTextClass encrypt = new EncryptTextClass();
                encrypt.setVisible(true);
                f.setVisible(false);
            }
        });
        f.add(EncryptText);

        JButton DecryptText = new JButton("Decrypt Text");
        DecryptText.setBounds(390,150,200,50);
        DecryptText.setFont(new Font("Serif", Font.PLAIN, 18));
        DecryptText.setBackground(Color.decode("#0000FF"));
        DecryptText.setForeground(Color.WHITE);
        DecryptText.setFocusPainted(false);
        DecryptText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DecryptTextClass decrypt = new DecryptTextClass();
                decrypt.setVisible(true);
                f.setVisible(false);
            }
        });
        f.add(DecryptText);

        JButton EncodeImage = new JButton("Encode Image");
        EncodeImage.setBounds(90,250,200,50);
        EncodeImage.setFont(new Font("Serif", Font.PLAIN, 18));
        EncodeImage.setBackground(Color.decode("#0000FF"));
        EncodeImage.setForeground(Color.WHITE);
        EncodeImage.setFocusPainted(false);
        EncodeImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncodeImageClass encodeImage = new EncodeImageClass();
                encodeImage.setVisible(true);
                f.setVisible(false);
            }
        });
        f.add(EncodeImage);

        JButton DecodeImage = new JButton("Decode Image");
        DecodeImage.setBounds(390,250,200,50);
        DecodeImage.setFont(new Font("Serif", Font.PLAIN, 18));
        DecodeImage.setBackground(Color.decode("#0000FF"));
        DecodeImage.setForeground(Color.WHITE);
        DecodeImage.setFocusPainted(false);
        DecodeImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DecodeImageClass decodeImage = new DecodeImageClass();
                decodeImage.setVisible(true);
                f.setVisible(false);
            }
        });
        f.add(DecodeImage);


        f.setSize(700,400);
        f.setResizable(false);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.decode("#fde61e"));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
