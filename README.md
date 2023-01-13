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

If your platform is mac, you can use a shortcut (⌥+ Space) to appear on the top while running TRANSER.
(Will be supported windows and linux platforms too.)

Currently, mac is safety version. windows required to more test.

### Android

TRANSER automatically translates text when you select the text and click TRANSER in the menu that comes out.

## Captures

### Desktop

- Onboarding

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/da3d6ba9-29a5-4c16-8f77-1562aade2242/Untitled.gif)

- Translate & Recent & Saved

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0f530f05-c35b-4e8c-b2e2-71c3d1387fe1/Untitled.gif)

- Preferences

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2d4c54ab-18dd-4e51-9901-99f8d4b6e340/Untitled.gif)

- Light Theme

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d2b94b50-c015-4fb7-a19a-fce1d1b9e486/Untitled.gif)


### Android

- Translate

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cb8dec82-f093-49b1-99d3-e981ef49b291/Untitled.gif)

- Recent

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7736736f-0ad4-457f-8b36-6c51b5488fec/Untitled.gif)

- Saved

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c3b1f661-76e0-451d-9f8c-570908d82f0c/Untitled.gif)

- Preferences

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/17941e3a-6af1-45a3-a671-f3b52d6c5df4/Untitled.gif)

- Light Theme

  ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/668e59b1-f7cb-4341-9782-a00b35ff2b8d/Untitled.gif)


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