//
//  BottomNavScreen.swift
//  iosApp
//
//  Created by Gor Ohanyan on 23.04.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct BottomNavScreen: View {
    
    @State private var selectedTab = 0

    var body: some View {

            TabView(selection: $selectedTab) {
                
                TrailsScreenView()
                    .tag(0)
                
                StartHikingScreen()
                    .tag(1)
                
                AboutScreenView()
                    .tag(2)
                
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
            .navigationBarHidden(true)
            
            ZStack {
                
                HStack{
                    ForEach((TabbedItems.allCases), id: \.self){ item in
                        Button{
                            selectedTab = item.rawValue
                        } label: {
                            CustomTabItem(imageName: item.iconName, title: item.title, isActive: (selectedTab == item.rawValue))
                            if item.rawValue != 2  { Spacer() }
                        }
                    }
                }
                .padding(6)
            }
            .frame(height: 70)
            .background(Color("Primary"))
            .cornerRadius(35)
            .padding(.horizontal, 14)
        

        
    }
}

struct BottomNavScreen_Previews: PreviewProvider {
    static var previews: some View {
        BottomNavScreen()
    }
}

