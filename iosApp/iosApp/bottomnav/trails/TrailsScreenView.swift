//
//  TrailsScreenView.swift
//  iosApp
//
//  Created by Gor Ohanyan on 29.04.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TrailsScreenView: View {
    
    @StateObject private var viewModel: TestViewModel


    init() {
           self._viewModel = StateObject(wrappedValue: TestViewModel())
        
    
            for family in UIFont.familyNames {
                 print(family)

                 for names in UIFont.fontNames(forFamilyName: family){
                 print("== \(names)")
                 }
        }
        }

    var body: some View {

         ScrollView {
                LazyVStack {
                    ForEach(viewModel.mockList, id: \.self) { item in
                        VStack {
                            ZStack(alignment: .top) {
                                HStack {
                              

                                       Spacer()

                                    Image(systemName: item.hikeIsFavourite ? "heart.fill": "heart")
                                           .foregroundColor(.red)
                                           .onTapGesture {
                                               viewModel.onFavouriteClick(hikeEntity: item)
                                           }
                                   }
                                   .padding(8)
                                   .frame(alignment: .top)
                                   .zIndex(10)
                                
                                AsyncImage(url: URL(string: "https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/topic_centers/2019-8/couple-hiking-mountain-climbing-1296x728-header.jpg?w=1155&h=1528")) { phase in
                                    switch phase {
                                    case .empty:
                                        ProgressView()
                                    case .success(let image):
                                        image
                                            .resizable()
                                            .aspectRatio(contentMode: .fill)
                                    case .failure:
                                        Image("placeholder")
                                            .resizable()
                                            .aspectRatio(contentMode: .fill)
                                    @unknown default:
                                        EmptyView()
                                    }
                                }
                                .frame(height: 150)
                                .clipped()
                                
                
                            }
                            
                            HStack {
                                VStack {
                                    Text(item.hikeName)
                                        .font(.custom("JosefinSans-SemiBold",size:12))
                                        .foregroundColor(Color("Teritary"))
                                    Text(item.hikeDescription)
                                        .foregroundColor(Color("Background"))
                                        .font(.custom("JJosefinSans-SemiBold",size:12))
                                }
                                    Spacer()
                            }.padding(.horizontal, 6)
                        
                        }
                        .background(Color("Primary"))
                        .cornerRadius(8)
                        .padding(.horizontal, 12)
                        .onTapGesture {
                           viewModel.addmockItem()
                        }
                    }
                }
        }.background(Color("Background"))
    }
      
}

struct TrailsScreenView_Previews: PreviewProvider {
    static var previews: some View {
        TrailsScreenView()
    }
}
