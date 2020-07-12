Pod::Spec.new do |s|
    package = JSON.parse(File.read(File.join(File.dirname(__FILE__), 'package.json')))

    s.name = 'CapacitorCommunityFirebaseAnalytics'
    s.version = package['version']
    s.summary = package['description']
    s.license = package['license']
    s.homepage = package['homepage']
    s.author = package['author']
    s.source = { :git => 'https://github.com/capacitor-community/firebase-analytics', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.static_framework = true
    s.dependency 'Capacitor'
    s.dependency 'Firebase'
    # s.dependency 'FirebaseCore'
    s.dependency 'Firebase/Core'
    s.dependency 'Firebase/Analytics'
  end
