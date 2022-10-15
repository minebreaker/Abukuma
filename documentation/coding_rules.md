# Coding rules for Abukuma itself (not for the application using it)

## APIs

- Receive `List`, return `PersistentList`
- Implementations should be thread safe unless marked `@Mutable`

## Namings

- Prefer long name.
  - Better than not-understandable
  - IDE will support it.
