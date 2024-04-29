//
//  TabbedItems.swift
//  iosApp
//
//  Created by Gor Ohanyan on 29.04.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum TabbedItems: Int, CaseIterable{
    case trails = 0
    case start
    case about
    
    var title: String{
        switch self {
        case .trails:
            return "Trails"
        case .start:
            return "Start"
        case .about:
            return "About"
        }
    }
    
    var iconName: String{
        switch self {
        case .trails:
            return "ic_trails"
        case .start:
            return "ic_start"
        case .about:
            return "ic_about"
        }
    }
}
