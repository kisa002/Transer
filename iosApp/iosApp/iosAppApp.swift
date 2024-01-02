//
//  iosAppApp.swift
//  iosApp
//
//  Created by 성스러운기사 on 11/12/23.
//

import SwiftUI
import shared

@main
struct iosAppApp: App {
    init() {
        MobileKoinKt.startKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea(.all, edges: .vertical)
        }
    }
}
