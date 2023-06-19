//
//  LifecycleHolder.swift
//  iosApp
//
//  Created by Stanislav Radchenko on 06.06.2023.
//

import Foundation
import ComposeApp

class LifecycleHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
