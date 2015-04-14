package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class JFSudoku extends JFrame implements ActionListener{    
    private JButton btnResolver;
    private JButton btnLimpiar;
    private JTextField[][] cells;
    private final Font marked = new Font("Arial", Font.BOLD, 24);
    
    //-------------------------------------------------------------------------
    public JFSudoku() {
        super();
        this.setTitle("Sudoku");
        this.setSize(300, 350);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        
        cells = GetArrayCells();
        InitializeCells(cells);
        SetSizeCells(cells);
        SetLocationCells(cells);
        AddCells(cells);
        
        BtnResolver();
        BtnLimpiar();
    }
    
    private void ResetCells() {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                cells[x][y].setBackground(Color.white);
                cells[x][y].setText("");
            }
        }
    }
    
    private void BtnResolver() {
        btnResolver = new JButton();
        btnResolver.setText("Resolver");
        btnResolver.setSize(90, 20);
        btnResolver.setLocation(10, 290);
        btnResolver.addActionListener(this);
        this.add(btnResolver);
    }
    
    private void BtnLimpiar() {
        btnLimpiar = new JButton();
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setSize(90, 20);
        btnLimpiar.setLocation(110, 290);
        btnLimpiar.addActionListener(this);
        this.add(btnLimpiar);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnResolver)) {
            Solve();
        }
        if(e.getSource().equals(btnLimpiar)) {
            ResetCells();
        }
    }
    
    private JTextField[][] GetArrayCells() {
        JTextField[][] cells = new JTextField[9][9];
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                cells[x][y] = new JTextField();
            }
        }
        return cells;
    }
    
    private void InitializeCells(JTextField[][] cells) {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                cells[x][y] = new JTextField();
            }
        }
    }
    
    private void SetSizeCells(JTextField[][] cells) {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                cells[x][y].setSize(20, 20);
            }
        }
    }
    
    private void SetLocationCells(JTextField[][] cells) {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                cells[x][y].setLocation(x*30 + 10 + x / 3 * 6, y*30 + 10 + y / 3 * 6);
            }
        }
    }
    
    private void AddCells(JTextField[][] cells) {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                this.add(cells[x][y]);
            }
        }
    }
    
    private void Solve() {
        boolean changes = false;
        for(int counter = 1; counter < 10; counter++) {
            changes = Check(counter);
            if(changes) {
                counter = 1;
            }
        }
    }
    
    private boolean Check(int num) {
        boolean changes = false;
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                if(cells[x][y].getText().equals(Integer.toString(num))) {
                    BlockColumn(x);
                    BlockRow(y);
                    BlockSquare(x, y);
                }
                if(!cells[x][y].getText().equals("")) {
                    cells[x][y].setBackground(Color.red);
                }
            }
        }
                    
        for(int x2 = 0; x2 < 9; x2++) {    
            changes = changes || CheckColumn(num, x2);
        }
        for(int y2 = 0; y2 < 9; y2++) {
            changes = changes || CheckRow(num, y2);                        
        }
        for(int aux = 1; aux < 9; aux+=3) {   
            changes = changes || CheckSquare(num, aux, aux);
        }
        ClearHints();
        return changes;
    }
    
    private void BlockColumn(int column) {
        for(int y = 0; y < 9; y++) {
            cells[column][y].setBackground(Color.red);
        }
    }
    
    private void BlockRow(int row) {
        for(int x = 0; x < 9; x++) {
            cells[x][row].setBackground(Color.red);
        }
    }
    
    private void BlockSquare(int x, int y) {
        x /= 3;
        x *= 3;
        y /= 3;
        y *=3;
        for(int y2 = y; y2 < y + 3; y2++) {    
            for(int x2 = x; x2 < x + 3; x2++) {
                cells[x2][y2].setBackground(Color.red);
            }
        }
    }

    private boolean CheckColumn(int num, int column) {
        int spaces = 0;
        int row = 0;
        for(int y = 0; y < 9; y++) {
            if(cells[column][y].getBackground() != Color.red) {
                spaces ++;
                row = y;
            }
        }
        if(spaces == 1) {
            cells[column][row].setText(Integer.toString(num));
            cells[column][row].setFont(marked);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean CheckRow(int num, int row) {
        int spaces = 0;
        int column = 0;
        for(int x = 0; x < 9; x++) {
            if(cells[x][row].getBackground() != Color.red) {
                spaces ++;
                column = x;
            }
        }
        if(spaces == 1) {
            cells[column][row].setText(Integer.toString(num));
            cells[column][row].setFont(marked);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean CheckSquare(int num, int x, int y) {
        x /= 3;
        x *= 3;
        y /= 3;
        y *=3;
        int spaces = 0;
        int column = 0;
        int row = 0;
        for(int y2 = y; y2 < y + 3; y2++) {    
            for(int x2 = x; x2 < x + 3; x2++) {
                if(cells[x2][y2].getBackground() != Color.red) {
                    spaces = 0;
                    column = x;
                    row = y;
                }
            }
        }
        if(spaces == 1) {
            cells[column][row].setText(Integer.toString(num));
            cells[column][row].setFont(marked);
            return true;
        }
        else {
            return false;
        }
    }

    private void ClearHints() {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                cells[x][y].setBackground(Color.white);
            }
        }
    }
}
