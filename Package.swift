// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorCommunityFirebaseAnalytics",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorCommunityFirebaseAnalytics",
            targets: ["Plugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/firebase/firebase-ios-sdk", from: "12.3.0")
    ],
    targets: [
        .target(
            name: "Plugin",
            dependencies: [
              .product(name: "Capacitor", package: "capacitor-swift-pm"),
              .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FirebaseCore", package: "firebase-ios-sdk"),
                .product(name: "FirebaseAnalytics", package: "firebase-ios-sdk"),
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "PluginTests",
            dependencies: ["Plugin"],
            path: "ios/PluginTests")
    ]
)
