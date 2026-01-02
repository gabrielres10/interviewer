-- Initial data ingestion for Interview Application
-- Study Areas, Topics, and Questions

-- ==============================================
-- STUDY AREAS
-- ==============================================
INSERT INTO study_area (name, description) VALUES 
('Java', 'Core Java programming language concepts, features, and best practices'),
('Spring Framework', 'Spring ecosystem including Spring Boot, Spring MVC, and Spring Data'),
('Databases', 'Database concepts, SQL, and database design principles'),
('Data Structures', 'Fundamental data structures and their implementations'),
('Algorithms', 'Algorithm design, analysis, and problem-solving techniques'),
('Design Patterns', 'Software design patterns and architectural principles');

-- ==============================================
-- TOPICS
-- ==============================================
-- Java Topics
INSERT INTO topic (name, study_area_id) VALUES 
('Collections Framework', (SELECT id FROM study_area WHERE name = 'Java')),
('Multithreading', (SELECT id FROM study_area WHERE name = 'Java')),
('Java 8+ Features', (SELECT id FROM study_area WHERE name = 'Java')),
('Exception Handling', (SELECT id FROM study_area WHERE name = 'Java')),
('OOP Principles', (SELECT id FROM study_area WHERE name = 'Java'));

-- Spring Framework Topics
INSERT INTO topic (name, study_area_id) VALUES 
('Spring Boot', (SELECT id FROM study_area WHERE name = 'Spring Framework')),
('Dependency Injection', (SELECT id FROM study_area WHERE name = 'Spring Framework')),
('Spring Data JPA', (SELECT id FROM study_area WHERE name = 'Spring Framework')),
('REST APIs', (SELECT id FROM study_area WHERE name = 'Spring Framework'));

-- Database Topics
INSERT INTO topic (name, study_area_id) VALUES 
('SQL Queries', (SELECT id FROM study_area WHERE name = 'Databases')),
('Database Normalization', (SELECT id FROM study_area WHERE name = 'Databases')),
('Transactions', (SELECT id FROM study_area WHERE name = 'Databases')),
('Indexing', (SELECT id FROM study_area WHERE name = 'Databases'));

-- Data Structures Topics
INSERT INTO topic (name, study_area_id) VALUES 
('Arrays and Lists', (SELECT id FROM study_area WHERE name = 'Data Structures')),
('Trees', (SELECT id FROM study_area WHERE name = 'Data Structures')),
('Hash Tables', (SELECT id FROM study_area WHERE name = 'Data Structures')),
('Graphs', (SELECT id FROM study_area WHERE name = 'Data Structures'));

-- Algorithms Topics
INSERT INTO topic (name, study_area_id) VALUES 
('Sorting Algorithms', (SELECT id FROM study_area WHERE name = 'Algorithms')),
('Searching Algorithms', (SELECT id FROM study_area WHERE name = 'Algorithms')),
('Dynamic Programming', (SELECT id FROM study_area WHERE name = 'Algorithms'));

-- Design Patterns Topics
INSERT INTO topic (name, study_area_id) VALUES 
('Creational Patterns', (SELECT id FROM study_area WHERE name = 'Design Patterns')),
('Structural Patterns', (SELECT id FROM study_area WHERE name = 'Design Patterns')),
('Behavioral Patterns', (SELECT id FROM study_area WHERE name = 'Design Patterns'));

-- ==============================================
-- QUESTIONS
-- ==============================================

-- Java - Collections Framework Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the difference between ArrayList and LinkedList?', 
'ArrayList uses a dynamic array internally, providing fast random access (O(1)) but slower insertions/deletions in the middle (O(n)). LinkedList uses a doubly-linked list, offering faster insertions/deletions (O(1)) but slower random access (O(n)). ArrayList is better for frequent reads, while LinkedList is better for frequent insertions/deletions.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Collections Framework'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the difference between HashMap and ConcurrentHashMap?', 
'HashMap is not thread-safe and allows one null key and multiple null values. ConcurrentHashMap is thread-safe, uses fine-grained locking (segment-based in Java 7, node-based in Java 8+), does not allow null keys or values, and provides better concurrent performance than synchronized HashMap.',
'HARD',
(SELECT id FROM topic WHERE name = 'Collections Framework'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the difference between Set and List interfaces.', 
'List is an ordered collection that allows duplicate elements and provides positional access. Set is an unordered collection (except LinkedHashSet and TreeSet) that does not allow duplicate elements. List implementations include ArrayList and LinkedList, while Set implementations include HashSet, LinkedHashSet, and TreeSet.',
'EASY',
(SELECT id FROM topic WHERE name = 'Collections Framework'));

-- Java - Multithreading Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the difference between synchronized method and synchronized block?', 
'A synchronized method locks the entire method using the object monitor. A synchronized block allows you to lock only a specific section of code and can use any object as a lock. Synchronized blocks provide more fine-grained control, better performance, and can reduce contention by minimizing the critical section.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Multithreading'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the concept of volatile keyword in Java.', 
'The volatile keyword ensures that a variable is always read from and written to main memory, preventing thread caching. It guarantees visibility of changes across threads but does not guarantee atomicity. It is useful for flags and status indicators but not for compound operations like increment.',
'HARD',
(SELECT id FROM topic WHERE name = 'Multithreading'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is a Thread in Java?', 
'A Thread is the smallest unit of execution in a program. In Java, threads can be created by extending the Thread class or implementing the Runnable interface. Threads allow concurrent execution of code, enabling better utilization of CPU resources.',
'EASY',
(SELECT id FROM topic WHERE name = 'Multithreading'));

-- Java - Java 8+ Features Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What are Lambda expressions and why are they useful?', 
'Lambda expressions are anonymous functions that provide a concise way to represent functional interfaces. They enable functional programming in Java, reduce boilerplate code, and make code more readable. Syntax: (parameters) -> expression or (parameters) -> { statements }. They are commonly used with Streams API and collections.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Java 8+ Features'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the Stream API and its benefits.', 
'The Stream API processes sequences of elements in a functional style. It supports operations like filter, map, reduce, and collect. Benefits include: declarative code, parallel processing support, lazy evaluation, and better readability. Streams do not store data and can be used only once.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Java 8+ Features'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is Optional in Java 8?', 
'Optional is a container object that may or may not contain a non-null value. It helps avoid NullPointerException and makes null-handling explicit. Methods include isPresent(), ifPresent(), orElse(), orElseGet(), and orElseThrow(). It encourages better API design and clearer intent.',
'EASY',
(SELECT id FROM topic WHERE name = 'Java 8+ Features'));

-- Spring Framework - Spring Boot Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is Spring Boot and what are its advantages?', 
'Spring Boot is an opinionated framework that simplifies Spring application development. Advantages: auto-configuration, embedded servers (Tomcat, Jetty), starter dependencies, production-ready features (actuator, metrics), minimal configuration, and convention over configuration approach.',
'EASY',
(SELECT id FROM topic WHERE name = 'Spring Boot'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the concept of Auto-configuration in Spring Boot.', 
'Auto-configuration automatically configures Spring application based on classpath dependencies. It uses @EnableAutoConfiguration and conditional annotations (@ConditionalOnClass, @ConditionalOnMissingBean) to apply configurations only when specific conditions are met. It can be customized or disabled using properties or exclusions.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Spring Boot'));

-- Spring Framework - Dependency Injection Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is Dependency Injection and why is it important?', 
'Dependency Injection is a design pattern where objects receive their dependencies from external sources rather than creating them. Benefits: loose coupling, easier testing (mock dependencies), better maintainability, and adherence to SOLID principles. Spring provides DI through constructor, setter, and field injection.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Dependency Injection'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What are the different types of dependency injection in Spring?', 
'Spring supports three types: 1) Constructor Injection - dependencies injected through constructor (recommended, ensures immutability). 2) Setter Injection - dependencies injected through setter methods (allows optional dependencies). 3) Field Injection - dependencies injected directly into fields using @Autowired (not recommended due to testability issues).',
'EASY',
(SELECT id FROM topic WHERE name = 'Dependency Injection'));

-- Spring Framework - Spring Data JPA Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is Spring Data JPA and how does it simplify database access?', 
'Spring Data JPA is a framework that reduces boilerplate code for data access layers. It provides repository interfaces with automatic implementation, query derivation from method names, custom query support with @Query, pagination and sorting support, and auditing capabilities. It abstracts common CRUD operations.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Spring Data JPA'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the difference between @OneToMany and @ManyToOne relationships.', 
'@OneToMany represents a relationship where one entity is associated with multiple instances of another entity (e.g., one Department has many Employees). @ManyToOne is the inverse, where multiple instances relate to one entity (many Employees belong to one Department). Typically used together with mappedBy attribute.',
'HARD',
(SELECT id FROM topic WHERE name = 'Spring Data JPA'));

-- Databases - SQL Queries Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the difference between INNER JOIN and LEFT JOIN?', 
'INNER JOIN returns only matching rows from both tables. LEFT JOIN returns all rows from the left table and matching rows from the right table; for non-matching rows, NULL values are returned for right table columns. LEFT JOIN is useful when you want to preserve all records from the main table.',
'EASY',
(SELECT id FROM topic WHERE name = 'SQL Queries'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the GROUP BY clause and aggregate functions.', 
'GROUP BY groups rows with the same values into summary rows. It is used with aggregate functions like COUNT(), SUM(), AVG(), MAX(), MIN(). For example, "SELECT department, COUNT(*) FROM employees GROUP BY department" counts employees per department. HAVING clause filters grouped results.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'SQL Queries'));

-- Databases - Indexing Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is a database index and why is it important?', 
'An index is a data structure that improves query performance by providing quick lookups. Like a book index, it allows the database to find rows without scanning the entire table. Indexes speed up SELECT queries but slow down INSERT, UPDATE, and DELETE operations. Common types include B-tree and hash indexes.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Indexing'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the difference between clustered and non-clustered indexes?', 
'A clustered index determines the physical order of data in a table (one per table). A non-clustered index creates a separate structure with pointers to data rows (multiple per table). Clustered indexes are faster for range queries, while non-clustered indexes are better for specific lookups.',
'HARD',
(SELECT id FROM topic WHERE name = 'Indexing'));

-- Data Structures - Arrays and Lists Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the time complexity of accessing an element in an array?', 
'O(1) constant time. Arrays provide direct access to elements using index calculation: base_address + (index * element_size). This makes array access very fast regardless of array size.',
'EASY',
(SELECT id FROM topic WHERE name = 'Arrays and Lists'));

-- Data Structures - Trees Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is a Binary Search Tree (BST) and what are its properties?', 
'A BST is a binary tree where each node has at most two children, and for each node: all values in the left subtree are less than the node value, and all values in the right subtree are greater. This property enables efficient searching, insertion, and deletion with O(log n) average time complexity.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Trees'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain tree traversal methods: Inorder, Preorder, and Postorder.', 
'Inorder (Left-Root-Right): visits left subtree, then root, then right subtree; produces sorted sequence for BST. Preorder (Root-Left-Right): visits root first; used for tree copying. Postorder (Left-Right-Root): visits root last; used for tree deletion. Each has specific use cases in tree algorithms.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Trees'));

-- Algorithms - Sorting Algorithms Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain QuickSort algorithm and its time complexity.', 
'QuickSort is a divide-and-conquer algorithm that selects a pivot element, partitions the array around it (elements smaller on left, larger on right), and recursively sorts the partitions. Average time complexity: O(n log n), Worst case: O(n²), Space: O(log n). It is generally faster than mergesort in practice.',
'HARD',
(SELECT id FROM topic WHERE name = 'Sorting Algorithms'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the difference between stable and unstable sorting algorithms?', 
'A stable sort maintains the relative order of equal elements. For example, if two elements are equal, they appear in the same order in the sorted output as in the input. Stable sorts: Merge Sort, Insertion Sort. Unstable sorts: Quick Sort, Heap Sort. Stability matters when sorting by multiple criteria.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Sorting Algorithms'));

-- Algorithms - Dynamic Programming Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is Dynamic Programming and when should it be used?', 
'Dynamic Programming is an optimization technique that solves complex problems by breaking them into simpler subproblems and storing their results to avoid redundant calculations. Use when: problem has overlapping subproblems, optimal substructure exists. Two approaches: top-down (memoization) and bottom-up (tabulation). Examples: Fibonacci, knapsack, longest common subsequence.',
'HARD',
(SELECT id FROM topic WHERE name = 'Dynamic Programming'));

-- Design Patterns - Creational Patterns Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the Singleton pattern and its use cases.', 
'Singleton ensures a class has only one instance and provides global access to it. Implementation: private constructor, static instance variable, getInstance() method. Use cases: configuration managers, logging, database connections, thread pools. Consider thread-safety (double-checked locking, enum singleton).',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Creational Patterns'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the Factory Pattern?', 
'Factory Pattern provides an interface for creating objects without specifying their exact classes. It encapsulates object creation logic, promoting loose coupling. Types: Simple Factory, Factory Method, Abstract Factory. Benefits: centralized creation logic, easier maintenance, supports open/closed principle.',
'EASY',
(SELECT id FROM topic WHERE name = 'Creational Patterns'));

-- Design Patterns - Structural Patterns Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the Adapter pattern with an example.', 
'Adapter pattern converts the interface of a class into another interface expected by clients. It allows incompatible interfaces to work together. Example: adapting a third-party library to your application interface, or wrapping legacy code. Can be implemented as class adapter (inheritance) or object adapter (composition).',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Structural Patterns'));

-- Design Patterns - Behavioral Patterns Questions
INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('What is the Observer pattern and where is it used?', 
'Observer pattern defines a one-to-many dependency where when one object (subject) changes state, all dependents (observers) are notified automatically. Used in event handling systems, MVC architecture, and reactive programming. Java provides Observable class and Observer interface, though now deprecated in favor of other mechanisms.',
'MEDIUM',
(SELECT id FROM topic WHERE name = 'Behavioral Patterns'));

INSERT INTO question (question_text, answer, difficulty, topic_id) VALUES 
('Explain the Strategy pattern.', 
'Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. It allows the algorithm to vary independently from clients using it. Example: different sorting strategies, payment methods, or compression algorithms. Promotes open/closed principle and eliminates conditional statements.',
'HARD',
(SELECT id FROM topic WHERE name = 'Behavioral Patterns'));