# Restaurant Ordering System

## A System for Ordering Food In a Restaurant



*Intro*
- This is a project for Ordering system in a restaurant.  

*User*
- Staffs in the restaurant can order food through the application.
- Users would be restaurant staffs.
- It might give me a great understanding on interacting with an interface with data at the back, how they can be set and get, and how to display
them properly.  

*User Stories*
- As a user, I want to be able to add items from my menu to orders.
- As a user, I want to be able to display price and the order list after finishing orders.
- As a user, I want to be able to help customers to adjust their orders while ordering.
- As a user, I want to be able to know different orders from 10 different tables.
- As a user, I want to be able to remove the order when it is finished.
- As a user, I want to be able to save modified menu to a file.
- As a user, I want to be able to load modified menu from a file.

*Phase 4: Task2*
- I choose to add a NegativePriceException to constructor and set price methods in Item class.
    The exception will be thrown when the price from parameter is less than 0.
    
*Phase 4: Task3*
- The most thing I want to refactor is the TabbedPaneGUI class. There are a lot of duplicate methods
that I think should be refactored, and I would also create more classes to handle different tasks in there.
- I would also create an abstract class for Order and Menu class since they both have some similar methods,
and often being used in the same way.