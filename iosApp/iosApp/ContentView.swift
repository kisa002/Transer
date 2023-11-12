//
//  ContentView.swift
//  iosApp
//
//  Created by 성스러운기사 on 11/12/23.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeView()
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        PresentationsKt.PreferencesScreen()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

#Preview {
    ContentView()
}
