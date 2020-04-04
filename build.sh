#!/bin/bash

if [ "$#" -ne 1 ]; then
  echo "./build.sh api|android|web|full"
elif [ $1 == "api" ]; then
  cargo build -p etopai --release
elif [ $1 == "android" ]; then
  rm -rf etopan-app/app/build
  (cd etopan-app && ./gradlew :app:bundleRelease)
  java -jar ~/.bundletool-all.jar build-bundle --modules=etopan-app/app/build/intermediates/module_bundle/release/out/base.zip --output=target/build/etopan.aab
  jarsigner -keystore ~/.ltheinrich.jks -sigalg SHA256withRSA -digest-alg SHA-256 target/build/etopan.aab etopa
  JNI_LIBS=etopan-app/app/src/main/jniLibs
  if ! [[ "$PATH" == ?(*:)"$HOME/.NDK/arm64/bin"?(:*) ]]; then
    export PATH="$PATH:$HOME/.NDK/arm64/bin"
  fi
  if ! [[ "$PATH" == ?(*:)"$HOME/.NDK/arm/bin"?(:*) ]]; then
    export PATH="$PATH:$HOME/.NDK/arm/bin"
  fi
  cross build -p etopan --release --target aarch64-linux-android
  cross build -p etopan --release --target armv7-linux-androideabi
  rm -rf $JNI_LIBS && mkdir -p $JNI_LIBS/arm64-v8a && mkdir -p $JNI_LIBS/armeabi-v7a
  cp target/aarch64-linux-android/release/libetopan.so $JNI_LIBS/arm64-v8a/libetopan.so
  cp target/armv7-linux-androideabi/release/libetopan.so $JNI_LIBS/armeabi-v7a/libetopan.so
elif [ $1 == "web" ]; then
  wasm-pack build --release etopaw
  (cd etopaw-app && npm install)
  (cd etopaw-app && npm run build)
elif [ $1 == "full" ]; then
  rm -rf target/build && mkdir -p target/build
  ./build.sh api && cp target/x86_64-unknown-linux-musl/release/etopai target/build/etopai
  ./build.sh android
  ./build.sh web && (cd etopaw-app/dist && tar cfJ ../../target/build/etopaw.tar.xz *)
else
  echo "./build.sh api|android|web|full"
fi
