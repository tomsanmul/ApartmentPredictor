# CRUD: update PUT

## WRONG implementation

### What happens when we call the PUT endpoint

Postman send:

```
PUT /api/apartment/updateById?id=2aacac51-4eea-4f7d-8a03-227751c23ba2
{
  "price": 48
}
```

#### 1) In the controller

```java
public Apartment updateApartmentById(@RequestParam String id, @RequestBody Apartment apartment){
    return apartmentService.updateApartmentById(id, apartment);
}
```

- `@RequestParam String id` reads the `id` from the URL query string.
- `@RequestBody Apartment apartment` asks Spring/Jackson to convert the JSON body into an [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:8:0-219:1) object.

**Important:** because our JSON only contains `"price"`, the created `Apartment apartment` will have:

- `price = 48`
- **all other fields = `null`** (area, bedrooms, etc.), because they weren’t provided.

#### 2) In the service

```java
Apartment existing = apartmentRepository.findById(id).get();
existing.setPrice(apartment.getPrice());
existing.setArea(apartment.getArea());
...
return apartmentRepository.save(existing);
```

- `findById(id)` loads the current apartment row from the DB.
- Then we overwrite *every field* on `existing` with the values from the request body.

So with our request body containing only `price`:

- `existing.price` becomes `48`
- `existing.area` becomes `null`
- `existing.bedrooms` becomes `null`
- etc.

That means this code is behaving like a **full update (PUT)**, but POSTMAN client is sending a **partial object**, so <mark>we unintentionally wipe fields.</mark>

### Why `save(existing)` updates (not inserts)

- `existing` is an entity that came from `findById(id)` so it has a real DB identity.
- `save(existing)` will result in an SQL `UPDATE` for that row (not a new `INSERT`).

### Reviews part

This part:

```java
if (apartment.getReviews() != null) { ... }
```

means:

- If we don’t send `reviews` in JSON, the relationship is untouched.
- If we do send `reviews`, it replaces the list and re-links each [Review](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Review.java:9:0-104:1) back to the same [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:8:0-219:1).

## RIGHT implementation

Right now our method behaves like a **full PUT replace**: it copies every field from the request body onto the existing DB entity. If the JSON omits fields, Jackson sets them to `null`, and we overwrite the DB values with `null` (wipe).

To make it update **only what Postman sends**, refactor it to **“patch” behavior**: only call setters when the incoming value is not `null`.

### Refactored service method

partial update / PATCH-style:

```java
public Apartment updateApartmentById(String id, Apartment apartment) {
    if (id == null || apartment == null) {
        return null;
    }

    Optional<Apartment> existingOpt = apartmentRepository.findById(id);
    if (existingOpt.isEmpty()) {
        return null;
    }

    Apartment existing = existingOpt.get();

    if (apartment.getPrice() != null) existing.setPrice(apartment.getPrice());
    if (apartment.getArea() != null) existing.setArea(apartment.getArea());
    if (apartment.getBedrooms() != null) existing.setBedrooms(apartment.getBedrooms());
    if (apartment.getBathrooms() != null) existing.setBathrooms(apartment.getBathrooms());
    if (apartment.getStories() != null) existing.setStories(apartment.getStories());
    if (apartment.getMainroad() != null) existing.setMainroad(apartment.getMainroad());
    if (apartment.getGuestroom() != null) existing.setGuestroom(apartment.getGuestroom());
    if (apartment.getBasement() != null) existing.setBasement(apartment.getBasement());
    if (apartment.getHotwaterheating() != null) existing.setHotwaterheating(apartment.getHotwaterheating());
    if (apartment.getAirconditioning() != null) existing.setAirconditioning(apartment.getAirconditioning());
    if (apartment.getParking() != null) existing.setParking(apartment.getParking());
    if (apartment.getPrefarea() != null) existing.setPrefarea(apartment.getPrefarea());
    if (apartment.getFurnishingstatus() != null) existing.setFurnishingstatus(apartment.getFurnishingstatus());

    if (apartment.getReviews() != null) {
        existing.setReviews(apartment.getReviews());
        for (Review review : existing.getReviews()) {
            if (review != null) {
                review.setApartment(existing);
            }
        }
    }

    return apartmentRepository.save(existing);
}
```

### How this fixes the Postman example

Sending:

```json
{ "price": 48 }
```

- Only `price` is non-null → only `price` updates.
- All other fields remain unchanged in DB.

### Note on semantics

What we’re implementing is technically **PATCH** behavior. We can keep using `@PutMapping`, but if we want to be strict REST-wise, consider `@PatchMapping("/updateById")`.



## Table POST, PUT, PATCH

| Method    | Main idea                                | Typical use                                                                                                                                                                                                                   | Body expectation                        | Partial update? | What happens to missing fields                                      | Idempotent?                                                                | Example for your case                                                          |
| --------- | ---------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------- | --------------- | ------------------------------------------------------------------- | -------------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| **POST**  | Create a new resource (or custom action) | Create [Apartment](cci:2://file:///home/albert/MyProjects/Sandbox/ApartmentPredictorProject/ApartmentPredictor/src/main/java/com/example/apartment_predictor/model/Apartment.java:8:0-219:1), submit form, “action” endpoints | Often **full** data for creation        | Not the goal    | Missing fields may become `null`/defaults on create                 | **No** (often creates duplicates)                                          | `POST /api/apartment/create` with full apartment data                          |
| **PUT**   | Replace the resource at that URI         | Full replace update                                                                                                                                                                                                           | **Full representation** of the resource | Usually **No**  | Missing fields are treated as “set to null/default” (can wipe data) | **Yes**                                                                    | Your original `setX(apartment.getX())` for all fields behaves like PUT-replace |
| **PATCH** | Apply a partial modification             | Update only some fields                                                                                                                                                                                                       | **Only fields to change**               | **Yes**         | Missing fields are **left unchanged**                               | **Not guaranteed** (depends on patch operations), but often designed to be | Your refactor: `if (apartment.getPrice()!=null) existing.setPrice(...)` etc.   |
