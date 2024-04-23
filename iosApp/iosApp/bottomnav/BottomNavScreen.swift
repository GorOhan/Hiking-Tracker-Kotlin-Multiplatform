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
                      Text("Home View")
                          .tabItem {
                              Label("Home", systemImage: "house")
                          }
                          .tag(0)
                      
                      Text("Search View")
                          .tabItem {
                              Label("Search", systemImage: "magnifyingglass")
                          }
                          .tag(1)
                      
                      Text("Profile View")
                          .tabItem {
                              Image("ic_about")
                              Text("About")
                          }
                          .tag(2)
                  }
        .tint(.pink)
        .onAppear(perform: {
            //2
            UITabBar.appearance().unselectedItemTintColor = .systemBrown
            //3
            UITabBarItem.appearance().badgeColor = .systemPink
            //4
            UITabBar.appearance().backgroundColor = UIColor(Color(hex: 0x175366))
            
        //    UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor.systemPink]
            //UITabBar.appearance().scrollEdgeAppearance = UITabBarAppearance()
            //Above API will kind of override other behaviour and bring the default UI for TabView
        })
        .navigationBarHidden(true)

    }
}

struct BottomNavScreen_Previews: PreviewProvider {
    static var previews: some View {
        BottomNavScreen()
    }
}
