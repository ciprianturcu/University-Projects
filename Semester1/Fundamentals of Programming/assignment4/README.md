# 💻 Assignment 4
## New Requirements
- Continue the problem statement started at **A3** by adding its code to this repository
- Implement **modular programming** by having a **UI** module, a **Functions** module and a **Start** module
## Common with A3
- Use procedural programing and the simple feature-driven software development process
- Provide a console-based user interface that accepts given commands **exactly** as required, and works when additional whitespaces are included
- Use built-in compound types to represent entities in the problem domain and access them using *getter* and *setter* functions
- Have at least 10 items in your application at startup
- Provide **specification** and **tests** for all non-UI functions related to every functionality

## Problem Statement

### Bank Account
John wants to manage his bank account. To do this, he needs an application to store all the bank transactions performed on his account during a month. Each transaction is stored in the application using the following elements: `day` (of the month in which the transaction was made, between 1 and 30 for simplicity), `amount of money` (transferred, positive integer), `type` (`in` - into the account, `out` – from the account), and `description`. Write a program that implements the functionalities exemplified below:

**(A) Add transaction**\
`add <value> <type> <description>`\
`insert <day> <value> <type> <description>`\
e.g.\
`add 100 out pizza` – add to the current day an `out` transaction of `100 RON` with the *"pizza"* description\
`insert 25 100 in salary` – insert to day 25 an `in` transaction of `100 RON` with the *“salary”* description

**(B) Modify transactions**\
`remove <day>`\
`remove <start day> to <end day>`\
`remove <type>`\
`replace <day> <type> <description> with <value>`\
e.g.\
`remove 15` – remove all transactions from day 15\
`remove 5 to 10` – remove all transactions between days 5 and 10\
`remove in` – remove all `in` transactions\
`replace 12 in salary with 2000` – replace the amount for the `in` transaction having the *“salary”* description from day 12 with `2000 RON`

**(C) Display transactions having different properties**\
`list`\
`list <type>`\
`list [ < | = | > ] <value>`\
`list balance <day>`\
e.g.\
`list` – display all transactions\
`list in` – display all `in` transactions\
`list > 100` - display all transactions having an amount of money `>100`\
`list = 67` - display all transactions having an amount of money `=67`\
`list balance 10` – compute the account’s balance at the end of day 10. This is the sum of all `in` transactions, from which we subtract `out` transactions occurring before or on day 10

**(D) Obtain different characteristics of the transactions**\
`sum <type>`\
`max <type> <day>`\
e.g.\
`sum in` – display the total amount from in transactions\
`max out 15` – display the maximum out transaction on day 15

**(E) Filter**\
`filter <type>`\
`filter <type> <value>`\
e.g.\
`filter in` – keep only `in` transactions\
`filter in 100` – keep only `in` transactions having an amount of money smaller than `100 RON`

**(F) Undo**\
`undo` – the last operation that modified program data is reversed. The user can undo all operations performed since program start by repeatedly calling this function.

---
### 5. Apartment Building Administrator
Jane is the administrator of an apartment building and she wants to manage the monthly expenses for each apartment. Each expense is stored using the following elements: `apartment` (*number of apartment, positive integer*), `amount` (*positive integer*), `type` (*from one of the predefined categories `water`, `heating`, `electricity`, `gas` and `other`*). Write a program that implements the functionalities exemplified below:

**(A) Add new transaction**\
`add <apartment> <type> <amount>`\
e.g.\
`add 25 gas 100` – add to apartment 25 an expense for `gas` in amount of `100 RON`

**(B) Modify expenses**\
`remove <apartment>`\
`remove <start apartment> to <end apartment>`\
`remove <type>`\
`replace <apartment> <type> with <amount>`\
e.g.\
`remove 15` – remove all expenses for apartment 15\
`remove 5 to 10` – remove all expenses for apartments between 5 and 10\
`remove gas` – remove all `gas` expenses from all apartments\
`replace 12 gas with 200` – replace the amount of the expense with type `gas` for apartment 12 with `200 RON`

**(C)	Display expenses having different properties**\
`list`\
`list <apartment>`\
`list [ < | = | > ] <amount>`\
e.g.\
`list` – display all expenses\
`list 15` – display all expenses for apartment 15\
`list > 100` - display all apartments having total expenses `>100 RON`\
`list = 17` - display all apartments having total expenses `=17 RON`

**(D) Obtain different characteristics of the expenses**\
`sum <type>`\
`max <apartment>`\
`sort apartment`\
`sort type`\
e.g.\
`sum gas` – display the total amount for the expenses having type `gas`\
`max 25` – display the maximum amount per each expense type for apartment `25`\
`sort apartment` – display the list of apartments sorted ascending by total amount of expenses\
`sort type` – display the total amount of expenses for each type, sorted ascending by amount of money

**(E) Filter**\
`filter <type>`\
`filter <value>`\
e.g.\
`filter gas` – keep only expenses for `gas`\
`filter 300` – keep only expenses having an amount of money smaller than 300 RON

**(F) Undo**\
`undo` – the last operation that modified program data is reversed. The user can undo all operations performed since program start by repeatedly calling this function.
