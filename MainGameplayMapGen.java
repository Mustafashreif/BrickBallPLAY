import javax.swing.*;


public class MainGameplayMapGen {
    public static void main(String[] args) {
        JFrame jFrame=new JFrame();
        Gameplay gameplay=new Gameplay();
        jFrame.setBounds(10,10,700,600);
        jFrame.setTitle("Brickout Ball");
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // jFrame.setLocationRelativeTo();
        jFrame.add(gameplay);

    }


}
