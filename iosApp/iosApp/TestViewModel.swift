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
    
    let data = DatabaseModule()
  //  let ktor = KtorExampleApi()

    private var db: Database
    
    private var dbRepository: DBRepositoryImpl
 //   private var network: NetworkRepositoryImpl
    
    private var insertHikeInDbUseCase: InsertHikeInDbUseCase
    private var getHikesUseCase: GetHikesUseCase
//    private var testUseCase: TestUseCase

     
    init() {
        self.db = data.dbDataSource
        self.dbRepository = DBRepositoryImpl(database: db)
        self.getHikesUseCase = GetHikesUseCase(dbRepository: self.dbRepository)
        self.insertHikeInDbUseCase = InsertHikeInDbUseCase(dbRepository: self.dbRepository)
     //   self.network = NetworkRepositoryImpl(ktorExampleApi: ktor)
       // self.testUseCase = TestUseCase(networkRepository: network)

    }
    

    
    func test(){
        let hike = HikeEntity(
            hikeId: nil,
            hikeName: "iosHike",
            hikeDescription: "des",
            hikeLengthInKm: 2.0,
            hikeDifficulty: "easy",
            hikeRating: 4.0,
            hikeImage: "",
            hikeTime: "",
            hikeLocationLot: 2.2,
            hikeLocationLat: 2.2
        )
        insertHikeInDbUseCase.invoke(hikeEntity: hike)
       // getHikesUseCase.invoke()
        print(getHikesUseCase.invoke())

    }    // Empty view model
    // You can add properties and methods here as needed
}
