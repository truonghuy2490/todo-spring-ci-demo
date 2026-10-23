# 1. How can i break down the problem before prompt?

Before coding or prompting, I alway have a routine that read carefully the requirement and make sure i can fully understand it. So that, i specify those requirement includes add, update, delete task, complete task and save data after rerun the application. Because time limit was 195 min, so i divide task into small piece and do step by step:
1. initialize project
2. build model and repository 
3. implementation service 
4. write unit test for service 
5. write controller mvc and finish thymleaf template for UI

I priority to finish all the core function/requirement first then handle the UX/UI design and error pop up. For each part, i give a current context and specific of requirements to make sure the instruction was fit and compatibilty. Instead of write me all for once. 

# 2. What did AI get wrong and how do you fix it ?

The AI ​​might provide solutions that are inconsistent across layers or don't fully consider how data is sent from the interface to the backend. For example, the initial task update method might only change the title without considering the completed state. To fix this, I should provide more context and clear requirements. When making changes, I should also ask AI to check how the changes affect other parts of the application.

# 3. What did you deliberately not delegate to AI, and Why ?

I don't completely delegate the task of defining requirements, choosing the deployment order, and deciding when a function is considered complete to the AI. I also don't assume that AI-generated code is correct by default. My own understanding of CRUD logic, dependency injection, JPA, and testing methods allows me to explain technical decisions and detect errors, rather than simply creating an application that appears to work.

# 4. What would you do different with more time? 

With more time, I would follow the same approach: understand the requirements first, make a plan, and then work step by step. I would focus on the core functions first, followed by UI/UX design and more testing. I would also add integration tests for the controller, check that data is still saved after restarting the application, and test cases such as missing task IDs and empty titles. Finally, I would improve input validation, UI accessibility, and the README file.
