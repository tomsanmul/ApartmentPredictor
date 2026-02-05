# Populate Apartments by REST API

## Goal

> Provide a REST endpoint that inserts a number of fake `Apartment` rows into the database.

You call:

```
GET /api/apartment/populate?qty=50
```

and the backend creates `qty` new `Apartment` entities.

## Code

- **REST controller**
  - `src/main/java/com/example/apartment_predictor/controller/ApartmentRestController.java`
- **Population component (DB seeder)**
  - `src/main/java/com/example/apartment_predictor/utils/PopulateDB.java`
- **Persistence/service layer**
  - `src/main/java/com/example/apartment_predictor/service/ApartmentService.java`
- **Entity**
  - `src/main/java/com/example/apartment_predictor/model/Apartment.java`

## Data generation strategy

This project currently generates <mark>random</mark> values using `ThreadLocalRandom`.

The `Apartment` entity stores many boolean-like values as `String` fields:

- `mainroad`, `guestroom`, `basement`, `hotwaterheating`, `airconditioning`, `prefarea`

So the generator should use values like:

- `"yes"` or `"no"`

`furnishingstatus` is also a `String`, commonly one of:

- `"furnished"`
- `"semi-furnished"`
- `"unfurnished"`

## Implementation steps

### 1) Create a Spring component to populate the DB

File: `PopulateDB.java`

Responsibilities:

- Accept a quantity `qty`
- Create `qty` new `Apartment` objects
- Persist them via `ApartmentService.createApartment(...)`
- Return how many were created

Key points:

- Annotate the class with `@Component` so Spring can inject it
- Autowire `ApartmentService`
- Validate `qty` (e.g. `qty <= 0` returns `0`)

### 2) Expose a REST endpoint

File: `ApartmentRestController.java`

Responsibilities:

- Accept request param `qty`
- Call `populateDB.populateApartments(qty)`
- Return a message (or a DTO) confirming how many were created

Suggested mapping:

- Controller base path is already:

```
@RequestMapping("api/apartment")
```

So the full endpoint becomes:

- `GET /api/apartment/populate?qty=50`

### 3) Verify it works

## Call the endpoint

Example (browser / curl / Postman):

```
GET http://localhost:8080/api/apartment/populate?qty=25
```

Expected response example:

```
Populated apartments: 25
```

### 4) Confirm rows exist

Call:

```
GET http://localhost:8080/api/apartment/getAll
```

You should see a list (or iterable) of apartments returned.

## Notes / common pitfalls

- **Dependency not required**
  - If you are not using a faker library, do not add it to `pom.xml`.
- **Field types**
  - The entity uses `String` for yes/no fields. If you change them to `boolean`, update:
    - Entity fields
    - JSON payload expectations
    - Population logic
    - Any ML/CSV import logic
- **Performance**
  - For large quantities, you may want `saveAll(...)` and batching.

---

## Optional improvements

- **Only populate when DB is empty**
  - Similar to the “create fake customers” pattern:

```
if (apartmentRepository.count() != 0) return 0;
```

- **Return JSON instead of string**
  - Example response structure:

```
{ "requested": 50, "created": 50 }
```
