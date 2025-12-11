import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickwidth;
    public int brickheight;
    private Color[][] brickColors;

    public MapGenerator(int row,int col){
        map=new int[row][col];
        brickColors = new Color[row][col];
        for (int i=0;i< map.length;i++){
            for (int j=0;j<map[0].length;j++){
             map[i][j]=1;
                int r = (int)(Math.random() * 256);
                int g = (int)(Math.random() * 256);
                int b = (int)(Math.random() * 256);
                brickColors[i][j] = new Color(r, g, b);
            }
        }
        brickwidth=540/col ;
        brickheight=150/row;
    }
    public void draw(Graphics2D g){
        for (int i=0;i< map.length;i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]>0){
                    g.setColor(brickColors[i][j]);
                    g.fillRect(j*brickwidth+80,i*brickheight +50 ,brickwidth,brickheight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*brickwidth+80,i*brickheight +50 ,brickwidth,brickheight);
                }

            }
        }
    }

    public void setBrickvalue(int value,int row ,int col){
        map[row][col]= value;
    }


}
