//
//  MainTabbedView.swift
//  iosApp
//
//  Created by Gor Ohanyan on 29.04.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


extension BottomNavScreen {
    func CustomTabItem(imageName: String, title: String, isActive: Bool) -> some View{
        VStack(spacing: 10){
            Image(imageName)
                .resizable()
                .renderingMode(.template)
                .foregroundColor(isActive ? Color("Teritary") : Color("Background"))
                .frame(width: 20, height: 20)
                .padding(.horizontal, 14)
                .padding(.vertical, 4)
                .background(isActive ? Color("Secondary") : .clear)
                .cornerRadius(30)
          //  if isActive{
                Text(title)
                    .font(.custom("JosefinSans-SemiBold",size:12))
                    .foregroundColor(Color("Background"))
                    .padding(.horizontal, 12)
         //   }
         //   Spacer()
        }
        .frame(width: isActive ? .infinity : 60, height: 60)
//        .background(isActive ? Color("Secondary") : .clear)
//        .cornerRadius(30)
    }
}
