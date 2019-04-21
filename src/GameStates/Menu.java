package GameStates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends JPanel {

    int height;
    int width;
    JButton startButton;
    JButton quitButton;
    Gamestate option;
    JLabel title;
    JLabel imageLabel = null;


    public Menu(int width, int height) {

        this.height = height;
        this.width = width;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            makeComponents();
            addComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeComponents() throws IOException {

        title = new JLabel("MAIN MENU");
        title.setFont(new Font("Helvetica", Font.PLAIN, 50));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setForeground(Color.pink);

        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("../player.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon imageIcon = null;
        if (image != null){
            Image dimg = image.getScaledInstance(120,120, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(dimg);
        }

        if (imageIcon != null){
            imageLabel = new JLabel(imageIcon);
            imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        }

        ImageIcon startIcon = new ImageIcon(getClass().getResource("../button.png"));
        startButton = new JButton(startIcon){
            {
                setPreferredSize(new Dimension(400, 100));
                setMaximumSize(new Dimension(400, 100));
                setText("START");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.GAME;
            }
        });

        ImageIcon quitIcon = new ImageIcon(getClass().getResource("../button.png"));
        quitButton = new JButton(quitIcon){
            {
                setPreferredSize(new Dimension(400, 100));
                setMaximumSize(new Dimension(400, 100));
                setText("QUIT");
                setHorizontalTextPosition(JButton.CENTER);
                setAlignmentX(JButton.CENTER_ALIGNMENT);
            }
        };
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                option = Gamestate.QUIT;
            }
        });
    }

    private void addComponents() {
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(Box.createVerticalGlue());
        if (imageLabel != null){
            this.add(imageLabel);
        }
        this.add(Box.createVerticalGlue());
        this.add(startButton);
        this.add(Box.createVerticalGlue());
        this.add(quitButton);
        this.add(Box.createVerticalGlue());
    }


    public Gamestate getOption(){
        return option;
    }

}
