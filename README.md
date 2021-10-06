
# My Personal Project

***

## A Budgeting app!

This app is going to help people budget their money by tracking their income and expenses.
This app will be designed for people of all ages, but will be especially useful for students
who need assistance in managing their money.  This project is of interest to me because I
believe it will help people manage their money better.  Additionally, this project will
enable me to learn new things such as implementing a graphical user interface.

***

**Features** I want to incorporate in the app:
- Track monthly income via user entry.
- Track monthly expenses via user entry.
- Categorize expenses into groups such as *entertainment, transportation, utility...*
- Display pie chart of expense categories to show what you spend your money on.
- Display a monthly overview of income and expenses, and if you saved more than you spent or vice versa.
- Allow user to input an item, it's price, and how long from now they want it, and receive deposit goals for different
  time periods.
- The ability to register an account and login.
- Store data between sessions.
- View data from previous months

***

## User Stories

***

- As a user, I want to be able to add a source of **income** to my monthly budget.
- As a user, I want to be able to add an **expense** to my monthly budget.
- As a user, I want to be able to see my total income.
- As a user, I want to be able to see my total expenses.
- As a user, I want to be able to see all of my income sources in my budget.
- As a user, I want to be able to see all of my expenses in my budget.
- As a user, I want to be able to categorize my expenses.
- As a user, I want to be able to see my expenses of a certain category, and the total amount.
- As a user, I want to be able to save my BudgetMonth, which contains my Income and Expenses.
- As a user, I want to be able to retrieve my BudgetMonth and be able to access Incomes and Expenses previously added.
- As a user, I want to be able to remove an income
- As a user, I want to be able to remove an expense


## Phase 4: Task 2

***

I made my **BudgetMonth** class robust by adding a new exception type InvalidIndex.  This was thrown by my removeIncome
and removeExpense methods inside BudgetMonth.  These were then caught in the GUI class inside the button press functions
for removeIncomeButton and removeExpenseButton. 

***

## Phase 4: Task 3

There are a few changes I would make.  
- I would make a new class which would control the styling/colouring of panels and JObjects (button, text fields...).
- I would make a new class for each of the sub panels in the main panel. 
- I would re-implement my totalExpense method to avoid duplicate code.

Making new classes for sub-panels and styling would improve cohesion, and also make the code more organized.

Additionally, in BudgetMonth, I would refactor my totalExpense method to take in a List<Expense> so that the
totalExpenseOfCategory method could simply call totalExpense with the categorized list as a parameter.
This would reduce some duplicated code and also make the totalExpense method more useful.

Income and Expense class' are very similar in terms of functionality, and what we want to do with them.
Because of this, it would be optimal to introduce some forms of abstraction between the two.  For example,
the methods incomeListToStringList and expenseListToStringList are very similar, the only difference is the extra String
for the category of the Expense.  Creating an interface or abstract class to be a supertype for Income and Expense 
objects would allow us to create a single method that would work on both types, removing duplicate code.





