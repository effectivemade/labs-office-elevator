import SwiftUI
import shared
import GoogleSignIn

@UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        let rootViewController = RootViewControllersKt.RootViewController()
        //GIDSignIn.sharedInstance.presentingViewController = rootViewController
        
        let iosClient = GoogleAuthorization.shared.getClientId()
        let serverIdClient = GoogleAuthorization.shared.getServerClientId()
        let config = GIDConfiguration.init(clientID: iosClient, serverClientID: serverIdClient)
        
        GIDSignIn.sharedInstance.configuration = config
        
        GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
            if error != nil || user == nil {
                GoogleAuthorization.shared.token = nil
            } else {
                let dimension = round(100 * UIScreen.main.scale)
                let imageUrl = user?.profile?.imageURL(withDimension: UInt(dimension))?.absoluteURL.absoluteString
                let username = user?.profile?.givenName
                let email = user?.profile?.email
                GoogleAuthorization.shared.token = user?.idToken?.tokenString
                GoogleAuthorization.shared.googleAccountUser = GoogleAccountUser(imageUrl: imageUrl, username: username, email: email)
            }
        }
        
        GoogleAuthorization.shared.setSignIn {
            GIDSignIn.sharedInstance.signIn(
                withPresenting: rootViewController) { signInResult, error in
                    guard signInResult != nil else {
                        GoogleAuthorization.shared.onSignInFailure(KotlinException(message: error?.localizedDescription))
                    return
                  }
                    let dimension = round(100 * UIScreen.main.scale)
                    let imageUrl = signInResult?.user.profile?.imageURL(withDimension: UInt(dimension))?.absoluteURL.absoluteString
                    let username = signInResult?.user.profile?.givenName
                    let email = signInResult?.user.profile?.email
                    GoogleAuthorization.shared.token = signInResult?.user.idToken?.tokenString
                    GoogleAuthorization.shared.googleAccountUser = GoogleAccountUser(imageUrl: imageUrl, username: username, email: email)
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
