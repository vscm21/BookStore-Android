# BookStore Android App

An Android app to browse and save favorite books about Android development, using the Google Books API.

## Features

- Browse Android books in a 2-column grid
- Lazy loading with pagination (Paging 3)
- Save and manage favorite books locally
- Book detail view with description and buy link
- Open buy link in external browser
- Offline favorites with Room database

## Architecture

Clean Architecture + MVVM with separated layers:

- **Presentation** — Jetpack Compose, ViewModels
- **Domain** — Repository interfaces, Domain Models
- **Data** — Repository implementations, Room, Retrofit

## Tech Stack

| Category | Technology |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt |
| Database | Room |
| Networking | Retrofit + OkHttp |
| Pagination | Paging 3 |
| Images | Coil 3 |
| Navigation | Navigation Compose |
| Testing | JUnit + MockK + Turbine |

## API

Google Books API — https://developers.google.com/books

## Setup

1. Clone the repository
2. Get a Google Books API key from Google Cloud Console
3. Add to `gradle.properties`:
   BOOKS_API_KEY=your_api_key_here
4. Build and run