# MovieNight

# Setup Documentation


Clone the project and hit run!


# App Description

This app has 2 main screens for searching movies and tv series. In order to perform a search, click on the search icon at the top of the screen on the toolbar,
type a name and results will be shown in a grid. 
After the results have been fetched, click on a show for seeing more details (plot, director, year of release, main actors, rating)
It is possible to save a movie on a Watchlist, by clicking the icon at the bottom of the page. 
The main page of the app has a bottom action button. By tapping it users can check their watchlist, remove a movie from the watchlist or see the details of the
movie saved. 

# App Architecture

The design pattern adopted for this app is MVI, with ViewModel and StateFlow. 
The project structure follows the logic of folder-by-feature structure.

# Libraries

This app has used several libraries and architecture components to achieve the main goals required:
- [Retrofit](https://github.com/square/retrofit) for Api Calls
- [Room Database](https://developer.android.com/training/data-storage/room) for storing movies in a Watchlist
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection
- [Espresso](https://developer.android.com/training/testing/espresso) for UI Tests
- [Mockito](https://site.mockito.org/) for Unit Tests
- [Coroutines](https://developer.android.com/kotlin/coroutines) for Asynchronous Operations
- [LeakCanary](https://github.com/square/leakcanary) for MemoryLeaks detections 
- [Glide](https://github.com/bumptech/glide) for loading images 
