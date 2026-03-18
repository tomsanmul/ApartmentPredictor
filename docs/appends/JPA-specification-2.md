# Criteria API: Specification

- [Specifications :: Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [AlbertProfe/userBorrowBookFilter · GitHub](https://github.com/AlbertProfe/userBorrowBookFilter)
- [repository/BorrowSpecification.java](https://github.com/AlbertProfe/userBorrowBookFilter/blob/master/userBorrowBookFilter/src/main/java/com/example/userBorrowBook/repository/BorrowSpecification.java)
- [BorrowController.java](https://github.com/AlbertProfe/userBorrowBookFilter/blob/master/userBorrowBookFilter/src/main/java/com/example/userBorrowBook/controller/BorrowController.java)

## Criteria API

**Criteria API** is a way in Java (JPA / Spring Data JPA) to build database queries **using regular Java code** instead of writing <mark>strings</mark>.

Instead of writing this (old style – string):

```java
"SELECT b FROM Borrow b WHERE b.book.title LIKE '%Harry%' AND b.returned = false"
```

You write normal `Java objects` and methods like this:

```java
CriteriaBuilder cb = entityManager.getCriteriaBuilder();
CriteriaQuery<Borrow> query = cb.createQuery(Borrow.class);
Root<Borrow> borrow = query.from(Borrow.class);

Predicate condition1 = cb.like(borrow.get("book").get("title"), "%Harry%");
Predicate condition2 = cb.equal(borrow.get("returned"), false);

query.where(condition1, condition2);

List<Borrow> results = entityManager.createQuery(query).getResultList();
```

People usually compare it to these three:

- **JPQL**         → writing query as string (like mini-SQL)  
- **Native query** → real SQL string (most powerful, but no safety)  
- **Criteria API** → building query with Java objects & methods (safest + dynamic)

So in one sentence:

**Criteria API = "write database search using Java code instead of strings, so it's safer and easier when the search rules change a lot."**

## Specification

Imagine you’re building a search form for borrowed books, and the user can fill in **any combination** of these fields:

- book title  
- ISBN  
- is the book still available?  
- user younger than X years?  
- archived users or not?  
- exact date of birth  
- returned or not returned

> You **don’t** want to write 2⁷ = 128 different repository methods or huge if-else blocks with native SQL.

Spring Data JPA **Specifications** let you build the SQL query **dynamically**, piece by piece.

The four most important actors

| Word                | Simple meaning                                 | What it really does in your code                                     |
|:------------------- |:---------------------------------------------- |:-------------------------------------------------------------------- |
| **Root**            | The main table you start from                  | `Root<Borrow>` → “We are starting from the Borrow table”             |
| **Join**            | Go to another related table                    | `root.join("book")` → “Give me the Book that belongs to this Borrow” |
| **Predicate**       | One condition / one WHERE piece                | `book.title LIKE '%Harry%'`   or   `user.age < 18`                   |
| **CriteriaBuilder** | The helper that knows how to create conditions | The factory: “make me a LIKE”, “make me an =”, “make me an AND”      |

### Step-by-step

1. You say: “I want to search in the **Borrow** table”  
   → `Root<Borrow> root`

2. You need fields from other tables too  
   → You make bridges (joins):  
   
   ```java
   Join<Borrow, Book> bookJoin   = root.join("book");
   Join<Borrow, UserApp> userJoin = root.join("user");
   ```
   
   Now you can reach `book.title`, `user.age`, etc.

3. You start with an **empty condition list**  
   
   ```java
   Predicate predicate = criteriaBuilder.conjunction();   
   // this means "true" for now (no filter)
   ```

4. For **every field the user actually filled in**, you add one more condition:
   
   ```java
   if (bookTitle is filled) {
       add condition:  book.title  LIKE  '%value%'
   }
   
   if (userAge is filled) {
       add condition:  user.age  <  value
   }
   
   if (returned is filled) {
       add condition:  borrow.returned  =  true/false
   }
   ```
   
   Every time you do:
   
   ```java
   predicate = criteriaBuilder.and(predicate, newCondition);
   ```
   
   → You’re saying: “take what we already have AND also this new rule”

5. At the end you give back **one big combined condition**  
   
   ```java
   return predicate;
   ```
   
   Spring turns this into:
   
   ```sql
   SELECT * FROM borrow b
   INNER JOIN book bk ON b.book_id = bk.id
   INNER JOIN user u  ON b.user_id = u.id
   WHERE bk.title LIKE '%potter%'
     AND u.age < 20
     AND b.returned = false
     ...
   ```

### Super short summary: the core idea

- **Root**          = starting table  
- **Join**           = go to related table (book, user)  
- **Predicate**      = one small condition (age < 18, title like …)  
- **CriteriaBuilder** = the toolbox (“make LIKE”, “make <”, “make AND”, “make OR”)  
- You collect many small Predicates → glue them with **AND** → return one big Predicate  
- Spring automatically turns it into correct SQL

<mark>Quick memory rule</mark>

**If the interface has only ONE method → use arrow →** (That's exactly what Specification is — one method only)

```java
return (root, query, cb) -> { ... real logic: predicates ... };
```

## Why arrow function?

The `Specification<Borrow>` interface is a **functional interface** — it has **only one method** that you need to implement:

```java
Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
```

Because it has **exactly one abstract method**, Java lets you write it in two styles:

### Style 1: Old classical way (anonymous class)

```java
public static Specification<Borrow> filterByParameters(...) {
    return new Specification<Borrow>() {          // ← new + interface name + {}
        @Override
        public Predicate toPredicate(
                Root<Borrow> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) {

            Predicate p = cb.conjunction();
            // ... 20–30 lines of code ...
            return p;
        }
    };
}
```

→ Lots of ceremony: `new Specification<...> { @Override public ... }`

### Style 2: Modern arrow way (lambda)

```java
public static Specification<Borrow> filterByParameters(...) {
    return (root, query, cb) -> {               // ← just (params) -> { ... }
        Predicate p = cb.conjunction();
        // ... same 20–30 lines ...
        return p;
    };
}
```
