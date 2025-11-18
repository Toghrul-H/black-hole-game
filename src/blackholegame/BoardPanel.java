package blackholegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class BoardPanel extends JPanel {

    private final GameModel model;
    private Pos selected = null;
    private Map<Pos, SlideOutcome> moves = Map.of();
    private static final int CELL = 72;

    public BoardPanel(GameModel model) {
        this.model = model;
        setBackground(new Color(245, 245, 245));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick(e);
            }
        });
    }

    public void onModelReset() {
        selected = null;
        moves = Map.of();
        repaint();
    }

    private Pos fromPixel(int x, int y) {
        int r = y / CELL;
        int c = x / CELL;

        if (r < 0 || c < 0 || r >= model.n || c >= model.n) {
            return null;
        }
        return new Pos(r, c);
    }

    private void onClick(MouseEvent e) {
        Pos p = fromPixel(e.getX(), e.getY());
        if (p == null) return;

        if (moves.containsKey(p) && selected != null) {
            String end = model.move(selected, p);
            selected = null;
            moves = Map.of();
            repaint();

            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof BlackHoleGame) {
                ((BlackHoleGame) w).setStatus();

                if (end != null) {
                    JOptionPane.showMessageDialog(
                        this,
                        end,
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    ((BlackHoleGame) w).restartFromUI();
                }
            }
            return;
        }

        if (model.isPlayersShip(p, model.currentPlayer)) {
            if (selected != null && selected.equals(p)) {
                selected = null;
                moves = Map.of();
            } else {
                selected = p;
                moves = model.legalSlides(p);
            }
            repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(model.n * CELL, model.n * CELL);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int n = model.n;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int x = c * CELL;
                int y = r * CELL;

                g2.setColor(((r + c) % 2 == 0)
                        ? new Color(230, 230, 230)
                        : new Color(210, 210, 210));
                g2.fillRect(x, y, CELL, CELL);

                g2.setColor(new Color(200, 200, 200));
                g2.drawRect(x, y, CELL, CELL);
            }
        }

        int mid = n / 2;
        int hx = mid * CELL + CELL / 2;
        int hy = mid * CELL + CELL / 2;
        int hr = (int) (CELL * 0.35);

        g2.setColor(Color.BLACK);
        g2.fillOval(hx - hr, hy - hr, 2 * hr, 2 * hr);

        g2.setStroke(new BasicStroke(3f));
        for (Pos d : moves.keySet()) {
            int x = d.c * CELL;
            int y = d.r * CELL;
            g2.setColor(new Color(70, 130, 180));
            g2.drawRect(x + 4, y + 4, CELL - 8, CELL - 8);
        }

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                CellKind k = model.board[r][c];
                if (k == CellKind.SHIP_P1 || k == CellKind.SHIP_P2) {
                    int x = c * CELL + CELL / 2;
                    int y = r * CELL + CELL / 2;
                    int rad = (int) (CELL * 0.32);

                    if (k == CellKind.SHIP_P1) {
                        g2.setColor(new Color(220, 20, 60));
                    } else {
                        g2.setColor(new Color(25, 25, 112));
                    }

                    g2.fillOval(x - rad, y - rad, 2 * rad, 2 * rad);

                    if (selected != null && selected.r == r && selected.c == c) {
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(2.5f));
                        g2.drawOval(x - rad - 3, y - rad - 3, 2 * rad + 6, 2 * rad + 6);
                    }
                }
            }
        }
    }
}
