import SwiftUI
import shared

struct ContentView: View {
	//let greet = Greeting().greet()
    @StateObject private var viewModel: TestViewModel
    @State private var shouldNavigate = false


    init() {
           self._viewModel = StateObject(wrappedValue: TestViewModel())
        
        }

    var body: some View {
        NavigationStack {
            
            NavigationLink(
                            destination:BottomNavScreen(),
                            isActive: $shouldNavigate) {
                            EmptyView()
                        }.hidden()
            
            ZStack {
                Color("Primary") // Custom color with hex code
                    .edgesIgnoringSafeArea(.all)
                    Text("Hiking")
                        .foregroundColor(.white)
                        .font(.custom("JosefinSans-SemiBold",size:64))
                        .onAppear{
                            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                                shouldNavigate = true
                            }
                           viewModel.test()
                          //  viewModel.apiCallExample()
                        }
            
          
            }
        }
    }
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
