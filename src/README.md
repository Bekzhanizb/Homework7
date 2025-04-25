# 🎬 Episode Player – Iterator Pattern

## Why Use Iterator Instead of Exposing `List<Episode>`?

In building a streaming service episode player, we chose the **Iterator Pattern** over directly exposing internal episode lists. Here's why that decision leads to a more flexible and maintainable design.

---

### ✅ 1. Encapsulation & Flexibility

Each `Season` can store its episodes differently — `ArrayList`, `LinkedList`, or even lazy-loaded from disk. The **Iterator** pattern hides these internal details.

```java
EpisodeIterator it = season.createIterator();
while (it.hasNext()) {
    Episode ep = it.next();
    // Play or display episode
}
```
---
### 🔄 2. Multiple Traversal Modes
By implementing different iterators, we can support:

SeasonIterator — normal order

ReverseSeasonIterator — reverse order

ShuffleSeasonIterator — randomized, repeatable order

Trying to achieve the same with exposed List<Episode> would involve copying, sorting, or reimplementing logic repeatedly.

---
### 🔁 3. Binge Viewing Across Seasons
The BingeIterator combines all seasons into a single continuous stream of episodes. The UI can loop through all content with no need to manage season boundaries.
