import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class DecodeImageClass extends JFrame {
    JLabel imageLabel;
    JTextArea message;
    BufferedImage image;

    public DecodeImageClass() {
        setTitle("Decode Message with Image");

        JLabel welcome = new JLabel("Decode message with image", SwingConstants.CENTER);
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


        JButton open = new JButton("Open");
        open.setBounds(50, 411, 160, 40);
        open.setFont(new Font("Serif", Font.BOLD, 21));
        open.setBackground(Color.decode("#0000FF"));
        open.setForeground(Color.WHITE);
        open.setFocusPainted(false);
        add(open);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
            }
        });


        JButton reset = new JButton("Reset");
        reset.setBounds(260, 411, 160, 40);
        reset.setFont(new Font("Serif", Font.BOLD, 21));
        reset.setBackground(Color.decode("#0000FF"));
        reset.setForeground(Color.WHITE);
        reset.setFocusPainted(false);
        add(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageLabel.setIcon(null);
                message.setText("");
            }
        });


        JButton decode = new JButton("DECODE");
        decode.setBounds(471, 411, 160, 40);
        decode.setFont(new Font("Serif", Font.BOLD, 21));
        decode.setBackground(Color.decode("#0000FF"));
        decode.setForeground(Color.WHITE);
        decode.setFocusPainted(false);
        add(decode);
        decode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decodeMessage();
            }
        });


        JLabel heading1 = new JLabel("Encoded Image", SwingConstants.CENTER);
        heading1.setFont(new Font("Serif", Font.PLAIN, 17));
        heading1.setForeground(Color.WHITE);
        heading1.setBackground(Color.BLACK);
        heading1.setOpaque(true);
        heading1.setBounds(15,100,320,26);
        add(heading1);

        imageLabel = new JLabel();
        JPanel EImage = new JPanel();
        EImage.setBounds(15, 126, 320, 240);
        EImage.add(imageLabel);
        EImage.setBackground(Color.DARK_GRAY);
        add(EImage);


        JLabel heading2 = new JLabel("Decoded Message", SwingConstants.CENTER);
        heading2.setFont(new Font("Serif", Font.PLAIN, 17));
        heading2.setForeground(Color.WHITE);
        heading2.setBackground(Color.BLACK);
        heading2.setOpaque(true);
        heading2.setBounds(350, 100, 320, 26);
        add(heading2);

        message = new JTextArea();
        JScrollPane MScrollPane = new JScrollPane(message);
        MScrollPane.setBounds(350, 126, 320, 240);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        add(MScrollPane);


        setSize(700, 551);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#fde61e"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void decodeMessage() {
        if (image == null) {
            JOptionPane.showMessageDialog(null, "First open a picture");
            return;
        }
        int len = extractInteger(image, 0, 0);
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[i] = extractByte(image, i * 8 + 32, 0);
        }
        message.setText(new String(b));
    }

    private int extractInteger(BufferedImage img, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(), startX = start / maxY, startY = start - startX * maxY, count = 0;
        int length = 0;
        for (int i = startX; i < maxX && count < 32; i++) {
            for (int j = startY; j < maxY && count < 32; j++) {
                int rgb = img.getRGB(i, j);
                int bit = getBitValue(rgb, storageBit);
                length = setBitValue(length, count, bit);
                count++;
            }
        }
        return length;
    }

    private byte extractByte(BufferedImage img, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(), startX = start / maxY, startY = start - startX * maxY, count = 0;
        byte b = 0;
        for (int i = startX; i < maxX && count < 8; i++) {
            for (int j = startY; j < maxY && count < 8; j++) {
                int rgb = img.getRGB(i, j);
                int bit = getBitValue(rgb, storageBit);
                b = (byte) setBitValue(b, count, bit);
                count++;
            }
        }
        return b;
    }

    private int getBitValue(int n, int location) {
        int v = n & (int) Math.round(Math.pow(2, location));
        return v == 0 ? 0 : 1;
    }

    private int setBitValue(int n, int location, int bit) {
        int toggle = (int) Math.pow(2, location);
        int bv = getBitValue(n, location);
        if (bv == bit) return n;
        if (bv == 0 && bit == 1) n |= toggle;
        else if (bv == 1 && bit == 0) n ^= toggle;
        return n;
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(DecodeImageClass.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(selectedFile);
                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(320, 240, Image.SCALE_DEFAULT));
                imageLabel.setIcon(imageIcon);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
