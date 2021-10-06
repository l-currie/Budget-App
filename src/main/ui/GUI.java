package ui;

import model.BudgetMonth;
import model.Expense;
import model.Income;
import model.exceptions.InvalidIndex;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

//Creates and runs the GUI for the app!
// GUI consists of a JFrame with many JPanels inside for different blocks of content.
// Handles button features.
// Stylizes the app

// took some bits and pieces from https://www.youtube.com/user/davekirkwood
// also used this a little bit   https://www.youtube.com/channel/UC_fFL5jgoCOrwAVoM_fBYwA
// there are also a few other sources that are cited where they are used.

public class GUI {

    private BudgetMonth month;
    private SoundEffect se = new SoundEffect();
    private CategoryWindow categoryWindow;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private final JFrame frame = new JFrame();
    private final JPanel mainPanel = new JPanel();

    private JPanel saveLoadPanel;
    private JPanel incomesPanel;
    private JPanel expensesPanel;
    private JPanel expenseListPanel;
    private JPanel incomeListPanel;
    private JPanel addButtonsPanel;
    private JPanel expenseBottomPanel;

    private final JButton loadButton = new JButton("Load");
    private final JButton saveButton = new JButton("Save");
    private JButton addIncomeButton;
    private JButton addExpenseButton;
    private JButton removeIncomeButton;
    private JButton removeExpenseButton;
    private JButton categorizeButton;

    private JTextField userText;
    private JTextField monthText;
    private JTextField amountText;
    private JTextField descriptionText;
    private JTextField categoryText;
    private JTextField numExpenseToRemove;
    private JTextField numIncomeToRemove;
    private JTextField categorizeText;

    private JLabel totalIncomeLabel;
    private JLabel totalExpenseLabel;
    private JLabel amountLabel;
    private JLabel categoryLabel;
    private JLabel descLabel;

    private JScrollPane expensePane;
    private JScrollPane incomePane;

    private final Color lightText = new Color(222, 222, 222);
    private final Color darkBackground = new Color(38, 40, 49);
    private final Color darkInner = new Color(60, 60, 68);
    private final Color incomeGreen = new Color(143, 232, 108);
    private final Color expenseRed = new Color(253, 106, 106);
    private final Color borderColor = new Color(56, 58, 66);

    private final Border darkBorder = BorderFactory.createBevelBorder(1, borderColor, borderColor);

    private final String goodSound = "./data/audio/goodSound.wav";
    private final String click = "./data/audio/click2.wav";
    private final String positive = "./data/audio/positive.wav";
    private final String save = "./data/audio/save.wav";
    private final String error = "./data/audio/error.wav";



    //EFFECTS: creates a new GUI object, initializes panels.
    public GUI() {
        this.month = new BudgetMonth("default", "default");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Budgetly");

        initSaveLoadPanel();
        initMainPanel();
        initIncomesPanel();
        initExpensesPanel();
        initMiddleSplit();
        initAddButtons();
        initPersistenceButtons();
        initAddIncomeButtonFeatures();
        initAddExpenseButtonFeatures();
        initRemoveButtonFeatures();
        initColors();

        frame.setVisible(true);
    }

    //modifies: this
    //EFFECTS: creates the saveloadpanel, adds buttons to it and adds it to the mainpanel.
    public void initSaveLoadPanel() {
        this.saveLoadPanel = new JPanel();
        this.saveLoadPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel userLabel = new JLabel("User");
        JLabel monthLabel = new JLabel("Month");
        userLabel.setForeground(lightText);
        monthLabel.setForeground(lightText);
        userText = new JTextField(10);
        monthText = new JTextField(10);

        saveLoadPanel.add(userLabel);
        saveLoadPanel.add(userText);
        saveLoadPanel.add(monthLabel);
        saveLoadPanel.add(monthText);
        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(loadButton);


    }

    //modifies: this
    //EFFECTS:  adds ActionListeners' to the save/load buttons, implements the features.
    public void initPersistenceButtons() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound(save);
                doSaveBudgetMonth();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText().toLowerCase();
                String month = monthText.getText().toLowerCase();
                try {
                    playSound(save);
                    doLoadBudgetMonth(user, month);
                } catch (IOException exception) {
                    System.out.println("Error Loading.. no such file.");
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
    }

    //modifies this
    //EFFECTS: creates the main panel and adds saveloadpanel.
    public void initMainPanel() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(saveLoadPanel, BorderLayout.NORTH);
        frame.add(mainPanel);

    }

    //modifies: this
    //effects: initializes the incomes panel.
    //incomeListPanel is the panel responsible for displaying individual panels containing income data.
    //incomeBottomPanel contains buttons and total.
    public void initIncomesPanel() {
        incomeListPanel = new JPanel();
        incomesPanel = new JPanel();
        incomesPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(incomeListPanel);
        scrollPane.setBorder(null);
        JPanel incomeBottomPanel = new JPanel();
        incomeBottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel incomeLabel = new JLabel("  Incomes:   [ Amount ($)  |  Description ]");
        numIncomeToRemove = new JTextField(3);
        incomeLabel.setForeground(lightText);
        totalIncomeLabel = new JLabel("Total: $420.0");
        removeIncomeButton = new JButton("Remove Number");
        incomesPanel.add(incomeLabel, BorderLayout.NORTH);

        incomeBottomPanel.add(removeIncomeButton);
        incomeBottomPanel.add(numIncomeToRemove);
        incomeBottomPanel.add(totalIncomeLabel);
        incomeBottomPanel.setBackground(darkBackground);
        incomeBottomPanel.setBorder(null);
        incomesPanel.add(incomeBottomPanel, BorderLayout.SOUTH);
        incomesPanel.add(scrollPane, BorderLayout.CENTER);
        incomesPanel.setBorder(darkBorder);

    }

    //modifies this:
    //EFFECTS: initializes the expenses panel.
    //expenses panel is the main panel, expenseListPanel is used for displaying expense data.
    public void initExpensesPanel() {
        expenseListPanel = new JPanel();
        expensesPanel = new JPanel();
        expensesPanel.setLayout(new BorderLayout());
        expensePane = new JScrollPane(expenseListPanel);
        expensePane.setBorder(null);

        JLabel expenseLabel = new JLabel("  Expenses:   [ Amount ($)  |  Description  |  Category ]");
        expenseLabel.setForeground(lightText);
        expensesPanel.setBorder(darkBorder);
        expensesPanel.add(expenseLabel, BorderLayout.NORTH);

        initExpensesBottomPanel();
        expensesPanel.add(expensePane, BorderLayout.CENTER);
        expensesPanel.add(expenseBottomPanel, BorderLayout.SOUTH);
    }

    //modifies: this
    //effects: initializes the bottom panel for the expenses panel.
    //adds the buttons and text fields and total expense label.
    public void initExpensesBottomPanel() {
        expenseBottomPanel = new JPanel();
        expenseBottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        numExpenseToRemove = new JTextField(3);
        removeExpenseButton = new JButton("Remove Number");
        categorizeButton = new JButton("Categorize");
        categorizeText = new JTextField(5);
        totalExpenseLabel = new JLabel("Total: $" + month.totalExpenses());
        expenseBottomPanel.add(totalExpenseLabel);
        expenseBottomPanel.add(categorizeButton);
        expenseBottomPanel.add(categorizeText);
        expenseBottomPanel.add(removeExpenseButton);
        expenseBottomPanel.add(numExpenseToRemove);
        expenseBottomPanel.setBackground(darkBackground);
        expenseBottomPanel.setBorder(null);

        initCategorizeButton();

    }

    //modifies this
    //EFFECTS: initializes the middle split pane, which contains the incomesPanel and expensesPanel, and splits it
    //down the middle.  Styles the panel, adds it to main panel.
    public void initMiddleSplit() {
        JSplitPane middleSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, incomesPanel, expensesPanel);
        middleSplit.setDividerLocation(425);
        middleSplit.setEnabled(false);
        //Code for changing divider color from
        //https://stackoverflow.com/questions/8934169/how-to-change-the-color-or-background-color-of-jsplitpane-divider
        //from answerer's Balaram26 and cuoka
        middleSplit.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {
                    }

                    @Override
                    public void paint(Graphics g) {
                        g.setColor(darkBackground);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        middleSplit.setBorder(null);
        mainPanel.add(middleSplit, BorderLayout.CENTER);
    }

    //modifies this:
    //EFFECTS: initializes the add buttons visually.
    public void initAddButtons() {
        addButtonsPanel = new JPanel();
        addButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        amountText = new JTextField(8);
        descriptionText = new JTextField(8);
        categoryText = new JTextField(8);

        amountLabel = new JLabel("Amount: ");
        descLabel = new JLabel("Description: ");
        categoryLabel = new JLabel("Category: ");

        addIncomeButton = new JButton("Add Income");
        addExpenseButton = new JButton("Add Expense");
        addButtonsPanel.add(amountLabel);
        addButtonsPanel.add(amountText);
        addButtonsPanel.add(descLabel);
        addButtonsPanel.add(descriptionText);
        addButtonsPanel.add(categoryLabel);
        addButtonsPanel.add(categoryText);
        addButtonsPanel.add(addIncomeButton);
        addButtonsPanel.add(addExpenseButton);

        mainPanel.add(addButtonsPanel, BorderLayout.SOUTH);
    }

    //modifies: this
    //effects: initializes the features for when the addIncome button is clicked.
    public void initAddIncomeButtonFeatures() {
        addIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountText.getText());
                    String desc = descriptionText.getText();
                    Income newIncome = new Income(amount, desc);

                    month.addIncome(newIncome);
                    update();
                    playSound(positive);
                } catch (Exception exc) {
                    playSound(error);
                }
            }
        });
    }

    //modifies: this
    //EFFECTS: initializes the on click features for the addExpense button.
    public void initAddExpenseButtonFeatures() {
        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    double amount = Double.parseDouble(amountText.getText());
                    String desc = descriptionText.getText();
                    String category = categoryText.getText();
                    Expense newExpense = new Expense(amount, desc, category);
                    month.addExpense(newExpense);
                    update();
                    playSound(positive);
                } catch (Exception exc) {
                    playSound(error);
                }
            }
        });
    }


    // Modifies: this
    //EFFECTS: if this.month is null, creates a BudgetMonth with user, month, then executes following behaviour, else
    //executes following behaviour.
    //Saves a JSON file of a JSON representation of the BudgetMonth month.  Saves it under the file
    //  ./data/user_month.json
    public void doSaveBudgetMonth() {
        String newUser = this.userText.getText();
        String newMonth = this.monthText.getText();
        if (this.month == null) {
            this.month = new BudgetMonth(newUser, newMonth);
        }
        BudgetMonth newBudget = new BudgetMonth(newUser, newMonth);
        for (Income i : this.month.getIncomeList()) {
            newBudget.addIncome(i);
        }
        for (Expense e : this.month.getExpenseList()) {
            newBudget.addExpense(e);
        }

        try {
            String destination = "./data/" + newUser + "_" + newMonth + ".json";
            jsonWriter = new JsonWriter(destination);

            jsonWriter.open();
            jsonWriter.write(newBudget);
            jsonWriter.close();

        } catch (IOException e) {
            System.out.println("ERROR WRITING JSON FILE, username or month are probably invalid");
        }

    }

    //Modifies this.
    //EFFECTS: Loads a budgetMonth from a json file.
    public void doLoadBudgetMonth(String user, String month) throws IOException {

        String jsonFileName = "./data/" + user + "_" + month + ".json";
        jsonReader = new JsonReader(jsonFileName);

        this.month = jsonReader.read();

        System.out.println("Success: Budget found! ... Returning to main menu");

        update();

    }

    //modifies this
    //updates labels/parts of the GUI that were changed after loading/adding/removing
    public void update() {

        this.totalExpenseLabel.setText("Total: $" + month.totalExpenses());
        this.totalIncomeLabel.setText("Total: $" + month.totalIncome());
        showExpenses(month.getExpenseList());
        showIncomes();
    }

    //modifies this
    //EFFECTS: updates the expenseListPanel to show each expense.
    // if expenseList is not null, displays each expenses data on a new panel
    // adds panel to parent panel, and the parent panel to main panel so the changes are shown.
    // also stylizes the panels.
    // else - do nothing
    public void showExpenses(LinkedList<Expense> expenseList) {
        if (expenseList != null) {
            int num = 1;
            this.expenseListPanel = new JPanel();
            expenseListPanel.setBorder(null);
            for (Expense e : expenseList) {
                String numStuff = num + ":  ";
                String expenseStuff = "$" + e.getAmount() + "  |  " + e.getDescription() + "  |  " + e.getCategory();
                String displayString = numStuff + expenseStuff;
                JPanel tempPanel = new JPanel();
                JLabel tempLabel = new JLabel(displayString);
                tempPanel.setBackground(darkInner);
                tempPanel.setBorder(null);
                tempLabel.setForeground(expenseRed);
                tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                tempPanel.add(tempLabel);
                expenseListPanel.add(tempPanel);
                num++;
            }
            expenseListPanel.setLayout(new BoxLayout(expenseListPanel, BoxLayout.Y_AXIS));
            expensePane = new JScrollPane(expenseListPanel);
            expensePane.setBorder(null);
            expensesPanel.add(expensePane, BorderLayout.CENTER);
        }
    }

    //modifies  this
    //effects: if month.getIncomeList is not null
    // - sets incomeListPanel to a new JPanel,
    // - adds each income to a panel, adds panel to the incomeListPanel
    // - stylizes and adds incomeListPanel to the expense pane, and the pane to mainpanel.
    // else do nothing if null
    public void showIncomes() {
        if (month.getIncomeList() != null) {

            int num = 1;
            this.incomeListPanel = new JPanel();
            incomeListPanel.setBorder(null);

            for (Income i : month.getIncomeList()) {
                String numStuff = num + ":  ";
                String expenseStuff = "$" + i.getAmount() + "  |  " + i.getDescription();
                String displayStuff = numStuff + expenseStuff;

                JPanel tempPanel = new JPanel();
                JLabel tempLabel = new JLabel(displayStuff);
                tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                tempPanel.add(tempLabel);
                tempPanel.setBackground(darkInner);
                tempPanel.setBorder(null);
                incomeListPanel.add(tempPanel);
                tempLabel.setForeground(incomeGreen);
                num++;
            }

            incomeListPanel.setLayout(new BoxLayout(incomeListPanel, BoxLayout.Y_AXIS));
            incomePane = new JScrollPane(incomeListPanel);
            incomePane.setBorder(null);
            incomesPanel.add(incomePane);
        }
    }

    //modifies this
    //effects: calls styling functions
    public void initColors() {
        setBackgrounds();
        setForegrounds();
        changeBorders();
    }

    //modifies this
    //effects: sets the background colour of panels, labels, textfields.
    public void setBackgrounds() {
        saveLoadPanel.setBackground(darkBackground);
        incomeListPanel.setBackground(darkInner);
        monthText.setBackground(darkInner);
        categoryText.setBackground(darkInner);
        descriptionText.setBackground(darkInner);
        amountText.setBackground(darkInner);
        incomesPanel.setBackground(darkBackground);
        expenseListPanel.setBackground(darkInner);
        addButtonsPanel.setBackground(darkBackground);
        expensesPanel.setBackground(darkBackground);
        numExpenseToRemove.setBackground(darkInner);
        numIncomeToRemove.setBackground(darkInner);
        userText.setBackground(darkInner);
        categorizeText.setBackground(darkInner);
    }

    //modifies this
    //EFFECTS: sets the foreground colours of all textFields and labels.
    public void setForegrounds() {
        totalExpenseLabel.setForeground(lightText);
        totalIncomeLabel.setForeground(lightText);
        amountLabel.setForeground(lightText);
        descLabel.setForeground(lightText);
        categoryLabel.setForeground(lightText);
        amountText.setForeground(lightText);
        descriptionText.setForeground(lightText);
        categoryText.setForeground(lightText);
        userText.setForeground(lightText);
        monthText.setForeground(lightText);
        numIncomeToRemove.setForeground(lightText);
        numExpenseToRemove.setForeground(lightText);
        categorizeText.setForeground(lightText);
    }

    //modifies: this
    //effects: changes the borders for panels and JObjects to style app.
    public void changeBorders() {
        categoryText.setBorder(darkBorder);
        descriptionText.setBorder(darkBorder);
        amountText.setBorder(darkBorder);
        userText.setBorder(darkBorder);
        monthText.setBorder(darkBorder);
        addButtonsPanel.setBorder(darkBorder);
        numExpenseToRemove.setBorder(darkBorder);
        numIncomeToRemove.setBorder(darkBorder);
        incomesPanel.setBorder(darkBorder);
        expensesPanel.setBorder(darkBorder);
        saveLoadPanel.setBorder(darkBorder);
        categorizeText.setBorder(darkBorder);
    }

    //modifies: this
    //effects: initializes the feature for the removeIncome button, and calls to initialize remove expense.
    public void initRemoveButtonFeatures() {

        removeIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(numIncomeToRemove.getText());
                try {
                    month.removeIncome(num);
                    playSound(positive);
                } catch (InvalidIndex exc) {
                    playSound(error);
                }
                update();
            }
        });
        initRemoveExpenseButton();
    }

    public void initRemoveExpenseButton() {
        removeExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int num = Integer.parseInt(numExpenseToRemove.getText());
                try {
                    month.removeExpense(num);
                    playSound(positive);
                } catch (InvalidIndex exc) {
                    playSound(error);
                }
                update();

            }
        });
    }
    //

    //modifies this
    //Effects: plays inputted soundFile.
    public void playSound(String soundFile) {
        se.setFile(soundFile);
        se.play();
    }

    //modifies: this
    //EFFECTS: initializes the click feature for categorizeButton.
    //  If the categorizeText fields is empty, plays error sound
    //  Else if the categorized list of expenses is not null, and non-zero size, plays positive sound, displays list
    //  in a new window
    //  else does nothing
    public void initCategorizeButton() {
        categorizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categorizeText.getText();
                LinkedList<Expense> expenseList = month.expenseListOfCategory(category);
                if (categorizeText.getText().equals("")) {
                    playSound(error);
                    System.out.println("emptystring");
                } else if (expenseList != null && expenseList.size() > 0) {
                    playSound(positive);
                    showExpensesOfCategory(
                            month.expenseListToStringList(expenseList),
                            category,
                            month.totalExpensesOfCategory(category));
                }
            }
        });
    }


    //effects: creates a new CategoryWindow with params.
    // displays the users expenses of a certain category in a new window
    public void showExpensesOfCategory(LinkedList<String> expenseList, String category, double totalExpense) {
        this.categoryWindow = new CategoryWindow(expenseList, category, totalExpense);
    }
}

