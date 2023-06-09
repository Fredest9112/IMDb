package com.globant.imdb.data

object Constants {
    const val API_KEY = "8164f20cfa23d5e18b954dc70494fc6c"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val INITIAL_SEARCH_SCREEN_PATH = "movie/top_rated"
    const val SEARCH_MOVIE_PATH = "search/movie"
    const val MOST_POPULAR_MOVIES = "movie/popular"
    const val PASSWORD_PATTERN =
        "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}$"
    const val DATABASE_NAME = "movies_database"
    const val LOGIN_PREFERENCES = "login_preferences"
    const val IS_USER_LOGGED = "is_user_logged"
}