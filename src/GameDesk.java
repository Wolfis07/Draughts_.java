import java.util.ArrayList;
import java.util.Scanner;

public class GameDesk {
    public Stone[][] grid = new Stone[8][8];
    private boolean active = true;
    private ArrayList<Point> validmoves = new ArrayList<>();
    private ArrayList<Jump> jumps = new ArrayList<>();


    public void Draw() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (!((j + i) % 2 == 0)) {
                    if (!(grid[i][j] == null)) {
                        System.out.print(grid[i][j].getDisplay());
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
            Draw();
        }
    }

    public void Move() {
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;
        int x = 0;
        int y = 0;
        Stone activeKamen;
        System.out.println("choose kamen which you want to move(x,y)");
        while (!isValid) {
            try {
                String s = sc.nextLine();
                x = Integer.parseInt(s.split(",")[0]);
                y = Integer.parseInt(s.split(",")[1]);
                isValid = true;
                activeKamen = grid[x][y];

                if (activeKamen.isColor() != active || grid[x][y] == null) {
                    System.out.println("bad color ");
                }
            } catch (Exception e) {
                System.out.println("invalid format -> (number,numbr)");
            }
        }
        activeKamen = grid[x][y];
        if (activeKamen.isColor() == active) {
            System.out.println("bad color ");
        }
        if (grid[x][y].isColor()) {
            if (x - 1 >= 0 && y - 1 < 8) {
                if (!grid[x - 1][y - 1].isFull()) {
                    validmoves.add(new Point(x - 1, y - 1));
                }
            }
            if (x - 2 >= 0 && y - 2 >= 0) {
                if (!grid[x - 1][y - 1].isColor() && !grid[x - 2][y - 2].isFull()) {
                    validmoves.add(new Point(x - 2, y - 2));
                    jumps.add(new Jump(new Point(x - 2, y - 2), new Point(x - 1, y - 1)));
                }
            }
            if (x + 2 < 8 && y - 2 >= 0) {
                if (!grid[x + 1][y - 1].isColor() && !grid[x + 2][y - 2].isFull()) {
                    validmoves.add(new Point(x + 2, y - 2));
                    jumps.add(new Jump(new Point(x + 2, y - 2), new Point(x + 1, y - 1)));
                }
            }
            if (y - 1 >= 0 && x + 1 < 8) {
                if (!grid[x + 2][y - 2].isFull()) {
                    validmoves.add(new Point(x + 1, y - 1));
                }
            }
            if (grid[x][y].isKing()) {

            }
        } else {
            if (x - 1 >= 0 && y + 1 < 8) {
                if (!grid[x - 1][y + 1].isFull()) {
                    validmoves.add(new Point(x - 1, y + 1));
                }
            }
            if (y + 1 <= 8 && x + 1 < 8) {
                if (!grid[x + 1][y + 1].isFull()) {
                    validmoves.add(new Point(x + 1, y + 1));
                }
            }
            if (x - 2 >= 0 && y + 2 < 8) {
                if (!grid[x - 1][y + 1].isColor() && !grid[x - 2][y + 2].isFull()) {
                    validmoves.add(new Point(x - 2, y + 2));
                    jumps.add(new Jump(new Point(x - 2, y + 2), new Point(x - 1, y + 1)));
                }
            }
            if (x + 2 <= 8 && y + 2 < 8) {
                if (!grid[x + 1][y + 1].isColor() && !grid[x + 2][y + 2].isFull()) {
                    validmoves.add(new Point(x + 2, y + 2));
                    jumps.add(new Jump(new Point(x + 2, y + 2), new Point(x + 1, y + 1)));
                }
            }
            if (grid[x][y].isKing()) {

            }
        }
    }

}
