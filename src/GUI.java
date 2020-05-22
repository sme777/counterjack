import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class GUI implements ActionListener {

    public GUI() {


        _frame = new JFrame();
        _panel = new JPanel();
        _panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        _panel.setLayout(new GridLayout(4, 4));
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setTitle("Welcome to Counter Jack");

        _frame.setVisible(true);
        _frame.setPreferredSize(new Dimension(500, 500));


        //_userHitButton = new JButton("Hit");
       // _userHitButton.setSize(new Dimension(40, 40));
//        _userStandButton = new JButton("Stand");
//        _userDoubleButton = new JButton("Double");
//        _userSurrenderButton = new JButton("Surrender");
//        _userNotSplitButton = new JButton("Don't Split");
//        _userSplitButton = new JButton("Split");
        //_panel.add(_userHitButton);
//        _panel.add(_userStandButton);
//        _panel.add(_userDoubleButton);
//        _panel.add(_userSplitButton);
//        _panel.add(_userNotSplitButton);
//        _panel.add(_userSurrenderButton);

        //_userHitButton.addActionListener(this);
//        _userStandButton.addActionListener(this);
//        _userDoubleButton.addActionListener(this);
//        _userSplitButton.addActionListener(this);
//        _userNotSplitButton.addActionListener(this);
//        _userSurrenderButton.addActionListener(this);
        _frame.add(_panel, BorderLayout.CENTER);
        _frame.pack();

    }

    void loadImage(String card) {
        BufferedImage img = null;
        try {

            img = ImageIO.read(new File("C:\\Users\\User\\IdeaProjects\\counterjack\\cards\\" + card + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel label = new JLabel();
        label.setSize(new Dimension(69, 105));
        Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(dimg));
        _panel.setBackground(Color.GREEN);
        _panel.add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(_userHitButton.getText())) {
            System.out.println("Hit");
        } else if (e.getActionCommand().equals(_userStandButton.getText())) {
            System.out.println("Stand");
        } else if (e.getActionCommand().equals(_userDoubleButton.getText())) {
            System.out.println("Double");
        } else if (e.getActionCommand().equals(_userSplitButton.getText())) {
            System.out.println("Split");
        } else if (e.getActionCommand().equals(_userNotSplitButton.getText())) {
            System.out.println("Don't Split");
        } else if (e.getActionCommand().equals(_userSurrenderButton.getText())) {
            System.out.println("Surrender");
        }

    }


    JButton _userHitButton;
    JButton _userStandButton;
    JButton _userDoubleButton;
    JButton _userSplitButton;
    JButton _userNotSplitButton;
    JButton _userSurrenderButton;
    JFrame _frame;
    JPanel _panel;

}
