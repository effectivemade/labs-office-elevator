import SwiftUI
import shared
import GoogleSignIn

@UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        let rootViewController = RootViewControllersKt.RootViewController()
        //GIDSignIn.sharedInstance.presentingViewController = rootViewController
        
        GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
            if error != nil || user == nil {
                GoogleAuthorization.shared.token = nil
            } else {
                GoogleAuthorization.shared.token = user?.idToken?.tokenString
            }
        }
        
        GoogleAuthorization.shared.setSignIn {
            GIDSignIn.sharedInstance.signIn(
                withPresenting: rootViewController) { signInResult, error in
                    guard signInResult != nil else {
                        GoogleAuthorization.shared.onSignInFailure(KotlinException(message: error?.localizedDescription))
                    return
                  }
                    GoogleAuthorization.shared.onSignInSuccess()
                }
        }
        
        GoogleAuthorization.shared.signOut {
            GIDSignIn.sharedInstance.signOut()
        }
        
        GoogleAuthorization.shared.setLastSignedAccount {
            GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
                if error != nil || user == nil {
                    GoogleAuthorization.shared.token = nil
                } else {
                    GoogleAuthorization.shared.token = user?.idToken?.tokenString
                }
            }
        }

        
        let controller = AvoidDispose(rootViewController)
        
        //GIDSignIn.sharedInstance.clientID = "627570596451-pmtas2pcm6hpkeddpd19uasql1upknun.apps.googleusercontent.com"
            //GIDSignIn.sharedInstance.dele = self
        

        controller.view.backgroundColor = .white
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.backgroundColor = .white
        window.rootViewController = controller
        window.makeKeyAndVisible()
        self.window = window
        
        return true
    }
    
    func application(
      _ app: UIApplication,
      open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
      var handled: Bool

     handled = GIDSignIn.sharedInstance.handle(url)
      if handled {
        return true
      }

      // Handle other custom URL types.

      // If not handled by this app, return false.
      return false
    }
}
