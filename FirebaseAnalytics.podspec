
  Pod::Spec.new do |s|
    s.name = 'FirebaseAnalytics'
    s.version = '0.0.1'
    s.summary = 'Firebase Analytics for Capacitor Community'
    s.license = ''
    s.homepage = 'git@github.com:capacitor-community/firebase-analytics.git'
    s.author = 'Priyank Patel <priyank.patel@stackspace.ca>'
    s.source = { :git => 'git@github.com:capacitor-community/firebase-analytics.git', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end