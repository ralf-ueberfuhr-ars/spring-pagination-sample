# Spring Pagination Samples

Just start as any other Spring Boot app and open `http://localhost:8080`.
This will open a swagger UI with a list resource that can be read out using
 - Page-based pagination or
 - Cursor-based pagination.

You can find [request samples](http-requests) in this project too.

## Comparison

We can compare both Page/Offset-based and Cursor-based pagination like the table shows:

For both types of pagination, we need a fixed sorting.

|                         | **Page/Offset-based**                                                                                                                                                     | **Cursor-based**                                                                                                                                                                                     |
|-------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Principle               | The first entry is identified by an index.                                                                                                                                | The first entry is identified by its ID or another unique and sortable property.                                                                                                                     |
| Restrictions            |                                                                                                                                                                           | The entry must not be deleted while the cursor points on it. Free page jumps (incl. go to last page) are only possible if we know the ID of an element.                                              |
| Data consistency        | When entries are inserted or deleted, indices will shift. So we'll get duplicated or missing entries while iterating, which must not ocur e.g. within infinite scrolling. | Inserting or removing entries while iterating will not move the cursor, except the cursor points to a deleted entry.                                                                                 |
| Development | Directly supported via Spring's Pageable. Creating hypermedia links is easy because page number or offset can be calulated.                                               | We need to find the entry that the cursor points to first. Then, we need a query criteria to only get rows after this entry. Esp. moving backwards or creating hypermedia links is more complicated. |
