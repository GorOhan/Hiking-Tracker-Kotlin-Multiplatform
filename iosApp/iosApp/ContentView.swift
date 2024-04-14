import SwiftUI
import shared

struct ContentView: View {
	//let greet = Greeting().greet()
    private var noteDataSource: Database
     
    init(noteDataSource: Database) {
        self.noteDataSource = noteDataSource
    }
    
    var body: some View {
        VStack{
            Text("Hello Gor")
            Text("This is very first time")
        }
    }
}



//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		ContentView()
//	}
//}
