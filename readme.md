# HPG — Hibernate N+1 Queries Demo (Spring Boot + JPA)

This project is a small Spring Boot application created to **demonstrate the Hibernate/JPA N+1 query problem** and explore common, practical ways to **avoid N+1**.

It uses a simple domain model:

- `Author` (1) → (N) `Book`
- `Author.books` is `LAZY`
- `Book.author` is `LAZY`

That setup makes it easy to reproduce N+1 on both sides of the relationship.

---

## What is the N+1 problem?

The N+1 problem usually happens when you:

1. Run **1 query** to load a list of parent entities (e.g., all authors)
2. Then, for each parent, Hibernate triggers **1 additional query** to load its children (e.g., the books)

So you get:

- 1 query for the list
- + N additional queries (often one per entity in the list)

This becomes a performance bottleneck quickly.

---

## How this project demonstrates N+1

### 1) N+1 on the collection side (`Author -> books`)
Typical pattern:

- Load `List<Author>` with `findAll()`
- Convert to DTO and access `author.getBooks()`

Result:
- 1 query for authors
- N queries for books (one query per author)

### 2) N+1 on the many-to-one side (`Book -> author`)
Typical pattern:

- Load `List<Book>` with `findAll()`
- Convert to DTO and access `book.getAuthor()`

Result:
- 1 query for books
- then many queries for authors (often one per distinct author referenced by those books)

---

## How to run

### Start the app
Run the Spring Boot application from your IDE or with Gradle:

- Run the main class: `HpgApplication`
- Or run `bootRun`

The app runs on embedded Tomcat (usually `http://localhost:8080`).

---

## Seeding data

You can create data via HTTP using the controller endpoints.

### Create one author with books
`POST /api/authors`


### Bulk create multiple authors with books
`POST /api/authors/bulk`

sample request body:
```json
[
  {
    "name": "Author 1",
    "books": [
      { "title": "Book 1.1" },
      { "title": "Book 1.2" },
      { "title": "Book 1.3" }
    ]
  },
  {
    "name": "Author 2",
    "books": [
      { "title": "Book 2.1" },
      { "title": "Book 2.2" }
    ]
  },
  {
    "name": "Author 3",
    "books": [
      { "title": "Book 3.1" }
    ]
  }
]