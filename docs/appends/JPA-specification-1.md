# How to filter Java 2026

- [Specifications :: Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [AlbertProfe/userBorrowBookFilter · GitHub](https://github.com/AlbertProfe/userBorrowBookFilter)
- [repository/BorrowSpecification.java](https://github.com/AlbertProfe/userBorrowBookFilter/blob/master/userBorrowBookFilter/src/main/java/com/example/userBorrowBook/repository/BorrowSpecification.java)
- [BorrowController.java](https://github.com/AlbertProfe/userBorrowBookFilter/blob/master/userBorrowBookFilter/src/main/java/com/example/userBorrowBook/controller/BorrowController.java)

## Think of it like this:

You have a List<Book> with 50,000 books.

You want to find books that match **many conditions at the same time**, for example:

- title contains "Harry"
- isbn starts with "978"
- price < 30
- published after 2015
- in stock == true
- NOT in category "children"

If you write normal Java code, you usually end up with something ugly like this:

```java
List<Book> result = new ArrayList<>();
for (Book b : allBooks) {
    if (b.getTitle().contains("Harry") &&
        b.getIsbn().startsWith("978") &&
        b.getPrice() < 30 &&
        b.getPublishYear() > 2015 &&
        b.isInStock() &&
        !b.getCategory().equals("children")) {
        result.add(b);
    }
}
```

→ Very fast to write → very painful to reuse / combine / test / change later

## The "Specification container" idea

Instead of one big if-monster, you create small, independent pieces — each piece is responsible for **one single decision** (true/false).

We call each piece a **Specification**.

And then you have a way to **put many Specifications inside one container** and say:  
"Give me only books where ALL these little judges say YES".

In plain Java (no Spring), it could look like this:

```java
// One small decision maker
class TitleContains implements Specification<Book> {
    private String word;
    public TitleContains(String word) { this.word = word; }

    public boolean isSatisfiedBy(Book book) {
        return book.getTitle() != null && 
               book.getTitle().toLowerCase().contains(word.toLowerCase());
    }
}

// Another small decision maker
class PriceLowerThan implements Specification<Book> {
    private double maxPrice;
    public PriceLowerThan(double max) { this.maxPrice = max; }

    public boolean isSatisfiedBy(Book book) {
        return book.getPrice() < maxPrice;
    }
}

// The magic container that combines them
class AndSpecification<T> implements Specification<T> {
    private List<Specification<T>> specs = new ArrayList<>();

    public AndSpecification(Specification<T>... specifications) {
        specs.addAll(Arrays.asList(specifications));
    }

    public boolean isSatisfiedBy(T candidate) {
        for (Specification<T> spec : specs) {
            if (!spec.isSatisfiedBy(candidate)) {
                return false;
            }
        }
        return true;
    }
}
```

And now you use it like this:

```java
Specification<Book> goodBooks = new AndSpecification<>(
    new TitleContains("harry"),
    new PriceLowerThan(30),
    new PublishedAfter(2015),
    new InStock()
);

List<Book> result = new ArrayList<>();
for (Book b : allBooks) {
    if (goodBooks.isSatisfiedBy(b)) {
        result.add(b);
    }
}
```

### What your Spring code is actually doing

The thing called `Specification<Borrow>` in Spring Data JPA is **exactly this idea** — but instead of returning `boolean`, it returns a **database WHERE clause piece** (Predicate).

So when you write:

```java
return (root, query, cb) -> {
    Predicate p = cb.conjunction();           // start with TRUE
    if (bookTitle != null) 
        p = cb.and(p, cb.like(...));
    if (isbn != null) 
        p = cb.and(p, cb.equal(...));
    ...
    return p;
};
```

> You're building a **container of little database conditions** that can be combined with AND / OR / NOT very flexibly.

### Summary — container analogy for a Java programmer

Specification = **a small object that knows how to say YES or NO to one single question**

Many Specifications + And/Or container = **a smart box that collects many little yes/no judges and only lets pass candidates that satisfy the whole jury**

> Spring just makes that box speak SQL instead of plain Java booleans.
