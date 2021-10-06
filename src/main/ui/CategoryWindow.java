package ui;

import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class CategoryWindow {
    private JFrame newWindow;

    private JPanel newPanel;
    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel bottomPanel;

    private JLabel topLabel;

    private final Color lightText = new Color(222, 222, 222);
    private final Color darkBackground = new Color(38, 40, 49);
    private final Color darkInner = new Color(60, 60, 68);
    private final Color expenseRed = new Color(253, 106, 106);


    //EFFECTS:
    //Creates a new window to show the categorized expenses.  initializes everything, adds panels to main panel.
    public CategoryWindow(LinkedList<String> expenseList, String category, double totalExpense) {

        newWindow = new JFrame();
        newWindow.setSize(300, 300);
        newWindow.setTitle(category + " Expenses");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setBackground(darkBackground);

        initTopPanel(category);
        initInnerPanel(expenseList);
        initBottomPanel(category, totalExpense);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(newPanel);
        newWindow.add(mainPanel);
        newWindow.setVisible(true);
    }

    //modifies this
    //effects: initializes the inner panel, adds the content to the panel aswell.
    public void initInnerPanel(LinkedList<String> expenseList) {
        int num = 1;
        for (String s : expenseList) {
            JPanel tempPanel = new JPanel();
            JLabel tempLabel = new JLabel(num + ": " + s);
            tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            tempLabel.setForeground(expenseRed);
            tempPanel.add(tempLabel);
            tempPanel.setBackground(darkInner);
            newPanel.add(tempPanel);
            num++;
        }

    }

    //modifies: this
    //effects:  initializes the top/header panel.
    public void initTopPanel(String category) {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(darkBackground);
        topLabel = new JLabel("Expenses of category: " + category);
        topLabel.setForeground(lightText);
        topPanel.add(topLabel);
    }

    //modifies: this
    //EFFECTS: initializes the bottom panel
    public void initBottomPanel(String category, double totalExpense) {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(darkBackground);


        JLabel totalExpenseLabel = new JLabel("Total expense of " + category + ": $" + totalExpense);
        totalExpenseLabel.setForeground(lightText);
        bottomPanel.add(totalExpenseLabel);

    }
}
