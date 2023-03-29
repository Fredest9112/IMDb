package com.globant.imdb.data

import androidx.annotation.VisibleForTesting
import com.globant.imdb.database.Movie

@VisibleForTesting
object MoviesRepoMock {

    val movies = listOf(
        Movie(1, "posterpath1", "title1", "2021-02-03", 8.5),
        Movie(2, "posterpath2", "title2", "2020-10-30", 7.8),
        Movie(3, "posterpath3", "title3", "2018-11-28", 6.2),
        Movie(4, "posterpath4", "title4", "2023-12-21", 9.1),
        Movie(5, "posterpath5", "title5", "2015-05-25", 5.8),
        Movie(6, "posterpath6", "title6", "2017-09-17", 7.3),
        Movie(7, "posterpath7", "title7", "2016-07-09", 8.1),
        Movie(8, "posterpath8", "title8", "2019-04-12", 6.8)
    )
}