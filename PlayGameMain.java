
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;





public class PlayGameMain extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    private JButton playButton, addPlayerButton, musicButton,showScoreButton ;
    private boolean musicPlaying = false;
    private Gameplay gameplay;
    private Image background;

    private ImageIcon soundIcon, muteIcon;



    public PlayGameMain(){
        setTitle("Brickout Ball - Menu");
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        background = new ImageIcon(getClass().getResource("Brickphoto.png")).getImage();
        JPanel jPanel=new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background,0,0,getWidth(),getHeight(),this);
            }
        };
        jPanel.setLayout(null);
        setContentPane(jPanel);


        //زرراscore
        showScoreButton = new JButton("SHOW SCORE");
        showScoreButton.setBounds(20, 20, 150, 50);
        showScoreButton.setOpaque(false);
        showScoreButton.setContentAreaFilled(false);
        showScoreButton.setBorderPainted(false);
        showScoreButton.setForeground(Color.cyan);
        showScoreButton.setFont(new Font("Arial", Font.BOLD, 16));
        showScoreButton.addActionListener(this);
        add(showScoreButton);



        // زرار Play
        playButton = new JButton("PLAY");
        playButton.setBounds(250, 300, 160, 50);
        playButton.addActionListener(this);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setForeground(Color.cyan);
        playButton.setFont(new Font("Arial", Font.ITALIC, 40));

        jPanel.add(playButton);

        // زرار Add Player
        addPlayerButton = new JButton("ADD PLAYER");
        addPlayerButton.setBounds(230, 400, 250, 100);
        addPlayerButton.addActionListener(this);
        addPlayerButton.setOpaque(false);
        addPlayerButton.setContentAreaFilled(false);
        addPlayerButton.setBorderPainted(false);
        addPlayerButton.setForeground(Color.cyan);
        addPlayerButton.setFont(new Font("Arial", Font.BOLD, 30));
        jPanel.add(addPlayerButton);

        // زرار Music
      /*  musicButton = new JButton("MUSIC");
        musicButton.setBounds(250, 500, 200, 50);
        musicButton.addActionListener(this);
        musicButton.setOpaque(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setBorderPainted(false);
        musicButton.setForeground(Color.CYAN);
        musicButton.setFont(new Font("Arial", Font.BOLD, 40));
        jPanel.add(musicButton);*/
        soundIcon = new ImageIcon(getClass().getResource("/soundicon.png"));
        muteIcon = new ImageIcon(getClass().getResource("/muteicon.png"));

        musicButton = new JButton(soundIcon);
        musicButton.setBounds(50, 450, 200, 100);
        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);

        musicButton.addActionListener(this);

        add(musicButton);



        ///
/*
        getContentPane().setLayout(null);


        getContentPane().add(playButton);
        getContentPane().add(addPlayerButton);
        getContentPane().add(musicButton);*/
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            // افتح اللعبة
            this.setVisible(false);
            gameplay = new Gameplay();
            JFrame gameFrame = new JFrame();
            gameFrame.setBounds(10, 10, 700, 600);
            gameFrame.setTitle("Brickout Ball");
            gameFrame.setResizable(true);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.add(gameplay);
            gameFrame.setVisible(true);
        }

        if (e.getSource() == addPlayerButton) {
            // اضف مستطيل أزرق جديد تحت
            if (gameplay == null) {
                this.setVisible(false);
                gameplay = new Gameplay();
                JFrame gameFrame = new JFrame();
                gameFrame.setBounds(10, 10, 700, 600);
                gameFrame.setTitle("Brickout Ball");
                gameFrame.setResizable(true);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.add(gameplay);
                gameFrame.setVisible(true);

            }
            gameplay.addBluePlayer();
        }
        if (e.getSource() == musicButton) {
            System.out.println("Button clicked");
            if (gameplay != null) {
                if (!musicPlaying) {
                    gameplay.playMusic();
                    musicPlaying = true;
                    musicButton.setIcon(muteIcon);
                } else {
                    gameplay.stopMusic();
                    musicPlaying = false;
                    musicButton.setIcon(soundIcon);
                }
            }
        }




        if (e.getSource() == showScoreButton) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("score.txt"));
                String line;
                StringBuilder allScores = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    allScores.append(line).append("\n");
                }
                reader.close();
                JOptionPane.showMessageDialog(this, allScores.length() > 0 ? allScores.toString() : "No scores yet!",
                        "Saved Scores", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading score file!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }



    }

    public static void main(String[] args) {

        new PlayGameMain();
    }
}

