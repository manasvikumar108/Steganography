import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EncodeImageClass extends JFrame {
    JLabel normalImageLabel;
    JLabel encodedImageLabel;
    JTextArea messageTextArea;
    BufferedImage sourceImage = null;
    BufferedImage embeddedImage = null;

    public EncodeImageClass() {
        setTitle("Encrypt Text");

        JLabel welcome = new JLabel("Encode message with image", SwingConstants.CENTER);
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


        JLabel heading1 = new JLabel("Normal Image", SwingConstants.CENTER);
        heading1.setFont(new Font("Serif", Font.PLAIN, 17));
        heading1.setForeground(Color.WHITE);
        heading1.setBackground(Color.BLACK);
        heading1.setOpaque(true);
        heading1.setBounds(15,100,320,26);
        add(heading1);

        normalImageLabel = new JLabel();
        normalImageLabel.setBounds(15, 126, 320, 240);
        JPanel normalImagePanel = new JPanel();
        normalImagePanel.setBounds(15, 126, 320, 240);
        normalImagePanel.add(normalImageLabel);
        normalImagePanel.setBackground(Color.DARK_GRAY);
        add(normalImagePanel);


        JLabel heading2 = new JLabel("Encoded Image", SwingConstants.CENTER);
        heading2.setFont(new Font("Serif", Font.PLAIN, 17));
        heading2.setForeground(Color.WHITE);
        heading2.setBackground(Color.BLACK);
        heading2.setOpaque(true);
        heading2.setBounds(350,100,320,26);
        add(heading2);

        encodedImageLabel = new JLabel();
        encodedImageLabel.setBounds(350, 126, 320, 240);
        JPanel encodedImagePanel = new JPanel();
        encodedImagePanel.setBounds(350, 126, 320, 240);
        encodedImagePanel.add(encodedImageLabel);
        encodedImagePanel.setBackground(Color.DARK_GRAY);
        add(encodedImagePanel);


        JLabel TextM = new JLabel(" Enter Message ",SwingConstants.CENTER);
        TextM.setBounds(120,380,150,80);
        TextM.setFont(new Font("Serif", Font.BOLD, 19));
        TextM.setForeground(Color.WHITE);
        TextM.setBackground(Color.BLACK);
        TextM.setOpaque(true);
        add(TextM);

        messageTextArea = new JTextArea();
        messageTextArea.setBounds(280, 380, 280, 80);
        JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
        messageScrollPane.setBounds(280, 380, 280, 80);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        add(messageScrollPane);


        JButton Open = new JButton("Open");
        Open.setBounds(50, 470, 160, 40);
        Open.setFont(new Font("Serif", Font.BOLD, 21));
        Open.setBackground(Color.decode("#0000FF"));
        Open.setForeground(Color.WHITE);
        Open.setFocusPainted(false);
        add(Open);
        Open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
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
                messageTextArea.setText("");
                normalImageLabel.setIcon(null);
                encodedImageLabel.setIcon(null);
                sourceImage = null;
                embeddedImage = null;
            }
        });


        JButton Save = new JButton("SAVE");
        Save.setBounds(260,511,160,40);
        Save.setFont(new Font("Serif", Font.BOLD, 21));
        Save.setBackground(Color.decode("#0000FF"));
        Save.setForeground(Color.WHITE);
        Save.setFocusPainted(false);
        add(Save);
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });


        JButton Encode = new JButton("ENCODE");
        Encode.setBounds(471,470,160,40);
        Encode.setFont(new Font("Serif", Font.BOLD, 21));
        Encode.setBackground(Color.decode("#0000FF"));
        Encode.setForeground(Color.WHITE);
        Encode.setFocusPainted(false);
        add(Encode);
        Encode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                embedMessage();
            }
        });


        setSize(700, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#fde61e"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(EncodeImageClass.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                sourceImage = ImageIO.read(selectedFile);
                ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
                Image image = imageIcon.getImage().getScaledInstance(320, 240, Image.SCALE_DEFAULT);
                normalImageLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void embedMessage() {
        String mess = messageTextArea.getText();

        if (mess.equals("") || sourceImage.getHeight() == 0) {
            JOptionPane.showMessageDialog(this, "Please Select Image and Enter the Text First!");
            return;
        }

        embeddedImage = sourceImage.getSubimage(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        embedMessage(embeddedImage, mess);
        ImageIcon embeddedIcon = new ImageIcon(embeddedImage.getScaledInstance(320, 240, Image.SCALE_DEFAULT));
        encodedImageLabel.setIcon(embeddedIcon);
    }

    private void embedMessage(BufferedImage img, String mess) {
        int messageLength = mess.length();
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int imageSize = imageWidth * imageHeight;
        if (messageLength * 8 + 32 > imageSize) {
            JOptionPane.showMessageDialog(this, "Message is too long for the chosen image", "Message too long!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        embedInteger(img, messageLength, 0, 0);

        byte[] b = mess.getBytes();
        for (int i = 0; i < b.length; i++)
            embedByte(img, b[i], i * 8 + 32, 0);
    }

    private void embedInteger(BufferedImage img, int n, int start, int storageBit) {
        int maxX = img.getWidth();
        int maxY = img.getHeight();
        int startX = start / maxY;
        int startY = start - startX * maxY;
        int count = 0;
        for (int i = startX; i < maxX && count < 32; i++) {
            for (int j = startY; j < maxY && count < 32; j++) {
                int rgb = img.getRGB(i, j);
                int bit = getBitValue(n, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }

    private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
        int maxX = img.getWidth();
        int maxY = img.getHeight();
        int startX = start / maxY;
        int startY = start - startX * maxY;
        int count = 0;
        for (int i = startX; i < maxX && count < 8; i++) {
            for (int j = startY; j < maxY && count < 8; j++) {
                int rgb = img.getRGB(i, j);
                int bit = getBitValue(b, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }

    private int getBitValue(int n, int location) {
        int v = n & (int) Math.round(Math.pow(2, location));
        return v == 0 ? 0 : 1;
    }

    private int setBitValue(int n, int location, int bit) {
        int toggle = (int) Math.pow(2, location);
        int bv = getBitValue(n, location);
        if (bv == bit)
            return n;
        if (bv == 0 && bit == 1)
            n |= toggle;
        else if (bv == 1 && bit == 0)
            n ^= toggle;
        return n;
    }

    private void saveImage() {
        if (embeddedImage == null) {
            JOptionPane.showMessageDialog(this, "No message has been embedded!", "Nothing to save", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                ImageIO.write(embeddedImage, "PNG", fileToSave);
                JOptionPane.showMessageDialog(this, "Image saved successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred while saving the image.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

}
