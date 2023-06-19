//
//  ComposeControllerToSwiftUI.swift
//  iosApp
//
//  Created by Stanislav Radchenko on 06.06.2023.
//

import SwiftUI
import ComposeApp

struct ComposeViewControllerToSwiftUI: UIViewControllerRepresentable {
    private let lifecycle: LifecycleRegistry
    private let topSafeArea: Float
    private let bottomSafeArea: Float
    
    init(lifecycle: LifecycleRegistry, topSafeArea: Float, bottomSafeArea: Float) {
        self.lifecycle = lifecycle
        self.topSafeArea = topSafeArea
        self.bottomSafeArea = bottomSafeArea
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        
        let controller = MainKt.MainViewController(
            lifecycle: lifecycle,
            topSafeArea: topSafeArea,
            bottomSafeArea: bottomSafeArea
        )
        UIViewControllerProviderKt.uiViewController = controller
        return controller
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
