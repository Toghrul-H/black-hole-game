package blackholegame;

import java.util.*;

public class GameModel {

    int n;
    CellKind[][] board;
    Player currentPlayer = Player.P1;
    int scoreP1 = 0, scoreP2 = 0;

    final int[][] DIRS = {
        { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 },
        { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 }
    };

    public GameModel(int n) {
        reset(n);
    }

    public void reset(int n) {
        this.n = n;
        board = new CellKind[n][n];

        for (int r = 0; r < n; r++) {
            Arrays.fill(board[r], CellKind.EMPTY);
        }

        int mid = n / 2;
        board[mid][mid] = CellKind.HOLE;

        for (int i = 0; i < n; i++) {
            if (i != mid) {
                board[i][i] = CellKind.SHIP_P1;
            }

            int j = n - 1 - i;
            if (j != mid) {
                board[i][j] = CellKind.SHIP_P2;
            }
        }

        currentPlayer = Player.P1;
        scoreP1 = 0;
        scoreP2 = 0;
    }

    public int targetScore() {
        return (int) Math.ceil((n - 1) / 2.0);
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && c >= 0 && r < n && c < n;
    }

    public CellKind at(Pos p) {
        return board[p.r][p.c];
    }

    public boolean isPlayersShip(Pos p, Player pl) {
        CellKind k = at(p);
        return (pl == Player.P1 && k == CellKind.SHIP_P1)
            || (pl == Player.P2 && k == CellKind.SHIP_P2);
    }

    public Map<Pos, SlideOutcome> legalSlides(Pos p) {
        Map<Pos, SlideOutcome> res = new LinkedHashMap<>();

        for (int[] d : DIRS) {
            SlideOutcome out = slideUntilBlock(p, d[0], d[1]);
            if (out != null) {
                res.put(out.dest, out);
            }
        }

        return res;
    }

    public SlideOutcome slideUntilBlock(Pos p, int dr, int dc) {
        int r = p.r, c = p.c;
        int nr = r + dr, nc = c + dc;

        if (!inBounds(nr, nc)) {
            return null;
        }

        while (inBounds(nr, nc)) {
            CellKind k = board[nr][nc];

            if (k == CellKind.HOLE) {
                return new SlideOutcome(new Pos(nr, nc), true);
            } else if (k == CellKind.SHIP_P1 || k == CellKind.SHIP_P2) {
                int br = nr - dr;
                int bc = nc - dc;

                if (br == p.r && bc == p.c) {
                    return null;
                }

                return new SlideOutcome(new Pos(br, bc), false);
            }

            nr += dr;
            nc += dc;
        }

        int br = nr - dr;
        int bc = nc - dc;

        if (br == p.r && bc == p.c) {
            return null;
        }

        return new SlideOutcome(new Pos(br, bc), false);
    }

    public String move(Pos src, Pos dest) {
        if (!inBounds(src.r, src.c) || !inBounds(dest.r, dest.c)) {
            return null;
        }

        CellKind ship = at(src);
        boolean p1 = (ship == CellKind.SHIP_P1);

        Map<Pos, SlideOutcome> leg = legalSlides(src);
        SlideOutcome out = leg.get(dest);

        if (out == null) {
            return null;
        }

        board[src.r][src.c] = CellKind.EMPTY;

        if (out.fallsIn) {
            if (p1) {
                scoreP1++;
            } else {
                scoreP2++;
            }
        } else {
            board[dest.r][dest.c] = p1 ? CellKind.SHIP_P1 : CellKind.SHIP_P2;
        }

        int target = targetScore();

        if (scoreP1 >= target) {
            return "Player 1 wins!";
        }

        if (scoreP2 >= target) {
            return "Player 2 wins!";
        }

        currentPlayer = (currentPlayer == Player.P1) ? Player.P2 : Player.P1;

        if (!hasAnyMove(currentPlayer)) {
            currentPlayer = (currentPlayer == Player.P1) ? Player.P2 : Player.P1;
        }

        return null;
    }

    public boolean hasAnyMove(Player pl) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Pos p = new Pos(r, c);
                if (isPlayersShip(p, pl) && !legalSlides(p).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
