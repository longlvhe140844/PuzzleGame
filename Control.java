/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j2.s.p0021;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import sun.security.x509.AlgorithmId;

/**
 *
 * @author Acer
 */
public class Control {

    private PuzzleGame pz;
    private int size = 3;
    private JButton[][] matrix;
    private int move = 0;
    private Timer timer;
    private int time = 0;
    private boolean check = false;
    private JButton btn;
    private boolean startGame = false;
    public Control(PuzzleGame pz) {
        this.pz = pz;
        addButton();
        initTimer();
    }
    
    public void abc{
    }
    
    public void newGame() {
        time = 0;
        move = 0;
        startGame = true;
        
        pz.lblCount.setText("0");
        pz.Time.setText("0");
        timer.start();
        pz.btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                newGame();
                
            }
        });
        
        timer.start();
        
        addButton();
    }
    
    public void initTimer(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                pz.Time.setText(time + "");
            }
        });
    }

    public void addButton() {

        size = Integer.parseInt(pz.cbbSize.getSelectedItem().toString().split("x")[0]);
        pz.pbLayout.removeAll();
        pz.pbLayout.setLayout(new GridLayout(size, size, 10, 10));
        pz.pbLayout.setPreferredSize(new Dimension(size * 60, size * 60));
        matrix = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton btn = new JButton(i * size + j + 1 + "");
                matrix[i][j] = btn;
                pz.pbLayout.add(btn);

                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (startGame == true) {
                            if (canMoveBtn(btn) == true) {
                                moveBtn(btn);
                                if (checkWin() == true) {
                                    timer.stop();
                                    JOptionPane.showMessageDialog(pz, "win");
                                }
                            }
                        }
                    }
                });
            }
        }
        matrix[size - 1][size - 1].setText("");
        mixBtn();
        pz.pack();
    }

    public Point findEmpty() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].getText().equals("")) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    public void mixBtn() {
        for (int i = 0; i < 1000; i++) {
            Point p = findEmpty(); // lay toa do rong
            int a = p.x;
            int b = p.y;
            Random r = new Random();
            int choice = r.nextInt(4);
            switch (choice) {
                //up
                case 0: {
                    if (a > 0) {
                        String txt = matrix[a - 1][b].getText();
                        matrix[a][b].setText(txt);
                        matrix[a - 1][b].setText("");
                    }
                    break;
                }
                //down
                case 1: {
                    if (a < size - 1) {
                        String txt = matrix[a + 1][b].getText();
                        matrix[a][b].setText(txt);
                        matrix[a + 1][b].setText("");
                    }
                    break;
                }
                //left
                case 2: {
                    if (b > 0) {
                        String txt = matrix[a][b - 1].getText();
                        matrix[a][b].setText(txt);
                        matrix[a][b - 1].setText("");
                    }
                    break;
                }
                //right
                case 3: {
                    if (b < size - 1) {
                        String txt = matrix[a][b + 1].getText();
                        matrix[a][b].setText(txt);
                        matrix[a][b + 1].setText("");
                    }
                    break;
                }
            }
        }
    }

    public boolean canMoveBtn(JButton btn) {
        Point p = findEmpty();
        int k = 0;
        int h = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (btn.getText().equals(matrix[i][j].getText())) {
                    k = i;
                    h = j;
                }
            }
        }
        if (p.x == k && Math.abs(p.y - h) == 1) {
            return true;
        }
        if (p.y == h && Math.abs(p.x - k) == 1) {
            return true;
        }
        return false;
    }

    public void moveBtn(JButton btn) {
        Point p = findEmpty();
        String txt = btn.getText();
        matrix[p.x][p.y].setText(txt);
        btn.setText("");
        move++;
        pz.lblCount.setText(move + "");
    }

    public boolean checkWin() {
        if (matrix[size - 1][size - 1].getText().equals("")) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == size - 1 && j == size - 1) {
                       
                        return true;
                    }
                    if (!matrix[i][j].getText().equals(i * size + j + 1 + "")) {
                        return false;
                    }
                }
            }
            
            return true;
        }
        return false;
    }
}
