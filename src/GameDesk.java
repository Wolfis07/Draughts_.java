import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class GameDesk {
    public Stone[][] grid = new Stone[8][8];
    private boolean active = true;
    private ArrayList<Move> validmoves = new ArrayList<>();
    JFrame frame = new JFrame();
    JTextField textField = new JTextField();
    JPanel panel = new JPanel();
    JButton button = new JButton();
    JPanel[] elemnts = new JPanel[64];
    String command = null;
    public void Draw() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (!((j + i) % 2 == 0)) {
                    if (!(grid[i][j] == null)) {
                        String s = null;
                        for (Move p : validmoves) {
                            if (p.getTo().getX() == i && p.getTo().getY() == j) {
                                s = grid[i][j].getDisplay() + "X";
                            }
                        }
                        if (s == null) {
                            s = grid[i][j].getDisplay() + " ";
                        }
                        System.out.print(s);
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print("▉▉");
                }
            }
            System.out.println("");
        }
    }

    public GameDesk() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setBounds(0,0,900,900);

        panel.setBackground(new Color(0,75,0));
        panel.setLayout(new GridLayout(8,8));

        textField.setBackground(new Color(255,255,255));
        textField.setSize(900,100);

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                elemnts[i * 8 + j] = new JPanel();
                elemnts[i * 8 + j].setBackground(Color.LIGHT_GRAY);
                elemnts[i * 8 + j].setSize(80, 80);
                if (!((j + i) % 2 == 0)) {
                    elemnts[i * 8 + j].setBackground(Color.WHITE);
                }
                panel.add(elemnts[i * 8 + j]);
            }
        }

        button.setText("Submit");
        button.setSize(100,900);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                command = textField.getText();
                System.out.println(command);
            }
        });

        frame.add(textField,BorderLayout.SOUTH);
        frame.add(button,BorderLayout.EAST);
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
                System.out.println(e.getMessage());
            }
        }
    }
    public void Move() throws Exception{
        int x;
        int y;
        int tox;
        int toy;
        Scanner scanner = new Scanner(System.in);
        System.out.println("napiste vstup ve formatu x,y (cislo,cislo)");
        String input = scanner.next();
        if(!(input.matches("[0-7],[0-7]"))) {
            throw new Exception("chyba ryba skill issue spatný vstup");
        }
        char[] c = input.toCharArray();
        x = Character.getNumericValue(c[0]);
        y = Character.getNumericValue(c[2]);
        if( grid[x][y] == null){
            throw new Exception("na vybranem mistě není kmamen ");
        }
        if(!( grid[x][y].isFull())){
            throw new Exception("na vybranem mistě není kmamen ");
        }

        if(!(grid[x][y].isColor() == active)){
            throw new Exception("blba barva");
        }
        validmoves = generatePossibleMoves(x,y);
        if(validmoves.isEmpty()){
            throw new Exception("tímto kamenem se nemuzes nikam pohnout");
        }
        for (Move m:validmoves) {
            System.out.println(m.toString());
        }
        Draw();
        System.out.println("vyber tah");
        input = scanner.next();
        if(!(input.matches("[0-7],[0-7]"))) {
            throw new Exception("chyba ryba skill issue spatný vstup");
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
            throw new Exception("to neni tah");
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