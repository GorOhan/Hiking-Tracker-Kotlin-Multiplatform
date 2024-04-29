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
    private var updateHikeUseCase: UpdateHikeUseCase
    
    @Published var mockList:[HikeEntity] =  []

     
    init() {
        self.getHikesUseCase = GetHikesUseCase()
        self.insertHikeInDbUseCase = InsertHikeInDbUseCase()
        self.testUseCase = TestUseCase()
        self.updateHikeUseCase = UpdateHikeUseCase()
        
        mockList = getHikesUseCase.invoke()
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
            hikeIsFavourite: false,
            hikePoints: []
        )
        insertHikeInDbUseCase.invoke(hikeEntity: hike)
        getHikesUseCase.invoke()
        print(getHikesUseCase.invoke())

    }
    
    func apiCallExample()  {
        //testUseCase.invoke()
    }
    
    func addmockItem() {
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
            hikeIsFavourite: false,
            hikePoints: []
        )
        insertHikeInDbUseCase.invoke(hikeEntity: hike)
      
        mockList = getHikesUseCase.invoke()
        
    }
    
    func updateHike(hike: HikeEntity){
        updateHikeUseCase.invoke(hikeEntity: hike)
        mockList = getHikesUseCase.invoke()
     }
    
    func onFavouriteClick(hikeEntity: HikeEntity){
        let test = HikeEntity(
            hikeId: hikeEntity.hikeId,
            hikeName: hikeEntity.hikeName,
            hikeDescription: hikeEntity.hikeDescription,
            hikeLengthInKm: hikeEntity.hikeLengthInKm,
            hikeDifficulty: hikeEntity.hikeDifficulty,
            hikeRating: hikeEntity.hikeRating,
            hikeImage: hikeEntity.hikeImage,
            hikeTime: hikeEntity.hikeTime,
            hikeLocationLot: hikeEntity.hikeLocationLot,
            hikeLocationLat: hikeEntity.hikeLocationLat,
            hikeIsFavourite: !hikeEntity.hikeIsFavourite,
            hikePoints: hikeEntity.hikePoints)
        
        print(test)

        updateHike(hike: test)
      }
}
