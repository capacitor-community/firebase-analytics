
  Pod::Spec.new do |s|
    s.name = 'CapacitorAnalytics'
    s.version = '0.0.1'
    s.summary = 'Enable Firebase Analytics for Capacitor Apps'
    s.license = 'MIT'
    s.homepage = 'https://github.com/stewwan/capacitor-analytics'
    s.author = 'Stewan Silva'
    s.source = { :git => 'https://github.com/stewwan/capacitor-analytics', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
    s.dependency 'Firebase'
    s.dependency 'FirebaseCore'
    s.dependency 'FirebaseAnalytics'
    s.static_framework = true
  end