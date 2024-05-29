import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

public class GameDesk {
    public Stone[][] grid = new Stone[8][8];
    private boolean active = true;
    private ArrayList<Move> validmoves = new ArrayList<>();
    JFrame frame = new JFrame();

    final int TILE_SIZE = 100;

    Font font = new Font("Mv Boli",Font.BOLD,50);
    JTextField textField = new JTextField();
    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    int x = j * TILE_SIZE;
                    int y = i * TILE_SIZE;
                    if (!((j + i) % 2 == 0)) {
                        if (!(grid[i][j] == null)) {
                            g2.setColor(Color.WHITE);
                            g2.setFont(font );
                            g2.fillRect(x,y,TILE_SIZE,TILE_SIZE);


                            String s = grid[i][j].getDisplay();
                            for (Move p : validmoves) {
                                if (p.getTo().getX() == i && p.getTo().getY() == j) {
                                    s = grid[i][j].getDisplay() + "X";
                                }
                            }
                            if (s == null) {
                                s = grid[i][j].getDisplay() + " ";
                            }
                            g2.setColor(Color.BLACK);

                            FontMetrics m = g2.getFontMetrics(g2.getFont());
                            g2.drawString(s,x,y + m.getDescent() + m.getAscent());

                            System.out.print(s);
                        } else {
                        }
                    } else {
                        g2.setColor(Color.BLACK);
                        g2.fillRect(x,y,TILE_SIZE,TILE_SIZE);
                    }
                }
                System.out.println("");
            }
        }
    };
    public void Draw() {
        panel.revalidate();
        panel.repaint();
    }

    public GameDesk() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setBounds(0,0,900,900);

        panel.setBackground(new Color(0,75,0));
        panel.setLayout(new GridLayout(8,8));

        textField.setBackground(new Color(255,255,255));
        textField.setSize(900,100);

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10) canmove = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        frame.add(textField,BorderLayout.SOUTH);
        frame.add(panel);

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 7; j++) {
                if (!((j + i) % 2 == 0)) {
                    grid[i][j] = Stone.Black();
                }
            }
        }
        for (int i = 3; i <= 4; i++) {
            for (int j = 0; j <= 7; j++) {
                if (!((j + i) % 2 == 0)) {
                    grid[i][j] = Stone.Empty();
                }
            }
        }
        for (int i = 5; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (!((j + i) % 2 == 0)) {
                    grid[i][j] = Stone.White();
                }
            }
        }
        Start();
    }

    public String winner() {
        int b = 0;
        int w = 0;
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (!(grid[i][j] == null)) {
                    if (grid[i][j].isColor()) {
                        w++;
                    } else {
                        b++;
                    }
                }
            }
        }
        if (b == 0) {
            return "white";
        }
        if (w == 0) {
            return "black";
        }
        return null;
    }

    public void Start() {
        while (winner() == null) {
            validmoves.clear();
            Draw();
            try {
                Move();
            } catch (Exception e) {
                textField.setText(e.getMessage());
            }
        }
        textField.setText(winner());
    }

    public boolean canmove = false;

    public void Move() throws Exception{
        canmove = false ;
        int x;
        int y;
        int tox;
        int toy;
        textField.setText("write input in the format y,x (number,number)");
        String input;
        while(!canmove){
            Thread.sleep(5);
        }
        input = textField.getText();
        canmove = false;
        if(!(input.matches("[0-7],[0-7]"))) {
            throw new Exception("wronk input");
        }
        char[] c = input.toCharArray();
        x = Character.getNumericValue(c[0]);
        y = Character.getNumericValue(c[2]);
        if( grid[x][y] == null){
            throw new Exception("there is no stone in the selected place ");
        }
        if(!( grid[x][y].isFull())){
            throw new Exception("there is no stone in the selected place ");
        }
        if(!(grid[x][y].isColor() == active)){
            throw new Exception("wrong color");
        }
        validmoves = generatePossibleMoves(x,y);
        if(validmoves.isEmpty()){
            throw new Exception("you cant move with this stone");
        }
        for (Move m:validmoves) {
            System.out.println(m.toString());
        }
        Draw();
        while(!canmove){

        }
        input = textField.getText();
        canmove = false;
        if(!(input.matches("[0-7],[0-7]"))) {
            throw new Exception("wrong input");
        }
        c = input.toCharArray();
        tox = Character.getNumericValue(c[0]);
        toy = Character.getNumericValue(c[2]);
        boolean moved = false;
        for (Move m : validmoves){
            if(m.getTo().getX() == tox && m.getTo().getY() == toy){
                grid[tox][toy] = grid[x][y];
                grid[x][y] = Stone.Empty();
                if(!(m.getJump() == null)){
                    if(m.getJump().getX() == tox && m.getJump().getY() == toy){
                        grid[m.getJump().getX()][m.getJump().getY()] = Stone.Empty();
                    }
                }
                moved = true;
                active = !active;
                break;
            }
        }
        if(!moved){
            throw new Exception("you cant go there");
        }
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, boolean player) {
        if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8)
            return false;
        if (grid[toRow][toCol].isFull())
            return false;
        if (Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 2) {
            int jumpRow = (toRow + fromRow) / 2;
            int jumpCol = (toCol + fromCol) / 2;
            boolean opponent = !player;
            if (grid[jumpRow][jumpCol].isFull()) {
                if (grid[jumpRow][jumpCol].isColor() == opponent) {
                    return true;
                }
            }
        }
        if (Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 1) {
            return true;
        }
        return false;
    }
    public ArrayList<Move> generatePossibleMoves(int row, int col) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        Stone player = grid[row][col];
        Point[] directions = {new Point(-1, -1),new Point(-1, 1),new Point(1, -1), new Point(1, 1)};
        for (Point dir : directions) {
            int newRow = row + dir.getX();
            int newCol = col + dir.getY();
            if (isValidMove(row, col, newRow, newCol, player.isColor())) {
                possibleMoves.add(new Move( new Point(newRow , newCol)));
            }
        }
        for (Point dir : directions) {
            int jumpRow = row + 2 * dir.getX();
            int jumpCol = col + 2 * dir.getY();
            if (isValidMove(row, col, jumpRow, jumpCol, player.isColor())) {
                int removeRow = (row + jumpRow) / 2;
                int removeCol = (col + jumpCol) / 2;
                possibleMoves.add(new Move(new Point(jumpRow , jumpCol),new Point(removeRow,removeCol)));
            }
        }
        return possibleMoves;
    }

}