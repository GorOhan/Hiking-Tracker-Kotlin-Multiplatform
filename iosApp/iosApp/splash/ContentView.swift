import SwiftUI
import shared

struct ContentView: View {
	//let greet = Greeting().greet()
    @StateObject private var viewModel: TestViewModel


    init() {
           self._viewModel = StateObject(wrappedValue: TestViewModel())
        }

    var body: some View {
        NavigationView {
            ZStack {
                Color(hex: 0x175366) // Custom color with hex code
                    .edgesIgnoringSafeArea(.all)
                NavigationLink(destination: BottomNavScreen(), label:  {
                    Text("Hiking")
                        .foregroundColor(.white)
                        .font(.system(size: 64))
//                        .onTapGesture {
//                            
//                            viewModel.test()
//                            print("test Gor")
//                        }.onAppear{
//                            viewModel.apiCallExample()
//                        }
                })
          
            }
        }
    }
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

extension Color {
    init(hex: UInt, opacity: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 8) & 0xff) / 255,
            blue: Double(hex & 0xff) / 255,
            opacity: opacity
        )
    }
}

