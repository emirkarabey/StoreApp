<h1 align="center">StoreApp</h1>

<p align="center">  
This is an android application where data from the internet can be listed, added to favorites, added to the cart and the details can be viewed.
<br/>
<p align="center">I used <a href="https://fakestoreapi.com/">Fake Store</a> API for building this application.</p>
</p>


## App Gif
<img src="https://github.com/emirkarabey/StoreApp/blob/master/appgif/storeapp.gif"  width="25%"/>


#### App Screenshots

| Home | Favorite Screen | Cart Screen |
|:-:|:-:|:-:|
| ![Fist](https://github.com/emirkarabey/StoreApp/blob/master/screenshots/bottomsheetdialog.png) | ![3](https://github.com/emirkarabey/StoreApp/blob/master/screenshots/favoritescreen.png) | ![3](https://github.com/emirkarabey/StoreApp/blob/master/screenshots/cartscreen.png)
| Home Dark Mode | Favorite Screen Dark Mode | Cart Screen Dark Mode |
| ![4](https://github.com/emirkarabey/StoreApp/blob/master/screenshots/bottomshetdark.png) | ![5](https://github.com/emirkarabey/StoreApp/blob/master/screenshots/favoritescreendark.png) | ![3](https://github.com/emirkarabey/StoreApp/blob/master/screenshots/cartscreendark.png)

## Tech stack
* ✅ MVVM with Clean Architecture
* ✅ [Kotlin Flow][31] - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
* ✅ [Coroutines][51] - A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
* ✅ [Navigation Component][24] - Handle everything needed for in-app navigation. asynchronous tasks for optimal execution.
* ✅ [Safe-Args][25] - For passing data between destinations
* ✅ [Dagger-Hilt][93] - A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
* ✅ [ViewModel][17] - Easily schedule asynchronous tasks for optimal execution.
* ✅ [Retrofit][90]- Retrofit is a REST client for Java/ Kotlin and Android by Square inc under Apache 2.0 license. Its a simple network library that is used for network transactions. By using this library we can seamlessly capture JSON response from web service/web API.
* ✅ [OkHttp][23] - Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
* ✅ [View Binding][11] - a feature that allows you to more easily write code that interacts with views.
* ✅ [Lifecycle][22] - As a user navigates through, out of, and back to your app, the Activity instances in your app transition through different states in their lifecycle.
* ✅ [Room][53] - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* ✅ [Swipe-to-Refresh Layout][56] The swipe-to-refresh user interface pattern is implemented entirely within the SwipeRefreshLayout widget, which detects the vertical swipe, displays a distinctive progress bar, and triggers callback methods in your app.
* ✅ [Glide][94] for image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.

[11]: https://developer.android.com/topic/libraries/view-binding
[53]: https://developer.android.com/jetpack/androidx/releases/room
[93]: https://developer.android.com/jetpack/compose/libraries#hilt
[51]: https://developer.android.com/kotlin/coroutines
[90]: https://square.github.io/retrofit/
[31]: https://developer.android.com/kotlin/flow
[22]: https://developer.android.com/guide/components/activities/activity-lifecycle
[17]: https://developer.android.com/jetpack/compose/state#viewmodel-state
[23]: https://square.github.io/okhttp/
[24]: https://developer.android.com/guide/navigation/navigation-getting-started
[25]: https://developer.android.com/guide/navigation/navigation-pass-data
[56]: https://developer.android.com/training/swipe/add-swipe-interface
[94]: https://github.com/bumptech/glide
