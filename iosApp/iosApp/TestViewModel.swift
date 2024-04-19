//
//  TestViewModel.swift
//  iosApp
//
//  Created by Gor Ohanyan on 14.04.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared


class TestViewModel: ObservableObject {
    
    private var insertHikeInDbUseCase: InsertHikeInDbUseCase
    private var getHikesUseCase: GetHikesUseCase
    private var testUseCase: TestUseCase

     
    init() {
        self.getHikesUseCase = GetHikesUseCase()
        self.insertHikeInDbUseCase = InsertHikeInDbUseCase()
        self.testUseCase = TestUseCase()
    }
    

    
    func test() {
        let hike = HikeEntity(
            hikeId: nil,
            hikeName: "iosHike",
            hikeDescription: "des",
            hikeLengthInKm: 2.0,
            hikeDifficulty: .medium,
            hikeRating: 4.0,
            hikeImage: "",
            hikeTime: "",
            hikeLocationLot: 2.2,
            hikeLocationLat: 2.2,
            hikeIsFavourite: false
        )
        insertHikeInDbUseCase.invoke(hikeEntity: hike)
        getHikesUseCase.invoke()
        print(getHikesUseCase.invoke())

    }
    
    func apiCallExample()  {
        //testUseCase.invoke()
    }
    
    
    // Empty view model
    // You can add properties and methods here as needed
}
