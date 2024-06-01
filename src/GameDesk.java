import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameDesk {
    public Stone[][] grid = new Stone[8][8];
    private boolean active = true;
    private ArrayList<Move> validmoves = new ArrayList<>();
    JFrame frame = new JFrame();

    final int TILE_SIZE = 100;

    Font font = new Font("Bell MT",Font.BOLD,50);
    JTextField textField = new JTextField();
    JPanel panel = new JPanel() {
        /**
         * Renders the game board, including its squares and stones
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);//
            Graphics2D g2 = (Graphics2D) g;

            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    int x = j * TILE_SIZE;
                    int y = i * TILE_SIZE;
                    if (!((j + i) % 2 == 0)) {
                        if (!(grid[i][j] == null)) {
                            g2.setColor(Color.BLACK);
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
                            g2.setColor(Color.WHITE);

                            FontMetrics m = g2.getFontMetrics(g2.getFont());
                            g2.drawString(s,x,y + m.getDescent() + m.getAscent());

                            System.out.print(s);
                        } else {
                        }
                    } else {
                        g2.setColor(Color.WHITE);
                        g2.fillRect(x,y,TILE_SIZE,TILE_SIZE);
                    }
                }
                System.out.println("");
            }
        }
    };
    /**
     * Redraws the game board.
     */
    public void draw() {
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Initializes the game board
     * Adding a text field to the bottom of the frame, which will be used for user input
     * Key listener is for the text field, which will send user input.
     */
    public GameDesk() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setBounds(0,0,815,850);

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
        start();
    }


    /**
     * Returns the winner of the game, or null if the game is not over.
     */
    public String winner() {
        int blackCount = 0;
        int whiteCount = 0;
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (grid[i][j] != null) {
                    if (grid[i][j] == Stone.Black()) {
                        blackCount++;
                    } else if (grid[i][j] == Stone.White()) {
                        whiteCount++;
                    }
                }
            }
        }
        if (blackCount == 0) {
            return "White won";
        } else if (whiteCount == 0) {
            return "Black won";
        } else {
            return null;
        }
    }
    public boolean canmove = false;

    /**
     * Makes a move in the game.
     *
     * user enter the starting and ending coordinates of the move.
     * Then it checks if the move is valid and updates the game state accordingly.
     */
    public void move() throws Exception {
        canmove = false;
        int x;
        int y;
        int tox;
        int toy;
        textField.setText("write input in the format y,x (number,number)");
        String input;
        while (!canmove) {
            Thread.sleep(5);
        }
        input = textField.getText();
        canmove = false;
        if (!(input.matches("[0-7],[0-7]"))) {
            throw new Exception("wrong input");
        }
        char[] c = input.toCharArray();
        x = Character.getNumericValue(c[0]);
        y = Character.getNumericValue(c[2]);

        if (grid[x][y] == null) {
            throw new Exception ("there is no stone in the selected place ");
        }
        if (!(grid[x][y].isFull())) {
            throw new Exception("there is no stone in the selected place ");
        }
        if (!(grid[x][y].isColor() == active)) {
            throw new Exception("wrong color");
        }

        validmoves = generatePossibleMoves(x, y);
        if (validmoves.isEmpty()) {
            throw new Exception("you cant move with this stone");
        }

        for (Move m : validmoves) {
            System.out.println(m.toString());
        }

        draw();


        while (!canmove) {
            Thread.sleep(5);
        }
        input = textField.getText();
        canmove = false;
        if (!(input.matches("[0-7],[0-7]"))) {
            throw new Exception("wrong input");
        }
        c = input.toCharArray();
        tox = Character.getNumericValue(c[0]);
        toy = Character.getNumericValue(c[2]);

        boolean moved = false;
        for (Move m : validmoves) {
            if (m.getTo().getX() == tox && m.getTo().getY() == toy) {
                grid[tox][toy] = grid[x][y];
                grid[x][y] = Stone.Empty();
                if (m.getJump()!= null) {
                    grid[m.getJump().getX()][m.getJump().getY()] = Stone.Empty();
                }
                moved = true;
                active =!active;
            }
        }
        if (!moved) {
            throw new Exception("you cant go there");
        }
        draw();
    }

    private boolean play = true;

    /**
     * Starts the game
     */
    public void start() {
        while (play) {
            validmoves.clear();
            draw();
            try {
                move();
            } catch (Exception e) {
                textField.setText(e.getMessage());
            }
            winner();
        }
        play = false;
        textField.setText("GAME ENDS");
    }




    /**
     *Checks if a move is valid in the game
     */
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

    /**
     * Generates a list of possible moves for a stone at a given position
     * It checks all possible directions (diagonal up-left, diagonal up-right, diagonal down-left, diagonal down-right)
     * and generates a list of valid moves, including jumps
     * (col = column)
     */
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