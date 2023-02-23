# TestApp
Android application that reads and shows records from database. At first opening, it copies database from file in assets folder to local storage using Room. After it gets records using Paging and shows in RecyclerView.

## Stack
- Kotlin
- Coroutines
- Flow
- Paging
- Room
- RecyclerView

## Screenshots
In folder screenshots

## Bugs
Infinitely adding items to RecyclerView list. It was found after adding Paging Library, so I think there can be found a problem.
