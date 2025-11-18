
package blackholegame;


public class BlackHoleGame extends javax.swing.JFrame {
    private GameModel model;
    private BoardPanel boardPanel;


    
    public BlackHoleGame() {
        initComponents();
        model = new GameModel(7);
        boardPanel = new BoardPanel(model);

        boardPanelContainer.setLayout(new java.awt.BorderLayout());
        boardPanelContainer.removeAll();
        boardPanelContainer.add(boardPanel, java.awt.BorderLayout.CENTER);
        boardPanelContainer.revalidate();
        boardPanelContainer.repaint();

        sizeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"5 x 5", "7 x 7", "9 x 9"}));
        sizeCombo.setSelectedIndex(1);

        newGameBtn.addActionListener(e -> restartFromUI());
        sizeCombo.addActionListener(e -> restartFromUI());

        setStatus();
        pack();
        setLocationRelativeTo(null);
    }
    
    public void setStatus() {
        statusLabel.setText(String.format("Turn: %s   |   Score  P1: %d  P2: %d   |   Goal: %d",
            model.currentPlayer == Player.P1 ? "Player 1" : "Player 2",
            model.scoreP1, model.scoreP2, model.targetScore()));
    }

    public void restartFromUI() {
        int n = selectedBoardSize();
        model.reset(n);
        boardPanel.onModelReset();
        boardPanelContainer.revalidate();
        boardPanelContainer.repaint();
        setStatus();
        pack();
    }

    private int selectedBoardSize() {
        int idx = sizeCombo.getSelectedIndex();
        if (idx < 0) idx = 1;
        int n = 7;
        if (idx == 0) n = 5;
        if (idx == 1) n = 7;
        if (idx == 2) n = 9;
        return n;
    }


    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jInternalFrame3 = new javax.swing.JInternalFrame();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        sizeCombo = new javax.swing.JComboBox<>();
        newGameBtn = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();
        boardPanelContainer = new javax.swing.JPanel();

        jInternalFrame3.setVisible(true);

        javax.swing.GroupLayout jInternalFrame3Layout = new javax.swing.GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame3Layout.setVerticalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame2.setVisible(true);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sizeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5 x 5", "7 x 7", "9 x 9", " " }));
        sizeCombo.setToolTipText("jComboBox1");
        jPanel1.add(sizeCombo);

        newGameBtn.setText("New Game");
        jPanel1.add(newGameBtn);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        statusLabel.setText("jLabel1");
        statusLabel.setName("statusLabel"); 
        getContentPane().add(statusLabel, java.awt.BorderLayout.PAGE_END);

        boardPanelContainer.setName(""); 

        javax.swing.GroupLayout boardPanelContainerLayout = new javax.swing.GroupLayout(boardPanelContainer);
        boardPanelContainer.setLayout(boardPanelContainerLayout);
        boardPanelContainerLayout.setHorizontalGroup(
            boardPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 855, Short.MAX_VALUE)
        );
        boardPanelContainerLayout.setVerticalGroup(
            boardPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 691, Short.MAX_VALUE)
        );

        getContentPane().add(boardPanelContainer, java.awt.BorderLayout.CENTER);

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BlackHoleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BlackHoleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BlackHoleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BlackHoleGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      

      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BlackHoleGame().setVisible(true);
            }
        });
    }

    
    private javax.swing.JPanel boardPanelContainer;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton newGameBtn;
    private javax.swing.JComboBox<String> sizeCombo;
    private javax.swing.JLabel statusLabel;
   
}
