package com.globant.imdb.data

import androidx.annotation.VisibleForTesting
import com.globant.imdb.database.Movie
import com.globant.imdb.database.RecentWatchedMovie
import com.globant.imdb.database.WatchListMovie

@VisibleForTesting
object MoviesRepoMock {

    val movies = listOf(
        Movie(1, "posterpath1", "title1","originalTitle1", "overview1", "2021-02-03", 8.5, 3452.5),
        Movie(2, "posterpath2", "title2","originalTitle2", "overview2", "2020-10-30", 7.8, 6574.7),
        Movie(3, "posterpath3", "title3","originalTitle3", "overview3", "2018-11-28", 6.2, 123547.4),
        Movie(4, "posterpath4", "title4","originalTitle4", "overview4", "2023-12-21", 9.1, 126585.5),
        Movie(5, "posterpath5", "title5","originalTitle5", "overview5", "2015-05-25", 5.8, 75537.8),
        Movie(6, "posterpath6", "title6","originalTitle6", "overview6", "2017-09-17", 7.3, 236485.2),
        Movie(7, "posterpath7", "title7","originalTitle7", "overview7", "2016-07-09", 8.1, 45759.3),
        Movie(8, "posterpath8", "title8","originalTitle8", "overview8", "2019-04-12", 6.8, 75337.6)
    )

    val recentWatchedMovies = listOf(
        RecentWatchedMovie(1, "posterpath1", "title1","originalTitle1", "overview1", "2021-02-03", 8.5, 3452.5),
        RecentWatchedMovie(2, "posterpath2", "title2","originalTitle2", "overview2", "2020-10-30", 7.8, 6574.7),
        RecentWatchedMovie(3, "posterpath3", "title3","originalTitle3", "overview3", "2018-11-28", 6.2, 123547.4),
        RecentWatchedMovie(4, "posterpath4", "title4","originalTitle4", "overview4", "2023-12-21", 9.1, 126585.5),
        RecentWatchedMovie(5, "posterpath5", "title5","originalTitle5", "overview5", "2015-05-25", 5.8, 75537.8),
        RecentWatchedMovie(6, "posterpath6", "title6","originalTitle6", "overview6", "2017-09-17", 7.3, 236485.2),
        RecentWatchedMovie(7, "posterpath7", "title7","originalTitle7", "overview7", "2016-07-09", 8.1, 45759.3),
        RecentWatchedMovie(8, "posterpath8", "title8","originalTitle8", "overview8", "2019-04-12", 6.8, 75337.6)
    )

    val watchListMovies = listOf(
        WatchListMovie(1, "posterpath1", "title1","originalTitle1", "overview1", "2021-02-03", 8.5, 3452.5),
        WatchListMovie(2, "posterpath2", "title2","originalTitle2", "overview2", "2020-10-30", 7.8, 6574.7),
        WatchListMovie(3, "posterpath3", "title3","originalTitle3", "overview3", "2018-11-28", 6.2, 123547.4),
        WatchListMovie(4, "posterpath4", "title4","originalTitle4", "overview4", "2023-12-21", 9.1, 126585.5),
        WatchListMovie(5, "posterpath5", "title5","originalTitle5", "overview5", "2015-05-25", 5.8, 75537.8),
        WatchListMovie(6, "posterpath6", "title6","originalTitle6", "overview6", "2017-09-17", 7.3, 236485.2),
        WatchListMovie(7, "posterpath7", "title7","originalTitle7", "overview7", "2016-07-09", 8.1, 45759.3),
        WatchListMovie(8, "posterpath8", "title8","originalTitle8", "overview8", "2019-04-12", 6.8, 75337.6)
    )
}