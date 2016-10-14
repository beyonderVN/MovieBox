# MovieBox

**Name of your app** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **36** hours spent in total

## User Stories

The following **required** functionality is completed:

* [ok] User can **scroll through current movies** from the Movie Database API
* [ok] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [ok] For each movie displayed, user can see the following details:
  * [ok] Title, Poster Image, Overview (Portrait mode)
  * [ok] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [ok] User can **pull-to-refresh** popular stream to get the latest movies.
* [ok] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* [ok] Improved the user interface through styling and coloring.

The following **bonus** features are implemented:

* [ok] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [ok] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [ok] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [ok] Overlay a play icon for videos that can be played.
    * [ok] More popular movies should start a separate activity that plays the video immediately.
    * [ok] Less popular videos rely on the detail page should show ratings and a YouTube preview.
* [ok] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [ok] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
* [ok] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) or [Volley](http://guides.codepath.com/android/Networking-with-the-Volley-Library) networking libraries.

The following **additional** features are implemented:

* [ok] MVP Pattern, clear architecture, using Rxjava
* [ok] User can **push-to-load-more** popular stream to get more movies.

## Video Walkthrough

Here's a walkthrough of implemented user stories:
https://drive.google.com/open?id=0B9BWZYW-mLj1QW52cjdwdEkxQkk

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [ButterKnife] (https://github.com/JakeWharton/butterknife) - Field and method binding for Android views which uses annotation processing to generate boilerplate code for you
- [Rxjava] (https://github.com/ReactiveX/RxJava) -  Reactive Extensions for the JVM
- [Dagger] (https://github.com/square/dagger) - A fast dependency injector for Android and Java.
- [retrofit] (https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc.
- [Okhttp] (https://github.com/square/okhttp) - An HTTP+HTTP/2 client for Android and Java applications.
- [simpleratingbar] (https://github.com/FlyingPumba/SimpleRatingBar) - a simple yet powerful RatingBar alternative to Android's default
...

## License

    Copyright [2016] [beyonderVN]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
