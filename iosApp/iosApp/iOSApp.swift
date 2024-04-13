import SwiftUI
import shared

@main
struct iOSApp: App {

    let data = DatabaseModule()

	var body: some Scene {
		WindowGroup {
			ContentView(noteDataSource: data.dbDataSource)
		}
	}
}
