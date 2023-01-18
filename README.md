# TRANSER for easy and fast translation

## What is TRANSER

### O**ne-line summary**

TRANSER is an application built for easy and fast translation.

~~How can it be easy and faster than this?~~

### Why did I make it?

I created this project to solve the inconvenience of using a translator when people like me read other language documents and see words or sentences that they don't know.

This project started on January 2nd, 2023.

### Kotlin Multiplatform

TRANSER is developed based on the Kotlin Multiplatform, so many logic are shared like UI, API, Database, etc.

I think the Kotlin Multiplatform is more powerful than other cross-platforms when requiring native platform features, so I focused on shared logic but also the features of each native platform.

## How to use it?

### Desktop

It's simple, just type it in the TRANSER then it is translated.

If your platform is mac, you can use a shortcut (⌥ + Space) to appear on the top while running TRANSER.
Or other platforms, you can use Control + Space.

Currently, mac is safety version. windows, linux required to more test.

### Android

TRANSER automatically translates text when you select the text and click TRANSER in the menu that comes out.

## Captures

### Desktop

- Onboarding

  ![Desktop-Onboarding](https://user-images.githubusercontent.com/4679634/212265744-28a19a8f-d059-40f7-8e74-cac5d1065e7b.gif)

- Translate & Recent & Saved

  ![Desktop-Translate & Recent & Saved](https://user-images.githubusercontent.com/4679634/212265754-73ae4047-069c-4048-b185-8e392ffbf6b4.gif)

- Preferences

  ![Desktop-Preferences](https://user-images.githubusercontent.com/4679634/212265747-d00f9f5e-c02b-4af9-a9e5-fbcb55cc606b.gif)

- Light Theme

  ![Desktop-Light Theme](https://user-images.githubusercontent.com/4679634/212265741-c68b4b22-d1b9-4bd9-9932-49e5b2af99f5.gif)


### Android

- Translate

  ![Android-Translate](https://user-images.githubusercontent.com/4679634/212265736-7a497a00-8cb8-4b34-a27a-3c4bf74ba50d.gif)

- Recent

  ![Android-Recent](https://user-images.githubusercontent.com/4679634/212265726-3a3bc92c-e193-48f7-9b64-b30a883d315b.gif)

- Saved

  ![Android-Saved](https://user-images.githubusercontent.com/4679634/212265734-d4c24848-d0c1-4816-979c-ecaf55d9bb60.gif)

- Preferences

  ![Android-Preferences](https://user-images.githubusercontent.com/4679634/212265719-cc7e2f1b-ba90-431d-9177-41b372bb0960.gif)

- Light Theme

  ![Android-Light Theme](https://user-images.githubusercontent.com/4679634/212265656-7bad5f08-a44c-4662-8130-c2b608bc28be.gif)


### Features

- Common
    - Translate
    - Recent Translate
    - Saved Translate
    - Preferences
    - Support dark/light theme
    - API
    - Database
- Native
    - Desktop
        - Shortcut
        - Commands
        - Onboarding (Will be supported the Android)
    - Android
        - Detect text event
          (click TRASER in the menu that showed up on long-press select text in other apps)

### Architecture

- Architecture
    - Bob’s Clean Architecture
    - MVVM Design Pattern

### Shared Logic

- Data
    - Model
    - Remote
        - Translate
        - Detect
        - Languages
    - Local
        - Preferences
        - Recent Translate
        - Saved Translate
- Domain
    - Model (All platforms use the same model)
    - Repository
        - Remote
            - Translate
            - Detect
            - Languages
        - Local
            - Preferences
            - Recent Translate
            - Saved Translate
    - UseCase
- Presentation
    - Theme
        - Color
        - Font
    - Screen
        - Preferences
    - Component
        - Header
        - RainbowCircularProgressIndicator

### Native Logic

- Desktop
    - Translate flow
        - Desktop flow is like the mac spotlight.
    - Shortcut
        - with JNativeHook
    - Onboarding
- Mobile
    - Translate flow
        - Mobile flow is like a bottom sheet that showed up on dragging the text and clicking the menu.

### Libraries

- Common (aka. Kotlin Multiplatform Library)
    - Ktor
    - SQLDelight
- Native
    - Desktop
        - JNativeHook
    - Android
        - Material-icons-extended

## Todos

- [ ]  Common
    - [ ]  Separate data-domain-presentation module (currently, just separated in the same package)
    - [ ]  Cache translate API result
    - [ ]  Multilingual support (TRANSER UI)
- [ ]  Native
    - [ ]  Desktop
        - [ ]  Test Linux
        - [ ]  Onboarding (User try it self)
    - [ ]  Android
        - [ ]  Onboarding
    - [ ]  Server
        - [ ]  API Server
        - [ ]  Analytics
    - [ ]  Web
        - [ ]  Landing Page
        - [ ]  Preview translation for desktop

## License

MIT License

Copyright (c) 2023 kisa002

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
