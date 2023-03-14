# IMDb
A project to show movies from the TMDB website https://www.themoviedb.org/, it also implements login functionality with firebase

REQUIREMENTS

Basic Project:

Functional requirements
The application must have a splash with animation that can last 1.5 seconds before entering the next screen, this space can be used to check if the user is logged in, according to this, decide whether to go directly to the search screen.
The login screen should allow the user to log in, persisting locally once the user has registered.
The create account screen must create a user that is persisted locally and must implement restrictions or validations for a password (min 8 characters, uppercase, lowercase, special character and number) as well as not allowing duplicate emails.
The search screen should list movies obtained via the suggested API and should be able to filter by name using the search bar.
Present the architecture diagram for the demo.
Additional validations to the flow can be assumed by each developer.

Technical requirements
compose
Room
Retrofit
MVVM
unit test
clean architecture
coroutines
Flow
Dependency injection (Hilt)
git
